package gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import klassenObjekte.*;

/**
 * 
 * @author Wagner_Eri
 *
 */
//Ein Model um ein Tabelle in der View Scrollpane Darzustellen
@SuppressWarnings("serial")
public class StudentTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = { "Klasse", "Vorname", "Nachname", "Wahl1", "Wahl2", "Wahl3", "Wahl4",
			"Wahl5", "Wahl6" };
	private List<Schueler> schülerListe;

	public StudentTableModel(List<Schueler> schüler) {
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
		Schueler student = schülerListe.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return student.getKlasse();
		case 1:
			return student.getVorname();
		case 2:
			return student.getNachname();
		case 3:
			return student.getWunsch(0);
		case 4:
			return student.getWunsch(1);
		case 5:
			return student.getWunsch(2);
		case 6:
			return student.getWunsch(3);
		case 7:
			return student.getWunsch(4);
		case 8:
			return student.getWunsch(5);
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
	}

}
