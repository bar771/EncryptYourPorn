package com.ilcapo.framework.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.crypto.SecretKey;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.ilcapo.framework.util.AES;
import com.ilcapo.framework.util.Crypto;

public class MainController implements Initializable {

	final String initVector_FILENAME = "iv.dat";
	
	public Button encryptButton, decryptButton;
	public Button generateIVButton, openIVButton;
	public Button folderDestinationButton, aboutButton;
	public TextField seedText, destinationText;
	
	private String ivPath;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	File file = new File(initVector_FILENAME);
    	if (file.exists()) {
    		encryptButton.setDisable(false);
            decryptButton.setDisable(false);
            folderDestinationButton.setDisable(false);
            seedText.setDisable(false);
            destinationText.setDisable(false);
    		openIVButton.setDisable(true);
            generateIVButton.setDisable(true);
            return;
    	}
    	
        encryptButton.setDisable(true);
        decryptButton.setDisable(true);
        folderDestinationButton.setDisable(true);
        seedText.setDisable(true);
        destinationText.setDisable(true);
    }
    
    public void handleClick_encryptButton() {
    	if (seedText.getText().toString().isEmpty() || 
    			destinationText.getText().toString().isEmpty())
    		return;
        
        AES aes = new AES();
        SecretKey key = null;
        try {
            key = aes.generateKey(seedText.getText().toString());
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
        String path = destinationText.getText().toString();

        SecretKey iv = null;
        try {
            if (ivPath == "")
                iv = aes.readKeyFromFile(initVector_FILENAME);
            else
                iv = aes.readKeyFromFile(ivPath);
        } catch (IOException e) {
        	e.printStackTrace();
        }

        Crypto crypto = new Crypto();
        crypto.encryptFiles(path, key, iv, aes, true);
    }
    
    public void handleClick_decryptButton() {
    	if (seedText.getText().toString().isEmpty())
            return;
        if (destinationText.getText().toString().isEmpty())
            return;
        
        AES aes = new AES();
        SecretKey key = null;
        try {
            key = aes.generateKey(seedText.getText().toString());
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
        String path = destinationText.getText().toString();

        SecretKey iv = null;
        try {
            if (ivPath == "")
                iv = aes.readKeyFromFile(initVector_FILENAME);
            else
                iv = aes.readKeyFromFile(ivPath);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        Crypto crypto = new Crypto();
        crypto.decryptFiles(path, key, iv, aes, true);
    }
    
    public void handleClick_generateIVButton() {
    	int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to generate new IV ?" ,"Warning", JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.NO_OPTION)
            return;
        try {
        	AES aes = new AES();
            SecretKey iv = aes.generateIV();
			aes.writeKeyToFile(iv, "iv.dat");
			
			encryptButton.setDisable(false);
            decryptButton.setDisable(false);
            folderDestinationButton.setDisable(false);
            seedText.setDisable(false);
            destinationText.setDisable(false);
            openIVButton.setDisable(true);
            generateIVButton.setDisable(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    }
    
    public void handleClick_openIVButton() {
    	final JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Locate IV file");
        int result = fileChooser.showOpenDialog(null); //openIVButton 
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.exists()) 
            	return;
            ivPath = selectedFile.getPath();
            encryptButton.setDisable(false);
            decryptButton.setDisable(false);
            folderDestinationButton.setDisable(false);
            seedText.setDisable(false);
            destinationText.setDisable(false);
            openIVButton.setDisable(true);
            generateIVButton.setDisable(true);
        }
    }
    
    public void handleClick_folderDestinationButton() {
    	final JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Locate Destination directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null); //folderDestinationButton 
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            destinationText.setText(selectedFile.getPath());
        }
    }
    
    public void handleClick_aboutButton() {
    	//JOptionPane.showMessageDialog(null, "Developed by ilCapo © 2018", "Credit", JOptionPane.INFORMATION_MESSAGE);
    	Stage aboutStage = new Stage();
    	aboutStage.initModality(Modality.APPLICATION_MODAL);
    	aboutStage.setResizable(false);
    	aboutStage.setTitle("About");
    	try {
			aboutStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/windows/aboutPage.fxml")), 344, 400));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	aboutStage.show();
    	
    }
}