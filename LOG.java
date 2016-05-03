package Log;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

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
    JPanel viewEntryPanel = new JPanel();
    JPanel textPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
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
    JButton saveEdit = new JButton ("Save Edit");
    JButton addUser = new JButton ("Add User");//
    JButton mainMenu = new JButton("Main Menu");
    JButton viewUsers = new JButton("View Users");
    JButton editUser = new JButton("Edit User");
    JButton findUser = new JButton("Search");
    JButton cancel = new JButton("Cancel");
    JButton delete = new JButton("Delete User");
    JButton update = new JButton("Update Status");
    JButton chngPswd = new JButton("Change Password");
    JButton submit = new JButton("Submit");
    UserList userList;
    EntryList entryList;
    String editName;
    Entry editing;
    
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
            submit.setBorder(BorderFactory.createRaisedBevelBorder());
            saveEntry.setBorder(BorderFactory.createRaisedBevelBorder());
            saveEntry.setBackground(Color.GREEN);
            saveEdit.setBorder(BorderFactory.createRaisedBevelBorder());
            saveEdit.setBackground(Color.GREEN);
            panel.add(user);
            panel.add (username);
            panel.add(pass);
            panel.add (passwordHidden);
            panel.add (loginButton);
            this.add(panel, BorderLayout.PAGE_START);
            this.add(panelBottom, BorderLayout.PAGE_END);
            this.getRootPane().setDefaultButton(loginButton);
	        
            //Create GUI
            setTitle ("LOG");
            setSize (800, 600);
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            validate();
	    
            /**
             * Add listeners to buttons
             * Login listener
             */        
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
	        
	    
            /**
             * Listener for logout
             */
            logoutButton.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent e) {
                	if(scrollPane != null)
                		scrollPane.removeAll();
                    login = false;
                    panel.removeAll();
                    panelBottom.removeAll();
                    username.setText("");
                    passwordHidden.setText("");
                    panel.add(user);
                    panel.add (username);
                    panel.add(pass);
                    panel.add (passwordHidden);
                    panel.add (loginButton);
                    panel.getRootPane().setDefaultButton(loginButton);
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
            
            // Call log entry creation function
            saveEntry.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                	String data = textArea.getText().trim();
                	  if(!data.equals("")){
                          entryList.createLogEntry(current.getName(),data);
                          mainMenu();
                	  }else{
                		  JOptionPane.showMessageDialog(frame, "Entry text cannot be blank!",
                				  "Blank Log Entry",JOptionPane.WARNING_MESSAGE);
                	  }
                }
            });
            
            //Call log entry edit saving function
            saveEdit.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    String data = textArea.getText().trim();
                    if(!data.equals("")){
                        Date today = new Date();
                        data = data + "\n\nEdited by: " + current.getName() + 
                                " at " + today.toString() + ".";
                        entryList.editLogEntry(editing, data);
                        mainMenu();
                    }
                    else{
                	JOptionPane.showMessageDialog(frame, "Entry text cannot be blank!",
                            "Blank Log Entry",JOptionPane.WARNING_MESSAGE);
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
            
            // Call view users function
            viewUsers.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    viewUsers();
                }
            });
            
         // Call edit user function
            editUser.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    findUser();
                }
            });
            
            findUser.addActionListener(new ActionListener(){ //MOVE
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
        
            cancel.addActionListener(new ActionListener(){ //MOVE
                @Override
                public void actionPerformed (ActionEvent e){
                    accountManage();
                }
            });
            
            // Delete user listener
            delete.addActionListener(new ActionListener(){ //MOVE
                public void actionPerformed (ActionEvent e){
                    userList.deleteUser(editName);
                    accountManage();
                }
            });
        
            // Status change listener
            update.addActionListener(new ActionListener(){ //MOVE
                public void actionPerformed (ActionEvent e){
                    userList.setStatus(editName, isAdmin.getState());
                    accountManage();
                }
            });
        
            // Password change listener
            chngPswd.addActionListener(new ActionListener(){ //MOVE
                public void actionPerformed (ActionEvent e){
                    changePassword(userList, userObjct);
                }
            });
            
            // Submit button listener
            submit.addActionListener(new ActionListener(){ //MOVE
                public void actionPerformed (ActionEvent e){
                    try {
                        userList.addUser(username.getText(), password.getText(), isAdmin.getState());
                        accountManage();
                    } catch (DuplicateUserException ex) {
                        JOptionPane.showMessageDialog(frame, ex.message, "Username is taken", WIDTH);
                    }
                
                }
            });
	        
	      //Catch file read error
    	} catch (IOException | FileFormatException e) {
            JOptionPane.showMessageDialog(null, "Error During File Read", "Error During File Read", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    /**
     * Display list of users and status
     */
    protected void viewUsers() {
		panel.remove(viewUsers);
    	if(scrollPane != null){
    		scrollPane.removeAll();
    		this.remove(scrollPane);
    	}
    	textArea = new JTextArea(5, 20);
    	textArea.setText(userList.viewUsers());
    	textArea.setEditable(false);
    	scrollPane = new JScrollPane(textArea);
    	scrollPane.revalidate();
    	this.add(scrollPane,BorderLayout.CENTER);
    	revalidate();
        repaint();
    }

	
    /**
     * Delete all log entry window
     */
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

	
    /**
     * Log creation window
     */
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
    }
    
    //Log entry editing window
    protected void editLogEntry(Entry e){
        editing = e;
        //Update GUI
    	if(scrollPane != null){
            scrollPane.removeAll();
            this.remove(scrollPane);
    	}
    	textArea = new JTextArea(5, 20);
        textArea.setText(e.getText());
    	scrollPane = new JScrollPane(textArea);
    	scrollPane.revalidate();
    	this.add(scrollPane,BorderLayout.CENTER);
        panelBottom.add(saveEdit,BorderLayout.PAGE_END);
        revalidate();
        repaint();
    }
    
    /**
     * Builds panel of entries with associated buttons
     */
    protected void viewLogEntries() {
    	//Remove GUI elements
    	if(scrollPane != null){
            scrollPane.removeAll();
            this.remove(scrollPane);
    	}
    	panelBottom.removeAll();
    	
        //Build panel of entries
    	viewEntryPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    	viewEntryPanel.setLayout(new BorderLayout());
    	textPanel.setLayout(new GridLayout(0,1));
        textPanel.removeAll();
    	buttonPanel.removeAll();
    	
    	for(int i = 0; i < entryList.logbase.size();i++){
    		Entry entry = entryList.logbase.get(i);
    		//Add listeners to each button
    		//View entry listener
    		entry.view.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    viewLogEntry(entry);
                }
            });
                
                //This makes sure that ActionListeners don't pile up.
                for(ActionListener al: entry.edit.getActionListeners()){
                    entry.edit.removeActionListener(al);
                }
                for(ActionListener al: entry.delete.getActionListeners()){
                    entry.delete.removeActionListener(al);
                }

                //Edit listener
                entry.edit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){//DO THIS TODAY
                    editLogEntry(entry);
                }
            });
    		//Delete listener
       		entry.delete.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    String[] options = new String[] {"Yes", "No"};
                        int selection = JOptionPane.showOptionDialog(null, "This CANNOT be undone. Are you sure?", "Delete Log Entry", //GUHHHH DOING THE SAME THINGIE AS THE OLDER PROBLEM WE HAD WITH BUTTONS
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                        revalidate();
                        repaint();
                        if(selection == 0){
                            entryList.deleteLogEntry(entry);
                            viewLogEntries();
                        }
                        
                }
                
            });
       		
       		//Add buttons to panel
    		if(current.userStatus()){
    			//Create button/text panel & add components
                    entry.entryText.setPreferredSize(new Dimension (450,20));
                    entry.view.setPreferredSize(new Dimension (318/3, 20));
                    entry.edit.setPreferredSize(new Dimension (318/3, 20));
                    entry.delete.setPreferredSize(new Dimension (318/3, 20));
        	    buttonPanel.setLayout(new GridLayout(0,3));
    			buttonPanel.add(entry.view);
    			buttonPanel.add(entry.edit);
    			buttonPanel.add(entry.delete);
    			textPanel.add(entry.entryText);
    			viewEntryPanel.add(textPanel,BorderLayout.CENTER);
    			viewEntryPanel.add(buttonPanel,BorderLayout.LINE_END);
    		}
    		else{
    			//Create button/text panel & add components
                    entry.entryText.setPreferredSize(new Dimension (450,20));
                    entry.view.setPreferredSize(new Dimension (318, 20));
        	    buttonPanel.setLayout(new GridLayout(0,1));
        	    buttonPanel.add(entry.view);
    			textPanel.add(entry.entryText);
    			viewEntryPanel.add(textPanel,BorderLayout.CENTER);
    			viewEntryPanel.add(buttonPanel,BorderLayout.LINE_END);
    		}
    	}

    	//Add container
    	JPanel container = new JPanel(new FlowLayout(FlowLayout.LEADING, 2,2));
        container.add(viewEntryPanel);
    	//Create text scroll pane
        scrollPane = new JScrollPane (container);
        scrollPane.revalidate();
    	this.add(scrollPane,BorderLayout.CENTER);
    	validate();
    	repaint();
    }

    protected void viewLogEntry(Entry entry) {
    	//Remove GUI elements
        scrollPane.removeAll();
        this.remove(scrollPane);
    	panelBottom.removeAll();
    	textArea = new JTextArea(20, 20);
    	textArea.setText(entryList.viewLogEntry(entry));
    	textArea.setEditable(false);
    	scrollPane = new JScrollPane(textArea);
    	scrollPane.revalidate();
    	this.add(scrollPane,BorderLayout.CENTER);
        revalidate();
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
    	if(scrollPane != null){
    		scrollPane.removeAll();
    		this.remove(scrollPane);
    	}
        panel.add(addUser);
        panel.add(viewUsers);
        panel.add(editUser);
        panel.add(mainMenu);
        revalidate();
        repaint();
    }
    
    
    /**
     * Find user method
     */
    protected void findUser(){
    	viewUsers();
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
    
    
    /**
     * Edit user method
     * @param editUsername 
     */
    protected void editUser(final String editUsername){
    	if(scrollPane != null){
            scrollPane.removeAll();
            this.remove(scrollPane);
    	}
        userObjct = userList.getUser(editUsername);
        editName = new String(editUsername);
        
        isAdmin = new Checkbox("Admin");
        isAdmin.setState(userObjct.userStatus());
        
        panel.removeAll();
        panel.add(new JLabel(editUsername));
        panel.add(isAdmin);
        panel.add(delete);
        panel.add(update);
        panel.add(chngPswd);
        revalidate();
        repaint();
    }
    
    /**
     * Adds user
     */
    protected void addUser(){
    	if(scrollPane != null){
            scrollPane.removeAll();
            this.remove(scrollPane);
    	}
        panel.removeAll();
        panel.add(mainMenu);
        panel.add(user);
        username.setText("");
        panel.add(username);
        panel.add(pass);
        password.setText("");
        panel.add(password);
        isAdmin = new Checkbox("Admin");
        panel.add(new JLabel("Admin"));
        panel.add(isAdmin);
        panel.add(submit);
        
        revalidate();
        repaint();
    }

    
    /**
     * Main menu method
     */
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

    
    /**
     * Main method
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException{
        LOG run = new LOG();
    }
}
