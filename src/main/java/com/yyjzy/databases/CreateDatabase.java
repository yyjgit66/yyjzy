package com.yyjzy.databases;

import com.yyjzy.string.StringUtils;
import com.yyjzy.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
@Slf4j
public class CreateDatabase {

    public static void createDatabase(String dataBaseName) {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = JdbcUtils.getConnection();
            stmt = conn.createStatement();
            String exist = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = '"+dataBaseName+"';";
            ResultSet resultSet = stmt.executeQuery(exist);

            if(null==resultSet || !resultSet.next() || StringUtils.isBlank(resultSet.getString(1))){
                String sql = "CREATE DATABASE "+dataBaseName;
                stmt.executeUpdate(sql);
                log.info("Database created successfully...");
            }


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JdbcUtils.closeConnection(conn,stmt);
        }
    }
}