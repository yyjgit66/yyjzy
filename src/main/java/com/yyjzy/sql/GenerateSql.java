package com.yyjzy.sql;


import com.yyjzy.utils.YyjUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 生成sql
 * @author yyj
 */
@Slf4j
public class GenerateSql {

    /**
     * 获取插入的sql语句
     * @param valueType
     * @return
     * @throws IllegalAccessException
     */
    public static String  getInserting(Object valueType) throws IllegalAccessException {
        Field[] declaredFields = valueType.getClass().getDeclaredFields();
        String packageName = valueType.getClass().getName();
        int lastIndex = packageName.lastIndexOf(".");
        String className = packageName.substring(lastIndex + 1);
        char[] chars = className.toCharArray();
        chars[0] += 32;
        String  tableName = getField(String.valueOf(chars));
        StringBuilder insertName = new StringBuilder();
        StringBuilder insertValue = new StringBuilder();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object value = declaredField.get(valueType);
            if(null!=value && !value.equals("")){
                insertValue.append("'"+value+"'");
                insertValue.append(",");
                String name = declaredField.getName();
                  name = getField(name);

                insertName.append(name);
                insertName.append(",");
            }
         }
        if(!"".equals(insertName.toString()) && !"".equals(insertValue.toString())){
            return  "insert into "+ tableName + " ("+ insertName.substring(0,insertName.length()-1)+")"+ " values ("+insertValue.substring(0,insertValue.length()-1)+");";
        }

        return null;
    }

    /**
     * 获取大写字母
     * @param values
     * @return
     * @throws IOException
     */
    public static String getTemplate(List<Map<String,String>> values) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("yyjzy.text");
        byte b[] = new byte[1024];
        int len = 0;
        /**
         * 所有读取的内容都使用temp接收
         */
        int temp=0;
        /**
         *  当没有读取完时，继续读取
         */
        while((temp=inputStream.read())!=-1){
            b[len]=(byte)temp;
            len++;
        }
        inputStream.close();
        String result = new String(b, 0, len);
        for (Map<String, String> value : values) {
            Set<String> key = value.keySet();
            for (String mapName : key) {
                result= result.replace("${"+mapName+"}",value.get(mapName));
            }
        }
        return result;
    }

    /**
     * 获取数据库字段
     * @param name
     * @return
     */
    public static String getField(String name){
        String capital = YyjUtil.getCapital(name);
        if(null!=capital && !"".equals(capital)){
            int index = name.indexOf(capital);
            String prefix = name.substring(0, index);
            String suffix = name.substring(index + 1);
            String substring = name.substring(index, index + 1);
            suffix = substring.toLowerCase()+suffix;
            return prefix+"_"+suffix;
        }
        return name;
    }

    public void temp(File file){
        //需要解析的Excel文件
        //File file=new  File("e:/sheet2.xls");
        try {
            //获取工作簿
            FileInputStream fs= FileUtils.openInputStream(file);
            HSSFWorkbook workbook=new HSSFWorkbook(fs);
            //获取第一个工作表
            HSSFSheet hs=workbook.getSheetAt(0);
            //获取Sheet的第一个行号和最后一个行号
            int last=hs.getLastRowNum();
            int first=hs.getFirstRowNum();
            //遍历获取单元格里的信息
            for (int i = first; i <last; i++) {
                HSSFRow row=hs.getRow(i);
                int firstCellNum=row.getFirstCellNum();//获取所在行的第一个行号
                int lastCellNum=row.getLastCellNum();//获取所在行的最后一个行号
                for (int j = firstCellNum; j <lastCellNum; j++) {
                    HSSFCell cell=row.getCell(j);
                    String value=cell.getStringCellValue();
                    System.out.print(value+" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            log.info("读取文件异常"+e.getMessage());
        }

    }
}
