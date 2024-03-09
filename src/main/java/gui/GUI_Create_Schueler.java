package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class GUI_Create_Schueler {

	private JFrame frmSchlerHinzufgen;
	private JTextField tfieldKlasse;
	private JTextField tfieldVorname;
	private JTextField tfieldNachname;
	private JLabel lblWahl1;
	private JLabel lblWahl2;
	private JLabel lblWahl3;
	private JLabel lblWahl4;
	private JLabel lblWahl5;
	private JLabel lblWahl6;

	private JComboBox<String> cBoxWahl1;
	private JComboBox<String> cBoxWahl2;
	private JComboBox<String> cBoxWahl3;
	private JComboBox<String> cBoxWahl4;
	private JComboBox<String> cBoxWahl5;
	private JComboBox<String> cBoxWahl6;
	private GUI_ListView gui_ListView;

	/**
	 * 
	 * @author Wagner_Eri
	 *
	 */

	public GUI_Create_Schueler(GUI_ListView gui_ListView, List<Unternehmen> listUnternehmen) {
		this.gui_ListView = gui_ListView;
		initialize();
		this.frmSchlerHinzufgen.setVisible(true);
		listUnternehmen = List.of(new Unternehmen(1, "Test", "Test", 1, 2, "A"),
				new Unternehmen(2, "ADWdwad", "Test", 1, 2, "A"), new Unternehmen(3, "Test3", "Test", 1, 2, "A"),
				new Unternehmen(4, "Test", "Test", 1, 2, "A"), new Unternehmen(5, "Test", "Test", 1, 2, "A"),
				new Unternehmen(6, "Test", "Test", 1, 2, "A"));
		for (Unternehmen unt : listUnternehmen) {
			cBoxWahl1.addItem(unt.getFirmenID() + "- " + unt.getUnternehmen().toString());
			cBoxWahl2.addItem(unt.getFirmenID() + "- " + unt.getUnternehmen().toString());
			cBoxWahl3.addItem(unt.getFirmenID() + "- " + unt.getUnternehmen().toString());
			cBoxWahl4.addItem(unt.getFirmenID() + "- " + unt.getUnternehmen().toString());
			cBoxWahl5.addItem(unt.getFirmenID() + "- " + unt.getUnternehmen().toString());
			cBoxWahl6.addItem(unt.getFirmenID() + "- " + unt.getUnternehmen().toString());

		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSchlerHinzufgen = new JFrame();

		frmSchlerHinzufgen.setTitle("Schüler Hinzufügen");
		frmSchlerHinzufgen.setBounds(100, 100, 255, 443);
		frmSchlerHinzufgen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblKlasse = new JLabel("Klasse:");

		tfieldKlasse = new JTextField();
		tfieldKlasse.setColumns(10);

		JLabel lblVorname = new JLabel("Vorname:");

		tfieldVorname = new JTextField();
		tfieldVorname.setColumns(10);

		JLabel lblNachname = new JLabel("Nachname:");

		tfieldNachname = new JTextField();
		tfieldNachname.setColumns(10);

		lblWahl1 = new JLabel("Wahl 1:");

		lblWahl2 = new JLabel("Wahl 2:");

		cBoxWahl1 = new JComboBox<>();

		cBoxWahl2 = new JComboBox<>();

		lblWahl3 = new JLabel("Wahl 3:");

		lblWahl4 = new JLabel("Wahl 4:");

		lblWahl5 = new JLabel("Wahl 5:");

		lblWahl6 = new JLabel("Wahl 6:");

		cBoxWahl3 = new JComboBox<>();

		cBoxWahl4 = new JComboBox<>();

		cBoxWahl5 = new JComboBox<>();

		cBoxWahl6 = new JComboBox<>();

		JButton btnHinzufügen = new JButton("Hinzufügen");
		btnHinzufügen.addActionListener((e) -> btnpressedHinzufügen());

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener((e) -> btnPressedAbbrechen());
		GroupLayout groupLayout = new GroupLayout(frmSchlerHinzufgen.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(btnAbbrechen)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnHinzufügen))
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(lblWahl6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblWahl5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblWahl4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblWahl3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblWahl2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblWahl1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblNachname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(
														lblVorname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblKlasse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														80, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(cBoxWahl6, 0, 171, Short.MAX_VALUE)
												.addComponent(tfieldVorname, GroupLayout.DEFAULT_SIZE, 171,
														Short.MAX_VALUE)
												.addComponent(tfieldKlasse, GroupLayout.DEFAULT_SIZE, 171,
														Short.MAX_VALUE)
												.addComponent(tfieldNachname, GroupLayout.DEFAULT_SIZE, 171,
														Short.MAX_VALUE)
												.addComponent(cBoxWahl1, 0, 171, Short.MAX_VALUE)
												.addComponent(cBoxWahl2, 0, 171, Short.MAX_VALUE)
												.addComponent(cBoxWahl3, 0, 171, Short.MAX_VALUE)
												.addComponent(cBoxWahl4, 0, 171, Short.MAX_VALUE)
												.addComponent(cBoxWahl5, 0, 171, Short.MAX_VALUE))))
						.addGap(20)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblKlasse, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfieldKlasse, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblVorname, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfieldVorname, GroupLayout.PREFERRED_SIZE, 26,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNachname, GroupLayout.PREFERRED_SIZE, 32,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(tfieldNachname, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxWahl1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxWahl2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxWahl3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxWahl4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl5, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxWahl5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWahl6, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(cBoxWahl6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnHinzufügen)
								.addComponent(btnAbbrechen))
						.addContainerGap(23, Short.MAX_VALUE)));
		frmSchlerHinzufgen.getContentPane().setLayout(groupLayout);
	}

	private void btnPressedAbbrechen() {
		frmSchlerHinzufgen.dispose();

	}

	private void btnpressedHinzufügen() {
		List<String> listofChoosedWishes = List.of(cBoxWahl1.getSelectedItem().toString(),
				cBoxWahl2.getSelectedItem().toString(), cBoxWahl3.getSelectedItem().toString(),
				cBoxWahl4.getSelectedItem().toString(), cBoxWahl5.getSelectedItem().toString(),
				cBoxWahl6.getSelectedItem().toString());
		List<String> listofWishes = new ArrayList<>();
		for (String string : listofChoosedWishes) {
			String[] splittedChoose = string.split("-");
			listofWishes.add(splittedChoose[0]);
		}
		if (!gleicheZahlenPrüfen(listofWishes) && !tfieldKlasse.getText().isEmpty()
				&& !tfieldVorname.getText().isEmpty() && !tfieldNachname.getText().isEmpty()) {
			gui_ListView.addSchülerToList(new Schueler(tfieldKlasse.getText(), tfieldVorname.getText(),
					tfieldNachname.getText(), listofWishes));
			frmSchlerHinzufgen.dispose();

		} else {
			JOptionPane.showMessageDialog(null, "Feld: Vorname oder Nachname ist leer", "Fehler Leeres Feld",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private boolean gleicheZahlenPrüfen(List<String> listofWishes) {

		List<String> checkList = new ArrayList<>();

		for (String wish : listofWishes) {

			if (checkList.contains(wish)) {
				JOptionPane.showMessageDialog(null, "Fehler Sie haben den Wunsch " + wish + " doppelt ausgewählt",
						"Doppelte Auswahl Fehler", JOptionPane.ERROR_MESSAGE);
				return true;
			} else {
				checkList.add(wish);
				continue;
			}
		}

		return false;
	}
}
