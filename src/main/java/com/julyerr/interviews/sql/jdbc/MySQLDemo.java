package com.julyerr.interviews.sql.jdbc;

import java.sql.*;

public class MySQLDemo {
    public static void main(String[] args) {

//        待插入的数据
        String[] ID = {"2008", "2009", "2010", "2012", "2015", "2018"};
        String[] name = {"Wang", "Hui", "Wan", "Yuan", "Yuan", "Yang"};
        int[] age = {16, 18, 20, 18, 22, 21};
        String[] FM = {"F", "F", "M", "M", "M", "F"};

//        连接数据库信息
        String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8";
        String username = "root";
        String password = "root";

//        静态查询语句
        String sql = "INSERT INTO student(ID,name,age,FM)VALUES(?,?,?,?)";
        String querysql = "SELECT * FROM student WHERE name LIKE 'Yuan';";

        try {
//            加载JDBC驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC驱动加载失败");
            e.printStackTrace();
        }

        try {
//            获取连接
            Connection con = DriverManager.getConnection(url, username, password);
//            语句声明
            Statement stmt = con.createStatement();
            PreparedStatement prest = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < ID.length; i++) {
                prest.setString(1, ID[i]);
                prest.setString(2, name[i]);
                prest.setInt(3, age[i]);
                prest.setString(4, FM[i]);
                prest.addBatch();
            }

//            执行语句
            prest.executeBatch();
//            获取操作结果
            ResultSet rs = stmt.executeQuery(querysql);
            while (rs.next()) {
                String qname = rs.getString("name");
                String qage = rs.getString(3);
                System.out.println(qname + " " + qage);
            }

            int rows = stmt.executeUpdate("DELETE FROM student;");
            System.out.println(rows);
//            关闭打开的对象
//            关闭结果集
            if (rs != null) {
                rs.close();
            }
//            关闭声明语句
            if (stmt != null) {
                stmt.close();
            }
//            关闭连接
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
