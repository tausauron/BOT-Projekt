package de.bwvaachen.botscheduler.database;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.lang.ClassLoader;


public class dbContext {
    public static void main(String[] args) throws Exception {

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:H:/ProjektRepos/BOT-Projekt/src/main/resources/test", "sa", "");

        String sqlCreateTblSchueler = "DROP TABLE IF EXISTS Schueler;" +
                "CREATE TABLE Schueler (" +
                "    schuelerID int," +
                "    nachname varchar(50)," +
                "    vorname varchar(50)," +
                "    wunsch1 int," +
                "    wunsch2 int," +
                "    wunsch3 int," +
                "    wunsch4 int," +
                "    wunsch5 int," +
                "    wunsch6 int," +
                "    klasse varchar(50)" +
                ");";

        String sqlCreateTblUnternehmen = "DROP TABLE IF EXISTS Unternehmen;" +
                "CREATE TABLE Unternehmen (" +
                "    firmenID int," +
                "    unternehmenName varchar(50)," +
                "    fachrichtung varchar(50)," +
                "    maxTeilnehmer int," +
                "    maxVeranstaltungen int," +
                "    fruehsterZeitSlot int," +
                "    gewichtung double," + // double als datentyp näher anschauen was notwending
                "    aktiv boolean," +
                "    wunsch6 int," +
                "    klasse varchar(50)" +
                ");";


        String sqlInsert = "INSERT INTO Persons VALUES (2, 'Gabor', 'Tomas', 'Bla', 'Aachen');";
        String sql = "SELECT * FROM Persons;";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sqlCreate);
        statement.executeUpdate(sqlInsert);
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        System.out.println(resultSet.getString("PersonID"));

        conn.close();
    }
}
