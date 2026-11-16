package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class EncryptionPage extends JPanel implements ActionListener{
	
	private JLabel pageTitleLabel, textAreaLabel, keyLabel;
	private JPanel titlePanel, textAreaPanel, textTopPanel, keyPanel;
	private JButton backButton, encryptButton, clearButton, openFileButton, saveAsFileButton, encryptFileButton;
	private JTextArea textArea;
	private JTextField textField;
	
	private String plainText;
	private String password;
	private boolean isEncrypted = false;
	
	private FileManager fileManager;
	private AES aes;
	private Main main;
	
	public EncryptionPage(Main main) {
		this.main = main;
		this.setPreferredSize(new Dimension(1100, 800));
		this.setBackground(Color.cyan);
		this.setLayout(null);
		
		fileManager = new FileManager();
		aes = new AES();

		pageTitleLabel = new JLabel();
		pageTitleLabel = new JLabel("ENCRYPTION PAGE");
		pageTitleLabel.setForeground(Color.black); //sets the color of the text into black
        pageTitleLabel.setFont(new Font("Arial", Font.BOLD, 30)); //customizes the font
		
		titlePanel = new JPanel(); //1st Panel
		this.add(titlePanel); //Adding titlePanel onto this
		titlePanel.setBackground(Color.white); //titlePanel is white
		titlePanel.setBounds(190, 10, 720, 60); //Defined titlePanel's position and size; UPDATED TO BE IN CENTER!!
		titlePanel.add(pageTitleLabel);
		
		textAreaLabel = new JLabel("Type the Text here: ");
        textAreaLabel.setForeground(Color.black); //sets the color of the text into black
        textAreaLabel.setFont(new Font("Arial", Font.BOLD, 20)); //customizes the font
        textAreaLabel.setHorizontalAlignment(JLabel.CENTER);
        textAreaLabel.setVerticalAlignment(JLabel.CENTER);
		
        textTopPanel = new JPanel();
        textTopPanel.setBackground(Color.white);
        textTopPanel.setLayout(new BorderLayout());
        textTopPanel.add(textAreaLabel);
        
		textAreaPanel = new JPanel(); //2nd Panel
		this.add(textAreaPanel); //Adding textAreaPanel onto this
		textAreaPanel.setBackground(Color.black); //textAreaPanel is black
		textAreaPanel.setBounds(190, 85, 720, 410); //Defined textAreaPanel's position and size; UPDATED TO BE IN CENTER!!
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
		textAreaPanel.add(textTopPanel);
		
		keyLabel = new JLabel("Password: ");
        keyLabel.setForeground(Color.black); //sets the color of the text into black
        keyLabel.setFont(new Font("Arial", Font.BOLD, 20)); //customizes the font
		
		keyPanel = new JPanel(); //3rd Panel
		this.add(keyPanel); //Adding keyPanel onto this
		keyPanel.setBackground(Color.white); //keyPanel is white
		keyPanel.setBounds(190, 505, 720, 40); //Defined keyPanel's position and size; UPDATED TO BE IN CENTER!!
		keyPanel.add(keyLabel);
		
		// creating buttons
		backButton = new JButton("BACK");
		encryptButton = new JButton("ENCRYPT");
		clearButton = new JButton("CLEAR ALL");
		openFileButton = new JButton("OPEN FILE");
		saveAsFileButton = new JButton("SAVE AS FILE");
		encryptFileButton = new JButton("ENCRYPT FILE/FOLDER");
		
		// buttons' positions
		backButton.setBounds(190, 555, 150, 40);
		encryptButton.setBounds(475, 555, 180, 40); 
		clearButton.setBounds(760, 555, 150, 40);
		openFileButton.setBounds(190, 610, 150, 40); 
		saveAsFileButton.setBounds(760, 610, 150, 40);
		encryptFileButton.setBounds(475, 610, 180, 40);
		
		// buttons' colors
		backButton.setBackground(Color.white); 
		encryptButton.setBackground(Color.white);
		clearButton.setBackground(Color.white);
		openFileButton.setBackground(Color.white);
		saveAsFileButton.setBackground(Color.white);
		encryptFileButton.setBackground(Color.white);
		
		// buttons' font colors
		backButton.setForeground(Color.black);
		encryptButton.setForeground(Color.black);
		clearButton.setForeground(Color.black);
		openFileButton.setForeground(Color.black);
		saveAsFileButton.setForeground(Color.black);
		encryptFileButton.setForeground(Color.black);
		
		// buttons' functions
		backButton.addActionListener(this);
		encryptButton.addActionListener(this);
		clearButton.addActionListener(this);
		openFileButton.addActionListener(this);
		saveAsFileButton.addActionListener(this);
		encryptFileButton.addActionListener(this);
		
	
		this.add(backButton);
		this.add(encryptButton);
		this.add(clearButton);
		this.add(openFileButton);
		this.add(saveAsFileButton);
		this.add(encryptFileButton);
		
		textArea = new JTextArea(21, 25);
		textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setLineWrap(true); //makes sure that long lines wrap to the next line
        textArea.setWrapStyleWord(true); //makes sure that user only inputs words
        textAreaPanel.add(textArea);
        
        textField = new JTextField(40); //for the user input, textField is used for single line text
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        keyPanel.add(textField, BorderLayout.NORTH);
        //adding ScrollPane for the textArea, this is used if the content in the textArea exceeds the size of the textArea Panel.
        JScrollPane ScrollBar = new JScrollPane(textArea);
        ScrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textAreaPanel.add(ScrollBar);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			textField.setText("");
			textArea.setText("");
			isEncrypted = false;
			main.changePage("Menu Page");
		}
		
		if(e.getSource() == clearButton) {
			textField.setText("");
			textArea.setText("");
			isEncrypted = false;
		}
		
		if(e.getSource() == encryptButton) {
			if(textArea.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Text area is empty.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(textField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a password.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(isEncrypted){
				JOptionPane.showMessageDialog(null, "Text is already encrypted.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				plainText = textArea.getText();
				password = textField.getText();
				
				textArea.setText(aes.encrypt(plainText, password));
				JOptionPane.showMessageDialog(null, "Encryption completed successfully!", 
		                "Success", JOptionPane.INFORMATION_MESSAGE);
				isEncrypted = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == openFileButton) {
			File file = fileManager.openFile(textArea);
			
			if(file != null) {
				fileManager.getTextInFile(file, textArea);
				isEncrypted = false;
			}
		}
		
		if(e.getSource() == saveAsFileButton) {
			fileManager.saveAsFile(textArea);
		}
		
		if(e.getSource() == encryptFileButton) {
			aes.showFileDialog(true);
		}
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("res/bg.png");
		
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}