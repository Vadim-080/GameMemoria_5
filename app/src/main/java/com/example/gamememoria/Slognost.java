package com.example.gamememoria;


import static com.example.gamememoria.Menu.Key_Koef_Pobed;
import static com.example.gamememoria.Menu.Key_Slognost_game;
import static com.example.gamememoria.Menu.Key_Slognost_step;
import static com.example.gamememoria.Menu.Key_Slognost_time;
import static com.example.gamememoria.Menu.koefPobed;
import static com.example.gamememoria.Menu.koef_slogn_step;
import static com.example.gamememoria.Menu.koef_slogn_time;
import static com.example.gamememoria.Menu.mSettings;

import static com.example.gamememoria.Menu.slognost_game;
import static com.example.gamememoria.Zastavka.povtorTriGameOverPodrad;
import static com.example.gamememoria.Zastavka.promegutGameOverPodrad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Slognost extends AppCompatActivity {

    Button zabiv, novi, opit, mast, exit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slognost);

        // Задаем цвет верхей строки и строки навигации
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }

        zabiv = findViewById(R.id.buZabiv);
        novi = findViewById(R.id.buNovishok);
        opit = findViewById(R.id.buOpitn);
        mast = findViewById(R.id.buMaster);
        exit = findViewById(R.id.buExit);

        onResume();
        povtorTriGameOverPodrad = false;

        switch (koefPobed) {
            case 1:
                BlokZabiv();
                break;
            case 2:
                BlokZabiv();
                BlokNovi();
                break;
            case 3:
                BlokZabiv();
                BlokNovi();
                BlokOpit();
                break;
        }

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

        if (mSettings.contains(String.valueOf(Key_Koef_Pobed))) {
            koefPobed = mSettings.getInt(String.valueOf(Key_Koef_Pobed), 0);
        } else koefPobed = 0;
    }

    private void BlokZabiv() {
        Button b1 = (Button) findViewById(R.id.buZabiv);  // Блокировка КНОПКИ
        b1.setEnabled(false);
        Button a1 = findViewById(R.id.buZabiv);   // ПРОЗРАЧНОСТЬ КНОПКИ
        a1.setAlpha(1f);
        a1.animate().alpha(0.2f).setDuration(1500);
    }

    private void BlokNovi() {
        Button b1 = (Button) findViewById(R.id.buNovishok);  // Блокировка КНОПКИ
        b1.setEnabled(false);
        Button a1 = findViewById(R.id.buNovishok);   // ПРОЗРАЧНОСТЬ КНОПКИ
        a1.setAlpha(1f);
        a1.animate().alpha(0.2f).setDuration(1500);
    }

    private void BlokOpit() {
        Button b1 = (Button) findViewById(R.id.buOpitn);  // Блокировка КНОПКИ
        b1.setEnabled(false);
        Button a1 = findViewById(R.id.buOpitn);   // ПРОЗРАЧНОСТЬ КНОПКИ
        a1.setAlpha(1f);
        a1.animate().alpha(0.2f).setDuration(1500);
    }

}
