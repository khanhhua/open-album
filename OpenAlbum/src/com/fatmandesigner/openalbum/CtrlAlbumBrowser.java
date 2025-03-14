package com.fatmandesigner.openalbum;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import com.fatmandesigner.openalbum.model.Album;
import com.fatmandesigner.openalbum.model.Photo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.WindowEvent;

public class CtrlAlbumBrowser implements Initializable {

	@FXML
	private ListView<Album> albumListView;
	
	@FXML
	private FlowPane imagesPane;

	@FXML
	private AnchorPane imagesAnchorPane;
	
  @FXML
	private ScrollPane imagesScrollPane;

	@FXML
	private ImageView largeImageView;

  private ListProperty<Photo> photosProperty = new SimpleListProperty<>();

  private ObjectBinding<Bounds> visibleScrollBounds;

  private FilteredList<Node> visibleImageViews;

  private WeakHashMap<Node, Photo> npMap = new WeakHashMap<>();

  private AlbumSource albumSource;

  public void initialize(URL _location, ResourceBundle _bundle) {
    this.imagesPane.prefWrapLengthProperty().bind(this.imagesAnchorPane.widthProperty());

    this.photosProperty.addListener(new ListChangeListener<Photo>() {
      @Override
      public void onChanged(ListChangeListener.Change<? extends Photo> c) {
        while (c.next()) {
          if (c.wasAdded()) {
            ObservableList<Node> children = CtrlAlbumBrowser.this.imagesPane.getChildren();
            children.clear();

            ObservableList<Photo> photos = photosProperty.get();

            for (int i=c.getFrom(); i<c.getTo(); i++) {
              Photo photo = photos.get(i);

              ImageView item = new ImageView(photo.getImage());
              item.setId(Integer.valueOf(i).toString());
              item.setViewport(new Rectangle2D(0, 0, 200, 200));
              npMap.put(item, photo);

              item.setPreserveRatio(true);
              item.setFitHeight(100);
              children.add(item);
            }
          }
        }
      }
    });
    this.photosProperty.bind(Bindings.createObjectBinding(() -> {
      Album album = this.albumListView.getSelectionModel().getSelectedItem();

      if (album == null) {
        return FXCollections.emptyObservableList();
      }

      List<Photo> photos = this.albumSource.getPhotosByAlbum(album.getUuid());

      return FXCollections.observableArrayList(photos);
    }, this.albumListView.itemsProperty(),
      this.albumListView.getSelectionModel().selectedItemProperty()));

    this.visibleScrollBounds = Bindings.createObjectBinding(() -> {
      Bounds viewportBounds = this.imagesScrollPane.getViewportBounds();
      Bounds inScene = this.imagesScrollPane.localToScene(viewportBounds);
      Bounds result = this.imagesPane.sceneToLocal(inScene);

      return result;
    }, this.imagesScrollPane.vvalueProperty());

    this.visibleImageViews = new FilteredList<>(this.imagesPane.getChildren());
    this.visibleImageViews.predicateProperty().bind(Bindings.createObjectBinding(() -> 
      node -> {
        double v = this.imagesScrollPane.getVvalue();
        double index = Double.valueOf(node.getId()) - 1;
        int count = photosProperty.get().size();

        return (index / count) < v + (24.0 / count);
      }
    , this.imagesScrollPane.vvalueProperty(), visibleScrollBounds));

    this.imagesScrollPane.setOnScrollFinished((ScrollEvent e) -> {
      this.loadImagesInView();
    });
  }

  public void setCurrentPhoto(Image image) {
    this.largeImageView.setImage(image);
  }

  public void setDataSource(AlbumSource albumSource) {
    if (albumSource == null) {
      throw new IllegalArgumentException();
    }

    this.albumSource = albumSource;
    
    setAlbums(albumSource.getAlbums());
  }

  private void loadImagesInView() {
    for (Node node:visibleImageViews) {
      Photo photo = npMap.get(node);
      if (photo.isLoaded()) continue;

      photo.load();
      ((ImageView)node).setImage(photo.getImage());
    }
  }

  private void setAlbums(List<Album> albums) {
    ObservableList<Album> observableAlbums = FXCollections.observableArrayList(albums);
    this.albumListView.setItems(observableAlbums);
  }

}

