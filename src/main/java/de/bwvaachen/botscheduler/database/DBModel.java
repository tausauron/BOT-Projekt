package de.bwvaachen.botscheduler.database;

import de.bwvaachen.botscheduler.model.KursDAO;
import de.bwvaachen.botscheduler.model.UnternehmenDAO;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;

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

    public DBModel() throws Exception {
        if (!exitsTable("Schueler")){
            createDbModel();
        }
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
                        "kursID int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "raumID int, " +
                        "firmenID int," +
                        "zeitslot varchar(1)," +
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

        String sqlCreateTblUnternehmenInput =
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

        String sqlCreateTblRaumInput =
                "CREATE TABLE Raum (" +
                        "    raumID int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "    name varchar(50)," +
                        "    kapazitaet int" +
                        ");";

        String sqlCreateTblKursInput =
                "CREATE TABLE Kurs (" +
                        "kursID int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "raumID int, " +
                        "firmenID int," +
                        "zeitslot varchar(1)," +
                        "FOREIGN KEY (firmenID) REFERENCES Unternehmen(firmenID)," +
                        "FOREIGN KEY (raumID) REFERENCES Raum(raumID)" +
                        ");";

        String sqlCreateTblKursTeilnehmerInput =
                "CREATE TABLE KursTeilnehmer (" +
                        "kursID int NOT NULL, " +
                        "schuelerID int NOT NULL," +
                        "FOREIGN KEY (kursID) REFERENCES Kurs(kursID)," +
                        "FOREIGN KEY (schuelerID) REFERENCES Schueler(schuelerID)," +
                        "PRIMARY KEY (kursID, schuelerID)" +
                        ");";


        String sqlCreateTblSchuelerInput =
                "CREATE TABLE SchuelerInput (" +
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


        //********** Execute Statements **********

        Statement deletTabel = connection().createStatement();
        deletTabel.executeUpdate(sqlDropTable);

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


        // Statement for creating Schuler Table
        Statement schuelerTblStmntInput = connection().createStatement();
        schuelerTblStmntInput.executeUpdate(sqlCreateTblSchuelerInput);

        // Statement for creating Unternehmen Table
        Statement unternehmenTblStmntInput = connection().createStatement();
        unternehmenTblStmntInput.executeUpdate(sqlCreateTblUnternehmenInput);

        // Statement for creating Raum Table
        Statement raumTblStmntInput = connection().createStatement();
        raumTblStmntInput.executeUpdate(sqlCreateTblRaumInput);

        // Statement for creating Kurs Table
        Statement kursTblStmntInput = connection().createStatement();
        kursTblStmntInput.executeUpdate(sqlCreateTblKursInput);

        // Statement for creating Kurs Teilnehmer Table
        Statement kursTeilnehmerTblStmntInput = connection().createStatement();
        kursTeilnehmerTblStmntInput.executeUpdate(sqlCreateTblKursTeilnehmerInput);


        connection().close();
    }


    //********** GET- & SET-Methods for Database **********

    // Schueler
    public void setSchuelerData(List<Schueler> schuelerList) throws SQLException, ClassNotFoundException {
        if (schuelerList != null) {
            Connection conn = connection();

            int i = 1;

            for (Schueler schuel : schuelerList) {
                String sqlInsert = "INSERT INTO Schueler VALUES ('" +
                        i + "', " +
                        "'" + schuel.getNachname() + "', " +
                        "'" + schuel.getVorname() + "', " +
                        "'" + schuel.getAllWuensche().get(0) + "', " +
                        "'" + schuel.getAllWuensche().get(1) + "', " +
                        "'" + schuel.getAllWuensche().get(2) + "', " +
                        "'" + schuel.getAllWuensche().get(3) + "', " +
                        "'" + schuel.getAllWuensche().get(4) + "', " +
                        "'" + schuel.getAllWuensche().get(5) + "', " +
                        "'" + schuel.getKlasse() + "');";

                //String sql_getSchlrID = "SELECT schuelerID FROM Schueler;";


                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);
                //Statement statementGetSchlr = connection().createStatement();
                //ResultSet rsSet = statementGetSchlr.executeQuery(sql_getSchlrID);

                schuel.setSchuelerID(i);
                i++;
            }
            conn.close();
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
        Connection conn = connection();

        List<Schueler> schuelerList = new ArrayList<Schueler>();
        if (exitsTable("Schueler")) {
            int schulerID;
            String vorname, nachname, klasse;

            String schulerList = "SELECT * FROM Schueler;";

            Statement statement = connection().createStatement();
            ResultSet resultSet = statement.executeQuery(schulerList);

            while (resultSet.next()) {
                List<String> wuensche = new ArrayList<String>();
                schulerID = resultSet.getInt("schuelerID");
                vorname = resultSet.getString("vorname");
                nachname = resultSet.getString("nachname");
                klasse = resultSet.getString("klasse");
                for (int i = 0; i < 6; i++) {
                    wuensche.add(i, resultSet.getString("wunsch" + i));
                }

                Schueler schueler = new Schueler(klasse, vorname, nachname, wuensche);
                schueler.setSchuelerID(schulerID);

                schuelerList.add(schueler);
            }
        }

        conn.close();

        return schuelerList;

    }


    // Unternehmen
    public void setUnternehmenData(List<UnternehmenDAO> unternehmenList) throws SQLException, ClassNotFoundException {
        if (unternehmenList != null) {
            connection();
            for (int i = 0; i < unternehmenList.size(); i++) {
                String sqlInsert = "INSERT INTO Unternehmen VALUES (" +
                        unternehmenList.get(i).getFirmenID() + ", " +
                        "'" + unternehmenList.get(i).getUnternehmen() + "', " +
                        "'" + unternehmenList.get(i).getFachrichtung() + "', " +
                        "'" + unternehmenList.get(i).getMaxTeilnehmer() + "', " +
                        "'" + unternehmenList.get(i).getMaxVeranstaltungen() + "', " +
                        "'" + unternehmenList.get(i).getFruehesterZeitslot() + "', " +
                        "'" + unternehmenList.get(i).getGewichtung() + "', " +
                            unternehmenList.get(i).isAktiv() + ");";

                Statement statement = connection().createStatement();
                statement.executeUpdate(sqlInsert);

            }
            connection().close();
        }
    }

    @Override
    public List<UnternehmenDAO> loadUnternehmen() throws SQLException, ClassNotFoundException {
        connection();
        List<de.bwvaachen.botscheduler.model.UnternehmenDAO> unternehmenList = new ArrayList<de.bwvaachen.botscheduler.model.UnternehmenDAO>();

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


                de.bwvaachen.botscheduler.model.UnternehmenDAO unternehmen = new de.bwvaachen.botscheduler.model.UnternehmenDAO(firmenID, unternehmenName, fachrichtung, maxTeilnehmer, maxVeranstaltungen, fruehsterZeitslot);
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
            Connection conn = connection();
            for (KursDAO kurseIn : kurse) {
                String sqlInsert = "INSERT INTO Kurs (raumID, firmenID, zeitslot) VALUES (" +
                        "NULL, " +
                        "'" + kurseIn.getUnternehmen().getFirmenID() + "', " +
                        "'" + kurseIn.getZeitslot() + "');";
                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

                String sql_getKursID = "SELECT MAX(kursID) FROM Kurs;";
                Statement stmt_getKursID = conn.createStatement();
                ResultSet result = stmt_getKursID.executeQuery(sql_getKursID);
                result.next();
                kurseIn.setID(result.getInt(1));
            }

            for (KursDAO kurseIn : kurse) {
                for (Schueler schlr : kurseIn.getKursTeilnehmer()) {

                    String sqlInsertTeilnehmer = "INSERT INTO KursTeilnehmer VALUES (" +
                            kurseIn.getID() + "," +
                            schlr.getSchuelerID()+ ");";

                    Statement statementKursTeilnehmer = connection().createStatement();
                    statementKursTeilnehmer.executeUpdate(sqlInsertTeilnehmer);
                }
            }
            conn.close();
        }
    }

    @Override
    public List<KursDAO> loadKurse(List<Schueler> schlrList, List<Raum> raum, List<UnternehmenDAO> unternehmen) throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<KursDAO> kursDAOList = new ArrayList<KursDAO>();

        if (exitsTable("Kurs")) {


            String sql_kursList = "SELECT * FROM Kurs;";
            String sql_kursTeilnehmerList = "SELECT * FROM KursTeilnehmer;";

            Statement statementKurs = conn.createStatement();
            ResultSet resultSetKurs = statementKurs.executeQuery(sql_kursList);
            Statement statementKursTeil = conn.createStatement();
            ResultSet resultSetKursTeilnehmer = statementKursTeil.executeQuery(sql_kursTeilnehmerList);

            while (resultSetKurs.next()) {
                KursDAO kurdD = new KursDAO(findRaum(raum, resultSetKurs.getInt("raumID")),
                        new ArrayList<>(),
                        findUnternehmen(unternehmen, resultSetKurs.getInt("firmenID")),
                        resultSetKurs.getString("zeitslot"));
                kursDAOList.add(kurdD);
                int id;
                while (resultSetKursTeilnehmer.next()) {
                    if (resultSetKursTeilnehmer.getInt("kursID") == resultSetKurs.getInt("kursID")) {
                        id = resultSetKursTeilnehmer.getInt("schuelerID");
                        kurdD.getKursTeilnehmer().add(findSchueler(schlrList, id));
                    }
                }
            }
        }
        conn.close();

        return kursDAOList;
    }


    private Raum findRaum(List<Raum> raeume, int ID){
        Raum res = null;
        for (Raum r : raeume){
            if (r.getRaumID() == ID){
                res = r;
            }
        }
        return res;
    }
    private Schueler findSchueler(List<Schueler> schueler, int ID){
        Schueler res = null;
        for (Schueler s : schueler){
            if (s.getSchuelerID() == ID){
                res = s;
            }
        }
        return res;
    }
    private UnternehmenDAO findUnternehmen(List<UnternehmenDAO> unternehmen, int ID){
        UnternehmenDAO res = null;
        for (UnternehmenDAO u : unternehmen){
            if (u.getFirmenID() == ID){
                res = u;
            }
        }
        return res;
    }


    // Raum
    public void setRaumData(List<Raum> raumList) throws SQLException, ClassNotFoundException {
        if (raumList != null) {
            Connection conn = connection();
            for (int i = 0; i < raumList.size(); i++) {
                String sqlInsert = "INSERT INTO Raum (name, kapazitaet) VALUES (" +
                        "'" + raumList.get(i).getName() + "', " +
                        "'" + raumList.get(i).getKapazitaet() + "'" +
                        ");";

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

            }
            conn.close();
        }
    }

    @Override
    public List<Raum> loadRoomsInput() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Raum> loadSchuelerInput() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Raum> loadUnternehmenInput() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Raum> loadRooms() throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<Raum> raumList = new ArrayList<Raum>();

        if (exitsTable("Raum")) {
            int raumID, kapazitaet;
            String name;

            String raumListe = "SELECT * FROM Raum;";

            Statement statement = conn.createStatement();
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
        conn.close();

        return raumList;
    }


    @Override
    public void saveState(List<Raum> raeume, List<Schueler> schueler, List<UnternehmenDAO> unternehmen, List<KursDAO> kurse) throws Exception {
        createDbModel();
        setRaumData(raeume);
        setSchuelerData(schueler);
        setUnternehmenData(unternehmen);
        saveKurse(kurse);
    }
}
