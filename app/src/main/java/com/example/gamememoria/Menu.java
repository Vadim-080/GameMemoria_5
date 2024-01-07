package com.example.gamememoria;

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

public class Menu extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PAPKA_MEMORI_GAME_MEMOR";  // константа для имени файла настроек
    public static SharedPreferences mSettings;  // Создаём переменную, представляющую экземпляр класса SharedPreferences, который отвечает за работу с настройками
    public static int Key_Uroven = 1; //  ключ текущий уровень
    public static int Key_Score = 2; //  ключ текущие очки
    public static int Key_Time = 3; //  ключ бонусное время
    public static int Key_Uroven_Max = 11; //  ключ максимального уровня
    public static int Key_Score_Max = 12; //  ключ набранных на максимальн уровне очков
    public static int Key_Time_Max = 13; //  ключ набранного на максимальн уровне времени
    public static int uroven;  // Задаём уровень в игре
    public static int score;  // Задаём количество очков в игре
    public static int bonusTime;  // Задаём количество бонусного времени
    public static int urovenMax;  // Для хранения максимального достигнутого в игре уровня
    public static int scoreMax;  // Для хранения набранных на максимальн уровне очков
    public static int bonusTimeMax;  // Для хранения набранного на максимальн уровне времени
    public static String PrichinGameOver;
    public static int Visot_fishek; // высота фишек
    public static int Shirin_fishek; // высота фишек

    private ImageView monet;
    private ImageView time;

    Button start, start1, exit, newGame;
    Chronometer timeBonus;
    TextView namberUroven, score_viev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        monet = findViewById(R.id.monet_viev);
        time = findViewById(R.id.time_viev);
        timeBonus = findViewById(R.id.timeBonus_view);
        score_viev = findViewById(R.id.score_viev);
        start = findViewById(R.id.buStart);
        start1 = findViewById(R.id.buStart1);
        exit = findViewById(R.id.buExit);
        newGame = findViewById(R.id.buNewGame);
        namberUroven = findViewById(R.id.NamberUroven_view);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

        onResume();
        namberUroven.setText("" + uroven);
        score_viev.setText("" + score);
        timeBonus.setBase(SystemClock.elapsedRealtime() - bonusTime * 1000);

       /* Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim_monet);
        monet.startAnimation(a1);*/

        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.anim_time2);
        time.startAnimation(a2);

        View a3 = findViewById(R.id.monet_viev);                   // ПОВОРОТ КНОПКИ
        a3.animate().rotationYBy(99720).setDuration(300000);

        Animation a4 = AnimationUtils.loadAnimation(this, R.anim.anim_bu_start);
        start.startAnimation(a4);

        Animation a5 = AnimationUtils.loadAnimation(this, R.anim.anim_uroven);
        namberUroven.startAnimation(a5);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    private void startGame() {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void newGame() {
        uroven = 1;
        score = 0;
        bonusTime = 0;
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Uroven))) {
            uroven = mSettings.getInt(String.valueOf(Key_Uroven), 0);
        } else uroven = 1;

        if (mSettings.contains(String.valueOf(Key_Uroven_Max))) {
            urovenMax = mSettings.getInt(String.valueOf(Key_Uroven_Max), 0);
        } else urovenMax = 1;

        if (mSettings.contains(String.valueOf(Key_Score))) {
            score = mSettings.getInt(String.valueOf(Key_Score), 0);
        } else score = 0;

        if (mSettings.contains(String.valueOf(Key_Score_Max))) {
            scoreMax = mSettings.getInt(String.valueOf(Key_Score_Max), 0);
        } else scoreMax = 0;

        if (mSettings.contains(String.valueOf(Key_Time_Max))) {
            bonusTimeMax = mSettings.getInt(String.valueOf(Key_Time_Max), 0);
        } else bonusTimeMax = 0;
    }
}
