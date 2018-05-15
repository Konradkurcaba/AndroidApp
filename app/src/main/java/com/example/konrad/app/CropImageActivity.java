package com.example.konrad.app;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropImageActivity extends AppCompatActivity
        implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

    /** The crop image view library widget used in the activity */
    private CropImageView mCropImageView;

    /** Persist URI image to crop URI if specific permissions are required */
    private Uri mCropImageUri;

    /** the options that were set for the crop image */
    private CropImageOptions mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {

    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
