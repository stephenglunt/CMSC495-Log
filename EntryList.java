package log;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 * Changes by Matt
 */

public class EntryList {
    
    List<Entry> logbase = new ArrayList<Entry>();
    JPanel panel = new JPanel();
    Entry entry;
    
    public EntryList() throws IOException, FileFormatException{ //LOG BASE HAS BEEN BUILT BUT MUST BE TESTED THOROUGHLY!
        
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
    
    // Builds panel of entries with associated buttons
    public JPanel displayEntries(Boolean admin){
    	for(int i = 0; i < logbase.size();i++){
    		entry = logbase.get(i);
    		//Add listeners to each button
    		//View entry listener
    		entry.view.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    viewLogEntry(entry);
                }
            });
    		//Edit listener
    		entry.edit.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    viewLogEntry(entry);
                }
            });
    		//Delete listener
       		entry.delete.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent e){
                    viewLogEntry(entry);
                }
            });
       		
    	    panel.setBorder(BorderFactory.createRaisedBevelBorder());
    
       		//Add buttons to panel
    		if(admin){
        	    panel.setLayout(new GridLayout(0,4));
    			panel.add(entry.entryText);
    			panel.add(entry.view);
    			panel.add(entry.edit);
    			panel.add(entry.delete);
    		}
    		else{
        	    panel.setLayout(new GridLayout(0,2));
    			panel.add(entry.entryText);
    			panel.add(entry.view);
    		}
    	}
    	return panel;
    }
    
    
    public void createLogEntry(){//TODO
        
    }
    
    
    public void editLogEntry(Entry entry){//TODO
        
    }
    
    
    public void viewLogEntry(Entry entry){//TODO
        
    }
    
    
    public void deleteLogEntry(Entry entry){//TODO
        
    }
    
    
    public void deleteAllLogEntries(){//TODO
        
    }
    
    
    public void updateLogFile(){//TODO
        
    }
    
}
