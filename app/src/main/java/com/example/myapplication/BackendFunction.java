package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class BackendFunction  extends AsyncTask<Bitmap,Void,String>{
    private Context context;
    private String Challan_no = "default" ;
    private String Action = "default";
    private String Role = "default" ;
    private String MEDIA = null ;


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


    public  void uploadMedia(Bitmap media, String challanid ,String action , String role) {
          Challan_no = challanid ; Action = action ; Role = role ;
          execute(media);   // call to start the background thread execution
    }


    @Override
    protected String doInBackground(Bitmap ... bitmaps) {
        // Background thread

        // this will convert the bitmap to string so we can send it through URL
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmaps[0].compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] imagebytearray = byteArrayOutputStream.toByteArray();
        String encodedimageforupload = Base64.encodeToString(imagebytearray, Base64.DEFAULT);
        return encodedimageforupload;
    }

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected void onPostExecute(String result) {
        // DO what after doInBackground process complete
        MEDIA  = result ;

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Constant.ROOT_URL + "challanverify";

            StringRequest sr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("HttpClient", "success! response: " + response.toString());
                         //   Toast.makeText(context,""+response.toString()+" UPLOADED",Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                            Toast.makeText(context,"Server_Error",Toast.LENGTH_LONG).show();
                        }

                    })
            {  // pass request body
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("media",MEDIA);
                    params.put("challanid",Challan_no);
                    params.put("action",Action);
                    params.put("Role",Role);
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };  // you are sending a imagestring so it takes too much time So you need to set it
            sr.setRetryPolicy(new DefaultRetryPolicy(120000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(sr);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);  // eq.. SHow Downloading percentage   .. process update
    }
}

/*---------------------------------------- AsyncTask -------------------------------*/
/* AsyncTask is used to do heavy processing (HTTP request who took more time,DB operation ,  image processing ) in another thread (doInBackground) because if main thread do such thing its hold or stop the App UI execution ....
So better approach is do such things in another thread ,, many other thing also for doing Async is one of them
 */
