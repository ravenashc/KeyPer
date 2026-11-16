package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Help extends JPanel implements ActionListener{
	private JLabel pageTitleLabel, pageFAQLabel; //for Titles
	private JPanel pageTitlePanel, pageFAQPanel, faqDropDownPanel;
	private JButton backButton;

	private Main main;

	public Help(Main main){
		this.main = main;
		this.setPreferredSize(new Dimension(800, 800));
		this.setBackground(Color.CYAN);
		this.setLayout(null);

		pageTitleLabel = new JLabel("Help Page");
		pageTitleLabel.setForeground(Color.BLACK); //sets the font color to white.
		pageTitleLabel.setFont(new Font("Arial", Font.BOLD, 30)); //sets the font type, style and size.
		
		pageFAQLabel = new JLabel("Frequently Asked Questions:");  
		pageFAQLabel.setForeground(Color.BLACK); //sets the font color to white.
		pageFAQLabel.setFont(new Font("Arial", Font.BOLD, 25)); //sets the font type, style and size.

		// Frequently Asked Questions in parallel array
		String [] faqQuestions = {
			"What is KeyPer?",
			"How do I encrypt a text, file, or folder?",
            "How do I decrypt a text, file, or folder?",
            "How do I save encrypted text as a file?",
            "How do I open an encrypted file/folder?"
		};
	
	// FAQ's answers in parallel array
		String [] faqAnswers = {
			"KeYper is an application that allows users to encrypt and decrypt files and entire folders, providing an efficient way to secure the confidentiality and integrity of their data files. This application uses the Advanced Encryption Standard (AES) algorithm, which is the standard algorithm adopted by the United States government for data security. The application also employs several advanced cryptographic components within the Java Cryptography Architecture (JCA), namely: AES-256, Galois/Counter Mode (GCM), PBKDF2 with HMAC-SHA256, Initialization Vector (IV), Tag Length, and file and folder operations. \n \r\n"
			+ "The application's interface was developed using Java Swing, a library from the Java Foundation Classes (JFC) and an extension of the Abstract Window Toolkit (AWT). The developers chose Java Swing because it provides many improved components necessary for developing complex graphical user interfaces (GUIs). Moreover, the developers of this application, namely Casas, Nicos James C.; Coloma, Raven Ashley R.; and Guteza, Christian B., designed it to be user-friendly, providing an easy experience while effectively maintaining security through advanced cryptographic algorithms.\r\n",
			"After opening the application, you may proceed with the following steps: \n\r\n"
			+ "\n\r\n"
			+ "1. Click the Encrypt button on the Main Menu Page. You will be directed to the Encryption Page of the application. \n\r\n"
			+ "2. To encrypt text, you may input your text by typing it manually or by copying it from a different desktop application. To encrypt a file or folder, click the Encrypt a File/Folder button. Choose the designated file/folder in your file manager. \n\r\n"
			+ "3. Next, set your preferred password. \n\r\n"
			+ "Note: Make sure to remember the password that you've set, in order to avoid an error while decrypting your text. You may also use one password for every text, file, or folder that you will be encrypting. \n\r\n"
			+ "4. Next, click the Encrypt button. A notification pop-up will appear indicating that the text, file, or folder has been successfully encrypted. \n\r\n"
			+ "5. The data is now fully encrypted. You may copy the encrypted text and paste it in a different application or save it as a file. As for files and folders, you will see that the data will be converted into a .enc file format. \n\r\n"
			+ "6. Click the Clear All button to clear the contents in the text area (for encrypting text only).\r\n",
			"After opening the application, you may proceed with the following steps: \n\r\n"
			+ "\n\r\n"
			+ "1. Click the Decrypt button on the Main Menu Page. You will be directed to the Decryption Page of the application. \n\r\n"
			+ "2. To decrypt text, copy the encrypted format of your text and paste it in the text area. To decrypt a file or folder, click the Decrypt a File/Folder button. Choose the designated file/folder (in .enc file format). \n\r\n"
			+ "3. Enter the password that you set for encrypting the text, file, or folder. \n\r\n"
			+ "4. Click the Decrypt button. A notification pop-up will appear indicating that the text, file, or folder has been successfully decrypted. \n\r\n"
			+ "5. The data is now fully decrypted. You may now copy the decrypted text and paste it in a different application or save it as a file. As for files and folders, check your file manager, and you will see that the data has been reverted to its original file format.\n\r\n"
			+ "\n\r\n"
			+ "Note: In order to unlock the encrypted text, file, or folder, please input the correct password.\r\n",
			"After encrypting text, you may save it as a file. This removes the hassle of copying and pasting it into a different desktop application. Please refer to the following steps:\n\r\n"
			+ "\n\r\n"
			+ "1. After successfully encrypting text, click the Save as File button. \n\r\n"
			+ "2. You will be directed to your desktop file manager. Select the folder where you want to save your encrypted text. \n\r\n"
			+ "3. You may rename your file, then click Save. Your encrypted text is now saved as a file. \n\r\n"
			+ "4. In order to decrypt the saved encrypted text, proceed to the decryption page. \n\r\n"
			+ "5. Click the Open File button, select your saved encrypted text file, enter the password, and click the Decrypt button. \n\r\n"
			+ "\n\r\n"
			+ "Note: A notification pop-up will appear once the text has been successfully decrypted. An error notification will appear if you enter the wrong password.\r\n",
			"You may not be able to open encrypted files or folders using other desktop applications. Once they are in .enc file format, they will not display or function as expected. This is a security feature of the application. The only way to access the original content is by decrypting the file/folder using this application.\n\r\n"
			+ "For instructions on how to decrypt a file or folder, please refer to the third item in the Frequently Asked Questions (FAQ) section.\r\n"
		};
	
	//Setting up the Panels
		pageTitlePanel = new JPanel();
		pageFAQPanel = new JPanel();
		faqDropDownPanel = new JPanel();
		
		//back button setup
		backButton = new JButton("BACK");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setBounds(190, 600, 150, 40);
        backButton.addActionListener(this);
        this.add(backButton);

        //panel for page title
		this.add(pageTitlePanel);
        pageTitlePanel.setBackground(Color.WHITE); //set color for the first Panel
        pageTitlePanel.setBounds(190, 10, 720, 60); //location of the Page Title Panel
        pageTitlePanel.add(pageTitleLabel); //shows the Title label
        pageFAQPanel.setBackground(Color.WHITE); //set color for the Panel
        pageFAQPanel.setBounds(190, 90, 720, 400);
        pageFAQPanel.setLayout(new BoxLayout(pageFAQPanel, BoxLayout.Y_AXIS));
        pageFAQPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        pageFAQPanel.add(Box.createVerticalStrut(10));
		pageFAQPanel.add(pageFAQLabel);
		
		JScrollPane scrollPane = new JScrollPane(pageFAQPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
	    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    scrollPane.setBounds(190, 90, 720, 400);
	    this.add(scrollPane);
		
		addFAQSection(pageFAQPanel, faqQuestions[0], faqAnswers[0]);
		addFAQSection(pageFAQPanel, faqQuestions[1], faqAnswers[1]);
		addFAQSection(pageFAQPanel, faqQuestions[2], faqAnswers[2]);
		addFAQSection(pageFAQPanel, faqQuestions[3], faqAnswers[3]);
		addFAQSection(pageFAQPanel, faqQuestions[4], faqAnswers[4]);

	}
	
	//method for adding a FAQ
	private void addFAQSection(JPanel panel, String question, String answer) {
	    JCheckBox checkBox = new JCheckBox(question);
	    checkBox.setFont(new Font("Arial", Font.BOLD, 16));
	    checkBox.setForeground(Color.BLACK);
	    checkBox.setFocusPainted(false);
	    checkBox.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
	    checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	    checkBox.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));

	    JTextArea textArea = new JTextArea(answer);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setEditable(false);
	    textArea.setFocusable(false);
	    textArea.setFont(new Font("Arial", Font.PLAIN, 15));
	    textArea.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
	    textArea.setBackground(new Color(245, 245, 245));
	    textArea.setSize(720, 100);

	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 230), 1));
	    scrollPane.setMaximumSize(new Dimension(900, 200));
	    scrollPane.setVisible(false);

	    checkBox.addActionListener(e -> {
	        boolean selected = checkBox.isSelected();
	        scrollPane.setVisible(selected);	
	        
	        panel.revalidate();
	        panel.repaint();
	    });

	    panel.add(checkBox);
	    panel.add(scrollPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			main.changePage("Menu Page");
		}
	}

	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("res/bg.png");
		
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}