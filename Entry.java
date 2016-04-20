package log;

import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;
//import java.util.Calendar; NOT NEEDED?

/**
 *
 * @author Chris
 * Changes by Matt
 */
public class Entry {
    
    private String user;
    private String text;
    private Date creationTime;
    //private Calendar cal; NOT NEEDED?
    JButton view,edit,delete;
    JTextArea entryText;
    
    public Entry(String username, String logText, Date time){ //MUST TEST
        user = username;
        text = logText;
        creationTime = time;
        
        //cal = Calendar.getInstance(); NO?
        //cal.setTime(creationTime); NO?
        
        //Add buttons/text area to each entry
        this.view = new JButton("View Entry");
        this.view.setBorder(BorderFactory.createRaisedBevelBorder());
        this.edit = new JButton("Edit Entry");
        this.edit.setBorder(BorderFactory.createRaisedBevelBorder());
        this.delete = new JButton("Delete Entry");
        this.delete.setBorder(BorderFactory.createRaisedBevelBorder());
        this.entryText = new JTextArea();
        this.entryText.setBorder(BorderFactory.createRaisedBevelBorder());
        this.entryText.setText(text);
   
        System.out.println(creationTime.toString()); //JUST TESTING, DO NOT INCLUDE LINE IN FINAL VERSION
    }
    
    
    public String toString(){ //MUST TEST FOR RETURNING PROPER STRING (for file)
        return (user+" "+creationTime.getTime()+"\n"+text);//MUST RETURN PROPER STRING
    }
    
    
    public String view(){// MUST TEST FOR RETURNING PROPER STRING (for program)
        return (creationTime.toString()+" - "+user+" - "+text); //MUST RETURN PROPER STRING
    }
    
    
    public void edit(String newText){
        text = newText;
    }
}
