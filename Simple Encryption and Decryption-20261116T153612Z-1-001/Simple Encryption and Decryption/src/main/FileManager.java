package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class FileManager {
	private JFileChooser fileChooser;
	
	public FileManager() {
		fileChooser = new JFileChooser();
	}
	
	//method for opening a file to extract it's text
	public File openFile(JTextArea textArea) {
		int response = fileChooser.showOpenDialog(null); //show open dialog
		
		//if the user picked a file, the text area is cleared and return the file.
		if(response == JFileChooser.APPROVE_OPTION) {
			textArea.setText("");
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			return file;
		}
		
		//returns null if the user has not picked a file
		return null;
	}
	
	//method for saving a text into a file
	public void saveAsFile(JTextArea textArea) {
		fileChooser.setSelectedFile(new File("untitled.txt")); //set default file name
		
		int response = fileChooser.showSaveDialog(null); //show save dialog
		
		//if the user has picked a file, get the text of the text area then insert in the file.
		if(response == JFileChooser.APPROVE_OPTION) { 
			File file = fileChooser.getSelectedFile();
			PrintWriter fileOut = null;
			
			try {
				fileOut = new PrintWriter(file);
				fileOut.print(textArea.getText());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				fileOut.close();
			}
		}
	}
	
	//method for extracting text from a file.
	public void getTextInFile(File file, JTextArea textArea) {
		Scanner fileIn = null; //for scanning file content
		
		try {
			fileIn = new Scanner(file);
			if(file.isFile()) { //check if it is a file
				boolean firstLine = true;
				while(fileIn.hasNextLine()) {
					String line = fileIn.nextLine(); //getting the line of string
					
					if(!firstLine) {
						textArea.append("\n"); //not inserting a new line if it is the first line of the text
					}
					textArea.append(line); //putting the line of string in the text area.
					firstLine = false;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			fileIn.close();
		}
		
	}
	
}