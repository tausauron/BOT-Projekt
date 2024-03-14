package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

/**
 * 
 * @author Wagner_Eri
 *
 */
public class GUI_Main_Start {

	private MyController myController;

	private JFrame frmStartMain;

	public GUI_Main_Start(MyController myController, List<Schueler> listSchüler, List<Unternehmen> listUnternehmen,
			List<Raum> listRaum) {
		this.myController = myController;

		initialize();
		this.frmStartMain.setVisible(true);
		frmStartMain.setLocationRelativeTo(null);
	}

	private void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		frmStartMain = new JFrame();
		// Center the frame

		frmStartMain.setTitle("BOT");
		frmStartMain.setBounds(100, 100, 222, 270);
		frmStartMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btngenLösung = new JButton("Generieren");
		btngenLösung.addActionListener((e) -> btnPressedgenLösung());

		JButton btnBearbeiten = new JButton("Listen Bearbeiten");
		btnBearbeiten.addActionListener((e) -> öffneListView());

		JButton btnDatenLschen = new JButton("Daten löschen");
		btnDatenLschen.addActionListener((e) -> LöscheDateninDatenBank());
		GroupLayout groupLayout = new GroupLayout(frmStartMain.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btngenLösung, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
								.addComponent(btnBearbeiten, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
								.addComponent(btnDatenLschen, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addComponent(btngenLösung, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnBearbeiten, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnDatenLschen,
										GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(21, Short.MAX_VALUE)));
		frmStartMain.getContentPane().setLayout(groupLayout);

		Image ui_Logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ui_logo.jpg"));
		frmStartMain.setIconImage(ui_Logo);

	}

	private void öffneListView() {
		myController.startListView();
		frmStartMain.dispose();
	}

	private void LöscheDateninDatenBank() {
		String[] options = { "Alle Daten", "Nur Schüler", "Nur Unternehmen", "Nur Räume", "Abbrechen" };
		int choice = JOptionPane.showOptionDialog(null, "Wählen Sie, welche Daten Sie löschen möchten:",
				"Lösch-Optionen", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[4]);

		if (choice == JOptionPane.YES_OPTION) {
			myController.deleteAllStudent();
			myController.deleteAllCompany();
			myController.deleteAllRoom();

		} else if (choice == 1) {
			myController.deleteAllStudent();
		} else if (choice == 2) {
			myController.deleteAllCompany();
		} else if (choice == 3) {
			myController.deleteAllRoom();
		}
	}

	private void btnPressedgenLösung() {

		try {
			String path = MyJFileChooser.getPathFolder(frmStartMain, "");
			if (!path.equals("")) {
				// TODO
				myController.generiereLoesungen(path);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
