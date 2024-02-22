package execlLoad;

import klassenObjekte.schueler;
import klassenObjekte.unternehmen;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportFile {
    public static void main(String[] args) {
        //    ImportWunschSchuler();

        //  ExcelImport excelImport = new ExcelImport();
        List<schueler> schulerListe = getChoices("H:\\SUD\\BOT2_Wahl.xlsx");
        for (schueler schuler : schulerListe) {
            System.out.println(schuler.getKlasse() + "- " + schuler.getVorname() + "- " + schuler.getNachname() + " - " + schuler.getWuensche());

        }

    }


    public static List<schueler> getChoices(String path) {
        List<schueler> schulerListe = new ArrayList<>();

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

                schulerListe.add(new schueler(klasse, vorname, nachname, wunschliste)); // fügen aller Schueler mit der Wünsche hinzu
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


    public static List<unternehmen> getCompany(String path) {
        List<unternehmen> companyListe = new ArrayList<>();

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

                companyListe.add(new unternehmen(firmenName, maxTeilnehmer, zeitslotsListe, gewichtung, aktiv));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return companyListe;
    }



}