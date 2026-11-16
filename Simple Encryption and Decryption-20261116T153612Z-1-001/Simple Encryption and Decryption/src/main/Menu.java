package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener{
	//center Panel
	private JPanel centerPanel;
	private JLabel title;
	
	//button
	private JButton toEncryptPage;
	private JButton toDecryptPage;
	private JButton toHelpPage;
	private JButton toAboutPage;
	
	private Main main;
	
	public Menu(Main main) {
		this.main = main;
		this.setPreferredSize(new Dimension(1100, 800));
		this.setBackground(Color.cyan);
		this.setLayout(null);
		
		//creating title label
		title = new JLabel("KeyPer");
		title.setBounds(320, 10, 800, 60);
		title.setFont(new Font("Arial", Font.BOLD, 50));
		
		//creating center panel
		centerPanel = new JPanel();
		centerPanel.setBounds(150, 100, 800, 500);
		centerPanel.setLayout(null);
		
		//creating toEncryptPage Button
		toEncryptPage = new JButton("Encrypt");
		toEncryptPage.setBounds(200, 100, 400, 60);
		toEncryptPage.setFont(new Font("Arial", Font.BOLD, 40));
		toEncryptPage.setBackground(Color.cyan);
		
		//creating toDecryptPage Button
		toDecryptPage = new JButton("Decrypt");
		toDecryptPage.setBounds(200, 200, 400, 60);
		toDecryptPage.setFont(new Font("Arial", Font.BOLD, 40));
		toDecryptPage.setBackground(Color.cyan);
		
		//creating toHelpPage Button
		toHelpPage = new JButton("Help");
		toHelpPage.setBounds(200, 300, 400, 60);
		toHelpPage.setFont(new Font("Arial", Font.BOLD, 40));
		toHelpPage.setBackground(Color.cyan);
		
		//creating toAboutPage Button
		toAboutPage = new JButton("About");
		toAboutPage.setBounds(200, 400, 400, 60);
		toAboutPage.setFont(new Font("Arial", Font.BOLD, 40));
		toAboutPage.setBackground(Color.cyan);
		
		//button's function
		toEncryptPage.addActionListener(this);
		toDecryptPage.addActionListener(this);
		toHelpPage.addActionListener(this);
		toAboutPage.addActionListener(this);
		
		//add all to center panel 
		centerPanel.add(title);
		centerPanel.add(toEncryptPage);
		centerPanel.add(toDecryptPage);
		centerPanel.add(toHelpPage);
		centerPanel.add(toAboutPage);
		
		//add center panel to this class
		this.add(centerPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//every button's function is to go to a designated page
		if(e.getSource() == toEncryptPage) {
			main.changePage("Encryption Page");
		}
		
		if(e.getSource() == toDecryptPage) {
			main.changePage("Decryption Page");
		}
		
		if(e.getSource() == toHelpPage) {
			main.changePage("Help Page");
		}
		
		if(e.getSource() == toAboutPage) {
			main.changePage("About Page");
		}
	}	
	
	//background image
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("res/bg.png");
		
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}