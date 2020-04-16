package com.zxl.creditcard.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard.utils
 * @作者: 祝学梁
 * @创建时间: 2020/4/16  10:36
 * @描述: 数据库工具类：连接数据库用、获取数据库数据
 **/
public class DBUtils {
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://101.201.143.123:3306/credit_card" +//数据库名
            "?useUnicode=true&useSSL=false&characterEncoding=utf-8";
    private static String USER = "credit_card";
    private static String PASSWORD = "zxlzjq";

    //获取dbName数据表的连接
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
