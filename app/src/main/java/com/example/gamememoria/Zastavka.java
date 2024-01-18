package com.example.gamememoria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Zastavka extends AppCompatActivity {

    private TextView nazvGame, stimulirNadp;
    private ImageView emblema;
    public static int promegutGameOverPodrad=0; // промежут перемен для расчета количества проигрышей подряд

    public static boolean povtorTriGameOverPodrad=false; // промежут перемен для расчета количества проигрышей подряд

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zastavkaa);

        nazvGame = findViewById(R.id.nazvGame_view);
        emblema = findViewById(R.id.emblem_view);
        stimulirNadp = (TextView) findViewById(R.id.stimulirNadp_view);

        Typeface type1 = getResources().getFont(R.font.vanowitsch);    // шрифт
        nazvGame.setTypeface(type1);

        Animation m1 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim1);
        emblema.startAnimation(m1);

        Animation m2 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim2);   // Плавное появление экрана
        stimulirNadp.startAnimation(m2);

        Animation m3 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim3);
        nazvGame.startAnimation(m3);

        final Intent i = new Intent(this, Menu1.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(6000);  // Время продолжительности заставки
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
