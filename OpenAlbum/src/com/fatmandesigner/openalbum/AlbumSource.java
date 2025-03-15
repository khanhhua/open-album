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

public class AlbumSource {
  private List<Album> albums;
  private WeakHashMap<Album, List<Photo>> cache;

  public AlbumSource() {
    this.cache = new WeakHashMap<>();
    this.albums = Collections.emptyList();
  }

  public void sync() {
    albums = new ArrayList<>();

    try (Connection conn = openConnection()) {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT uuid, name FROM albums");

      while (rs.next()) {
        Album album = new Album(rs.getString("uuid"));
        album.setName(rs.getString("name"));
        albums.add(album);
      }

      st.close();
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

    List<Photo> photos = new ArrayList<>();
    try (Connection conn = openConnection()) {
      PreparedStatement st = conn.prepareStatement("SELECT uuid, ext FROM photos WHERE album_id = ?");
      st.setString(1, albumId);

      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        String photoId = rs.getString("uuid");
        String photoExt = rs.getString("ext");
        Photo photo = new Photo(String.format("data/%s.%s", photoId, photoExt));

        photos.add(photo);
      }

      st.close();

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
