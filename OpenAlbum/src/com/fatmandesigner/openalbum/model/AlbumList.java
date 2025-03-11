package com.fatmandesigner.openalbum.model;

import java.util.ArrayList;

import javafx.collections.ModifiableObservableListBase;

public class AlbumList extends ModifiableObservableListBase<Album> {
  private final ArrayList<Album> delegate = new ArrayList<>();

  public Album get(int index) {
    return delegate.get(index);
  }

  public int size() {
    return delegate.size();
  }

  protected void doAdd(int index, Album element) {
    delegate.add(index, element);
  }

  protected Album doSet(int index, Album element) {
    return delegate.set(index, element);
  }

  protected Album doRemove(int index) {
    return delegate.remove(index);
  }
}
