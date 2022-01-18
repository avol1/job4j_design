package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Config cf = new Config("./src/main/resources/app.properties");
        cf.load();

        Class.forName(cf.value("jdbc.driver"));
        String url = cf.value("jdbc.db.url");
        String login = cf.value("jdbc.login");
        String password = cf.value("jdbc.password");

        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
        }
    }
}
