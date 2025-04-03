package Datasource;

import java.sql.*;

public class DatabaseConnection {
    private static String url = "jdbc:mariadb://localhost:3306/inclass_demo";
    private static String user = "appuser";
    private static String password = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}