import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstract User class to define common attributes and methods for all users.
 */

public abstract class User {
    // Define new data type Profile for user's role
    public enum Profile {
        ADMIN, PATIENT
    }
    public String firstName;
    public String lastName;
    public String inputEmail;
    public String inputPassword;
    public Profile role;
    
    /**
     * Constructor to initialize a User object.
     */
    public User(String firstName, String lastName, String inputEmail, String inputPassword, Profile role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
        this.role = role;
    }

    public void displayMenu (){
        
    }
    

    public String login(){
         
            //Login_Admin
            
            System.out.println("Enter your email:");
            String adminEmail = reader.readLine(); // Get admin email
            Console console = System.console();
            if(console==null){
                return;
            }
            char[] passwordArray = console.readPassword("Enter your password:");
            String password = new String(passwordArray);
            String adminPassword = password;
            
            //System.out.println("Enter your password:");
            //String adminPassword = reader.readLine(); // Get admin password

            //String result = runBashScript("user-manager.sh", "login", adminEmail, adminPassword); // Call bash script to login admin
            
            String result = runBashScript("user_login.sh", adminEmail, adminPassword);

            System.out.println("Login was " + result);

                if (null == result.trim()) {
                    
                    return "Invalid login credentials.";
                } else // Determine the action based on the user type returned
                    return result.trim();
        }

    private static String runBashScript(String scriptName, String... args) throws IOException, InterruptedException {
        // Construct command to run the bash script with arguments
        StringBuilder command = new StringBuilder("bash ").append(scriptName);
        for (String arg : args) {
            command.append(" ").append(arg);
        }

        // Execute the command
        Process process = Runtime.getRuntime().exec(command.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Read and accumulate the output from the script
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // Wait for the process to complete
        process.waitFor();

        // Return the accumulated output as a String
        return output.toString().trim();
    }
    

    /**
     * Abstract method to be implemented by subclasses for logout functionality.
     */
    public abstract void logout();
}
