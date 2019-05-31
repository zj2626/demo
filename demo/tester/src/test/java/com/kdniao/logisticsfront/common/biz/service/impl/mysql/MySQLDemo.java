package com.kdniao.logisticsfront.common.biz.service.impl.mysql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDemo {
    // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.1.233:3306/test";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "sql@kdn!123";

    private String sql = "INSERT INTO testa (NAME, AGE) VALUES ('eee', 30)";
    private String sql2 = "INSERT INTO testb (NAME, AGE) VALUES ('abcde', 12)";

    @Test
    public void test1() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            int rs = 1;
            for (int i = 0; i < 100000; i++) {
                stmt.executeUpdate(sql);
                System.out.println(i);
            }

            System.out.println("rs: " + rs);
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                closeConnection(conn, stmt);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test2() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            int rs = 1;
            for (int i = 0; i < 100000; i++) {
                stmt.executeUpdate(sql2);
                System.out.println(i);
            }

            System.out.println("rs: " + rs);
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                closeConnection(conn, stmt);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);

        // 打开链接
        System.out.println("连接数据库...");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void closeConnection(Connection conn, Statement stmt) throws ClassNotFoundException, SQLException {
        // 关闭资源
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ignored) {
        }// 什么都不做
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
