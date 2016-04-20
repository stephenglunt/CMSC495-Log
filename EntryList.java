package log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

/**
 *
 * @author Chris
 */
public class EntryList {
    
    List<Entry> logbase = new ArrayList<Entry>();
    
    public EntryList() throws IOException{ //LOG BASE HAS BEEN BUILT BUT MUST BE TESTED THOROUGHLY!
        
        FileReader fr = new FileReader("Logs.txt");
	BufferedReader br = new BufferedReader(fr);;
	String logline = null;
        
        //reading user info from file and filling userbase appropriately
        while((logline = br.readLine()) != null) {
            if(logline.length()!=0){
                System.out.println(logline); //TESTING: YOU DON'T WANNA DO THIS IN THE FULL THING but it's a quick and handy reference for our testing purposes
                String u = "";
                String d = "";
                long dat;
                String t = "";
                
                while(Character.isLetterOrDigit(logline.charAt(0))){ //Retrieving username
                    u = u + logline.charAt(0);
                    logline = logline.substring(1);
                }
                if(Character.isWhitespace(logline.charAt(0))){ //Confirming end of username & moving to date long
                    logline = logline.substring(1);
                }
                else{
                    System.out.println("FILE FORMAT ERROR"); //THIS COULD BE A SERIOUS ISSUE WITH THE PROGRAM IF IT OCCURS and should probably throw an error that stops the entire thing
                }
                
                while(Character.isDigit(logline.charAt(0))){ //Retrieving date long
                    d = d + logline.charAt(0);
                    if(logline.length()>1)
                        logline = logline.substring(1);
                    else
                        break;
                }
                
                if((logline = br.readLine()) != null){
                    System.out.println(logline); //TESTING: YOU DON'T WANNA DO THIS IN THE FULL THING but it's a quick and handy reference for our testing purposes
                    while(logline.length()>0){
                        t = t + logline.charAt(0);
                        if(logline.length()>1)
                            logline = logline.substring(1);
                        else
                            break;
                    }
                }
                
                dat=Long.parseLong(d);
                logbase.add(new Entry(u, t, new Date(dat)));
            }
        }
        br.close();
    }
    
    
    public void displayEntries(Boolean admin){//TODO
        
    }
    
    
    public void createLogEntry(){//TODO
        
    }
    
    
    public void editLogEntry(){//TODO
        
    }
    
    
    public void viewLogEntry(){//TODO
        
    }
    
    
    public void deleteLogEntry(){//TODO
        
    }
    
    
    public void deleteAllLogEntries(){//TODO
        
    }
    
    
    public void updateLogFile(){//TODO
        
    }
    
}
