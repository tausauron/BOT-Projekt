package de.bwvaachen.botscheduler.database;

import klassenObjekte.Schueler;

import java.net.URL;
import java.sql.*;
import java.util.List;


public class DBModel {
    private URL pfad = getClass().getResource("test.mv.db");


    public static void main(String[] args) throws Exception {
        DBModel db = new DBModel();
        db.createDbModel();

    }

    public Connection connection() throws ClassNotFoundException, SQLException {
        String dbPfad = pfad.getPath().toString().replaceFirst("/","");

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:" + dbPfad, "sa", "");

        return conn;
    }

    public void createDbModel() throws Exception {
        connection();
        String sqlCreateTblSchueler = "DROP TABLE IF EXISTS Schueler;" +
                "CREATE TABLE Schueler (" +
                "    schuelerID int," +
                "    nachname varchar(50)," +
                "    vorname varchar(50)," +
                "    wunsch1 varchar(50)," +
                "    wunsch2 varchar(50)," +
                "    wunsch3 varchar(50)," +
                "    wunsch4 varchar(50)," +
                "    wunsch5 varchar(50)," +
                "    wunsch6 varchar(50)," +
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
                "    klasse varchar(50)" +
                ");";


        String sqlInsert = "INSERT INTO Persons VALUES (2, 'Gabor', 'Tomas', 'Bla', 'Aachen');";
        String sql = "SELECT * FROM Schueler;";

        Statement statement = connection().createStatement();
        statement.executeUpdate(sqlCreateTblSchueler);
        //statement.executeUpdate(sqlInsert);
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        //System.out.println(resultSet.getString("PersonID"));

        connection().close();
    }

    public void setSchuelerData(List<Schueler> schuelerList) throws SQLException, ClassNotFoundException {
        connection();
        for (int i = 0; i < schuelerList.size(); i++){
            String sqlInsert = "INSERT INTO Schueler VALUES ('"+
                    schuelerList.get(i).getSchuelerID() +"', " +
                    "'"+ schuelerList.get(i).getNachname() + "', " +
                    "'"+ schuelerList.get(i).getVorname() +"', " +
                    "'"+ schuelerList.get(i).getAllWuensche().get(0) +"', " +
                    "'"+ schuelerList.get(i).getAllWuensche().get(1) +"', " +
                    "'"+ schuelerList.get(i).getAllWuensche().get(2) +"', " +
                    "'"+ schuelerList.get(i).getAllWuensche().get(3) +"', " +
                    "'"+ schuelerList.get(i).getAllWuensche().get(4) +"', " +
                    "'"+ schuelerList.get(i).getAllWuensche().get(5) +"', " +
                    "'"+ schuelerList.get(i).getKlasse() +"');";

            Statement statement = connection().createStatement();
            statement.executeUpdate(sqlInsert);

        }
        connection().close();
    }

    public List<Schueler> getSchuelerData() throws SQLException, ClassNotFoundException {
        connection();
        List<Schueler> schuelerList;

        String getRowCount = "SELECT COUNT(schuelerID) FROM Schueler;";

        Statement statement = connection().createStatement();
        ResultSet resultSet = statement.executeQuery(getRowCount);

        //for (int i = 0; resultSet.get; i++)
        //int resultID = resultSet.getInt(0);

        connection().close();

        return null;
    }
}
