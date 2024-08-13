import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @Override
    public void displayMenu()  {
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
}