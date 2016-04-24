package log;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
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
    Date today = new Date();
    
    /**
     * Constructor
     * @throws IOException
     * @throws FileFormatException 
     */
    public EntryList() throws IOException, FileFormatException{ //LOG BASE HAS BEEN BUILT BUT MUST BE TESTED THOROUGHLY!
        
        String file = readFile("Logs.txt", Charset.forName("UTF-8"));
        System.out.println(file.length());
        if(file.length()<2){
            System.out.println("Log is Empty");
        }
        
        else{
            String[] entries = file.split("/;E/;\n");
            for(int i = 0; i < entries.length; i++){
                System.out.println(entries[i]);                                   //For testing purposes only
                String[] words = entries[i].split("/;e;/");
                if(words.length > 3)
                    throw new FileFormatException("File has become corrupt.");

                long dat = Long.parseLong(words[1]);
                logbase.add(new Entry(words[0], words[2], new Date(dat)));
            }
        }
    }
    
    /**
     * Method to read the file
     * @param path
     * @param encoding
     * @return
     * @throws IOException 
     */
    private String readFile(String path, Charset encoding) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
    
    /**
     * Builds panel of entries with associated buttons
     * @param admin
     * @return 
     */
    public JPanel displayEntries(Boolean admin){
    	for(int i = 0; i < logbase.size();i++){
    		entry = logbase.get(i);
    		//Add listeners to each button
    		//View entry listener
    		entry.view.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    viewLogEntry(entry);
                }
            });
    		//Edit listener
    		entry.edit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e){
                    viewLogEntry(entry);
                }
            });
    		//Delete listener
       		entry.delete.addActionListener(new ActionListener(){
                @Override
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
    
    
    /**
     * Add log entry to Logs.txt
     * @param userName
     * @param text 
     */
    public void createLogEntry(String userName, String text){
    	Entry entry = new Entry(userName, text, today);
    	logbase.add(entry);
        try {
            updateLogFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Updating Logs.txt File",
                "Error!",JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Successfully added entry", 
                "Entry List Updated", JOptionPane.WARNING_MESSAGE);
    }
    
    
    public void editLogEntry(Entry entry){//TODO
        
    }
    
    
    public void viewLogEntry(Entry entry){//TODO
        
    }
    
    
    public void deleteLogEntry(Entry entry){//TODO
        
    }
    
    
    /**
     * Clears Logs.txt file
     */
    public void deleteAllLogEntries(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("Logs.txt");
        	writer.print("");
        	writer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File Not Found!", "File Not Found", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
    }
    
    
    /**
     * Updates log file
     * @throws IOException 
     */
    public void updateLogFile()throws IOException{
    	/*FileWriter writer = new FileWriter("Logs.txt", false);
        try (BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(this.toString());
        }*/
        Writer out = new BufferedWriter(new OutputStreamWriter(new 
                FileOutputStream("Logs.txt"), "UTF-8"));
        try {
            out.write(this.toString());
        } finally {
            out.close();
        }
    }
    
    
    /**
     * All entries to string formated for saving to file.
     * @return 
     */
    @Override
    public String toString(){
        String output = "";
        for(Entry e: logbase){
            output += e.toString() + "/;E/;\n";
        }
        return output;
    }
}
