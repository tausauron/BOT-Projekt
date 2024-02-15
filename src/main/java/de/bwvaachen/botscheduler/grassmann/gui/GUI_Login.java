package de.bwvaachen.botscheduler.grassmann.gui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;

// Grassmann

public class GUI_Login extends MouseAdapter {

	private JFrame frmLogin;
	JLabel pwdVisibilityIcon;
	private JPasswordField passwordField;
	private JLabel lblErrorMessage;
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
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setlblErrorMessage("");
				myController.checkLogin(textFieldUsername.getText(), passwordField.getText());
			}
		});
		
		lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(Color.RED);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmLogin.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsername)
								.addComponent(lblPassword))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(textFieldUsername, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblErrorMessage, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnLogin)))
					.addGap(9))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblErrorMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(31))
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		Icon ico = new ImageIcon(getClass().getResource("/de/bwvaachen/botscheduler/grassmann/img/dontShowPWD.jpg"));
		pwdVisibilityIcon = new JLabel(ico);
        pwdVisibilityIcon.setOpaque(true);
        pwdVisibilityIcon.addMouseListener(this);
		panel.add(pwdVisibilityIcon, BorderLayout.EAST);
		passwordField = new JPasswordField();
		panel.add(passwordField, BorderLayout.CENTER);
		pwdVisibilityIcon.setBackground(passwordField.getBackground());
        frmLogin.getContentPane().setLayout(groupLayout);
		frmLogin.getRootPane().setDefaultButton(btnLogin);
		
		Image ui_Logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ui_logo.jpg"));    
		frmLogin.setIconImage(ui_Logo);    
	}

	public void close() {
		this.frmLogin.setVisible(false);
	}

    public void mousePressed(MouseEvent event) {
    	passwordField.setEchoChar('\u0000');
    	Icon ico = new ImageIcon(getClass().getResource("showPWD.jpg"));
//        Icon ico = new ImageIcon("H:\\BOT-Projekt\\BOT-Projekt\\src\\main\\java\\de\\bwvaachen\\botscheduler\\grassmann\\img\\showPWD.jpg\\");
		pwdVisibilityIcon.setIcon(ico);
    }

    public void mouseReleased(MouseEvent event) {
    	passwordField.setEchoChar('•');

    	Icon ico = new ImageIcon(getClass().getResource("dontShowPWD.jpg"));
//        Icon ico = new ImageIcon("H:\\BOT-Projekt\\BOT-Projekt\\src\\main\\java\\de\\bwvaachen\\botscheduler\\grassmann\\img\\dontShowPWD.jpg\\");
		pwdVisibilityIcon.setIcon(ico);
    }
	
	public void setlblErrorMessage(String s) {
		this.lblErrorMessage.setText(s);
	}
}