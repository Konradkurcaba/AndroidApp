package com.example.konrad.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SynchronizeActivity extends AppCompatActivity {

    private BufferedReader reader;
    private PrintWriter writer;

    Button downloadMealsButton;
    Button uploadMealsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize);

        this.reader = Login.getBufferedReader();
        this.writer = Login.getWriter();

        downloadMealsButton = (Button) findViewById(R.id.downloadMealsButton);
        downloadMealsButton.setOnClickListener((event) -> {

            try {
                SyncTask task = new SyncTask(reader,writer, getApplicationContext());
                task.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        uploadMealsButton = (Button) findViewById(R.id.uploadMealsButton);



    }
}
