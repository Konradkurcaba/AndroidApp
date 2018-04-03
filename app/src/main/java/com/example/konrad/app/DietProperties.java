package com.example.konrad.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DietProperties extends AppCompatActivity {

    TextView sv;
    TextView dv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_properties);
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
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String summary = i.getStringExtra("summary");
        String description = i.getStringExtra("description");
        sv = (TextView) findViewById(R.id.desc);
        sv.setText(summary);
        dv = findViewById(R.id.longdesp);
        dv.setText(description);
        dv.setVisibility(TextView.VISIBLE);
        setTitle(title);
    }

}
