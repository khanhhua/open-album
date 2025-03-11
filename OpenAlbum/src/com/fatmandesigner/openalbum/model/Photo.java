package com.fatmandesigner.openalbum.model;

import java.io.FileInputStream;

import javafx.scene.image.Image;

public class Photo {
  private static Image PLACEHOLDER;

  static {
    try (FileInputStream instream = new FileInputStream("./data/train-10.jpg")) {
		  PLACEHOLDER= new Image(instream);
    } catch (Exception e) {}
  }

  private Image image = null;
  private String path = null;
  private boolean loaded = false;

  public Photo(String path) {
    this.path= path;
  }

  public Image getImage() {
    if (this.image == null) {
      return Photo.PLACEHOLDER;
    }

    return image;
  }

  public boolean isLoaded() {
    return loaded;
  }

  public void load() {
    if (loaded) {
      return;
    }

    try (FileInputStream instream = new FileInputStream(this.path)) {
      this.image = new Image(instream);
      this.loaded = true;
    } catch (Exception e) {
      System.out.println("EX during image loading: " + e.getMessage());
    }
  }
}
