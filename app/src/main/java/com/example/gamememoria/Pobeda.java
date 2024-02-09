package com.example.gamememoria;

import static com.example.gamememoria.MainActivity.StepCountIspolz;
import static com.example.gamememoria.MainActivity.bonusStep;
import static com.example.gamememoria.MainActivity.bonusTimeViv;
import static com.example.gamememoria.MainActivity.timeIstr;
import static com.example.gamememoria.Menu.Key_Koef_Pobed;
import static com.example.gamememoria.Menu.Key_Koef_Pobed_Max;
import static com.example.gamememoria.Menu.Key_Score;
import static com.example.gamememoria.Menu.Key_Score_Max;
import static com.example.gamememoria.Menu.Key_Slognost_Max;
import static com.example.gamememoria.Menu.Key_Time;
import static com.example.gamememoria.Menu.Key_Time_Max;
import static com.example.gamememoria.Menu.Key_Uroven;
import static com.example.gamememoria.Menu.Key_Uroven_Max;
import static com.example.gamememoria.Menu.bonusTime;
import static com.example.gamememoria.Menu.bonusTimeMax;
import static com.example.gamememoria.Menu.koefPobed;
import static com.example.gamememoria.Menu.koefPobedMax;
import static com.example.gamememoria.Menu.mSettings;
import static com.example.gamememoria.Menu.score;
import static com.example.gamememoria.Menu.scoreMax;
import static com.example.gamememoria.Menu.slognostMax;
import static com.example.gamememoria.Menu.slognost_game;
import static com.example.gamememoria.Menu.uroven;
import static com.example.gamememoria.Menu.urovenMax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class Pobeda extends AppCompatActivity {

    TextView result;
    ImageView smailPobeda;
    MediaPlayer mediaPlayer1;
    ConstraintLayout KartinraZadnegoPlana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pobeda);

        // Задаем цвет верхей строки и строки навигации
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }

        result = findViewById(R.id.Result_view);
        smailPobeda = findViewById(R.id.smailPobeda_view);
        KartinraZadnegoPlana = findViewById(R.id.fon_pobeda_view);

        mediaPlayer1 = MediaPlayer.create(this, R.raw.elektron1);

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // Разрешает тускнениия экрана телефона и его выключения во время игры

        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim_smail_pobeda);
        smailPobeda.startAnimation(a1);

        KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_pobeda));  // задаем фоновое поле

        if (slognost_game == 5) {
            result.setText("ПОЗДРАВЛЯЕМ" + " \n" + "ВЫ ПРОШЛИ ИГРУ" + " \n" + "НА МАКСИМАЛЬНОМ" + " \n" + "УРОВНЕ СЛОЖНОСТИ" + " \n" +
                    "У ВАС ОТЛИЧНАЯ ПАМЯТЬ" + " \n" + "ЗАРАБОТАНО МОНЕТ - " + bonusStep + " \n " + " \n" +
                    "БОНУСНОЕ ВРЕМЯ - " + (bonusTimeViv / 1000) + " сек");
            slognostMax = 1;
            bonusTimeMax = 0;
            bonusTime = 0;
            scoreMax = 0;
            score=0;
            urovenMax = 1;
            koefPobed = 0;
            koefPobedMax = 0;
            uroven = 1;
            onPause();
        } else {
            if (uroven == 23) {
                result.setText("ПОЗДРАВЛЯЕМ" + " \n" + "ВЫ ПРОШЛИ ИГРУ" + " \n" + "ПРЕДЛАГАЕМ УВЕЛИЧИТЬ СЛОЖНОСТЬ" + " \n" +
                        "И ТРЕНЕРОВАТЬСЯ ДАЛЬШЕ" + " \n" + "ЗАРАБОТАНО МОНЕТ - " + bonusStep + " \n " + " \n" +
                        "БОНУСНОЕ ВРЕМЯ - " + (bonusTimeViv / 1000) + " сек");
                uroven = 1;
                onPause();
            } else {
                result.setText("НОВЫЙ УРОВЕНЬ - " + uroven + " \n" + " \n" + "СДЕЛАНО ХОДОВ - " + StepCountIspolz + " \n" + " \n" +
                        "ВРЕМЯ ИГРЫ - " + timeIstr + " \n" + " \n" + "ЗАРАБОТАНО МОНЕТ - " + bonusStep + " \n " + " \n" +
                        "БОНУСНОЕ ВРЕМЯ - " + (bonusTimeViv / 1000) + " сек");
            }
        }
    }

    public void clickMenu(View v) {
        Intent intent = new Intent(this, Menu.class);   // Переход на другой класс
        startActivity(intent);
    }

    public void clickProdolgitGame(View v) {
        Intent intent = new Intent(this, MainActivity.class);   // Переход на другой класс
        startActivity(intent);
    }

    @Override
    public void onPause() {    // Запоминаем данные
        super.onPause();

        SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_Uroven), uroven);
        a1.apply();

        SharedPreferences.Editor a2 = mSettings.edit();
        a2.putInt(String.valueOf(Key_Score), score);
        a2.apply();

        SharedPreferences.Editor a3 = mSettings.edit();
        a3.putInt(String.valueOf(Key_Time), bonusTime);
        a3.apply();

        SharedPreferences.Editor a4 = mSettings.edit();
        a4.putInt(String.valueOf(Key_Uroven_Max), urovenMax);
        a4.apply();

        SharedPreferences.Editor a5 = mSettings.edit();
        a5.putInt(String.valueOf(Key_Score_Max), scoreMax);
        a5.apply();

        SharedPreferences.Editor a6 = mSettings.edit();
        a6.putInt(String.valueOf(Key_Time_Max), bonusTimeMax);
        a6.apply();

        SharedPreferences.Editor a7 = mSettings.edit();
        a7.putInt(String.valueOf(Key_Slognost_Max), slognostMax);
        a7.apply();

        SharedPreferences.Editor a8 = mSettings.edit();
        a8.putInt(String.valueOf(Key_Koef_Pobed), koefPobed);
        a8.apply();

        SharedPreferences.Editor a9 = mSettings.edit();
        a9.putInt(String.valueOf(Key_Koef_Pobed_Max), koefPobedMax);
        a9.apply();
    }
}

