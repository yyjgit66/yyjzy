package com.yyjzy.databases;

import com.yyjzy.collection.CollectionUtil;
import com.yyjzy.string.StringUtils;
import com.yyjzy.annotation.*;
import com.yyjzy.constants.YyjConstants;
import com.yyjzy.utils.YamlReader;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Slf4j
public class YyjzyApplication {

    public static void run(Class primarySource) throws ClassNotFoundException {
        Annotation annotation = primarySource.getAnnotation(YyjzyScan.class);
        if(null==annotation){
            throw new ClassNotFoundException("YyjzyScan annotation not exist");
        }
        YyjzyScan yyjzyScan = (YyjzyScan) annotation;
        String[] value = yyjzyScan.value();
        if(null==value || value.length<=0){
            throw new NullPointerException("value of YyjzyScan can not  empty");
        }
        String dataBaseName = getDatabasesName();
        if(StringUtils.isBlank(dataBaseName)){
            throw new NullPointerException("databases can not  empty");
        }
        for (String s : value) {
            Reflections reflections = new Reflections(s);
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(TableName.class);
            for (Class<?> aClass : typesAnnotatedWith) {
                // 获取表名
                String teName ="";
                TableName tableName = aClass.getAnnotation(TableName.class);
                if(null!=tableName){
                    String tName = tableName.value();
                    if(StringUtils.isBlank(tName)){
                        teName = aClass.getSimpleName().toUpperCase();
                    }
                }
                // 获取所有字段
                Field[] fields = aClass.getFields();
                if(null==fields || fields.length==0){
                    continue;
                }
                String line = "";
                String primaryKey = "";
                List<String> addList = new ArrayList<>();
                List<String> modifyList = new ArrayList<>();
                for (Field field : fields) {
                    Property property = field.getAnnotation(Property.class);
                    Index index = field.getAnnotation(Index.class);
                    Modify modify = field.getAnnotation(Modify.class);
                    Add add = field.getAnnotation(Add.class);
                    addColumn(dataBaseName,teName, addList, add);
                    modifyColumn(dataBaseName,teName, modifyList, modify);
                    line = insertColumn(line, property);
                    if(null!=index && !StringUtils.isBlank(line)){
                        String f = property.value().toUpperCase();
                        primaryKey=" PRIMARY KEY ( "+f+" )";
                    }

                }

                if(!StringUtils.isBlank(line)){
                    String sql  = "create table "+dataBaseName+"."+teName+"( "+line+primaryKey +")";
                    log.debug("sql------>>>>>>{}",sql);
                    CreateDatabase.createDatabase(dataBaseName);
                    String tname = isExist(dataBaseName, teName);
                    if(!teName.equalsIgnoreCase(tname)){
                        CreateTable.createTable(sql);
                    }else{
                        updateTable(addList, modifyList);
                    }

                }

            }

        }
    }

    /**
     * 查询表是否存在
     * @param dataBaseName
     * @param teName
     * @return
     */
    private static String isExist(String dataBaseName, String teName) {
        String exist = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE" +
                " table_schema = '"+ dataBaseName +"' AND table_name = '"+ teName +"' " +
                "AND table_type = 'BASE TABLE';";
        String tname = CreateTable.exist(exist);
        return tname;
    }

    /**
     * 修改表结构
     * @param addList
     * @param modifyList
     */
    private static void updateTable(List<String> addList, List<String> modifyList) {
        boolean notEmptyToAdd = CollectionUtil.isNotEmpty(addList);
        if(notEmptyToAdd){
            for (String a : addList) {
                CreateTable.createTable(a);
            }
        }
        boolean notEmptyToMidify = CollectionUtil.isNotEmpty(modifyList);
        if(notEmptyToMidify){
            for (String a : modifyList) {
                CreateTable.createTable(a);
            }
        }
    }

    private static String insertColumn(String line, Property property) {
        if(null!= property){
            String f = property.value().toUpperCase();
            String type = property.type().toUpperCase();
            String comment = property.comment().toUpperCase();
            boolean empty = property.isEmpty();
            String isEmpty = "";
            if(!empty){
                isEmpty="NOT NULL";
            }
            line += f+" "+type+" "+isEmpty+" COMMENT '"+comment+"',";

        }
        return line;
    }

    private static void modifyColumn(String dataBaseName,String teName, List<String> modifyList, Modify modify) {
        if(null!=modify){
            String f = modify.value().toUpperCase();
            String type = modify.type().toUpperCase();
            String comment = modify.comment().toUpperCase();
            boolean empty = modify.isEmpty();
            String isEmpty = "";
            if(!empty){
                isEmpty="NOT NULL";
            }
            String a = "alter  table "+dataBaseName+"."+teName+" modify "+f+" "+type+" "+isEmpty+" COMMENT '"+comment+"';";
            modifyList.add(a);
        }
    }

    /**
     * 添加字段
     * @param teName
     * @param addList
     * @param add
     */
    private static void addColumn(String dataBaseName,String teName, List<String> addList, Add add) {
        if(null!=add){
            String f = add.value().toUpperCase();
            String type = add.type().toUpperCase();
            String comment = add.comment().toUpperCase();
            boolean empty = add.isEmpty();
            String isEmpty = "";
            if(!empty){
                isEmpty="NOT NULL";
            }

            String a = "alter  table "+dataBaseName+"."+teName+" add column "+f+" "+type+" "+isEmpty+" COMMENT '"+comment+"';";
            String sql = "select count(*) from information_schema.columns where table_schema='"+dataBaseName+"' " +
                    " and  table_name = '"+teName+"' and column_name = '"+f+"' ";
            String exist = CreateTable.exist(sql);
            if(Integer.parseInt(exist)==0){
                addList.add(a);
            }

        }
    }


    /**
     * 获取库名
     * @return
     */
    private static String getDatabasesName() {
        String dataBaseName = YamlReader.getValueByKey(YyjConstants.DB_URL).toString();
        if(!StringUtils.isBlank(dataBaseName)){
            int i = dataBaseName.indexOf("?");
            if(i>0){
                dataBaseName=dataBaseName.substring(0,i);
                int i1 = dataBaseName.lastIndexOf("/");
                if(i1>0){
                    dataBaseName=dataBaseName.substring(i1+1);
                }
            }
        }
        return dataBaseName;
    }
}
