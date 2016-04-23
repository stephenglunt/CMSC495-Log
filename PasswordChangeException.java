/*
 * CMSC 194
 */
package Log;

/**
 * Eexception thrown if there is an error in changing password.
 * @author Stephen
 */
class PasswordChangeException extends Exception {
    public String title;
    public String message;

    /**
     * Constructor
     * @param ttl
     * @param msg 
     */
    public PasswordChangeException(String ttl, String msg) {
        title = ttl;
        message = msg;
    }
    
}
