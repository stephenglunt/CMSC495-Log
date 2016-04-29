package log;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author Chris
 * Changes by Matt & Stephen
 */
public class Entry {
    
    private final String user;
    private String text;
    private final Date creationTime;
    JButton view,edit,delete;
    JTextArea entryText;
    
    public Entry(String username, String logText, Date time){
        user = username;
        text = logText;
        creationTime = time;
        
        //Add buttons/text area to each entry
        this.view = new JButton("View Entry");
        this.view.setBorder(BorderFactory.createRaisedBevelBorder());
        this.edit = new JButton("Edit Entry");
        this.edit.setBorder(BorderFactory.createRaisedBevelBorder());
        this.delete = new JButton("Delete Entry");
        this.delete.setBorder(BorderFactory.createRaisedBevelBorder());
        this.entryText = new JTextArea();
        this.entryText.setBorder(BorderFactory.createRaisedBevelBorder());
        this.entryText.setText(firstSentence());
        this.entryText.setEditable(false);
   
        System.out.println(creationTime.toString()); //JUST TESTING, DO NOT INCLUDE LINE IN FINAL VERSION
    }
    
    //Get first sentence of entry
    public String firstSentence(){
    	if(text.length() <= 50)
    		return text + ".....";
    	else
    		return text.substring(0, 50) + ".....";
    }
    
    /**
     * Returns Entry object as String formatted for input into file.
     * @return
     */
    @Override
    public String toString(){
        return (user+"/;e;/"+creationTime.getTime()+"/;e;/"+text);
    }
    
    /**
     * Returns Entry object as String formatted for viewing on screen.
     * @return 
     */	
    public String view(){
        return (creationTime.toString()+" - "+user+"\n" + text);
    }
    
    public String getText(){
        return text;
    }
    
    /**
     * Saves newText as entry data
     * @param newText 
     */
    public void edit(String newText){
        this.entryText.setEditable(true);
        text = newText;
        this.entryText.setText(firstSentence());
        this.entryText.setEditable(false);
    }
}
