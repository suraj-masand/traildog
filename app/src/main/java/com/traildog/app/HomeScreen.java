package com.traildog.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.traildog.app.model.TREATTYPE;
import com.traildog.app.model.Treats;


public class HomeScreen extends FragmentActivity implements OnMapReadyCallback {

    private FirebaseAuth firebaseAuth;
    private Treats activeWearTreat;
    private ImageView activeWearImageView;
    private Treats donutTreat;
    private ImageView donutImageView;
    private Treats hockeyTreat;
    private ImageView hockeyImageView;
    private Treats italianFoodTreat;
    private Treats lampLightTreat;
    private Treats musicTreat;
    private Treats popcornTreat;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        firebaseAuth = FirebaseAuth.getInstance();

        //NOTE: these items are already in wallet
        italianFoodTreat = new Treats(TREATTYPE.COUPON, 4,
                "Two free side dishes of your choice " +
                        "with the purchase of an extra-large pizza",
                "drawable/italianfood.png");
        lampLightTreat = new Treats(TREATTYPE.EVENT, 5, "Contact Us: (123) 456 7890", "drawable/lamplight.png");
        popcornTreat = new Treats(TREATTYPE.COUPON, 7, "One free popcorn refill", "drawable/popcorn.png");


        //NOTE: these following items are geotagged with location and longitude
        //creates activeWear at campanile with id = 1 and TREATTYPE = COUPON
        activeWearTreat = new Treats(TREATTYPE.COUPON, 1,
                "$10.00", "drawable/activeWear.png", 33.7743,
                84.3982, 200);

        //creates donut treat at crc id = 2 TREATTYPE = COUPON
        donutTreat = new Treats(TREATTYPE.COUPON, 2, "50%",
                "drawable/donut.png", 33.7757,
                84.4040, 200);

        //creates hockey treat at klaus id = 3 TREATTYPE = EVENT
        hockeyTreat = new Treats(TREATTYPE.EVENT, 3,
                "April 13th",  "drawable/hockey.png",
                33.7773, 84.3962, 200);

        //creates music treat at library id = 6 TREATTYPE = EVENT
        musicTreat = new Treats(TREATTYPE.EVENT, 6,
                "October 29th", "drawable/music.png",
                33.7743, 84.3957, 200);




        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SigninActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();


//
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng current = new LatLng(33.777397, -84.396292);
        float zoom = 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

}
