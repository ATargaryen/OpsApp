package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import nl.bravobit.ffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

public class VideoPreview extends AppCompatActivity  {
    VideoView videoView;
  private   Uri video_data ;
  private   String videoString , VideoPath;
  private   int Source ;
    Context mContext;
    private  String Challan_no ,challantype, Action ;
    Integer back_token = 0;


    Button UploadButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        videoView = (VideoView)findViewById(R.id.videoView);
        UploadButton = (Button)findViewById(R.id.UploadButton);
        back_token = 0;

        UploadButton.setEnabled(true);
        Intent intent = getIntent();

        Source = intent.getIntExtra("data",0);
        Challan_no = intent.getStringExtra("Challan_no");
        challantype = intent.getStringExtra("challantype");
        Action = intent.getStringExtra("Action");
        videoString = intent.getStringExtra("video_string_uri");
        VideoPath = intent.getStringExtra("video_path");   // path of video file using to upload server
        video_data = Uri.parse(videoString);             // video data used in preview video
        videoView.setVideoURI(video_data);  // set videodata to videoview widget

        MediaController mediaController = new MediaController(this); // MediaController Class is used to provide set of controls like buttons like "Play/Pause", "Rewind", "Fast Forward" and a progress slider
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

       // videoView.requestFocus();
        videoView.start(); // run the video


    }

    public void UploadVideo(View view) throws FFmpegCommandAlreadyRunningException {

            LoadingDialog.start(VideoPreview.this,"Video Uploading");
            UploadButton.setEnabled(false);
            back_token = 1;


            JSONObject data = new JSONObject();  // create Data object  which send to server
            try {
                data.put("challanid", Challan_no);
                data.put("action", Action);
                data.put("userRole", Constant.ROLE);
                data.put("challantype",challantype);

                BackendFunction backendFunction = new BackendFunction(this);
                backendFunction.Compress_and_upload_Video(VideoPath, data);
                UploadButton.setEnabled(true);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    @Override
    public void onBackPressed() {

        if (back_token==0) {
            super.onBackPressed();
        }
        if (back_token==1) {
              // do nothing
        }
    }

}

/*          thumbnail of a video
*                       Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND);
*
*
* */