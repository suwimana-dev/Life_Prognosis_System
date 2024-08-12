import java.io.BufferedReader;
import java.io.IOException;
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
    public Patient(String firstName, String lastName, String inputEmail, String inputPassword,
                   LocalDate dateBirth, boolean statusHiv, LocalDate dateDiagnosis, boolean statusArt, LocalDate dateArt, String countryISO) {
        super(firstName, lastName, inputEmail, inputPassword, Profile.PATIENT);
        this.dateBirth = dateBirth;
        this.statusHiv = statusHiv;
        this.dateDiagnosis = dateDiagnosis;
        this.statusArt = statusArt;
        this.dateArt = dateArt;
        this.countryISO = countryISO;
    }

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


    @Override
    public void logout() {
        // Implement logout logic for Patient
    }
}
