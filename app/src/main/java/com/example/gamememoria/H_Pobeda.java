package com.example.gamememoria;

import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
import static com.example.gamememoria.B_Menu.Key_Urov_Volum;
import static com.example.gamememoria.B_Menu.fonMusic;
import static com.example.gamememoria.B_Menu.pologenRegulVolum;
import static com.example.gamememoria.B_Menu.zadanUrovVolume;
import static com.example.gamememoria.C_MainActivity.StepCountIspolz;
import static com.example.gamememoria.C_MainActivity.bonusStep;
import static com.example.gamememoria.C_MainActivity.bonusTimeViv;
import static com.example.gamememoria.C_MainActivity.timeIstr;
import static com.example.gamememoria.B_Menu.Key_Koef_Pobed;
import static com.example.gamememoria.B_Menu.Key_Koef_Pobed_Max;
import static com.example.gamememoria.B_Menu.Key_Score;
import static com.example.gamememoria.B_Menu.Key_Score_Max;
import static com.example.gamememoria.B_Menu.Key_Slognost_Max;
import static com.example.gamememoria.B_Menu.Key_Time;
import static com.example.gamememoria.B_Menu.Key_Time_Max;
import static com.example.gamememoria.B_Menu.Key_Uroven;
import static com.example.gamememoria.B_Menu.Key_Uroven_Max;
import static com.example.gamememoria.B_Menu.bonusTime;
import static com.example.gamememoria.B_Menu.bonusTimeMax;
import static com.example.gamememoria.B_Menu.koefPobed;
import static com.example.gamememoria.B_Menu.koefPobedMax;
import static com.example.gamememoria.B_Menu.mSettings;
import static com.example.gamememoria.B_Menu.pologSostoyanSvernutogoPrilogen;
import static com.example.gamememoria.B_Menu.score;
import static com.example.gamememoria.B_Menu.scoreMax;
import static com.example.gamememoria.B_Menu.slognostMax;
import static com.example.gamememoria.B_Menu.slognost_game;
import static com.example.gamememoria.B_Menu.uroven;
import static com.example.gamememoria.B_Menu.urovenMax;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class H_Pobeda extends AppCompatActivity {

    TextView result;
    ImageView smailPobeda;
    MediaPlayer zvPerexV_Menu, zvMute;
    ImageButton mute;
    ConstraintLayout KartinraZadnegoPlana;
    int urovenVolume, timeOnFonMusik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pobeda);

        onResume();

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        ConstraintLayout LinearLayout = findViewById(R.id.fon_pobeda_view);

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
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }*/

        result = findViewById(R.id.Result_view);
        smailPobeda = findViewById(R.id.smailPobeda_view);
        KartinraZadnegoPlana = findViewById(R.id.fon_pobeda_view);
        mute= findViewById(R.id.buMute);

// Задаём звуковые сигналы
        zvPerexV_Menu = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1);
        zvMute = MediaPlayer.create(this, R.raw.mute_1);



        fonMusic = MediaPlayer.create(this, R.raw.legki_jazz);  // ЗАМЕНИТЬ ФОНОВУЮ МУЗЫКУ





        mute.setAlpha(0.7f);    // ПРОЗРАЧНОСТЬ КНОПКИ Mute

        if (pologenieKnopkiMute == true) {
         /*   urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            mute.setImageResource(R.drawable.mute);
        } else {
            mute.setImageResource(R.drawable.zvuk);
            fonMusic.start();
        }

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
            score = 0;
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

    // ПРЕКРАЩЕНИЕ Музыки при свертывании приложения
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        pologSostoyanSvernutogoPrilogen = true;
        fonMusic.pause();
    }

    public void clickMenu(View v) {

        zvPerexV_Menu.start();

        Intent intent = new Intent(this, B_Menu.class);   // Переход на другой класс
        startActivity(intent);
    }

    public void clickProdolgitGame(View v) {
        Intent intent = new Intent(this, C_MainActivity.class);   // Переход на другой класс
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

    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (pologenRegulVolum != 0) {
            zadanUrovVolume = mSettings.getInt(String.valueOf(Key_Urov_Volum), 0);
        }
    }

    public void clickMute(View view) {

        if (pologenieKnopkiMute == false) {
            urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            pologenieKnopkiMute = true;
            mute.animate().rotationYBy(180).setDuration(500);
            mute.setImageResource(R.drawable.mute);
        } else {
            mute.setImageResource(R.drawable.zvuk);

            if (pologenRegulVolum != 0) {
                urovenVolume = zadanUrovVolume;
            } else {
                urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            }
           /* urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %*/

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

