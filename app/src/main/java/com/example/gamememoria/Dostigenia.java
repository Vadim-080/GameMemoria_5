package com.example.gamememoria;

import static com.example.gamememoria.Menu.Key_Koef_Dostign_Slogn;
import static com.example.gamememoria.Menu.Key_Koef_Pobed;
import static com.example.gamememoria.Menu.Key_Kolvo_Igr;
import static com.example.gamememoria.Menu.Key_Kolvo_Pobed;
import static com.example.gamememoria.Menu.Key_Kolvo_Proigr_Step;
import static com.example.gamememoria.Menu.Key_Kolvo_Proigr_Time;
import static com.example.gamememoria.Menu.Key_Slognost_game;
import static com.example.gamememoria.Menu.Key_Slognost_step;
import static com.example.gamememoria.Menu.Key_Slognost_time;
import static com.example.gamememoria.Menu.Key_Uroven_Max;
import static com.example.gamememoria.Menu.koefDostignSlogn;
import static com.example.gamememoria.Menu.koefPobed;
import static com.example.gamememoria.Menu.koef_slogn_step;
import static com.example.gamememoria.Menu.koef_slogn_time;
import static com.example.gamememoria.Menu.kolvoIgr;
import static com.example.gamememoria.Menu.kolvoPobed;
import static com.example.gamememoria.Menu.kolvoProigrStep;
import static com.example.gamememoria.Menu.kolvoProigrTime;
import static com.example.gamememoria.Menu.mSettings;
import static com.example.gamememoria.Menu.slognost_game;
import static com.example.gamememoria.Menu.urovenMax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class Dostigenia extends AppCompatActivity {

    // Create the object of TextView and PieChart class
    TextView vsegoGame, pobed1, proigrTime, proigrStep,  koefSlognost;
    PieChart pieChart;
    MediaPlayer mediaPlayer1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dostigenia);

// Задаем цвет верхей строки и строки навигации
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }


// ГРАФИКИ
// https://www.geeksforgeeks.org/how-to-add-a-pie-chart-into-an-android-application/


// Link those objects with their respective
// id's that we have given in .XML file
        vsegoGame = findViewById(R.id.tvVsegoGame);
        pobed1 = findViewById(R.id.tvPobed1);
        proigrStep = findViewById(R.id.tvProigrStep);
        proigrTime = findViewById(R.id.tvProigrTime);
        koefSlognost = findViewById(R.id.tvKoefSlognost);
        pieChart = findViewById(R.id.piechart);

        onResume();

        mediaPlayer1 = MediaPlayer.create(this, R.raw.elektron1);

        vsegoGame.setText(Integer.toString(kolvoIgr));
        pobed1.setText(Integer.toString(kolvoPobed));
        proigrStep.setText(Integer.toString(kolvoProigrStep));
        proigrTime.setText(Integer.toString(kolvoProigrTime));
        koefSlognost.setText(Integer.toString(koefDostignSlogn));


// Цвет диаграммы
        pieChart.addPieSlice(
                new PieModel(
                        "ПОБ",
                        Integer.parseInt(pobed1.getText().toString()),
                        Color.parseColor("#008839")));
        pieChart.addPieSlice(
                new PieModel(
                        "ПРОИГ ХОД",
                        Integer.parseInt(proigrStep.getText().toString()),
                        Color.parseColor("#FF8225")));
        pieChart.addPieSlice(
                new PieModel(
                        "ПРОИГ ВРЕМ",
                        Integer.parseInt(proigrTime.getText().toString()),
                        Color.parseColor("#DF0165")));

// To animate the pie chart
        pieChart.startAnimation();

    }

    public void clickMenu(View view) {
        Intent intent = new Intent(this, Menu.class);    // Переход на другой класс (сдесь класс Vvod)
        startActivity(intent);
    }

    public void clickPieChart (View view) {
        pieChart.startAnimation();
    }

    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Kolvo_Igr))) {
            kolvoIgr = mSettings.getInt(String.valueOf(Key_Kolvo_Igr), 0);
        } else kolvoIgr = 0;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Pobed))) {
            kolvoPobed = mSettings.getInt(String.valueOf(Key_Kolvo_Pobed), 0);
        } else kolvoPobed = 0;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Proigr_Step))) {
            kolvoProigrStep = mSettings.getInt(String.valueOf(Key_Kolvo_Proigr_Step), 0);
        } else kolvoProigrStep = 0;

        if (mSettings.contains(String.valueOf(Key_Uroven_Max))) {
            urovenMax = mSettings.getInt(String.valueOf(Key_Uroven_Max), 0);
        } else urovenMax = 0;

        if (mSettings.contains(String.valueOf(Key_Koef_Dostign_Slogn))) {
            koefDostignSlogn = mSettings.getInt(String.valueOf(Key_Koef_Dostign_Slogn), 0);
        } else koefDostignSlogn = 0;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Proigr_Time))) {
            kolvoProigrTime = mSettings.getInt(String.valueOf(Key_Kolvo_Proigr_Time), 0);
        } else kolvoProigrTime = 0;
    }


}
