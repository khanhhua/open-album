package com.fatmandesigner.openalbum;

import java.net.URL;
import java.util.ResourceBundle;

import com.fatmandesigner.openalbum.model.Album;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class CtrlAlbumBrowser implements Initializable {
	@FXML
	private ListView<Album> albumListView;
	
	@FXML
	private FlowPane imagesPane;

	@FXML
	private AnchorPane imagesAnchorPane;

	@FXML
	private ImageView imageView;

  public void initialize(URL _location, ResourceBundle _bundle) {
    this.imagesPane.prefWrapLengthProperty().bind(this.imagesAnchorPane.widthProperty());
  }

  public void setCurrentPhoto(Image image) {
    this.imageView.setImage(image);
  }

  public void setAlbums(ObservableList<Album> albums) {
    this.albumListView.setItems(albums);
  }

  public void setTiledImages(ObservableList<Image> images) {
    ObservableList<Node> children = this.imagesPane.getChildren();

    for (Image img:images) {
      ImageView item = new ImageView(img);
      item.setFitWidth(100);
      item.setFitHeight(100);
      children.add(item);
    }
    
  }
}
