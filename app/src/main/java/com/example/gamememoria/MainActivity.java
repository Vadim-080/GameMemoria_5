// Игра для тренировки памяти - начало разработки 13.12.2023
//
package com.example.gamememoria;

import static com.example.gamememoria.Menu1.Key_Score;
import static com.example.gamememoria.Menu1.Key_Score_Max;
import static com.example.gamememoria.Menu1.Key_Slognost_Max;
import static com.example.gamememoria.Menu1.Key_Slognost_game;
import static com.example.gamememoria.Menu1.Key_Slognost_step;
import static com.example.gamememoria.Menu1.Key_Slognost_time;
import static com.example.gamememoria.Menu1.Key_Time;
import static com.example.gamememoria.Menu1.Key_Time_Max;
import static com.example.gamememoria.Menu1.Key_Uroven;
import static com.example.gamememoria.Menu1.Key_Uroven_Max;
import static com.example.gamememoria.Menu1.Shirin_fishek;
import static com.example.gamememoria.Menu1.Visot_fishek;
import static com.example.gamememoria.Menu1.bonusTime;
import static com.example.gamememoria.Menu1.bonusTimeMax;
import static com.example.gamememoria.Menu1.koef_slogn_step;
import static com.example.gamememoria.Menu1.koef_slogn_time;

import static com.example.gamememoria.Menu1.mSettings;
import static com.example.gamememoria.Menu1.score;
import static com.example.gamememoria.Menu1.scoreMax;
import static com.example.gamememoria.Menu1.slognostMax;
import static com.example.gamememoria.Menu1.slognost_game;
import static com.example.gamememoria.Menu1.uroven;
import static com.example.gamememoria.Menu1.urovenMax;
import static com.example.gamememoria.Menu1.PrichinGameOver;
import static com.example.gamememoria.Zastavka.promegutGameOverPodrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
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
    public static long bonusTimeViv;
    public static int bonusStep;
    public static String timeIstr;
    private Button buPause;
    private Chronometer timeScreen, timeItog;
    private Integer StepCount; // кол-во ходов
    private Integer StepCountStart; // начальное число ходов, для вычислений
    public static Integer StepCountIspolz; // Использованное за игру число ходов, для вычислений
    private GridView mGrid;
    private PoleGame mAdapter;
    private static final int NOTIFICATION_REMINDER = 3;
    int koef_timeGame; // Коэфициент для задания времени игры
    TextView namberUroven, nadpUrovenGame, time;
    boolean zapuskBonusTime = false;
    boolean zapuskBonusScore = false;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // Запрет тускнениия экрана телефона и его выключения во время игры

        ProzrachButton();

        iconSlogn = findViewById(R.id.slogn_viev);
        mGrid = findViewById(R.id.igrovoePole);
        namberUroven = findViewById(R.id.NamberUroven_view);
        stepScreen = findViewById(R.id.step_view);
        time = findViewById(R.id.time_view);
        timeScreen = findViewById(R.id.time_view);
        timeItog = findViewById(R.id.timeItog);
        buPause = findViewById(R.id.buPause);
        nadpUrovenGame = findViewById(R.id.nadpUrovenGame_view);

        Typeface type1 = getResources().getFont(R.font.qwe);    // шрифт
        nadpUrovenGame.setTypeface(type1);
        Typeface type2 = getResources().getFont(R.font.pacifico_regular);    // шрифт
        namberUroven.setTypeface(type2);

        onResume();

        switch (slognost_game) {
            case 1:
                iconSlogn.setImageResource(R.drawable.slogn1);
                break;
            case 2:
                iconSlogn.setImageResource(R.drawable.slogn2);
                break;
            case 3:
                iconSlogn.setImageResource(R.drawable.slogn3);
                break;
            case 4:
                iconSlogn.setImageResource(R.drawable.slogn4);
                break;
        }

        namberUroven.setText("" + uroven);

        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim_uroven);
        namberUroven.startAnimation(a1);

        ParametrUrovneiGame();

        mGrid.setEnabled(true);
        mGrid.setAdapter(mAdapter);

        Typeface type = getResources().getFont(R.font.ocker);    // шрифт

        timeScreen.setTypeface(type);
        stepScreen.setTypeface(type);

        StepCountStart = StepCount;

        stepScreen.setText(StepCount.toString());

        timeGame = 1000 * 6 * koef_timeGame;  // Задаём время игры

        timeScreen.setBase(SystemClock.elapsedRealtime() + timeGame);
        timeScreen.start();   // Запуск время игры

        timeItog.setBase(SystemClock.elapsedRealtime() - 1000);
        timeItog.start();   // Запуск подсчета итогового времени

        timeScreen   // Определение проигрыша
                .setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {

                        long provOkonshTimeVZdushemRezime = SystemClock.elapsedRealtime() - timeScreen.getBase();

                        if (StepCount <= 6) {
                            stepScreen.setTextColor(Color.BLUE);
                            miganStep1(null);
                        }
                        if (StepCount <= 4) {
                            stepScreen.setTextColor(Color.MAGENTA);
                            miganStep1(null);
                        }
                        if (StepCount <= 2) {
                            stepScreen.setTextColor(Color.RED);
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

                        if (StepCount <= 0 & score != 0 & zapuskBonusScore == false) {    // Предлаг использ бонус очки
                            timeScreen.stop();
                            ProdolGameStep();
                        }
                        zapuskBonusScore = false;

                        if (chronometer.getText().toString().equalsIgnoreCase("00:00") & bonusTime != 0
                                & zapuskBonusTime == false) {  // Предлаг использ бонус время
                            timeScreen.stop();
                            ProdolGameTime();

                        }
                        zapuskBonusTime = false;


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
    }

   /* @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }*/

    // Диалоговое окно "Использовать бонусные ходы"
    private void ProdolGameStep() {

        GridView a1 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
        a1.setAlpha(1f);
        a1.animate().alpha(0.3f).setDuration(1000);

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this, R.style.StyleDialogOknaStep);

        // Заголовок и текст
        alertbox.setTitle("\n" + "   ХОДЫ ЗАКОНЧИЛИСЬ!");

        String TextToast = "\n" + "\n" + "для продолжения игры" + "\n" + "можно использовать:" + "\n" + "\n" + score +
                "   монет" + "\n" + "\n" + "ХОТИТЕ ПРОДОЛЖИТЬ ЭТУ ИГРУ?" + "\n" + "\n";

        alertbox.setMessage(TextToast);

        // Добавляем кнопки

        alertbox.setPositiveButton("ДА" + "\n", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

                zapuskBonusScore = true;
                timeScreen.start();
                stepScreen.setTextColor(Color.parseColor("#AA00FF"));
                stepScreen.clearAnimation();  // Остановка Анимации ходов
                StepCount = score;
                stepScreen.setText(StepCount.toString());
                score = 0;

                ProzrachButton();
            }
        });
        alertbox.setNegativeButton("НАЧАТЬ ИГРУ ЗАНОВО", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                PovtorGame(null);
            }
        });

        AlertDialog alert = alertbox.create();    //  https://stackoverflow.com/questions/33437398/how-to-change-textcolor-in-alertdialog
        alert.show();  // показываем окно
        alert.setCanceledOnTouchOutside(false);  // Не даёт закрывать диалоговое окно при нажатии на пустое место экрана

        Button nbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.RED);
        nbutton.setTextSize(17);

        Button nbutton2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton2.setTextColor(Color.DKGRAY);
        nbutton2.setTextSize(17);

        // показываем окно
        /* alertbox.show();*/
    }

    // Диалоговое окно "Использовать бонусное время"
    private void ProdolGameTime() {

        GridView a1 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
        a1.setAlpha(1f);
        a1.animate().alpha(0.3f).setDuration(1500);

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this, R.style.StyleDialogOknaTime);

        // Заголовок и текст
        alertbox.setTitle("\n" + "    ВРЕМЯ ЗАКОНЧИЛОСЬ!");

        String TextToast = "\n" + "\n" + "для продолжения игры" + "\n" + "можно использовать:" + "\n" + "\n" +
                bonusTime + "   секунд" + "\n" + "\n" + "бонусного времени" + "\n" + "\n" + "ХОТИТЕ ПРОДЛИТЬ ВРЕМЯ?" + "\n" + "\n";

        alertbox.setMessage(TextToast);

        // Добавляем кнопки

        alertbox.setPositiveButton("ДА" + "\n", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                zapuskBonusTime = true;
                timeGame = 1000 * bonusTime;
                timeScreen.setBase(SystemClock.elapsedRealtime() + timeGame);
                timeScreen.start();
                time.setTextColor(Color.parseColor("#00FF00"));
                time.clearAnimation();  // Остановка Анимации времени
                bonusTime = 0;

                ProzrachButton();
            }
        });

        alertbox.setNegativeButton("НАЧАТЬ ИГРУ ЗАНОВО", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                PovtorGame(null);
            }
        });

        AlertDialog alert = alertbox.create();    //  https://stackoverflow.com/questions/33437398/how-to-change-textcolor-in-alertdialog

        alert.show(); // показываем окно
        alert.setCanceledOnTouchOutside(false);  // Не даёт закрывать диалоговое окно при нажатии на пустое место экрана

// Цвет заголовка и цвет разделительной полосы
        /*try {
            Resources resources = alert.getContext().getResources();
            int alertTitleId = resources.getIdentifier("alertTitle", "id", "android");
            TextView alertTitle = (TextView) alert.getWindow().getDecorView().findViewById(alertTitleId);
            alertTitle.setTextColor(Color.MAGENTA); // change title text color

            int titleDividerId = resources.getIdentifier("titleDivider", "id", "android");
            View titleDivider = alert.getWindow().getDecorView().findViewById(titleDividerId);
            titleDivider.setBackgroundColor(Color.YELLOW); // change divider color
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        Button nbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.MAGENTA);
        nbutton.setTextSize(17);

        Button nbutton2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton2.setTextColor(Color.BLUE);
        nbutton2.setTextSize(17);

        /* alertbox.show(); */   // показываем окно


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

    private void GamePobeda() {

        promegutGameOverPodrad = 0;
        StepCountIspolz = StepCountStart - StepCount; // Подсчет количества использованных ходов

        timeIstr = timeItog.getText().toString();

        uroven = uroven + 1;

        bonusStep = StepCount;
        bonusTimeViv = (SystemClock.elapsedRealtime() - timeScreen.getBase()) * (-1);
        score = score + StepCount;
        bonusTime = bonusTime + (int) (bonusTimeViv / 1000);

        if (urovenMax < uroven) {
            urovenMax = uroven;
            scoreMax = score;
            bonusTimeMax = bonusTime;
            slognostMax = slognost_game;
        }

        onPause();

        Intent intent = new Intent(this, Pobeda.class);    // Переход на другой класс
        startActivity(intent);
    }

    public void gameOver(View view) {      // Переход на другой класс (сдесь класс Vvod)
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);
    }

    public void miganStep1(View view) {

        Animation a = AnimationUtils.loadAnimation(this, R.anim.anim_mig_step1);
        stepScreen.startAnimation(a);
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

        SharedPreferences.Editor a7 = mSettings.edit();
        a7.putInt(String.valueOf(Key_Slognost_Max), slognostMax);
        a7.apply();
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

            GridView b1 = (GridView) findViewById(R.id.igrovoePole);  // Блокировка КНОПКИ
            b1.setEnabled(false);
            GridView a1 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a1.setAlpha(1f);
            a1.animate().alpha(0.1f).setDuration(1500);

        } else {
            timeScreen.setBase(SystemClock.elapsedRealtime() + timeStopped);
            timeScreen.start();
            sostoyniePause = false;
            buPause.setText("" + "ПАУЗА");

            GridView b1 = (GridView) findViewById(R.id.igrovoePole);  // Блокировка КНОПКИ
            b1.setEnabled(true);
            GridView b2 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
            b2.setAlpha(0.1f);
            b2.animate().alpha(1f).setDuration(1500);
        }
    }

    public void clickMenu(View view) {

        Intent intent = new Intent(this, Menu1.class);    // Переход на другой класс
        startActivity(intent);
    }

    public void PovtorGame(View view) {

        Intent intent = new Intent(this, MainActivity.class);    // Переход на другой класс
        startActivity(intent);
    }

    private void ParametrUrovneiGame() {
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
            StepCount = 92 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 190;
        }

        if (uroven == 14) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 9);
            koef_timeGame = 47 + koef_slogn_time;
            StepCount = 100 + koef_slogn_step; // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 160;
        }

        if (uroven == 15) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 10);
            koef_timeGame = 55 + koef_slogn_time;
            StepCount = 126 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 170;
        }

        if (uroven == 16) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 11);
            koef_timeGame = 60 + koef_slogn_time;
            StepCount = 138 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 160;
        }

        if (uroven == 17) {
            mGrid.setNumColumns(7);
            mAdapter = new PoleGame(this, 7, 10);
            koef_timeGame = 65 + koef_slogn_time;
            StepCount = 146 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 160;
            Shirin_fishek = 150;
        }

        if (uroven == 18) {
            mGrid.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 12);
            koef_timeGame = 68 + koef_slogn_time;
            StepCount = 150 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 140;
            Shirin_fishek = 150;
        }

        if (uroven == 19) {
            mGrid.setNumColumns(7);
            mAdapter = new PoleGame(this, 7, 12);
            koef_timeGame = 80 + koef_slogn_time;
            StepCount = 170 + koef_slogn_step;  // Задаём максимальное количество ходов
             Visot_fishek = 140;
            Shirin_fishek = 150;
        }
    }

    private void ProzrachButton() {
        GridView b1 = findViewById(R.id.igrovoePole);   // ПРОЗРАЧНОСТЬ КНОПКИ
        b1.setAlpha(0.3f);
        b1.animate().alpha(1f).setDuration(2000);
    }
}

 /*     mGrid.setHorizontalSpacing(10); // расстояние между иконками
        mGrid.setVerticalSpacing(10);*/
/* mGrid.setAut*/

// https://developer.alexanderklimov.ru/android/games/memoria.php?ysclid=lq527nk13p378087110
// https://ru-code-android.livejournal.com/2665.html

/* Typeface type = Typeface.createFromAsset(getAssets(),"komi.ttf");*/  // Шрифт