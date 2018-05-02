package com.example.konrad.app;


import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Add_meal extends AppCompatActivity {

    private Spinner mealKindSpinner;
    private TextInputEditText summary_input;
    private TextInputEditText descritpion_input;
    private DatabaseHelper db = new DatabaseHelper(this);
    private Button mealDateButton;
    private Button add_meal_button;
    private DatePicker picker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);

        // Setup fields
        mealKindSpinner = (Spinner) findViewById(R.id.mealKindSpinner);
        summary_input = (TextInputEditText) findViewById(R.id.summary_input);
        descritpion_input = (TextInputEditText) findViewById(R.id.description_input);

        // Setup spinner with chose meal kind
        mealKindSpinner = findViewById(R.id.mealKindSpinner);
        String[] items = new String[]{"Sniadanie", "Drugie Sniadanie", "Obiad", "Podwieczorek", "Kolacja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mealKindSpinner.setAdapter(adapter);

        //Setup button with Date picker
        mealDateButton = (Button) findViewById(R.id.dateButton);
        picker = new DatePicker();
        mealDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getFragmentManager(),"date");
            }
        });

        // Setup add meal button
        add_meal_button = (Button) findViewById(R.id.button);
        add_meal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Diet diet = new Diet(mealKindSpinner.getSelectedItem().toString(),summary_input.getText().toString(),descritpion_input.getText().toString(),
                        picker.getPickedDate() );

                db.instertDiet(diet);
            }
        });






    }
}

