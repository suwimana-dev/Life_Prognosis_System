import java.io.BufferedReader; // Importing BufferedReader to read input from the user
import java.io.Console;
import java.io.IOException; // Importing InputStreamReader to read input from the user
import java.io.InputStreamReader; // Importing IOException to handle IO exceptions
import java.time.LocalDate; // Importing UUID to generate unique IDs
import java.time.format.DateTimeFormatter; // Importing Matcher for regex operations
import java.time.format.DateTimeParseException; // Importing Pattern for regex operations
import java.util.UUID; // Importing LocalDate for date operations
import java.util.regex.Matcher; // Importing DateTimeFormatter for date formatting
import java.util.regex.Pattern; // Importing DateTimeParseException for handling date parsing exceptions

public class Main {

    // Method to run the bash script with specified arguments and return the output as a String
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

    public static void main(String[] args) {
        try {
            while (true) { // Infinite loop to keep the program running
                // Display initial menu
                System.out.println("1. Login:");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input
                int choice = Integer.parseInt(reader.readLine()); // Get user's choice

                switch (choice) { // Switch based on user's choice
                    case 1 -> {
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
                                
                                System.out.println("Invalid login credentials."); // Handle invalid login
                            } else // Determine the action based on the user type returned
                                switch (result.trim()) {
                                    case "ADMIN" -> adminActions(reader); // Call admin actions handler if login is successful
                                    case "PATIENT" -> patientActions(reader); // Call patient actions handler if login is successful
                                    default -> System.out.println("Invalid login credentials."); // Handle invalid login
                                    }
                    }
                    case 2 -> {
                        // registration for user.

                        
                        System.out.println("Enter the UUID of the user:");
                        String uuid = reader.readLine(); // Get UUID
                        
                        if (!isValidUUID(uuid)) { // Validate UUID format
                             System.out.println("Invalid UUID. Please try again.");
                             break;
                         }
                        //String email = runBashScript("user-manager.sh", "verify-uuid", uuid); // Call bash script to verify UUID
                        //Call bash script to verify UUID, returns email
                        String email = (runBashScript("verifyuuid.sh", uuid)); 
                        

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
                                break;
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
                                break;
                                }
                                System.out.println("ART Status (true/false):");
                                statusArt = reader.readLine(); // Get ART status
                                System.out.println("Date of ART (YYYY-MM-DD):");
                                dateArt = reader.readLine(); // Get date of ART
                                if (!isValidDate(dateArt)) { // Validate date format
                                    System.out.println("Invalid date format. Please try again.");
                                break;
                                }

                            //Validate date order
                                if (!isDateOrderValid(dateBirth, dateDiagnosis, dateArt)) {
                                    System.out.println("Invalid date order. Ensure date of birth is before diagnosis and ART dates.");
                                break;
                                }

                                
                            } else if (statusHiv.equalsIgnoreCase("false")) {
                                //return;
                            } else {
                                System.out.println("Invalid selection! Exit.");
                                System.exit(0);

                            }
                            
                            // System.out.println("Date of Diagnosis (YYYY-MM-DD):");
                            // String dateDiagnosis = reader.readLine(); // Get date of diagnosis
                            // if (!isValidDate(dateDiagnosis)) { // Validate date format
                            //     System.out.println("Invalid date format. Please try again.");
                            //     break;
                            // }
                            // System.out.println("ART Status (true/false):");
                            // String statusArt = reader.readLine(); // Get ART status
                            // System.out.println("Date of ART (YYYY-MM-DD):");
                            // String dateArt = reader.readLine(); // Get date of ART
                            // if (!isValidDate(dateArt)) { // Validate date format
                            //     System.out.println("Invalid date format. Please try again.");
                            //     break;
                            // }

                            // // Validate date order
                            // if (!isDateOrderValid(dateBirth, dateDiagnosis, dateArt)) {
                            //     System.out.println("Invalid date order. Ensure date of birth is before diagnosis and ART dates.");
                            //     break;
                            // }

                            System.out.println("Country ISO Code:");
                            String countryISO = reader.readLine(); // Get country ISO code
                            Console console = System.console();
                            if(console==null){
                                return;
                            }
                            char[] passwordArray = console.readPassword("Enter password:");
                            String apassword = new String(passwordArray);
                            String password = apassword;
                            //System.out.println("Enter Password:");
                            //String password = reader.readLine(); // Get password

                            // Call bash script to register patient
                            runBashScript("user-manager.sh", "register", firstName, lastName, email, uuid, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); 
                            //runBashScript("user-manager.sh", "register", firstName, lastName, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); 
                            System.out.println("Patient registered successfully.");
                        }
                    }
                    case 3 -> {
                        // Exit
                        System.out.println("Exiting...");
                        return; // Exit the program
                    }
                    default -> System.out.println("Invalid choice, please try again."); // Handle invalid choice
                }
                // Switch based on user's choice
                            }
        } catch (IOException | InterruptedException e) { // Catch exceptions
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void handleAdminMenu(BufferedReader reader) throws IOException, InterruptedException {
        while (true) { // Loop to keep the admin menu running
            // Display Admin menu
            System.out.println("Admin Menu:");
            System.out.println("1. Onboard User");
            System.out.println("3. Generate CSV Files");
            System.out.println("4. Exit to Main Menu");
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
                case 2, 3 -> // Login
                    // Generate CSV Files
                    System.out.println("Please log in to access this feature."); // Prompt admin to login first
                case 4 -> {
                    // Exit to Main Menu
                    return; // Return to main menu
                }
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid choice
            }
            // Switch based on admin's choice
            // Login
            // System.out.println("Enter your email:");
            // String adminEmail = reader.readLine(); // Get admin email
            // System.out.println("Enter your password:");
            // String adminPassword = reader.readLine(); // Get admin password
            // String result = runBashScript("user-manager.sh", "login", adminEmail, adminPassword); // Call bash script to login admin
            // if ("success".equals(result)) {
            //     adminActions(reader); // Call admin actions handler if login is successful
            // } else {
            //     System.out.println("Invalid login credentials."); // Handle invalid login
            // }
            // break;
                    }
    }

    private static void adminActions(BufferedReader reader) throws IOException, InterruptedException {
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

    private static void handlePatientMenu(BufferedReader reader) throws IOException, InterruptedException {
        while (true) { // Loop to keep patient menu running
            // Display Patient menu
            System.out.println("Patient Menu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(reader.readLine()); // Get patient's choice

            switch (choice) { // Switch based on patient's choice
                case 1, 2 -> // Register
                {
                    // Login
                    // System.out.println("Enter your email:");
                    // String patientEmail = reader.readLine(); // Get patient email
                    // System.out.println("Enter your password:");
                    // String patientPassword = reader.readLine(); // Get patient password
                    // String result = runBashScript("user-manager.sh", "login", patientEmail, patientPassword); // Call bash script to login patient
                    // if ("success".equals(result)) {
                    //     patientActions(reader); // Call patient actions handler if login is successful
                    // } else {
                    //     System.out.println("Invalid login credentials."); // Handle invalid login
                    // }

                    System.out.println("Enter your email:");
                    String patientEmail = reader.readLine(); // Get patient email


                    System.out.println("Enter your password:");
                    Console console = System.console();
                    String patientPassword = reader.readLine(); // Get patient password
                    String result = runBashScript("user-manager.sh", "login", patientEmail, patientPassword); // Call bash script to login patient
                    if ("success".equals(result)) {
                        patientActions(reader); // Call patient actions handler if login is successful
                    } else {
                        System.out.println("Invalid login credentials."); // Handle invalid login
                    }

                }
                // case 3 -> {
                //     // Exit
                //     return; // Exit to main menu
                // }
                // default -> System.out.println("Invalid choice, please try again."); // Handle invalid choice
            }
            // Switch based on patient's choice
            // Register
            // System.out.println("Enter the UUID of the user:");
            // String uuid = reader.readLine(); // Get UUID
            // if (!isValidUUID(uuid)) { // Validate UUID format
            //     System.out.println("Invalid UUID. Please try again.");
            //     break;
            // }
            // String email = runBashScript("user-manager.sh", "verify-uuid", uuid); // Call bash script to verify UUID
            // if (email.isEmpty()) {
            //     System.out.println("Invalid UUID."); // Handle invalid UUID
            // } else {
            //     // Proceed with patient registration
            //     System.out.println("Enter First Name:");
            //     String firstName = reader.readLine(); // Get first name
            //     System.out.println("Enter Last Name:");
            //     String lastName = reader.readLine(); // Get last name
            //     System.out.println("Enter Date of Birth (YYYY-MM-DD):");
            //     String dateBirth = reader.readLine(); // Get date of birth
            //     if (!isValidDate(dateBirth)) { // Validate date format
            //         System.out.println("Invalid date format. Please try again.");
            //         break;
            //     }
            //     System.out.println("HIV Status (true/false):");
            //     String statusHiv = reader.readLine(); // Get HIV status
            //     System.out.println("Date of Diagnosis (YYYY-MM-DD):");
            //     String dateDiagnosis = reader.readLine(); // Get date of diagnosis
            //     if (!isValidDate(dateDiagnosis)) { // Validate date format
            //         System.out.println("Invalid date format. Please try again.");
            //         break;
            //     }
            //     System.out.println("ART Status (true/false):");
            //     String statusArt = reader.readLine(); // Get ART status
            //     System.out.println("Date of ART (YYYY-MM-DD):");
            //     String dateArt = reader.readLine(); // Get date of ART
            //     if (!isValidDate(dateArt)) { // Validate date format
            //         System.out.println("Invalid date format. Please try again.");
            //         break;
            //     }
            //     // Validate date order
            //     if (!isDateOrderValid(dateBirth, dateDiagnosis, dateArt)) {
            //         System.out.println("Invalid date order. Ensure date of birth is before diagnosis and ART dates.");
            //         break;
            //     }
            //     System.out.println("Country ISO Code:");
            //     String countryISO = reader.readLine(); // Get country ISO code
            //     System.out.println("Enter Password:");
            //     String password = reader.readLine(); // Get password
            //     runBashScript("user-manager.sh", "register", firstName, lastName, email, uuid, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); // Call bash script to register patient
            //     System.out.println("Patient registered successfully.");
            // }
            // break;
                    }
    }

    private static void patientActions(BufferedReader reader) throws IOException, InterruptedException {
        while (true) { // Loop to keep patient actions menu running
            System.out.println("Patient Actions Menu:");
            System.out.println("1. Update Profile");
            System.out.println("2. View Profile");
            System.out.println("3. Download iCalendar");
            System.out.println("4. Logout");
            int choice = Integer.parseInt(reader.readLine()); // Get patient's choice

            switch (choice) { // Switch based on patient's choice
                case 1 -> {
                    // Update Profile
                    System.out.println("Updating profile...");
                    // Call bash script to update profile
                    System.out.println("Profile updated.");
                }
                case 2 -> {
                    // View Profile
                    System.out.println("Viewing profile...");
                    // Call bash script to view profile
                    System.out.println("Profile viewed.");
                }
                case 3 -> {
                    // Compute Lifespan (to be implemented later)
                    System.out.println("Computing lifespan...");
                    // Call bash script to compute lifespan
                    System.out.println("Lifespan computed.");
                }
                case 4 -> {
                    // Download iCalendar (to be implemented later)
                    System.out.println("Downloading iCalendar...");
                    // Call bash script to download iCalendar
                    System.out.println("iCalendar downloaded.");
                }
                case 5 -> {
                    // Logout
                    return; // Logout and return to patient menu
                }
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid choice
            }
            // Switch based on patient's choice
                    }
    }

    // Helper method to validate email format
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Helper method to validate UUID format
    private static boolean isValidUUID(String uuid) {
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(uuid);
        return matcher.matches();
    }

    // Helper method to validate date format (YYYY-MM-DD)
    private static boolean isValidDate(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    // Helper method to validate date order
    private static boolean isDateOrderValid(String dateBirth, String dateDiagnosis, String dateArt) {
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
