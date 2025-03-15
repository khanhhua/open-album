CREATE TABLE albums(uuid char(36) primary key, name varchar(255) not null unique);
CREATE TABLE photos(uuid char(36) primary key, album_id char(36) not null, ext VARCHAR(4));

INSERT INTO albums VALUES('1c13e6dc-01f1-11f0-926d-32740822dbd8', 'Default');
