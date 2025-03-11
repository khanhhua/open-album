package com.fatmandesigner.openalbum;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OpenAlbumFx extends Application  {
	
	@Override
	public void start(Stage stage) throws IOException {
		URL resourceURL = getClass().getResource("/AlbumBrowser.fxml");
		FXMLLoader loader = new FXMLLoader(resourceURL);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		FileInputStream instream = new FileInputStream("./data/leaf-65.jpg");
		Image image = new Image(instream);

		CtrlAlbumBrowser controller = loader.<CtrlAlbumBrowser>getController();
		controller.setCurrentPhoto(image);
		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
