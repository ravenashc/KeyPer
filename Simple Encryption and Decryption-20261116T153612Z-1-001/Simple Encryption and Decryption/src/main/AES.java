package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AES {
	
	private final int KEY_SIZE = 256; //256 bits key size 
	private final int ITERATION_COUNT = 600000; //600_000 iteration for generating encrypted key 
	private final int T_LEN = 128; //128 bits tag length 
	private final int IV_SIZE = 12; //12 bytes IV size
	private final int SALT_SIZE = 32; //32 bytes salt size
	
	//Generates key based from the password provided by the user.
	private SecretKey generateKeyFromPassword(String password, byte[] salt) 
			throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		char[] charPassword = password.toCharArray(); //converts the password from string to char array
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); //gets instance of PBKPF2 algorithm with HMAC-SHA256
		KeySpec spec = new PBEKeySpec(charPassword, salt, ITERATION_COUNT, KEY_SIZE); //creates specification for password, salt, iteration count, and key size.
		SecretKey secretKey = factory.generateSecret(spec); //produces the secret key based from the specification
		return new SecretKeySpec(secretKey.getEncoded(), "AES"); // returns the secret key
	}
	
	//-- ENCRYPTION METHODS --
	
	//generates salt for key generation
	//it makes the password have unique encryption key so the password will be slower to crack.
	private byte[] generateSalt() {
		byte[] salt = new byte[SALT_SIZE];
		new SecureRandom().nextBytes(salt);
		return salt;
	}
	
	//generates the initialization vector
	//used to randomize the encryption process.
	private byte[] generateIV() {
		byte[] iv = new byte[IV_SIZE];
		new SecureRandom().nextBytes(iv);
		return iv;
	}
	
	
	//encrypt the text using the password provided
	public String encrypt(String plainText, String password) throws Exception{
		byte[] salt = generateSalt(); //make the salt for every encryption
		SecretKey key = generateKeyFromPassword(password, salt); //make the secret key from the password
		byte[] iv = generateIV(); //make the IV
		
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //gets the instance of AES algorithm using the GCM mode.
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv); //create specification for tag length and IV
		cipher.init(Cipher.ENCRYPT_MODE, key, spec); //Initialize the cipher mode, key, and spec
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes()); //encrypt the text
		
		return encode(salt) + encode(iv) + encode(encryptedBytes); //returns the encrypted text in String
	}
	
	//converts the byte array data into String
	private String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	
	//-- DECRYPTION METHODS --
	
	//decrypt the encrypted text into readable String text using the password provided
	public String decrypt(String cipherText, String password) throws Exception{
		//separate the salt, IV, and encrypted text
		byte[] salt = decode(cipherText.substring(0, 44));
		byte[] iv = decode(cipherText.substring(44, 44 + 16));
		byte[] encryptedBytes = decode(cipherText.substring(60));
		
		//make the encrypted key from password and salt
		SecretKey key = generateKeyFromPassword(password, salt);
		
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv); //create spec for tag length and IV
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //get instance of AES algorithm using GCM mode
		cipher.init(Cipher.DECRYPT_MODE, key, spec); //initialize cipher mode, key, and spec
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes); //decrypt the encrypted text
		
		return new String(decryptedBytes); //return the String decrypted text
	}
	
	//converts String data into binary (byte array)
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	// -- FILE ENCRYPTION -- 
	private void encryptFile(Path file, String password) throws Exception {
        Path encryptedFile = file.resolveSibling(file.getFileName() + ".enc"); //creates path for the encrypted file
        Path tempFile = encryptedFile.resolveSibling("temp_" + System.nanoTime()); //creates temporary file path in the same directory

        byte[] salt = generateSalt(); //generate salt
        SecretKey key = generateKeyFromPassword(password, salt); //generate secret key
        byte[] iv = generateIV(); //generate IV

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //create AES cipher in GCM mode
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(T_LEN, iv)); //initialize cipher

        byte[] data = Files.readAllBytes(file); //make the file data in bytes
        byte[] encrypted = cipher.doFinal(data); //encrypt the file

        //write the salt + iv + encrypted(encrypted text + byte auth-tag) in the temp file
        try (OutputStream out = Files.newOutputStream(tempFile)) {
            out.write(salt);
            out.write(iv);
            out.write(encrypted);
        }

        // Replace original with encrypted version (now with .enc)
        Files.deleteIfExists(encryptedFile);
        Files.move(tempFile, encryptedFile);
        Files.deleteIfExists(file); // delete original
    }
	
	// -- FILE DECRYPTION --
	private void decryptFile(Path encryptedFile, String password) throws Exception {
        Path originalFile = encryptedFile.resolveSibling(encryptedFile.getFileName().toString().replace(".enc", ""));//creates path for the decrypted file
        Path tempFile = originalFile.resolveSibling("temp_" + System.nanoTime()); //creates temporary file path in the same directory

        byte[] all = Files.readAllBytes(encryptedFile); //read the encrypted file in memory
        
        //extract salt
        byte[] salt = new byte[SALT_SIZE];
        System.arraycopy(all, 0, salt, 0, SALT_SIZE);
        
        //extract iv
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(all, SALT_SIZE, iv, 0, IV_SIZE);
        
        //extract ciphertext
        byte[] ciphertext = new byte[all.length - SALT_SIZE - IV_SIZE];
        System.arraycopy(all, SALT_SIZE + IV_SIZE, ciphertext, 0, ciphertext.length);

        SecretKey key = generateKeyFromPassword(password, salt); //create secret key
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //create AES cipher in GCM mode
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(T_LEN, iv)); //initialize cipher

        byte[] decrypted = cipher.doFinal(ciphertext); //decrypt the file
        Files.write(tempFile, decrypted); //write the decrypted text in temp file

        Files.deleteIfExists(originalFile); //deletes pre-existing file
        Files.move(tempFile, originalFile); //replaces orig file with the decrypted one
        Files.deleteIfExists(encryptedFile); // remove .enc file
    }
	
	// -- FOLDER ENCRYPTION --
	
	private void encryptFolder(Path folder, String password) throws Exception {
		//create folder with .enc for encrypted files
        Path encFolder = Paths.get(folder.toString() + ".enc");
        Files.createDirectories(encFolder);

        //recursively walks all files
        Files.walk(folder)
             .parallel() //uses multiple threads to access multiple files at the same time
             .filter(Files::isRegularFile) //only process regular files
             .forEach(file -> {
                 try {
                     Path rel = folder.relativize(file); //constructs relative file path
                     Path target = encFolder.resolve(rel + ".enc"); //constructs a file path with .enc
                     Files.createDirectories(target.getParent()); //create the target file using the path

                     // Encrypt directly to target location
                     byte[] salt = generateSalt();
                     SecretKey key = generateKeyFromPassword(password, salt);
                     byte[] iv = generateIV();
                     Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                     cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(T_LEN, iv));

                     byte[] data = Files.readAllBytes(file); //read data from the file
                     byte[] encrypted = cipher.doFinal(data); //encrypt the file

                     //write the encrypted data in the target file
                     try (OutputStream out = Files.newOutputStream(target)) {
                         out.write(salt);
                         out.write(iv);
                         out.write(encrypted);
                     }
                 } catch (Exception e) {}
             });

        // Ask to delete original folder
        int choice = JOptionPane.showConfirmDialog(null,
            "Encryption complete!\n\nDelete original folder?\n" + folder.getFileName(),
            "Success", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
        	//deletes every file in the folder, including the folder itself
            Files.walk(folder)
                 .sorted((a, b) -> -a.compareTo(b))
                 .forEach(p -> { try { Files.delete(p); } catch (Exception ignored) {} });
        }
    }
	
	// -- FOLDER DECRYPTION --
	
	private void decryptFolder(Path encFolder, String password) throws Exception {
		//create folder for decrypted files
	    Path originalFolder = encFolder.resolveSibling(
	        encFolder.getFileName().toString().replace(".enc", "")
	    );
	    Files.createDirectories(originalFolder);

	    //to track any fail decryption
	    AtomicInteger decryptedCount = new AtomicInteger(0);
	    AtomicReference<Exception> firstError = new AtomicReference<>(null);

	    //recursively walks all files
	    Files.walk(encFolder)
	         .parallel() //uses multiple threads to access multiple files at the same time
	         .filter(p -> p.toString().endsWith(".enc")) //only process .enc files
	         .forEach(encFile -> {
	             try {
	                 Path rel = encFolder.relativize(encFile); //construct relative file path
	                 String nameNoEnc = rel.toString().substring(0, rel.toString().length() - 4); // remove ".enc"
	                 Path target = originalFolder.resolve(nameNoEnc); //constructs a file path without .enc
	                 Files.createDirectories(target.getParent()); //create the target file using the path

	                 byte[] all = Files.readAllBytes(encFile); //reads all data from the source file

	                 //extract salt, iv, and ciphertext from the data
	                 byte[] salt = Arrays.copyOfRange(all, 0, SALT_SIZE);
	                 byte[] iv = Arrays.copyOfRange(all, SALT_SIZE, SALT_SIZE + IV_SIZE);
	                 byte[] ciphertext = Arrays.copyOfRange(all, SALT_SIZE + IV_SIZE, all.length);

	                 SecretKey key = generateKeyFromPassword(password, salt);
	                 Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	                 cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(T_LEN, iv));

	                 byte[] decrypted = cipher.doFinal(ciphertext); //decrypt the file
	                 Files.write(target, decrypted); //write the decrypted data to the target file

	                 decryptedCount.incrementAndGet(); // success!

	             } catch (Exception e) {
	                 firstError.compareAndSet(null, e); //records only the first error
	             }
	         });

	    //if there is one successful decryption, show a messsage dialog that says success
	    if (decryptedCount.get() > 0) {
	    	//asks the user if the encrypted folder will be deleted or not
	        int choice = JOptionPane.showConfirmDialog(null,
	            "Decryption complete! (" + decryptedCount.get() + " files restored)\n\nDelete encrypted folder?",
	            "Success", JOptionPane.YES_NO_OPTION);

	        if (choice == JOptionPane.YES_OPTION) {
	            Files.walk(encFolder)
	                 .sorted(Comparator.reverseOrder())
	                 .forEach(p -> { try { Files.deleteIfExists(p); } catch (Exception ignored) {} });
	        }
	    } else {
	        // Clean up empty folder if created
	        try { Files.deleteIfExists(originalFolder); } catch (Exception ignored) {}
	        throw new RuntimeException("Decryption failed"); // prevent closing dialog as success
	    }
	}

	//dialog for encrypting/decrypting a file or folder
	public void showFileDialog(boolean isEncrypt) {
		//create the dialog
		JDialog fileDialog = new JDialog( (Frame) null, (isEncrypt ? "Encrypt" : "Decrypt") + " File/Folder", true);
		fileDialog.setSize(500, 400);
		fileDialog.setLocationRelativeTo(null);
		fileDialog.setLayout(null);
		
		//create title label
		JLabel title = new JLabel((isEncrypt ? "Encrypt" : "Decrypt") + " a File or Folder");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setBounds(0, 20, 500, 40);
		title.setHorizontalAlignment(JLabel.CENTER);
		fileDialog.add(title);
		
		//create file label
		JLabel fileLabel = new JLabel("Selected:");
	    fileLabel.setFont(new Font("Arial", Font.PLAIN, 14));
	    fileLabel.setBounds(20, 70, 100, 30);
	    fileDialog.add(fileLabel);
	    
	    //create text field for the path of selected file
	    JTextField filePathField = new JTextField("No file/folder selected");
	    filePathField.setBounds(20, 100, 280, 35);
	    filePathField.setEditable(false);
	    filePathField.setBackground(Color.GRAY);
	    filePathField.setForeground(Color.WHITE);
	    fileDialog.add(filePathField);

	    //create choose button
	    JButton chooseButton = new JButton("Choose File/Folder");
	    chooseButton.setBounds(310, 100, 150, 35);
	    chooseButton.setBackground(Color.cyan);
	    chooseButton.setForeground(Color.black);
	    fileDialog.add(chooseButton);
		
	    //create password label
		JLabel passLabel = new JLabel("Password:");
	    passLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    passLabel.setBounds(20, 160, 100, 30);
	    fileDialog.add(passLabel);

	    //create password field for password
	    JPasswordField passwordField = new JPasswordField();
	    passwordField.setBounds(120, 160, 300, 35);
	    passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
	    fileDialog.add(passwordField);
	    
	    //create checkbox for showing password
	    JCheckBox showPassword = new JCheckBox("Show Password");
	    showPassword.setBounds(20, 220, 150, 25);
	    showPassword.setBackground(new Color(30, 30, 40));
	    showPassword.setForeground(Color.white);
	    showPassword.setFocusPainted(false);
	    fileDialog.add(showPassword);
	    
	    //create button for encrypt/decrypt
	    JButton encryptDecryptBtn = new JButton((isEncrypt ? "ENCRYPT" : "DECRYPT"));
	    encryptDecryptBtn.setBounds(280, 300, 120, 30);
	    encryptDecryptBtn.setBackground(Color.cyan);
	    encryptDecryptBtn.setForeground(Color.black);
	    encryptDecryptBtn.setFont(new Font("Arial", Font.BOLD, 14));
	    fileDialog.add(encryptDecryptBtn);

	    //create cancel button
	    JButton cancelBtn = new JButton("CANCEL");
	    cancelBtn.setBounds(100, 300, 120, 30);
	    cancelBtn.setBackground(Color.GRAY);
	    cancelBtn.setForeground(Color.WHITE);
	    cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
	    fileDialog.add(cancelBtn);
	    
	    //selected file/folder will be inserted here
	    //is in array because local variable won't store in inner classes(lambda expressions), but can be stored when it is in array.
	    final File[] selectedFile = {null};
	    
	    //toggle to show either dot or the real inputted password
	    showPassword.addActionListener(e -> {
	    	if(showPassword.isSelected() ) {
	    		passwordField.setEchoChar((char) 0);
	    	}else {
	    		passwordField.setEchoChar('•');
	    	}
	    });
	    
	    //choose the file to be encrypted/decrypted
	    chooseButton.addActionListener(e -> {
	    	JFileChooser fileChooser = new JFileChooser(); //make a JFileChooser object
	    	fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //only files and directories will be shown
	    	fileChooser.setDialogTitle("Choose a File or Folder"); //title dialog
	    	
	    	int response = fileChooser.showOpenDialog(fileDialog); //show open dialog
	    	//if the user selected a file/folder, insert it in the selectedFile, and set the text of filePathField to the file/folder path
	    	if(response == JFileChooser.APPROVE_OPTION) {
	    		selectedFile[0] = fileChooser.getSelectedFile();
	    		filePathField.setText(selectedFile[0].getName());
	    	}
	    });
	    
	    //encrypts or decrypts the file or folder chosen with a provided password
	    encryptDecryptBtn.addActionListener(e -> {
	    	String password = new String(passwordField.getPassword()).trim(); //get the password from the password field
	    	if (password.isEmpty()) {
	    		//show error message if the password is empty.
	            JOptionPane.showMessageDialog(fileDialog, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        if (selectedFile[0] == null) {
	        	//show error message if the user has not selected a file
	            JOptionPane.showMessageDialog(fileDialog, "Please select a file or folder!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    	//make a path for the selected file/folder
	        Path path = selectedFile[0].toPath();

	        try {
	        	//if folder is selected, either encrypt or decrypt it
	            if (Files.isDirectory(path)) {
	                if (isEncrypt) {
	                    encryptFolder(path, password);
	                } else {
	                    decryptFolder(path, password);
	                }
	            } else {
	            //if not folder, either encrypt or decrypt the file
	                if (isEncrypt) {
	                    encryptFile(path, password);
	                } else {
	                    decryptFile(path, password);
	                }
	            }

	            //show a message that the encryption/decryption is a success, then close the dialog
	            JOptionPane.showMessageDialog(fileDialog, (isEncrypt ? "Encryption" : "Decryption") + " completed successfully!", 
	                "Success", JOptionPane.INFORMATION_MESSAGE);
		        fileDialog.dispose();

	        } catch (Exception ex) {
	        	//show error message if the password is wrong or the file is corrupted
	            String msg = ex instanceof javax.crypto.AEADBadTagException
	                ? "Wrong password!"
	                : "Failed: " + ex.getMessage();

	            JOptionPane.showMessageDialog(fileDialog, msg, "Error", JOptionPane.ERROR_MESSAGE);
	        }

	    });
	    
	    //cancel by disposing the dialog
	    cancelBtn.addActionListener(e -> {
	    	fileDialog.dispose();
	    });
	    
	    fileDialog.setVisible(true);
	}
}
