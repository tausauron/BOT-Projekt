package gui;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import klassenObjekte.*;

//Eric
//Ein Model um ein Tabelle in der View Scrollpane Darzustellen
public class UnternehmenTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = { "Firma", "Fachrichtung","Max Teilnehmer","Max Veranstaltungen"};
	private List<Unternehmen> unternehmenListe;

	public UnternehmenTableModel(List<Unternehmen> schüler) {
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
		Unternehmen student = unternehmenListe.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return student.getUnternehmen();
		case 1:
			return student.getFachrichtung();
		case 2:
			return student.getMaxTeilnehmer();
		case 3:
			return student.getMaxVeranstaltungen();
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
	}

}
