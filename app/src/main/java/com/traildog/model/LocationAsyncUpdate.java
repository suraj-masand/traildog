package com.traildog.model;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationAsyncUpdate extends AsyncTask<Void, Void, Void> {

    private LatLng coord;
    private GoogleMap myMap;

    public LocationAsyncUpdate (LatLng coord, GoogleMap myMap) {
        this.coord = coord;
        this.myMap = myMap;
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        Location loc = new Location("");
//        loc.setLatitude(coord.getLatitude());
//        loc.setLongitude(coord.getLongitude());
//        loc.setAccuracy(25.0f);

        //Place current location marker
//        LatLng latLng = new LatLng(coord.getLatitude(), coord.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(coord);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        Marker mCurrLocationMarker = myMap.addMarker(markerOptions);

        //move map camera
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord,12));
//        try {
//            RadarUser radarUser = RadarAPI.updateUserLocation(updatedRadarKey, "demoUser", "A demo user.", loc);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
