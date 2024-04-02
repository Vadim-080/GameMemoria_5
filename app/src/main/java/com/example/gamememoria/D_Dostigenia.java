package com.example.gamememoria;

import static com.example.gamememoria.B_Menu.Key_Koef_Dostign_Slogn;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Igr;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Pobed;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Proigr_Step;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Proigr_Time;
import static com.example.gamememoria.B_Menu.Key_Uroven_Max;
import static com.example.gamememoria.B_Menu.koefDostignSlogn;
import static com.example.gamememoria.B_Menu.kolvoIgr;
import static com.example.gamememoria.B_Menu.kolvoPobed;
import static com.example.gamememoria.B_Menu.kolvoProigrStep;
import static com.example.gamememoria.B_Menu.kolvoProigrTime;
import static com.example.gamememoria.B_Menu.mSettings;
import static com.example.gamememoria.B_Menu.urovenMax;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusic;
import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
import static com.example.gamememoria.B_Menu.fonMusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.my.target.ads.MyTargetView;
import com.my.target.common.models.IAdLoadingError;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class D_Dostigenia extends AppCompatActivity {

    // Create the object of TextView and PieChart class
    TextView vsegoGame, pobed1, proigrTime, proigrStep, koefSlognost;
    PieChart pieChart;
    ImageButton mute;
    int urovenVolume, timeOnFonMusik;

    // Перемен VK рекламы
    private MyTargetView adView; // Рекламный  экземпляр класса
    RelativeLayout layout;
    RelativeLayout.LayoutParams adViewLayoutParams;

    MediaPlayer zvMute, zvPerexV_Menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dostigenia);

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        RelativeLayout LinearLayout = findViewById(R.id.fon_dostigen_view);

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

        adView.setSlotId(1531466);

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
      /*  if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }*/


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
        mute = findViewById(R.id.buMute);

// Задаём звуковые сигналы







        fonMusic = MediaPlayer.create(this, R.raw.legki_jazz);  // ЗАМЕНИТЬ ФОНОВУЮ МУЗЫКУ









        zvMute = MediaPlayer.create(this, R.raw.mute_1);
        zvPerexV_Menu = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1);

        mute.setAlpha(0.7f);    // ПРОЗРАЧНОСТЬ КНОПКИ Mute

        if (pologenieKnopkiMute == true) {
            urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            mute.setImageResource(R.drawable.mute);
        } else {
        /*    urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            timeOnFonMusik = 8000;
            vklFonMusic();
            mute.setImageResource(R.drawable.zvuk);
        }

        onResume();

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

    @Override  // Остатки VK рекламы
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    public void clickMenu(View view) {

        fonMusic.stop();
        zvPerexV_Menu.start();

        Intent intent = new Intent(this, B_Menu.class);    // Переход на другой класс (сдесь класс Vvod)
        startActivity(intent);
    }

    public void clickPieChart(View view) {
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

    private void regulirovUrovenVolume() {    //Этот код мгновенно установит громкость на уровень заданной переменной urovenVolume

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int a = maxVolume * urovenVolume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, a, 0);
    }

   /* private void vklFonMusic() {  //Этот код делает анимацию плавного включения фоновой музыки

        ValueAnimator volumeAnimator = ValueAnimator.ofFloat(0f, 0.6f);
        volumeAnimator.setDuration(timeOnFonMusik); // Длительность анимации в миллисекундах
        volumeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float volume = (float) animation.getAnimatedValue();
                fonMusicDostigen.setVolume(volume, volume);
            }
        });
        volumeAnimator.start();

        fonMusicDostigen.start();
        fonMusicDostigen.setLooping(true);  // повтор проигрывания плеера
    }*/

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
            urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            zvMute.start();
            timeOnFonMusik = 1500;
            vklFonMusic();
            pologenieKnopkiMute = false;
            mute.animate().rotationXBy(180).setDuration(500);
        }
    }

}


