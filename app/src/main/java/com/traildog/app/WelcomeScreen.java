package com.traildog.app;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeScreen extends AppCompatActivity {


    private Button signinbutton;
    private ImageView samplecorgi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        signinbutton = (Button) findViewById(R.id.signinbutton);
        samplecorgi = findViewById(R.id.samplecorgi);
    }
}
