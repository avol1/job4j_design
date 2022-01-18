package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    public static final String JDBC_DRIVER = "jdbc.driver";
    public static final String JDBC_DB_URL = "jdbc.db.url";
    public static final String JDBC_LOGIN = "jdbc.login";
    public static final String JDBC_PASSWORD = "jdbc.password";

    private Connection connection;
    private Properties properties;

    public TableEditor(Properties properties) {
        try {
            this.properties = properties;
            initConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initConnection() throws Exception {
        connection = DriverManager.getConnection(properties.getProperty(JDBC_DB_URL),
                                                 properties.getProperty(JDBC_LOGIN),
                                                 properties.getProperty(JDBC_PASSWORD));
    }

    public void createTable(String tableName) {
        executeUpdate(String.format(
                "CREATE TABLE IF NOT EXISTS %s ()", tableName
        ));
    }

    public void dropTable(String tableName) {
        executeUpdate(String.format(
                "DROP TABLE %s", tableName
        ));
    }

    public void addColumn(String tableName, String columnName, String type) {
        executeUpdate(String.format(
                "ALTER TABLE %s ADD COLUMN %s %s", tableName, columnName, type
        ));
    }

    public void dropColumn(String tableName, String columnName) {
        executeUpdate(String.format(
                "ALTER TABLE %s DROP COLUMN %s", tableName, columnName
        ));
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        executeUpdate(String.format(
                "ALTER TABLE %s RENAME COLUMN %s TO %s", tableName, columnName, newColumnName
        ));
    }


    public static String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    private void executeUpdate(String query) {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        TableEditor editor = new TableEditor(initProperties());

        editor.createTable("sample_tab");
        System.out.println(getTableScheme(editor.connection, "sample_tab"));
        editor.addColumn("sample_tab", "number", "int");
        System.out.println(getTableScheme(editor.connection, "sample_tab"));
        editor.renameColumn("sample_tab", "number", "counter");
        System.out.println(getTableScheme(editor.connection, "sample_tab"));
        editor.dropColumn("sample_tab", "counter");
        System.out.println(getTableScheme(editor.connection, "sample_tab"));
        editor.dropTable("sample_tab");

        editor.close();

    }

    private static Properties initProperties() throws ClassNotFoundException {
        Config cf = new Config("./src/main/resources/app.properties");
        cf.load();

        Class.forName(cf.value(JDBC_DRIVER));
        String url = cf.value(JDBC_DB_URL);
        String login = cf.value(JDBC_LOGIN);
        String password = cf.value(JDBC_PASSWORD);

        Properties properties = new Properties();
        properties.put(JDBC_DB_URL, url);
        properties.put(JDBC_LOGIN, login);
        properties.put(JDBC_PASSWORD, password);

        return properties;
    }
}
