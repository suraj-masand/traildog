package com.traildog.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum TreatType {
    COUPON("COUPON"), MESSAGE("MESSAGE"), MONEY("MONEY"), EVENT("EVENT");

    private String name;

    TreatType(String name) {
        this.name = name;
    }

    public String getText() {
        return this.name;
    }

    public void setText(String name) {
        this.name = name;
    }
}


