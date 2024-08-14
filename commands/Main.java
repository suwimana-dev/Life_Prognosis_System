import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Main {

    // Method to run the bash script with specified arguments and return the output as a String
    

    public static void main(String[] args) {
        UserService userService = new UserService();
        try {
            while (true) { // Infinite loop to keep the program running
                // Display initial menu
                System.out.println("========================================");
                System.out.println("          WELCOME TO THE LIFE PROGNOSIS TOOL          ");
                System.out.println("========================================");
                System.out.println("Select one of the options below to proceed (1,2 or 3):");
                System.out.println("====================");
                System.out.println("1. Login:");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.println("========================================");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input
                int choice = Integer.parseInt(reader.readLine()); // Get user's choice

                switch (choice) { // Switch based on user's choice
                    case 1 -> {
                        //Login_Admin
                        System.out.println("Enter your email:");
                        String email = System.console().readLine(); // Get admin email
                        char[] passwordArray = System.console().readPassword("Enter your password:");
                        System.out.println("====================");
                        String password = new String(passwordArray);
                        String userRole = userService.login(email, password);
                        switch (userRole) {
                            case "ADMIN" -> {
                                Admin loggedInUser  = new Admin(email); // Call admin actions handler if login is successful
                                loggedInUser.displayMenu();
                            }
                            case "PATIENT" -> {
                                Patient loggedInUser = new Patient(email);
                                loggedInUser.displayMenu();
                            }
                            default -> System.out.println("Invalid login credentials."); // Handle invalid login
                        }
                    }
                    case 2 -> {
                        // registration of a patient 
                        userService.registerPatient();
                
                    }
                    case 3 -> {
                        // Exit
                        System.out.println("Exiting...");
                        return; // Exit the program
                    }
                    default ->
                        System.out.println("Invalid choice, please try again."); // Handle invalid choice
                }
                // Switch based on user's choice
            }
        } catch (IOException | InterruptedException e) { // Catch exceptions
            System.err.println("Error: " + e.getMessage());
        }
    }
    }
