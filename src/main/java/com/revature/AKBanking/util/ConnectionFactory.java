package com.revature.AKBanking.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;
    private Properties properties = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory(){
        try {
            properties.load(new FileReader("src/main/resources/LocalDB.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getConnectionFactory(){
        if(connectionFactory == null){
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
