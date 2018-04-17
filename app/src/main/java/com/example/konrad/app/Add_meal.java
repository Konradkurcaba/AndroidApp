package com.example.konrad.app;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Add_meal extends AppCompatActivity {

    TextInputEditText tittle_input;
    TextInputEditText summary_input;
    TextInputEditText descritpion_input;
    DatabaseHelper db = new DatabaseHelper(this);
    Button add_meal_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);
        add_meal_button = (Button) findViewById(R.id.button);
        add_meal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tittle_input = (TextInputEditText) findViewById(R.id.title_input);
                summary_input = (TextInputEditText) findViewById(R.id.summary_input);
                descritpion_input = (TextInputEditText) findViewById(R.id.description_input);


                Diet diet = new Diet(tittle_input.getText().toString(),summary_input.getText().toString(),descritpion_input.getText().toString());
                db.instertDiet(diet);
                Intent i = getIntent();



            }
        });






    }
}
