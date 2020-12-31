package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class Previewscreen extends AppCompatActivity {

    ImageView img_to_be_zoomed;
    Bitmap camera_img_bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewscreen);

        img_to_be_zoomed = (ImageView)findViewById(R.id.preview_imagescreen);

        Intent intent_camera = getIntent();
        camera_img_bitmap =  intent_camera.getParcelableExtra("image");     /* Getting ImageBitmap from Camera from Challanstatus Activity */

        if (camera_img_bitmap != null) {
            img_to_be_zoomed.setImageBitmap(camera_img_bitmap); // set bitmap to Imageview
        }
    }
}