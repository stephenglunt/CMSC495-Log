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
    private boolean firstLog;
    
    
    /**
     * Constructor
     * @param userName
     * @param password
     * @param admin
     */
    public User(String userName, String password, boolean admin, boolean fl){
        name = userName;
        this.password = password;
        isAdmin = admin;
        firstLog = fl;
    }
    
    /**
     * Sets the password.  Only allow access to admin users OR first-time users changing password
     * @param newPassword 
     */
    public void setPassword(String newPassword){
        password = newPassword;
        firstLog = false;
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
    
    //Checks for whether the account has been used to log in previously. Used to see if password change is necessary
    public boolean checkFirstLog(){
        return firstLog;
    }
    
    /**
     * Returns username.
     * @return 
     */
    public String getName(){
        return name;
    }
    
}
