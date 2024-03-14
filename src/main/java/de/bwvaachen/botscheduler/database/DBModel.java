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
    private final URL pfad = getClass().getResource("BOT-Database.db");

    public DBModel() throws SQLException, ClassNotFoundException {
        if (!exitsTable("Schueler")){
            createDbModel();
        }
    }

    /**
     * Strellt die Connection her zu der Datenbank dessen Pfad bereits in dem Attribut 'pfad' bespeichert werden muss
     * das heißt die Datenbank-Datei muss es bereits geben.
     * @return Die Connection die aufgebaut wird, wird zurückgegeben
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection connection() throws ClassNotFoundException, SQLException {
        assert pfad != null;
        String dbPfad = pfad.getPath().replaceFirst("/","");

        Class.forName("org.h2.Driver");

        return DriverManager.getConnection("jdbc:h2:" + dbPfad, "sa", "");
    }

    /**
     * Es werden die nötigen SQL Scripte in Strings gespeichert die in der nacheinander als Statements abgeschickt werden
     * Dafür wird eine Connection aufgebaut um die Statements auf die Datenbank abfeuern zu können
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createDbModel() throws SQLException, ClassNotFoundException {
        Connection conn = connection();


        //********** Define SQL-Statements **********

        String sqlDropTable =
                "DROP TABLE IF EXISTS KursTeilnehmer, Kurs, Raum, Unternehmen, Schueler, Zeitslot, RaumInput, UnternehmenInput, SchuelerInput;" ;

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
                        "CREATE TABLE UnternehmenInput (" +
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
                        "CREATE TABLE RaumInput (" +
                        "    raumID int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "    name varchar(50)," +
                        "    kapazitaet int" +
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

        Statement deletTabel = conn.createStatement();
        deletTabel.executeUpdate(sqlDropTable);

        // Statement for creating Schuler Table
        Statement schuelerTblStmnt = conn.createStatement();
        schuelerTblStmnt.executeUpdate(sqlCreateTblSchueler);

        // Statement for creating Unternehmen Table
        Statement unternehmenTblStmnt = conn.createStatement();
        unternehmenTblStmnt.executeUpdate(sqlCreateTblUnternehmen);

        // Statement for creating Raum Table
        Statement raumTblStmnt = conn.createStatement();
        raumTblStmnt.executeUpdate(sqlCreateTblRaum);

        // Statement for creating Kurs Table
        Statement kursTblStmnt = conn.createStatement();
        kursTblStmnt.executeUpdate(sqlCreateTblKurs);

        // Statement for creating Kurs Teilnehmer Table
        Statement kursTeilnehmerTblStmnt = conn.createStatement();
        kursTeilnehmerTblStmnt.executeUpdate(sqlCreateTblKursTeilnehmer);


        // Statement for creating SchulerInput Table
        Statement schuelerTblStmntInput = conn.createStatement();
        schuelerTblStmntInput.executeUpdate(sqlCreateTblSchuelerInput);

        // Statement for creating UnternehmenInput Table
        Statement unternehmenTblStmntInput = conn.createStatement();
        unternehmenTblStmntInput.executeUpdate(sqlCreateTblUnternehmenInput);

        // Statement for creating RaumInput Table
        Statement raumTblStmntInput = conn.createStatement();
        raumTblStmntInput.executeUpdate(sqlCreateTblRaumInput);


        conn.close();
    }


    //***** Check Tables in Database *****

    /**
     * Prüft ob eine bestimmte Tabelle in der Datenbank existiert
     * @param tableName name der Tabelle die geprüft werden soll
     * @return ein bool wert der aussagt ob es die Tabelle in der Datenbank existiert
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private boolean exitsTable(String tableName) throws SQLException, ClassNotFoundException {
        boolean res = false;
        Connection conn = connection();

        String existsTbl = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES";
        Statement exitsTblstmt = conn.createStatement();
        ResultSet resTblExists = exitsTblstmt.executeQuery(existsTbl);

        while (resTblExists.next()){
            if (tableName.equalsIgnoreCase(resTblExists.getString("TABLE_NAME"))){
                res = true;
            }
        }

        conn.close();

        return res;
    }

    //***** GET- & SET-Methods for Database *****


    //********** Schueler **********

    /**
     * Speichert eine Liste von Schüler in der Datenbank. Diese Liste kann auch nur einen oder keinen Eintrag haben
     * @param schuelerList
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void saveSchueler(List<Schueler> schuelerList) throws SQLException, ClassNotFoundException {
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

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

                schuel.setSchuelerID(i);
                i++;
            }
            conn.close();
        }
    }

    /**
     * Es werden Schüler aus der Datenbank geladen. Aus den gespeicherten Informationen aus der Datenbnak werden
     * Schülerobjekte zusammengebaut und in eine Liste gepackt
     * @return Eine Liste von Schülern
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<Schueler> loadSchueler() throws ClassNotFoundException, SQLException {
        Connection conn = connection();

        List<Schueler> schuelerList = new ArrayList<>();
        if (exitsTable("Schueler")) {
            int schulerID;
            String vorname, nachname, klasse;

            String schulerList = "SELECT * FROM Schueler;";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(schulerList);

            while (resultSet.next()) {
                List<String> wuensche = new ArrayList<>();
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


    //********** Unternehmen **********

    /**
     * Es wird eine Liste von Unternehmen in der Datenbank gespeichert. Diese Liste kann auch nur einen oder keinen Eintrag haben
     * @param unternehmenList
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void saveUnternehmen(List<UnternehmenDAO> unternehmenList) throws SQLException, ClassNotFoundException {
        if (unternehmenList != null) {
            Connection conn = connection();
            for (UnternehmenDAO unternehmenDAO : unternehmenList) {
                String sqlInsert = "INSERT INTO Unternehmen VALUES (" +
                        unternehmenDAO.getFirmenID() + ", " +
                        "'" + unternehmenDAO.getUnternehmen() + "', " +
                        "'" + unternehmenDAO.getFachrichtung() + "', " +
                        "'" + unternehmenDAO.getMaxTeilnehmer() + "', " +
                        "'" + unternehmenDAO.getMaxVeranstaltungen() + "', " +
                        "'" + unternehmenDAO.getFruehesterZeitslot() + "', " +
                        "'" + unternehmenDAO.getGewichtung() + "', " +
                        unternehmenDAO.isAktiv() + ");";

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

            }
            conn.close();
        }
    }

    @Override
    public List<UnternehmenDAO> loadUnternehmen() throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<UnternehmenDAO> unternehmenList = new ArrayList<>();

        if (exitsTable("Unternehmen")) {
            int firmenID, maxVeranstaltungen, maxTeilnehmer;
            String fruehsterZeitslot, unternehmenName, fachrichtung;
            double gewichtung;
            boolean aktiv;

            String unternehmenListe = "SELECT * FROM Unternehmen;";

            Statement statement = conn.createStatement();
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

        conn.close();

        return unternehmenList;
    }


    //********** Kurse **********
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

                for (Schueler schlr : kurseIn.getKursTeilnehmer()) {

                    String sqlInsertTeilnehmer = "INSERT INTO KursTeilnehmer VALUES (" +
                            kurseIn.getID() + "," +
                            schlr.getSchuelerID() + ");";

                    Statement statementKursTeilnehmer = conn.createStatement();
                    statementKursTeilnehmer.executeUpdate(sqlInsertTeilnehmer);

                }
            }
            conn.close();
        }
    }

    @Override
    public List<KursDAO> loadKurse(List<Schueler> schlrList, List<Raum> raum, List<UnternehmenDAO> unternehmen) throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<KursDAO> kursDAOList = new ArrayList<>();

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


    //********** Raum **********
    public void saveRooms(List<Raum> raumList) throws SQLException, ClassNotFoundException {
        if (raumList != null) {
            Connection conn = connection();
            for (Raum raum : raumList) {
                String sqlInsert = "INSERT INTO Raum (name, kapazitaet) VALUES (" +
                        "'" + raum.getName() + "', " +
                        "'" + raum.getKapazitaet() + "'" +
                        ");";

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

            }
            conn.close();
        }
    }

    @Override
    public List<Raum> loadRooms() throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<Raum> raumList = new ArrayList<>();

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


    //***** Input Methoden *****
    //********** Rooms **********
    @Override
    public List<Raum> loadRoomsInput() throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<Raum> raumList = new ArrayList<>();

        if (exitsTable("RaumInput")) {
            int raumID, kapazitaet;
            String name;

            String raumListe = "SELECT * FROM RaumInput;";

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
    public void saveRoomInputData(List<Raum> raumList) throws SQLException, ClassNotFoundException {
        if (raumList != null) {
            Connection conn = connection();
            for (Raum raum : raumList) {
                String sqlInsert = "INSERT INTO RaumInput (name, kapazitaet) VALUES (" +
                        "'" + raum.getName() + "', " +
                        "'" + raum.getKapazitaet() + "'" +
                        ");";

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

            }
            conn.close();
        }
    }

    //********** Schueler **********
    @Override
    public List<Schueler> loadSchuelerInput() throws SQLException, ClassNotFoundException {
        Connection conn = connection();

        List<Schueler> schuelerList = new ArrayList<>();
        if (exitsTable("SchuelerInput")) {
            int schulerID;
            String vorname, nachname, klasse;

            String schulerList = "SELECT * FROM SchuelerInput;";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(schulerList);

            while (resultSet.next()) {
                List<String> wuensche = new ArrayList<>();
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
    public void saveSchuelerInputData(List<Schueler> schuelerList) throws SQLException, ClassNotFoundException {
        if (schuelerList != null) {
            Connection conn = connection();

            int i = 1;

            for (Schueler schuel : schuelerList) {
                String sqlInsert = "INSERT INTO SchuelerInput VALUES ('" +
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

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

                schuel.setSchuelerID(i);
                i++;
            }
            conn.close();
        }
    }

    //********** Unternehmen **********
    @Override
    public List<UnternehmenDAO> loadUnternehmenInput() throws SQLException, ClassNotFoundException {
        Connection conn = connection();
        List<UnternehmenDAO> unternehmenList = new ArrayList<>();

        if (exitsTable("UnternehmenInput")) {
            int firmenID, maxVeranstaltungen, maxTeilnehmer;
            String fruehsterZeitslot, unternehmenName, fachrichtung;
            double gewichtung;
            boolean aktiv;

            String unternehmenListe = "SELECT * FROM UnternehmenInput;";

            Statement statement = conn.createStatement();
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

        conn.close();

        return unternehmenList;
    }
    public void saveUnternehmenInputData(List<UnternehmenDAO> unternehmenList) throws SQLException, ClassNotFoundException {
        if (unternehmenList != null) {
            Connection conn = connection();
            for (UnternehmenDAO unternehmenDAO : unternehmenList) {
                String sqlInsert = "INSERT INTO UnternehmenInput VALUES (" +
                        unternehmenDAO.getFirmenID() + ", " +
                        "'" + unternehmenDAO.getUnternehmen() + "', " +
                        "'" + unternehmenDAO.getFachrichtung() + "', " +
                        "'" + unternehmenDAO.getMaxTeilnehmer() + "', " +
                        "'" + unternehmenDAO.getMaxVeranstaltungen() + "', " +
                        "'" + unternehmenDAO.getFruehesterZeitslot() + "', " +
                        "'" + unternehmenDAO.getGewichtung() + "', " +
                        unternehmenDAO.isAktiv() + ");";

                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlInsert);

            }
            conn.close();
        }
    }



    //******* Find Methoden *******
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


    @Override
    public void saveState(List<Raum> raeume, List<Schueler> schueler, List<UnternehmenDAO> unternehmen, List<KursDAO> kurse,
                          List<Raum> raeumeInput, List<Schueler> schuelerInput, List<UnternehmenDAO> unternehmenInput) throws SQLException, ClassNotFoundException {
        createDbModel();
        saveRooms(raeume);
        saveSchueler(schueler);
        saveUnternehmen(unternehmen);
        saveKurse(kurse);
        saveSchuelerInputData(schuelerInput);
        saveRoomInputData(raeumeInput);
        saveUnternehmenInputData(unternehmenInput);
    }
}
