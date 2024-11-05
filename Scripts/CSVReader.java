package cz.peta;

import java.io.*;
import java.sql.*;

public class CSVReader {
    private String[] files;
    private Connection conn;

    public CSVReader(String[] files, Connection conn) {
        this.files = files;
        this.conn = conn;
        readCSVFiles(); 
        
    }

    private void readCSVFiles() {
        String line = "";
        String csvSplitBy = ",";

        for (String csvFile : files) {
            System.out.println("Obsah souboru " + csvFile + ":");

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                while ((line = br.readLine()) != null) {
                    
                    String[] data = line.split(csvSplitBy);
                    
                    for (String value : data) {
                        System.out.print(value + " ");
                    }
                    System.out.println(); 
                }
                System.out.println(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
