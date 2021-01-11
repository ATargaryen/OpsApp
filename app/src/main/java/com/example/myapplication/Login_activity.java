package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class Login_activity extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;
    String url = "http://192.168.0.40:8000/loginfvfd";
    public static String[] Challanlist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        UsernameEt = (EditText)findViewById(R.id.User_email);
        PasswordEt = (EditText)findViewById(R.id.UserPassword);
    }
    public void OnLogin(View view) {

    }

    public void go_to_dashboard(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        RequestQueue queue = Volley.newRequestQueue(this);
       // String url ="https://9a56cd96edf0.ngrok.io/loginauth?user="+ username+"&password="+password ;
        String url ="https://beaf9b92f6d7.ngrok.io/loginauth?user=amanponia@youngman.co.in&password=1234" ;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string

                        try {
                            JSONArray obj = new JSONArray(response);
                            for(int i=0; i < obj.length(); i++) {
                                JSONObject jsonobject = obj.getJSONObject(i);
                                String name       = jsonobject.getString("name");
                                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();

                                getChallanlist();
                             //   Intent intent = new Intent(Login_activity.this , Dashboard.class);
                              //  startActivity(intent);
                            }

                        } catch (JSONException e) {
                          //  e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"NOT AUTHENTICATED",Toast.LENGTH_LONG).show();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),type,Toast.LENGTH_LONG).show();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }



    private void getChallanlist() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://beaf9b92f6d7.ngrok.io/supervisor_challan?userid=910&type=Delivery" ;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string
                        try {
                            JSONArray obj = new JSONArray(response);
                             Challanlist = new String[obj.length()] ;
                            for(int i=0; i < obj.length(); i++) {
                                JSONObject jsonobject = obj.getJSONObject(i);
                                String name  = jsonobject.getString("challan_no"); System.out.println(name);
                                Challanlist[i] = name;
                            }

                            Intent intent = new Intent(Login_activity.this , Dashboard.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            //  e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"NOT AUTHENTICATED",Toast.LENGTH_LONG).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("error in getting challan");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public String[] getchallanlist(){
        return Challanlist ;
    }

}