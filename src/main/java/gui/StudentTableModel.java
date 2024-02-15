package gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"Klasse", "Name", "Last Name", "Wahl1", "Wahl2"};
    private List<Schüler> schüler;

    public StudentTableModel(List<Schüler> schüler) {
        this.schüler = schüler;
    }

    @Override
    public int getRowCount() {
        return schüler.size();
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
        Schüler student = schüler.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return student.getKlasse();
            case 1:
                return student.getName();
            case 2:
                return student.getNachname();
            case 3:
                return student.getWahl1();
            case 4:
                return student.getWahl2();
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
    }

}
