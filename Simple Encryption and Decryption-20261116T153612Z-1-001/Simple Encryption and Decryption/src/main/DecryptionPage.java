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

public class DecryptionPage extends JPanel implements ActionListener{
	private JLabel pageTitleLabel, textAreaLabel, keyLabel; 
	private JPanel pageTitlePanel, textAreaPanel, textTopPanel, keyPanel;
	private JButton backButton, decryptButton, clearButton, openFileButton, saveAsFileButton, decryptFileButton;
	private JTextArea textArea;
	private JTextField textField;
	
	private String password;
	private String encryptedText;
	
	private FileManager fileManager;
	private AES aes;
	
	private Main main;
	
	public DecryptionPage(Main main) {
		this.main = main;
		this.setPreferredSize(new Dimension(800, 800));
		this.setBackground(Color.cyan);
		this.setLayout(null);
		
		fileManager = new FileManager();
		aes = new AES();
		
		pageTitleLabel = new JLabel("DECRYPTION PAGE");
        pageTitleLabel.setForeground(Color.black); //sets the color of the text into black
        pageTitleLabel.setFont(new Font("Arial", Font.BOLD, 30)); //customizes the font

        textAreaLabel = new JLabel("Type the Text here: ");
        textAreaLabel.setForeground(Color.black); //sets the color of the text into black
        textAreaLabel.setFont(new Font("Arial", Font.BOLD, 20)); //customizes the font
        textAreaLabel.setHorizontalAlignment(JLabel.CENTER);
        textAreaLabel.setVerticalAlignment(JLabel.CENTER);

        keyLabel = new JLabel("Password: ");
        keyLabel.setForeground(Color.black); //sets the color of the text into black
        keyLabel.setFont(new Font("Arial", Font.BOLD, 20)); //customizes the font

        pageTitlePanel = new JPanel();
        textAreaPanel = new JPanel();
        textTopPanel = new JPanel();
        keyPanel = new JPanel();

        // creating backButton
        backButton = new JButton("BACK");
      	backButton.setBackground(Color.white);
      	backButton.setForeground(Color.black);
      	backButton.setBounds(190, 555, 150, 40);
      	backButton.addActionListener(this);
      	this.add(backButton);
      	
      	// creating decryptButton
      	decryptButton = new JButton("DECRYPT");
      	decryptButton.setBackground(Color.white);
      	decryptButton.setForeground(Color.black);
      	decryptButton.setBounds(475, 555, 180, 40);
      	decryptButton.addActionListener(this);
      	this.add(decryptButton);
      
      	// creating clearButton
      	clearButton = new JButton("CLEAR ALL");
      	clearButton.setBackground(Color.white);
      	clearButton.setForeground(Color.black);
      	clearButton.setBounds(760, 555, 150, 40);
      	clearButton.addActionListener(this);
      	this.add(clearButton);
      
      	// creating openFileButton
      	openFileButton = new JButton("OPEN FILE");
      	openFileButton.setBackground(Color.white);
      	openFileButton.setForeground(Color.black);
      	openFileButton.setBounds(190, 610, 150, 40);
      	openFileButton.addActionListener(this);
      	this.add(openFileButton);
      
      	// creating saveAsFileButton
      	saveAsFileButton = new JButton("SAVE AS FILE");
      	saveAsFileButton.setBackground(Color.white);
      	saveAsFileButton.setForeground(Color.black);
      	saveAsFileButton.setBounds(760, 610, 150, 40);
      	saveAsFileButton.addActionListener(this);
      	this.add(saveAsFileButton);
      
      	// creating decryptFileButton
      	decryptFileButton = new JButton("DECRYPT FILE/FOLDER");
      	decryptFileButton.setBackground(Color.white);
      	decryptFileButton.setForeground(Color.black);
      	decryptFileButton.setBounds(475, 610, 180, 40);
      	decryptFileButton.addActionListener(this);
      	this.add(decryptFileButton);
      
      	//methods
      	this.add(pageTitlePanel);
      	pageTitlePanel.setBackground(Color.white); //set color for the first Panel
      	pageTitlePanel.setBounds(190, 10, 720, 60); //location of the first panel
      	pageTitlePanel.add(pageTitleLabel); //shows the 1st label
      
      	textTopPanel.setBackground(Color.white);
      	textTopPanel.setLayout(new BorderLayout());
      	textTopPanel.add(textAreaLabel);

      	this.add(textAreaPanel);
      	textAreaPanel.setBackground(Color.white); //set color for the second Panel
      	textAreaPanel.setBounds(190, 135, 720, 410); //location of the second Panel
      	textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
      	textAreaPanel.add(textTopPanel); //shows the 2nd label

      	this.add(keyPanel);
      	keyPanel.setBackground(Color.white); //set color for the third Panel
      	keyPanel.setBounds(190, 85, 720, 40); //location of the third Panel
      	keyPanel.add(keyLabel); //shows the 3rd label

      	//adding textArea and textField for Input
      	textArea = new JTextArea(21, 25); //for the user input, textArea is used for multiple line of texts
      	textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
      	textArea.setLineWrap(true); //makes sure that long lines wrap to the next line
      	textArea.setWrapStyleWord(true); //makes sure that user only inputs words
      	textAreaPanel.add(textArea);

      	textField = new JTextField(40); //for the user input, textfield is used for single line text
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
			main.changePage("Menu Page");
		}
		
		if(e.getSource() == clearButton) {
			textField.setText("");
			textArea.setText("");
		}
		
		if(e.getSource() == decryptButton) {
			if(textArea.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Text area is empty.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(textField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a password.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				password = textField.getText();
				encryptedText = textArea.getText();
				
				textArea.setText(aes.decrypt(encryptedText, password));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Can't decrypt the text.", "Invalid text or key", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
		}
		
		if(e.getSource() == openFileButton) {
			File file = fileManager.openFile(textArea);
			
			if(file != null) {
				fileManager.getTextInFile(file, textArea);
			}
		}
		
		if(e.getSource() == saveAsFileButton) {
			fileManager.saveAsFile(textArea);
		}
		
		if(e.getSource() == decryptFileButton) {
			aes.showFileDialog(false);
		}
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("res/bg.png");
		
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}