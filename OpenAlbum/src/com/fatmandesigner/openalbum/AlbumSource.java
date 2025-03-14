package com.fatmandesigner.openalbum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fatmandesigner.openalbum.model.Album;
import com.fatmandesigner.openalbum.model.Photo;

public class AlbumSource {
  private List<Album> albums;

  public AlbumSource() {
    albums = new ArrayList<>();

    Album album = new Album("202aa277-aa68-4a91-bedd-9009af2cc12a");
    album.setName("Default");
    albums.add(album);

    album = new Album("5daf7339-fd12-4025-a1d2-1b407d09eee4");
    album.setName("Precious Memories");
    albums.add(album);
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public List<Photo> getPhotosByAlbum(String id) {
    List<Photo> photos = new ArrayList<>();

    if (id.equals("202aa277-aa68-4a91-bedd-9009af2cc12a")) {
      for (int i=0; i<80; i++) {
        Photo photo = new Photo("./data/leaf-65.jpg");
        photos.add(photo);
      }
    } else if (id.equals("5daf7339-fd12-4025-a1d2-1b407d09eee4")) {
      for (int i=0; i<80; i++) {
        Photo photo = new Photo("./data/wind-68.jpg");
        photos.add(photo);
      }
    }

    return photos;
  }
}
