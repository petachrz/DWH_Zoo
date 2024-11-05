package cz.peta;

import java.sql.*;

 public class App {

    public static void main(String[] args) {

        String[] csvFiles = {"Zvire.csv", "Osetrovatel.csv" , "Datum.csv", "Vysetreni.csv"};
     
        try (Connection conn = DriverManager.getConnection("jdbc:duckdb:dbzoo.db")) {
            
            //createTables(conn);
            
             //Vytvoreni ETL tridy Loader
           // CSVReader csv = new CSVReader(csvFiles, conn);
            CSVLoader csv = new CSVLoader(csvFiles, conn);
           
        } catch (SQLException e) {
            e.printStackTrace();
   }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            String[] sqlStatements = {
                "CREATE TABLE IF NOT EXISTS Zvire (" +
                    "idZvire INT PRIMARY KEY," +
                    "Nazev VARCHAR," +
                    "Druh VARCHAR," +
                    "Rad VARCHAR," +
                    "Pohlavi VARCHAR," +
                    "Narozeni DATE," +
                    "Puvod VARCHAR," +
                    "Umisteni VARCHAR," +
                    "Prijem DATE" +
                    ");",
                "CREATE TABLE IF NOT EXISTS Osetrovatel (" +
                    "idOsetrovatel INT PRIMARY KEY," +
                    "Jmeno VARCHAR," +
                    "Prijmeni VARCHAR," +
                    "Telefon INT," +
                    "Narozeni DATE," +
                    "Specializace VARCHAR," +
                    "Smena VARCHAR," +
                    "Uvazek VARCHAR," +
                    "PomerOd DATE" +
                    ");",
                "CREATE TABLE IF NOT EXISTS Datum (" +
                    "idDatum INT PRIMARY KEY," +
                    "Rok INT," +
                    "Mesic INT," +
                    "Den INT," +
                    "Cas TIME" +
                    ");",
                "CREATE TABLE IF NOT EXISTS Vysetreni (" +
                    "idVysetreni INT PRIMARY KEY," +
                    "Zvire_idZvire INT," +
                    "Datum_idDatum INT," +
                    "Osetrovatel_idOsetrovatel INT," +
                    "Vaha INT," +
                    "ZbyvajiciPotrava INT," +
                    "DoplnenaPotrava INT," +
                    "Onemocneni BOOLEAN," +
                    "FOREIGN KEY (Zvire_idZvire) REFERENCES Zvire(idZvire)," +
                    "FOREIGN KEY (Datum_idDatum) REFERENCES Datum(idDatum)," +
                    "FOREIGN KEY (Osetrovatel_idOsetrovatel) REFERENCES Osetrovatel(idOsetrovatel)" +
                    ");"
            };
    
            for (String sql : sqlStatements) {
                stmt.executeUpdate(sql);
            }
        }
    }
}
