package com.traildog.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Treats implements Parcelable {

    private String value;
    private int id;
    private int radius;
    private String imageFilePath;
    private double Latitude;
    private double Longitude;
    private TreatType type;


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
    public Treats(TreatType type, int id, String value,
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
    public Treats(TreatType type, int id, String value,
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

    public TreatType getType() {
        return type;
    }

    public void setType(TreatType type) {
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


    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel

    /**
     *     private String value;
     *     private int id;
     *     private int radius;
     *     private String imageFilePath;
     *     private double Latitude;
     *     private double Longitude;
     *     private TreatType type;
     * @param out
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(value);
        out.writeInt(id);
        out.writeInt(radius);
        out.writeString(imageFilePath);
        out.writeDouble(Latitude);
        out.writeDouble(Longitude);
        out.writeString(this.type.getText());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Treats> CREATOR = new Parcelable.Creator<Treats>() {
        public Treats createFromParcel(Parcel in) {
            return new Treats(in);
        }

        public Treats[] newArray(int size) {
            return new Treats[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    /**
     *     private String value;
     *     private int id;
     *     private int radius;
     *     private String imageFilePath;
     *     private double Latitude;
     *     private double Longitude;
     *     private TreatType type;
     * @param in
     */
    private Treats(Parcel in) {
        value = in.readString();
        id = in.readInt();
        radius = in.readInt();
        imageFilePath = in.readString();
        Latitude = in.readDouble();
        Longitude = in.readDouble();
        type.setText(in.readString());
    }
}
