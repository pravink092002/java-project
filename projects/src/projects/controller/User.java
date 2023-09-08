package projects.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import projects.ConfigReader;
import projects.model.Usermodel;
import projects.view.AdminView;

public class User extends Admin {
  

    private final Logger logger = Logger.getLogger(User.class.getName());
    AdminView adminView = new AdminView();

    public void showDetails() {
        Scanner sc = new Scanner(System.in);
        Properties properties = ConfigReader.loadProperties();
        

        String databaseUrl = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");
        String Name = properties.getProperty("database.name");
        String insertUser = properties.getProperty("insertUser");
        String selectUser = properties.getProperty("selectUser");

        Usermodel usermodel = new Usermodel();
        adminView.userName();
	    usermodel.setName(sc.next());
	    adminView.vehicleType();
        usermodel.setVehicleType(sc.next());
        adminView.vehicleName();
        usermodel.setVehicleName(sc.next());
        adminView.vehicleNumber();
        usermodel.setVehicleNumber(sc.next());

        try {
            Class.forName(Name);
            Connection con = DriverManager.getConnection(databaseUrl, username, password);
            String insertQuery = insertUser;
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1,usermodel.getName());
            pstmt.setString(2, usermodel.getVehicleType());
            pstmt.setString(3, usermodel.getVehicleName());
            pstmt.setString(4, usermodel.getVehicleNumber());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            logger.info("Details successfully stored in the database.");
        } catch (ClassNotFoundException | SQLException exception) {
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                if (sqlException.getSQLState().equals("28000")) {
                    logger.severe("Access denied. Unable to add Customer Details, please check your database credentials.");
                } else {
                    logger.log(Level.SEVERE, "An exception occurred:", exception);
                }
            } else {
                logger.log(Level.SEVERE, "An exception occurred:", exception);
            }
        }

        try {
            Class.forName(Name);
            Connection con = DriverManager.getConnection(databaseUrl, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectUser);
            logger.info("Customer Details:");
            while (rs.next()) {
                logger.info("Name: " + rs.getString("Customer_name"));
                logger.info("vehicleType: " + rs.getString("Vehicle_Type"));
                logger.info("vehicleName: " + rs.getString("Vehicle_Name"));
                logger.info("vehicleNumber: " + rs.getString("Vehicle_No"));
            }
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException exception) {
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                if (sqlException.getSQLState().equals("28000")) {
                    logger.severe("Access denied. Unable to Display Customer Details, please check your database credentials.");
                } else {
                    logger.log(Level.SEVERE, "An exception occurred:", exception);
                }
            } else {
                logger.log(Level.SEVERE, "An exception occurred:", exception);
            }
        }
    }
}
