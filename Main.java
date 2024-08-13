
import java.io.BufferedReader; // Importing BufferedReader to read input from the user
import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException; // Importing InputStreamReader to read input from the user
import java.io.InputStreamReader; // Importing IOException to handle IO exceptions
 
public class Main {

    // Method to run the bash script with specified arguments and return the output as a String
    

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
                        String email = System.console().readLine(); // Get admin email
                        char[] passwordArray = System.console().readPassword("Enter your password:");
                        String password = new String(passwordArray);
                        UserService userService = new UserService();
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
                            if (console == null) {
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
                    default ->
                        System.out.println("Invalid choice, please try again."); // Handle invalid choice
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
                default ->
                    System.out.println("Invalid choice, please try again."); // Handle invalid choice
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
                default ->
                    System.out.println("Invalid choice, please try again."); // Handle invalid choice
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
                    //Console console = System.console();
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
            // Display the Patient Actions Menu in an aesthetically pleasing format
            System.out.println("========================================");
            System.out.println("          PATIENT ACTIONS MENU          ");
            System.out.println("========================================");
            System.out.println("1. Update Profile");
            System.out.println("2. View Profile");
            System.out.println("3. Computer Lifespan");
            System.out.println("4. Download iCalendar");
            System.out.println("5. Logout");
            System.out.println("========================================");
            System.out.print("Please select an option: ");

            int choice = Integer.parseInt(reader.readLine()); // Get patient's choice

            switch (choice) { // Switch based on patient's choice
                case 1 -> {
                    try {
                        // Import FileInputStream to handle file reading
                        // Read the email of the logged-in user from session.txt
                        String emailFilePath = "session.txt";
                        BufferedReader emailBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(emailFilePath)));
                        String email = emailBufferedReader.readLine().trim();
                        emailBufferedReader.close();

                        if (email == null || email.isEmpty()) {
                            System.out.println("No email found. Please login first.");
                            break; // Exit case if email is not available
                        }

                        System.out.println("\nUpdating profile for: " + email);
                        System.out.println("\n \nLeave blank to keep current values.");

                        // Retrieve current profile information from user-store.txt via Bash script
                        String currentInfo = runBashScript("UpdateProfile.sh", "read", email);
                        String[] fields = currentInfo.split(";");

                        if (fields.length != 12) {
                            System.out.println("Error: Incorrect format in user-store.txt. Please check the file format.");
                            System.out.println("Received data: " + currentInfo);
                            break;
                        }

                        // Prompt for new values with defaults from current profile information
                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

                        // Display current profile details with sensitive information hidden
                        System.out.println("\nCurrent profile details:\n");
                        System.out.println("========================================");
                        System.out.println("Email               : " + fields[0]);
                        System.out.println("Password            : *******"); // Hide the password
                        System.out.println("Profile             : " + fields[2]);
                        System.out.println("First Name          : " + fields[3]);
                        System.out.println("Last Name           : " + fields[4]);
                        System.out.println("UUID                : " + fields[5]);
                        System.out.println("Date of Birth       : " + fields[6]);
                        System.out.println("HIV Status          : " + fields[7]);
                        System.out.println("Date of Diagnosis   : " + fields[8]);
                        System.out.println("ART Status          : " + fields[9]);
                        System.out.println("Date of ART         : " + fields[10]);
                        System.out.println("Country ISO Code    : " + fields[11]);
                        System.out.println("========================================");

                        // Collect new values from the user
                        System.out.print("\nNew Email (leave blank to keep current): ");
                        String newEmail = inputReader.readLine().trim();
                        newEmail = newEmail.isEmpty() ? fields[0] : newEmail;

                        System.out.print("Enter Old Password to Change: ");
                        String oldPassword = inputReader.readLine().trim();
                        if (!oldPassword.equals(fields[1])) {
                            System.out.println("Incorrect old password. Aborting.");
                            break;
                        }

                        System.out.print("New Password (leave blank to keep current): ");
                        String newPassword = inputReader.readLine().trim();
                        newPassword = newPassword.isEmpty() ? fields[1] : newPassword;

                        System.out.print("New Profile Type (e.g., PATIENT, leave blank to keep current): ");
                        String newProfile = inputReader.readLine().trim();
                        newProfile = newProfile.isEmpty() ? fields[2] : newProfile;

                        System.out.print("New First Name (leave blank to keep current): ");
                        String firstName = inputReader.readLine().trim();
                        firstName = firstName.isEmpty() ? fields[3] : firstName;

                        System.out.print("New Last Name (leave blank to keep current): ");
                        String lastName = inputReader.readLine().trim();
                        lastName = lastName.isEmpty() ? fields[4] : lastName;

                        System.out.print("New UUID (leave blank to keep current): ");
                        String uuid = inputReader.readLine().trim();
                        uuid = uuid.isEmpty() ? fields[5] : uuid;

                        System.out.print("New Date of Birth (YYYY-MM-DD, leave blank to keep current): ");
                        String dateBirth = inputReader.readLine().trim();
                        dateBirth = dateBirth.isEmpty() ? fields[6] : dateBirth;

                        System.out.print("New HIV Status (true/false, leave blank to keep current): ");
                        String statusHiv = inputReader.readLine().trim();
                        statusHiv = statusHiv.isEmpty() ? fields[7] : statusHiv;

                        System.out.print("New Date of Diagnosis (YYYY-MM-DD, leave blank to keep current): ");
                        String dateDiagnosis = inputReader.readLine().trim();
                        dateDiagnosis = dateDiagnosis.isEmpty() ? fields[8] : dateDiagnosis;

                        System.out.print("New ART Status (true/false, leave blank to keep current): ");
                        String statusArt = inputReader.readLine().trim();
                        statusArt = statusArt.isEmpty() ? fields[9] : statusArt;

                        System.out.print("New Date of ART (YYYY-MM-DD, leave blank to keep current): ");
                        String dateArt = inputReader.readLine().trim();
                        dateArt = dateArt.isEmpty() ? fields[10] : dateArt;

                        System.out.print("New Country ISO Code (leave blank to keep current): ");
                        String countryISO = inputReader.readLine().trim();
                        countryISO = countryISO.isEmpty() ? fields[11] : countryISO;

                        // Confirm changes before updating
                        System.out.println("========================================");
                        System.out.println("\nUpdated information");
                        System.out.println("New Email          : " + newEmail);
                        System.out.println("New Password       : " + (newPassword.isEmpty() ? "No change" : "*****")); // Hide the new password
                        System.out.println("New Profile        : " + newProfile);
                        System.out.println("New First Name     : " + firstName);
                        System.out.println("New Last Name      : " + lastName);
                        System.out.println("New UUID           : " + uuid);
                        System.out.println("New Date of Birth  : " + dateBirth);
                        System.out.println("New HIV Status     : " + statusHiv);
                        System.out.println("New Date of Diagnosis: " + dateDiagnosis);
                        System.out.println("New ART Status     : " + statusArt);
                        System.out.println("New Date of ART    : " + dateArt);
                        System.out.println("New Country ISO Code: " + countryISO);
                        System.out.print("\n\nConfirm changes? (yes/no): ");
                        String confirm = inputReader.readLine().trim();

                        if (confirm.equalsIgnoreCase("yes")) {
                            // Call the bash script to update the profile
                            String result = runBashScript("UpdateProfile.sh", "write", newEmail, newPassword, newProfile, firstName, lastName, uuid,
                                    dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO);
                            System.out.println(result);
                        } else {
                            System.out.println("Profile update canceled.");
                        }
                    } catch (IOException e) {
                        System.err.println("An error occurred while updating the profile:");
                        e.printStackTrace();
                    }
                }

                case 2 -> {
                    try {
                        // Call the bash script to retrieve the user's profile line
                        String userInfo = runBashScript("ViewProfile.sh").trim();

                        if (userInfo.isEmpty()) {
                            System.out.println("User not found.");
                        } else {
                            // Split the retrieved line into the corresponding fields
                            String[] userArray = userInfo.split(";");

                            // Ensure that the array matches the user-store.txt format
                            if (userArray.length == 12) {
                                System.out.println("==========================================================");
                                System.out.println("               USER PROFILE             ");
                                System.out.println("==========================================================");
                                System.out.printf("%-20s: %s%n", "Email", userArray[0]);
                                System.out.printf("%-20s: %s%n", "Password", userArray[1]);
                                System.out.printf("%-20s: %s%n", "Profile Type", userArray[2]);
                                System.out.printf("%-20s: %s%n", "First Name", userArray[3]);
                                System.out.printf("%-20s: %s%n", "Last Name", userArray[4]);
                                System.out.printf("%-20s: %s%n", "UUID", userArray[5]);
                                System.out.printf("%-20s: %s%n", "Date of Birth", userArray[6]);
                                System.out.printf("%-20s: %s%n", "HIV Status", userArray[7]);
                                System.out.printf("%-20s: %s%n", "Date of Diagnosis", userArray[8]);
                                System.out.printf("%-20s: %s%n", "ART Status", userArray[9]);
                                System.out.printf("%-20s: %s%n", "Date of ART", userArray[10]);
                                System.out.printf("%-20s: %s%n", "Country ISO", userArray[11]);
                                System.out.println("=========================================================");
                            } else {
                                System.out.println("Error: Incorrect format in user-store.txt. Please check the file format.");
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                case 3 -> {
                    // Compute Lifespan (to be implemented later)
                    System.out.println("Computing lifespan...");
                    // Call bash script to compute lifespan
                    System.out.println("Enter your email:");
                    String checkmail = reader.readLine(); // Get patient email
                    // System.out.println("Enter your password:");
                    // String patientPassword = reader.readLine(); // Get patient password
                    String lsp = runBashScript("lifespancompute.sh", checkmail);
                    System.out.println("Lifespan computation result: " + lsp);
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
                default ->
                    System.out.println("Invalid choice, please try again."); // Handle invalid choice
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
