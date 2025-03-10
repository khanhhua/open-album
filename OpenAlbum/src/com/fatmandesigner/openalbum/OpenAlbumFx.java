package com.fatmandesigner.openalbum;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OpenAlbumFx extends Application  {
	
	@Override
	public void start(Stage stage) {
		String version = System.getProperty("java.version");
		
		Label label = new Label("Hello Fx, " + version);
		Scene scene = new Scene(new StackPane(label), 640, 480);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
