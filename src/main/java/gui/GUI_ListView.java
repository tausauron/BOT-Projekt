package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Eric

public class GUI_ListView {

	private JFrame frame;
	private JScrollPane scrollPaneSchüler;
	private JScrollPane scrollPaneUnternehmen;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_ListView window = new GUI_ListView();
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
	public GUI_ListView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 752, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelSchüler = new JPanel();
		tabbedPane.addTab("Schüler", null, panelSchüler, null);

		JButton btnSchülerhinzufügen = new JButton("Hinzufügen");
		btnSchülerhinzufügen.addActionListener((e) -> addSchüler());

		JButton btnSchülerImportieren = new JButton("Importieren");

		JButton btnSchülerExportieren = new JButton("Exportieren");

		scrollPaneSchüler = new JScrollPane();
		scrollPaneSchüler.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelSchüler = new GroupLayout(panelSchüler);
		gl_panelSchüler.setHorizontalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchüler.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelSchüler.createSequentialGroup().addComponent(btnSchülerhinzufügen)
										.addPreferredGap(ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
										.addComponent(btnSchülerImportieren).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSchülerExportieren))
								.addGroup(gl_panelSchüler.createSequentialGroup()
										.addComponent(scrollPaneSchüler, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
										.addGap(3)))
						.addContainerGap()));
		gl_panelSchüler.setVerticalGroup(gl_panelSchüler.createParallelGroup(Alignment.LEADING).addGroup(gl_panelSchüler
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelSchüler.createParallelGroup(Alignment.BASELINE).addComponent(btnSchülerhinzufügen)
						.addComponent(btnSchülerExportieren).addComponent(btnSchülerImportieren))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPaneSchüler, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE).addGap(19)));
		panelSchüler.setLayout(gl_panelSchüler);

		
		JPanel panelUnternehmen = new JPanel();
		tabbedPane.addTab("Unternehmen", null, panelUnternehmen, null);
		
		JButton btnUnternehmenhinzufügen = new JButton("Hinzufügen");
		
		JButton btnUnternehmenImportieren = new JButton("Importieren");
		
		JButton btnUnternehmenExportieren = new JButton("Exportieren");
		
		scrollPaneUnternehmen = new JScrollPane();
		scrollPaneUnternehmen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panelUnternehmen = new GroupLayout(panelUnternehmen);
		gl_panelUnternehmen.setHorizontalGroup(
			gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelUnternehmen.createSequentialGroup()
							.addComponent(btnUnternehmenhinzufügen, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
							.addComponent(btnUnternehmenImportieren)
							.addGap(6)
							.addComponent(btnUnternehmenExportieren))
						.addComponent(scrollPaneUnternehmen, GroupLayout.PREFERRED_SIZE, 708, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panelUnternehmen.setVerticalGroup(
			gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUnternehmen.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelUnternehmen.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUnternehmenImportieren)
						.addComponent(btnUnternehmenExportieren)
						.addComponent(btnUnternehmenhinzufügen))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneUnternehmen, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		panelUnternehmen.setLayout(gl_panelUnternehmen);
	}

	private void addSchüler() {

		JPanel schülerPanel= new JPanel();
		JTextField schülerVorname =new JTextField();
		schülerVorname.setText("Peter");
		JTextField schülerNachname =new JTextField();
		schülerNachname.setText("Pan");
		schülerPanel.add(schülerVorname);
		schülerPanel.add(schülerNachname);
		
		scrollPaneSchüler.add(schülerPanel);
		scrollPaneSchüler.revalidate();
	}
}
