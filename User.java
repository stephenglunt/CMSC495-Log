package log;

/**
 * This is the User class for the program LOG.
 * @author Stephen
 * Changes by Chris & Matt
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
        forcePasswordChange = false; //TEMPORARILY disabling unforcing change for testing purposes
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
    
    /**
     * Returns admin status.
     * @return 
     */
    public Boolean userStatus(){
        return isAdmin;
    }
    
    // Returns User information for writing to Users.txt
    public String toString(){
    	String status = "b";
    	String firstTime = "n";
    	// Check if user is admin
    	if (this.isAdmin){
    		status = "a";
    	}
    	// Check if user needs password reset
    	if (passwordChangeNeeded()){
    		firstTime = "f";
    	}
    	// Example: Mlaclair 123abc af
		String output = name + " " + password + " " + status + firstTime;
		return output;
	}
    
}
