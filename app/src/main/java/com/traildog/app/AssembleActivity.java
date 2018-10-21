package com.traildog.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.traildog.app.model.Treats;
import com.traildog.app.model.TreatType;
import com.traildog.model.LocationAsyncUpdate;
import com.traildog.model.MyLocationListener;
import com.traildog.model.RadarAPI;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.radar.sdk.Radar;
import io.radar.sdk.model.Coordinate;
import io.radar.sdk.model.RadarUser;

public class AssembleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Treats activeWearTreat;
    private Treats donutTreat;
    private Treats hockeyTreat;
    private Treats italianFoodTreat;
    private Treats lampLightTreat;
    private Treats musicTreat;
    private Treats popcornTreat;

    final String RADAR_KEY_TYPE = "radar-api-test-key"; // can be radar-api-test-key or radar-api-live-key
    String updatedRadarKey;

    Handler h = new Handler();
    int delay = 5*1000; //1 second=1000 milisecond, 5*1000=5seconds
    Runnable runnable;

//    private FusedLocationProviderClient mFusedLocationClient;
//    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
//    private LocationCallback mLocationCallback;
//    private LocationRequest mLocationRequest = new LocationRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble);






//        int permissionCheck = ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION);

//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }

//        mLocationRequest.setInterval(10000L);
//        mLocationRequest.setFastestInterval(1000L);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 0);
        }

        String radarKey = "";
        Context context = getApplicationContext();
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
            radarKey = appInfo.metaData.getString(RADAR_KEY_TYPE);
            Radar.initialize(radarKey);
            Radar.setUserId("demoUser"); // change to UUID for currently logged in user
            Radar.setDescription("A demo user.");
            Radar.startTracking();
            MyRadarReceiver receiver = new MyRadarReceiver();
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        updatedRadarKey = radarKey;




//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            try {
//                                RadarUser resultUser = RadarAPI.updateUserLocation(radarKey, "demoUser", "A demo user.", location.getLongitude(), location.getLatitude(), location.getAccuracy());
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//
//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    try {
//                        RadarUser resultUser = RadarAPI.updateUserLocation(radarKey, "demoUser", "A demo user.", location.getLongitude(), location.getLatitude(), location.getAccuracy());
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            };
//        };

        //treats
        //NOTE: these items are already in wallet
        italianFoodTreat = new Treats(TreatType.COUPON, 4,
                "Two free side dishes of your choice " +
                        "with the purchase of an extra-large pizza",
                "drawable/italianfood.png");
        lampLightTreat = new Treats(TreatType.EVENT, 5, "Contact Us: (123) 456 7890", "drawable/lamplight.png");
        popcornTreat = new Treats(TreatType.COUPON, 7, "One free popcorn refill", "drawable/popcorn.png");


        //NOTE: these following items are geotagged with location and longitude
        //creates activeWear at campanile with id = 1 and TREATTYPE = COUPON
        activeWearTreat = new Treats(TreatType.COUPON, 1,
                "$10.00", "drawable/activeWear.png", 33.7743,
                84.3982, 200);

        //creates donut treat at crc id = 2 TREATTYPE = COUPON
        donutTreat = new Treats(TreatType.COUPON, 2, "50%",
                "drawable/donut.png", 33.7757,
                84.4040, 200);

        //creates hockey treat at klaus id = 3 TREATTYPE = EVENT
        hockeyTreat = new Treats(TreatType.EVENT, 3,
                "April 13th",  "drawable/hockey.png",
                33.7773, 84.3962, 200);

        //creates music treat at library id = 6 TREATTYPE = EVENT
        musicTreat = new Treats(TreatType.EVENT, 6,
                "October 29th", "drawable/music.png",
                33.7743, 84.3957, 200);

        //end treats

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder,  new MapFragment());
        ft.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

/*
String radarKey = null;
Context context = getApplicationContext();
ApplicationInfo appInfo = null;
Radar.initialize(radarKey);
Radar.setUserId("demoUser"); // change to UUID for currently logged in user
Radar.setDescription("A demo user.");
Radar.trackOnce(new RadarCallback() {
@Override
public void onComplete(Radar.RadarStatus status, Location location, RadarEvent[] events, RadarUser user) {
// do something with status, location, events, user
if (status == Radar.RadarStatus.SUCCESS) {
if (events.length > 0) {
for (RadarEvent e : events) {
String result = RadarUtils.handlweRadarEvent(e);
}
}
}
}
});
*/




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
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
////        }
//    }
//
//    private void startLocationUpdates() {
//
//        int permissionCheck = ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                mLocationCallback,
//                null /* Looper */);
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (mGoogleApiClient.isConnected()) {
//            startLocationUpdates();
//            Log.d(TAG, "Location update resumed .....................");
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d("LocationActivity", "onStop fired ..............");
//        SmartLocation.with(this.getApplicationContext()).location().stop();
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
//        startLocationUpdates();
//    }
//
//    protected void startLocationUpdates() {
//        int permissionCheck = ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
//                    1);
//        }
//        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
//                mGoogleApiClient, mLocationRequest, this);
//        Log.d(TAG, "Location update started ..............: ");
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.d(TAG, "Connection failed: " + connectionResult.toString());
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        Log.d(TAG, "Firing onLocationChanged..............................................");
//        mCurrentLocation = location;
//        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
////        updateUI();
//    }
//
////    private void updateUI() {
////        Log.d(TAG, "UI update initiated .............");
////        if (null != mCurrentLocation) {
////            String lat = String.valueOf(mCurrentLocation.getLatitude());
////            String lng = String.valueOf(mCurrentLocation.getLongitude());
////            tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
////                    "Latitude: " + lat + "\n" +
////                    "Longitude: " + lng + "\n" +
////                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
////                    "Provider: " + mCurrentLocation.getProvider());
////        } else {
////            Log.d(TAG, "location is null ...............");
////        }
////    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopLocationUpdates();
//    }
//
//    protected void stopLocationUpdates() {
//        LocationServices.FusedLocationApi.removeLocationUpdates(
//                mGoogleApiClient, this);
//        Log.d(TAG, "Location update stopped .......................");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mGoogleApiClient.isConnected()) {
//            startLocationUpdates();
//            Log.d(TAG, "Location update resumed .....................");
//        }
//    }


//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

    private Coordinate useCurrentLocation() {
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
            return new Coordinate(latitude, longitude);
        } else {
            System.out.println("Location is null" + longitude + "\t" + latitude);
        }

        return new Coordinate(latitude, longitude);

    }

    @Override
    protected void onResume() {
        //start as activity become visible

        h.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                Location loc = new Location("");
                Coordinate coord = useCurrentLocation();

                LocationAsyncUpdate lau = new LocationAsyncUpdate(coord, updatedRadarKey);
                lau.execute();

                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop when activity not visible
        super.onPause();
    }

}
