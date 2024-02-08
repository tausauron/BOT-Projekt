package de.bwvaachen.botscheduler.grassmann.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Grassmann

public class GUI_Login {

	private JFrame frmLogin;
	private JPasswordField passwordField;
	private JTextField textFieldUsername;
	private MyController myController;

	/**
	 * Create the application.
	 */
	public GUI_Login(MyController myController) {
		this.myController = myController;
		initialize();
		this.frmLogin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); }
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }
		
		frmLogin = new JFrame();
		frmLogin.setResizable(false);
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 224, 153);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblUsername = new JLabel("Username:");
		JLabel lblPassword = new JLabel("Password:");
		
		passwordField = new JPasswordField();
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myController.checkLogin(textFieldUsername.getText(), passwordField.getText());
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmLogin.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsername)
								.addComponent(lblPassword))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
								.addComponent(textFieldUsername, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)))
						.addComponent(btnLogin, Alignment.TRAILING))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnLogin)
					.addContainerGap(42, Short.MAX_VALUE))
		);
		frmLogin.getContentPane().setLayout(groupLayout);
	}

	public void close() {
		this.frmLogin.setVisible(false);
	}
}
