package com.traildog.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.traildog.model.LocationAsyncUpdate;
import com.traildog.model.MyLocationListener;


/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    private GoogleMap googleMap;
    Handler h = new Handler();
    int delay = 3*1000; //1 second=1000 milisecond
    Runnable runnable;
    Runnable runnable2;
    Activity activity;
    Marker mCurrLocationMarker;

    public MapFragment() {
        //default constructor;
    }

    // TODO: if it says this constructor is an error, just ignore it. It will still run lol
    public MapFragment(Activity activity) {
        this.activity = activity;
    }

//    public MapFragment(Activity activity) {
//        this.activity = activity;
//        this.getActivity().run
//    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately
        mMapView.getMapAsync(this);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//        // adding marker
//        googleMap.addMarker(marker);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory
//                .newCameraPosition(cameraPosition));
//
//        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        h.removeCallbacks(runnable);
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        LatLng current = new LatLng(33.777397, -84.396292);
        float zoom = 17;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
        runMapUpdates();
    }

    public void runMapUpdates(){

        runnable = new Runnable() {
            public void run() {
                //do something
                double latitude = MyLocationListener.getLatitude();
                double longitude = MyLocationListener.getLongitude();
                final LatLng coord = new LatLng(latitude, longitude);
                System.out.println("lat:" + latitude + " long:" + longitude);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(coord);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                mCurrLocationMarker = googleMap.addMarker(markerOptions);

                //move map camera
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coord,17f));

//                LocationAsyncUpdate lau = new LocationAsyncUpdate(coord, googleMap);
//                lau.execute();
            }
        };

        h.postDelayed(runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                activity.runOnUiThread(runnable);
                h.postDelayed(runnable2, delay);
            }
        }, delay);
    }

}
