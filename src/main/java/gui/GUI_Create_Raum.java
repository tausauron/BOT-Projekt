package gui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import klassenObjekte.Raum;

/**
 * 
 * @author Wagner_Eri
 *
 */

public class GUI_Create_Raum {

	private JFrame frmSchlerHinzufgen;
	private JTextField tfieldName;
	private GUI_ListView gui_ListView;
	private JSpinner spKapaz;

	public GUI_Create_Raum(GUI_ListView gui_ListView) {
		this.gui_ListView = gui_ListView;
		initialize();
		this.frmSchlerHinzufgen.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSchlerHinzufgen = new JFrame();

		frmSchlerHinzufgen.setTitle("Raum Hinzufügen");
		frmSchlerHinzufgen.setBounds(100, 100, 255, 175);
		frmSchlerHinzufgen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblKlasse = new JLabel("Name:");

		tfieldName = new JTextField();
		tfieldName.setColumns(10);

		JLabel lblVorname = new JLabel("Kapazität:");

		JButton btnHinzufügen = new JButton("Hinzufügen");
		btnHinzufügen.addActionListener((e) -> btnpressedHinzufügen());

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener((e) -> btnPressedAbbrechen());

		spKapaz = new JSpinner();
		GroupLayout groupLayout = new GroupLayout(frmSchlerHinzufgen.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(btnAbbrechen)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnHinzufügen))
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(lblVorname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblKlasse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														80, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(spKapaz, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
												.addComponent(tfieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														119, Short.MAX_VALUE))))
						.addGap(20)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblKlasse, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfieldName, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblVorname, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(spKapaz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnHinzufügen)
								.addComponent(btnAbbrechen))
						.addContainerGap(21, Short.MAX_VALUE)));
		frmSchlerHinzufgen.getContentPane().setLayout(groupLayout);
	}

	private void btnPressedAbbrechen() {
		frmSchlerHinzufgen.dispose();

	}

	private void btnpressedHinzufügen() {

		if (!tfieldName.getText().isEmpty()) {
			gui_ListView.addRaumToList(new Raum(tfieldName.getText(), (int) spKapaz.getValue()));
			frmSchlerHinzufgen.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Feld: Name ist leer", "Fehler Leeres Feld", JOptionPane.ERROR_MESSAGE);
		}

	}

}
