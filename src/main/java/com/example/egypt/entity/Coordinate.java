package com.example.egypt.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coordinate")
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, name = "latitude")
    private String latitude;
    @Column(nullable = false, name = "longitude")
    private String longitude;
    @Column(nullable = false, name = "monument_name")
    private String monument_name;
    @Column(nullable = false, name = "link", length = 1000)
    private String link;
    @Column(nullable = true, name = "picture", length = 1000)
    private String picture;
    @Column(nullable = false, name = "auhor_picture")
    private String author_picture;

    public Coordinate() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMonumentName() {
        return monument_name;
    }

    public void setMonumentName(String monumentName) {
        this.monument_name = monumentName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAuthor_picture() {
        return author_picture;
    }

    public void setAuthor_picture(String author_picture) {
        this.author_picture = author_picture;
    }
}
