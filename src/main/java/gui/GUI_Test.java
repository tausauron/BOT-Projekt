package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

public class GUI_Test {
	private JFrame frame;
	private DefaultListModel<Schüler> schülerListModel;
	private JList<Schüler> schülerList;
	private JTextField vornameField;
	private JTextField nachnameField;

	public GUI_Test(ArrayList<Schüler> personen) {
		frame = new JFrame("Personendaten");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		schülerListModel = new DefaultListModel<>();
		for (Schüler person : personen) {
			schülerListModel.addElement(person);
		}

		schülerList = new JList<>(schülerListModel);
		schülerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		schülerList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Schüler selectedPerson = schülerList.getSelectedValue();
				if (selectedPerson != null) {
					
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(schülerList);
		panel.add(scrollPane, BorderLayout.CENTER);

		//JPanel detailsPanel = new JPanel();
		//detailsPanel.setLayout(new GridLayout(3, 2));

		//JLabel vornameLabel = new JLabel("Vorname:");
		//JLabel nachnameLabel = new JLabel("Nachname:");
		//vornameField = new JTextField();
		//nachnameField = new JTextField();

		/*JButton updateButton = new JButton("Aktualisieren");
		updateButton.addActionListener(e -> {
			Person selectedPerson = personList.getSelectedValue();
			if (selectedPerson != null) {
				selectedPerson.setVorname(vornameField.getText());
				selectedPerson.setNachname(nachnameField.getText());
				listModel.setElementAt(selectedPerson, personList.getSelectedIndex());
			}
		});*/

		JButton addButton = new JButton("Hinzufügen");
		addButton.addActionListener(e -> {
		/*	String vorname = vornameField.getText();
			String nachname = nachnameField.getText();
			Person newPerson = new Person(vorname, nachname); */
			//listModel.addElement(newPerson);
		});

		/*detailsPanel.add(vornameLabel);
		detailsPanel.add(vornameField);
		detailsPanel.add(nachnameLabel);
		detailsPanel.add(nachnameField);
		detailsPanel.add(updateButton);
		detailsPanel.add(addButton);*/

		//panel.add(detailsPanel, BorderLayout.SOUTH);

		frame.add(panel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ArrayList<Schüler> personen = new ArrayList<>();
			
			new GUI_Test(personen);
		});
	}
}
