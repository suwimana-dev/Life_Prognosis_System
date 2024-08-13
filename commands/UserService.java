import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate; // Importing DateTimeParseException for handling date parsing exceptions
import java.time.format.DateTimeFormatter; // Importing DateTimeFormatter for date formatting
import java.time.format.DateTimeParseException; // Importing UUID to generate unique IDs
import java.util.regex.Matcher; // Importing Matcher for regex operations
import java.util.regex.Pattern; // Importing Pattern for regex operations

public class UserService {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input

    public String runBashScript(String scriptName, String... args) throws IOException, InterruptedException {
        // Construct command to run the bash script with arguments
        StringBuilder command = new StringBuilder("bash ").append(scriptName);
        for (String arg : args) {
            command.append(" ").append(arg);
        }

        // Execute the command
        Process process = Runtime.getRuntime().exec(command.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Read and accumulates the output from the script
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

    public String login(String email, String password) throws IOException, InterruptedException{
         
        //Login_Admin          
        
        String result = runBashScript("../scripts/user_login.sh", email, password);

        System.out.println("Login was " + result);

        if (null == result.trim()) {
            
            return "Invalid login credentials.";
        } else // Determine the action based on the user type returned
            return result.trim();
    }
        
    public void registerPatient() throws IOException, InterruptedException {
    // registration for user.             
        System.out.println("Enter the UUID of the user:");
        String uuid = reader.readLine(); // Get UUID
        
        if (!isValidUUID(uuid)) { // Validate UUID format
            System.out.println("Invalid UUID. Please try again.");
            return;
        }
        //String email = runBashScript("user-manager.sh", "verify-uuid", uuid); // Call bash script to verify UUID
        //Call bash script to verify UUID, returns email
        String email = (runBashScript("../scripts/verifyuuid.sh", uuid)); 
        
        if (email.isEmpty()) {
            System.out.println("Invalid UUID."); // Handle invalid UUID
        } else {
            // Proceed with patient registration
            System.out.println("Enter First Name:");
            String firstName = reader.readLine(); // Get first name
            System.out.println("Enter Last Name:");
            String lastName = reader.readLine(); // Get last name
            System.out.println("Enter Date of Birth (YYYY-MM-DD):");
            String dateBirth = reader.readLine(); // Get date of birth
            if (!isValidDate(dateBirth)) { // Validate date format
                System.out.println("Invalid date format. Please try again.");
                return;
            }
            System.out.println("HIV Status (true/false):");
            String statusHiv = reader.readLine(); // Get HIV status
            String dateDiagnosis = null;
            String statusArt = null;
            String dateArt = null;
            if (statusHiv.equalsIgnoreCase("true")) {
                System.out.println("Date of Diagnosis (YYYY-MM-DD):");
                dateDiagnosis = reader.readLine(); // Get date of diagnosis
                if (!isValidDate(dateDiagnosis)) { // Validate date format
                    System.out.println("Invalid date format. Please try again.");
                return;
                }
                System.out.println("ART Status (true/false):");
                statusArt = reader.readLine(); // Get ART status
                System.out.println("Date of ART (YYYY-MM-DD):");
                dateArt = reader.readLine(); // Get date of ART
                if (!isValidDate(dateArt)) { // Validate date format
                    System.out.println("Invalid date format. Please try again.");
                return;
                }
            //Validate date order
                if (!isDateOrderValid(dateBirth, dateDiagnosis, dateArt)) {
                    System.out.println("Invalid date order. Ensure date of birth is before diagnosis and ART dates.");
                return;
                }
            } else if (statusHiv.equalsIgnoreCase("false")) {
                //return;
            } else {
                System.out.println("Invalid selection! Exit.");
                System.exit(0);
            }
            System.out.println("Country ISO Code:");
            String countryISO = reader.readLine(); // Get country ISO code
            Console console = System.console();
            if(console==null){
                return;
            }
            char[] passwordArray = console.readPassword("Enter password:");
            String apassword = new String(passwordArray);
            String password = apassword;
            // Call bash script to register patient
            runBashScript("../scripts/user-manager.sh", "register", firstName, lastName, email, uuid, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); 
            //runBashScript("user-manager.sh", "register", firstName, lastName, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); 
            System.out.println("Patient registered successfully.");
        }
    }

    // Helper method to validate UUID format
    public boolean isValidUUID(String uuid) {
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(uuid);
        return matcher.matches();
    }

    // Helper method to validate date format (YYYY-MM-DD)
    public boolean isValidDate(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

     // Helper method to validate email format
     public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Helper method to validate date order
    public boolean isDateOrderValid(String dateBirth, String dateDiagnosis, String dateArt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate birthDate = LocalDate.parse(dateBirth, formatter);
            LocalDate diagnosisDate = LocalDate.parse(dateDiagnosis, formatter);
            LocalDate artDate = LocalDate.parse(dateArt, formatter);

            return birthDate.isBefore(diagnosisDate) && diagnosisDate.isBefore(artDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
    //Helper method for logging out or exiting.