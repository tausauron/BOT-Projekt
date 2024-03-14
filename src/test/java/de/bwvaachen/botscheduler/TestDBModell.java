package de.bwvaachen.botscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.database.DBModel;
import de.bwvaachen.botscheduler.model.DAOFactory;
import de.bwvaachen.botscheduler.model.KursDAO;
import de.bwvaachen.botscheduler.model.UnternehmenDAO;
import execlLoad.ImportFile;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDBModell {
    private static List<Schueler> schueler;
    private static List<Unternehmen> unternehmen;
    private static List<UnternehmenDAO> unternehmenDAO = new ArrayList<>();
    private static List<Raum> raum;
    private static List<KursDAO> kurseDAO = new ArrayList<>();
    private static DBModel database;


    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        URL dbPfad = DBModel.class.getResource("test.mv.db");
        String path = TestDBModell.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
        String unternehmenListPath = TestDBModell.class.getResource("IMPORT BOT1_Veranstaltungsliste.xlsx").toURI().getPath();
        String raumListPath = TestDBModell.class.getResource("IMPORT BOT0_Raumliste.xlsx").toURI().getPath();

        try {
            database = new DBModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String stringDbPfad = dbPfad.getPath().toString().replaceFirst("/","");
        schueler = ImportFile.getChoices(path);
        unternehmen = ImportFile.getCompany(unternehmenListPath);

        raum = ImportFile.getRoom(raumListPath);


        KursPlaner planer = new KursPlaner();

        planer.belegeKurse(schueler,unternehmen,raum);

        List<Kurse> kurse = planer.getKurse();

        for (Kurse k : kurse){
            kurseDAO.add(DAOFactory.createKursDAO(k));
        }
        for (Unternehmen u : unternehmen){
            unternehmenDAO.add(DAOFactory.createUnternehmenDAO(u));
        }

        database.createDbModel();
    }

    @BeforeEach
    void setBeforeTest() throws Exception {
         database.createDbModel();
    }

    @Test
    void testSetSchuelerData() throws SQLException, ClassNotFoundException {
        Connection conn = database.connection();
        database.saveSchueler(schueler);

        String getRow = "SELECT nachname,wunsch1,wunsch2,wunsch3 FROM Schueler;";
        String getRowCount = "SELECT COUNT(nachname) FROM Schueler;";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(getRow);

        Statement rowCount = conn.createStatement();
        //ResultSet resultSetCount = statement.executeQuery(getRowCount);

        for (int i = 0; resultSet.next(); i++){

            System.out.println(resultSet.getString("nachname"));
            System.out.println(resultSet.getString("wunsch1"));
            System.out.println(resultSet.getString("wunsch2"));
            System.out.println(resultSet.getString("wunsch3"));
            assertEquals(schueler.get(i).getNachname(), resultSet.getString("nachname"));
        }
    }

    @Test
    void testGetSchuelerData() throws SQLException, ClassNotFoundException {
        database.saveSchueler(schueler);
        List<Schueler> testList = database.loadSchueler();

        for (int i = 0; i < schueler.size(); i++) {
            assertEquals(schueler.get(i).getVorname(), testList.get(i).getVorname());
        }
    }


    @Test
    void testSetUnternehmenData() throws SQLException, ClassNotFoundException {
        Connection conn = database.connection();
        database.saveUnternehmen(unternehmenDAO);

        String getRow = "SELECT unternehmenName FROM Unternehmen;";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(getRow);

        for (int i = 0; resultSet.next(); i++){
            System.out.println("Unternehmen aus DB: " + resultSet.getString("unternehmenName"));
            System.out.println("Unternehmen aus Excel: " + unternehmen.get(i).getUnternehmen());
            System.out.println("----------------------------");
            assertEquals(unternehmen.get(i).getUnternehmen(), resultSet.getString("unternehmenName"));
        }
    }

    @Test
    void testGetUnternehmenData() throws SQLException, ClassNotFoundException {
        database.saveUnternehmen(unternehmenDAO);
        List<de.bwvaachen.botscheduler.model.UnternehmenDAO> testList = database.loadUnternehmen();

        for (int i = 0; i < unternehmen.size(); i++) {
            System.out.println("Unternehmen aus DB: " + testList.get(i).getUnternehmen());
            System.out.println("Unternehmen aus Excel: " + unternehmen.get(i).getUnternehmen());
            System.out.println("----------------------------");
            assertEquals(unternehmen.get(i).getUnternehmen(), testList.get(i).getUnternehmen());
        }
    }


    @Test
    void testSetRaumData() throws SQLException, ClassNotFoundException {
        Connection conn = database.connection();
        database.saveRooms(raum);

        String getRow = "SELECT name FROM Raum;";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(getRow);

        for (int i = 0; resultSet.next(); i++){
            System.out.println("Raum aus DB: " + resultSet.getString("name"));
            System.out.println("Raum aus Excel: " + raum.get(i).getName());
            System.out.println("----------------------------");
            assertEquals(raum.get(i).getName(), resultSet.getString("name"));
        }
    }

    @Test
    void testGetRaumData() throws SQLException, ClassNotFoundException {
        database.saveRooms(raum);
        List<Raum> testList = database.loadRooms();

        for (int i = 0; i < testList.size(); i++) {
            System.out.println("Raum aus DB: " + testList.get(i).getName());
            System.out.println("Raum aus Excel: " + raum.get(i).getName());
            System.out.println("----------------------------");
            assertEquals(raum.get(i).getName(), testList.get(i).getName());
        }
    }


    @Test
    void testLoadKurse() throws SQLException, ClassNotFoundException {
        database.saveSchueler(schueler);
        database.saveUnternehmen(unternehmenDAO);
        database.saveRooms(raum);
        database.saveKurse(kurseDAO);
        List<KursDAO> testList = database.loadKurse(database.loadSchueler(), database.loadRooms(), database.loadUnternehmen());

        System.out.println("fertig");
    }
}
