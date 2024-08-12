import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserService {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input

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
    
    
}
