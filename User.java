/*
 * 
 */
package log;

/**
 * This is the User class for the program LOG.
 * @author Stephen
 */
public class User {
    
    private String name;
    private String password;
    private boolean isAdmin;
    
    /**
     * Constructor
     * @param userName
     * @param password
     * @param admin
     */
    public User(String userName, String password, boolean admin){
        name = userName;
        this.password = password;
        isAdmin = admin;
    }
    
    /**
     * Sets the password.  Only allow access to admin users
     * @param newPassword 
     */
    public void setPassword(String newPassword){
        password = newPassword;
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
     * Returns username.
     * @return 
     */
    public String getName(){
        return name;
    }
    
}
