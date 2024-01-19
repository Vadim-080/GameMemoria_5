package com.example.gamememoria;


import static com.example.gamememoria.Menu.Key_Slognost_game;
import static com.example.gamememoria.Menu.Key_Slognost_step;
import static com.example.gamememoria.Menu.Key_Slognost_time;
import static com.example.gamememoria.Menu.koef_slogn_step;
import static com.example.gamememoria.Menu.koef_slogn_time;
import static com.example.gamememoria.Menu.mSettings;

import static com.example.gamememoria.Menu.slognost_game;
import static com.example.gamememoria.Zastavka.povtorTriGameOverPodrad;
import static com.example.gamememoria.Zastavka.promegutGameOverPodrad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class Slognost extends AppCompatActivity {

    Button zabiv, novi, opit, mast, exit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slognost);

        zabiv = findViewById(R.id.buZabiv);
        novi = findViewById(R.id.buNovishok);
        opit = findViewById(R.id.buOpitn);
        mast = findViewById(R.id.buMaster);
        exit = findViewById(R.id.buExit);

        onResume();
        povtorTriGameOverPodrad = false;

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

        if (slognost_game > 1) {
            promegutGameOverPodrad = 0;
        }

        slognost_game = 1;
        koef_slogn_time = 3;
        koef_slogn_step = 8;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Novi() {
        if (slognost_game > 2) {
            promegutGameOverPodrad = 0;
        }
        slognost_game = 2;
        koef_slogn_time = 1;
        koef_slogn_step = 4;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Opit() {
        if (slognost_game > 3) {
            promegutGameOverPodrad = 0;
        }
        slognost_game = 3;
        koef_slogn_time = 0;
        koef_slogn_step = 0;

        onPause();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Mast() {
        slognost_game = 4;
        koef_slogn_time = -1;
        koef_slogn_step = -2;

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

        SharedPreferences.Editor a3 = mSettings.edit();
        a3.putInt(String.valueOf(Key_Slognost_game), slognost_game);
        a3.apply();

    }

    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Slognost_game))) {
            slognost_game = mSettings.getInt(String.valueOf(Key_Slognost_game), 0);
        } else slognost_game = 1;
    }

}
