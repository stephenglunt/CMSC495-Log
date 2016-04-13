package log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LOG extends JFrame{

    List<User> userbase = new ArrayList<User>();
    boolean login = false;
    User current = null;
    
    FileReader fr;
    BufferedReader br;
        
    public LOG() throws IOException{
        fr = new FileReader("Users.txt");
        br = new BufferedReader(fr);
        
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
        //TODO: CHECK FOR ALL USERS FROM FILE
        
        
        
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
                User u = null;
                for(int i=0;i<userbase.size();i++){
                    if(userbase.get(i).getName().equals(username.getText())){
                        u=userbase.get(i);
                        if(u.passwordEquals(password.getText())){
                            System.out.println("Welcome, " + u.getName()); //CURRENTLY JUST TESTING, should probably print elsewhere
                        }
                        else
                            System.out.println("ERROR: Credentials do not match"); //CURRENTLY JUST TESTING, should probably print elsewhere
                        break;
                    }
                }
                if(u==null)
                    System.out.println("ERROR: Username not found");//CURRENTLY JUST TESTING, should probably print elsewhere
            }
        });
        
        //Create GUI
        setTitle ("LOG");
        setSize (800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        validate();
        
    }
    
    
    
    public static void main(String[] args) throws IOException{
        LOG run = new LOG();
    }
    
}
