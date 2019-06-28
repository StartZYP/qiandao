package com.github.startzyp.mc;

import java.sql.*;

public class DaoTool {

    private static Connection connection = null;
    private static Statement statement = null;
    public DaoTool(String DatabasePath){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+DatabasePath+".db");
            statement = connection.createStatement();
            String SuperGroupTable = "CREATE TABLE IF NOT EXISTS 'myTable'( ID INTEGER PRIMARY KEY  AUTOINCREMENT,PlayerName VARCHAR(100), PlayerDay INTEGER, CreateTime DATETIME)";
            statement.executeUpdate(SuperGroupTable);
            statement.close();
        } catch ( Exception e ) {
        }
    }


    public static void AddData(String PlayerName,String PlayerDay){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "INSERT INTO myTable (PlayerName,PlayerDay,CreateTime)values('"+PlayerName+"','"+PlayerDay+"',datetime('now','localtime'))";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void Updata(String PlayerName,String PlayerDay){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "update myTable set PlayerDay='"+PlayerDay+"' where PlayerName='"+PlayerName+"'";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static int GetPlayerDay(String PlayerName){
        int Count = -1;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from myTable where PlayerName='"+PlayerName+"'";
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                Count = rs.getInt("PlayerDay");
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Count;
    }



}
