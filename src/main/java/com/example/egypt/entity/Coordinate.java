package com.example.egypt.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "coodinate")
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, name = "latitude")
    private String latitude;
    @Column(nullable = false, name = "longitude")
    private String longitude;
    @Column(nullable = false, name = "monumentName")
    private String monumentName;
    @Column(nullable = false, name = "link",length = 1000)
    private String link;
    @Column(nullable = true, name = "picture")
    private String picture;

    public Coordinate() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMonumentName() {
        return monumentName;
    }

    public void setMonumentName(String monumentName) {
        this.monumentName = monumentName;
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
}

