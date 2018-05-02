package com.example.konrad.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DietProperties extends AppCompatActivity {

    private TextView summaryTextView;
    private TextView desctiptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_properties);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        //get diet fields
        String title = i.getStringExtra("title");
        String summary = i.getStringExtra("summary");
        String description = i.getStringExtra("description");
        Long mealDate = i.getLongExtra("mealDate",0);

        //write diet fields into TextViews
        summaryTextView = (TextView) findViewById(R.id.desc);
        summaryTextView.setText(summary);
        desctiptionTextView = findViewById(R.id.longdesp);
        desctiptionTextView.setText(description);
        desctiptionTextView.setVisibility(TextView.VISIBLE);

        //Create bar string, and get date from Unix time
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(mealDate);
        String barDescription = String.format("%-45s%8s",title, DateFormat.format("dd-MM-yyyy", cal).toString());
        setTitle(barDescription);
    }

}
