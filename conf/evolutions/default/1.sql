# Instances schema
 
# --- !Ups

CREATE TABLE instances (
    id varchar(100) NOT NULL,
    band_id int NOT NULL,
    PRIMARY KEY (id)
);
 
# --- !Downs
 
DROP TABLE instances;