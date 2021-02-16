package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {

     static    AlertDialog.Builder builder;  // used to show location to user
     static   AlertDialog alert;


     static void start(Activity activity_context,String msg){

        /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); // disable touch on screen
         getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//  enable touch  */

        //Setting message manually and performing action on button click
        builder = new AlertDialog.Builder(activity_context);
        LayoutInflater layoutInflater = activity_context.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loading_dialog,null));
        builder.setTitle(msg);
      // builder.setMessage();
        builder.setCancelable(false);
        //Creating dialog box
        alert = builder.create();
        alert.show();

    }
    static void close(){
        alert.dismiss();
    }
}
