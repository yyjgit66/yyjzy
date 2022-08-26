package com.yyjzy.databases;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 获取数据库配置文件
 */
public class Resource {
    public static String  DB_URL = "";
    public static String USER_NAME="";
    public static String PASS_WORD="";
    public static String DRIVER="";
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new InputStreamReader(Resource.class.getResourceAsStream("/application.yml"), "UTF-8"));
         } catch (IOException e) {
            e.printStackTrace();
        }
        DB_URL = properties.get("url").toString();
        USER_NAME = properties.get("username").toString();
        PASS_WORD = properties.get("password").toString();
        DRIVER = properties.get("driver-class-name").toString();
    }

}
