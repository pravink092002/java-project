package projects.view;

public class AdminView {

	  public void displayAdminUsernamePrompt() {
	        System.out.println("Enter Admin Username:");
	    }

	    public void displayAdminPasswordPrompt() {
	        System.out.println("Enter Admin Password:");
	    }

	    public void displayAdminLoginSuccess() {
	        System.out.println("Admin Login Success!");
	    }

	    public void displayInvalidAdminCredentials() {
	        System.out.println("Invalid Admin Username or Password!");
	    }
	    public void selectOption() {
	    	System.out.println("\nSelect an option:");
            System.out.println("1. Add Customer Details");
            System.out.println("2. Add Employee Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
	    }
	    public void invalidOption() {
	    	   System.out.println("Invalid choice. Please try again.");
	    }
	    public void userName() {
	    	System.out.println("Enter the Name of the Customer:");
	    }
	    public void vehicleType() {
	    	 System.out.println("Enter the Vehicle type:");
	    }
	    public void vehicleName() {
	    	System.out.println("Enter the Vehicle Name:");
	    }
	    public void vehicleNumber() {
	    	  System.out.println("Enter the Vehicle No:");
	    }
	    public void AttendeeName() {
	    	System.out.println("Enter the Name of Attendee:");
	    }
	    public void dieselAmount() {
	    	 System.out.println("Enter the Amount of Diesel filled by Attendee:");
	    }
	    public void age() {
	    	 System.out.println("Enter the Attendee age:");
	    }
	    public void petrolAmount() {
	        System.out.println("Enter the Amount of Petrol filled by Attendee:");
	    }
	    
	}