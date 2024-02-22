package de.bwvaachen.botscheduler.grassmann.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyJFileChooser extends JFileChooser{
	
	public static String getPath(JFrame frame) {
		try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel-Dateien (*.xls, *.xlsx)", "xls", "xlsx"));
        fileChooser.showOpenDialog(frame);
        File selectedFile = fileChooser.getSelectedFile();

        
        if (selectedFile != null) {
        	return selectedFile.getAbsolutePath();
        } else {
        	return "";
        }
	}
}