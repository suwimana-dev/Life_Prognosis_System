/**
 * Abstract User class to define common attributes and methods for all users.
 */

public abstract class User {
    // Define new data type Profile for user's role
    public enum Profile {
        ADMIN, PATIENT
    }
    private String firstName;
    private String lastName;
    private String inputEmail;
    private String inputPassword;
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

    // Getter and Setter methods

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }

    // public Profile getRole() {
    //     return profile;
    // }

    // public void setProfile(Profile profile) {
    //     this.profile = profile;
    // }

    /**
     * Abstract method to be implemented by subclasses for logout functionality.
     */
    public abstract void logout();
}
