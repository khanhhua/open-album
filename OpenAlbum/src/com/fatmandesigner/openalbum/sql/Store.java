package com.fatmandesigner.openalbum.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fatmandesigner.openalbum.model.Album;
import com.fatmandesigner.openalbum.model.Photo;

public class Store {
  private Connection conn;

  public Store(String url) {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection(url);
    } catch (ClassNotFoundException ex) {
      System.err.println("Cannot find Sqlite JDBC Driver");
    } catch (SQLException ex) {}
  }

  public List<Album> findAlbums() throws SQLException {
    ArrayList<Album> albums = new ArrayList<>();

    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("SELECT uuid, name FROM albums");

    while (rs.next()) {
      Album album = new Album(rs.getString("uuid"));
      album.setName(rs.getString("name"));
      albums.add(album);
    }

    st.close();
    return albums;
  }

  public List<Photo> findPhotos(String albumId) throws SQLException {
    List<Photo> photos = new ArrayList<>();

    PreparedStatement st = conn.prepareStatement("SELECT uuid, ext FROM photos WHERE album_id = ?");
    st.setString(1, albumId);

    ResultSet rs = st.executeQuery();
    while (rs.next()) {
      String id = rs.getString("uuid");
      String ext = rs.getString("ext");
      Photo photo = new Photo(String.format("data/%s.%s", id, ext));

      photos.add(photo);
    }

    st.close();

    return photos;
  }
}
