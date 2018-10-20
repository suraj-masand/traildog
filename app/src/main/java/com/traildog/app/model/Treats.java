package com.traildog.app.model;

import android.widget.ImageView;

public class Treats {

    private String value;
    private int id;
    private int radius;
    private String imageFilePath;
    private double Latitude;
    private double Longitude;
    private TREATTYPE type;

    /**
     * constructor for dropin treats
     * @param type
     * @param id
     * @param value
     * @param imageFilePath
     * @param Latitude
     * @param Longitude
     * @param radius
     */
    public Treats(TREATTYPE type, int id, String value,
                  String imageFilePath, double Latitude,
                  double Longitude, int radius) {
        this.type = type;
        this.id = id;
        this.value = value;
        this.imageFilePath = imageFilePath;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.radius = radius;
    }

    /**
     * constructor for treats already in wallet
     * @param type
     * @param id
     * @param value
     * @param imageFilePath
     */
    public Treats(TREATTYPE type, int id, String value,
                  String imageFilePath) {
        this.type = type;
        this.id = id;
        this.value = value;
        this.imageFilePath = imageFilePath;

    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public TREATTYPE getType() {
        return type;
    }

    public void setType(TREATTYPE type) {
        this.type = type;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
