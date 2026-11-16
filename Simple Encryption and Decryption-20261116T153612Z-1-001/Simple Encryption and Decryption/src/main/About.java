package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;


public class About extends JPanel implements ActionListener{
	private JLabel aboutLabel;
	private JPanel aboutPanel, aboutTextPanel;
	private JButton backButton;
	private JTextArea aboutTextArea;
	
	private Main main;
	
	public About(Main main) {
		this.main = main;
		this.setPreferredSize(new Dimension(1100, 800));
		this.setLayout(null);
		
		aboutLabel = new JLabel("ABOUT"); // creates About Label
		aboutLabel.setForeground(Color.black); //sets the color of the text into black
        aboutLabel.setFont(new Font("Arial", Font.BOLD, 30)); //customizes the font
		
		aboutPanel = new JPanel(); //About Title Panel
		Border aboutlineBorder = BorderFactory.createLineBorder(Color.black, 2); //Creates Borders
	    aboutPanel.setBorder(aboutlineBorder); 
		this.add(aboutPanel); //Adding aboutPanel onto this
		aboutPanel.setBackground(Color.white); //aboutPanel is white
		aboutPanel.setBounds(190, 20, 720, 60); //Defined aboutPanel's position and size
		aboutPanel.add(aboutLabel);
		
		aboutTextArea = new JTextArea(); //About Text Label
		String aboutText = 
			    "Welcome to KeyPer, your reliable, desktop solution for sensitive data protection. We provide a simple, powerful interface to secure your files using industry-standard encryption and decryption methods. In a digital world where privacy is paramount, KeyPer ensures that your data remains yours.\n\n" +
			    
			    "Our Mission:\n" +
			    "To democratize security. We recognized that advanced encryption is often locked behind complex, intimidating software. KeyPer was created to eliminate that hurdle, making file security as easy as a few clicks, without compromising the strength of the encryption. We believe every user should have the power to protect their most important files.\n\n" +
			    
			    "What KeyPer Offers:\n" +
			    "* Military-Grade Encryption: Utilizes the Advanced Encryption Standard (AES) in Galois/Counter Mode (GCM), which is a modern, highly secure Symmetric-Key Cipher.\n" +
			    "* Instant File Operations: Quickly encrypt or decrypt files in place, saving you time.\n" +
			    "* Secure Key Management: Your encryption key/password remains local and is never stored by the application.\n" +
			    "* Intuitive Interface: A clean, minimal design focused solely on securing or accessing your files.\n\n" +
			    
			    "The Developers:\n" +
			    "KeyPer is a project developed by a dedicated team of Second-Year Computer Engineering students from Cavite State University-Carmona. The team—Nicos James Casas, Raven Ashley Coloma, and Christian Guteza—created KeyPer as a practical security solution for our Data Structures and Algorithm Analysis (CPEN 65) course. We are passionate about building software that is transparent, trustworthy, and rigorously tested. Thank you for choosing KeyPer!";
			
		aboutTextArea.setText(aboutText); // Set the entire, corrected string
		aboutTextArea.setForeground(Color.black); //sets the color of the text into black
		aboutTextArea.setFont(new Font("Arial", Font.PLAIN, 18)); //customizes the font
		
		// disable editing for aboutTextArea and set line wrap
		aboutTextArea.setEditable(false);
		aboutTextArea.setLineWrap(true);
		aboutTextArea.setWrapStyleWord(true); // Wraps at word boundaries
		
		aboutTextPanel = new JPanel(); //About Text Panel
		Border aboutTextlineBorder = BorderFactory.createLineBorder(Color.black, 2); //Creates Borders
	    aboutTextPanel.setBorder(aboutTextlineBorder); 
		this.add(aboutTextPanel); //Adding aboutTextPanel onto this
		aboutTextPanel.setBackground(Color.white); //aboutPanel is white
		aboutTextPanel.setBounds(190, 80, 720, 500); //Defined aboutTextPanel's position and size
		aboutTextPanel.add(aboutTextArea);
		
		// Making abouTextPanel scrollable
		JScrollPane scrollPane = new JScrollPane(aboutTextArea);
		scrollPane.setPreferredSize(new Dimension(700, 480)); // Setting the size of the scroll pane to fill aboutTextPanel
		aboutTextPanel.add(scrollPane); // Adding the scroll pane to the aboutTextPanel
		
		backButton = new JButton("BACK"); // back button
		backButton.setBounds(190, 600, 150, 40);
		backButton.setBackground(Color.white); 
		backButton.setForeground(Color.black);
		Border backButtonlineBorder = BorderFactory.createLineBorder(Color.black, 2); //Creates Borders
		backButton.setBorder(backButtonlineBorder);
		backButton.addActionListener(this);
		this.add(backButton); 
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