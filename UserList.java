package Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JOptionPane;

/**
 * This is the UserList class for the program LOG.
 * @author Matt
 * Contributions by Stephen Glunt
 */
public class UserList {
    List<User> userbase = new ArrayList<User>();

    /**
     * Constructor
     * Creates array of User objects from file.
     * @throws IOException 
     */
    public UserList() throws IOException, FileFormatException{
        String filePath = new File("").getAbsolutePath();
        FileReader fr = new FileReader(filePath + "/Users.txt");
        BufferedReader br = new BufferedReader(fr);
        String userline = null;
        //reading user info from file and filling userbase appropriately
        while((userline = br.readLine()) != null) {
            if(userline.length()!=0){
                System.out.println(userline); //TESTING: YOU DON'T WANNA DO THIS IN THE FULL THING but it's a quick and handy reference for our testing purposes
                //Break the userline up using spaces as delimiter
                String[] words = userline.split(" ");

                //There should be 3 groups of characters and the last group
                //should have only two characters in them. If not throw exception
                if((words.length != 3) || (words[2].length() != 2)){
                    throw new FileFormatException(filePath + "/Users.txt");
                }

                String u = words[0];
                String p = words[1];
                Boolean a = null;
                Boolean f = null;

                if(words[2].charAt(0)=='a')
                    a = true;
                else a = false;
                if(words[2].charAt(1)=='f')
                    f = true;
                else f = false;

                userbase.add(new User(u, p, a, f));
            }
        }
        br.close();
    }

    /**
     * This compares the username and password with that of every user in
     * the userlist and returns true if there is a match.  Returns true if 
     * match/false if name and password do not match
     * @param username
     * @param password
     * @return
     */
    Boolean checkUserCredentials(String username, String password){
        for(int i = 0; i < userbase.size();i++){
            if(userbase.get(i).userEquals(username) && userbase.get(i).passwordEquals(password)){
                return true;
            } 
        }
        return false;
    }

    /**
     * This returns a User object that has the same name as the passed
     * parameter.  Returns null if user not found.
     * @param username
     * @return 
     */
    public User getUser(String username){
        for(int i = 0; i < userbase.size();i++){
            User user = userbase.get(i);
            if(user.userEquals(username)){
                    return user;
            }
        }
        return null;
    }

    /**
     * This creates a user object, adds it to the list and saves it to the
     * Users.txt file.
     * @param username
     * @param password
     * @param admin 
     */
    public void addUser(String username, String password, Boolean admin) throws DuplicateUserException{
        User u = new User(username, password, admin);
        for(int i = 0; i < userbase.size(); i++){
            if(userbase.get(i).userEquals(username))
                throw new DuplicateUserException(username);
        }
        userbase.add(u);
        try {
            updateUserFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Updating Users.txt File",
                "Error!",JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Successfully added user", 
                "User List Updated", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This removes the User object that matches the username from the 
     * UserList and updates the Users.txt file.
     * @param u 
     */
    public void deleteUser(String username){
        userbase.remove(getUser(username));
        try {
            updateUserFile();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error Updating Users.txt File",
                "Error!",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Successfully deleted user", 
                "User List Updated", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Changes user password
     * @param u
     * @param newPassword 
     */
    public void changePassword(User u, String newPassword){
        try{
            u.setPassword(newPassword);
            updateUserFile();
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Updating Users.txt File",
                "Error!",JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Changes status i.e. admin or not
     * @param username 
     * @param admin
     */
    public void setStatus(String username, Boolean admin){
        getUser(username).setStatus(admin);
        try {
            updateUserFile();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error Updating Users.txt File",
                "Error!",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }
        String status;
        if(admin)
            status = "admin.";
        else
            status = "basic user.";
        JOptionPane.showMessageDialog(null, "Successfully changed user status to " + status, 
                "User List Updated", JOptionPane.WARNING_MESSAGE);

    }

    /**
     * The copies the entire userbase to the Users.txt file formatted
     * properly for retrieval.  This method will overwrite any data that
     * was in the Users.txt file.
     * @throws java.io.IOException
     */
    public void updateUserFile() throws IOException{
        FileWriter writer = new FileWriter("Users.txt", false);
        try (BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(this.toString());
        }
    }
    
    /**
     * Returns string of all users formatted for output to Users.txt
     * @return 
     */
    @Override
    public String toString(){
        String output = "";
        for(User u: userbase){
            output += u.toString() + "\n";
            output += "\n";
        }
        return output;
    }
}
