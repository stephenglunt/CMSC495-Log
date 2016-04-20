package log;

import java.util.Date;
//import java.util.Calendar; NOT NEEDED?

/**
 *
 * @author Chris
 */
public class Entry {
    
    private String user;
    private String text;
    private Date creationTime;
    //private Calendar cal; NOT NEEDED?
    
    public Entry(String username, String logText, Date time){ //MUST TEST
        user = username;
        text = logText;
        creationTime = time;
        
        //cal = Calendar.getInstance(); NO?
        //cal.setTime(creationTime); NO?
        
        System.out.println(creationTime.toString()); //JUST TESTING, DO NOT INCLUDE LINE IN FINAL VERSION
    }
    
    
    public String toString(){ //MUST TEST FOR RETURNING PROPER STRING (for file)
        String ret = new String(user+" "+creationTime.getTime()+"\n"+text);
        return ret; //MUST RETURN PROPER STRING
    }
    
    
    public String view(){// MUST TEST FOR RETURNING PROPER STRING (for program)
        String ret = new String(creationTime.toString()+" - "+user+" - "+text);
        return ret; //MUST RETURN PROPER STRING
    }
    
    
    public void edit(String newText){
        text = newText;
    }
}
