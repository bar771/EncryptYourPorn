package com.ilcapo.encryptyourporn;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
 * @author bar771
 */
public class Main extends Application {
	
	private int width = 366, height = 400;
	private Stage primaryStage = null;
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Platform.setImplicitExit(false);
		
		primaryStage.setTitle("EncryptYourPorn - 2.0");
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
	    primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/windows/mainPage.fxml")), width, height));
	    primaryStage.setResizable(false);
	    primaryStage.show();
	    
	    primaryStage.setOnCloseRequest(e -> {
	    	//Platform.runLater(() -> {});
	    	//primaryStage.hide();
	    	//e.consume();
	    	System.exit(0);
	    });
	}

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}
}