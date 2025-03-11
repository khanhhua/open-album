package com.fatmandesigner.openalbum.model;

import java.util.ArrayList;

import javafx.collections.ModifiableObservableListBase;
import javafx.scene.image.Image;

public class ImageList extends ModifiableObservableListBase<Image> {
  private final ArrayList<Image> delegate = new ArrayList<>();

  public Image get(int index) {
    return delegate.get(index);
  }

  public int size() {
    return delegate.size();
  }

  protected void doAdd(int index, Image element) {
    delegate.add(index, element);
  }

  protected Image doSet(int index, Image element) {
    return delegate.set(index, element);
  }

  protected Image doRemove(int index) {
    return delegate.remove(index);
  }
}
