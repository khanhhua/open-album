package com.fatmandesigner.openalbum;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import com.fatmandesigner.openalbum.sql.Store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OpenAlbumFx extends Application  {

  private Store store;

  private AlbumSource albumSource;

	@Override
	public void start(Stage stage) throws IOException, SQLException {
    store = new Store("data/albums.db");
    store.init();

    albumSource = new AlbumSource(store);
    albumSource.sync();

    stage.setTitle("Open Album");

		URL resourceURL = getClass().getResource("/AlbumBrowser.fxml");
		FXMLLoader loader = new FXMLLoader(resourceURL);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		CtrlAlbumBrowser controller = loader.<CtrlAlbumBrowser>getController();

    controller.setDataSource(albumSource);

		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}

