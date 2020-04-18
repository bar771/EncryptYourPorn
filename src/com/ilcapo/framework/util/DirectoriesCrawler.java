package com.ilcapo.framework.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoriesCrawler {
	private List<File> listFiles = new ArrayList<File>();
	private List<File> listDirs = new ArrayList<File>();
	private File baseDir = null;
	
	private File[] listOfFiles;
	
	public DirectoriesCrawler(String dirPath) {
		baseDir = new File(dirPath);
	}
	
	public void scanDirectories() {
		scanBaseDirectory();
		scanAllSubDirectories();
		listOfFiles = new File[listFiles.size()];
		convertListToArray();
	}
	
	private void scanBaseDirectory() {
		for (int i=0; i<baseDir.listFiles().length; i++) {
			if (baseDir.listFiles()[i].isFile()) {
				listFiles.add(baseDir.listFiles()[i]);
			} else if (baseDir.listFiles()[i].isDirectory()) {
				listDirs.add(baseDir.listFiles()[i]);
			}
		}
	}
	
	private void scanAllSubDirectories() {
		for (int i=0; i<listDirs.size(); i++) {
			File[] dir = listDirs.get(i).listFiles();
			for (int j=0; j<dir.length; j++) {
				if (dir[j].isFile()) {
					listFiles.add(dir[j]);
				} else if (dir[j].isDirectory()) {
					listDirs.add(dir[j]);
				}
			}
		}
	}
	
	private void convertListToArray() {
		for (int i=0; i<listFiles.size(); i++) { 
			listOfFiles[i] = listFiles.get(i);
		}
	}
	
	public File[] getAllFiles() {
		return listOfFiles;
	}
}