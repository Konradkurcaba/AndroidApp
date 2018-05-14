package com.example.konrad.app;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Add_meal extends AppCompatActivity {

    private Spinner mealKindSpinner;
    private TextInputEditText summary_input;
    private TextInputEditText descritpion_input;
    private DatabaseHelper db = new DatabaseHelper(this);
    private Button mealDateButton;
    private Button add_meal_button;
    private DatePicker picker;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    private Bitmap mealImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);

        // Setup fields
        mealKindSpinner = (Spinner) findViewById(R.id.mealKindSpinner);
        summary_input = (TextInputEditText) findViewById(R.id.summary_input);
        descritpion_input = (TextInputEditText) findViewById(R.id.description_input);
        imageView = (ImageView) findViewById(R.id.mealView);
        imageView.setVisibility(View.GONE);

        // Setup spinner with chose meal kind
        mealKindSpinner = findViewById(R.id.mealKindSpinner);
        String[] items = new String[]{"Sniadanie", "Drugie Sniadanie", "Obiad", "Podwieczorek", "Kolacja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mealKindSpinner.setAdapter(adapter);

        // Setup Image Picker button
        Button imagePickButton = (Button) findViewById(R.id.imagePicker);
        imagePickButton.setOnClickListener((event) -> {

            loadImageFromStorage();

        });

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
        add_meal_button.setOnClickListener((event) -> {
            addMealToDatabase();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                mealImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                imageView.setImageBitmap(mealImage);
                imageView.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void addMealToDatabase()
    {
        String imageFilePath = "";

        if(mealImage != null)
        {
            try {
                Long uniqueFileName = System.currentTimeMillis();
                File file = new File(getApplicationContext().getFilesDir(), uniqueFileName.toString());
                OutputStream saveImageOutputStream = new FileOutputStream(file);
                mealImage.compress(Bitmap.CompressFormat.JPEG,85,saveImageOutputStream);
                imageFilePath = file.getPath().toString();
            }
            catch(IOException exception)
            {

            }

        }

        Diet diet = new Diet(mealKindSpinner.getSelectedItem().toString(),summary_input.getText().toString(),descritpion_input.getText().toString(),
                picker.getPickedDate(),imageFilePath );

        db.instertDiet(diet);

    }
    private void loadImageFromStorage()
    {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


}

