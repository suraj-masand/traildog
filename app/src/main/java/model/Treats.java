package model;

import android.widget.ImageView;

import java.util.PriorityQueue;

enum TREATTYPE {
    COUPON, MESSAGE, MONEY, EVENT
        }

public class Treats {

    private int value;
    private ImageView image;
    private double Latitude;
    private double Longitude;
    private TREATTYPE type;

    public Treats(TREATTYPE type, int value, ImageView image, double Latitude, double Longitude ) {
        this.type = type;
        this.value = value;
        this.image = image;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
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
}
