package com.yyjzy.utils;

import com.yyjzy.string.StringUtils;
import com.yyjzy.constants.YyjConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {

    /**
     * 获取一个连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        String user = YamlReader.getValueByKey(YyjConstants.USER_NAME).toString();
        String password = YamlReader.getValueByKey(YyjConstants.PASS_WORD).toString();
        String url = YamlReader.getValueByKey(YyjConstants.DB_URL).toString();
        String driverClass = YamlReader.getValueByKey(YyjConstants.DRIVER).toString();
        if(!StringUtils.isBlank(url)&&url.indexOf("?")>0){
            url=url.substring(0,url.indexOf("?"));
            int i = url.lastIndexOf("/");
            url=url.substring(0,i);
        }
        // 2. 加载驱动
        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }


    /**
     * 关闭连接
     * @param connection
     * @param statement
     */
    public static void closeConnection(Connection connection, Statement statement) {
        if (connection !=  null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
