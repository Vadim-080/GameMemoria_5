package com.example.gamememoria;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PAPKA_MEMORI_GAME_MEMOR";  // константа для имени файла настроек
    public static int Key_Uroven=1; //  ключ текущий уровень,
    public static int Key_Score=2; //  ключ текущие очки,
    public static int Key_Time=3; //  ключ бонусные очки,

    public static int Key_Uroven_Max=11; //  ключ максимального уровня,
    public static int Key_Score_Max=12; //  ключ максимальных очков,
    public static int Key_Time_Max=13; //  ключ бонусные очки,

    public static final String APP_PREFERENCES_COUNTER_Uroven = String.valueOf(Key_Uroven); // Создадим параметр, который мы хотим сохранять в настройках
    public static SharedPreferences mSettings;  // Создаём переменную, представляющую экземпляр класса SharedPreferences, который отвечает за работу с настройками
    public static int uroven;  // Задаём уровень в игре
    public static int score;  // Задаём количество очков в игре

    private ImageView monet;
    private ImageView time;

    Button start, exit, newGame;

    TextView namberUroven, score_viev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        monet = findViewById(R.id.monet_viev);
        time = findViewById(R.id.time_viev);
        score_viev =   findViewById(R.id.score_viev);
        start =  findViewById(R.id.buStart);
        exit =  findViewById(R.id.buExit);
        newGame = findViewById(R.id.buNewGame);
        namberUroven =  findViewById(R.id.NamberUroven_view);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

        onResume();
        namberUroven.setText("" + uroven);
        score_viev.setText("" + score);

        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim_monet);
        monet.startAnimation(a1);

        start.setOnClickListener(new View.OnClickListener() {
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
        score=0;
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Uroven))) {
            uroven = mSettings.getInt(String.valueOf(Key_Uroven), 0);
        } else uroven = 1;

        if (mSettings.contains(String.valueOf(Key_Score))) {
            score = mSettings.getInt(String.valueOf(Key_Score), 0);
        } else score = 0;
    }
}
