package com.traildog.model;

import android.location.Location;
import android.os.AsyncTask;
import android.os.LocaleList;

import java.io.IOException;

import io.radar.sdk.model.Coordinate;
import io.radar.sdk.model.RadarUser;

public class LocationAsyncUpdate extends AsyncTask<Void, Void, Void> {

    private Coordinate coord;
    private String updatedRadarKey;

    public LocationAsyncUpdate (Coordinate coord, String updatedRadarKey) {
        this.coord = coord;
        this.updatedRadarKey = updatedRadarKey;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Location loc = new Location("");
        loc.setLatitude(coord.getLatitude());
        loc.setLongitude(coord.getLongitude());
        loc.setAccuracy(25.0f);
//        try {
//            RadarUser radarUser = RadarAPI.updateUserLocation(updatedRadarKey, "demoUser", "A demo user.", loc);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
