package de.bwvaachen.botscheduler.testInterface;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestGUI {

	private JFrame frmTestgui;
	private MyController myController;

	/**
	 * Create the application.
	 */
	public TestGUI(MyController myController) {
		this.myController = myController;
		initialize();
		this.frmTestgui.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// TODO: !!!also implement LookAndFeel in final GUI!!!
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) { }
		
		
		
		frmTestgui = new JFrame();
		frmTestgui.setTitle("Test-GUI");
		frmTestgui.setBounds(100, 100, 297, 92);
		frmTestgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnTest1 = new JButton("Test1");
		btnTest1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Test1");
				myController.checkLogin("Elefanten", "Stark!123");
			}
		});
		
		JButton btnTest2 = new JButton("Test2");
		btnTest2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Test2");
			}
		});
		
		JButton btnTest3 = new JButton("Test3");
		btnTest3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Test3");
			}
		});
		
		JButton btnTest4 = new JButton("Test4");
		btnTest4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Test4");
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmTestgui.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnTest1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTest2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTest3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTest4)
					.addContainerGap(353, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTest1)
						.addComponent(btnTest2)
						.addComponent(btnTest3)
						.addComponent(btnTest4))
					.addContainerGap(245, Short.MAX_VALUE))
		);
		frmTestgui.getContentPane().setLayout(groupLayout);
	}
}
