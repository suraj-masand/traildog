package com.traildog.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;
import com.traildog.app.model.MyRecyclerViewAdapter;
import com.traildog.app.model.TreatType;
import com.traildog.app.model.Treats;
import com.traildog.model.MyLocationListener;

import java.util.ArrayList;
import java.util.List;

public class AssembleActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Treats activeWearTreat;
    private Treats donutTreat;
    private Treats hockeyTreat;
    private Treats italianFoodTreat;
    private Treats lampLightTreat;
    private Treats musicTreat;
    private Treats popcornTreat;

    MyRecyclerViewAdapter adapter;

//    final String RADAR_KEY_TYPE = "radar-api-test-key"; // can be radar-api-test-key or radar-api-live-key
//    String updatedRadarKey;
//    MapView myMap = null;
    MapFragment mapFragment;
//    private GeofencingClient mGeofencingClient;
    private List<Geofence> mGeofenceList;
//    private PendingIntent mGeofencePendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        useCurrentLocation(); // just to start the listener? Maybe?
//        mGeofencingClient = LocationServices.getGeofencingClient(this);
//
//        String radarKey = "";
//        Context context = getApplicationContext();
//        ApplicationInfo appInfo = null;
//        try {
//            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
//            radarKey = appInfo.metaData.getString(RADAR_KEY_TYPE);
//            Radar.initialize(radarKey);
//            Radar.setUserId("demoUser"); // change to UUID for currently logged in user
//            Radar.setDescription("A demo user.");
//            Radar.startTracking();
//            MyRadarReceiver receiver = new MyRadarReceiver();
//        } catch (PackageManager.NameNotFoundException e1) {
//            e1.printStackTrace();
//        }
//
//        updatedRadarKey = radarKey;


        //treats
        //NOTE: these items are already in wallet
        italianFoodTreat = new Treats("Italian Food", TreatType.COUPON, 4,
                "Two free side dishes of your choice " +
                        "with the purchase of an extra-large pizza",
                "drawable/italianfood.png");
        lampLightTreat = new Treats("Lamplight Advenures", TreatType.EVENT, 5, "Contact Us: (123) 456 7890", "drawable/lamplight.png");
        popcornTreat = new Treats("Free Popcorn", TreatType.COUPON, 7, "One free popcorn refill", "drawable/popcorn.png");


        //NOTE: these following items are geotagged with location and longitude
        //creates activeWear at campanile with id = 1 and TREATTYPE = COUPON
        activeWearTreat = new Treats("Italian Food", TreatType.COUPON, 1,
                "$10.00", "drawable/italianfood.png", 33.7743,
                -84.3982, 70);

        //creates donut treat at crc id = 2 TREATTYPE = COUPON
        donutTreat = new Treats("Donut", TreatType.COUPON, 2, "50%",
                "drawable/donut.png", 33.7757,
                -84.4040, 50);

        //creates hockey treat at klaus id = 3 TREATTYPE = EVENT
        hockeyTreat = new Treats("Hockey Event", TreatType.EVENT, 3,
                "April 13th",  "drawable/hockey.png",
                33.7773, -84.3962, 30);

        //creates music treat at library id = 6 TREATTYPE = EVENT
        musicTreat = new Treats("Music Festival" ,TreatType.EVENT, 6,
                "October 29th", "drawable/music.png",
                33.7743, -84.3957, 50);

        //end treats

        ArrayList<Treats> treatList = new ArrayList<>();
        treatList.add(activeWearTreat);
        treatList.add(donutTreat);
        treatList.add(hockeyTreat);
        treatList.add(musicTreat);

        addGeofencesFromTreatList(treatList);
//        System.out.println("Geofence Length = " + mGeofenceList.size());

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvTreats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, treatList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mapFragment = new MapFragment(this, treatList);
//        myMap = mapFragment.mMapView;
        ft.replace(R.id.fragment_placeholder,  mapFragment);
        ft.commit();

//        markCurrentLocation();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Geofences added
//                        // ...
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Failed to add geofences
//                        // ...
//                    }
//                });

//        String radarKey = null;
//        Context context = getApplicationContext();
//        ApplicationInfo appInfo = null;
//        try {
//            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
//            radarKey = appInfo.metaData.getString(RADAR_KEY_TYPE);
//            Radar.initialize(radarKey);
//            Radar.setUserId("demoUser"); // change to UUID for currently logged in user
//            Radar.setDescription("A demo user.");
//
////            if (Build.VERSION.SDK_INT >= 23) {
//            int requestCode = 0;
//            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, requestCode);
////            }
//
////            Radar.trackOnce(new RadarCallback() {
////                @Override
////                public void onComplete(Radar.RadarStatus status, Location location, RadarEvent[] events, RadarUser user) {
////                    // do something with status, location, events, user
////                    if (status == Radar.RadarStatus.SUCCESS) {
////                        if (events.length > 0) {
////                            for (RadarEvent e : events) {
////                                String result = RadarUtils.handleRadarEvent(e);
////                            }
////                        }
////                    }
////                }
////            });
//            Radar.startTracking();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


    }

    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wallet) {
            // Handle the camera action
            Intent i = new Intent(this, Wallet.class);
            i.putExtra("italianfood", italianFoodTreat);
            i.putExtra("lamplight", lampLightTreat);
            i.putExtra("popcorn", popcornTreat);
            startActivity(i);

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private LatLng useCurrentLocation() {
        System.out.println("Called UseCurrentLocation");
        Toast.makeText(this, "Loading Location...", Toast.LENGTH_SHORT).show();

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {

            System.out.println("Does not have permission, will ask.");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        }


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new MyLocationListener());
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = 0;
        double latitude = 0;
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            System.out.println("Got the location.");
            Log.d("LONGITUDE", "" + longitude);
            Log.d("LATITUDE", "" + latitude);
            return new LatLng(latitude, longitude);
        } else {
            System.out.println("Location is null" + longitude + "\t" + latitude);
        }

        return new LatLng(latitude, longitude);

    }


//    public void markCurrentLocation() {
////        MapView myMap = null;
////        while (myMap == null) {
////            myMap = mapFragment.mMapView;
////        }
//
//        GoogleMap googleMap = null;
//        while (googleMap == null) {
//            googleMap = mapFragment.getGoogleMap();
//        }
//
//        LatLng latLng = useCurrentLocation();
//
//        LocationAsyncUpdate lau = new LocationAsyncUpdate(latLng, googleMap);
//        lau.execute();
//    }

    public void addGeofencesFromTreatList(List<Treats> treatsList) {
        if (treatsList == null || treatsList.size() == 0) {
            return;
        }

        List<Geofence> geofenceList = new ArrayList<>();
        for (Treats treat : treatsList) {
            geofenceList.add(createGeofenceFromTreat(treat));
        }
        mGeofenceList = geofenceList;
    }

    public Geofence createGeofenceFromTreat(Treats treat) {
        Geofence geofence = new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.

                .setRequestId(treat.getType().toString() + "-" + treat.getId())

                .setCircularRegion(
                        treat.getLatitude(),
                        treat.getLongitude(),
                        (float) (treat.getRadius())
                )
                .setExpirationDuration(1000L * 60L * 60L * 24L)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER ) // | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();

        return geofence;
    }

}
