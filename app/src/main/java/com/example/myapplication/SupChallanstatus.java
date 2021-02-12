package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.ScaffChallanstatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SupChallanstatus extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE_BTN1 = 1 , REQUEST_VIDEO_CAPTURE_BTN2 =2, REQUEST_IMAGE_CAPTURE_BTN3 = 3;
    private static final int FILE_SELECT_CODE = 2;  // used in selecting file
    ImageView imageView;
    Bitmap imageBitmap;
    TextView text_challan_no ,Sup_status_txt1 ,Sup_status_txt2,Sup_status_txt3;
    Button Sup_status_photobtn1,Sup_status_vidbtn2,Sup_status_photobtn3;
    private   String TYPE;

    private  String Challan_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challanstatus);

        Intent intent = getIntent();

        Challan_no = intent.getStringExtra("Challan_no");

        imageView = (ImageView) findViewById(R.id.preview_imagescreen);
        text_challan_no = (TextView) findViewById(R.id.challanno);
        Sup_status_txt1 = (TextView) findViewById(R.id.Sup_status_txt1);
        Sup_status_txt2 = (TextView) findViewById(R.id.Sup_status_txt2);
        Sup_status_txt3 = (TextView) findViewById(R.id.Sup_status_txt3);


        text_challan_no.setText(Challan_no);

        TYPE =  intent.getStringExtra("CallType");

        if(TYPE.equals("Pickup")){
            Sup_status_txt1.setText("Unloading  Done");
            Sup_status_txt2.setText("Material Verification");
            Sup_status_txt3.setText("Damage &  Segregation");
        }

        Sup_status_photobtn1 = (Button) findViewById(R.id.Sup_status_photobtn1);
        Sup_status_vidbtn2 = (Button) findViewById(R.id.Sup_status_vidbtn2);
        Sup_status_photobtn3 = (Button) findViewById(R.id.Sup_status_photobtn3);

        get_steps_toVerify(Challan_no,"Supervisor");
        Sup_status_photobtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_BTN1);  // start the camera activity
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }
        });

        Sup_status_vidbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE_BTN2);
                }
            }
        });

        Sup_status_photobtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_BTN3);  // start the camera activity
                    } catch (ActivityNotFoundException e) {
                        // display error state to the user
                    }
                }
        });
    }

    private void get_steps_toVerify(String challan_no, String role) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.ROOT_URL+"Stepsverifi?challanid="+challan_no+"&role="+role;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            disable_buttons(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void disable_buttons(JSONObject jsonObject) throws JSONException {
        if(TYPE.equals("Pickup")){

            if(!jsonObject.getString("vehicle_loaded_unloaded").equals("null"))
                Sup_status_photobtn1.setEnabled(false);
            if(!jsonObject.getString("material_verification").equals("null"))
                Sup_status_vidbtn2.setEnabled(false);
            if(!jsonObject.getString("material_segregation").equals("null"))
                Sup_status_photobtn3.setEnabled(false);

        }else
        {
            if(!jsonObject.getString("material_allocated").equals("null"))
                Sup_status_photobtn1.setEnabled(false);
            if(!jsonObject.getString("material_verification").equals("null"))
                Sup_status_vidbtn2.setEnabled(false);
            if(!jsonObject.getString("vehicle_loaded_unloaded").equals("null"))
                Sup_status_photobtn3.setEnabled(false);
        }

    }


    @Override   // used to show it on Imageframe
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_VIDEO_CAPTURE_BTN2:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    String path = getRealPathFromURI(uri);         // give us path of uri means where is file in phone
             //       System.out.println("pathoffile"+path);
                    /* Passing ImageURI to the Second Activity */
                    Intent intent = new Intent(this, VideoPreview.class);
                    intent.putExtra("video_string_uri", uri.toString());   // uri to string conversion
                    intent.putExtra("data", 2);
                    intent.putExtra("Challan_no", Challan_no);
                    intent.putExtra("video_path", path);
                    intent.putExtra("Action","material_verification");
                    startActivity(intent);
                }
                break;
            case REQUEST_IMAGE_CAPTURE_BTN1:
                if (resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();
                    Bitmap    imageBitmap = (Bitmap) extras.get("data"); // get image bitmap data

                    Intent intent = new Intent(this, Previewscreen.class);
                    intent.putExtra("image",imageBitmap); // send to another activity
                    intent.putExtra("data", 1);
                    intent.putExtra("Challan_no", Challan_no);
                    if(TYPE.equals("Pickup")){
                        intent.putExtra("Action","vehicle_loaded_unloaded");
                    }else {
                        intent.putExtra("Action","material_allocated");
                    }


                    startActivity(intent);

                }
                break;
            case REQUEST_IMAGE_CAPTURE_BTN3:
                if (resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();
                    Bitmap    imageBitmap = (Bitmap) extras.get("data"); // get image bitmap data

                    Intent intent = new Intent(SupChallanstatus.this, Previewscreen.class);
                    intent.putExtra("image",imageBitmap); // send to another activity
                    intent.putExtra("data", 1);
                    intent.putExtra("Challan_no", Challan_no);
                    if(TYPE.equals("Pickup")) {
                        intent.putExtra("Action", "material_segregation");
                    }else{
                        intent.putExtra("Action", "vehicle_loaded_unloaded");
                    }

                    startActivity(intent);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}