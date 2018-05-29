package com.example.konrad.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DietProperties extends AppCompatActivity {

    private TextView summaryTextView;
    private TextView desctiptionTextView;
    private ImageView mealImageView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dp_stare);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        //get diet fields
        int idMeal = i.getIntExtra("idMeal",0);
        String title = i.getStringExtra("title");
        String summary = i.getStringExtra("summary");
        String description = i.getStringExtra("description");
        Long mealDate = i.getLongExtra("mealDate",0);
        String imagePath = i.getStringExtra("imagePath");

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

        //display meal image or hide ImageView
        mealImageView = (ImageView) findViewById(R.id.mealImageView);
        if(imagePath != null && imagePath.length() > 0)
        {

            Bitmap mealImage = BitmapFactory.decodeFile(imagePath);
            mealImageView.setImageBitmap(mealImage);

        }else mealImageView.setVisibility(View.GONE);

        //add deleted button action

        Button deleteMealButton = (Button) findViewById(R.id.deleteMealButton);
        deleteMealButton.setOnClickListener(event ->
        {
            db = new DatabaseHelper(this);
            db.deleteDiet(idMeal);
            Toast.makeText(getApplicationContext(),
                    "Pomyslnie usunięto przepis", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}
