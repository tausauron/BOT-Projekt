package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class GUI_Main_Start {

	private MyController myController;

	private JFrame frmStartMain;

	private List<Schueler> listSchüler;

	private List<Unternehmen> listUnternehmen;

	private List<Raum> listRaum;

	/**
	 * 
	 * @author Wagner_Eri
	 *
	 */

	public GUI_Main_Start(MyController myController) {
		this.myController = myController;
		initialize();
		this.frmStartMain.setVisible(true);
	}

	public GUI_Main_Start(MyController myController, List<Schueler> listSchüler, List<Unternehmen> listUnternehmen,
			List<Raum> listRaum) {
		this.myController = myController;
		this.listSchüler=listSchüler;
		this.listUnternehmen=listUnternehmen;
		this.listRaum=listRaum;
		initialize();
		this.frmStartMain.setVisible(true);
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
		frmStartMain = new JFrame();
		frmStartMain.setTitle("BOT");
		frmStartMain.setBounds(100, 100, 222, 270);
		frmStartMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btngenLösung = new JButton("Generiere");
		btngenLösung.addActionListener((e) -> btnPressedgenLösung());

		JButton btnBearbeiten = new JButton("Listen Bearbeiten");
		btnBearbeiten.addActionListener((e) -> öffneListView());

		JButton btnDatenLschen = new JButton("Daten löschen");
		btnDatenLschen.addActionListener((e) -> LöscheDateninDatenBank());
		GroupLayout groupLayout = new GroupLayout(frmStartMain.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btngenLösung, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(btnBearbeiten, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(btnDatenLschen, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btngenLösung, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnBearbeiten, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDatenLschen, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		frmStartMain.getContentPane().setLayout(groupLayout);
		
			Image ui_Logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ui_logo.jpg"));
			frmStartMain.setIconImage(ui_Logo);
	
		
	}

	private void öffneListView() {
		myController.startListView();
		frmStartMain.dispose();
	}

	private void LöscheDateninDatenBank() {
		myController.deleteStudent();
		myController.deleteCompany();
		myController.deleteCompany();
	}

	private void btnPressedgenLösung() {
		myController.exportStudentSchedule(frmStartMain);
	}
}
