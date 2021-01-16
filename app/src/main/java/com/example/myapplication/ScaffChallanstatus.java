package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import com.example.myapplication.Geolocation;

public class ScaffChallanstatus extends AppCompatActivity {

    Context context;
    private static ScaffChallanstatus ScaffChallanstatus_instance;
    FusedLocationProviderClient fusedLocationProviderClient;  // used in getting Location
   // private static final int FILE_SELECT_CODE = 0;  // used in selecting file
    static final int REQUEST_IMAGE_CAPTURE_BTN2 = 2 , REQUEST_IMAGE_CAPTURE_BTN3 = 3;   // used in capture image

    AlertDialog.Builder builder;  // used to show location to user

    TextView text_challan_no ,Scaff_status_txt1 ,Scaff_status_txt2,Scaff_status_txt3;
    Button  Scaff_status_photobtn2 ,Scaff_status_photobtn3 ;
    private  String TYPE, Challan_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaffolder_challanstatus);
        ScaffChallanstatus_instance = this ;

        fusedLocationProviderClient    = LocationServices.getFusedLocationProviderClient(this); // intialize
        context = getBaseContext();
        builder = new AlertDialog.Builder(this);

        text_challan_no = (TextView) findViewById(R.id.challanno);
        Scaff_status_txt1 = (TextView) findViewById(R.id.Scaff_status_txt1);
        Scaff_status_txt2 = (TextView) findViewById(R.id.Scaff_status_txt2);
        Scaff_status_txt3 = (TextView) findViewById(R.id.Scaff_status_txt3);

        Intent intent = getIntent();
        Challan_no = intent.getStringExtra("Challan_no");
        text_challan_no.setText(Challan_no);

        TYPE =  intent.getStringExtra("CallType");

        if(TYPE.equals("Pickup")){
            Scaff_status_txt1.setText("Reached at Location");
            Scaff_status_txt2.setText("Pickup/Material Done");
            Scaff_status_txt3.setText("Moved to Warehouse");
        }

        Scaff_status_photobtn2 = (Button) findViewById(R.id.Scaff_status_photobtn2);
        Scaff_status_photobtn3 = (Button) findViewById(R.id.Scaff_status_photobtn3);


        Scaff_status_photobtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_BTN2);  // start the camera activity
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }
        });

        Scaff_status_photobtn3.setOnClickListener(new View.OnClickListener() {
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

    public static ScaffChallanstatus getInstance() {
        return ScaffChallanstatus_instance;
    }

    public void Verify_geolocation(View view) {

        if (ActivityCompat.checkSelfPermission(ScaffChallanstatus.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GetLocation(); // if permission granted than call the getlocation() method
        } else {
            Context context = getApplicationContext();
            CharSequence text = "TURN ON YOUR LOCATION PLS ..";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            ActivityCompat.requestPermissions(ScaffChallanstatus.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }
    public void GetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                // intialize location
                Location location = task.getResult();
                // intialize address list
                if (location != null) try {
                    Geocoder geocoder = new Geocoder(ScaffChallanstatus.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                   // System.out.println("Aman Your Location at " + addresses.get(0).getAddressLine(0));

                    BackendFunction backendFunction = new BackendFunction(context);
                    String lat = String.valueOf(location.getLatitude());
                    String lng = String.valueOf(location.getLongitude());
                    backendFunction.storelocation(lng, lat , Challan_no);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "TURN ON YOUR LOCATION PLS ..";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });
    }

    public void alert(String string){
        //Setting message manually and performing action on button click
        builder.setMessage(string)
                .setCancelable(false)
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ScaffChallanstatus.this, Dashboard.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"BACK", Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("YOUR LOCATION");
        alert.show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE_BTN2:
                if (requestCode == REQUEST_IMAGE_CAPTURE_BTN2 && resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();
                Bitmap    imageBitmap = (Bitmap) extras.get("data"); // get image bitmap data

                    Intent intent = new Intent(this, Previewscreen.class);
                    intent.putExtra("image",imageBitmap); // send to another activity
                    intent.putExtra("data", 1);
                    intent.putExtra("Challan_no", Challan_no);
                    intent.putExtra("Action","challan_file");

                    startActivity(intent);

                }
                break;
            case REQUEST_IMAGE_CAPTURE_BTN3:
                if (requestCode == REQUEST_IMAGE_CAPTURE_BTN3 && resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();
                    Bitmap    imageBitmap = (Bitmap) extras.get("data"); // get image bitmap data

                    Intent intent = new Intent(this, Previewscreen.class);
                    intent.putExtra("image",imageBitmap); // send to another activity
                    intent.putExtra("data", 1);
                    intent.putExtra("Challan_no", Challan_no);
                    if(TYPE.equals("Pickup")){
                        intent.putExtra("Action","vehicle_moved");
                    }else {
                        intent.putExtra("Action","material_installed");
                    }
                    startActivity(intent);

                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}




/* intent for selcting file from storage



    public void selectfile(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/ /*");                        //REMOVE '/'
       /*  intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

    }

            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    /* Passing ImageURI to the Second Activity
Intent intent = new Intent(this, Previewscreen.class);
                    intent.setData(uri);
                            intent.putExtra("data", 2);
                            intent.putExtra("Challan_no", Challan_no);
                            intent.putExtra("Action","vehicle_loaded_unloaded");
                            startActivity(intent);
                            }
                            break;

     */
