package com.example.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BackendFunction {
    private Context context;

    public BackendFunction(Context context){
        this.context=context;
    }


    public void storelocation(String  longitude , String lattitude , String challan_no ){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://6f674c9370d1.ngrok.io/storelocation?longitude="+longitude+"&lattitude="+lattitude +"&challanid="+challan_no;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     ScaffChallanstatus.getInstance().alert("Stored"+longitude+":"+longitude);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
