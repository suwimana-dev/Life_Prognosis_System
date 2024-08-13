import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        super(inputEmail, "", "", "", Profile.PATIENT);
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Reader to get user input

    UserService userService = new UserService();

    @Override
    public void displayMenu()  {
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
                        viewProfile();
                    }   catch (IOException | InterruptedException ex) {
                
                    }
                }
                case 2 -> {
                    // View Profile
                    try {
                        updateProfile();
                    }   catch (IOException | InterruptedException ex) {
                
                    }
                }
                case 3 -> {
                    // Compute Lifespan (to be implemented later)
                    try {
                        lifespanCompute();
                    }   catch (IOException | InterruptedException ex) {
                
                    }
                }
                case 4 -> {
                    // Download iCalendar (to be implemented later)
                    try {
                        downloadIcalendar();
                    }   catch (IOException | InterruptedException ex) {
                
                    }
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

     
    public void viewProfile() throws IOException, InterruptedException {
        // Viewing patient's Profile
        try {
            // Call the bash script to retrieve the user's profile line
            String userInfo = userService.runBashScript("ViewProfile.sh").trim();

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
             String lsp = userService.runBashScript("lifespancompute.sh", checkmail);
             System.out.println("Lifespan computation result: " + lsp);
         }
     
         public void downloadIcalendar() throws IOException, InterruptedException {
            // Updating patient's Profile
         
        }

    public void registerUser(){
        // registration for user.

                        System.out.println("Enter the UUID of the user:");
                        String uuid = reader.readLine(); // Get UUID

                        if (!isValidUUID(uuid)) { // Validate UUID format
                            System.out.println("Invalid UUID. Please try again.");
                            break;
                        }
                        //String email = runBashScript("user-manager.sh", "verify-uuid", uuid); // Call bash script to verify UUID
                        //Call bash script to verify UUID, returns email
                        String email = (UserService.runBashScript("verifyuuid.sh", uuid));

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
                            userService.runBashScript("user-manager.sh", "register", firstName, lastName, email, uuid, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO);
                            //runBashScript("user-manager.sh", "register", firstName, lastName, password, dateBirth, statusHiv, dateDiagnosis, statusArt, dateArt, countryISO); 
                            System.out.println("Patient registered successfully.");
                        }
    }
}


