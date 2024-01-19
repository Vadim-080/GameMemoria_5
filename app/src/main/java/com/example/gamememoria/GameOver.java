package com.example.gamememoria;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import static com.example.gamememoria.Menu.Key_Uroven;
import static com.example.gamememoria.Menu.PrichinGameOver;
import static com.example.gamememoria.Menu.kolvoGameOverPodrad;
import static com.example.gamememoria.Menu.mSettings;
import static com.example.gamememoria.Menu.uroven;
import static com.example.gamememoria.Zastavka.promegutGameOverPodrad;
import static com.example.gamememoria.Zastavka.povtorTriGameOverPodrad;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    private TextView PrichinGameOver_view, ViProigrali;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        PrichinGameOver_view = findViewById(R.id.PrichinGameOver_view);
        ViProigrali = findViewById(R.id.nadpViProigrali_view);

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
        nbutton2.setTextSize(15);
    }

    public void PovtorGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);    // Переход на другой класс
        startActivity(intent);
    }

    public void clickMenu(View view) {
        Intent intent = new Intent(this, Menu.class);    // Переход на другой класс (сдесь класс Vvod)
        startActivity(intent);
    }

    public void onPause() {    // Запоминаем данные
        super.onPause();

        SharedPreferences.Editor a1 = mSettings.edit();
        a1.putInt(String.valueOf(Key_Uroven), uroven);
        a1.apply();
    }
}

