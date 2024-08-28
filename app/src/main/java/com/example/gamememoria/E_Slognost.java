package com.example.gamememoria;

import static com.example.gamememoria.B_Menu.Key_Koef_Pobed;
import static com.example.gamememoria.B_Menu.Key_Slognost_game;
import static com.example.gamememoria.B_Menu.Key_Slognost_step;
import static com.example.gamememoria.B_Menu.Key_Slognost_time;
import static com.example.gamememoria.B_Menu.Key_Urov_Volum;
import static com.example.gamememoria.B_Menu.Key_Urov_Bridgs;
import static com.example.gamememoria.B_Menu.fonMusic;
import static com.example.gamememoria.B_Menu.koefPobed;
import static com.example.gamememoria.B_Menu.koef_slogn_step;
import static com.example.gamememoria.B_Menu.koef_slogn_time;
import static com.example.gamememoria.B_Menu.mSettings;

import static com.example.gamememoria.B_Menu.pologSostoyanSvernutogoPrilogen;
import static com.example.gamememoria.B_Menu.pologenRegulBridgs;
import static com.example.gamememoria.B_Menu.pologenRegulVolum;
import static com.example.gamememoria.B_Menu.slognost_game;
import static com.example.gamememoria.B_Menu.zadanUrovVolume;
import static com.example.gamememoria.B_Menu.zadanUrovBridgs;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.my.target.ads.MyTargetView;
import com.my.target.common.models.IAdLoadingError;

public class E_Slognost extends AppCompatActivity {

    Button zabiv, novi, opit, mast, buMenu;
    ConstraintLayout KartinraZadnegoPlana;
    ImageButton mute;
    int urovenVolume, timeOnFonMusik;
    MediaPlayer mediaPlayer1, zvPerexV_Menu , zvMute, zvSlogn1, zvSlogn2, zvSlogn3, zvSlogn4;

    // Перемен VK рекламы
    private MyTargetView adView; // Рекламный  экземпляр класса
    RelativeLayout layout;
    RelativeLayout.LayoutParams adViewLayoutParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slognost);

        onResume(); // Вывод данных из памяти

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        ConstraintLayout LinearLayout = findViewById(R.id.fon_slognost_view);

        int currentVis = LinearLayout.getSystemUiVisibility();
        int newVis;
        if ((currentVis & View.SYSTEM_UI_FLAG_LOW_PROFILE) == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            newVis = View.SYSTEM_UI_FLAG_VISIBLE;
        } else {
            newVis = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        LinearLayout.setSystemUiVisibility(newVis);

        // VK РЕКЛАМА
        layout =  findViewById(R.id.RelativeLayout);
        adView = new MyTargetView(this);
        // Устанавливаем id слота

        adView.setSlotId(1675164);

        // Устанавливаем LayoutParams
        adViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adView.setLayoutParams(adViewLayoutParams);
        // Устанавливаем слушатель событий
        adView.setListener(new MyTargetView.MyTargetViewListener() {
            @Override
            public void onLoad(MyTargetView myTargetView) {
                // Данные успешно загружены, запускаем показ объявлений
                layout.addView(adView);
                /*  layout.addView(adView, adViewLayoutParams );*/
            }

            /**
             * @param iAdLoadingError
             * @param myTargetView
             */
            public void onNoAd(@NonNull IAdLoadingError iAdLoadingError, @NonNull MyTargetView myTargetView) {
            }

            @Override
            public void onShow(MyTargetView myTargetView) {
            }

            @Override
            public void onClick(MyTargetView myTargetView) {
            }
        });
        // Запускаем загрузку данных
        adView.load();

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
        zvSlogn1 = MediaPlayer.create(this, R.raw.slogn_1);
        zvSlogn2 = MediaPlayer.create(this, R.raw.slogn_2);
        zvSlogn3 = MediaPlayer.create(this, R.raw.slogn_3);
        zvSlogn4 = MediaPlayer.create(this, R.raw.slogn_4);

        // Задаём звуковые сигналы
        zvMute = MediaPlayer.create(this, R.raw.mute_1);
        zvPerexV_Menu = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1);
        fonMusic = MediaPlayer.create(this, R.raw.muz_slogn_1);

        KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_slognost));  // задаем фоновое поле

        mute.setAlpha(0.7f);    // ПРОЗРАЧНОСТЬ КНОПКИ Mute

        if (pologenieKnopkiMute == true) {

           /* urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            mute.setImageResource(R.drawable.mute);
        } else {
            if (pologenRegulVolum != 0) {
                urovenVolume = zadanUrovVolume;
            } else {
                urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            }
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

    @Override  // Остатки VK рекламы
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    private void Zabiv() {

        fonMusic.pause();
        zvSlogn1.start();

        if (slognost_game > 1) {
            promegutGameOverPodrad = 0;
        }

        slognost_game = 1;
        koef_slogn_time = 2;
        koef_slogn_step = 6;

        Pusk();
    }

    private void Novi() {

        fonMusic.pause();
        zvSlogn2.start();

        if (slognost_game > 2) {
            promegutGameOverPodrad = 0;
        }
        slognost_game = 2;
        koef_slogn_time = 0;
        koef_slogn_step = 3;

        Pusk();
    }

    private void Opit() {

        fonMusic.pause();
        zvSlogn3.start();

        if (slognost_game > 3) {
            promegutGameOverPodrad = 0;
        }
        slognost_game = 3;
        koef_slogn_time = -1;
        koef_slogn_step = -1;

        Pusk();
    }

    private void Mast() {
        fonMusic.pause();
        zvSlogn4.start();
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

        zvPerexV_Menu.start();

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

        if (pologenRegulVolum != 0) {
            zadanUrovVolume = mSettings.getInt(String.valueOf(Key_Urov_Volum), 0);
        }

        if (pologenRegulBridgs != 0) {
            zadanUrovBridgs = mSettings.getInt(String.valueOf(Key_Urov_Bridgs), 0);
        }
    }

    // ПРЕКРАЩЕНИЕ Музыки при свертывании приложения
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        pologSostoyanSvernutogoPrilogen = true;
          fonMusic.pause();
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
