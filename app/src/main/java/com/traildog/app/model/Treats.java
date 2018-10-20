package com.traildog.app.model;

import android.widget.ImageView;

public class Treats {

    private String value;
    private int id;
    private int radius;
    private String imageFilePath;
    private double Latitude;
    private double Longitude;
<<<<<<< HEAD
    private TreatType type;
=======
    private TREATTYPE type;
>>>>>>> master

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
<<<<<<< HEAD
    public Treats(TreatType type, int id, String value,
=======
    public Treats(TREATTYPE type, int id, String value,
>>>>>>> master
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
<<<<<<< HEAD
    public Treats(TreatType type, int id, String value,
=======
    public Treats(TREATTYPE type, int id, String value,
>>>>>>> master
                  String imageFilePath) {
        this.type = type;
        this.id = id;
        this.value = value;
        this.imageFilePath = imageFilePath;

    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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

<<<<<<< HEAD
    public TreatType getType() {
        return type;
    }

    public void setType(TreatType type) {
=======
    public TREATTYPE getType() {
        return type;
    }

    public void setType(TREATTYPE type) {
>>>>>>> master
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
