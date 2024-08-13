import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Admin class extends User and adds admin-specific methods.
 */
public class Admin extends User {
    public Admin(String inputEmail) {
        super(inputEmail, "", "", "", Profile.ADMIN);
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input

    UserService userService = new UserService();
    
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
                    try {
                        createUser();
                    } catch (IOException ex) {
                        } catch (InterruptedException ex) {
                            }
                    }
                case 2 -> {
                    System.out.println("Download the analytics reports");
                }
                case 3 -> {
                try {
                    this.downloadListOfUsers();
                } catch (IOException ex) {
                } catch (InterruptedException ex) {
                }
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

    public void createUser() throws IOException, InterruptedException {
        // Onboard User
        System.out.println("Enter the email of the user to onboard:");
        String email = reader.readLine(); // Get user email
        if (!userService.isValidEmail(email)) { // Validate email format
            System.out.println("Invalid email format. Please try again.");
            return;
        }
        String uuid = UUID.randomUUID().toString(); // Generate UUID
        userService.runBashScript("test-create-user.sh", email, uuid); // Call bash script to onboard user
        System.out.println("User onboarded with UUID: " + uuid);   
    }

    public void downloadListOfUsers() throws IOException, InterruptedException {
        // Implement list of users download logic here
        String report = userService.runBashScript("generate_csv.sh");
        System.out.println(report); // Print the output from the bash script
        System.out.println("You can download the user report from the specified location.");
    }
}