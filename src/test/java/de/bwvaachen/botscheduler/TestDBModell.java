package de.bwvaachen.botscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import de.bwvaachen.botscheduler.database.DBModel;
import execlLoad.ImportFile;
import klassenObjekte.Schueler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TestDBModell {
    private static List<Schueler> schueler;
    private DBModel database = new DBModel();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        URL dbPfad = DBModel.class.getResource("test.mv.db");
        String path = TestDBModell.class.getResource("IMPORT BOT2_Wahl.xlsx").toURI().getPath();
        DBModel database = new DBModel();

        String stringDbPfad = dbPfad.getPath().toString().replaceFirst("/","");
        schueler = ImportFile.getChoices(path);
        database.createDbModel();
    }

    @Test
    void testSetSchuelerData() throws SQLException, ClassNotFoundException {
        Connection conn = database.connection();
        database.setSchuelerData(schueler);

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
        List<Schueler> testList = database.getSchuelerData();

        for (int i = 0; i < schueler.size(); i++) {
            assertEquals(schueler.get(i).getVorname(), testList.get(i).getVorname());
        }
    }
}
