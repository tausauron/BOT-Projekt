package de.bwvaachen.botscheduler.database;

import de.bwvaachen.botscheduler.model.KursDAO;
import de.bwvaachen.botscheduler.model.UnternehmenDAO;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBModel implements IDatabase {
    private URL pfad = getClass().getResource("test.mv.db");


    public static void main(String[] args) throws Exception {
        DBModel dbModel = new DBModel();

        dbModel.createDbModel();
        System.out.println(dbModel.exitsTable("Schueler"));

    }

    public Connection connection() throws ClassNotFoundException, SQLException {
        String dbPfad = pfad.getPath().toString().replaceFirst("/","");

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:" + dbPfad, "sa", "");

        return conn;
    }

    public void createDbModel() throws Exception {
        connection();


        //********** Define SQL-Statements **********

        String sqlDropTable =
                "DROP TABLE IF EXISTS KursTeilnehmer, Kurs, Raum, Unternehmen, Schueler, Zeitslot;" ;

        String sqlCreateTblSchueler =
                "CREATE TABLE Schueler (" +
                "    schuelerID int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
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

        String sqlCreateTblUnternehmen =
                "CREATE TABLE Unternehmen (" +
                "    firmenID int NOT NULL PRIMARY KEY," +
                "    unternehmenName varchar(50)," +
                "    fachrichtung varchar(200)," +
                "    maxTeilnehmer int," +
                "    maxVeranstaltungen int," +
                "    fruehsterZeitSlot varchar(1)," +
                "    gewichtung double," + // double als datentyp näher anschauen was notwending
                "    aktiv boolean" +
                ");";

        String sqlCreateTblRaum =
                "CREATE TABLE Raum (" +
                "    raumID int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "    name varchar(50)," +
                "    kapazitaet int" +
                ");";

        String sqlCreateTblKurs =
                        "CREATE TABLE Kurs (" +
                        "kursID int NOT NULL PRIMARY KEY, " +
                        "raumID int NOT NULL, " +
                        "firmenID int NOT NULL," +
                        "fruehsterZeitslot varchar(1) NOT NULL," +
                        "FOREIGN KEY (fruehsterZeitslot) REFERENCES Zeitslot(zeitslotChar)," +
                        "FOREIGN KEY (firmenID) REFERENCES Unternehmen(firmenID)," +
                        "FOREIGN KEY (raumID) REFERENCES Raum(raumID)" +
                        ");";

        String sqlCreateTblKursTeilnehmer =
                        "CREATE TABLE KursTeilnehmer (" +
                        "kursID int NOT NULL, " +
                        "schuelerID int NOT NULL," +
                        "FOREIGN KEY (kursID) REFERENCES Kurs(kursID)," +
                        "FOREIGN KEY (schuelerID) REFERENCES Schueler(schuelerID)," +
                        "PRIMARY KEY (kursID, schuelerID)" +
                        ");";

        String sqlCreateTblZeitslot =
                        "CREATE TABLE IF NOT EXISTS Zeitslot (" +
                        "zeitslotChar varchar(1) NOT NULL PRIMARY KEY, " +
                        "zeitStart DATETIME," +
                        "zeitEnde DATETIME" +
                        ");";



        //********** Execute Statements **********

        Statement deletTabel = connection().createStatement();
        deletTabel.executeUpdate(sqlDropTable);

        // Statement for creating Zeitslot Table
        Statement zeitslotTblStmnt = connection().createStatement();
        zeitslotTblStmnt.executeUpdate(sqlCreateTblZeitslot);

        // Statement for creating Schuler Table
        Statement schuelerTblStmnt = connection().createStatement();
        schuelerTblStmnt.executeUpdate(sqlCreateTblSchueler);

        // Statement for creating Unternehmen Table
        Statement unternehmenTblStmnt = connection().createStatement();
        unternehmenTblStmnt.executeUpdate(sqlCreateTblUnternehmen);

        // Statement for creating Raum Table
        Statement raumTblStmnt = connection().createStatement();
        raumTblStmnt.executeUpdate(sqlCreateTblRaum);

        // Statement for creating Kurs Table
        Statement kursTblStmnt = connection().createStatement();
        kursTblStmnt.executeUpdate(sqlCreateTblKurs);

        // Statement for creating Kurs Teilnehmer Table
        Statement kursTeilnehmerTblStmnt = connection().createStatement();
        kursTeilnehmerTblStmnt.executeUpdate(sqlCreateTblKursTeilnehmer);




        connection().close();
    }


    //********** GET- & SET-Methods for Database **********

    // Schueler
    public void setSchuelerData(List<Schueler> schuelerList) throws SQLException, ClassNotFoundException {
        if (schuelerList != null) {
            connection();
            for (Schueler schuel : schuelerList) {
                String sqlInsert = "INSERT INTO Schueler VALUES ('" +
                        schuel.getSchuelerID() + "', " +
                        "'" + schuel.getNachname() + "', " +
                        "'" + schuel.getVorname() + "', " +
                        "'" + schuel.getAllWuensche().get(0) + "', " +
                        "'" + schuel.getAllWuensche().get(1) + "', " +
                        "'" + schuel.getAllWuensche().get(2) + "', " +
                        "'" + schuel.getAllWuensche().get(3) + "', " +
                        "'" + schuel.getAllWuensche().get(4) + "', " +
                        "'" + schuel.getAllWuensche().get(5) + "', " +
                        "'" + schuel.getKlasse() + "');";

                String sql_getSchlrID = "SELECT schuelerID FROM Schueler;";


                Statement statement = connection().createStatement();
                statement.executeUpdate(sqlInsert);
                Statement statementGetSchlr = connection().createStatement();
                ResultSet rsSet = statementGetSchlr.executeQuery(sql_getSchlrID);

                schuel.setSchuelerID(rsSet.getInt("schuelerID"));

            }
            connection().close();
        }
    }

    private boolean exitsTable(String tableName) throws SQLException, ClassNotFoundException {
        boolean res = false;
        connection();

        String existsTbl = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES";
        Statement exitsTblstmt = connection().createStatement();
        ResultSet resTblExists = exitsTblstmt.executeQuery(existsTbl);

        while (resTblExists.next()){
            if (tableName.equalsIgnoreCase(resTblExists.getString("TABLE_NAME"))){
                res = true;
            }
        }

        connection().close();

        return res;
    }

    @Override
    public List<Schueler> loadSchueler() throws ClassNotFoundException, SQLException {
        connection();

        List<Schueler> schuelerList = new ArrayList<Schueler>();
        if (exitsTable("Schueler")) {
            int schulerID;
            String vorname, nachname, klasse;
            List<String> wuensche = new ArrayList<String>();

            String schulerList = "SELECT * FROM Schueler;";

            Statement statement = connection().createStatement();
            ResultSet resultSet = statement.executeQuery(schulerList);

            while (resultSet.next()) {
                schulerID = resultSet.getInt("schuelerID");
                vorname = resultSet.getString("vorname");
                nachname = resultSet.getString("nachname");
                klasse = resultSet.getString("klasse");
                for (int i = 0; i < 5; i++) {
                    wuensche.add(i, resultSet.getString("wunsch" + i));
                }

                Schueler schueler = new Schueler(klasse, vorname, nachname, wuensche);
                schueler.setSchuelerID(schulerID);

                schuelerList.add(schueler);
            }
        }

        connection().close();

        return schuelerList;

    }


    // Unternehmen
    public void setUnternehmenData(List<Unternehmen> unternehmenList) throws SQLException, ClassNotFoundException {
        if (unternehmenList != null) {
            connection();
            for (int i = 0; i < unternehmenList.size(); i++) {
                String sqlInsert = "INSERT INTO Unternehmen VALUES ('" +
                        unternehmenList.get(i).getFirmenID() + "', " +
                        "'" + unternehmenList.get(i).getUnternehmen() + "', " +
                        "'" + unternehmenList.get(i).getFachrichtung() + "', " +
                        "'" + unternehmenList.get(i).getMaxTeilnehmer() + "', " +
                        "'" + unternehmenList.get(i).getMaxVeranstaltungen() + "', " +
                        "'" + unternehmenList.get(i).getFruehesterZeitslot() + "', " +
                        "'" + unternehmenList.get(i).getGewichtung() + "', " +
                        "'" + unternehmenList.get(i).isAktiv() + "', " +
                        "'" + "Kurse kommt noch" + "');";

                Statement statement = connection().createStatement();
                statement.executeUpdate(sqlInsert);

            }
            connection().close();
        }
    }

    @Override
    public List<UnternehmenDAO> loadUnternehmen() throws SQLException, ClassNotFoundException {
        connection();
        List<UnternehmenDAO> unternehmenList = new ArrayList<UnternehmenDAO>();

        if (exitsTable("Unternehmen")) {
            int firmenID, maxVeranstaltungen, maxTeilnehmer;
            String fruehsterZeitslot, unternehmenName, fachrichtung;
            double gewichtung;
            boolean aktiv;

            String unternehmenListe = "SELECT * FROM Unternehmen;";

            Statement statement = connection().createStatement();
            ResultSet resultSet = statement.executeQuery(unternehmenListe);

            while (resultSet.next()) {
                firmenID = resultSet.getInt("firmenID");
                maxTeilnehmer = resultSet.getInt("maxTeilnehmer");
                maxVeranstaltungen = resultSet.getInt("maxVeranstaltungen");
                unternehmenName = resultSet.getString("unternehmenName");
                fachrichtung = resultSet.getString("fachrichtung");
                fruehsterZeitslot = resultSet.getString("fruehsterZeitSlot");
                gewichtung = resultSet.getDouble("gewichtung");
                aktiv = resultSet.getBoolean("aktiv");


                UnternehmenDAO unternehmen = new UnternehmenDAO(firmenID, unternehmenName, fachrichtung, maxTeilnehmer, maxVeranstaltungen, fruehsterZeitslot);
                unternehmen.setAktiv(aktiv);
                unternehmen.setGewichtung(gewichtung);

                unternehmenList.add(unternehmen);
            }
        }

        connection().close();

        return unternehmenList;
    }

    public void saveKurse(List<KursDAO> kurse) throws SQLException, ClassNotFoundException {
        if (kurse != null) {
            connection();
            for (KursDAO kurseIn : kurse) {
                String sqlInsert = "INSERT INTO Kurse VALUES (" +
                        "'" + kurseIn.getRaum().getRaumID() + "', " +
                        "'" + kurseIn.getUnternehmen().getFirmenID() + "', " +
                        "'" + kurseIn.getZeitslot() + "');";
                Statement statement = connection().createStatement();
                statement.executeUpdate(sqlInsert);

                String sql_getKursID = "SELECT kursID FROM Kurs;";
                Statement stmt_getKursID = connection().createStatement();
                ResultSet result = stmt_getKursID.executeQuery(sql_getKursID);
                kurseIn.setID(result.getInt("kursID"));

                for (Schueler schlr : kurseIn.getKursTeilnehmer()) {

                    String sqlInsertTeilnehmer = "INSERT INTO KursTeilnehmer VALUES (" +
                            "'" + schlr.getSchuelerID() + "'" +
                            "'" + kurseIn.getID() + "');";

                    Statement statementKursTeilnehmer = connection().createStatement();
                    statementKursTeilnehmer.executeUpdate(sqlInsertTeilnehmer);
                }
            }
            connection().close();
        }
    }

    @Override
    public List<KursDAO> loadKurse() throws SQLException, ClassNotFoundException {
        connection();
        List<KursDAO> kursDAOList = new ArrayList<KursDAO>();

        if (exitsTable("Kurse")) {
            Raum raum;
            List<Schueler> kursTeilnehmer;
            UnternehmenDAO unternehmen;
            String zeitslot;

            String sql_kursList = "SELECT * FROM Kurs;";
            String sql_kursTeilnehmerList = "SELECT Schueler.schuelerID, Schueler.vorname, Schueler.nachname, Schueler.klasse, " +
                    "Schueler.wunsch0, Schueler.wunsch1, Schueler.wunsch2, Schueler.wunsch3, Schueler.wunsch4, Schueler.wunsch5" +
                    "FROM KursTeilnehmer" +
                    "RIGHT JOIN Schueler ON kursTeilnehmer.schuelerID = Schueler.schuelerID" +
                    "LEFT JOIN Kurs ON kursTeilnehmer.kursID = Kurs.kursID;";

            Statement statementKurs = connection().createStatement();
            ResultSet resultSetKurs = statementKurs.executeQuery(sql_kursList);
            Statement statementKursTeil = connection().createStatement();
            ResultSet resultSetKursTeilnehmer = statementKursTeil.executeQuery(sql_kursTeilnehmerList);

            while (resultSetKursTeilnehmer.next()) {
                System.out.println(resultSetKurs.getString("vorname"));
            }
        }
        connection().close();

        return null;
    }


    // Raum
    public void setRaumData(List<Raum> raumList) throws SQLException, ClassNotFoundException {
        if (raumList != null) {
            connection();
            for (int i = 0; i < raumList.size(); i++) {
                String sqlInsert = "INSERT INTO Raum (name, kapazitaet) VALUES (" +
                        "'" + raumList.get(i).getName() + "', " +
                        "'" + raumList.get(i).getKapazitaet() + "'" +
                        ");";

                Statement statement = connection().createStatement();
                statement.executeUpdate(sqlInsert);

            }
            connection().close();
        }
    }

    @Override
    public List<Raum> loadRooms() throws SQLException, ClassNotFoundException {
        connection();
        List<Raum> raumList = new ArrayList<Raum>();

        if (exitsTable("Raum")) {
            int raumID, kapazitaet;
            String name;

            String raumListe = "SELECT * FROM Raum;";

            Statement statement = connection().createStatement();
            ResultSet resultSet = statement.executeQuery(raumListe);

            while (resultSet.next()) {
                raumID = resultSet.getInt("raumID");
                kapazitaet = resultSet.getInt("kapazitaet");
                name = resultSet.getString("name");


                Raum raum = new Raum(name, kapazitaet);
                raum.setRaumID(raumID);

                raumList.add(raum);
            }
        }
        connection().close();

        return raumList;
    }


    @Override
    public void saveState(List<Raum> raeume, List<Schueler> schueler, List<UnternehmenDAO> unternehmen, List<KursDAO> kurse) {

    }
}
