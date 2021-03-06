package com.example.konrad.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DatePickedCallback {

    private InterstitialAd fullScreenAd;
    private RecyclerView allMealsRecycler;
    private DietAdapter dietAdpater;

    private List<Diet> dietList = new ArrayList<>();

    private DatabaseHelper db;

    public DietAdapter getMa() {
        return dietAdpater;
    }
    public List<Diet> getDietList() {
        return dietList;
    }
    private DatePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setup activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup datepicker
        picker = new DatePicker(this);


        //setup ad
        fullScreenAd = new InterstitialAd(this);
        fullScreenAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        fullScreenAd.loadAd(new AdRequest.Builder().build());
        fullScreenAd.setAdListener(new AdListener(){
               @Override
               public void onAdLoaded() {
                   super.onAdLoaded();
                   fullScreenAd.show();
               }
           }
        );

        //change toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ???
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // for open add meal activity
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setup recycler with all meals list
        allMealsRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        dietAdpater = new DietAdapter(dietList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allMealsRecycler.setLayoutManager(mLayoutManager);
        allMealsRecycler.setItemAnimator(new DefaultItemAnimator());
        allMealsRecycler.setAdapter(dietAdpater);

        //get meals from local database
        db = new DatabaseHelper(this);
        db.getDiets(dietList);
        dietAdpater.notifyDataSetChanged();


    }

    @Override
    protected void onResume() { // refresh meals list
        super.onResume();
        db.getDiets(dietList);
        dietAdpater.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_calendar) {



            picker.show(getFragmentManager(),"date");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // open add meal activity
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create) {
            Intent myIntent = new Intent(this, Add_meal.class);
            this.startActivity(myIntent);

        } else if (id == R.id.nav_login) {
            Intent myIntent = new Intent(this,Login.class);
            this.startActivity(myIntent);

        } else if (id == R.id.nav_register) {
            Intent myIntent = new Intent(this,RegisterActivity.class);
            this.startActivity(myIntent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void DatePicked() {
        Long pickerDate = picker.getPickedDate();
        db.getDietsSpecifiedTime(dietList,pickerDate);
        dietAdpater.notifyDataSetChanged();
    }
}
