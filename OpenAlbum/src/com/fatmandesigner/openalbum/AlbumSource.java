package com.fatmandesigner.openalbum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;

import com.fatmandesigner.openalbum.model.Album;
import com.fatmandesigner.openalbum.model.Photo;
import com.fatmandesigner.openalbum.sql.Store;

public class AlbumSource {
  private Store store;
  private List<Album> albums;
  private WeakHashMap<Album, List<Photo>> cache;

  public AlbumSource(Store store) {
    this.store = store;
    this.cache = new WeakHashMap<>();
    this.albums = Collections.emptyList();
  }

  public void sync() {
    try {
      albums = store.findAlbums();
    } catch (SQLException ex) {
      System.out.println("SQLx: " + ex.getMessage());
    }
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public List<Photo> getPhotosByAlbum(String albumId) {
    Optional<Album> album = albums.stream().filter(item -> item.getUuid().equals(albumId)).findFirst();

    if (!album.isPresent()) {
      return Collections.emptyList();
    }

    if (cache.containsKey(album.get())) {
      return cache.get(album.get());
    }

    try {
      List<Photo> photos = store.findPhotos(albumId);
      cache.put(album.get(), photos);
      return photos;
    } catch (SQLException ex) {
      return Collections.emptyList();
    }
  }

  private Connection openConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:sqlite:data/albums.sqlite");
  }
}
