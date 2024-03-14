package execlLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import klassenObjekte.Raum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;


public class ImportFile {
    public static void main(String[] args) {
        System.out.println("---------------Schuelerlist------------------------------------------");
        List<Schueler> schulerListe = getChoices("H:\\SUD\\IMPORT BOT2_Wahl.xlsx");
         for (Schueler schuler : schulerListe) {
            System.out.println(schuler.getKlasse() + "- " + schuler.getVorname() + "- " + schuler.getNachname() + " - " + schuler.getAllWuensche());
        }
        System.out.println("---------------UnternehmenListe------------------------------------------");
        List<Unternehmen> unternehmenListe = getCompany("H:\\SUD\\IMPORT BOT1_Veranstaltungsliste.xlsx");
        for (Unternehmen unternehmen : unternehmenListe) {
            System.out.println(unternehmen.getFirmenID() +" - "+ unternehmen.getUnternehmen()+" - "+unternehmen.getFachrichtung()+
                    " - "+unternehmen.getMaxTeilnehmer()+" - "+ unternehmen.getMaxVeranstaltungen() +" - "+unternehmen.getFruehesterZeitslot());
        }
        System.out.println("----------------------RaumListe-----------------------------------");
        List<Raum> RaumListe = getRoom("H:\\SUD\\IMPORT BOT0_Raumliste.xlsx");
        for (Raum raum : RaumListe) {
            System.out.println(raum.getName() + " - " + raum.getKapazitaet());
        }

    }

    // Import Schueler mit Wuenche
    public static List<Schueler> getChoices(String path) throws IllegalArgumentException{
        List<Schueler> schulerListe = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            // Prüfen, ob die Datei mindestens eine Zeile (für Kopfzeilen) und die erwartete Anzahl von Spalten hat
            boolean isCorrectFormat = checkFormatChois(sheet);
            if (!isCorrectFormat) {
                throw new IllegalArgumentException("Die Excel-Datei hat nicht das erwartete Format.\n"
                									+ "Die Spalten müssen \"Klasse\", \"Name\", \"Vorname\", \"Wahl 1\",\n"  
                									+ "\"Wahl 2\", \"Wahl 3\", \"Wahl 4\", \"Wahl 5\", \"Wahl 6\" heißen.");
            }

            for ( Row row : sheet) {

                if (row.getRowNum() == 0) continue; // Kopfzeile überspringen, wenn vorhanden

                Cell klasseCell = row.getCell(0);
                Cell nachnameCell = row.getCell(1);
                Cell vornameCell = row.getCell(2);

                String klasse = klasseCell.getStringCellValue();
                String vorname = vornameCell.getStringCellValue();
                String nachname = nachnameCell.getStringCellValue();


                List<String> wunschliste = new ArrayList<>();
                // Geht davon aus, dass die Wünsche in den Spalten 3 bis 8 stehen
                for (int colIndex = 3; colIndex <= 8; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        wunschliste.add( String.valueOf(((int)cell.getNumericCellValue())));
                    } else {
                        wunschliste.add(""); // oder fügen Sie einen Standardwert hinzu
                    }
                }

                schulerListe.add(new Schueler(klasse, vorname, nachname, wunschliste)); // fügen aller Schueler mit der Wünsche hinzu
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return schulerListe;
    }


    public static List<Unternehmen> getCompany(String path) throws IllegalArgumentException {
        List<Unternehmen> companyListe = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isCorrectFormat = checkFormatCompany(sheet); // .getRow(0));

            if (!isCorrectFormat) {
                throw new IllegalArgumentException("Die Excel-Datei hat nicht das erwartete Format.\n"
						+ "Die Spalten müssen \"Nr.\", \"Unternehmen\", \"Fachrichtung\", \"Max.Teilnehmer\",\n"  
						+ "\"Max. Veranstaltungen\", \"Frühester Zeitpunkt\" heißen.");
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Kopfzeile überspringen
                //Nr. 	Unternehmen	 Fachrichtung	Max.Teilnehmer	Max.Veranstaltungen 	FrühesterZeitpunkt
                Cell firmenNrCell = row.getCell(0);
                Cell firmenNameCell = row.getCell(1);
                Cell fachrichtungCell = row.getCell(2);
                Cell maxTeilnehmerCell = row.getCell(3);
                Cell maxVeranstaltungenCell = row.getCell(4);
                Cell fruehsterZeitslotCell = row.getCell(5);


                int  firmenID = (int)firmenNrCell.getNumericCellValue();
                String unternehmen = firmenNameCell.getStringCellValue();
                String fachrichtung = "";
                if (fachrichtungCell != null)  {fachrichtung = fachrichtungCell.getStringCellValue();}else {fachrichtung = "";}
                int maxTeilnehmer = (int) maxTeilnehmerCell.getNumericCellValue();
                int maxVeranstaltungen = (int) maxVeranstaltungenCell.getNumericCellValue();
                String fruehsterZeitslot = "";
                if (fruehsterZeitslotCell != null){fruehsterZeitslot= fruehsterZeitslotCell.getStringCellValue();}else{fruehsterZeitslot ="";}


                // Hier weitere Informationen auslesen und zum Unternehmen-Objekt hinzufügen
               companyListe.add(new Unternehmen(firmenID, unternehmen, fachrichtung,
                              						maxTeilnehmer,  maxVeranstaltungen,  fruehsterZeitslot));

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return companyListe;
    }

    // get RaumList von Excel-Datei
    public static List<Raum> getRoom(String path) throws IllegalArgumentException{
        List<Raum> RoomListe = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isCorrectFormat = checkFormatRoom(sheet); // .getRow(0));

            if (!isCorrectFormat) {
                throw new IllegalArgumentException("Die Excel-Datei hat nicht das erwartete Format.\n"
						+ "Die Spalten müssen \"Raum\", \"Kapazität\" heißen.");
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Kopfzeile überspringen
                Cell roomNameCell = row.getCell(0);
                Cell roomKapazitaetCell = row.getCell(1);

               // prüf, ob roomNameCell String oder int sein
                if (roomNameCell.getCellType().toString() == "NUMERIC"){
                    int roomName1 = (int)roomNameCell.getNumericCellValue();
                    int  roomroomKapazitaet = (int)roomKapazitaetCell.getNumericCellValue();
                    RoomListe.add(new Raum(String.valueOf(roomName1) ,roomroomKapazitaet));
                    }
                else if(roomNameCell.getCellType().toString() == "STRING")
                {String roomName = roomNameCell.getStringCellValue();
                    int  roomroomKapazitaet = (int)roomKapazitaetCell.getNumericCellValue();
                    RoomListe.add(new Raum(roomName ,roomroomKapazitaet));
                    }
                else System.out.printf("Diese Wert muss String oder Anzahl sein");

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return RoomListe;

    }

    //prüf, ob Schueler-Excel-Datei im korrekten Format ist
    private static boolean checkFormatChois(Sheet sheet) {
        //Klasse	Name	Vorname	Wahl 1	Wahl 2	Wahl 3	Wahl 4	Wahl 5	Wahl 6
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return false;
        // Prüfen, ob die Datei mindestens eine Zeile (für Kopfzeilen) und die erwartete Anzahl von Spalten hat
        try {
            boolean isKlasseCorrect = headerRow.getCell(0).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Klasse");
            boolean isNachnameCorrect = headerRow.getCell(1).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Name");
            boolean isVornameCorrect = headerRow.getCell(2).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Vorname");
            boolean isWunsch1Correct = headerRow.getCell(3).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Wahl1");
            boolean isWunsch2Correct = headerRow.getCell(4).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Wahl2");
            boolean isWunsch3Correct = headerRow.getCell(5).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Wahl3");
            boolean isWunsch4Correct = headerRow.getCell(6).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Wahl4");
            boolean isWunsch5Correct = headerRow.getCell(7).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Wahl5");
            boolean isWunsch6Correct = headerRow.getCell(8).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Wahl6");



            return isKlasseCorrect && isNachnameCorrect && isVornameCorrect && isWunsch1Correct  &&
                    isWunsch2Correct && isWunsch3Correct &&
                    isWunsch4Correct && isWunsch5Correct && isWunsch6Correct ;
        } catch (NullPointerException e) {
            return false; // Falls eine Zelle fehlt oder null ist
        }

    }

    //prüf, ob Raum-Excel-Datei im korrekten Format ist
    private static boolean checkFormatRoom(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return false;

        //Raum-Format :RaumNr. 	RaumName
        try {
            boolean isFirmenIDCorrect = headerRow.getCell(0).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Raum");
            boolean isUnternehmenCorrect = headerRow.getCell(1).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Kapazität");

            return isFirmenIDCorrect && isUnternehmenCorrect ;
        } catch (NullPointerException e) {
            return false; // Falls eine Zelle fehlt oder null ist
        }
    }

    //prüf, ob Unternehemen-Excel-Datei im korrekten Format ist
    private static boolean checkFormatCompany(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return false;

        //Unternehemen-Format :Nr. 	Unternehmen	Fachrichtung	Max. Teilnehmer	Max. Veranstaltungen	Frühester Zeitpunkt
        try {
            boolean isFirmenIDCorrect = headerRow.getCell(0).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Nr.");
            boolean isUnternehmenCorrect = headerRow.getCell(1).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Unternehmen");
            boolean isFachrichtungCorrect = headerRow.getCell(2).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Fachrichtung");
            boolean isMaxTeilnehmerCorrect = headerRow.getCell(3).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Max.Teilnehmer");
            boolean isMaxVeranstaltungenCorrect = headerRow.getCell(4).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("Max.Veranstaltungen");
            boolean isFruehsterZeitslotCorrect = headerRow.getCell(5).getStringCellValue().replaceAll("\\s+","").equalsIgnoreCase("FrühesterZeitpunkt");


            return isFirmenIDCorrect && isUnternehmenCorrect && isFachrichtungCorrect &&
                    isMaxTeilnehmerCorrect && isMaxVeranstaltungenCorrect &&
                    isFruehsterZeitslotCorrect ;
        } catch (NullPointerException e) {
            return false; // Falls eine Zelle fehlt oder null ist
        }
    }

}