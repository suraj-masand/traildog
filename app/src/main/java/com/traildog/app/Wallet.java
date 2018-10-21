
package com.traildog.app;

//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//
//import com.traildog.app.model.CustomAdapter;
//import com.traildog.app.model.Treats;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.traildog.app.model.CustomAdapter;
import com.traildog.app.model.Treats;

public class Wallet extends AppCompatActivity {
    private ListView mCompleteListView;
    private Button mAddItemToList;
    private List<Treats> mItems;
    private CustomAdapter mListAdapter;
    //private static final int MIN = 0, MAX = 10000;
    private Treats italianFoodTreat;
    private Treats lampLightTreat;
    private Treats popcornTreat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        mItems = new ArrayList<Treats>();
        mListAdapter = new CustomAdapter(this, mItems);
        mCompleteListView.setAdapter(mListAdapter);
        //get treats from assembleActivity
        Intent i = getIntent();
        italianFoodTreat = (Treats) i.getParcelableExtra("italianFoodTreat");
        mItems.add(italianFoodTreat);
        lampLightTreat = (Treats) i.getParcelableExtra("lampLightTreat");
        mItems.add(lampLightTreat);
        popcornTreat = (Treats) i.getParcelableExtra("popcornTreat");
        mItems.add(popcornTreat);

        initViews();
        mItems = new ArrayList<Treats>();
        mListAdapter = new CustomAdapter(this, mItems);
        mCompleteListView.setAdapter(mListAdapter);
    }
    private void initViews() {
        mCompleteListView = (ListView) findViewById(R.id.completeList);
        mAddItemToList = (Button) findViewById(R.id.addItemToList);
        mAddItemToList.setOnClickListener((View.OnClickListener) this);
    }
    private void addItemsToList(Treats treats) {
        //int randomVal = MIN + (int) (Math.random() * ((MAX - MIN) + 1));
        mItems.add(treats);
        mListAdapter.notifyDataSetChanged();
    }


//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.addItemToList:
//                addItemsToList();
//                break;
//        }
//    }
}

