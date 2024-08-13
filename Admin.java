import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Admin class extends User and adds admin-specific methods.
 */
public class Admin extends User {
    public Admin(String inputEmail, String inputPassword) {
        super(inputEmail, inputPassword, Profile.ADMIN);
    }
    
    @Override
    public void displayMenu() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input

        while (true) { // Loop to keep admin actions menu running
            System.out.println("Admin Actions Menu:");
            System.out.println("1. Create User");
            System.out.println("2. Download Reports");
            System.out.println("3. Download List of Users");
            System.out.println("4. Logout");
            int choice = Integer.parseInt(reader.readLine()); // Get admin's choice
    
            switch (choice) { // Switch based on admin's choice
                case 1 -> {
                    // Onboard User
                    System.out.println("Enter the email of the user to onboard:");
                    String email = reader.readLine(); // Get user email
                    if (!isValidEmail(email)) { // Validate email format
                        System.out.println("Invalid email format. Please try again.");
                        break;
                    }
                    String uuid = UUID.randomUUID().toString(); // Generate UUID
                    runBashScript("test-create-user.sh", email, uuid); // Call bash script to onboard user
                    System.out.println("User onboarded with UUID: " + uuid);
                    break;
                }
                case 2 -> {
                    System.out.println("Download the analytics reports");
                }
                case 3 -> {
                    // Download List of Users
                    // String report = runBashScript("generate_csv.sh");
                    // // runBashScript("user-manager.sh", "generate-csv"); // Call bash script to generate list of users
                    // System.out.println(report);
                    String report = runBashScript("generate_csv.sh");
                    System.out.println(report); // Print the output from the bash script
                    System.out.println("You can download the user report from the specified location.");
                }
                case 4 -> {
                    // Logout
                    return; // Logout and return to admin menu
                }
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid choice
            }
            // Switch based on admin's choice
                    }
    }
    
    
    public void createUser() {
       // Onboard User
       System.out.println("Enter the email of the user to onboard:");
       String email = reader.readLine(); // Get user email
       if (!isValidEmail(email)) { // Validate email format
           System.out.println("Invalid email format. Please try again.");
           break;
       }
       String uuid = UUID.randomUUID().toString(); // Generate UUID
       runBashScript("test-create-user.sh", email, uuid); // Call bash script to onboard user
       System.out.println("User onboarded with UUID: " + uuid);
       break;
   
    }
    /**
     * Method to download reports.
     */
    public void downloadReports() {
        // Implement report download logic here
    }

    /**
     * Method to download list of users.
     */
    public void downloadListOfUsers() {
        // Implement list of users download logic here
            String report = runBashScript("generate_csv.sh");
                System.out.println(report); // Print the output from the bash script
                System.out.println("You can download the user report from the specified location.");
    }

    @Override
    public void logout() {
        // Implement logout logic for Admin
    }
}
private void AdminMenu(BufferedReader reader) {
    while (true) { // Loop to keep admin actions menu running
        System.out.println("Admin Actions Menu:");
        System.out.println("1. Create User");
        System.out.println("2. Download Reports");
        System.out.println("3. Download List of Users");
        System.out.println("4. Logout");
        int choice = Integer.parseInt(reader.readLine()); // Get admin's choice

        switch (choice) { // Switch based on admin's choice
            case 1 -> {
                // Onboard User
                System.out.println("Enter the email of the user to onboard:");
                String email = reader.readLine(); // Get user email
                if (!isValidEmail(email)) { // Validate email format
                    System.out.println("Invalid email format. Please try again.");
                    break;
                }
                String uuid = UUID.randomUUID().toString(); // Generate UUID
                runBashScript("test-create-user.sh", email, uuid); // Call bash script to onboard user
                System.out.println("User onboarded with UUID: " + uuid);
                break;
            }
            case 2 -> {
                System.out.println("Download the analytics reports");
            }
            case 3 -> {
                // Download List of Users
                // String report = runBashScript("generate_csv.sh");
                // // runBashScript("user-manager.sh", "generate-csv"); // Call bash script to generate list of users
                // System.out.println(report);
                String report = runBashScript("generate_csv.sh");
                System.out.println(report); // Print the output from the bash script
                System.out.println("You can download the user report from the specified location.");
            }
            case 4 -> {
                // Logout
                return; // Logout and return to admin menu
            }
            default -> System.out.println("Invalid choice, please try again."); // Handle invalid choice
        }
        // Switch based on admin's choice
                }
}
