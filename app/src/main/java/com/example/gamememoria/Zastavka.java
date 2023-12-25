package com.example.gamememoria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Zastavka extends AppCompatActivity {

    private TextView tv;
    private ImageView iv1, iv2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zastavka);

        tv = findViewById(R.id.tv);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);

        tv.setText("ТРЕНЕР  \n  ПАМЯТИ");

        Animation myanim1 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim1);
        Animation myanim2 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim2);
        Animation myanim3 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim3);
        iv1.startAnimation(myanim1);
        iv2.startAnimation(myanim2);
        tv.startAnimation(myanim3);
        final Intent i = new Intent(this, Menu.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();
    }
}
