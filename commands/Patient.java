
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * Patient class extends User and adds attributes specific to a Patient.
 */
public class Patient extends User {

    public LocalDate dateBirth;
    public boolean statusHiv;
    public LocalDate dateDiagnosis;
    public boolean statusArt;
    public LocalDate dateArt;
    public String countryISO;

    /**
     * Constructor to initialize a Patient object.
     */
    public Patient(String inputEmail) {
        super("", "", inputEmail, "", Profile.PATIENT);
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input

    UserService userService = new UserService();

    @Override
    public void displayMenu() throws NumberFormatException, IOException {
        while (true) { // Loop to keep patient actions menu running
            // Display the Patient Actions Menu in an aesthetically pleasing format
            System.out.println("========================================");
            System.out.println("          PATIENT ACTIONS MENU          ");
            System.out.println("========================================");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Compute Lifespan");
            System.out.println("4. Download iCalendar");
            System.out.println("5. Logout");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            int choice = Integer.parseInt(reader.readLine()); // Get patient's choice
            switch (choice) { // Switch based on patient's choice
                case 1 -> {
                    // Update Profile
                    try {
                        viewProfile(this.inputEmail);
                    } catch (IOException | InterruptedException ex) {

                    }
                }
                case 2 -> {
                    // View Profile
                    try {
                        updateProfile();
                    } catch (IOException | InterruptedException ex) {

                    }
                }
                case 3 -> {
                    // Compute Lifespan (to be implemented later)
                    try {
                        lifespanCompute();
                    } catch (IOException | InterruptedException ex) {

                    }
                }
                case 4 -> {
                    // Download iCalendar (to be implemented later)
                    try {
                        downloadIcalendar();
                    } catch (IOException | InterruptedException ex) {

                    }
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

    public void viewProfile(String email) throws IOException, InterruptedException {
        // Viewing patient's Profile
        try {
            // Call the bash script to retrieve the user's profile line
            String userInfo = userService.runBashScript("../scripts/ViewProfile.sh", email);
           

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

    public void updateProfile() throws IOException, InterruptedException {
        // Updating patient's Profile
        try {
            // Import FileInputStream to handle file reading
            // Read the email of the logged-in user from session.txt
            String emailFilePath = "../data/session.txt";
            BufferedReader emailBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(emailFilePath)));
            String email = emailBufferedReader.readLine().trim();
            emailBufferedReader.close();

            if (email == null || email.isEmpty()) {
                System.out.println("No email found. Please login first.");
            }

            System.out.println("\nUpdating profile for: " + email);
            System.out.println("\n \nLeave blank to keep current values.");

            // Retrieve current profile information from user-store.txt via Bash script
            String currentInfo = userService.runBashScript("../scripts/UpdateProfile.sh", "read", email);
            String[] fields = currentInfo.split(";");

            if (fields.length != 12) {
                System.out.println("Error: Incorrect format in user-store.txt. Please check the file format.");
                System.out.println("Received data: " + currentInfo);
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
                String result = userService.runBashScript("../scripts/UpdateProfile.sh", "write", newEmail, newPassword, newProfile, firstName, lastName, uuid,
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

    public void lifespanCompute() throws IOException, InterruptedException {
        // Computing patient lifespan 
        // Compute Lifespan (to be implemented later)
        System.out.println("Computing lifespan...");
        // Call bash script to compute lifespan
        System.out.println("Enter your email:");
        String checkmail = reader.readLine(); // Get patient email
        // System.out.println("Enter your password:");
        // String patientPassword = reader.readLine(); // Get patient password
        String lsp = userService.runBashScript("../scripts/lifespancompute.sh", checkmail);
        System.out.println("========================================");
        System.out.println("Lifespan computation result, your life expectancy is: " + lsp + " years.");
        System.out.println("========================================");
        
    }

    public void downloadIcalendar() throws IOException, InterruptedException {
        // Updating patient's Profile

    }

    public void registerUser() throws IOException, InterruptedException {
        // registration for user.

        System.out.println("Enter the UUID of the user:");
        String uuid = reader.readLine(); // Get UUID

        if (!userService.isValidUUID(uuid)) { // Validate UUID format
            System.out.println("Invalid UUID. Please try again.");
        }
        //String email = runBashScript("user-manager.sh", "verify-uuid", uuid); // Call bash script to verify UUID
        //Call bash script to verify UUID, returns email
        String email = userService.runBashScript("../scripts/verifyuuid.sh", uuid);

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
            if (!userService.isValidDate(dateBirth)) { // Validate date format
                System.out.println("Invalid date format. Please try again.");
            }
            System.out.println("HIV Status (true/false):");
            String statusHiv = reader.readLine(); // Get HIV status
            String dateDiagnosis = null;
            String statusArt = null;
            String dateArt = null;
            if (statusHiv.equalsIgnoreCase("true")) {
                System.out.println("Date of Diagnosis (YYYY-MM-DD):");
                dateDiagnosis = reader.readLine(); // Get date of diagnosis
                if (!userService.isValidDate(dateDiagnosis)) { // Validate date format
                    System.out.println("Invalid date format. Please try again.");
                }
                System.out.println("ART Status (true/false):");
                statusArt = reader.readLine(); // Get ART status
                System.out.println("Date of ART (YYYY-MM-DD):");
                dateArt = reader.readLine(); // Get date of ART
                if (!userService.isValidDate(dateArt)) { // Validate date format
                    System.out.println("Invalid date format. Please try again.");
                }

                //Validate date order
                if (!userService.isDateOrderValid(dateBirth, dateDiagnosis, dateArt)) {
                    System.out.println("Invalid date order. Ensure date of birth is before diagnosis and ART dates.");
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
            if (console == null) {
                return;
            }
            char[] passwordArray = console.readPassword("Enter password:");
            String apassword = new String(passwordArray);
            String password = apassword;
            //System.out.println("Enter Password:");
            //String password = reader.readLine(); // Get password

            // Call bash script to register patient
            userService.runBashScript("../scripts/user-manager.sh", "register", firstName, lastName, email, uuid, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO);
            //runBashScript("user-manager.sh", "register", firstName, lastName, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); 
            System.out.println("Patient registered successfully.");
        }
    }
}
