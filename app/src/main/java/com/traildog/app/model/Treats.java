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
    private String name;


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
    public Treats (String name, TreatType type, int id, String value,
                  String imageFilePath, double Latitude,
                  double Longitude, int radius) {
        this.name = name;
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
    public Treats(String name, TreatType type, int id, String value,
                  String imageFilePath) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.value = value;
        this.imageFilePath = imageFilePath;

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[] {name, value, imageFilePath});
//        out.writeDoubleArray(new double[] {Latitude, Longitude});
//        out.writeIntArray(new int[] {id, radius});
    }

    public static final Parcelable.Creator<Treats> CREATOR = new Parcelable.Creator<Treats>() {
        public Treats createFromParcel(Parcel in) {
            return new Treats(in);
        }

        public Treats[] newArray(int size) {
            return new Treats[size];
        }
    };

    private Treats(Parcel in) {
        String strings[] =  new String[3];
        in.readStringArray(strings);
        this.name = strings[0];
        this.value = strings[1];
        this.imageFilePath = strings[2];
//        this.value = strings[1];
//        this.imageFilePath = strings[2];
//        int ints[] = in.createIntArray();
//        if (ints != null) {
//            this.id = ints[0];
//            this.radius = ints[1];
//        }
//        double doubles[] = in.createDoubleArray();
//        if (doubles != null && doubles.length == 2) {
//            this.Latitude = doubles[0];
//            this.Longitude = doubles[1];
//        }
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
