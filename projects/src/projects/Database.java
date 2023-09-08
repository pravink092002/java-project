package projects;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static Connection connection;
    private static final Logger logger = Logger.getLogger(Database.class.getName());

    public static Connection getConnection(Properties properties) {
        if (connection == null) {
            try {
                String dbUrl = properties.getProperty("database.Admin");
                String dbUsername = properties.getProperty("database.Adminname");
                String dbPassword = properties.getProperty("database.Adminpassword");

                connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            } catch (SQLException exception) {
                logger.log(Level.SEVERE, "Error while establishing database connection.", exception);
            }
        }
        return connection;
    }

    public static String fetchUsername(Connection connection, String selectAdmin) {
        String username = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectAdmin);
            while (rs.next()) {
                username = rs.getString("username");
            }
            stmt.close();
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "Error while fetching admin details.", exception);
        }
        return username;
    }

    public static String fetchPassword(Connection connection, String selectAdmin) {
        String databasePassword = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectAdmin);
            while (rs.next()) {
                databasePassword = rs.getString("Password");
            }
            stmt.close();
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "Error while fetching admin details.", exception);
        }
        return databasePassword;
    }
    
}