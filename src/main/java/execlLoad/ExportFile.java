package execlLoad;

import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import klassenObjekte.*;

/**
 * 
 * @author Grassmann_Dus
 * 
 */

public class ExportFile {

	public static void main(String[] args) {
		String exportFilePath = "H:\\ExportedData.xlsx";

		exportCompanyData(List.of(new Unternehmen(0, "BWV", "Schule", 20, 2, "A")), exportFilePath);
	}

	public static void exportStudentData(List<Schueler> studentList, String exportFilePath) {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(exportFilePath)) {

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
			for (Schueler schueler : studentList) {
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

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
			}

			JOptionPane.showMessageDialog(null, "Erfolgreich exportiert!\n" + exportFilePath, "Export",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportCompanyData(List<Unternehmen> companyList, String exportFilePath) {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(exportFilePath)) {

			Sheet sheet = workbook.createSheet("Firmen");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Nr.");
			headerRow.createCell(1).setCellValue("Unternehmen");
			headerRow.createCell(2).setCellValue("Fachrichtung");
			headerRow.createCell(3).setCellValue("Max. Teilnehmer");
			headerRow.createCell(4).setCellValue("Max. Veranstaltungen");
			headerRow.createCell(5).setCellValue("Frühester Zeitpunkt");

			// Fill data rows
			int rowNum = 1;
			for (Unternehmen company : companyList) {
				Row dataRow = sheet.createRow(rowNum++);
				dataRow.createCell(0).setCellValue(company.getFirmenID());
				dataRow.createCell(1).setCellValue(company.getUnternehmen());
				dataRow.createCell(2).setCellValue(company.getFachrichtung());
				dataRow.createCell(3).setCellValue(company.getMaxTeilnehmer());
				dataRow.createCell(4).setCellValue(company.getMaxVeranstaltungen());
				dataRow.createCell(5).setCellValue(company.getFruehesterZeitslot());
			}

			// Write the workbook content to the output file
			workbook.write(fos);

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
			}

			JOptionPane.showMessageDialog(null, "Erfolgreich exportiert!\n" + exportFilePath, "Export",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void exportRoomData(List<Raum> roomList, String exportFilePath) {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(exportFilePath)) {

			Sheet sheet = workbook.createSheet("Räume");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Raum");
			headerRow.createCell(1).setCellValue("Kapazität");

			// Fill data rows
			int rowNum = 1;
			for (Raum room : roomList) {
				Row dataRow = sheet.createRow(rowNum++);
				dataRow.createCell(0).setCellValue(room.getName());
				dataRow.createCell(1).setCellValue(room.getKapazitaet());
			}

			// Write the workbook content to the output file
			workbook.write(fos);

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
			}

			JOptionPane.showMessageDialog(null, "Erfolgreich exportiert!\n" + exportFilePath, "Export",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
