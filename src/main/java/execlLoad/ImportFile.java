package execlLoad;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class ImportFile {
    public static void main(String[] args) {
        //    ImportWunschSchuler();

        //  ExcelImport excelImport = new ExcelImport();
        List<Schueler> schulerListe = getChoices("H:\\SUD\\BOT2_Wahl.xlsx");
        for (Schueler schuler : schulerListe) {
            System.out.println(schuler.getKlasse() + "- " + schuler.getVorname() + "- " + schuler.getNachname() + " - " + schuler.getAllWuensche());

        }

    }


    public static List<Schueler> getChoices(String path) {
        List<Schueler> schulerListe = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue; // Kopfzeile überspringen, wenn vorhanden

                Cell klasseCell = row.getCell(0);
                Cell nachnameCell = row.getCell(1);
                Cell vornameCell = row.getCell(2);


                String klasse = klasseCell.getStringCellValue();
                String vorname = vornameCell.getStringCellValue();
                String nachname = nachnameCell.getStringCellValue();


                List<String> wunschliste = new ArrayList<>();
                // Geht davon aus, dass die Wünsche in den Spalten 3 bis 8 stehen
                for (int colIndex = 3; colIndex < 8; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        wunschliste.add(cell.toString());
                    } else {
                        wunschliste.add(""); // oder fügen Sie einen Standardwert hinzu
                    }
                }

                schulerListe.add(new Schueler(klasse, vorname, nachname, wunschliste)); // fügen aller Schueler mit der Wünsche hinzu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schulerListe;
    }


    public static void ImportWunschSchuler() {
        try {
            File f = new File("H:\\SUD\\IMPORT BOT2_Wahl.xlsx");
            Workbook wb = new XSSFWorkbook(f);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("•\t");
                    }
                }
                System.out.println();
            }
            wb.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static List<Unternehmen> getCompany(String path) {
        List<Unternehmen> companyListe = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Kopfzeile überspringen, wenn vorhanden

                Cell firmenNameCell = row.getCell(0);
                Cell maxTeilnehmerCell = row.getCell(1);
                Cell gewichtungCell = row.getCell(2);
                Cell aktivCell = row.getCell(2);
                Cell zeitslotsCell = row.getCell(3);


                String firmenName = firmenNameCell.getStringCellValue();
                int maxTeilnehmer = (int) maxTeilnehmerCell.getNumericCellValue();
                String zeitslots = zeitslotsCell.getStringCellValue();
                double gewichtung = gewichtungCell.getNumericCellValue();
                boolean aktiv = aktivCell.getBooleanCellValue();
                List<String> zeitslotsListe = Arrays.asList(zeitslots.split(",")); // Wünsche sind durch Kommas getrennt

//                companyListe.add(new Unternehmen(0, firmenName, maxTeilnehmer, zeitslotsListe, gewichtung, aktiv));
                companyListe.add(new Unternehmen(0,firmenName, "", maxTeilnehmer, 5, zeitslots));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return companyListe;
    }



}