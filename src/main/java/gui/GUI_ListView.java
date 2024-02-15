package gui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

//Eric

public class GUI_ListView {

	private JFrame frame;
	private JScrollPane scrollPaneSchüler;
	private JScrollPane scrollPaneUnternehmen;
	private JList<Person> schülerList;
	private DefaultListModel<Person> schülerListModel;
	/**
	 * Create the application.
	 */
	
	public GUI_ListView(ArrayList<Person> personen) {
		
		schülerListModel = new DefaultListModel<>();
		for (Person person : personen) {
			schülerListModel.addElement(person);
		}
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 752, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelSchüler = new JPanel();
		tabbedPane.addTab("Schüler", null, panelSchüler, null);

		JButton btnSchülerhinzufügen = new JButton("Hinzufügen");
		btnSchülerhinzufügen.addActionListener((e) -> btnPressedSchülerhinzufügen());

		JButton btnSchülerImportieren = new JButton("Importieren");
		btnSchülerImportieren.addActionListener((e) -> btnPressedSchülerImportieren());

		JButton btnSchülerExportieren = new JButton("Exportieren");
		btnSchülerExportieren.addActionListener((e) -> btnPressedSchülerExportieren());

		
		
		schülerList = new JList<>(schülerListModel);
		schülerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		scrollPaneSchüler = new JScrollPane(schülerList);
		scrollPaneSchüler.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelSchüler = new GroupLayout(panelSchüler);
		gl_panelSchüler.setHorizontalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchüler.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelSchüler.createSequentialGroup().addComponent(btnSchülerhinzufügen)
										.addPreferredGap(ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
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

		scrollPaneUnternehmen = new JScrollPane();
		scrollPaneUnternehmen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelUnternehmen = new GroupLayout(panelUnternehmen);
		gl_panelUnternehmen.setHorizontalGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelUnternehmen.createSequentialGroup()
										.addComponent(btnUnternehmenhinzufügen, GroupLayout.PREFERRED_SIZE, 87,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
										.addComponent(btnUnternehmenImportieren).addGap(6)
										.addComponent(btnUnternehmenExportieren))
								.addComponent(scrollPaneUnternehmen, GroupLayout.PREFERRED_SIZE, 708,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		gl_panelUnternehmen.setVerticalGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUnternehmenImportieren).addComponent(btnUnternehmenExportieren)
								.addComponent(btnUnternehmenhinzufügen))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(scrollPaneUnternehmen,
								GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(19, Short.MAX_VALUE)));
		panelUnternehmen.setLayout(gl_panelUnternehmen);
	}

	private void addScrollPaneSchüler() {
		JPanel panelSchüler = new JPanel();
		scrollPaneSchüler.add(panelSchüler);

		JTextField textFieldVorname = new JTextField();
		panelSchüler.add(textFieldVorname);
		textFieldVorname.setColumns(10);
		JTextField textFieldNachName = new JTextField();
		panelSchüler.add(textFieldNachName);
		textFieldNachName.setColumns(10);
		JComboBox comboBoxUn1 = new JComboBox();
		panelSchüler.add(comboBoxUn1);
		JComboBox comboBoxUn2 = new JComboBox();
		panelSchüler.add(comboBoxUn2);
		JComboBox comboBoxUn3 = new JComboBox();
		panelSchüler.add(comboBoxUn3);
		JComboBox comboBoxUn4 = new JComboBox();
		panelSchüler.add(comboBoxUn4);
		JComboBox comboBoxUn5 = new JComboBox();
		panelSchüler.add(comboBoxUn5);
		JComboBox comboBoxUn6 = new JComboBox();
		panelSchüler.add(comboBoxUn6);
	}

	private void addScrollPaneUnternehmen() {

	}

	private Object btnPressedUnternehmenExportieren() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object btnPressedUnternehmenImportieren() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object btnPressedUnternehmenhinzufügen() {
		// TODO Auto-generated method stub
		return null;
	}

	private void btnPressedSchülerExportieren() {

	}

	private void btnPressedSchülerImportieren() {

	}

	private void btnPressedSchülerhinzufügen() {

	}
}
