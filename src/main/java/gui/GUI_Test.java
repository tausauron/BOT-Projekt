package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class GUI_Test {

	private JFrame frame;
	private JTextField textFieldVorname;
	private JTextField textFieldNachName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Test window = new GUI_Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Test() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelSchüler = new JPanel();
		frame.getContentPane().add(panelSchüler, BorderLayout.CENTER);
		textFieldVorname = new JTextField();
		panelSchüler.add(textFieldVorname);
		textFieldVorname.setColumns(10);
		textFieldNachName = new JTextField();
		panelSchüler.add(textFieldNachName);
		textFieldNachName.setColumns(10);
		JComboBox comboBoxUn1 = new JComboBox();
		panelSchüler.add(comboBoxUn1);
		JComboBox comboBoxUn2 = new JComboBox();
		panelSchüler.add(comboBoxUn2);
		JComboBox comboBoxUn3 = new JComboBox();
		panelSchüler.add(comboBoxUn3);
		JComboBox comboBoxUn4 = new JComboBox();
		panelSchüler.add(comboBoxUn4);
		JComboBox comboBoxUn5 = new JComboBox();
		panelSchüler.add(comboBoxUn5);
		JComboBox comboBoxUn6 = new JComboBox();
		panelSchüler.add(comboBoxUn6);
	}

}
