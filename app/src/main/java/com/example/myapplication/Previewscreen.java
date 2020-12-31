package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

public class Previewscreen extends AppCompatActivity {

    ImageView imageview;
    Bitmap bitmap ;
    int Source ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewscreen);

        imageview = (ImageView)findViewById(R.id.preview_imagescreen);

        Intent intent = getIntent();

        Source = intent.getIntExtra("data",0);



       if(Source == 1){  // IMAGE FROM CAMERA
           bitmap =  intent.getParcelableExtra("image");     /* Getting ImageBitmap from Camera from Challanstatus Activity */
            if (bitmap != null) {
                imageview.setImageBitmap(bitmap); // set bitmap to Imageview
            }
        }
        else if(Source == 2){ // IMAGE FROM FILE
            Uri selectedImgUri = intent.getData();
            if (selectedImgUri != null) {
                String[] selectedImgPath = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImgUri,
                        selectedImgPath, null, null, null);
                cursor.moveToFirst();

                int indexCol = cursor.getColumnIndex(selectedImgPath[0]);
                String imgPath = cursor.getString(indexCol);
                cursor.close();
                imageview.setImageBitmap(BitmapFactory.decodeFile(imgPath));
            }
        }


    }
}