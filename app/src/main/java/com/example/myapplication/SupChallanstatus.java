package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.ScaffChallanstatus;

public class SupChallanstatus extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int FILE_SELECT_CODE = 0;  // used in selecting file
    ImageView imageView;
    Bitmap imageBitmap;
    TextView text_challan_no ,Sup_status_txt1 ,Sup_status_txt2,Sup_status_txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challanstatus);

        Intent intent = getIntent();

        String Challan_no = intent.getStringExtra("Challan_no");

        imageView = (ImageView) findViewById(R.id.preview_imagescreen);
        text_challan_no = (TextView) findViewById(R.id.challanno);
        Sup_status_txt1 = (TextView) findViewById(R.id.Sup_status_txt1);
        Sup_status_txt2 = (TextView) findViewById(R.id.Sup_status_txt2);
        Sup_status_txt3 = (TextView) findViewById(R.id.Sup_status_txt3);



        text_challan_no.setText(Challan_no);



        String callFrom =  intent.getStringExtra("CallType");

        if(callFrom.equals("Pickup")){
            Sup_status_txt1.setText("Unloading  Done");
            Sup_status_txt2.setText("Material Verification");
            Sup_status_txt3.setText("Damage &  Segregation");
        }

    }

    public void OpenCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);  // start the camera activity
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    public  void selectvideo(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override   // used to show it on Imageframe
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    /* Passing ImageURI to the Second Activity */
                    Intent intent = new Intent(this, Previewscreen.class);
                    intent.setData(uri);
                    intent.putExtra("data", 2);
                    startActivity(intent);
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();
                    Bitmap    imageBitmap = (Bitmap) extras.get("data"); // get image bitmap data

                    Intent intent = new Intent(this, Previewscreen.class);
                    intent.putExtra("image",imageBitmap); // send to another activity
                    intent.putExtra("data", 1);
                    startActivity(intent);

                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}