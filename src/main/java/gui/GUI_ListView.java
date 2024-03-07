package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableModel;
import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
import klassenObjekte.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Wagner_Eri
 *
 */

public class GUI_ListView {

	private JFrame frame;
	private JScrollPane scrollPaneSchüler;
	private JScrollPane scrollPaneUnternehmen;
	private JScrollPane scrollPaneRaum;

	private JTabbedPane tabbedPane;
	private TableModel schülerListModel;
	private TableModel unterNehmenListModel;
	private TableModel raumListModel;
	private List<Schueler> schülerList;
	private List<Unternehmen> unternehmenList;
	private List<Raum> raumList;
	private MyController myController;

	private JTable tableSchüler;
	private JTable tableUn;
	private JTable tableRaum;

	/**
	 * Create the application.
	 */

	/*
	 * public GUI_ListView(List<Schueler> schüler, List<Unternehmen> unternehmen,
	 * List<Raum> räume, MyController myController) {
	 * 
	 * this.schülerList = schüler; this.unternehmenList = unternehmen; this.raumList
	 * = räume; initialize(); this.frame.setVisible(true); }
	 */

	public GUI_ListView(MyController myController) {
		this.myController = myController;
		this.schülerList = new ArrayList<Schueler>();
		this.unternehmenList = new ArrayList<Unternehmen>();
		this.raumList = new ArrayList<Raum>();
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fensterSchließen(schülerList, unternehmenList, raumList);
			}
		});
		frame.setTitle("BOT");

		frame.setBounds(100, 100, 752, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelSchüler = new JPanel();
		tabbedPane.addTab("Schüler", null, panelSchüler, null);

		JButton btnSchülerhinzufügen = new JButton("Hinzufügen");
		btnSchülerhinzufügen.addActionListener((e) -> btnPressedSchülerhinzufügen());

		JButton btnSchülerImportieren = new JButton("Importieren");
		btnSchülerImportieren.addActionListener((e) -> btnPressedSchülerImportieren());

		JButton btnSchülerExportieren = new JButton("Exportieren");
		btnSchülerExportieren.addActionListener((e) -> btnPressedSchülerExportieren());

		JButton btnSchülerDelete = new JButton("Löschen");
		btnSchülerDelete.addActionListener((e) -> btnPressedSchülerLöschen(e));

		// Schüler GUI
		schülerListModel = new StudentTableModel(schülerList);

		tableSchüler = new JTable(schülerListModel);
		tableSchüler.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(tableSchüler.getSelectedRow());
			}
		});

		scrollPaneSchüler = new JScrollPane(tableSchüler);

		scrollPaneSchüler.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		GroupLayout gl_panelSchüler = new GroupLayout(panelSchüler);
		gl_panelSchüler.setHorizontalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchüler.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelSchüler.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelSchüler.createSequentialGroup()
										.addComponent(btnSchülerhinzufügen, GroupLayout.PREFERRED_SIZE, 100,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSchülerDelete)
										.addPreferredGap(ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
										.addComponent(btnSchülerImportieren).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSchülerExportieren))
								.addGroup(gl_panelSchüler.createSequentialGroup()
										.addComponent(scrollPaneSchüler, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
										.addGap(3)))
						.addContainerGap()));
		gl_panelSchüler.setVerticalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchüler.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelSchüler.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSchülerhinzufügen).addComponent(btnSchülerExportieren)
								.addComponent(btnSchülerImportieren).addComponent(btnSchülerDelete))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneSchüler, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE).addGap(19)));
		panelSchüler.setLayout(gl_panelSchüler);

		JPanel panelUnternehmen = new JPanel();
		tabbedPane.addTab("Unternehmen", null, panelUnternehmen, null);

		JButton btnUnternehmenhinzufügen = new JButton("Hinzufügen");
		btnUnternehmenhinzufügen.addActionListener((e) -> btnPressedUnternehmenhinzufügen());

		JButton btnUnternehmenImportieren = new JButton("Importieren");
		btnUnternehmenImportieren.addActionListener((e) -> btnPressedUnternehmenImportieren());

		JButton btnUnternehmenExportieren = new JButton("Exportieren");
		btnUnternehmenExportieren.addActionListener((e) -> btnPressedUnternehmenExportieren());

		// Unternehmen GUI
		unterNehmenListModel = new UnternehmenTableModel(unternehmenList);
		tableUn = new JTable(unterNehmenListModel);

		scrollPaneUnternehmen = new JScrollPane(tableUn);

		scrollPaneUnternehmen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnUnternehmenDelete = new JButton("Löschen");
		btnUnternehmenDelete.addActionListener((e) -> btnPressedUnternehmeLöschen());
		GroupLayout gl_panelUnternehmen = new GroupLayout(panelUnternehmen);
		gl_panelUnternehmen.setHorizontalGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelUnternehmen.createSequentialGroup()
										.addComponent(btnUnternehmenhinzufügen, GroupLayout.PREFERRED_SIZE, 100,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnUnternehmenDelete, GroupLayout.PREFERRED_SIZE, 71,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
										.addComponent(btnUnternehmenImportieren).addGap(6)
										.addComponent(btnUnternehmenExportieren))
								.addGroup(
										gl_panelUnternehmen.createSequentialGroup().addComponent(scrollPaneUnternehmen,
												GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE).addGap(3)))
						.addContainerGap()));
		gl_panelUnternehmen.setVerticalGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUnternehmenImportieren).addComponent(btnUnternehmenExportieren)
								.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnUnternehmenhinzufügen).addComponent(btnUnternehmenDelete)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneUnternehmen, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
						.addGap(19)));
		panelUnternehmen.setLayout(gl_panelUnternehmen);

		// Räume GUI

		JPanel panelRaum = new JPanel();

		tabbedPane.addTab("Räume", null, panelRaum, null);

		JButton btnRaumhinzufügen = new JButton("Hinzufügen");
		btnRaumhinzufügen.addActionListener((e) -> btnPressedRaumHinzufügen());

		JButton btnRaumImportieren = new JButton("Importieren");

		JButton btnRaumExportieren = new JButton("Exportieren");

		raumListModel = new RaumTableModel(raumList);
		tableRaum = new JTable(raumListModel);

		scrollPaneRaum = new JScrollPane(tableRaum);

		scrollPaneRaum.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnRaumLöschen = new JButton("Löschen");
		btnRaumLöschen.addActionListener((e) -> btnPressedRaumLöschen());
		GroupLayout gl_panelRaum = new GroupLayout(panelRaum);
		gl_panelRaum.setHorizontalGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING).addGroup(gl_panelRaum
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING).addGroup(gl_panelRaum
						.createSequentialGroup()
						.addComponent(btnRaumhinzufügen, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnRaumLöschen, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
						.addComponent(btnRaumImportieren).addGap(6).addComponent(btnRaumExportieren))
						.addGroup(gl_panelRaum.createSequentialGroup()
								.addComponent(scrollPaneRaum, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
								.addGap(3)))
				.addContainerGap()));
		gl_panelRaum.setVerticalGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRaum.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING).addComponent(btnRaumImportieren)
								.addComponent(btnRaumExportieren)
								.addGroup(gl_panelRaum.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnRaumhinzufügen).addComponent(btnRaumLöschen)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneRaum, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE).addGap(19)));
		panelRaum.setLayout(gl_panelRaum);

		Image ui_Logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ui_logo.jpg"));
		frame.setIconImage(ui_Logo);
	}

	// Button Hinzufügen
	private void btnPressedSchülerhinzufügen() {
		// TODO
		addSchülerToList(new Schueler("Klasse", "Vorname", "Nachname", new ArrayList<>()));
	}

	private void btnPressedUnternehmenhinzufügen() {
		// TODO

		addUnternehmenToList(new Unternehmen(0, "Firma", "Fach", 10, 5, "A"));
	}

	private void btnPressedRaumHinzufügen() {
		// TODO

		addRaumToList(new Raum("Test", 0));
	}

	// Button die den Index Ermitteln und dann Löschen
	private void btnPressedSchülerLöschen(ActionEvent e) {
		if (tableSchüler.getSelectedRow() != -1) { // Check if a row is selected
			int selectedRow = tableSchüler.getSelectedRow();
			removeOnPositionSchüler(selectedRow);
		} else {
			JOptionPane.showMessageDialog(frame, "Bitte wählen Sie einen Schüler aus.", "Keine Auswahl",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void btnPressedUnternehmeLöschen() {
		if (tableUn.getSelectedRow() != -1) { // Check if a row is selected
			int selectedRow = tableUn.getSelectedRow();
			removeOnPositionUnternehmen(selectedRow);
		} else {
			JOptionPane.showMessageDialog(frame, "Bitte wählen Sie ein Unternehmen aus.", "Keine Auswahl",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void btnPressedRaumLöschen() {
		if (tableRaum.getSelectedRow() != -1) { // Check if a row is selected
			int selectedRow = tableRaum.getSelectedRow();
			removeOnPositionRaum(selectedRow);
		} else {
			JOptionPane.showMessageDialog(frame, "Bitte wählen Sie einen Raum aus.", "Keine Auswahl",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// Löschen Remover und Refresher
	private void removeOnPositionSchüler(int selectedRow) {
		schülerList.remove(selectedRow);
		refreshSchüler();
	}

	private void removeOnPositionUnternehmen(int selectedRow) {
		unternehmenList.remove(selectedRow);
		refreshUnternehmen();
	}

	private void removeOnPositionRaum(int selectedRow) {
		raumList.remove(selectedRow);
		refreshRaum();
	}

	// Import Buttons
	private void btnPressedSchülerImportieren() {
		schülerList.clear();
		List<Schueler> importedStudents = myController.importStudent(this.frame);
		for (Schueler schüler : importedStudents) {
			addSchülerToList(schüler);
		}
	}

	private void btnPressedUnternehmenImportieren() {
		unternehmenList.clear();
		List<Unternehmen> importUntList = myController.importCompany(this.frame);

		for (Unternehmen unternehmen : importUntList) {
			addUnternehmenToList(unternehmen);
		}
	}

	private void btnPressedRaumImportieren() {
		raumList.clear();
		List<Raum> importedRaumList = myController.importRooms(this.frame);
		for (Raum raum : importedRaumList) {
			addRaumToList(raum);
		}
	}

	// Export Buttons
	private void btnPressedSchülerExportieren() {

		myController.exportStudent(schülerList, this.frame);
	}

	private void btnPressedUnternehmenExportieren() {

		myController.exportCompany(unternehmenList, this.frame);
	}

	private void btnPressedRaumExportieren() {

		myController.exportRooms(raumList, this.frame);
	}

	// Methode to add "" to List
	private void addSchülerToList(Schueler newSchüler) {

		schülerList.add(newSchüler);

		refreshSchüler();
	}

	private void addUnternehmenToList(Unternehmen newUnternehmen) {
		unternehmenList.add(newUnternehmen);

		refreshUnternehmen();
	}

	private void addRaumToList(Raum newRaum) {

		raumList.add(newRaum);

		refreshRaum();
	}

	// Refresh Methoden
	private void refreshSchüler() {
		TableModel modelSchüler = new StudentTableModel(schülerList);
		tableSchüler = new JTable(modelSchüler);

		scrollPaneSchüler.setViewportView(tableSchüler);
	}

	private void refreshUnternehmen() {
		TableModel unTableModel = new UnternehmenTableModel(unternehmenList);
		tableUn = new JTable(unTableModel);

		scrollPaneUnternehmen.setViewportView(tableUn);
	}

	private void refreshRaum() {
		TableModel modelRaum = new RaumTableModel(raumList);
		tableRaum = new JTable(modelRaum);

		scrollPaneRaum.setViewportView(tableRaum);
	}

	// On Close Methode
	protected void fensterSchließen(List<Schueler> schülerList, List<Unternehmen> unternehmenList,
			List<Raum> raumList) {
		speichernDerDatenbeimSchließen(schülerList, unternehmenList, raumList);
	}

	private void speichernDerDatenbeimSchließen(List<Schueler> schülerList, List<Unternehmen> unternehmenList,
			List<Raum> raumList) {

		int confirmation = JOptionPane.showConfirmDialog(null, "Möchten Sie die Daten vor dem Schließen speichern?",
				"Bestätigung", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirmation == JOptionPane.YES_OPTION) {
			myController.closeListView(schülerList, raumList, unternehmenList);
		} else {

		}

	}
}
