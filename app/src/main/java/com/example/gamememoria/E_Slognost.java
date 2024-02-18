package com.example.gamememoria;

import static com.example.gamememoria.B_Menu.Key_Koef_Pobed;
import static com.example.gamememoria.B_Menu.Key_Slognost_game;
import static com.example.gamememoria.B_Menu.Key_Slognost_step;
import static com.example.gamememoria.B_Menu.Key_Slognost_time;
import static com.example.gamememoria.B_Menu.fonMusic;
import static com.example.gamememoria.B_Menu.koefPobed;
import static com.example.gamememoria.B_Menu.koef_slogn_step;
import static com.example.gamememoria.B_Menu.koef_slogn_time;
import static com.example.gamememoria.B_Menu.mSettings;

import static com.example.gamememoria.B_Menu.pologSostoyanSvernutogoPrilogen;
import static com.example.gamememoria.B_Menu.slognost_game;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusic;
import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
import static com.example.gamememoria.A_Zastavka.povtorTriGameOverPodrad;
import static com.example.gamememoria.A_Zastavka.promegutGameOverPodrad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class E_Slognost extends AppCompatActivity {

    Button zabiv, novi, opit, mast, buMenu;
    ConstraintLayout KartinraZadnegoPlana;
    ImageButton mute;
    int urovenVolume, timeOnFonMusik;
    MediaPlayer mediaPlayer1, zvPerexV_Menu , zvMute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slognost);

        // Задаем цвет верхей строки и строки навигации
       /* if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }*/

        zabiv = findViewById(R.id.buZabiv);
        novi = findViewById(R.id.buNovishok);
        opit = findViewById(R.id.buOpitn);
        mast = findViewById(R.id.buMaster);
        buMenu = findViewById(R.id.buMenu);
        mute = findViewById(R.id.buMute);
        KartinraZadnegoPlana = findViewById(R.id.fon_slognost_view);
        mediaPlayer1 = MediaPlayer.create(this, R.raw.elektron1);

        // Задаём звуковые сигналы
        zvMute = MediaPlayer.create(this, R.raw.mute_1);
        zvPerexV_Menu = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1);

        KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_slognost));  // задаем фоновое поле

        mute.setAlpha(0.7f);    // ПРОЗРАЧНОСТЬ КНОПКИ Mute

        if (pologenieKnopkiMute == true) {
           /* urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            mute.setImageResource(R.drawable.mute);
        } else {
           /* urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            timeOnFonMusik = 8000;
            vklFonMusic();
            mute.setImageResource(R.drawable.zvuk);
        }

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

        buMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu();
            }
        });
    }

    private void Zabiv() {

        if (slognost_game > 1) {
            promegutGameOverPodrad = 0;
        }

        slognost_game = 1;
        koef_slogn_time = 2;
        koef_slogn_step = 6;

        Pusk();
    }

    private void Novi() {
        if (slognost_game > 2) {
            promegutGameOverPodrad = 0;
        }
        slognost_game = 2;
        koef_slogn_time = 0;
        koef_slogn_step = 3;

        Pusk();
    }

    private void Opit() {
        if (slognost_game > 3) {
            promegutGameOverPodrad = 0;
        }
        slognost_game = 3;
        koef_slogn_time = -1;
        koef_slogn_step = -1;

        Pusk();
    }

    private void Mast() {
        slognost_game = 4;
        koef_slogn_time = -2;
        koef_slogn_step = -3;

        Pusk();
    }

    private void Pusk() {
        onPause();
        Intent i = new Intent(this, C_MainActivity.class);
        startActivity(i);
    }

    private void Menu() {
        Intent i = new Intent(this, B_Menu.class);
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

    // ПРЕКРАЩЕНИЕ Музыки при свертывании приложения
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        pologSostoyanSvernutogoPrilogen = true;
        /*  fonMusic.pause();*/
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

    public void clickMute(View view) {

        if (pologenieKnopkiMute == false) {
            urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            pologenieKnopkiMute = true;
            fonMusic.pause();
            mute.animate().rotationYBy(180).setDuration(500);
            mute.setImageResource(R.drawable.mute);
        } else {
            mute.setImageResource(R.drawable.zvuk);
            urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            zvMute.start();
            timeOnFonMusik = 1500;
            vklFonMusic();
            pologenieKnopkiMute = false;
            mute.animate().rotationXBy(180).setDuration(500);
        }
    }
    private void regulirovUrovenVolume() {    //Этот код мгновенно установит громкость на уровень заданной переменной urovenVolume

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int a = maxVolume * urovenVolume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, a, 0);
    }
}
