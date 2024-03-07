package execlLoad;
import klassenObjekte.Schueler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import klassenObjekte.Unternehmen;
import org.junit.Test;
import java.util.List;



public class ImportFile_Test {
    public static void main(String[] args) {
        testGetChoices_ValidFile();
    }

        @Test
        static void testGetChoices_ValidFile() throws IllegalArgumentException{
            // Ersetzen Sie diesen Pfad durch den Pfad zu Ihrer Test-Excel-Datei
            String path = "H:\\SUD\\IMPORT BOT2_Wahl.xlsx";

            try {
                List<Schueler> schuelerListe = ImportFile.getChoices(path);

                // Ersetzen Sie diese Überprüfungen durch spezifische Tests basierend auf dem Inhalt Ihrer Testdatei
                assertFalse( "Die Liste sollte nicht leer sein.", schuelerListe.isEmpty());

                // Überprüfen Sie, ob der erste Schüler in der Liste den erwarteten Werten entspricht
                Schueler ersterSchueler = schuelerListe.get(0);
                assertEquals("Klasse des ersten Schülers stimmt nicht überein.", ersterSchueler.getKlasse().toString(), "ASS221");
                assertEquals("Vorname des ersten Schülers stimmt nicht überein.", ersterSchueler.getVorname().toString(), "Ali Eren");
                assertEquals("Nachname des ersten Schülers stimmt nicht überein.", ersterSchueler.getNachname().toString(), "Bigay");

                // Überprüfen der Wünsche des ersten Schülers, wenn bekannt
                assertTrue( "Die Wunschliste des ersten Schülers sollte '3' enthalten.", ersterSchueler.getAllWuensche().contains("6"));
                assertTrue( "Die Wunschliste des ersten Schülers sollte '3' enthalten.", ersterSchueler.getAllWuensche().contains("18"));
                assertTrue( "Die Wunschliste des ersten Schülers sollte '3' enthalten.", ersterSchueler.getAllWuensche().contains("19"));
                assertTrue( "Die Wunschliste des ersten Schülers sollte '3' enthalten.", ersterSchueler.getAllWuensche().contains("4"));
                assertTrue( "Die Wunschliste des ersten Schülers sollte '3' enthalten.", ersterSchueler.getAllWuensche().contains("14"));
                assertTrue( "Die Wunschliste des ersten Schülers sollte '3' enthalten.", ersterSchueler.getAllWuensche().contains("22"));

            } catch (IllegalArgumentException e) {
                assertTrue( "Die Methode sollte keine IllegalArgumentException werfen.", false);
            }
        }
    @Test
    static void testGetCompany_ValidFile() throws IllegalArgumentException{
        // Ersetzen Sie diesen Pfad durch den Pfad zu Ihrer Test-Excel-Datei
        String path = "H:\\SUD\\IMPORT BOT1_Veranstaltungsliste.xlsx";

        try {
            List<Unternehmen> unternehmenList = ImportFile.getCompany(path);

            // Ersetzen Sie diese Überprüfungen durch spezifische Tests basierend auf dem Inhalt Ihrer Testdatei
            assertFalse( "Die Liste sollte nicht leer sein.", unternehmenList.isEmpty());

            // Überprüfen Sie, ob der erste Schüler in der Liste den erwarteten Werten entspricht
            Unternehmen ersterUnternehmen = unternehmenList.get(0);
            assertEquals("Klasse des ersten Schülers stimmt nicht überein.", ersterUnternehmen.getFirmenID(), "1");
            assertEquals("Vorname des ersten Schülers stimmt nicht überein.", ersterUnternehmen.getUnternehmen().toString(), "Ali Eren");
            assertEquals("Nachname des ersten Schülers stimmt nicht überein.", ersterUnternehmen, "Bigay");



        } catch (IllegalArgumentException e) {
            assertTrue( "Die Methode sollte keine IllegalArgumentException werfen.", false);
        }

    }


}
