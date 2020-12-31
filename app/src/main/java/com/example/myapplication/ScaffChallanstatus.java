package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ScaffChallanstatus extends AppCompatActivity {


    FusedLocationProviderClient fusedLocationProviderClient;  // used in getting Location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaffolder_challanstatus);

        fusedLocationProviderClient    = LocationServices.getFusedLocationProviderClient(this); // intialize

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

                    System.out.println("Aman Your Location at " + addresses.get(0).getAddressLine(0));

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

}