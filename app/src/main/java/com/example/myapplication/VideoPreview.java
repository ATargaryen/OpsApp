package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
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


    Button UploadButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        videoView = (VideoView)findViewById(R.id.videoView);
        UploadButton = (Button)findViewById(R.id.UploadButton);

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
           getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); // disable touch on screen
        //  getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);  enable touch

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
}

/*          thumbnail of a video
*                       Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND);
*
*
* */