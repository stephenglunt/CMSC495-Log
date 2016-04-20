package log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Checkbox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    UserList userList;
    
    /**
     * This creates the GUI and userList which is loaded from Users.txt
     */
    public LOG() {
    	try {
            // Compile userbase
            userList = new UserList();
            
            // Add fields/buttons to GUI
            panel.setBorder(BorderFactory.createRaisedBevelBorder());
            loginButton.setBorder(BorderFactory.createRaisedBevelBorder());
            logoutButton.setBorder(BorderFactory.createRaisedBevelBorder());
            viewLogEntries.setBorder(BorderFactory.createRaisedBevelBorder());
            createLogEntry.setBorder(BorderFactory.createRaisedBevelBorder());
            accountManagement.setBorder(BorderFactory.createRaisedBevelBorder());
            savePassword.setBorder(BorderFactory.createRaisedBevelBorder());
            addUser.setBorder(BorderFactory.createRaisedBevelBorder());
            mainMenu.setBorder(BorderFactory.createRaisedBevelBorder());
            viewUsers.setBorder(BorderFactory.createRaisedBevelBorder());
            editUser.setBorder(BorderFactory.createRaisedBevelBorder());
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
            
            // Call log entry page
            viewLogEntries.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    viewLogEntries();
                }
            });
            
            // Call account management page
            accountManagement.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    accountManage();
                    
                }
            });
            
            // Call main menu page
            mainMenu.addActionListener(new ActionListener(){

                public void actionPerformed (ActionEvent e){
                    mainMenu();
                }
            });
            
            // Call add user function
            addUser.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    addUser();
                }
            });
            
         // Call edit user function
            editUser.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    findUser();
                }
            });
	        
	      //Catch file read error
    	} catch (IOException | FileFormatException e) {
            JOptionPane.showMessageDialog(null, "Error During File Read", "Error During File Read", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    //Log entries window
    protected void viewLogEntries() {
    	try{
    		//Compile entries
    		EntryList entryList = new EntryList();
    		//Build panel of entries
	    	JPanel entryPanel = entryList.displayEntries(current.userStatus());
	    	//Create text scroll pane
	        JScrollPane scrollPane = new JScrollPane (entryPanel);
	    	this.add(scrollPane,BorderLayout.CENTER);
	    	validate();
	    	repaint();
    	} catch (IOException | FileFormatException e) {
            JOptionPane.showMessageDialog(null, "Error During File Read", "Error During File Read", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}

	/**
     * Brings up the Change Password menu
     * @param listClass
     * @param current 
     */
    protected void changePassword(UserList listClass, User current){
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
    
    // Find user method
    protected void findUser(){
        JButton findUser = new JButton("Search");
        findUser.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent e){
                if(userList.getUser(username.getText()) == null){
                    JOptionPane.showMessageDialog(frame, "User not found.");
                }
                else if(username.getText().equalsIgnoreCase(current.getName())){
                    JOptionPane.showMessageDialog(frame, "Cannot edit own account.");
                }
                else{
                    editUser(username.getText());
                }
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent e){
                accountManage();
            }
        });
        User userObjct = current;
        panel.removeAll();
        panel.add(user);
        username.setText("");
        panel.add(username);
        panel.add(findUser);
        panel.add(cancel);
        panel.revalidate();
        panel.repaint();
        
    }
    
    // Edit user method
    protected void editUser(String username){
        User userObjct = userList.getUser(username);
        JButton delete = new JButton("Delete User");
        JButton update = new JButton("Update Status");
        JButton chngPswd = new JButton("Change Password");
        Checkbox isAdmin = new Checkbox("Admin");
        isAdmin.setState(userObjct.userStatus());
        
        // Delete user listener
        delete.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                userList.deleteUser(username);
                accountManage();
            }
        });
        
        // Status change listener
        update.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                userList.setStatus(username, isAdmin.getState());
                accountManage();
            }
        });
        
        // Password change listener
        chngPswd.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                changePassword(userList, userObjct);
                accountManage();
            }
        });
        
        panel.removeAll();
        panel.add(new JLabel(username));
        panel.add(isAdmin);
        panel.add(delete);
        panel.add(update);
        panel.add(chngPswd);
        panel.revalidate();
        panel.repaint();
        
        
    }
    
    /**
     * Adds user
     */
    protected void addUser(){
        JButton submit = new JButton("Submit");
        panel.removeAll();
        panel.add(user);
        username.setText("");
        panel.add(username);
        panel.add(pass);
        password.setText("");
        panel.add(password);
        //JTextField uName = new JTextField(20);
        Checkbox isAdmin = new Checkbox("Admin");
        panel.add(new JLabel("Admin"));
        panel.add(isAdmin);
        panel.add(submit);
        
        // Submit button listener
        submit.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                try {
                    userList.addUser(username.getText(), password.getText(), isAdmin.getState());
                    accountManage();
                } catch (DuplicateUserException ex) {
                    JOptionPane.showMessageDialog(frame, ex.message, "Username is taken", WIDTH);
                }
                
            }
        });
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