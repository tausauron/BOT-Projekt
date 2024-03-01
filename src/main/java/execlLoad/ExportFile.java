package execlLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import klassenObjekte.*;


/**
 * 
 * @author Grassmann_Dus
 * 
 * */

public class ExportFile {

    public static void main(String[] args) {
        // Assuming you have lists of students and companies
        List<Schueler> schuelerListe = getChoices("H:\\BOT2_Wahl.xlsx");

        // Specify the output file path for the export
        String exportFilePath = "H:\\ExportedData.xlsx";

        // Call the export method for students
        exportStudentData(schuelerListe, exportFilePath);

        // You can do a similar export for company data
        // List<Unternehmen> companyListe = getCompany("Path to Company Excel file");
        // exportCompanyData(companyListe, "Output path for company export");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schulerListe;
    }

    public static void exportStudentData(List<Schueler> schuelerListe, String exportFilePath) {
        try (Workbook workbook = new XSSFWorkbook(); 
             FileOutputStream fos = new FileOutputStream(exportFilePath)) {

            Sheet sheet = workbook.createSheet("Schueler");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Klasse");
            headerRow.createCell(1).setCellValue("Vorname");
            headerRow.createCell(2).setCellValue("Nachname");
            headerRow.createCell(3).setCellValue("Wunsch 1");
            headerRow.createCell(4).setCellValue("Wunsch 2");
            headerRow.createCell(5).setCellValue("Wunsch 3");
            headerRow.createCell(6).setCellValue("Wunsch 4");
            headerRow.createCell(7).setCellValue("Wunsch 5");
            headerRow.createCell(8).setCellValue("Wunsch 6");

            // Fill data rows
            int rowNum = 1;
            for (Schueler schueler : schuelerListe) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(schueler.getKlasse());
                dataRow.createCell(1).setCellValue(schueler.getVorname());
                dataRow.createCell(2).setCellValue(schueler.getNachname());

                List<String> wunschliste = schueler.getAllWuensche();
                for (int i = 0; i < wunschliste.size(); i++) {
                    dataRow.createCell(3 + i).setCellValue(wunschliste.get(i));
                }
            }

            // Write the workbook content to the output file
            workbook.write(fos);


    		try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); }
    		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }

            JOptionPane.showMessageDialog(null, "Erfolgreich exportiert!\n" + exportFilePath, "Export", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: exportCompanyData anpassen an das Sheet
    public static void exportCompanyData(List<Unternehmen> companyListe, String exportFilePath) {
        try (Workbook workbook = new XSSFWorkbook(); 
                FileOutputStream fos = new FileOutputStream(exportFilePath)) {

               Sheet sheet = workbook.createSheet("Firmen");

               // Create header row
               Row headerRow = sheet.createRow(0);
               headerRow.createCell(0).setCellValue("Klasse");
               headerRow.createCell(1).setCellValue("Vorname");
               headerRow.createCell(2).setCellValue("Nachname");
               headerRow.createCell(3).setCellValue("Wunsch 1");
               headerRow.createCell(4).setCellValue("Wunsch 2");
               headerRow.createCell(5).setCellValue("Wunsch 3");
               headerRow.createCell(6).setCellValue("Wunsch 4");
               headerRow.createCell(7).setCellValue("Wunsch 5");
               headerRow.createCell(8).setCellValue("Wunsch 6");

               // Fill data rows
               int rowNum = 1;
               for (Unternehmen company: companyListe) {
                   Row dataRow = sheet.createRow(rowNum++);
                   dataRow.createCell(0).setCellValue(company.getKlasse());
                   dataRow.createCell(1).setCellValue(company.getVorname());
                   dataRow.createCell(2).setCellValue(company.getNachname());

                   List<String> wunschliste = company.getAllWuensche();
                   for (int i = 0; i < wunschliste.size(); i++) {
                       dataRow.createCell(3 + i).setCellValue(wunschliste.get(i));
                   }
               }

               // Write the workbook content to the output file
               workbook.write(fos);


       		try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); }
       		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }

               JOptionPane.showMessageDialog(null, "Erfolgreich exportiert!\n" + exportFilePath, "Export", JOptionPane.INFORMATION_MESSAGE);

           } catch (Exception e) {
               e.printStackTrace();
           }
    }
}
