package com.ilcapo.encryptyourporn.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class AboutController implements Initializable {
	
	public ImageView logo;
	public Label creditLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void clickHandle_openSource() {
		if (Desktop.isDesktopSupported()) {
		    try {
				Desktop.getDesktop().browse(new URI("https://github.com/bar771"));
			} catch (IOException e1) {e1.printStackTrace();
			} catch (URISyntaxException e1) {e1.printStackTrace();}
		}
	}

}
