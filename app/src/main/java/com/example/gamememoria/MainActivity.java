// Игра для тренировки памяти - начало разработки 13.12.2023
//
package com.example.gamememoria;

import static com.example.gamememoria.Menu.Key_Score;
import static com.example.gamememoria.Menu.Key_Score_Max;
import static com.example.gamememoria.Menu.Key_Slognost_game;
import static com.example.gamememoria.Menu.Key_Slognost_step;
import static com.example.gamememoria.Menu.Key_Slognost_time;
import static com.example.gamememoria.Menu.Key_Time;
import static com.example.gamememoria.Menu.Key_Time_Max;
import static com.example.gamememoria.Menu.Key_Uroven;
import static com.example.gamememoria.Menu.Key_Uroven_Max;
import static com.example.gamememoria.Menu.Shirin_fishek;
import static com.example.gamememoria.Menu.Visot_fishek;
import static com.example.gamememoria.Menu.bonusTime;
import static com.example.gamememoria.Menu.bonusTimeMax;
import static com.example.gamememoria.Menu.koef_slogn_step;
import static com.example.gamememoria.Menu.koef_slogn_time;

import static com.example.gamememoria.Menu.mSettings;
import static com.example.gamememoria.Menu.score;
import static com.example.gamememoria.Menu.scoreMax;
import static com.example.gamememoria.Menu.slognost_game;
import static com.example.gamememoria.Menu.uroven;
import static com.example.gamememoria.Menu.urovenMax;
import static com.example.gamememoria.Menu.PrichinGameOver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int timeGame;  // Задаём время игры на разных уровнях
    long timeStopped = 0; // для остановки времени во время паузы
    boolean sostoyniePause = false; // для переключения надписи кнопки пвуза-продолжить
    private TextView stepScreen;
    private ImageView iconSlogn;

    private Button buPause;
    private Chronometer timeScreen, timeItog;

    private Integer StepCount; // кол-во ходов
    private Integer StepCountStart; // начальное число ходов, для вычислений
    private Integer StepCountIspolz; // Использованное за игру число ходов, для вычислений
    private GridView mGrid;
    private PoleGame mAdapter;
    private static final int NOTIFICATION_REMINDER = 3;

    int koef_timeGame; // Коэфициент для задания времени игры

    TextView namberUroven, step, time;

    int bonusStep;


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

        iconSlogn = findViewById(R.id.slogn_viev);

// https://developer.alexanderklimov.ru/android/games/memoria.php?ysclid=lq527nk13p378087110
// https://ru-code-android.livejournal.com/2665.html


        mGrid = findViewById(R.id.igrovoePole);
        namberUroven = findViewById(R.id.NamberUroven_view);
        step = findViewById(R.id.step_view);
        time = findViewById(R.id.time_view);

        onResume();

        if (slognost_game == 1) {
            iconSlogn.setImageResource(R.drawable.slogn1);
        }
        if (slognost_game == 2) {
            iconSlogn.setImageResource(R.drawable.slogn2);
        }
        if (slognost_game == 3) {
            iconSlogn.setImageResource(R.drawable.slogn3);
        }
        if (slognost_game == 4) {
            iconSlogn.setImageResource(R.drawable.slogn4);
        }

        namberUroven.setText("" + uroven);

        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim_uroven);
        namberUroven.startAnimation(a1);

        if (uroven == 1) {
            mGrid.setNumColumns(3);
            mAdapter = new PoleGame(this, 3, 4);
            koef_timeGame = 5 + koef_slogn_time;
            StepCount = 20 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 300;
            Shirin_fishek = 300;

        }

        if (uroven == 2) {
            mGrid.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 4);
            koef_timeGame = 8 + koef_slogn_time;
            StepCount = 28 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 3) {
            mGrid.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 5);
            koef_timeGame = 11 + koef_slogn_time;
            StepCount = 36 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 4) {
            mGrid.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 6);
            koef_timeGame = 13 + koef_slogn_time;
            StepCount = 46 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 250;
            Shirin_fishek = 260;
        }

        if (uroven == 5) {
            mGrid.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 7);
            koef_timeGame = 15 + koef_slogn_time;
            StepCount = 54 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 240;
            Shirin_fishek = 250;
        }

        if (uroven == 6) {
            mGrid.setNumColumns(5);
            mAdapter = new PoleGame(this, 5, 6);
            koef_timeGame = 17 + koef_slogn_time;
            StepCount = 56 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 230;
            Shirin_fishek = 220;
        }

        if (uroven == 7) {
            mGrid.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 8);
            koef_timeGame = 19 + koef_slogn_time;
            StepCount = 60 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 210;
            Shirin_fishek = 220;
        }
        if (uroven == 8) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 6);
            koef_timeGame = 23 + koef_slogn_time;
            StepCount = 66 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }
        if (uroven == 9) {
            mGrid.setNumColumns(5);
            mAdapter = new PoleGame(this, 5, 8);
            koef_timeGame = 75 + koef_slogn_time;
            StepCount = 70 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 210;
            Shirin_fishek = 220;
        }
        if (uroven == 10) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 7);
            koef_timeGame = 78 + koef_slogn_time;
            StepCount = 74 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }
        if (uroven == 11) {
            mGrid.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 11);
            koef_timeGame = 33 + koef_slogn_time;
            StepCount = 78 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 160;
        }
        if (uroven == 12) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 8);
            koef_timeGame = 38 + koef_slogn_time;
            StepCount = 86 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }

        if (uroven == 13) {
            mGrid.setNumColumns(5);
            mAdapter = new PoleGame(this, 5, 10);
            koef_timeGame = 42 + koef_slogn_time;
            StepCount = 90 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 190;
        }

        if (uroven == 14) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 9);
            koef_timeGame = 47 + koef_slogn_time;
            StepCount = 96 + koef_slogn_step; // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 160;
        }

        if (uroven == 15) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 10);
            koef_timeGame = 55 + koef_slogn_time;
            StepCount = 100 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 170;
        }

        if (uroven == 16) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 11);
            koef_timeGame = 60 + koef_slogn_time;
            StepCount = 110 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 160;
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

                        long provOkonshTimeVZdushemRezime = SystemClock.elapsedRealtime() - timeScreen.getBase();

                        if (StepCount <= 6) {
                            step.setTextColor(Color.BLUE);
                            miganStep1(null);
                        }
                        if (StepCount <= 4) {
                            step.setTextColor(Color.MAGENTA);
                            miganStep1(null);
                        }
                        if (StepCount <= 2) {
                            step.setTextColor(Color.RED);
                            miganStep1(null);
                        }


                        if (chronometer.getText().toString().equalsIgnoreCase("00:10")) {
                            time.setTextColor(Color.DKGRAY);
                            miganTime1(null);
                        }
                        if (chronometer.getText().toString().equalsIgnoreCase("00:08")) {
                            time.setTextColor(Color.BLUE);
                            miganTime1(null);
                        }
                        if (chronometer.getText().toString().equalsIgnoreCase("00:06")) {
                            time.setTextColor(Color.MAGENTA);
                            miganTime1(null);
                        }
                        if (chronometer.getText().toString().equalsIgnoreCase("00:04")) {
                            time.setTextColor(Color.RED);
                            miganTime1(null);
                        }


                        if (StepCount <= 0 & score != 0) {    // Предлаг использ бонус очки
                            ProdolGameStep();
                        }

                        if (chronometer.getText().toString().equalsIgnoreCase("00:00") & bonusTime != 0) {  // Предлаг использ бонус время
                            timeScreen.stop();
                            ProdolGameTime();
                        }


                        if (StepCount <= 0 & score == 0) {
                            PrichinGameOver = "ИЗРАСХОДОВАНО  ЗАДАННОЕ  ЧИСЛО  ХОДОВ";
                            gameOver(null);
                        }
                        if ((chronometer.getText().toString().equalsIgnoreCase("00:00")
                                | (provOkonshTimeVZdushemRezime >= 0)) & bonusTime == 0) {
                            PrichinGameOver = "ВРЕМЯ  ИГРЫ  ИСТЕКЛО";
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

                    GamePobeda();
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

    private void ProdolGameStep() {

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        // Заголовок и текст
        alertbox.setTitle("ХОДЫ!");

        String TextToast = "МОЖНО ИСПОЛЬЗ " + score + "  БОН ОЧКОВ";

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

    private void ProdolGameTime() {

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        // Заголовок и текст
        alertbox.setTitle("ВРЕМЯ!");

        String TextToast = "МОЖНО ИСПОЛЬЗ " + bonusTime + " сек  БОН врем";

        alertbox.setMessage(TextToast);

        // Добавляем кнопки
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // закрываем текущюю Activity
                /*  finish();*/

                clickMenu(null);

            }
        });

        alertbox.setNeutralButton("НЕТ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                clickMenu(null);

            }
        });


        // показываем окно
        alertbox.show();

    }

    private void GamePobeda() {
        StepCountIspolz = StepCountStart - StepCount; // Подсчет количества использованных ходов

        String time = timeItog.getText().toString();

        uroven = uroven + 1;

        bonusStep = StepCount;
        long bonusTimeViv = (SystemClock.elapsedRealtime() - timeScreen.getBase()) * (-1);
        score = score + StepCount;
        bonusTime = bonusTime + (int) (bonusTimeViv / 1000);

        if (urovenMax < uroven) {
            urovenMax = uroven;
            scoreMax = score;
            bonusTimeMax = bonusTime;
        }

        onPause();

        Intent intent = new Intent(this, Pobeda.class);    // Переход на другой класс
        startActivity(intent);

        /*String TextToast = "ВЫ ПОБЕДИЛИ" + "\n \n" + "Открыт уровень: " + uroven + " \n \n" + "Сделано ходов: " + StepCountIspolz + " \n\n " +
                "Истрачено времени: " + time + " \n\n " + "Бонусные очки: " + bonusStep + " \n\n " + "Бонусное время: " + (bonusTimeViv / 1000) + " сек";
        */
    }


    public void gameOver(View view) {      // Переход на другой класс (сдесь класс Vvod)
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);
    }

    public void miganStep1(View view) {

        Animation a = AnimationUtils.loadAnimation(this, R.anim.anim_mig_step1);
        step.startAnimation(a);
    }

    public void miganTime1(View view) {

        Animation a = AnimationUtils.loadAnimation(this, R.anim.anim_mig_time1);
        time.startAnimation(a);
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

    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Time))) {
            bonusTime = mSettings.getInt(String.valueOf(Key_Time), 0);
        } else bonusTime = 0;

        if (mSettings.contains(String.valueOf(Key_Slognost_time))) {
            koef_slogn_time = mSettings.getInt(String.valueOf(Key_Slognost_time), 0);
        } else {
            Intent i = new Intent(this, Slognost.class);
            startActivity(i);
        }

        if (mSettings.contains(String.valueOf(Key_Slognost_step))) {
            koef_slogn_step = mSettings.getInt(String.valueOf(Key_Slognost_step), 0);
        } else {
            Intent i = new Intent(this, Slognost.class);
            startActivity(i);
        }
        if (mSettings.contains(String.valueOf(Key_Slognost_game))) {
            slognost_game = mSettings.getInt(String.valueOf(Key_Slognost_game), 0);
        } else slognost_game = 1;
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

        Intent intent = new Intent(this, Menu.class);    // Переход на другой класс
        startActivity(intent);
    }


}