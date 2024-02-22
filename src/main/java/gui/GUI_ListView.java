package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableModel;

import klassenObjekte.*;

import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import klassenObjekte.Schueler;

//Eric

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

	/**
	 * Create the application.
	 */

	public GUI_ListView(List<Schueler> schüler, List<Unternehmen> unternehmen, List<Raum> räume) {

		this.schülerList = schüler;
		this.unternehmenList = unternehmen;
		this.raumList = räume;
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

		// Model für Schüler GUI wird erstellt
		schülerListModel = new StudentTableModel(schülerList);
		JTable tableSchüler = new JTable(schülerListModel);

		scrollPaneSchüler = new JScrollPane(tableSchüler);

		scrollPaneSchüler.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelSchüler = new GroupLayout(panelSchüler);
		gl_panelSchüler.setHorizontalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchüler.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panelSchüler.createSequentialGroup()
										.addComponent(btnSchülerhinzufügen, GroupLayout.PREFERRED_SIZE, 100,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
										.addComponent(btnSchülerImportieren).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSchülerExportieren))
								.addGroup(gl_panelSchüler.createSequentialGroup()
										.addComponent(scrollPaneSchüler, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
										.addGap(3)))
						.addContainerGap()));
		gl_panelSchüler.setVerticalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING).addGroup(gl_panelSchüler
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelSchüler.createParallelGroup(Alignment.BASELINE).addComponent(btnSchülerhinzufügen)
						.addComponent(btnSchülerExportieren).addComponent(btnSchülerImportieren))
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

		// Model für Unternehmen GUI wird erstellt
		unterNehmenListModel = new UnternehmenTableModel(unternehmenList);
		JTable tableUn = new JTable(unterNehmenListModel);

		scrollPaneUnternehmen = new JScrollPane(tableUn);

		scrollPaneUnternehmen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelUnternehmen = new GroupLayout(panelUnternehmen);
		gl_panelUnternehmen.setHorizontalGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelUnternehmen.createSequentialGroup()
										.addComponent(btnUnternehmenhinzufügen, GroupLayout.PREFERRED_SIZE, 100,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
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
								.addComponent(btnUnternehmenhinzufügen))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneUnternehmen, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
						.addGap(19)));
		panelUnternehmen.setLayout(gl_panelUnternehmen);

		// Räume

		JPanel panelRaum = new JPanel();

		tabbedPane.addTab("Räume", null, panelRaum, null);

		JButton btnRaumhinzufügen = new JButton("Hinzufügen");

		JButton btnRaumImportieren = new JButton("Importieren");

		JButton btnRaumExportieren = new JButton("Exportieren");

		raumListModel = new RaumTableModel(raumList);
		JTable tableRaum = new JTable(raumListModel);

		scrollPaneRaum = new JScrollPane(tableRaum);

		scrollPaneRaum.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelRaum = new GroupLayout(panelRaum);
		gl_panelRaum.setHorizontalGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING)
				.addGap(0, 731, Short.MAX_VALUE)
				.addGroup(gl_panelRaum.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelRaum.createSequentialGroup()
										.addComponent(btnRaumhinzufügen, GroupLayout.PREFERRED_SIZE, 100,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
										.addComponent(btnRaumImportieren).addGap(6).addComponent(btnRaumExportieren))
								.addGroup(gl_panelRaum.createSequentialGroup()
										.addComponent(scrollPaneRaum, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
										.addGap(3)))
						.addContainerGap()));
		gl_panelRaum.setVerticalGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING)
				.addGap(0, 439, Short.MAX_VALUE)
				.addGroup(gl_panelRaum.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelRaum.createParallelGroup(Alignment.LEADING).addComponent(btnRaumImportieren)
								.addComponent(btnRaumExportieren).addComponent(btnRaumhinzufügen))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneRaum, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE).addGap(19)));
		panelRaum.setLayout(gl_panelRaum);
	}

	private void btnPressedUnternehmenExportieren() {

	}

	private void btnPressedUnternehmenImportieren() {
		ArrayList<Unternehmen> unList = new ArrayList<>();
		
		unList.add(new Unternehmen(0, "Firma", "Fach", 1, 1, "A"));
		for (Unternehmen unternehmen : unList) {
			addUnternehmenToList(unternehmen);
		}
	}

	private void btnPressedUnternehmenhinzufügen() {

		addUnternehmenToList(new Unternehmen(0, "Firma", "Fach", 1, 1, "A"));

	}

	private void btnPressedSchülerExportieren() {

	}

	private void btnPressedSchülerImportieren() {

		for (Schueler schueler : schülerList) {

		}
	}

	private void btnPressedSchülerhinzufügen() {
		ArrayList<String> x = new ArrayList<String>();
		x.add("1");
		x.add("2");
		x.add("3");
		x.add("4");
		x.add("5");
		x.add("6");

		Schueler newSchüler = new Schueler("Klasse", "Vor", "Nach", x);
		addSchülerToList(newSchüler);
	}

	private void addSchülerToList(Schueler newSchüler) {

		schülerList.add(newSchüler);

		TableModel modelSchüler = new StudentTableModel(schülerList);
		JTable tableSchüler = new JTable(modelSchüler);

		scrollPaneSchüler.setViewportView(tableSchüler);
	}

	private void addUnternehmenToList(Unternehmen newUnternehmen) {
		unternehmenList.add(newUnternehmen);

		TableModel unTableModel = new UnternehmenTableModel(unternehmenList);
		JTable tableUnternehmen = new JTable(unTableModel);

		scrollPaneUnternehmen.setViewportView(tableUnternehmen);
	}
}
