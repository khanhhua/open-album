package com.fatmandesigner.openalbum.model;

import java.io.Serializable;

public class Album implements Serializable {

  private String name;
  
  private String uuid;

  protected Album() {}

  public Album(String uuid) {
    this.uuid = uuid;
    this.name = null;
  }

  public String getUuid() {
      return uuid;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  @Override
  public String toString() {
      return this.name;
  }
}
