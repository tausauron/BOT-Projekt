package gui;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class GUI_Main_Start {

	private MyController myController;

	private JFrame frmTitelDesProgramms;

	/**
	 * 
	 * @author Wagner_Eri
	 *
	 */

	public GUI_Main_Start(MyController myController) {
		this.myController = myController;
		initialize();
		this.frmTitelDesProgramms.setVisible(true);
	}
	
	public GUI_Main_Start(MyController myController,List<Schueler> listSchüler,List<Unternehmen> listUnternehmen,List<Raum> listRaum) 
	{
		this.myController = myController;
		initialize();
		this.frmTitelDesProgramms.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTitelDesProgramms = new JFrame();
		frmTitelDesProgramms.setTitle("BOT");
		frmTitelDesProgramms.setBounds(100, 100, 222, 270);
		frmTitelDesProgramms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btngenLösung = new JButton("Generiere");
		btngenLösung.addActionListener((e) -> btnPressedgenLösung());

		JButton btnBearbeiten = new JButton("Listen Bearbeiten");
		btnBearbeiten.addActionListener((e) -> öffneListView());

		JButton btnDatenLschen = new JButton("Daten löschen");
		btnDatenLschen.addActionListener((e) -> LöscheDateninDatenBank());
		GroupLayout groupLayout = new GroupLayout(frmTitelDesProgramms.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btngenLösung, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(btnBearbeiten, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDatenLschen, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addComponent(btngenLösung, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnBearbeiten, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnDatenLschen,
										GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(51, Short.MAX_VALUE)));
		frmTitelDesProgramms.getContentPane().setLayout(groupLayout);
	}

	private void öffneListView() {
		// TODO
	}

	private void LöscheDateninDatenBank() {
		myController.deleteStudent();
		myController.deleteCompany();
		myController.deleteCompany();
	}

	private void btnPressedgenLösung() {
		myController.exportStudentSchedule(frmTitelDesProgramms);
	}
}
