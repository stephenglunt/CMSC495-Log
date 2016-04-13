package log;

/**
 * This is the User class for the program LOG.
 * @author Stephen
 * Minor changes by Chris
 */
public class User {
    
    private String name;
    private String password;
    private boolean isAdmin;
    private boolean forcePasswordChange;
    
    
    /**
     * Constructor
     * @param userName
     * @param password
     * @param admin
     * @param change
     */
    public User(String userName, String password, boolean admin, boolean change){
        name = userName;
        this.password = password;
        isAdmin = admin;
        forcePasswordChange = change;
    }
    
    /**
     * Constructor
     * @param userName
     * @param password
     * @param admin
     * @param change
     */
    public User(String userName, String password, boolean admin){
        name = userName;
        this.password = password;
        isAdmin = admin;
        forcePasswordChange = true;
    }
    
    /**
     * Sets the password.  Only allow access to admin users OR first-time users changing password
     * @param newPassword 
     */
    public void setPassword(String newPassword){
        password = newPassword;
        forcePasswordChange = false;
    }
    
    /**
     * Checks if string is equal to password. Used to verify password.
     * @param testPassword
     * @return 
     */
    public boolean passwordEquals(String testPassword){
        return password.contentEquals(testPassword);
    }
    
    /**
     * Checks if string is equal to name (ignores case). Used to ensure no
     * duplicate users.
     * @param name
     * @return 
     */
    public boolean userEquals(String name){
        return this.name.equalsIgnoreCase(name);
    }
    
    /**
     * Checks for whether account password change is necessary
     * @return
     */
    public boolean passwordChangeNeeded(){
        return forcePasswordChange;
    }
    
    /**
     * Returns username.
     * @return 
     */
    public String getName(){
        return name;
    }
    
}
