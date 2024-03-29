package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Wagner_Eri
 *
 */
public class GUI_Create_Unternehmen {

	private JFrame frmUnternehmenHinzufgen;
	private JTextField tfieldName;
	private JTextField tfieldFachrichtung;
	private JLabel lblWahl1;
	private JLabel lblWahl2;
	private GUI_ListView gui_ListView;
	private JComboBox<TypSlotEnum> cBoxFrZeit;
	private JSpinner spMaxVeran;
	private JSpinner spMaxTeilnehmer;
	private JSpinner spIDNummer;
	private List<Unternehmen> unternehmenList;

	/**
	 * Konstruktor um ein Unternehmen hinzuzufügen
	 * 
	 * @param gui_ListView    Übergabe um addUnterhnehmen zu nutzen
	 * @param unternehmenList Damit geprüft werden kann, ob es gleiche Unternehmen
	 *                        gibt mir der gleichen Nummer
	 */
	public GUI_Create_Unternehmen(GUI_ListView gui_ListView, List<Unternehmen> unternehmenList) {
		this.gui_ListView = gui_ListView;
		this.unternehmenList = unternehmenList;
		initialize();

		cBoxFrZeit.addItem(TypSlotEnum.A);
		cBoxFrZeit.addItem(TypSlotEnum.B);
		cBoxFrZeit.addItem(TypSlotEnum.C);
		cBoxFrZeit.addItem(TypSlotEnum.D);
		cBoxFrZeit.addItem(TypSlotEnum.E);
		this.frmUnternehmenHinzufgen.setVisible(true);
		frmUnternehmenHinzufgen.setLocationRelativeTo(null);
	}

	/**
	 * Enums für ZeitSlots
	 * 
	 * @author Wagner_Eri
	 *
	 */
	public enum TypSlotEnum {
		A, B, C, D, E
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("removal")
	private void initialize() {
		frmUnternehmenHinzufgen = new JFrame();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		Image ui_Logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ui_logo.jpg"));
		frmUnternehmenHinzufgen.setIconImage(ui_Logo);

		frmUnternehmenHinzufgen.setTitle("Unternehmen Hinzufügen");
		frmUnternehmenHinzufgen.setBounds(100, 100, 255, 310);
		frmUnternehmenHinzufgen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblKlasse = new JLabel("Name:");

		tfieldName = new JTextField();
		tfieldName.setColumns(10);

		JLabel lblVorname = new JLabel("Fachrichtung:");

		tfieldFachrichtung = new JTextField();
		tfieldFachrichtung.setColumns(10);

		JLabel lblNachname = new JLabel("Max. Teilnehmer:");

		lblWahl1 = new JLabel("Max. Veranstaltungen:");

		lblWahl2 = new JLabel("Frühester Zeitslot:");

		JButton btnHinzufügen = new JButton("Hinzufügen");
		btnHinzufügen.addActionListener((e) -> btnpressedHinzufügen());

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener((e) -> btnPressedAbbrechen());

		cBoxFrZeit = new JComboBox<TypSlotEnum>();

		spMaxVeran = new JSpinner();
		spMaxVeran.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spMaxTeilnehmer = new JSpinner();
		spMaxTeilnehmer.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));

		JLabel lblNummer = new JLabel("Nummer:");

		spIDNummer = new JSpinner();
		spIDNummer.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));

		GroupLayout groupLayout = new GroupLayout(frmUnternehmenHinzufgen.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnAbbrechen)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnHinzufügen))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(lblWahl2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblWahl1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblNachname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblVorname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblKlasse, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
														80, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblNummer, GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(spIDNummer, GroupLayout.PREFERRED_SIZE, 93,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(cBoxFrZeit, Alignment.LEADING, 0, 93, Short.MAX_VALUE)
												.addComponent(tfieldFachrichtung, Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
												.addComponent(tfieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														93, Short.MAX_VALUE)
												.addComponent(spMaxVeran, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
												.addComponent(spMaxTeilnehmer, GroupLayout.DEFAULT_SIZE, 93,
														Short.MAX_VALUE)))))
				.addGap(20)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNummer, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(spIDNummer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
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
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnHinzufügen)
								.addComponent(btnAbbrechen))
						.addContainerGap()));
		frmUnternehmenHinzufgen.getContentPane().setLayout(groupLayout);
	}

	/**
	 * Methode damit Frame Dispose und nicht Closed werden
	 */
	private void btnPressedAbbrechen() {
		frmUnternehmenHinzufgen.dispose();
	}

	/**
	 * Button um einen Unternehmen der Oberfläche hinzuzufügen
	 */
	private void btnpressedHinzufügen() {
		if (!tfieldName.getText().isEmpty() && !tfieldFachrichtung.getText().isEmpty()) {

			if (!überPrüfeDopplung(unternehmenList)) {
				gui_ListView.addUnternehmenToList(new Unternehmen((int) spIDNummer.getValue(), tfieldName.getText(),
						tfieldFachrichtung.getText(), (int) spMaxTeilnehmer.getValue(), (int) spMaxVeran.getValue(),
						cBoxFrZeit.getSelectedItem().toString()));
				frmUnternehmenHinzufgen.dispose();
			} else {

			}

		} else {
			JOptionPane.showMessageDialog(null, "Ein Feld ist nicht ausgefüllt", "Fehler Leeres Feld",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Dopplung Prüfung damit Unternehmen Nummer nicht doppelt vorhanden sind
	 * 
	 * @param unternehmenList Liste die 
	 * @return True: Wenn es eine gleiche nummer gibt False: Wenn es keine Dopplung
	 *         gibt
	 */
	private boolean überPrüfeDopplung(List<Unternehmen> unternehmenList) {
		List<Integer> x = new ArrayList<>();

		for (Unternehmen unternehmen : unternehmenList) {
			x.add(unternehmen.getFirmenID());
		}
		if (x.contains(spIDNummer.getValue())) {
			JOptionPane.showMessageDialog(null, "Ein Unternehmen hat die gleiche Nummer", "Doppelte Unternehme Vergabe",
					JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
}
