package de.bwvaachen.botscheduler.database;

import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBModel {
    private URL pfad = getClass().getResource("test.mv.db");


    public static void main(String[] args) throws Exception {
        DBModel db = new DBModel();
        db.createDbModel();
        db.getSchuelerData();

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
                "    schuelerID int NOT NULL AUTO_INCREMENT," +
                "    nachname varchar(50)," +
                "    vorname varchar(50)," +
                "    wunsch0 varchar(50)," +
                "    wunsch1 varchar(50)," +
                "    wunsch2 varchar(50)," +
                "    wunsch3 varchar(50)," +
                "    wunsch4 varchar(50)," +
                "    wunsch5 varchar(50)," +
                "    klasse varchar(50)" +
                ");";

        String sqlCreateTblUnternehmen = "DROP TABLE IF EXISTS Unternehmen;" +
                "CREATE TABLE Unternehmen (" +
                "    firmenID int," +
                "    unternehmenName varchar(50)," +
                "    fachrichtung varchar(200)," +
                "    maxTeilnehmer int," +
                "    maxVeranstaltungen int," +
                "    fruehsterZeitSlot varchar(100)," +
                "    gewichtung double," + // double als datentyp näher anschauen was notwending
                "    aktiv boolean," +
                "    mapKurse varchar(50)" +
                ");";

        String sqlCreateTblRaum = "DROP TABLE IF EXISTS Raum;" +
                "CREATE TABLE Raum (" +
                "    raumID int NOT NULL AUTO_INCREMENT," +
                "    name varchar(50)," +
                "    kapazitaet int" +
                ");";




        Statement schuelerTblStmnt = connection().createStatement();
        schuelerTblStmnt.executeUpdate(sqlCreateTblSchueler);

        Statement unternehmenTblStmnt = connection().createStatement();
        unternehmenTblStmnt.executeUpdate(sqlCreateTblUnternehmen);

        Statement raumTblStmnt = connection().createStatement();
        raumTblStmnt.executeUpdate(sqlCreateTblRaum);
        //statement.executeUpdate(sqlInsert);
        //System.out.println(resultSet.getString("PersonID"));

        connection().close();
    }


    // Schuüler
    public void setSchuelerData(List<Schueler> schuelerList) throws SQLException, ClassNotFoundException {
        connection();
        for (Schueler schuel : schuelerList){
            String sqlInsert = "INSERT INTO Schueler VALUES ('"+
                    schuel.getSchuelerID() +"', " +
                    "'"+ schuel.getNachname() + "', " +
                    "'"+ schuel.getVorname() +"', " +
                    "'"+ schuel.getAllWuensche().get(0) +"', " +
                    "'"+ schuel.getAllWuensche().get(1) +"', " +
                    "'"+ schuel.getAllWuensche().get(2) +"', " +
                    "'"+ schuel.getAllWuensche().get(3) +"', " +
                    "'"+ schuel.getAllWuensche().get(4) +"', " +
                    "'"+ schuel.getAllWuensche().get(5) +"', " +
                    "'"+ schuel.getKlasse() +"');";

            Statement statement = connection().createStatement();
            statement.executeUpdate(sqlInsert);

        }
        connection().close();
    }

    public List<Schueler> getSchuelerData() throws SQLException, ClassNotFoundException {
        connection();
        List<Schueler> schuelerList = new ArrayList<Schueler>();
        int schulerID;
        String vorname, nachname, klasse;
        List<String> wuensche = new ArrayList<String>();

        String schulerList = "SELECT * FROM Schueler;";

        Statement statement = connection().createStatement();
        ResultSet resultSet = statement.executeQuery(schulerList);

        while (resultSet.next()){
            schulerID = resultSet.getInt("schuelerID");
            vorname = resultSet.getString("vorname");
            nachname = resultSet.getString("nachname");
            klasse = resultSet.getString("klasse");
            for (int i = 0; i < 5; i++){
                wuensche.add(i, resultSet.getString("wunsch" + i));
            }

            Schueler schueler = new Schueler(klasse, vorname, nachname, wuensche);
            schueler.setSchuelerID(schulerID);

            schuelerList.add(schueler);
        }


        connection().close();

        return schuelerList;
    }


    // Unternehmen
    public void setUnternehmenData(List<Unternehmen> unternehmenList) throws SQLException, ClassNotFoundException {
        connection();
        for (int i = 0; i < unternehmenList.size(); i++){
            String sqlInsert = "INSERT INTO Unternehmen VALUES ('"+
                    unternehmenList.get(i).getFirmenID() +"', " +
                    "'"+ unternehmenList.get(i).getUnternehmen() + "', " +
                    "'"+ unternehmenList.get(i).getFachrichtung() +"', " +
                    "'"+ unternehmenList.get(i).getMaxTeilnehmer() +"', " +
                    "'"+ unternehmenList.get(i).getMaxVeranstaltungen() +"', " +
                    "'"+ unternehmenList.get(i).getFruehesterZeitslot() +"', " +
                    "'"+ unternehmenList.get(i).getGewichtung() +"', " +
                    "'"+ unternehmenList.get(i).isAktiv() +"', " +
                    "'"+ "Kurse kommt noch" +"');";

            Statement statement = connection().createStatement();
            statement.executeUpdate(sqlInsert);

        }
        connection().close();
    }

    public List<Unternehmen> getUnternehmenData() throws SQLException, ClassNotFoundException {
        connection();
        List<Unternehmen> unternehmenList = new ArrayList<Unternehmen>();
        int firmenID, maxVeranstaltungen, maxTeilnehmer;
        String fruehsterZeitslot, unternehmenName, fachrichtung;
        double gewichtung;
        boolean aktiv;

        String unternehmenListe = "SELECT * FROM Unternehmen;";

        Statement statement = connection().createStatement();
        ResultSet resultSet = statement.executeQuery(unternehmenListe);

        while (resultSet.next()){
            firmenID = resultSet.getInt("firmenID");
            maxTeilnehmer = resultSet.getInt("maxTeilnehmer");
            maxVeranstaltungen = resultSet.getInt("maxVeranstaltungen");
            unternehmenName = resultSet.getString("unternehmenName");
            fachrichtung = resultSet.getString("fachrichtung");
            fruehsterZeitslot = resultSet.getString("fruehsterZeitSlot");
            gewichtung = resultSet.getDouble("gewichtung");
            aktiv = resultSet.getBoolean("aktiv");


            Unternehmen unternehmen = new Unternehmen(firmenID, unternehmenName, fachrichtung, maxTeilnehmer, maxVeranstaltungen, fruehsterZeitslot);
            unternehmen.setAktiv(aktiv);
            unternehmen.setGewichtung(gewichtung);

            unternehmenList.add(unternehmen);
        }


        connection().close();

        return unternehmenList;
    }


    // Raum
    public void setRaumData(List<Raum> raumList) throws SQLException, ClassNotFoundException {
        connection();
        for (int i = 0; i < raumList.size(); i++){
            String sqlInsert = "INSERT INTO Raum VALUES ('"+
                    raumList.get(i).getName() +"', " +
                    "'"+ raumList.get(i).getKapazitaet() + "'" +
                    ");";

            Statement statement = connection().createStatement();
            statement.executeUpdate(sqlInsert);

        }
        connection().close();
    }

    public List<Raum> getRaumData() throws SQLException, ClassNotFoundException {
        connection();
        List<Raum> raumList = new ArrayList<Raum>();
        int raumID, kapazitaet;
        String name;

        String raumListe = "SELECT * FROM Raum;";

        Statement statement = connection().createStatement();
        ResultSet resultSet = statement.executeQuery(raumListe);

        while (resultSet.next()){
            raumID = resultSet.getInt("raumID");
            kapazitaet = resultSet.getInt("kapazitaet");
            name = resultSet.getString("name");


            Raum raum = new Raum(name, kapazitaet);
            raum.setRaumID(raumID);

            raumList.add(raum);
        }

        connection().close();

        return raumList;
    }
}
