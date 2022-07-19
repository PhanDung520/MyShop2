package com.example.myshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop2.ActivityPackage.HomeScreenActivity;

public class MainActivity extends AppCompatActivity {
    private static  int SLASH_SCREEN = 5000;
    //variables
    Animation topAnim;
    Animation bottomAnim;
    ImageView imgLogo;
    TextView txtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //hoooks
        imgLogo = findViewById(R.id.imgLogo);
        txtLogo = findViewById(R.id.txtLogo);


        imgLogo.setAnimation(topAnim);
        txtLogo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0]  = new Pair<View, String> (imgLogo, "image_trandition");
                pairs[1]  = new Pair<View, String> (txtLogo, "logo_trandition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());

            }
        },SLASH_SCREEN);


    }
}