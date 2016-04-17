package log;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is the User class for the program LOG.
 * @author Stephen
 * Changes by Chris & Matt
 */

public class LOG extends JFrame{
    private static final long serialVersionUID = 1L;
    boolean login = false;
    User current = null;
    JFrame frame;
    JPanel panel = new JPanel();
    JButton loginButton = new JButton ("Login");
    JLabel user = new JLabel("Username");
    JTextField username = new JTextField (15);
    JLabel pass = new JLabel("Password");
    JTextField password = new JTextField (15);
    JLabel passConfirm = new JLabel("Confirm Password");
    JTextField passwordConfirm = new JTextField (15);
    JButton logoutButton = new JButton ("Logout");
    JButton viewLogEntries = new JButton ("View Log Entries");
    JButton createLogEntry = new JButton ("Create Log Entry");
    JButton accountManagement = new JButton ("Account Management");
    JButton savePassword = new JButton ("Save Password");
    JButton addUser = new JButton ("Add User");//
    JButton mainMenu = new JButton("Main Menu");
    JButton viewUsers = new JButton("View Users");
    JButton editUser = new JButton("Edit User");
    
    /**
     * This creates the GUI and userList which is loaded from Users.txt
     */
    public LOG() {
    	try {
            // Compile userbase
            UserList userList = new UserList();
            // Add fields/buttons to GUI
            panel.add(user);
	    panel.add (username);
	    panel.add(pass);
	    panel.add (password);
	    panel.add (loginButton);
	    this.add(panel, BorderLayout.PAGE_START);
	        
            //Create GUI
            setTitle ("LOG");
            setSize (800, 600);
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            validate();
	    
            //Add listeners to buttons
            //Login listener
	    loginButton.addActionListener (new ActionListener () {
                @Override
	        public void actionPerformed (ActionEvent e) {
                // Check if login is valid
                if(userList.checkUserCredentials(username.getText(), password.getText())){
                    current = userList.getUser(username.getText());
                    login = true;
                    mainMenu();

                            // Check if password needs changed
                    if (current.passwordChangeNeeded()){
                        JOptionPane.showMessageDialog(frame, "Password must be changed!",
                            "First Time Login Notification",JOptionPane.WARNING_MESSAGE);
                        changePassword(userList, current);
                    }
                }
                // Invalid login
                else
                    JOptionPane.showMessageDialog(frame, "Incorrect Username/Password Combination!",
                            "Incorrect Credentials",JOptionPane.WARNING_MESSAGE);
                }
             });
	        
	    // Listener for logout
            logoutButton.addActionListener (new ActionListener () {
                @Override
                public void actionPerformed (ActionEvent e) {
                    login = false;
                    panel.removeAll();
                    username.setText("");
                    password.setText("");
                    panel.add(user);
                    panel.add (username);
                    panel.add(pass);
                    panel.add (password);
                    panel.add (loginButton);
                    panel.revalidate();
                    panel.repaint();
                }
            });
                
            //Listener for password change confirmation
            savePassword.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    try {
                        if(!(password.getText().equals(passwordConfirm.getText()))) //checking for password match
                            throw new PasswordChangeException("Password Mismatch", "Passwords must match.");
                        if(password.getText().contains(" "))
                            throw new PasswordChangeException("Invalid Password", "Passwords cannot contain spaces.");
                        if(password.getText().length()==0)
                            throw new PasswordChangeException("No Password", "No password entered.");
                        
                        userList.changePassword(current, passwordConfirm.getText());
                        JOptionPane.showMessageDialog(frame, "Password change complete!",
                                "Accepted Password",JOptionPane.WARNING_MESSAGE);
                        
                        // Call main menu again to restore buttons after password change
                        mainMenu();

                    } catch (PasswordChangeException ex) {
                        JOptionPane.showMessageDialog(frame, ex.message, ex.title, WIDTH);
                    }
                }
            });
            
            accountManagement.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    accountManage();
                    
                }
            });
            
            mainMenu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    mainMenu();
                }
            });
	        
	      //Catch file read error
    	} catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error During File Read", "Error During File Read", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Brings up the Change Password menu
     * @param listClass
     * @param current 
     */
    protected void changePassword(UserList listClass, User current) {
    	// Remove main menu options/add password change boxes
    	panel.removeAll();
    	password.setText("");
    	passwordConfirm.setText("");
    	panel.add(pass);
    	panel.add(password);
    	panel.add(passConfirm);
    	panel.add(passwordConfirm);
        panel.add(savePassword);
    	panel.revalidate();
	panel.repaint();
		
        //DO NOT CHANGE PASSWORD IMMEDIATELY WHEN CALLING THIS METHOD. Must call and check for password change through button press
		
    }
    
    /**
     * Launches the Account Management interface - still needs work.
     */
    protected void accountManage(){
        panel.removeAll();
        panel.add(addUser);
        panel.add(viewUsers);
        panel.add(editUser);
        panel.add(mainMenu);
        
        panel.revalidate();
        panel.repaint();
        
    }

    // Main menu method
    protected void mainMenu(){
        // Remove login buttons
        panel.removeAll();
        System.out.println("Welcome, " + current.getName() + "!"); //CURRENTLY JUST TESTING, should probably print elsewhere
        System.out.println("Admin? " + current.userStatus());
        // Switch to main menu
        panel.add(viewLogEntries);
        panel.add(createLogEntry);
        // Add additional option for admin
        if(current.userStatus()){
            panel.add(accountManagement);
        }
        panel.add(logoutButton);
        panel.revalidate();
        panel.repaint();
    }

    // Main method
    public static void main(String[] args) throws IOException{
        LOG run = new LOG();
    }
    
}
