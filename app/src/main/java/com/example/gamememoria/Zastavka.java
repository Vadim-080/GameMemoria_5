package com.example.gamememoria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Zastavka extends AppCompatActivity {

    private TextView nazvGame, stimulirNadp;
    private ImageView emblema;
    public static int promegutGameOverPodrad=0; // промежут перемен для расчета количества проигрышей подряд

    public static boolean povtorTriGameOverPodrad=false; // промежут перемен для расчета количества проигрышей подряд

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zastavka);

        // Задаем цвет верхей строки и строки навигации
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }

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

        final Intent i = new Intent(this, Menu.class);
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
