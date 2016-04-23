/*
 * CMSC495
 */
package Log;

/**
 * This is an exception thrown when a new account is attempted to be created
 * with an already taken user name.
 * @author Stephen
 */
class DuplicateUserException extends Exception {
    public String message;
    
    /**
     * Constructor 
     * @param username 
     */
    public DuplicateUserException(String username) {
        message = "User name: " + username + " is already taken.";
    }
}
