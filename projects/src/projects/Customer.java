package projects;

import java.sql.SQLException;
import projects.controller.Admin;
public class Customer {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Admin admin = new Admin();
        admin.showDetails();
    }
}
