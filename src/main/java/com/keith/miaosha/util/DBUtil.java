package com.keith.miaosha.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author YMX
 * @date 2019/3/13 0:05
 */
public class DBUtil {
    private static Properties properties;

    static {
        try {
            InputStream in=DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(in);
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws ClassNotFoundException,SQLException{
        String url = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");
        String driver = properties.getProperty("spring.datasource.driver-class-name");
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
