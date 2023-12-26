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
    public static final String APP_PREFERENCES_COUNTER_Uroven = String.valueOf(Key_Uroven); // Создадим параметр, который мы хотим сохранять в настройках
    public static SharedPreferences mSettings;  // Создаём переменную, представляющую экземпляр класса SharedPreferences, который отвечает за работу с настройками
    public static int uroven;  // Задаём уровень в игре
    public static int score;  // Задаём количество очков в игре

    private ImageView monet;

    Button start, exit, newGame;

    TextView namberUroven, score_viev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        monet = findViewById(R.id.monet_viev);
        score_viev = (TextView)  findViewById(R.id.score_viev);
        start = (Button) findViewById(R.id.buStart);
        exit = (Button) findViewById(R.id.buExit);
        newGame = (Button) findViewById(R.id.buNewGame);
        namberUroven = (TextView) findViewById(R.id.NamberUroven_view);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

        onResume();
        namberUroven.setText("" + uroven);
        score_viev.setText("" + score);

        Animation myanim1 = AnimationUtils.loadAnimation(this, R.anim.anim_monet);

        monet.startAnimation(myanim1);


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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void clickRedaktor (View v) {
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
