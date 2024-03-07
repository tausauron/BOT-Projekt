package gui;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import klassenObjekte.Unternehmen;

public class GUI_Create_Unternehmen {

	private JFrame frmUnternehmenHinzufgen;
	private JTextField tfieldName;
	private JTextField tfieldFachrichtung;
	private JLabel lblWahl1;
	private JLabel lblWahl2;
	private GUI_ListView gui_ListView;
	private List<Unternehmen> listUnternehmen;
	private JComboBox<TypSlotEnum> cBoxFrZeit;
	private JSpinner spMaxVeran;
	private JSpinner spMaxTeilnehmer;

	public GUI_Create_Unternehmen(GUI_ListView gui_ListView, List<Unternehmen> listUnternehmen) {
		this.gui_ListView = gui_ListView;
		this.listUnternehmen = listUnternehmen;
		initialize();

		cBoxFrZeit.addItem(TypSlotEnum.A);
		cBoxFrZeit.addItem(TypSlotEnum.B);
		cBoxFrZeit.addItem(TypSlotEnum.C);
		cBoxFrZeit.addItem(TypSlotEnum.D);
		cBoxFrZeit.addItem(TypSlotEnum.E);
		this.frmUnternehmenHinzufgen.setVisible(true);
	}

	public enum TypSlotEnum {
		A, B, C, D, E
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUnternehmenHinzufgen = new JFrame();

		frmUnternehmenHinzufgen.setTitle("Unternehmen Hinzufügen");
		frmUnternehmenHinzufgen.setBounds(100, 100, 255, 284);
		frmUnternehmenHinzufgen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblKlasse = new JLabel("Name:");

		tfieldName = new JTextField();
		tfieldName.setColumns(10);

		JLabel lblVorname = new JLabel("Fachrichtung:");

		tfieldFachrichtung = new JTextField();
		tfieldFachrichtung.setColumns(10);

		JLabel lblNachname = new JLabel("Max Teilnehmer:");

		lblWahl1 = new JLabel("Max Veranstaltungen:");

		lblWahl2 = new JLabel("Frühester Zeitslot:");

		JButton btnHinzufügen = new JButton("Hinzufügen");
		btnHinzufügen.addActionListener((e) -> btnpressedHinzufügen());

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener((e) -> btnPressedAbbrechen());

		cBoxFrZeit = new JComboBox<TypSlotEnum>();

		spMaxVeran = new JSpinner();
		spMaxTeilnehmer = new JSpinner();

		GroupLayout groupLayout = new GroupLayout(frmUnternehmenHinzufgen.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnAbbrechen)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnHinzufügen))
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblWahl2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblWahl1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNachname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblVorname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblKlasse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 80,
												Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(cBoxFrZeit, Alignment.LEADING, 0, 93, Short.MAX_VALUE)
										.addComponent(tfieldFachrichtung, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												93, Short.MAX_VALUE)
										.addComponent(tfieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 93,
												Short.MAX_VALUE)
										.addComponent(spMaxVeran, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
										.addComponent(spMaxTeilnehmer, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))))
				.addGap(20)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblKlasse, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfieldName, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblVorname, GroupLayout.PREFERRED_SIZE, 32,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(tfieldFachrichtung, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNachname, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(spMaxTeilnehmer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(spMaxVeran, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxFrZeit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnHinzufügen)
								.addComponent(btnAbbrechen))
						.addContainerGap(16, Short.MAX_VALUE)));
		frmUnternehmenHinzufgen.getContentPane().setLayout(groupLayout);
	}

	private void btnPressedAbbrechen() {
		frmUnternehmenHinzufgen.dispose();
	}

	private void btnpressedHinzufügen() {
		if (!tfieldName.getText().isEmpty() && !tfieldFachrichtung.getText().isEmpty()) {

			gui_ListView.addUnternehmenToList(new Unternehmen(listUnternehmen.size()+1, tfieldName.getText(),
					tfieldFachrichtung.getText(), (int) spMaxTeilnehmer.getValue(), (int) spMaxVeran.getValue(),
					cBoxFrZeit.getSelectedItem().toString()));
			frmUnternehmenHinzufgen.dispose();

		} else {
			JOptionPane.showMessageDialog(null, "Feld nicht ausgefüllt", "Fehler Leeres Feld",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
