package Log;

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
      
    public LOG() {
    	try {
    		UserList userList = new UserList();
	        JButton loginButton = new JButton ("Login");
	        JLabel user = new JLabel("Username");
	        JTextField username = new JTextField (15);
	        JLabel pass = new JLabel("Password");
	        JTextField password = new JTextField (15);
	        
	        JPanel panel1 = new JPanel();
	        panel1.add(user);
	        panel1.add (username);
	        panel1.add(pass);
	        panel1.add (password);
	        panel1.add (loginButton);
	        this.add(panel1);
	        
	        //Add listeners to buttons
	        loginButton.addActionListener ( new ActionListener () {
	            public void actionPerformed (ActionEvent e) {
	            	if(userList.checkUserCredentials(username.getText(), password.getText())){
	            		User u = userList.getUser(username.getText());
	            		System.out.println("Welcome, " + u.getName() + "!"); //CURRENTLY JUST TESTING, should probably print elsewhere
	                    System.out.println("Admin? " + u.userStatus());
	                    if (u.passwordChangeNeeded()){
	                        System.out.println("Password needs changed!");
	                        //Call userList.changePassword(u)
	                    }
	            	}
	            	else
                        System.out.println("ERROR: Credentials do not match"); //CURRENTLY JUST TESTING, should probably print elsewhere
	            }
	        });
        
		    //Create GUI
		    setTitle ("LOG");
		    setSize (800, 600);
		    setVisible(true);
		    setLocationRelativeTo(null);
		    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		    validate();
    	} catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error During File Read", "Error During File Read", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    
    public static void main(String[] args) throws IOException{
        LOG run = new LOG();
    }
    
}
