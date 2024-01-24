package com.example.tictactoe;

import static com.example.tictactoe.R.id.txtAnim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView txtAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtAnim = findViewById(R.id.txtAnim);

        Animation move = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
        Intent iHome = new Intent(SplashActivity.this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iHome);
                txtAnim.setAnimation(move);
                txtAnim.startAnimation(move);
                finish();
            }
        },4000);

    }
}