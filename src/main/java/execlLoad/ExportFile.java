package execlLoad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.SchuelerSlot;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.*;

/**
 * 
 * @author Grassmann_Dus
 * 
 */

public class ExportFile implements IExport {

	public void exportStudentData(List<Schueler> studentList, String exportFilePath) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportCompanyData(List<Unternehmen> companyList, String exportFilePath) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportRoomData(List<Raum> roomList, String exportFilePath) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportStudentSchedule(List<CalcSchueler> calcStudents, String exportFilePath) throws FileNotFoundException, IOException {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(exportFilePath)) {
			
			Set<String> uniqueKlassen = new HashSet<>();
			for (CalcSchueler calcStudent : calcStudents) {
			    String klasse = calcStudent.getSchueler().getKlasse();
			    uniqueKlassen.add(klasse.toUpperCase());
			}

			List<String> sortedKlassen = new ArrayList<>(uniqueKlassen);
			Collections.sort(sortedKlassen);

			
			for (String klasse : sortedKlassen) {
				Sheet sheet = workbook.createSheet(klasse);
				
				int rowNum = 0;
				for (CalcSchueler calcStudent : calcStudents) {
					if (calcStudent.getSchueler().getKlasse().equals(klasse)) {

						// Klasse
						Row dataRow = sheet.createRow(rowNum);
						rowNum++;
						dataRow.createCell(0).setCellValue(calcStudent.getSchueler().getKlasse());

						// Name
						Row dataRow2 = sheet.createRow(rowNum);
						rowNum++;
						dataRow2.createCell(0).setCellValue(
								calcStudent.getSchueler().getNachname() + ", " + calcStudent.getSchueler().getVorname());

						// Überschriften
						Row dataRow3 = sheet.createRow(rowNum);
						rowNum++;
						dataRow3.createCell(0).setCellValue("Zeit");
						dataRow3.createCell(1).setCellValue("Raum");
						dataRow3.createCell(2).setCellValue("Veranstaltung");
						dataRow3.createCell(4).setCellValue("Wunsch");

						for (SchuelerSlot slot : calcStudent.getSlots()) {
							// Daten
							Row dataRow4 = sheet.createRow(rowNum);
							rowNum++;
							Typ t = slot.getTyp();
							dataRow4.createCell(0).setCellValue(t.getZeitraum());

							if (slot.getKurs() != null ) {
								if (slot.getKurs().getRaum() != null) {
									dataRow4.createCell(1).setCellValue(slot.getKurs().getRaum().getName());
								} else {
									dataRow4.createCell(1).setCellValue(" - ");
								}
							} else {
								dataRow4.createCell(1).setCellValue(" - ");
							}

							if (slot.getErfuellterWunsch() != null) {
								dataRow4.createCell(2).setCellValue(slot.getErfuellterWunsch().getVeranstaltung().getUnternehmen());
								dataRow4.createCell(3).setCellValue(slot.getErfuellterWunsch().getVeranstaltung().getFachrichtung());
								dataRow4.createCell(4).setCellValue(slot.getErfuellterWunsch().getNummer());
							} else {
								dataRow4.createCell(2).setCellValue(" - ");
								dataRow4.createCell(3).setCellValue(" - ");
								dataRow4.createCell(4).setCellValue(" - ");
							}
						}

						// Eine Zeile frei lassen
						rowNum++;
					}
					
				}

		        // Auto-Size für jede Spalte
		        for (int i = 0; i < 5; i++) {
		            sheet.autoSizeColumn(i);
		        }
				
			}

			

			
			
			
			// Write the workbook content to the output file
			workbook.write(fos);
		}
	}

	@Override
	public void exportRoomUsage(List<Unternehmen> unternehmen, String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exportParticipants(List<Unternehmen> unternehmen, String path) {
		// TODO Auto-generated method stub

	}

	/*
	 * try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new
	 * FileOutputStream(exportFilePath)) {
	 * 
	 * Sheet sheet = workbook.createSheet("Laufzettel");
	 * 
	 * // Klasse Row dataRow = sheet.createRow(0);
	 * dataRow.createCell(0).setCellValue(calcStudent.getSchueler(). getKlasse());
	 * 
	 * // Name Row dataRow2 = sheet.createRow(1);
	 * dataRow2.createCell(0).setCellValue(calcStudent.getSchueler(). getNachname()
	 * + ", " + calcStudent.getSchueler().getVorname());
	 * 
	 * // Überschriften Row dataRow3 = sheet.createRow(2);
	 * dataRow3.createCell(0).setCellValue("Zeit");
	 * dataRow3.createCell(1).setCellValue("Raum");
	 * dataRow3.createCell(2).setCellValue("Veranstaltung");
	 * dataRow3.createCell(3).setCellValue("Wunsch");
	 * 
	 * // Daten Row dataRow4 = sheet.createRow(3);
	 * dataRow4.createCell(0).setCellValue(calcStudent.getSlots().get(0).
	 * getTyp().getZeitraum()); dataRow4.createCell(1).setCellValue(" ");
	 * dataRow4.createCell(2).setCellValue(calcStudent.getWuensche().get(0).
	 * getVeranstaltung().getUnternehmen());
	 * dataRow4.createCell(3).setCellValue(calcStudent.getWuensche().get(0).
	 * getPrio());
	 * 
	 * // eine Zeile frei lassen Row dataRow5 = sheet.createRow(4);
	 * 
	 * 
	 * // Write the workbook content to the output file workbook.write(fos);
	 * 
	 * try {
	 * UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
	 * ); } catch (ClassNotFoundException | InstantiationException |
	 * IllegalAccessException | UnsupportedLookAndFeelException e) { }
	 * 
	 * JOptionPane.showMessageDialog(null, "Erfolgreich exportiert!\n" +
	 * exportFilePath, "Export", JOptionPane.INFORMATION_MESSAGE); } catch
	 * (Exception e) { e.printStackTrace(); }
	 */

}
