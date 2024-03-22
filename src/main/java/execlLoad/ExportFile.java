package execlLoad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
	
	@Override
	public void exportStudentData(List<Schueler> studentList, String exportFilePath) {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(exportFilePath)) {

			Sheet sheet = workbook.createSheet("Schueler");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Klasse");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Vorname");
			headerRow.createCell(3).setCellValue("Wahl 1");
			headerRow.createCell(4).setCellValue("Wahl 2");
			headerRow.createCell(5).setCellValue("Wahl 3");
			headerRow.createCell(6).setCellValue("Wahl 4");
			headerRow.createCell(7).setCellValue("Wahl 5");
			headerRow.createCell(8).setCellValue("Wahl 6");

			// Fill data rows
			int rowNum = 1;
			for (Schueler schueler : studentList) {
				Row dataRow = sheet.createRow(rowNum++);
				dataRow.createCell(0).setCellValue(schueler.getKlasse());
				dataRow.createCell(1).setCellValue(schueler.getNachname());
				dataRow.createCell(2).setCellValue(schueler.getVorname());

				List<String> wunschliste = schueler.getAllWuensche();
				for (int i = 0; i < wunschliste.size(); i++) {
					if (!wunschliste.get(i).equals(""))
						dataRow.createCell(3 + i).setCellValue(Integer.valueOf(wunschliste.get(i)));
				}
			}

			// Write the workbook content to the output file
			workbook.write(fos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
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

	@Override
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

	@Override
	public void exportStudentSchedule(List<CalcSchueler> calcStudents, String exportFilePath)
			throws FileNotFoundException, IOException {
		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(exportFilePath)) {
			CellStyle title = titleStyle(workbook);
			CellStyle table = tableStyle(workbook);
			CellStyle tableHead = tableHeadStyle(workbook);
			CellStyle event = workbook.createCellStyle();
			event.cloneStyleFrom(table);
			event.setWrapText(true);

			Map<String, List<CalcSchueler>> klassenMap = new HashMap<>();
			// Iteriere durch die calcStudents und fülle die klassenMap
			for (CalcSchueler calcStudent : calcStudents) {
				String klasse = calcStudent.getSchueler().getKlasse().toUpperCase();
				// Überprüfen, ob die Klasse bereits in der Map vorhanden ist
				if (!klassenMap.containsKey(klasse)) {
					klassenMap.put(klasse, new ArrayList<>());
				}

				// Die Schüler der Klasse hinzufügen
				klassenMap.get(klasse).add(calcStudent);
			}

			// Sortiere die Klassen
			List<String> sortedKlassen = new ArrayList<>(klassenMap.keySet());
			Collections.sort(sortedKlassen);

			Row dataRow = null;
			Cell upperLeft = null;
			Cell lowerRight = null;

			for (String klasse : sortedKlassen) {
				List<CalcSchueler> calcStudentsByClass = klassenMap.get(klasse);
				Sheet sheet = workbook.createSheet(klasse);

				dataRow = sheet.createRow(0);
				createCell(dataRow, 0, title).setCellValue(klasse);

				int rowNum = 2;
				for (CalcSchueler calcStudent : calcStudentsByClass) {
					// Name
					dataRow = sheet.createRow(rowNum);
					rowNum++;
					dataRow.createCell(0).setCellValue(
							calcStudent.getSchueler().getNachname() + ", " + calcStudent.getSchueler().getVorname());

					// Überschriften
					dataRow = sheet.createRow(rowNum);
					rowNum++;
					upperLeft = createCell(dataRow, 0, tableHead);
					dataRow.getCell(0).setCellValue("Zeit");
					createCell(dataRow, 1, tableHead).setCellValue("Raum");
					createCell(dataRow, 2, tableHead).setCellValue("Veranstaltung");
					createCell(dataRow, 3, tableHead).setCellValue("");
					createCell(dataRow, 4, tableHead).setCellValue("Wahl");

					for (SchuelerSlot slot : calcStudent.getSlots()) {
						// Daten
						dataRow = sheet.createRow(rowNum);
						rowNum++;
						Typ t = slot.getTyp();

						createCell(dataRow, 0, table).setCellValue(t.getZeitraum());
						if (slot.getKurs() != null) {
							if (slot.getKurs().getRaum() != null) {
								createCell(dataRow, 1, table).setCellValue(slot.getKurs().getRaum().getName());
							} else {
								createCell(dataRow, 1, table).setCellValue(" - ");
							}
						} else {
							createCell(dataRow, 1, table).setCellValue(" - ");
						}

						lowerRight = createCell(dataRow, 4, null);

						if (slot.getErfuellterWunsch() != null
								&& slot.getErfuellterWunsch().getVeranstaltung() != null) {
							createCell(dataRow, 2, table)
									.setCellValue(slot.getErfuellterWunsch().getVeranstaltung().getUnternehmen());
							createCell(dataRow, 3, event)
									.setCellValue(slot.getErfuellterWunsch().getVeranstaltung().getFachrichtung());
							createCell(dataRow, 4, table).setCellValue(slot.getErfuellterWunsch().getNummer());
						} else {
							createCell(dataRow, 2, table).setCellValue(" - ");
							createCell(dataRow, 3, event).setCellValue(" - ");
							createCell(dataRow, 4, table).setCellValue(" - ");
						}
					}

					// Eine Zeile frei lassen
					rowNum++;
					rowNum++;
					fatBorder(upperLeft, lowerRight, sheet);
				}

				// Auto-Size für jede Spalte
				for (int i = 0; i < 5; i++) {
					sheet.autoSizeColumn(i);
				}
				// 30 Zeichen in der breite
				sheet.setColumnWidth(3, 30 * 256);
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

			for (Unternehmen unt : unternehmen) {
				dataRow = sheet.createRow(rowNum++);
				createCell(dataRow, 0, tableLeft).setCellValue(unt.getFirmenID());
				createCell(dataRow, 1, tableLeft).setCellValue(unt.getUnternehmen());
				createCell(dataRow, 2, table).setCellValue(
						unt.getKurse().get(Typ.A) != null ? unt.getKurse().get(Typ.A).getRaum().getName() : "");
				createCell(dataRow, 3, table).setCellValue(
						unt.getKurse().get(Typ.B) != null ? unt.getKurse().get(Typ.B).getRaum().getName() : "");
				createCell(dataRow, 4, table).setCellValue(
						unt.getKurse().get(Typ.C) != null ? unt.getKurse().get(Typ.C).getRaum().getName() : "");
				createCell(dataRow, 5, table).setCellValue(
						unt.getKurse().get(Typ.D) != null ? unt.getKurse().get(Typ.D).getRaum().getName() : "");
				lowerRight = createCell(dataRow, 6, table);
				lowerRight.setCellValue(
						unt.getKurse().get(Typ.E) != null ? unt.getKurse().get(Typ.E).getRaum().getName() : "");
			}

			fatBorder(upperLeft, sheet.getRow(--rowNum).getCell(1), sheet);
			fatBorder(upperLeft, lowerRight, sheet);

			// Spaltenbreite 3 Zeichen
			sheet.setColumnWidth(0, 3 * 256);
			// Spaltenbreite 30 Zeichen
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

			for (Unternehmen unt : unternehmen) {
				Sheet sheet = workbook.createSheet(unt.getFirmenID() + " " + unt.getUnternehmen());
				int rowNum = 0;
				dataRow = sheet.createRow(rowNum++);
				createCell(dataRow, 0, title).setCellValue(unt.getUnternehmen());
				dataRow = sheet.createRow(rowNum++);
				createCell(dataRow, 0, null).setCellValue(unt.getFachrichtung());
				dataRow = sheet.createRow(rowNum++);

				List<Kurse> kurse = new ArrayList<>(unt.getKurse().values());
				kurse.sort(bySlotType);

				for (Kurse kurs : kurse) {
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

					for (CalcSchueler cSchuel : kurs.getKursTeilnehmer()) {
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

	/**
	 * erzeugt eine neue Zelle in der uebergebenen Zeile
	 * 
	 * @param row   Zeile
	 * @param col   Spalte
	 * @param style Zellenformatierung
	 * @return erzeugte Zelle
	 */
	private Cell createCell(Row row, int col, CellStyle style) {
		Cell cell = row.createCell(col);
		if (style != null) {
			cell.setCellStyle(style);
		}
		return cell;
	}

	/**
	 * umrahmt einen Bereich auf dem Arbeitsblatt mit einem fetten Rahmen
	 * 
	 * @param upperLeft  obere linke Zelle des Bereichs
	 * @param lowerRight untere rechte Zelle des Bereichs
	 * @param sheet      Arbeitsblatt mit dem Bereich
	 */
	private void fatBorder(Cell upperLeft, Cell lowerRight, Sheet sheet) {
		CellRangeAddress region = CellRangeAddress
				.valueOf(upperLeft.getAddress().formatAsString() + ":" + lowerRight.getAddress().formatAsString());
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
	}

	/**
	 * erzeugt die Zellenformatierung fuer eine Zelle in einer Tabelle mit duennen
	 * Rahmen
	 * 
	 * @param wb Arbeitsmappe fuer die Formatierung
	 * @return Zellenformatierung
	 */
	private CellStyle tableStyle(Workbook wb) {
		CellStyle retVal = wb.createCellStyle();

		retVal.setBorderBottom(BorderStyle.THIN);
		retVal.setBorderLeft(BorderStyle.THIN);
		retVal.setBorderTop(BorderStyle.THIN);
		retVal.setBorderRight(BorderStyle.THIN);

		return retVal;
	}

	/**
	 * erzeugt die Zellenformatierung fuer einen Tabellenkopf mit fetter Schrift
	 * 
	 * @param wb Arbeitsmappe fuer die Formatierung
	 * @return Zellenformatierung
	 */
	private CellStyle tableHeadStyle(Workbook wb) {
		CellStyle retVal = wb.createCellStyle();

		retVal.cloneStyleFrom(tableStyle(wb));
		Font tableHeadFont = wb.createFont();
		tableHeadFont.setBold(true);
		retVal.setFont(tableHeadFont);

		return retVal;
	}

	/**
	 * erzeugt die Zellenformatierung fuer die Ueberschrift eines Arbeitsblatts
	 * 
	 * @param wb Arbeitsmappe fuer die Formatierung
	 * @return Zellenformatierung
	 */
	private CellStyle titleStyle(Workbook wb) {
		CellStyle retVal = wb.createCellStyle();

		Font titleFont = wb.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 20);
		retVal.setFont(titleFont);

		return retVal;
	}

	/**
	 * sortiert die Kurse in einer Liste nach der zeitlichen Reihenfolge ihrer
	 * Zeitslots
	 */
	Comparator<Kurse> bySlotType = new Comparator<Kurse>() {

		@Override
		public int compare(Kurse o1, Kurse o2) {

			return Integer.compare(o1.getZeitslot().getTyp().ordinal(), o2.getZeitslot().getTyp().ordinal());
		}
	};
}