package com.fatmandesigner.openalbum;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CtrlAlbumBrowser {
	@FXML
	private ListView albumListView;
	
	@FXML
	private ImageView imageView;

  public void setCurrentPhoto(Image image) {
    this.imageView.setImage(image);
  }
}
