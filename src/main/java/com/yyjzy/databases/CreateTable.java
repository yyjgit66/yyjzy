package com.yyjzy.databases;

import com.yyjzy.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
@Slf4j
public class CreateTable {

    public static  void createTable(String sql){
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = JdbcUtils.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            log.info("Created table in given database...");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JdbcUtils.closeConnection(conn,stmt);
        }
    }

    public static String exist(String sql){
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = JdbcUtils.getConnection();
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            if(null!=resultSet && resultSet.next()){
                return resultSet.getString(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JdbcUtils.closeConnection(conn,stmt);
        }
        return null;
    }
}
