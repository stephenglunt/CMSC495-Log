/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Log;

/**
 *
 * @author Stephen
 */
class PasswordChangeException extends Exception {
    public String title;
    public String message;

    public PasswordChangeException(String ttl, String msg) {
        title = ttl;
        message = msg;
    }
    
}
