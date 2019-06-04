package com.kdniao.logisticsfront.common.biz.service.impl.mysql;

import org.junit.Test;

import java.sql.*;
import java.util.UUID;

public class MySQLDemo {
    // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.1.233:3306/test";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "sql@kdn!123";

    private String sql = "INSERT INTO testa (NAME, AGE) VALUES";
    private String sql2 = "INSERT INTO testb (NAME, AGE) VALUES";
    private String sql3 = "SELECT t1.ID, t1.AGE FROM testa t1 JOIN testb t2 ON t1.AGE = t2.AGE WHERE t2.AGE < 35;";
    private String sql4 = "SELECT t1.ID, t1.AGE FROM testa t1 JOIN (SELECT t2.AGE FROM testb t2 WHERE t2.AGE < 35) t2 WHERE t1.AGE = t2.AGE;";
    private String sql5 = "SELECT t1.ID, t1.AGE FROM testa t1 WHERE AGE IN (SELECT t2.AGE FROM testb t2 WHERE t2.AGE < 35);";

    @Test
    public void test() {
        System.out.println(Double.valueOf(Math.floor(Math.random() * 10000)).intValue());
    }

    @Test
    public void test1() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            int rs = 1;
            for (int i = 0; i < 100; i++) {
                StringBuilder stringBuilder = new StringBuilder(sql);
                for (int j = 0; j < 200000; j++) {
                    stringBuilder.append(String.format(" ('%s', %d) ", UUID.randomUUID().toString().substring(0, 5), Double.valueOf(Math.floor(Math.random() * 500)).intValue())).append(",");
                }
                System.out.println(i);
                stmt.executeUpdate(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
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
            for (int i = 0; i < 10; i++) {
                StringBuilder stringBuilder = new StringBuilder(sql2);
                for (int j = 0; j < 200000; j++) {
                    stringBuilder.append(String.format(" ('%s', %d) ", UUID.randomUUID().toString().substring(0, 5), Double.valueOf(Math.floor(Math.random() * 500)).intValue())).append(",");
                }
                System.out.println(i);
                stmt.executeUpdate(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
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
    public void test3() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sql3);
            int rs = 0;
            while (resultSet.next()) {
                rs++;
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
    public void test4() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sql4);
            int rs = 0;
            while (resultSet.next()) {
                rs++;
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
    public void test5() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sql5);
            int rs = 0;
            while (resultSet.next()) {
                rs++;
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
