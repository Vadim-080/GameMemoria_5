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
    public static int Key_Uroven; //  ключ текущий уровень,
    public static final String APP_PREFERENCES_COUNTER_Uroven = String.valueOf(Key_Uroven); // Создадим параметр, который мы хотим сохранять в настройках
    public static SharedPreferences mSettings;  // Создаём переменную, представляющую экземпляр класса SharedPreferences, который отвечает за работу с настройками
    public static int uroven;  // Задаём уровень в игре

    private ImageView monet;

    Button start;
    Button exit;
    TextView namberUroven;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        monet = findViewById(R.id.monet_viev);

        Animation myanim1 = AnimationUtils.loadAnimation(this, R.anim.anim_monet);

        monet.startAnimation(myanim1);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

        start = (Button) findViewById(R.id.buStart);
        exit = (Button) findViewById(R.id.buExit);
        namberUroven = (TextView) findViewById(R.id.nadpNamberUroven_view);

        onResume();
        namberUroven.setText("" + uroven);

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
    }

    private void startGame() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Uroven))) {
            uroven = mSettings.getInt(String.valueOf(Key_Uroven), 0);
        } else uroven = 1;
    }
}
