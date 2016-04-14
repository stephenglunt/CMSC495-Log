package Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the UserList class for the program LOG.
 * @author Matt
 */

public class UserList {
	List<User> userbase = new ArrayList<User>();
	
	// Constructor for UserList creates userbase array
	public UserList() throws IOException{
		FileReader fr = new FileReader("Users.txt");
		BufferedReader br = new BufferedReader(fr);;
	    String userline = null;
        //reading user info from file and filling userbase appropriately
        while((userline = br.readLine()) != null) {
            if(userline.length()!=0){
                System.out.println(userline); //TESTING: YOU DON'T WANNA DO THIS IN THE FULL THING but it's a quick and handy reference for our testing purposes
                String u = "";
                String p = "";
                Boolean a = null;
                Boolean f = null;
                
                while(Character.isLetterOrDigit(userline.charAt(0))){ //Retrieving username
                    u = u + userline.charAt(0);
                    userline = userline.substring(1);
                }
                if(Character.isWhitespace(userline.charAt(0))){ //Confirming end of username & moving to password
                    userline = userline.substring(1);
                }
                else{
                    System.out.println("FILE FORMAT ERROR"); //THIS COULD BE A SERIOUS ISSUE WITH THE PROGRAM IF IT OCCURS and should probably throw an error that stops the entire thing
                }
                
                while(Character.isLetterOrDigit(userline.charAt(0))){ //Retrieving password
                    p = p + userline.charAt(0);
                    userline = userline.substring(1);
                }
                if(Character.isWhitespace(userline.charAt(0))){ //Confirming end of password & moving to status flags
                    userline = userline.substring(1);
                }
                else{
                    System.out.println("FILE FORMAT ERROR"); //THIS COULD BE A SERIOUS ISSUE WITH THE PROGRAM IF IT OCCURS and should probably throw an error that stops the entire thing
                }
                
                if(userline.charAt(0)=='a')
                    a = true;
                else if(userline.charAt(0)=='b')
                    a = false;
                else
                    System.out.println("FILE FORMAT ERROR"); //THIS COULD BE A SERIOUS ISSUE WITH THE PROGRAM IF IT OCCURS and should probably throw an error that stops the entire thing
                if(userline.charAt(1)=='f')
                    f = true;
                else if(userline.charAt(1)=='n')
                    f = false;
                else
                    System.out.println("FILE FORMAT ERROR"); //THIS COULD BE A SERIOUS ISSUE WITH THE PROGRAM IF IT OCCURS and should probably throw an error that stops the entire thing
                
                userbase.add(new User(u, p, a, f));
            }
        }
        br.close();
	}
	
	// Checks username & password
	Boolean checkUserCredentials(String username, String password){
		for(int i = 0; i < userbase.size();i++){
			User user = userbase.get(i);
			if(user.userEquals(username) && user.passwordEquals(password)){
				return true;
			}
		}
		return false;
	}
	
	// Returns User object
	public User getUser(String username){
		for(int i = 0; i < userbase.size();i++){
			User user = userbase.get(i);
			if(user.userEquals(username)){
				return user;
			}
		}
		return null;
	}
	
	// Add a User object
	public void addUser(String username, String password, Boolean admin){
		
	}
	
	// Delete a User object
	public void deleteUser(User u){
		
	}
	
	// Changes user password
	public void changePassword(User u){
		
	}
	
	// Changes user status
	public void changeStatus(User u){
		
	}
	
	// Updates Users.txt file
	public void updateUserFile(){
	}
}
