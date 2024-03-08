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
public class RaumTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = { "Name", "Kapazität" };
	private List<Raum> raumListe;

	public RaumTableModel(List<Raum> räume) {
		this.raumListe = räume;
	}

	@Override
	public int getRowCount() {
		return raumListe.size();
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
		Raum raum = raumListe.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return raum.getName();
		case 1:
			return raum.getKapazitaet();
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
	}

}
