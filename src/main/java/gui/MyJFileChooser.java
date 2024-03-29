package gui;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Wagner_Eri, Grassman_Dus
 *
 */

@SuppressWarnings("serial")
public class MyJFileChooser extends JFileChooser {

	/**
	 * Erhalt vom Pfad einer Excel Datei
	 * 
	 * @param frame    Für das Look and Feel
	 * @param filename "Name der Datei wie sie in der Suche angezeigt werden soll"
	 * @return Pfad der Erhalten wird bei keiner Auswahl kommt "" zurück
	 * @throws FileNotFoundException
	 */
	public static String getPathExcel(JFrame frame, String filename) throws FileNotFoundException {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel-Dateien (*.xls, *.xlsx)", "xls", "xlsx"));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File(filename));

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				if (selectedFile.getAbsolutePath().endsWith(".xlsx")) {
					return selectedFile.getAbsolutePath();
				} else {
					return selectedFile.getAbsolutePath() + ".xlsx";
				}
			} else {
				throw new FileNotFoundException("Angegebene Datei wurde nicht gefunden");
			}
		}
		return "";

	}

	/**
	 * Erhalt vom Pfad einer Ordners
	 * 
	 * @param frame    Für das Look and Feel
	 * @param filename "Name des Ordners wie sie in der Suche angezeigt werden soll"
	 * @return Pfad vom Ordner, "" wenn kein Pfad gewählt wurde
	 * @throws FileNotFoundException
	 */
	public static String getPathFolder(JFrame frame, String foldername) throws FileNotFoundException {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setSelectedFile(new File(foldername));

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = fileChooser.getSelectedFile();
			if (selectedFolder != null) {
				return selectedFolder.getAbsolutePath();
			} else {
				throw new FileNotFoundException("Angegebener Ordner wurde nicht gefunden");
			}
		}
		return "";
	}

	/**
	 * Pfad von Excel Datei mit Titel Import
	 * 
	 * @param frame    Für das Look and Feel
	 * @param filename "Name des Ordners wie sie in der Suche angezeigt werden soll"
	 * @return Pfad vom Ordner, "" wenn kein Pfad gewählt wurde
	 * @throws FileNotFoundException
	 */
	public static String getPathExcelImport(JFrame frame, String filename) throws FileNotFoundException {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Excel-Datei Importieren"); // Hier wird der Titel geändert
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel-Dateien (*.xls, *.xlsx)", "xls", "xlsx"));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File(filename));

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				if (selectedFile.getAbsolutePath().endsWith(".xlsx")) {
					return selectedFile.getAbsolutePath();
				} else {
					return selectedFile.getAbsolutePath() + ".xlsx";
				}
			} else {
				throw new FileNotFoundException("Angegebene Datei wurde nicht gefunden");
			}
		}
		return "";
	}

	/**
	 * Pfad von Excel Datei mit Titel Export
	 * 
	 * @param frame    Für das Look and Feel
	 * @param filename "Name des Ordners wie sie in der Suche angezeigt werden soll"
	 * @return Pfad vom Ordner, "" wenn kein Pfad gewählt wurde
	 * @throws FileNotFoundException
	 */
	public static String getPathExcelExport(JFrame frame, String filename) throws FileNotFoundException {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Excel-Datei Exportieren"); // Hier wird der Titel geändert
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel-Dateien (*.xls, *.xlsx)", "xls", "xlsx"));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File(filename));

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				if (selectedFile.getAbsolutePath().endsWith(".xlsx")) {
					return selectedFile.getAbsolutePath();
				} else {
					return selectedFile.getAbsolutePath() + ".xlsx";
				}
			} else {
				throw new FileNotFoundException("Angegebene Datei wurde nicht gefunden");
			}
		}
		return "";
	}

}