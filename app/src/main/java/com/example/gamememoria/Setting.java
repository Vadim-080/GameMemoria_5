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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Setting extends AppCompatActivity {

    Button zabiv, novi, opit, mast, buMenu;
    MediaPlayer mediaPlayer1;

    private float mBackLightValue = 0.5f; // значение по умолчанию

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

// Задаем цвет верхей строки и строки навигации
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }



        SeekBar backLightSeekBar = findViewById(R.id.seekBar);
        final TextView settingTextView = findViewById(R.id.textViewSetting);

        backLightSeekBar
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float backLightValue = (float) progress / 100;
                        settingTextView.setText(String.valueOf(backLightValue));

                        WindowManager.LayoutParams layoutParams = getWindow()
                                .getAttributes();
                        layoutParams.screenBrightness = backLightValue;
                        getWindow().setAttributes(layoutParams);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });




    }





    @Override
    public void onPause() {    // Запоминаем данные
        super.onPause();

       /* SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_Slognost_time), koef_slogn_time);
        a1.apply();

        SharedPreferences.Editor a2 = mSettings.edit();
        a2.putInt(String.valueOf(Key_Slognost_step), koef_slogn_step);
        a2.apply();

        SharedPreferences.Editor a3 = mSettings.edit();
        a3.putInt(String.valueOf(Key_Slognost_game), slognost_game);
        a3.apply();*/

    }

    public void onResume() {    // Получаем число из настроек
        super.onResume();

        /*if (mSettings.contains(String.valueOf(Key_Slognost_game))) {
            slognost_game = mSettings.getInt(String.valueOf(Key_Slognost_game), 0);
        } else slognost_game = 1;

        if (mSettings.contains(String.valueOf(Key_Koef_Pobed))) {
            koefPobed = mSettings.getInt(String.valueOf(Key_Koef_Pobed), 0);
        } else koefPobed = 0;*/
    }

    public void clickMenu(View v) {
        Intent intent = new Intent(this, Menu.class);   // Переход на другой класс
        startActivity(intent);
    }


}
