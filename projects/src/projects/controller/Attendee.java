package projects.controller;
import projects.ConfigReader;
import projects.model.Attendeemodel;
import projects.view.AdminView;
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

public class Attendee extends User {
    AdminView employee = new AdminView();

    private final Logger logger = Logger.getLogger(Attendee.class.getName());

    public void showDetails() {
        Scanner sc = new Scanner(System.in);
        Properties properties = ConfigReader.loadProperties();

        String databaseUrl = properties.getProperty("database.urls");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");
        String names = properties.getProperty("database.name");
        String insertDealer = properties.getProperty("insertDealer");
        String selectDealer = properties.getProperty("selectDealer");
        
        Attendeemodel attendeemodel=new Attendeemodel();

        employee.AttendeeName();
        attendeemodel.setName(sc.next());
        employee.age();
        attendeemodel.setage(sc.next());
        employee.dieselAmount();
        attendeemodel.setdieselAmount(sc.next());
        employee.petrolAmount();
        attendeemodel.setpetrolAmount(sc.next());

        try {
            Class.forName(names);
            Connection con = DriverManager.getConnection(databaseUrl, username, password);
            String insertQuery = insertDealer;
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, attendeemodel.getName());
            pstmt.setString(2, attendeemodel.getage());
            pstmt.setString(3, attendeemodel.getdieselAmount());
            pstmt.setString(4, attendeemodel.getpetrolAmount());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            logger.info("Details successfully stored in the database.");
        } catch (ClassNotFoundException | SQLException exception) {
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                if (sqlException.getSQLState().equals("28000")) {
                    logger.severe("Access denied. Unable to add Employee Details, please check your database credentials.");
                } else {
                    logger.log(Level.SEVERE, "An exception occurred:", exception);
                }
            } else {
                logger.log(Level.SEVERE, "An exception occurred:", exception);
            }
        }

        try {
            Class.forName(names);
            Connection con = DriverManager.getConnection(databaseUrl, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectDealer);
            logger.info("Employee Details:");
            while (rs.next()) {
                logger.info("name: " + rs.getString("Employee_Name"));
                logger.info("age: " + rs.getString("Employee_age"));
                logger.info("dieselAmount: " + rs.getString("Amount_of_Petrol"));
                logger.info("petrolAmount: " + rs.getString("Amount_of_Diesel"));
            }
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException exception) {
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                if (sqlException.getSQLState().equals("28000")) {
                    logger.severe("Access denied. Unable to Display Employee Details, please check your database credentials.");
                } else {
                    logger.log(Level.SEVERE, "An exception occurred:", exception);
                }
            } else {
                logger.log(Level.SEVERE, "An exception occurred:", exception);
            }
        }
    }
}
