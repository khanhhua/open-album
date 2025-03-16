#!/usr/bin/env bash

java --module-path /Users/khanhhua/javafx-sdk-23.0.2/lib \
  --add-modules javafx.controls,javafx.fxml \
  -cp target/OpenAlbum-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  com.fatmandesigner.openalbum.OpenAlbumFx

#java -cp target/OpenAlbum-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
#  --module-path target \
#  --add-modules javafx.controls,javafx.fxml \
#  com.fatmandesigner.openalbum.OpenAlbumFx
