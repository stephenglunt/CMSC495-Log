package log;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    User userObjct = null;
    JFrame frame;
    JTextArea textArea;
    JScrollPane scrollPane = null;
    Checkbox isAdmin;
    JPanel panel = new JPanel();
    JPanel panelBottom = new JPanel();
    JButton loginButton = new JButton ("Login");
    JLabel user = new JLabel("Username");
    JTextField username = new JTextField (15);
    JLabel pass = new JLabel("Password");
    JPasswordField passwordHidden = new JPasswordField (15);
    JTextField password = new JTextField (15);
    JLabel passConfirm = new JLabel("Confirm Password");
    JTextField passwordConfirm = new JTextField (15);
    JButton logoutButton = new JButton ("Logout");
    JButton viewLogEntries = new JButton ("View Log Entries");
    JButton createLogEntry = new JButton ("Create Log Entry");
    JButton deleteAllEntries = new JButton ("Delete All Log Entries");
    JButton accountManagement = new JButton ("Account Management");
    JButton savePassword = new JButton ("Save Password");
    JButton saveEntry = new JButton ("Save Entry");
    JButton addUser = new JButton ("Add User");//
    JButton mainMenu = new JButton("Main Menu");
    JButton viewUsers = new JButton("View Users");
    JButton editUser = new JButton("Edit User");
    UserList userList;
    EntryList entryList;
    
    /**
     * This creates the GUI and userList which is loaded from Users.txt
     */
    public LOG() {
    	try {
            // Compile userbase & entrybase
            userList = new UserList();
            entryList = new EntryList();
            
            // Add fields/buttons to GUI
            panel.setBorder(BorderFactory.createRaisedBevelBorder());
            panelBottom.setBorder(BorderFactory.createRaisedBevelBorder());
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
            deleteAllEntries.setBorder(BorderFactory.createRaisedBevelBorder());
            saveEntry.setBorder(BorderFactory.createRaisedBevelBorder());
            saveEntry.setBackground(Color.GREEN);
            panel.add(user);
            panel.add (username);
            panel.add(pass);
            panel.add (passwordHidden);
            panel.add (loginButton);
            this.add(panel, BorderLayout.PAGE_START);
            this.add(panelBottom, BorderLayout.PAGE_END);
	        
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
                String password = new String(passwordHidden.getPassword());
                if(userList.checkUserCredentials(username.getText(), password)){
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
                	if(scrollPane != null)
                		scrollPane.removeAll();
                    login = false;
                    panel.removeAll();
                    username.setText("");
                    passwordHidden.setText("");
                    panel.add(user);
                    panel.add (username);
                    panel.add(pass);
                    panel.add (passwordHidden);
                    panel.add (loginButton);
                    revalidate();
                    repaint();
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
                        if(userObjct != null){
                        	userList.changePassword(userObjct, passwordConfirm.getText());
                        }else{
                        	userList.changePassword(current, passwordConfirm.getText());
                        }
                        JOptionPane.showMessageDialog(frame, "Password change complete!",
                                "Accepted Password",JOptionPane.WARNING_MESSAGE);
                        
                        // Call main menu again to restore buttons after password change
                        mainMenu();

                    } catch (PasswordChangeException ex) {
                        JOptionPane.showMessageDialog(frame, ex.message, ex.title, WIDTH);
                    }
                }
            });
            
            // Call log entry creation page
            createLogEntry.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    createLogEntry();
                }
            });
            
            // Call view log entries page
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
            
            // Call delete all log page
            deleteAllEntries.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    deleteAllLogEntries();
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
    
    //Delete all log entry window
    protected void deleteAllLogEntries() {
    	//Update GUI
		panel.remove(deleteAllEntries);
    	if(scrollPane != null)
    		scrollPane.removeAll();
    	String[] options = new String[] {"Yes", "No"};
        int selection = JOptionPane.showOptionDialog(null, "This CANNOT be undone. Are you sure?", "Delete All Log Entries",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            null, options, options[0]);
        revalidate();
        repaint();
       if(selection == 0){
    	   entryList.deleteAllLogEntries();
    	   mainMenu();
       }
       else{
    	   mainMenu();
       }
	}

	//Log creation window
    protected void createLogEntry() {
    	//Update GUI
    	if(scrollPane != null){
    		scrollPane.removeAll();
    		this.remove(scrollPane);
    	}
    	textArea = new JTextArea(5, 20);
    	scrollPane = new JScrollPane(textArea);
    	scrollPane.revalidate();
    	this.add(scrollPane,BorderLayout.CENTER);
    	panelBottom.add(saveEntry,BorderLayout.PAGE_END);
        revalidate();
        repaint();
        
     // Call edit user function
        saveEntry.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                entryList.createLogEntry(current.getName(),textArea.getText());
                mainMenu();
            }
        });
	}

	//Log entries window
    protected void viewLogEntries() {
    	if(scrollPane != null){
    		scrollPane.removeAll();
    		this.remove(scrollPane);
    	}
    	panelBottom.removeAll();
		//Build panel of entries
    	JPanel entryPanel = entryList.displayEntries(current.userStatus());
    	//Create text scroll pane
        scrollPane = new JScrollPane (entryPanel);
        scrollPane.revalidate();
    	this.add(scrollPane,BorderLayout.CENTER);
    	validate();
    	repaint();
	}

	/**
     * Brings up the Change Password menu
     * @param listClass
     * @param user 
     */
    protected void changePassword(UserList listClass, User user){
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
    	panelBottom.removeAll();
        panel.removeAll();
    	if(scrollPane != null)
    		scrollPane.removeAll();
        panel.add(addUser);
        panel.add(viewUsers);
        panel.add(editUser);
        panel.add(mainMenu);
        revalidate();
        repaint();
        
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
        userObjct = current;
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
    protected void editUser(final String username){
        userObjct = userList.getUser(username);
        JButton delete = new JButton("Delete User");
        JButton update = new JButton("Update Status");
        JButton chngPswd = new JButton("Change Password");
        isAdmin = new Checkbox("Admin");
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
        panel.add(mainMenu);
        panel.add(user);
        username.setText("");
        panel.add(username);
        panel.add(pass);
        password.setText("");
        panel.add(password);
        //JTextField uName = new JTextField(20);
        isAdmin = new Checkbox("Admin");
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
    	//Clear screen
    	panelBottom.removeAll();
    	if(scrollPane != null){
    		scrollPane.removeAll();
    		this.remove(scrollPane);
    	}
        panel.removeAll();
        
        System.out.println("Welcome, " + current.getName() + "!"); //CURRENTLY JUST TESTING, should probably print elsewhere
        System.out.println("Admin? " + current.userStatus());
        // Switch to main menu
        panel.add(viewLogEntries);
        panel.add(createLogEntry);
        // Add additional option for admin
        if(current.userStatus()){
            panel.add(accountManagement);
            panel.add(deleteAllEntries);
        }
        panel.add(logoutButton);
        revalidate();
        repaint();
    }

	// Main method
    public static void main(String[] args) throws IOException{
        LOG run = new LOG();
    }
}