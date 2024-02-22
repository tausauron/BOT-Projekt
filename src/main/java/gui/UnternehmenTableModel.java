package gui;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import klassenObjekte.*;

//Eric
//Ein Model um ein Tabelle in der View Scrollpane Darzustellen
public class UnternehmenTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = { "Firma", "Firma_ID"};
	private List<unternehmen> unternehmenListe;

	public UnternehmenTableModel(List<unternehmen> schüler) {
		this.unternehmenListe = schüler;
	}

	@Override
	public int getRowCount() {
		return unternehmenListe.size();
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
		unternehmen student = unternehmenListe.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return student.getFirmenName();
		case 1:
			return student.getFirmenID();
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
	}

}
