package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_Main_Start {

	private MyController myController;
	
	private JFrame frmTitelDesProgramms;

	/**
	 * Create the application.
	 * @param myController 
	 */
	public GUI_Main_Start(MyController myController) {
		this.myController=myController;
		initialize();
		this.frmTitelDesProgramms.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTitelDesProgramms = new JFrame();
		frmTitelDesProgramms.setTitle("Titel des Programms");
		frmTitelDesProgramms.setBounds(100, 100, 222, 300);
		frmTitelDesProgramms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btngenLösung = new JButton("Generiere");
		btngenLösung.addActionListener((e)->btnPressedgenLösung());
		GroupLayout groupLayout = new GroupLayout(frmTitelDesProgramms.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btngenLösung, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btngenLösung, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(191, Short.MAX_VALUE))
		);
		frmTitelDesProgramms.getContentPane().setLayout(groupLayout);
	}

	private void btnPressedgenLösung() {
		
	}
}
