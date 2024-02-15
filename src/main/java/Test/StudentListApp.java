package Test;

import javax.swing.*;
import javax.swing.table.TableModel;

import gui.Schüler;
import gui.StudentTableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class StudentListApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Schüler> students = Arrays.asList(
                    new Schüler("1A", "John", "Doe", "Math", "Science"),
                    new Schüler("1B", "Jane", "Doe", "English", "History")
            );

            TableModel model = new StudentTableModel(students);
            JTable table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Student List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(800, 600));

            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
