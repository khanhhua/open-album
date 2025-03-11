package com.fatmandesigner.openalbum;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fatmandesigner.openalbum.model.Album;
import com.fatmandesigner.openalbum.model.AlbumList;
import com.fatmandesigner.openalbum.model.ImageList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OpenAlbumFx extends Application  {
	
	@Override
	public void start(Stage stage) throws IOException {
    stage.setTitle("Open Album");

		URL resourceURL = getClass().getResource("/AlbumBrowser.fxml");
		FXMLLoader loader = new FXMLLoader(resourceURL);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		FileInputStream instream = new FileInputStream("./data/train-10.jpg");
		Image image = new Image(instream);

		CtrlAlbumBrowser controller = loader.<CtrlAlbumBrowser>getController();
		controller.setCurrentPhoto(image);

    List<Image> images = new ArrayList<>();
    for (int i=0; i<80; i++) images.add(image);
    controller.setTiledImages(images);

    ObservableList<Album> albums = FXCollections.observableArrayList();
    Album album = new Album("202aa277-aa68-4a91-bedd-9009af2cc12a");
    album.setName("Default");
    albums.add(album);

    album = new Album("5daf7339-fd12-4025-a1d2-1b407d09eee4");
    album.setName("Precious Memories");
    albums.add(album);

    controller.setAlbums(albums);
		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}

