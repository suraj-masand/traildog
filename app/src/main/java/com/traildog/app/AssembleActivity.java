package com.traildog.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.traildog.app.model.Treats;
import com.traildog.app.model.TreatType;

import io.radar.sdk.Radar;

public class AssembleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Treats activeWearTreat;
    private Treats donutTreat;
    private Treats hockeyTreat;
    private Treats italianFoodTreat;
    private Treats lampLightTreat;
    private Treats musicTreat;
    private Treats popcornTreat;

    private float mPrimStartTouchEventX = -1;
    private float mPrimStartTouchEventY = -1;
    private float mSecStartTouchEventX = -1;
    private float mSecStartTouchEventY = -1;
    private float mPrimSecStartTouchDistance = 0;

    final String RADAR_KEY_TYPE = "radar-api-test-key"; // can be radar-api-test-key or radar-api-live-key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble);
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

        String radarKey = null;
        Context context = getApplicationContext();
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
            radarKey = appInfo.metaData.getString(RADAR_KEY_TYPE);
            Radar.initialize(radarKey);
            Radar.setUserId("demoUser"); // change to UUID for currently logged in user
            Radar.setDescription("A demo user.");

//            if (Build.VERSION.SDK_INT >= 23) {
            int requestCode = 0;
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, requestCode);
//            }

//            Radar.trackOnce(new RadarCallback() {
//                @Override
//                public void onComplete(Radar.RadarStatus status, Location location, RadarEvent[] events, RadarUser user) {
//                    // do something with status, location, events, user
//                    if (status == Radar.RadarStatus.SUCCESS) {
//                        if (events.length > 0) {
//                            for (RadarEvent e : events) {
//                                String result = RadarUtils.handleRadarEvent(e);
//                            }
//                        }
//                    }
//                }
//            });
            Radar.startTracking();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


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
}
