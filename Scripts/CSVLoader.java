package cz.peta;

import java.io.*;
import java.sql.*;


public class CSVLoader {
    private String[] files;
    private Connection conn;

    public CSVLoader(String[] files, Connection conn) {
        this.files = files;
        this.conn = conn;
        loadCSVFiles();
    }

    private void loadCSVFiles() {
        String line = "";
        String csvSplitBy = ",";

        for (String csvFile : files) {
            String tableName = csvFile.substring(0, csvFile.lastIndexOf(".")); // Extract table name from file name
            System.out.println("Loading data from file " + csvFile + " into table " + tableName);

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String headerLine = br.readLine(); // Read header line
                String[] columns = headerLine.split(csvSplitBy); // Extract column names

                
// Prepare SQL query
StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");
for (String column : columns) {
    insertQuery.append(column).append(",");
}
insertQuery.setLength(insertQuery.length() - 1); // Remove last comma
insertQuery.append(") VALUES (");
for (int i = 0; i < columns.length; i++) {
    insertQuery.append("?,");
}
insertQuery.setLength(insertQuery.length() - 1); // Remove last comma
insertQuery.append(") ON CONFLICT DO NOTHING");

PreparedStatement preparedStatement = conn.prepareStatement(insertQuery.toString());


while ((line = br.readLine()) != null) {
    String[] data = line.split(csvSplitBy);
    for (int i = 0; i < data.length; i++) {
        // Convert ' true' and ' false' strings to boolean values
        if (data[i].trim().equalsIgnoreCase("true")) {
            preparedStatement.setBoolean(i + 1, true);
        } else if (data[i].trim().equalsIgnoreCase("false")) {
            preparedStatement.setBoolean(i + 1, false);
        } else {
            preparedStatement.setString(i + 1, data[i]);
        }
    }
    preparedStatement.addBatch();
}

                //int[] affectedRecords = preparedStatement.executeBatch();
                System.out.println("Records inserted successfully from file " + csvFile);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}