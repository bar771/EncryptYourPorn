package com.ilcapo.encryptyourporn.util;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.crypto.SecretKey;
 
public class Crypto {
	
	public Crypto() {
	}
	
	/*
	 * ** OUT-DATED- Dont call mainMenu() method!
	 */
	public void mainMenu() throws NoSuchAlgorithmException, IOException {
		AES aes = new AES();
		SecretKey key = null, iv; 
		String filename, path;
		File inputFile;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Action: "); 
		switch(scan.next()) {
		case "keygen":
			iv = aes.generateIV();
			aes.writeKeyToFile(iv, "iv.dat");
			System.out.println("[SUCCESS]You have been generated a new InitVector.");
			break;
		case "encrypt":
			System.out.println("Key to encrypt: "); 
			key = aes.generateKey(scan.next());
			System.out.println("Filename to encrypt: "); 
			filename = scan.next();
			
			iv = aes.readKeyFromFile("iv.dat");
			
			inputFile = new File(filename);
			aes.encrypt(key, iv, inputFile, new File(filename+".dat"));
			System.out.println("[SUCCESS]Your file has been encrypted.");
			inputFile.delete();
			break;
		case "decrypt":
			System.out.println("Key to decrypt: "); 
			key = aes.generateKey(scan.next());
			System.out.println("Filename to decrypt: "); 
			filename = scan.next();
			
			iv = aes.readKeyFromFile("iv.dat");
			
			inputFile = new File(filename);
			aes.decrypt(key, iv, new File(filename), new File(filename.replace(".dat", "")));
			System.out.println("[SUCCESS]Your file has been decrypted.");
			inputFile.delete();
			break;
		case "encryptfolder":
			System.out.println("Key to encrypt: "); 
			key = aes.generateKey(scan.next());
			System.out.println("Path to folder to encrypt all files: "); 
			path = scan.next();
			
			iv = aes.readKeyFromFile("iv.dat");
			
			encryptFiles(path, key, iv, aes, true);
			break;
		case "decryptfolder":
			System.out.println("Key to decrypt: "); 
			key = aes.generateKey(scan.next());
			System.out.println("Path to folder to decrypt all files: "); 
			path = scan.next();
			
			iv = aes.readKeyFromFile("iv.dat");
			
			decryptFiles(path, key, iv, aes, true);
			break;
		case "quit":
			System.out.print("BB !");
			return;
		default:
			System.err.println("[ERROR] Wrong Syntex.");
			break;
		}
	}
	
	public void encryptFiles(String dirPath, SecretKey key, SecretKey iv, AES aes, boolean deleteFiles) {
		//File[] listOfFiles = getFilesInDirectory(dirPath);
		DirectoriesCrawler crawler = new DirectoriesCrawler(dirPath);
		crawler.scanDirectories();
		File[] listOfFiles = crawler.getAllFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			File inputFile = listOfFiles[i];
			
			System.out.println("Encrypting: "+inputFile);
			aes.encrypt(key, iv, inputFile, new File(inputFile.getPath()+".dat"));
			if (deleteFiles) inputFile.delete();
		}
	}
	
	public void decryptFiles(String dirPath, SecretKey key, SecretKey iv, AES aes, boolean deleteFiles) {
		//File[] listOfFiles = getFilesInDirectory(dirPath);
		DirectoriesCrawler crawler = new DirectoriesCrawler(dirPath);
		crawler.scanDirectories();
		File[] listOfFiles = crawler.getAllFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			File inputFile = listOfFiles[i];
			
			aes.decrypt(key, iv, inputFile, new File(inputFile.getPath().replace(".dat", "")));
			if (aes.isDecryptionKeyBad() == true) 
				return;
			System.out.println("Decrypting: "+inputFile);
			if (deleteFiles) inputFile.delete();
		}
	}
	
}