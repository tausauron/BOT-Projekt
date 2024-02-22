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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableModel;

import klassenObjekte.Schueler;

//Eric

public class GUI_ListView {

	private JFrame frame;
	private JScrollPane scrollPaneSchüler;
	private JScrollPane scrollPaneUnternehmen;
	private JTabbedPane tabbedPane;
	private TableModel schülerListModel;
	private TableModel unterNehmenListModel;
	private List<Schueler> schülerList;
	private List<Schueler> unternehmenList;

	/**
	 * Create the application.
	 */

	public GUI_ListView(List<Schueler> schüler, List<Schueler> unternehmen) {

		this.schülerList = schüler;
		this.unternehmenList = unternehmen;
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
		unterNehmenListModel = new StudentTableModel(unternehmenList);
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
	}

	private void btnPressedUnternehmenExportieren() {

	}

	private void btnPressedUnternehmenImportieren() {

	}

	private void btnPressedUnternehmenhinzufügen() {

	}

	private void btnPressedSchülerExportieren() {

	}

	private void btnPressedSchülerImportieren() {

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
}