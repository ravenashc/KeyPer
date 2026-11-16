package main;

import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame{
	
	private JPanel cardPanel;
	private CardLayout cardLayout;
	
	private Menu menuPage;
	private EncryptionPage encryptionPage;
	private DecryptionPage decryptionPage;
	private About aboutPage;
	private Help helpPage;
	
	public Main() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("KeyPer");
		this.setIconImage(new ImageIcon("res/icon.jpg").getImage());
		this.setResizable(false);
		
		//creating panel with card layout
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		
		//create all page here
		menuPage = new Menu(this);
		encryptionPage = new EncryptionPage(this);
		decryptionPage = new DecryptionPage(this);
		aboutPage = new About(this);
		helpPage = new Help(this);
		
		//add all pages to the panel
		cardPanel.add(menuPage, "Menu Page");
		cardPanel.add(encryptionPage, "Encryption Page");
		cardPanel.add(decryptionPage, "Decryption Page");
		cardPanel.add(aboutPage, "About Page");
		cardPanel.add(helpPage, "Help Page");
		this.add(cardPanel);
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	//method for changing panels.
	public void changePage(String name) {
		cardLayout.show(cardPanel, name);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}