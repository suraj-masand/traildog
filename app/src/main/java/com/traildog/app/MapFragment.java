package com.traildog.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.traildog.app.model.Treats;
import com.traildog.model.MyLocationListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
//    Runnable addingTreats;
    Activity activity;
    Marker mCurrLocationMarker;
    List<Treats> treatsList;
    Set<Treats> collectedTreats;

    public MapFragment() {
        //default constructor;
    }

    // TODO: if it says this constructor is an error, just ignore it. It will still run lol
    public MapFragment(Activity activity, List<Treats> treatsList) {
        this.activity = activity;
        this.treatsList = treatsList;
        this.collectedTreats = new HashSet<>();
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
        addTreatsToMap();
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

                collectTreat(coord);

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

    public void addTreatsToMap() {
        Runnable addingTreats = new Runnable() {
            public void run() {
                //do something
                for (Treats treat : treatsList) {
                    double latitude = treat.getLatitude();
                    double longitude = treat.getLongitude();
                    final LatLng coord = new LatLng(latitude, longitude);
                    System.out.println("lat:" + latitude + " long:" + longitude);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(coord);
                    markerOptions.title(treat.getValue() + " - " + treat.getType().name());
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    Marker marker = googleMap.addMarker(markerOptions);

                    Circle circle = googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(latitude, longitude))
                            .radius((double)(treat.getRadius()))
                            .fillColor(Color.argb(50, 255, 0, 0))
                            .strokeColor(Color.BLUE));

//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coord,17f));

                }
            }
        };

        activity.runOnUiThread(addingTreats);
    }

    public void collectTreat(LatLng currentPos) {
        for (Treats treat : treatsList) {
            Location currentLoc = new Location("");
            currentLoc.setLatitude(currentPos.latitude);
            currentLoc.setLongitude(currentPos.longitude);
            Location treatLoc = new Location("");
            treatLoc.setLatitude(treat.getLatitude());
            treatLoc.setLongitude(treat.getLongitude());
            System.out.println("DISTANCE to " + treat.getValue());
            System.out.println((int)(currentLoc.distanceTo(treatLoc)));
            if ((int)(currentLoc.distanceTo(treatLoc)) <= treat.getRadius()) {
                if (!collectedTreats.contains(treat)) {
                    collectedTreats.add(treat);
                    treatPopup(treat);
                }
            }
        }
    }

    public void treatPopup(Treats treat) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("You got a Treat!");
        alertDialog.setMessage(treat.toString());
        ImageView pic = new ImageView(activity.getApplicationContext());
        String imageName = treat.getImageFilePath().split("/")[1];
        int imageId = 0;
        switch (imageName) {
            case "donut.png":
                imageId = R.drawable.donut;
                break;
            case "italianfood.png":
                imageId = R.drawable.italianfood;
                break;
            case "hockey.png":
                imageId = R.drawable.hockey;
                break;
            case "music.png":
                imageId = R.drawable.music;
                break;
            default:
                imageId = R.drawable.popcorn;
                break;
        }
        pic.setImageResource(imageId);
        pic.setAdjustViewBounds(true);
        pic.setMaxHeight(800);
//        pic.setMaxWidth(600);
        alertDialog.setView(pic);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
