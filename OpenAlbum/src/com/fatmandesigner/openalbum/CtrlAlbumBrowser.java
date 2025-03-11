package com.fatmandesigner.openalbum;

import com.fatmandesigner.openalbum.model.Album;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CtrlAlbumBrowser {
	@FXML
	private ListView<Album> albumListView;
	
	@FXML
	private ImageView imageView;

  public void setCurrentPhoto(Image image) {
    this.imageView.setImage(image);
  }

  public void setAlbums(ObservableList<Album> albums) {
    this.albumListView.setItems(albums);
  }
}
