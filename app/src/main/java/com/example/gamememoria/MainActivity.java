// Игра для тренировки памяти - начало разработки 13.12.2023
//
package com.example.gamememoria;

import static com.example.gamememoria.Menu.Key_Score;
import static com.example.gamememoria.Menu.Key_Score_Max;
import static com.example.gamememoria.Menu.Key_Time;
import static com.example.gamememoria.Menu.Key_Time_Max;
import static com.example.gamememoria.Menu.Key_Uroven;
import static com.example.gamememoria.Menu.Key_Uroven_Max;
import static com.example.gamememoria.Menu.Shirin_fishek;
import static com.example.gamememoria.Menu.Visot_fishek;
import static com.example.gamememoria.Menu.bonusTime;
import static com.example.gamememoria.Menu.bonusTimeMax;
import static com.example.gamememoria.Menu.mSettings;
import static com.example.gamememoria.Menu.score;
import static com.example.gamememoria.Menu.scoreMax;
import static com.example.gamememoria.Menu.uroven;
import static com.example.gamememoria.Menu.urovenMax;
import static com.example.gamememoria.Menu.PrichinGameOver;
import static java.util.Calendar.getInstance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int timeGame;  // Задаём время игры на разных уровнях
    long timeStopped = 0; // для остановки времени во время паузы
    boolean sostoyniePause = false; // для переключения надписи кнопки пвуза-продолжить
    private TextView stepScreen;



    private Button buPause;
    private Chronometer timeScreen, timeItog;
    private Integer StepCount; // кол-во ходов
    private Integer StepCountStart; // начальное число ходов, для вычислений
    private Integer StepCountIspolz; // Использованное за игру число ходов, для вычислений
    private GridView mGrid;
    private Pole mAdapter;
    private static final int NOTIFICATION_REMINDER = 3;


    int koef_timeGame; // Коэфициент для задания времени игры

    TextView namberUroven;


    // Поворот экрана

   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            mGrid.setNumColumns(9);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            mGrid.setNumColumns(6);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// https://developer.alexanderklimov.ru/android/games/memoria.php?ysclid=lq527nk13p378087110
// https://ru-code-android.livejournal.com/2665.html

        mGrid = findViewById(R.id.igrovoePole);
        namberUroven = findViewById(R.id.NamberUroven_view);

        namberUroven.setText("" + uroven);

        if (uroven == 1) {
            mGrid.setNumColumns(3);
            mAdapter = new Pole(this, 3, 4);
            koef_timeGame = 5;
            StepCount = 20;  // Задаём максимальное количество ходов
            Visot_fishek = 300;
            Shirin_fishek = 300;
        }

        if (uroven == 2) {
            mGrid.setNumColumns(4);
            mAdapter = new Pole(this, 4, 4);
            koef_timeGame = 8;
            StepCount = 28;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 3) {
            mGrid.setNumColumns(4);
            mAdapter = new Pole(this, 4, 5);
            koef_timeGame = 11;
            StepCount = 36;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 4) {
            mGrid.setNumColumns(4);
            mAdapter = new Pole(this, 4, 6);
            koef_timeGame = 13;
            StepCount = 46;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 5) {
            mGrid.setNumColumns(4);
            mAdapter = new Pole(this, 4, 7);
            koef_timeGame = 15;
            StepCount = 54;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 6) {
            mGrid.setNumColumns(5);
            mAdapter = new Pole(this, 5, 6);
            koef_timeGame = 17;
            StepCount = 56;  // Задаём максимальное количество ходов
            Visot_fishek = 230;
            Shirin_fishek = 220;
        }

        if (uroven == 7) {
            mGrid.setNumColumns(4);
            mAdapter = new Pole(this, 4, 8);
            koef_timeGame = 19;
            StepCount = 60;  // Задаём максимальное количество ходов
            Visot_fishek = 210;
            Shirin_fishek = 230;
        }
        if (uroven == 8) {
            mGrid.setNumColumns(6);
            mAdapter = new Pole(this, 6, 6);
            koef_timeGame = 23;
            StepCount = 66;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }
        if (uroven == 9) {
            mGrid.setNumColumns(5);
            mAdapter = new Pole(this, 5, 8);
            koef_timeGame = 75;
            StepCount = 70;  // Задаём максимальное количество ходов
            Visot_fishek = 220;
            Shirin_fishek = 220;
        }
        if (uroven == 10) {
            mGrid.setNumColumns(6);
            mAdapter = new Pole(this, 6, 7);
            koef_timeGame = 78;
            StepCount = 74;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 150;
        }
        if (uroven == 11) {
            mGrid.setNumColumns(4);
            mAdapter = new Pole(this, 4, 11);
            koef_timeGame = 33;
            StepCount = 78;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 150;
        }
        if (uroven == 12) {
            mGrid.setNumColumns(6);
            mAdapter = new Pole(this, 6, 8);
            koef_timeGame = 38;
            StepCount = 86;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }

        if (uroven == 13) {
            mGrid.setNumColumns(5);
            mAdapter = new Pole(this, 5, 10);
            koef_timeGame = 42;
            StepCount = 90;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 190;
        }

        if (uroven == 14) {
            mGrid.setNumColumns(6);
            mAdapter = new Pole(this, 6, 9);
            koef_timeGame = 47;
            StepCount = 96;  // Задаём максимальное количество ходов
            Visot_fishek = 190;
            Shirin_fishek = 190;
        }

        if (uroven == 15) {
            mGrid.setNumColumns(6);
            mAdapter = new Pole(this, 6, 10);
            koef_timeGame = 55;
            StepCount = 100;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 180;
        }

   /*     mGrid.setHorizontalSpacing(10); // расстояние между иконками
        mGrid.setVerticalSpacing(10);*/
        /* mGrid.setAut*/

        mGrid.setEnabled(true);

        mGrid.setAdapter(mAdapter);

        timeScreen = findViewById(R.id.time_view);
        timeItog = findViewById(R.id.timeItog);
        stepScreen = findViewById(R.id.step_view);
        buPause = findViewById(R.id.buPause);

        // шрифт
        /* Typeface type = Typeface.createFromAsset(getAssets(),"komi.ttf");*/

        Typeface type = getResources().getFont(R.font.ocker);
        timeScreen.setTypeface(type);
        stepScreen.setTypeface(type);

        StepCountStart = StepCount;

        stepScreen.setText(StepCount.toString());

        timeGame = 1000 * 6 * koef_timeGame;  // Задаём время игры

        timeScreen.setBase(SystemClock.elapsedRealtime() + timeGame);
        timeScreen.start();

        timeItog.setBase(SystemClock.elapsedRealtime() - 1000);
        timeItog.start();

        timeScreen   // Определение проигрыша
                .setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        if (chronometer.getText().toString().equalsIgnoreCase("00:00") ) {
                            PrichinGameOver = "ВРЕМЯ ИГРЫ ИСТЕКЛО";
                            gameOver(null);
                        }
                        if ( StepCount == 0) {
                            PrichinGameOver = "ИЗРАСХОДОВАНО ЗАДАННОЕ ЧИСЛО ХОДОВ";
                            gameOver(null);
                        }
                    }
                });

        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                mAdapter.checkOpenCells();
                if (mAdapter.openCell(position)) {
                    StepCount--;
                    stepScreen.setText(StepCount.toString());
                }
                if (mAdapter.checkGameOver()) {
                    timeScreen.stop();

                    ShowGameOver();
                }
            }
        });

        //  УВЕДОМЛЕНИЯ
        // https://stackru.com/questions/54083216/sozdanie-uvedomleniya-android-kotoroe-povtoryaetsya-kazhdyij-den-v-opredelennoe?ysclid=lplngnkswq786714462

      /*  Intent notifyIntent = new Intent(this, ReceiverNapomin.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_REMINDER, notifyIntent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 10);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);*/
    }

    private void ShowGameOver() {

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        // Заголовок и текст
        alertbox.setTitle("ПОЗДРАВЛЯЕМ!");
        StepCountIspolz = StepCountStart - StepCount; // Подсчет количества использованных ходов

        String time = timeItog.getText().toString();

        uroven = uroven + 1;


        int bonusStep = StepCount;
        long bonusTimeViv = (SystemClock.elapsedRealtime() - timeScreen.getBase()) * (-1);
        score = score + StepCount;
        bonusTime = bonusTime + (int) (bonusTimeViv / 1000);

        if (urovenMax < uroven) {
            urovenMax = uroven;
            scoreMax = score;
            bonusTimeMax = bonusTime;
        }

        onPause();

        String TextToast = "ВЫ ПОБЕДИЛИ" + "\n \n" + "Открыт уровень: " + uroven + " \n \n" + "Сделано ходов: " + StepCountIspolz + " \n\n " +
                "Истрачено времени: " + time + " \n\n " + "Бонусные очки: " + bonusStep + " \n\n " + "Бонусное время: " + (bonusTimeViv / 1000) + " сек";

        alertbox.setMessage(TextToast);

        // Добавляем кнопку
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // закрываем текущюю Activity
                /*  finish();*/

                clickMenu(null);

            }
        });
        // показываем окно
        alertbox.show();

    }

    public void gameOver(View view) {      // Переход на другой класс (сдесь класс Vvod)
        Intent intent = new Intent(this, GameOver.class);
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
    }

// ПАУЗА - ПРОДОЛЖИТЬ

    public void clickPause(View view) {

        if (sostoyniePause == false) {
            timeStopped = timeScreen.getBase() - SystemClock.elapsedRealtime();
            timeScreen.stop();
            sostoyniePause = true;
            buPause.setText("" + "ПРОДОЛЖИТЬ");

            GridView b1 = (GridView) findViewById(R.id.igrovoePole);
            b1.setEnabled(false);
            GridView a1 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a1.setAlpha(1f);
            a1.animate().alpha(0.1f).setDuration(1500);

        } else {
            timeScreen.setBase(SystemClock.elapsedRealtime() + timeStopped);
            timeScreen.start();
            sostoyniePause = false;
            buPause.setText("" + "ПАУЗА");

            GridView b1 = (GridView) findViewById(R.id.igrovoePole);
            b1.setEnabled(true);
            GridView a1 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a1.setAlpha(0.2f);
            a1.animate().alpha(1f).setDuration(1000);
        }
    }

    public void clickMenu(View view) {

        Intent intent = new Intent(this, Menu.class);    // Переход на другой класс (сдесь класс Vvod)
        startActivity(intent);
    }

}