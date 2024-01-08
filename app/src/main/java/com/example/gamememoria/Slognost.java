package com.example.gamememoria;




import static com.example.gamememoria.Menu.Key_Slognost_step;
import static com.example.gamememoria.Menu.Key_Slognost_time;
import static com.example.gamememoria.Menu.Key_Uroven;
import static com.example.gamememoria.Menu.koef_slogn_step;
import static com.example.gamememoria.Menu.koef_slogn_time;
import static com.example.gamememoria.Menu.mSettings;

import static com.example.gamememoria.Menu.uroven;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Slognost extends AppCompatActivity {

    Button zabiv, novi, opit, mast, exit;
    Chronometer timeBonus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slognost);

        zabiv = findViewById(R.id.buZabiv);
        novi = findViewById(R.id.buNovishok);
        opit = findViewById(R.id.buOpitn);
        mast = findViewById(R.id.buMaster);
        exit = findViewById(R.id.buExit);

        zabiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zabiv();
            }
        });

        novi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Novi();
            }
        });

        opit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opit();
            }
        });

        mast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mast();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Zabiv() {

        koef_slogn_time = 2;
        koef_slogn_step = 6;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Novi() {

        koef_slogn_time = 1;
        koef_slogn_step = 2;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Opit() {

        koef_slogn_time = -1;
        koef_slogn_step = 0;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Mast() {

        koef_slogn_time = -1;
        koef_slogn_step= -2;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onPause() {    // Запоминаем данные
        super.onPause();

        SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_Slognost_time), koef_slogn_time);
        a1.apply();

        SharedPreferences.Editor a2 = mSettings.edit();
        a2.putInt(String.valueOf(Key_Slognost_step), koef_slogn_step);
        a2.apply();

    }

}
