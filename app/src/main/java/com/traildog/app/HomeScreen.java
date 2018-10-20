package com.traildog.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.traildog.app.model.TREATTYPE;
import com.traildog.app.model.Treats;


public class HomeScreen extends AppCompatActivity {

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
        lampLightTreat = new Treats(TREATTYPE.EVENT, 5, "", "drawable/lamplight.png");
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
                "",  "drawable/hockey.png",
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
