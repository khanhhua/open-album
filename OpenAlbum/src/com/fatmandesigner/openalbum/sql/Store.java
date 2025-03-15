package com.fatmandesigner.openalbum.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
  private boolean initialized = false;

  public Store(String dbPath) {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    } catch (ClassNotFoundException ex) {
      System.err.println("Cannot find Sqlite JDBC Driver");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void init() throws SQLException {
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("SELECT type FROM sqlite_schema");

    if (rs.next()) {
      initialized = true;
      return;
    }

    try (InputStream instream = getClass().getResourceAsStream("/init.sql")) {
      StringBuilder builder = new StringBuilder();
      BufferedReader br = new BufferedReader(new InputStreamReader(instream));

      String line;
      while ((line = br.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }

      String[] sqlStatements = builder.toString().split(";");
      for(String sql:sqlStatements) {
        if (sql.trim().length() == 0) {
          continue;
        }

        System.out.println("SQL Init statement: " + sql);
        st = conn.createStatement();
        st.execute(sql);
      }

      initialized = true;
    } catch (IOException ex) {
      ex.printStackTrace();
    }
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
