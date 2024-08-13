import java.io.IOException;

/**
 * Abstract User class to define common attributes and methods for all users.
 */

 public abstract class User {
    // Define new data type Profile for user's role
    public enum Profile {
        ADMIN, 
        PATIENT
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

    public void displayMenu () throws NumberFormatException, IOException{
        
    }
}
