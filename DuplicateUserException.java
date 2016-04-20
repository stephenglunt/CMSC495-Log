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
class DuplicateUserException extends Exception {
    public String message;

    public DuplicateUserException(String username) {
        message = "User name: " + username + " is already taken.";
    }
}
