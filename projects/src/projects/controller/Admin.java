package projects.controller;
import projects.view.AdminView;
import projects.Database;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class Owner {
    abstract void showDetails() throws ClassNotFoundException, SQLException;
}

public class Admin extends Owner {
    private String name;
    private String password;
    private String username;
    private String databasePassword;
    AdminView adminView = new AdminView();

    private final Logger logger = Logger.getLogger(Admin.class.getName());
    private final Scanner sc = new Scanner(System.in);

   public Admin() {
        configureLogger();
    }

    private void configureLogger() {
        logger.setLevel(Level.ALL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
    }

   public void showDetails() {
        Properties properties = loadProperties();

        String selectAdmin = properties.getProperty("selectAdmin");
        Connection connection = Database.getConnection(properties);

        if (connection != null) {
            username = Database.fetchUsername(connection, selectAdmin);
            databasePassword = Database.fetchPassword(connection, selectAdmin);

            logger.info("Admin Login");
            adminView.displayAdminUsernamePrompt();
            name = sc.nextLine();

            if (name.equals(this.username)) {
                adminView.displayAdminPasswordPrompt();
                password = sc.nextLine();

                if (databasePassword != null && password.equals(this.databasePassword)) {
                    logger.info("Welcome !");
                    adminView.displayAdminLoginSuccess();
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        adminView.selectOption();
                        int choice = scanner.nextInt();

                        switch (choice) {
                            case 1:
                                User display2 = new User();
                                display2.showDetails();
                                break;
                            case 2:
                                Attendee display3 = new Attendee();
                                display3.showDetails();
                                break;
                            default:
                                adminView.invalidOption();
                                break;
                        }
                    }
                } else {
                    logger.warning("Wrong Password");
                    adminView.displayInvalidAdminCredentials();
                }
            } else {
                logger.warning("Wrong Login Id:");
                adminView.displayInvalidAdminCredentials();
            }
        }
    }
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An exception occurred while loading properties:", e);
        }
        return properties;
    }
}
