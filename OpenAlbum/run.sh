#!/usr/bin/env bash

java --module-path /Users/khanhhua/javafx-sdk-23.0.2/lib \
  --add-modules javafx.controls,javafx.fxml \
  -cp target/OpenAlbum-0.0.1-SNAPSHOT.jar:lib/* \
  com.fatmandesigner.openalbum.OpenAlbumFx

