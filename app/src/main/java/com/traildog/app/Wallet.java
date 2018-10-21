package com.traildog.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.traildog.app.model.Treats;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends AppCompatActivity {
    private List<Treats> list;
    private RecyclerView recyclerView;
    private WalletAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        i.putExtra("italianfood", italianFoodTreat);
//        i.putExtra("lamplight", lampLightTreat);
//        i.putExtra("popcorn", popcornTreat);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_wallet);
        setSupportActionBar(toolbar);


        list = new ArrayList<>();
        Treats italianFoodTreat = getIntent().getParcelableExtra("italianfood");
        list.add(italianFoodTreat);
        Treats lampLightTreat = getIntent().getParcelableExtra("lamplight");
        list.add(lampLightTreat);
        Treats popcornTreat = getIntent().getParcelableExtra("popcorn");
        list.add(popcornTreat);
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new WalletAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.close);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AssembleActivity.class);
                startActivity(i);
            }
        });
}
}
