package android.example.musiclibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class Player extends AppCompatActivity {
    ProgressBar spinner;
    ImageView fullScreenop;
    FrameLayout frameLayout;
    VideoView videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        spinner = findViewById(R.id.progressBar);
        fullScreenop = findViewById(R.id.fullScreenOp);
        frameLayout = findViewById(R.id.frameLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        Bundle data = i.getExtras();
        Video v = (Video) data.getSerializable("videoData");
        getSupportActionBar().setTitle(v.getTitle());
        TextView title = findViewById(R.id.videoTitle);
        TextView desc = findViewById(R.id.Description);
        desc.setText(v.getDescription());
        final VideoView videoPlayer = findViewById(R.id.videoView);
        title.setText(v.getTitle());
        final Uri videoUrl = Uri.parse(v.getVideoUrl());
        videoPlayer.setVideoURI(videoUrl);
        MediaController mc = new MediaController(this);
        videoPlayer.setMediaController(mc);
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoPlayer.start();
                spinner.setVisibility(View.GONE);
            }
        });
        fullScreenop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreenop.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getSupportActionBar().hide();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
                videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        fullScreenop.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int heightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, getResources().getDisplayMetrics());
        frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightValue)));
        videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightValue)));

        int orentation = getResources().getConfiguration().orientation;
        if (orentation == Configuration.ORIENTATION_PORTRAIT) {
            super.onBackPressed();
        }

    }
}