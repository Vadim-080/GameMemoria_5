package com.example.gamememoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.gamememoria.B_Menu.Key_Uroven;
import static com.example.gamememoria.B_Menu.PrichinGameOver;
import static com.example.gamememoria.B_Menu.fonMusic;
import static com.example.gamememoria.B_Menu.kolvoGameOverPodrad;
import static com.example.gamememoria.B_Menu.mSettings;
import static com.example.gamememoria.B_Menu.uroven;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusic;
import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
import static com.example.gamememoria.A_Zastavka.promegutGameOverPodrad;
import static com.example.gamememoria.A_Zastavka.povtorTriGameOverPodrad;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.gamememoria.B_Menu.pologSostoyanSvernutogoPrilogen;

public class G_GameOver extends AppCompatActivity {

    private TextView PrichinGameOver_view, ViProigrali;
    ImageButton mute;
    MediaPlayer zvPerexV_Menu, zvMute;
    ConstraintLayout KartinraZadnegoPlana;
    int urovenVolume, timeOnFonMusik;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

// Задаем цвет верхей строки и строки навигации
        /*if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }*/

        PrichinGameOver_view = findViewById(R.id.PrichinGameOver_view);
        ViProigrali = findViewById(R.id.nadpViProigrali_view);
        KartinraZadnegoPlana = findViewById(R.id.fon_game_over_view);
        mute = findViewById(R.id.buMute);

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

          /*  urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
        }

        KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_game_over));  // задаем фоновое поле

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // Разрешает тускнениия экрана телефона и его выключения во время игры

        PrichinGameOver_view.setText("" + PrichinGameOver);

        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.anim_proigr);
        ViProigrali.startAnimation(a2);

        kolvoGameOverPodrad = promegutGameOverPodrad++;

        if (uroven > 1) {

            if (kolvoGameOverPodrad == 2) {
                UmenshenUrovn();
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

    private void UmenshenUrovn() {

        int a = uroven - 1;
        String TextToast;

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this, R.style.StyleDialogOknaGameOver);

        // Заголовок и текст

        if (povtorTriGameOverPodrad == true) {
            alertbox.setTitle("\n" + "ПОВТОРНО ПРЕДЛАГАЮ" + "\n" + "ПОНИЗИТЬ УРОВЕНЬ");
            TextToast = "НА ЭТОМ УРОВНЕ ВЫ" + "\n" + "УЖЕ ПОВТОРНО ПРОИГРАЛИ" + "\n" + "ТРИ РАЗА ПОДРЯД" + "\n" + "ПРЕДЛАГАЕМ ПЕРЕЙТИ" + "\n"
                    + "НА ПРЕДЫДУЩИЙ" + "\n" + "\n" + a + "-й УРОВЕНЬ";
        } else {
            alertbox.setTitle("\n" + "       УПРОСТИТЬ ЗАДАЧУ");
            TextToast = "НА ЭТОМ УРОВНЕ ВЫ ПРОИГРАЛИ" + "\n" + "ТРИ РАЗА ПОДРЯД" + "\n" + "ПРЕДЛАГАЕМ ПЕРЕЙТИ" + "\n"
                    + "НА ПРЕДЫДУЩИЙ" + "\n" + "\n" + a + "-й УРОВЕНЬ";
        }

        alertbox.setMessage(TextToast);

        // Добавляем кнопки
        alertbox.setPositiveButton("ПЕРЕЙТИ" + "\n", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

                uroven--;
                promegutGameOverPodrad = 0;
                povtorTriGameOverPodrad = false;
                onPause();
                PovtorGame(null);
            }
        });
        alertbox.setNegativeButton("ПРОДОЛЖИТЬ ИГРУ", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                povtorTriGameOverPodrad = true;
                promegutGameOverPodrad = 0;
                PovtorGame(null);
            }
        });

        AlertDialog alert = alertbox.create();    //  https://stackoverflow.com/questions/33437398/how-to-change-textcolor-in-alertdialog
        alert.show();  // показываем окно

        alert.setCanceledOnTouchOutside(false);  // Не даёт закрывать диалоговое окно при нажатии на пустое место экрана

        Button nbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.GREEN);
        nbutton.setTextSize(15);

        Button nbutton2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton2.setTextColor(Color.BLUE);
        nbutton2.setTextSize(13);
    }

    public void PovtorGame(View view) {
        Intent intent = new Intent(this, C_MainActivity.class);    // Переход на другой класс
        startActivity(intent);
    }

    public void clickMenu(View view) {
        zvPerexV_Menu.start();

        Intent intent = new Intent(this, B_Menu.class);    // Переход на другой класс (сдесь класс Vvod)
        startActivity(intent);
    }

    public void onPause() {    // Запоминаем данные
        super.onPause();

        SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_Uroven), uroven);
        a1.apply();
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
            urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
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

