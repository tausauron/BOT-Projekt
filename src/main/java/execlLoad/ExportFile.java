package execlLoad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.SchuelerSlot;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

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

			Sheet sheet = workbook.createSheet("Laufzettel");

/*
			TODO: create a sheet for each class
			
			
			Set<String> uniqueKlassen = new HashSet<>();

			for (CalcSchueler calcStudent : calcStudents) {
			    String klasse = calcStudent.getSchueler().getKlasse();
			    uniqueKlassen.add(klasse);
			}

			for (String klasse : uniqueKlassen) {
			    System.out.println(klasse);
			}

*/
			
			int rowNum = 0;
			for (CalcSchueler calcStudent : calcStudents) {
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

	        // Auto-Size für jede Spalte
	        for (int i = 0; i < 5; i++) {
	            sheet.autoSizeColumn(i);
	        }
			
			// Write the workbook content to the output file
			workbook.write(fos);
		}
	}

	@Override
	public void exportRoomUsage(List<Unternehmen> unternehmen, String path) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(path)) {
			Sheet sheet = workbook.createSheet("Raumplan");
			CellStyle title = titleStyle(workbook);
			CellStyle table = tableHeadStyle(workbook);
			CellStyle tableLeft = workbook.createCellStyle();
			tableLeft.cloneStyleFrom(table);
			table.setAlignment(HorizontalAlignment.CENTER);
			tableLeft.setWrapText(true);
			CellStyle tableHead = workbook.createCellStyle();
			tableHead.cloneStyleFrom(table);
			tableHead.setAlignment(HorizontalAlignment.CENTER);
			tableHead.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			tableHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			tableHead.setBorderTop(BorderStyle.NONE);
			tableHead.setBorderBottom(BorderStyle.NONE);
			
			
			int rowNum = 0;
			Row dataRow = sheet.createRow(rowNum++);
			createCell(dataRow, 0, title).setCellValue("Organisationsplan für den Berufsorientierungstag");
			rowNum++;
			
			Cell upperLeft = null;
			Cell lowerRight = null;
			
			dataRow = sheet.createRow(rowNum++);			
			upperLeft = createCell(dataRow, 0, tableHead);
			createCell(dataRow, 1, tableHead);
			createCell(dataRow, 2, tableHead).setCellValue(Typ.A.getZeitraum());
			createCell(dataRow, 3, tableHead).setCellValue(Typ.B.getZeitraum());
			createCell(dataRow, 4, tableHead).setCellValue(Typ.C.getZeitraum());
			createCell(dataRow, 5, tableHead).setCellValue(Typ.D.getZeitraum());
			createCell(dataRow, 6, tableHead).setCellValue(Typ.E.getZeitraum());
			
			dataRow = sheet.createRow(rowNum++);
			createCell(dataRow, 0, tableHead);
			createCell(dataRow, 1, tableHead);
			createCell(dataRow, 2, tableHead).setCellValue(Typ.A.name());
			createCell(dataRow, 3, tableHead).setCellValue(Typ.B.name());
			createCell(dataRow, 4, tableHead).setCellValue(Typ.C.name());
			createCell(dataRow, 5, tableHead).setCellValue(Typ.D.name());
			createCell(dataRow, 6, tableHead).setCellValue(Typ.E.name());
			
			for(Unternehmen unt : unternehmen) {
				dataRow = sheet.createRow(rowNum++);
				createCell(dataRow, 0, tableLeft).setCellValue(unt.getFirmenID());
				createCell(dataRow, 1, tableLeft).setCellValue(unt.getUnternehmen());
				createCell(dataRow, 2, table).setCellValue(unt.getKurse().get(Typ.A) != null ? unt.getKurse().get(Typ.A).getRaum().getName(): "");
				createCell(dataRow, 3, table).setCellValue(unt.getKurse().get(Typ.B) != null ? unt.getKurse().get(Typ.B).getRaum().getName(): "");
				createCell(dataRow, 4, table).setCellValue(unt.getKurse().get(Typ.C) != null ? unt.getKurse().get(Typ.C).getRaum().getName(): "");
				createCell(dataRow, 5, table).setCellValue(unt.getKurse().get(Typ.D) != null ? unt.getKurse().get(Typ.D).getRaum().getName(): "");
				lowerRight = createCell(dataRow, 6, table);
				lowerRight.setCellValue(unt.getKurse().get(Typ.E) != null ? unt.getKurse().get(Typ.E).getRaum().getName(): "");
			}
			
			fatBorder(upperLeft, sheet.getRow(--rowNum).getCell(1), sheet);
			fatBorder(upperLeft, lowerRight, sheet);
						
			sheet.setColumnWidth(0, 3 * 256);
			sheet.setColumnWidth(1, 30 * 256);
	        // Auto-Size für jede Spalte
	        for (int i = 2; i <= 6; i++) {
	            sheet.autoSizeColumn(i);
	        }
			
			
			workbook.write(fos);			
		}
	}

	@Override
	public void exportParticipants(List<Unternehmen> unternehmen, String path) throws IOException {
		
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(path)) {
			CellStyle title = titleStyle(workbook);
			CellStyle table = tableStyle(workbook);
			CellStyle tableHead = tableHeadStyle(workbook);
			
			Row dataRow = null;


			for(Unternehmen unt : unternehmen) {
				Sheet sheet = workbook.createSheet(unt.getFirmenID() + " " +  unt.getUnternehmen());
				int rowNum = 0;
				dataRow = sheet.createRow(rowNum++);
				createCell(dataRow, 0, title).setCellValue(unt.getUnternehmen());;
				dataRow = sheet.createRow(rowNum++);
				createCell(dataRow, 0, null).setCellValue(unt.getFachrichtung());;
				dataRow = sheet.createRow(rowNum++);
				
				List<Kurse> kurse = new ArrayList<>(unt.getKurse().values());
				kurse.sort(bySlotType);				
				
				for(Kurse kurs : kurse) {
					Cell upperLeft;
					Cell lowerRight = createCell(dataRow, rowNum, null);
					
					dataRow = sheet.createRow(rowNum++);
					upperLeft = createCell(dataRow, 1, tableHead);
					upperLeft.setCellValue("Raum");
					createCell(dataRow, 2, tableHead).setCellValue(kurs.getRaum().getName());
					dataRow = sheet.createRow(rowNum++);
					createCell(dataRow, 1, tableHead).setCellValue("Zeit");
					lowerRight = createCell(dataRow, 2, tableHead);
					lowerRight.setCellValue(kurs.getZeitslot().getTyp().getZeitraum());
					fatBorder(upperLeft, lowerRight, sheet);
					
					dataRow = sheet.createRow(rowNum++);
					upperLeft = createCell(dataRow, 1, tableHead);
					upperLeft.setCellValue("Klasse");
					createCell(dataRow, 2, tableHead).setCellValue("Name");
					createCell(dataRow, 3, tableHead).setCellValue("Vorname");
					createCell(dataRow, 4, tableHead).setCellValue("Anwesend?");
										
					for(CalcSchueler cSchuel : kurs.getKursTeilnehmer()) {
						dataRow = sheet.createRow(rowNum++);
						createCell(dataRow, 1, table).setCellValue(cSchuel.getSchueler().getKlasse());
						createCell(dataRow, 2, table).setCellValue(cSchuel.getSchueler().getNachname());
						createCell(dataRow, 3, table).setCellValue(cSchuel.getSchueler().getVorname());
						lowerRight = createCell(dataRow, 4, table);
					}
					
					fatBorder(upperLeft, lowerRight, sheet);
					
					dataRow = sheet.createRow(rowNum++);
			        // Auto-Size für jede Spalte
			        for (int i = 1; i < 5; i++) {
			            sheet.autoSizeColumn(i);
			        }
				}
			}
			workbook.write(fos);
		}
	}
	
	
	private Cell createCell(Row row, int col ,CellStyle style) {
		Cell cell = row.createCell(col);
		if(style != null) {
			cell.setCellStyle(style);
		}
		return cell;
	}
	
	private void fatBorder(Cell upperLeft, Cell lowerRight, Sheet sheet) {
		CellRangeAddress region = CellRangeAddress.valueOf(upperLeft.getAddress().formatAsString() + ":" + 
															lowerRight.getAddress().formatAsString());
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
	}
	
	private CellStyle tableStyle(Workbook wb) {
		CellStyle retVal = wb.createCellStyle();
		
		retVal.setBorderBottom(BorderStyle.THIN);
		retVal.setBorderLeft(BorderStyle.THIN);
		retVal.setBorderTop(BorderStyle.THIN);
		retVal.setBorderRight(BorderStyle.THIN);
		
		return retVal;		
	}
	
	private CellStyle tableHeadStyle(Workbook wb) {
		CellStyle retVal = wb.createCellStyle();
		
		retVal.cloneStyleFrom(tableStyle(wb));
		Font tableHeadFont = wb.createFont();
		tableHeadFont.setBold(true);
		retVal.setFont(tableHeadFont);
		
		return retVal;
	}
	
	private CellStyle titleStyle(Workbook wb) {
		CellStyle retVal = wb.createCellStyle();
		
		Font titleFont = wb.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 20);
		retVal.setFont(titleFont);
		
		return retVal;		
	}
	
	
	Comparator<Kurse> bySlotType = new Comparator<Kurse>() {
		
		@Override
		public int compare(Kurse o1, Kurse o2) {
			
			return Integer.compare(o1.getZeitslot().getTyp().ordinal(), o2.getZeitslot().getTyp().ordinal());
		}
	};

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
