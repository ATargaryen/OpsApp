package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Previewscreen extends AppCompatActivity {

    ImageView imageview;
    Bitmap bitmap ;
    int Source ;
    Context mContext;
    private  String Challan_no , Action ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewscreen);

        imageview = (ImageView)findViewById(R.id.preview_imagescreen);

        Intent intent = getIntent();

        Source = intent.getIntExtra("data",0);
        Challan_no = intent.getStringExtra("Challan_no");
        Action = intent.getStringExtra("Action");



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
    public void uploadfile(View view ){

        Bitmap imagebitmap = ((BitmapDrawable)imageview.getDrawable()).getBitmap();
        BackendFunction backendFunction = new BackendFunction(this);
        backendFunction.uploadMedia(imagebitmap,Challan_no,Action,Constant.ROLE);

      //  Toast.makeText(getApplicationContext(),"uploaded",Toast.LENGTH_LONG).show();
    }
}