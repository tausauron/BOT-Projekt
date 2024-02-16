package gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import klassenObjekte.*;

//Eric

public class StudentTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = { "Klasse", "Vorname", "Nachname", "Wahl1", "Wahl2", "Wahl3", "Wahl4",
			"Wahl5", "Wahl6" };
	private List<schueler> schülerListe;

	public StudentTableModel(List<schueler> schüler) {
		this.schülerListe = schüler;
	}

	@Override
	public int getRowCount() {
		return schülerListe.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		schueler student = schülerListe.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return student.getKlasse();
		case 1:
			return student.getVorname();
		case 2:
			return student.getNachname();
		case 3:
			return student.getWuensche(0);
		case 4:
			return student.getWuensche(1);
		case 5:
			return student.getWuensche(2);
		case 6:
			return student.getWuensche(3);
		case 7:
			return student.getWuensche(4);
		case 8:
			return student.getWuensche(5);
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
	}

}
