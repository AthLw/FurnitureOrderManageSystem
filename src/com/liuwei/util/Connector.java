package com.liuwei.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName Connection
 * @Description TODO
 * @Author AthLw
 * @Date 10:00 2019/5/25
 * @Version 1.0
 **/
public class Connector {
    public static final String user="root";

    public static final String pwd="99886";

    public static final String driver = "com.mysql.cj.jdbc.Driver";

    public static final String url = "jdbc:mysql://localhost:3306/ordermanagementsystem?" +
            "useSSL=false&serverTimezone=UTC";

    private static Connection conn;

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("load driver failed!");
            e.printStackTrace();
        }
    }

    /*
    * single instance
    * */
    public synchronized static Connection getInstance(){
        if(conn == null){
            try {
                conn = DriverManager.getConnection(url, user, pwd);
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                System.out.println("connect mysql failed");
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void close(){
        try {
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
