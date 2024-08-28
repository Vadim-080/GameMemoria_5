package com.example.gamememoria;

import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.my.target.ads.MyTargetView;
import com.my.target.common.models.IAdLoadingError;

public class F_Setting extends AppCompatActivity {

    MediaPlayer zvPerexV_Menu;

    AudioManager urovVolumR;

    // Перемен VK рекламы
    private MyTargetView adView; // Рекламный  экземпляр класса
    RelativeLayout layout;
    RelativeLayout.LayoutParams adViewLayoutParams;

  /*  private float mBackLightValue = 0.5f; // значение по умолчанию*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        onResume();

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        ConstraintLayout LinearLayout = findViewById(R.id.fon_setting_view);

        int currentVis = LinearLayout.getSystemUiVisibility();
        int newVis;
        if ((currentVis & View.SYSTEM_UI_FLAG_LOW_PROFILE) == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            newVis = View.SYSTEM_UI_FLAG_VISIBLE;
        } else {
            newVis = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        LinearLayout.setSystemUiVisibility(newVis);

// VK РЕКЛАМА
        layout = findViewById(R.id.RelativeLayout);
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

// Задаём звуковые сигналы

        zvPerexV_Menu = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1);

// Регулировка громкости

        SeekBar volumeControl = findViewById(R.id.volumeControl);
        final TextView settingVolum = findViewById(R.id.text_urov_vol);

        urovVolumR = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int tekUrovVolume = urovVolumR.getStreamVolume(AudioManager.STREAM_MUSIC); // текущий уровень громкости

        int maxVolume = urovVolumR.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int volumeNachaln = ((100 * tekUrovVolume) / maxVolume);

        settingVolum.setText((String.valueOf(volumeNachaln)) + "  %");

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(tekUrovVolume);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int cifrUrovVolum = (100 * progress) / maxVolume;
                settingVolum.setText((String.valueOf(cifrUrovVolum)) + "  %");     //Уровень громк в цифрах от 0 до 100

                pologenieKnopkiMute = false;

              /*  urovVolumR.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);*/

                pologenRegulVolum = 1;
                zadanUrovVolume = cifrUrovVolum;

                onPause();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

// Регулировка ЯРКОСТИ - Ползунок и показ цифрами уровня яркости
        SeekBar backLightSeekBar = findViewById(R.id.seekBar);
        final TextView settingTextView = findViewById(R.id.textViewSetting);

     /*   onResume();*/

        backLightSeekBar.setProgress(zadanUrovBridgs);

        settingTextView.setText((String.valueOf(zadanUrovBridgs)) + "  %");

        backLightSeekBar
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        /*  int backLightValue = progress;*/
                        settingTextView.setText((String.valueOf(progress)) + "  %");

                        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                        layoutParams.screenBrightness = progress;
                        getWindow().setAttributes(layoutParams);

                        pologenRegulBridgs = 1;
                        zadanUrovBridgs = progress;

                        onPause();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
    }

    @Override  // Остатки VK рекламы
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    // ПРЕКРАЩЕНИЕ Музыки при свертывании приложения
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        pologSostoyanSvernutogoPrilogen = true;
        /*  fonMusic.pause();*/
    }

    @Override
    public void onPause() {    // Запоминаем данные
        super.onPause();

        SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_Urov_Volum), zadanUrovVolume);
        a1.apply();

        SharedPreferences.Editor a2 = mSettings.edit();
        a2.putInt(String.valueOf(Key_pologen_regul_Volum), pologenRegulVolum);
        a2.apply();

        SharedPreferences.Editor a3 = mSettings.edit();
        a3.putInt(String.valueOf(Key_Urov_Bridgs), zadanUrovBridgs);
        a3.apply();

        SharedPreferences.Editor a4 = mSettings.edit();
        a4.putInt(String.valueOf(Key_pologen_regul_Bridgs), pologenRegulBridgs);
        a4.apply();
    }

    public void onResume() {    // Получаем число из настроек
        super.onResume();

       if (mSettings.contains(String.valueOf(Key_Urov_Bridgs))) {
            zadanUrovBridgs = mSettings.getInt(String.valueOf(Key_Urov_Bridgs), 0);
        } else zadanUrovBridgs = 10;

          /*if (mSettings.contains(String.valueOf(Key_Koef_Pobed))) {
            koefPobed = mSettings.getInt(String.valueOf(Key_Koef_Pobed), 0);
        } else koefPobed = 0;*/
    }

    public void clickMenu(View v) {

        zvPerexV_Menu.start();

        Intent intent = new Intent(this, B_Menu.class);   // Переход на другой класс
        startActivity(intent);
    }


}
