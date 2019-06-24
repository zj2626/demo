package com.kdniao.logisticsfront.common.biz.service.impl.mysql;

import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.UUID;

/**
 * 操作数据库 test库中的testa表和testb表
 */
public class MySQLDemo {
    // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://192.168.1.233:3306/test";
    private static final String USER = "root";
    private static final String PASS = "sql@kdn!123";

//    private static String DB_URL = "jdbc:mysql://127.0.0.1:3306/test";
//    private static String USER = "root";
//    private static String PASS = "123456";

    private String sql = "INSERT INTO testa (NAME, AGE) VALUES";
    private String sql2 = "INSERT INTO testb (NAME, AGE) VALUES";
    private String sql21 = "INSERT INTO testc (NAME, AGE) VALUES";
    private String sql22 = "INSERT INTO testd (NAME, AGE, SEX, COMMENT) VALUES";
    private String sql3 = "SELECT t1.ID, t1.AGE FROM testc t1 JOIN testd t2 ON t1.AGE = t2.AGE WHERE t2.AGE < 35;";
    private String sql4 = "SELECT t1.ID, t1.AGE FROM testc t1 JOIN (SELECT t2.AGE FROM testd t2 WHERE t2.AGE < 35) t2 WHERE t1.AGE = t2.AGE;";
    private String sql5 = "SELECT t1.ID, t1.AGE FROM testc t1 WHERE AGE IN (SELECT t2.AGE FROM testd t2 WHERE t2.AGE < 35);";

    private String sql6 = "SELECT * FROM testc_copy1";

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
    public void test21() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            int rs = 1;
            for (int i = 0; i < 2; i++) {
                StringBuilder stringBuilder = new StringBuilder(sql21);
                for (int j = 0; j < 2000; j++) {
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
    public void test22() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            int rs = 1;
            for (int i = 0; i < 2; i++) {
                StringBuilder stringBuilder = new StringBuilder(sql22);
                for (int j = 0; j < 4000; j++) {
                    stringBuilder.append(String.format(" ('%s', %d, %d, '%s') ",
                            UUID.randomUUID().toString().substring(0, 5),
                            Double.valueOf(Math.floor(Math.random() * 500)).intValue(),
                            Double.valueOf(Math.floor(Math.random() * 500)).intValue(),
                            UUID.randomUUID().toString().substring(0, 5)
                            )
                    ).append(",");
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

    /**
     * 数据库
     * 字符类型:
     * ***char varchar 都是以字符为单位, 也就是说如果设置长度为10,则可以插入10个字母或者10个汉字(varchar是变长,实际存储大小随字符串长度而定)
     * <p>
     * 数字类型:
     * ***tinyint smallint int 其都有固定长度 所以设计表时设置长度并不会影响其存储大小 只是影响了其显示长度(不影响使用代码操作的显示)
     * tinyint:     存储大小为 1 个字节(-128 ~ 127)
     * smallint:    存储大小为 2 个字节(--32,768 ~ 32,767)
     * int:         存储大小为 4 个字节(-2,147,483,648 ~ 2,147,483,647)
     * <p>
     * 浮点类型:
     * ***float double decimal 其长度受到设计表时设置长度影响 如果超过长度则进行四舍五入,之后的结果也要满足设置的长度要求
     * ***使用（M，D）表示,其中M表示该值的总共长度，D表示小数点后面的长度，M和D又称为精度和标度
     * ***FLOAT和DOUBLE在不指 定精度时，默认会按照实际的精度来显示，而DECIMAL在不指定精度时，默认整数为10，小数为0。
     * ***decimal是精确的 其他不是;FLOAT用于单精度，而DOUBLE是双精度数字。 MySQL的单精度值使用四个字节，双精度值使用八个字节。
     * float:       设置长度(5,2) : (-999.99 ~ 999.99)
     * double:      设置长度(5,2) : (-999.99 ~ 999.99)
     * decimal:     设置长度(5,2) : (-999.99 ~ 999.99)
     */
    @Test
    public void test6() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sql6);
            int rs = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String namec = resultSet.getString("NAMEC");
                int age = resultSet.getInt("AGE");
                int head = resultSet.getInt("head");
                int it = resultSet.getInt("IT");
                float ft = resultSet.getFloat("FT");
                double dt = resultSet.getDouble("DT");
                BigDecimal bt = resultSet.getBigDecimal("BT");
                System.out.println(age + "\t" + head + "\t" + it + "\t" + ft + "\t" + dt + "\t" + bt);

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

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);

        // 打开链接
        System.out.println("连接数据库...");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void closeConnection(Connection conn, Statement stmt) throws ClassNotFoundException, SQLException {
        // 关闭资源
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ignored) {
        }// 什么都不做
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
