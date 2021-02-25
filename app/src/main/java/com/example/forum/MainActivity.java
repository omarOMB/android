package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //variables
    private static int Splash = 5000;
    Animation topAnim, bottomAnim, rotateAnim;
    ImageView main_logo, small_logo, medium_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        rotateAnim = AnimationUtils.loadAnimation(this , R.anim.rotation);
        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        //Hooks
        main_logo = findViewById(R.id.main_logo);
        small_logo = findViewById(R.id.small_logo);

        small_logo.setAnimation(rotateAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, Login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(main_logo, "main_logo_trans");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs[0]);
                    MainActivity.this.startActivity(intent, options.toBundle());
                }

            }
        },Splash);

    }
}
