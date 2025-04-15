//package hashingAndDocumentation;//comment this out if you do not need a package

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SongProgram {

    // HashMap to store SongRecords with the song's ID as the key
    private HashMap<String, SongRecord> songMap;

    // Constructor initializes the songMap as a new HashMap
    public SongProgram() {
        songMap = new HashMap<>();
    }

    /** 
     * Method to load songs from a CSV file
     * precondition: The CSV file contains appropriate song data.
     * postcondition: The songMap contains all songs from the CSV file.
     * @param filePath the path to the CSV file
     * @throws IOException if an error occurs while reading the file
     * This method reads the CSV file, creates SongRecord 
     *    objects, and stores them in the songMap.
     */
    public void loadSongsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; //line is declared but not initialized as to read the lines of the CSV file
            
            //read in first line and do nothing with it
            br.readLine();
            
            while ((line = br.readLine()) != null) { //when the line is not null, will create a new SongRecord
            	
            	//System.out.println(line);//for testing
                // Create a SongRecord from the line and add it to the map
                SongRecord song = new SongRecord(line); //songRecord constructor takes a line of CSV
                songMap.put(song.getId(), song); //song gets the ID and puts it in the map
            }
            System.out.println("Songs successfully loaded from CSV."); //the song was successfully created
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage()); //otherwise, the file was not read
        }
    }

    /** 
     * Method to retrieve a SongRecord by ID that was recorded in the hashMap
     * precondition: The songMap HashMap is correctly filled with song objects and their IDs.
     * postcondition: The requested song is returned
     * @param id the ID of the song to retrieve
     * @return the song object associated with the given ID, or null if not found
     */
    public SongRecord getSongById(String id) {
        return songMap.get(id);
    }

    /**
     * Method to print all songs (for debugging or display purposes)
     * precondition: songMap HashMap is filled
     * postcondition: All songs present in the HashMap are printed out in the console
    */
    public void printAllSongs() {
        for (SongRecord song : songMap.values()) { //enhanced for loop to iterate through the values of the songMap
            System.out.println(song); //prints out the song of each ID from the map
        }
    }
    
    /**
     * GUI method to search for a song by ID
     * precondition: The songMap HashMap is correctly filled with song objects and their IDs.
     * postcondition: The user can search for a song through a GUI
     */
    public void openSearchGui() {
        // Create the main frame
        JFrame frame = new JFrame("Song Lookup"); //frame is titled Song Lookup
        frame.setSize(400, 200); //size of the frame is 400x200
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //frame will exit on close

        // Create a panel to hold input and button
        JPanel panel = new JPanel(); 
        panel.setLayout(new FlowLayout());

        // Label, Text Field, and Button
        JLabel label = new JLabel("Enter Song ID:");
        JTextField idField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Add label, text field, and button to panel
        panel.add(label);
        panel.add(idField);
        panel.add(searchButton);

        // Result area to display song details
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane);

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                SongRecord song = getSongById(id);
                if (song != null) {
                    resultArea.setText("Song Found:\n" + song.toString());
                } else {
                    resultArea.setText("Song with ID " + id + " not found.");
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Main method to demonstrate functionality through the GUI
     * precondition: The CSV file is appropriately constructed and accessible
     * postcondition: The program loads songs from a CSV file and retrieves a song by ID
     * @param args
     */
    public static void main2(String[] args) {
        SongProgram program = new SongProgram();

        // Load songs from a CSV file
        String filePath = "data.csv";  // replace with actual file path
        program.loadSongsFromCSV(filePath); // calls the loadSongsFromCSV method to load the songs

        // Open GUI for searching songs by ID
        program.openSearchGui();
    }

    /**
     * Main method to demonstrate functionality
     * precondition: The CSV file is appropriately constructed and accessible
     * postcondition: The program loads songs from a CSV file and retrieves a song by ID
     * @param args
     */
    public static void main(String[] args) {
        SongProgram program = new SongProgram();

        // Load songs from a CSV file
        String filePath = "data.csv";  // replace with actual file path
        program.loadSongsFromCSV(filePath); // calls the loadSongsFromCSV method to load the songs

        // Demonstrate retrieving a song by ID
        String testId = "4BJqT0PrAfrxzMOxytFOIz";  // replace with an actual ID from your file
        SongRecord song = program.getSongById(testId); //calls the getSongById method to retrieve the song
        if (song != null) { //if the song is found in the HashMap, print that the song was found
            System.out.println("Retrieved song: " + song);
        } else { //otherwise, print that the song was not found
            System.out.println("Song with ID " + testId + " not found.");
        }

        // Print all songs
        program.printAllSongs(); //used for debugging purposes
    }
}

