package com.example.gamememoria;

import static com.example.gamememoria.B_Menu.APP_PREFERENCES;
import static com.example.gamememoria.B_Menu.Key_Urov_Bridgs;
import static com.example.gamememoria.B_Menu.Key_Urov_Volum;
import static com.example.gamememoria.B_Menu.Key_pologen_regul_Bridgs;
import static com.example.gamememoria.B_Menu.Key_pologen_regul_Volum;
import static com.example.gamememoria.B_Menu.mSettings;
import static com.example.gamememoria.B_Menu.pologSostoyanSvernutogoPrilogen;
import static com.example.gamememoria.B_Menu.pologenRegulBridgs;
import static com.example.gamememoria.B_Menu.pologenRegulVolum;
import static com.example.gamememoria.B_Menu.zadanUrovBridgs;
import static com.example.gamememoria.B_Menu.zadanUrovVolume;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class A_Zastavka extends AppCompatActivity {

    private TextView nazvGame, stimulirNadp;
    private ImageView emblema;
    public static int promegutGameOverPodrad = 0; // промежут перемен для расчета количества проигрышей подряд

    public static boolean pologenieKnopkiMute = false;

    public static boolean povtorTriGameOverPodrad = false; // промежут перемен для расчета количества проигрышей подряд

    ConstraintLayout KartinraZadnegoPlana;
    MediaPlayer zvZastavka;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zastavka);

// Задаём уровень яркости
        zadanUrovBridgs = 80; // задаём уровень яркости при первом запуске Игры

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = zadanUrovBridgs;
        getWindow().setAttributes(layoutParams);


        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

        pologenRegulVolum = 0;
        pologenRegulBridgs = 0;
        onPause();

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        ConstraintLayout LinearLayout = findViewById(R.id.fon_zastavka_view);

        int currentVis = LinearLayout.getSystemUiVisibility();
        int newVis;
        if ((currentVis & View.SYSTEM_UI_FLAG_LOW_PROFILE) == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            newVis = View.SYSTEM_UI_FLAG_VISIBLE;
        } else {
            newVis = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        LinearLayout.setSystemUiVisibility(newVis);

        // Задаем цвет верхей строки и строки навигации
       /* if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.red)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }*/


// Задаем MAX яркость экрана

       /* final Dialog dialog = new Dialog(this) {
            @Override
            public void onAttachedToWindow() {
                super.onAttachedToWindow();
                WindowManager.LayoutParams layout = getWindow()
                        .getAttributes();
                layout.screenBrightness = 0.0f;
                getWindow().setAttributes(layout);
            }
        };
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();*/


       /* WindowManager.LayoutParams lp = getWindow().getAttributes();
        float brightness=0.1f;
        lp.screenBrightness = brightness;
        getWindow().setAttributes(lp);*/


        nazvGame = findViewById(R.id.nazvGame_view);
        emblema = findViewById(R.id.emblem_view);
        stimulirNadp = findViewById(R.id.stimulirNadp_view);
        KartinraZadnegoPlana = findViewById(R.id.fon_zastavka_view);

// Задаём звуковые сигналы
        zvZastavka = MediaPlayer.create(this, R.raw.zastavka_1);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int a = maxVolume * 30 / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, a, 0);
        zvZastavka.start();

        KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_zastavka1));  // задаем фоновое поле

        byte kartinka = (byte) (Math.random() * 4); // Случайное число от 0 до 3 -- Для выбора фоновое поле
        switch (kartinka) {
            case 0:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_zastavka1));
                break;
            case 1:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_zastavka2));
                stimulirNadp.setTextColor(Color.rgb(160, 255, 100));
                break;
            case 2:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_zastavka3));
                stimulirNadp.setTextColor(Color.rgb(255, 215, 0));
                break;
            case 3:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_zastavka4));
                stimulirNadp.setTextColor(Color.rgb(62, 2, 92));
                break;

        }

        Typeface type1 = getResources().getFont(R.font.vanowitsch);    // шрифт
        nazvGame.setTypeface(type1);

        Animation m1 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim1);
        emblema.startAnimation(m1);

        Animation m2 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim2);   // Плавное появление экрана
        stimulirNadp.startAnimation(m2);

        Animation m3 = AnimationUtils.loadAnimation(this, R.anim.zastav_anim3);
        nazvGame.startAnimation(m3);

        final Intent i = new Intent(this, B_Menu.class);
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

    @Override
    public void onPause() {    // Запоминаем данные
        super.onPause();

        SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_pologen_regul_Bridgs), pologenRegulBridgs);
        a1.apply();

        SharedPreferences.Editor a2 = mSettings.edit();
        a2.putInt(String.valueOf(Key_pologen_regul_Volum), pologenRegulVolum);
        a2.apply();

        SharedPreferences.Editor a3 = mSettings.edit();
        a3.putInt(String.valueOf(Key_Urov_Bridgs), zadanUrovBridgs);
        a3.apply();
    }

    // ПРЕКРАЩЕНИЕ Музыки при свертывании приложения
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        pologSostoyanSvernutogoPrilogen = true;
        zvZastavka.pause();
    }

}
