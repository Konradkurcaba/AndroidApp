package com.example.konrad.app;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.File;
import java.io.FileNotFoundException;
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
    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private AdView mAdView;

    private Uri imageUri;
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
        String[] items = new String[]{"Sniadanie", "II Sniadanie", "Obiad", "Podwieczorek", "Kolacja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mealKindSpinner.setAdapter(adapter);

        // Setup Image Picker button
        Button imagePickButton = (Button) findViewById(R.id.imagePicker);
        imagePickButton.setOnClickListener((event) -> {
            loadAndResizeImage();
        });

        //Setup button with Date picker
        mealDateButton = (Button) findViewById(R.id.dateButton);
        picker = new DatePicker();
        mealDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getFragmentManager(),"date");
            }
        }
        );

        // Setup add meal button
        add_meal_button = (Button) findViewById(R.id.button);
        add_meal_button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View event) {

                try {
                    addMealToDatabase();
                    Toast.makeText(getApplicationContext(),
                            "Dodano nowy przepis!", Toast.LENGTH_SHORT).show();
                    summary_input.setText("");
                    descritpion_input.setText("");
                    mealImage = null;
                    imageView.setVisibility(View.GONE);
                }catch (FileNotFoundException exception)
                {
                    Toast.makeText(getApplicationContext(),
                            "Cos poszlo nie tak", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //setup ad banner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    View.OnClickListener buttonOnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),
                    "Dodano nowy przepis!", Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    mealImage = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    mealImage = Bitmap.createScaledBitmap(mealImage,1080,720,true);
                    imageView.setImageBitmap(mealImage);
                    imageView.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



    private void addMealToDatabase() throws FileNotFoundException {
        String imageFilePath = "";

        if(mealImage != null)
        {
            Long uniqueFileName = System.currentTimeMillis();
            File file = new File(getApplicationContext().getFilesDir(), uniqueFileName.toString());
            OutputStream saveImageOutputStream = new FileOutputStream(file);
            mealImage.compress(Bitmap.CompressFormat.JPEG,85,saveImageOutputStream);
            imageFilePath = file.getPath().toString();
        }
        Diet diet = new Diet(mealKindSpinner.getSelectedItem().toString(),summary_input.getText().toString(),descritpion_input.getText().toString(),
                picker.getPickedDate(),imageFilePath );

        db.instertDiet(diet);

    }

    private void loadAndResizeImage()
    {
        CropImage.activity().setAspectRatio(3,2).setFixAspectRatio(true).start(this);
    }


}

