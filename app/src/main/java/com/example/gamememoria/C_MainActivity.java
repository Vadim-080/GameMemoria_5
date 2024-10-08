// Игра для тренировки памяти - начало разработки 13.12.2023
//
package com.example.gamememoria;

import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
import static com.example.gamememoria.A_Zastavka.promegutGameOverPodrad;
import static com.example.gamememoria.B_Menu.Key_Koef_Dostign_Slogn;
import static com.example.gamememoria.B_Menu.Key_Koef_Pobed;
import static com.example.gamememoria.B_Menu.Key_Koef_Pobed_Max;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Igr;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Pobed;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Proigr_Step;
import static com.example.gamememoria.B_Menu.Key_Kolvo_Proigr_Time;
import static com.example.gamememoria.B_Menu.Key_Score;
import static com.example.gamememoria.B_Menu.Key_Score_Max;
import static com.example.gamememoria.B_Menu.Key_Slognost_Max;
import static com.example.gamememoria.B_Menu.Key_Slognost_game;
import static com.example.gamememoria.B_Menu.Key_Slognost_step;
import static com.example.gamememoria.B_Menu.Key_Slognost_time;
import static com.example.gamememoria.B_Menu.Key_Time;
import static com.example.gamememoria.B_Menu.Key_Time_Max;
import static com.example.gamememoria.B_Menu.Key_Urov_Volum;
import static com.example.gamememoria.B_Menu.Key_Uroven;
import static com.example.gamememoria.B_Menu.Key_Uroven_Max;
import static com.example.gamememoria.B_Menu.Key_pologen_regul_Volum;
import static com.example.gamememoria.B_Menu.PrichinGameOver;
import static com.example.gamememoria.B_Menu.Shirin_fishek;
import static com.example.gamememoria.B_Menu.Visot_fishek;
import static com.example.gamememoria.B_Menu.bonusTime;
import static com.example.gamememoria.B_Menu.bonusTimeMax;
import static com.example.gamememoria.B_Menu.fonMusic;
import static com.example.gamememoria.B_Menu.koefDostignSlogn;
import static com.example.gamememoria.B_Menu.koefPobed;
import static com.example.gamememoria.B_Menu.koefPobedMax;
import static com.example.gamememoria.B_Menu.koef_slogn_step;
import static com.example.gamememoria.B_Menu.koef_slogn_time;
import static com.example.gamememoria.B_Menu.kolvoIgr;
import static com.example.gamememoria.B_Menu.kolvoPobed;
import static com.example.gamememoria.B_Menu.zadanUrovVolume;
import static com.example.gamememoria.B_Menu.kolvoProigrStep;
import static com.example.gamememoria.B_Menu.kolvoProigrTime;
import static com.example.gamememoria.B_Menu.mSettings;
import static com.example.gamememoria.B_Menu.pologSostoyanSvernutogoPrilogen;
import static com.example.gamememoria.B_Menu.pologenRegulVolum;
import static com.example.gamememoria.B_Menu.score;
import static com.example.gamememoria.B_Menu.scoreMax;
import static com.example.gamememoria.B_Menu.slognostMax;
import static com.example.gamememoria.B_Menu.slognost_game;
import static com.example.gamememoria.B_Menu.timeOnFonMusik;
import static com.example.gamememoria.B_Menu.uroven;
import static com.example.gamememoria.B_Menu.urovenMax;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusic;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusicProdolgenGame;
import static java.util.Calendar.getInstance;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;

public class C_MainActivity extends AppCompatActivity {

    public static long bonusTimeViv;
    public static int bonusStep;
    public static String timeIstr;
    private Chronometer timeScreen, timeItog;
    private Integer StepCount; // кол-во ходов
    private Integer StepCountStart; // начальное число ходов, для вычислений
    public static Integer StepCountIspolz; // Использованное за игру число ходов, для вычислений
    private GridView igrovoePole;
    private PoleGame mAdapter;
    private static final int NOTIFICATION_REMINDER = 3;
    long timeStopped = 0; // для остановки времени во время паузы
    boolean sostoyniePause = false; // для переключения надписи кнопки пвуза-продолжить
    boolean zapuskBonusTime = false;
    boolean zapuskBonusScore = false;
    int urovenVolume;
    int timeGame;  // Задаём время игры на разных уровнях
    int koef_timeGame; // Коэфициент для задания времени игры
    boolean sostTimeOver = false;

    Button buPause, buStart;
    ImageView iconSlogn;
    TextView stepScreen, namberUroven, nadpUrovenGame, time;
    ConstraintLayout fonKartink;
    ImageButton mute;
    Button btn1;
    Animation animation1;

    // звуковые переменные
    public static MediaPlayer musicVsplivOknoProdolGame;
    MediaPlayer zvPerevorashKart, zvTimeOver, zvMute, zvPause, zvProdolgitGame, zvStartGame, zvPerexV_Menu;


    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconSlogn = findViewById(R.id.slogn_viev);
        igrovoePole = findViewById(R.id.igrovoePole);
        namberUroven = findViewById(R.id.NamberUroven_view);
        stepScreen = findViewById(R.id.step_view);
        time = findViewById(R.id.time_view);
        timeScreen = findViewById(R.id.time_view);
        timeItog = findViewById(R.id.timeItog);
        buPause = findViewById(R.id.buPause);
        buStart = findViewById(R.id.buStart);
        mute = findViewById(R.id.buMute);
        nadpUrovenGame = findViewById(R.id.nadpUrovenGame_view);
        fonKartink = findViewById(R.id.fon_game_view);

// Задаём звуковые сигналы

        zvPerevorashKart = MediaPlayer.create(this, R.raw.zv_perevorash_kart_1);
        zvMute = MediaPlayer.create(this, R.raw.mute_1);
        zvPause = MediaPlayer.create(this, R.raw.pause);
        zvProdolgitGame = MediaPlayer.create(this, R.raw.prodolgit_game);
        zvTimeOver = MediaPlayer.create(this, R.raw.time_over);
        zvStartGame = MediaPlayer.create(this, R.raw.start_game_1);
        zvPerexV_Menu = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1);

// Задаём Шрифт
        nadpUrovenGame.setTypeface(getResources().getFont(R.font.qwe));
        namberUroven.setTypeface(getResources().getFont(R.font.vanowitsch));
        timeScreen.setTypeface(getResources().getFont(R.font.ocker));
        stepScreen.setTypeface(getResources().getFont(R.font.ocker));

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        ConstraintLayout LinearLayout = findViewById(R.id.fon_game_view);

        int currentVis = LinearLayout.getSystemUiVisibility();
        int newVis;
        if ((currentVis & View.SYSTEM_UI_FLAG_LOW_PROFILE) == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            newVis = View.SYSTEM_UI_FLAG_VISIBLE;
        } else {
            newVis = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        LinearLayout.setSystemUiVisibility(newVis);

// Задаем цвет верхей строки и строки навигации
      /*  if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }*/

// Запрет тускнениия экрана телефона и его выключения во время игры
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mute.setAlpha(0.7f);    // ПРОЗРАЧНОСТЬ КНОПКИ Mute
        viborFonKartinki(); // Выбор фоновой картинки
        onResume(); // Вывод данных из памяти

        byte melodiyaGame = (byte) (Math.random() * 9); // Случайное число от 0 до 8 -- Для выбора мелодии фоновой музыки
        switch (melodiyaGame) {
            case 0:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_0);
                break;
            case 1:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_1);
                break;
            case 2:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_2);
                break;
            case 3:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_3);
                break;
            case 4:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_4);
                break;
            case 5:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_5);
                break;
            case 6:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_6);
                break;
            case 7:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_7);
                break;
            case 8:
                fonMusic = MediaPlayer.create(this, R.raw.music_game_8);
                break;
        }

        if (pologenieKnopkiMute == true) {
            urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            mute.setImageResource(R.drawable.mute);
        } else {
           /* urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            mute.setImageResource(R.drawable.zvuk);
        }

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

        igrovoePole.setEnabled(true);
        igrovoePole.setAdapter(mAdapter);

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

                            fonMusic.stop();
                            zvTimeOver.start();
                            sostTimeOver = true;


                            fonMusic.stop();

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

                        if ((chronometer.getText().toString().equalsIgnoreCase("00:00")
                                | (provOkonshTimeVZdushemRezime >= 0)) & bonusTime != 0 & zapuskBonusTime == false) {  // Предлаг использ бонус время
                            timeScreen.stop();
                            ProdolGameTime();
                        }
                        zapuskBonusTime = false;

                        if (StepCount <= 0 & score == 0) {
                            igrovoePole.setEnabled(false);  // Блокировка нажатия игрового поля
                            PrichinGameOver = "ИЗРАСХОДОВАНО  ЗАДАННОЕ  ЧИСЛО  ХОДОВ";
                            timeScreen.stop();
                            kolvoProigrStep = kolvoProigrStep + 1;
                            gameOver();
                        }
                        if ((chronometer.getText().toString().equalsIgnoreCase("00:00")
                                | (provOkonshTimeVZdushemRezime >= 0)) & bonusTime == 0) {

                            PrichinGameOver = "ВРЕМЯ  ИГРЫ  ИСТЕКЛО";
                            timeScreen.stop();
                            kolvoProigrTime = kolvoProigrTime + 1;
                            gameOver();
                        }
                    }
                });

        igrovoePole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                zvPerevorashKart.start();
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

        animation1 = new AlphaAnimation(1, 0.1f); // Изменение с полностью видимого на невидимый
        animation1.setDuration(500); // продолжительность
        animation1.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation1.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation1.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        btn1 = (Button) findViewById(R.id.buPause);
        btn1.startAnimation(animation1);

        timeStopped = timeScreen.getBase() - SystemClock.elapsedRealtime();

        igrovoePole.setEnabled(false);    // Блокировка нажатия игрового поля
        igrovoePole.setAlpha(1f);    // ПРОЗРАЧНОСТЬ игрового поля
        igrovoePole.animate().alpha(0.5f).setDuration(1000);

        buPause.setEnabled(false);   // Блокировка КНОПКИ
        buPause.setAlpha(0f);     // ПРОЗРАЧНОСТЬ КНОПКИ
        /*   buPause.animate().alpha(0f).setDuration(1);*/


        Animation a4 = AnimationUtils.loadAnimation(this, R.anim.anim_bu_start_1);
        buStart.startAnimation(a4);

        timeStopped = timeScreen.getBase() - SystemClock.elapsedRealtime();
        timeScreen.stop();
    }

    // ПРЕКРАЩЕНИЕ Музыки при свертывании приложения
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        pologSostoyanSvernutogoPrilogen = true;

        if (pologenieKnopkiMute == false) {
            fonMusic.pause();
        }

        if (sostTimeOver == true) {
            zvTimeOver.pause();
        }


        /*  fonMusicGame.pause();*/


        // Приостанав игры при свертывании приложения*/
        if (sostoyniePause == false) {
            /*   clickPause(null); // Пуск игры после свертывании приложения*/

            timeStopped = timeScreen.getBase() - SystemClock.elapsedRealtime();
            timeScreen.stop();
            sostoyniePause = true;
            buPause.setTextSize(16);
            buPause.setText("" + "ПРОДОЛЖИТЬ");
            btn1.startAnimation(animation1);
            buPause.setTextColor(Color.parseColor("#63FF00")); // Цвет текста кнопки
            buPause.setBackgroundColor(Color.parseColor("#FF00B7"));   // Цвет поля кнопки
        }
    }

    // ВОЗОБНОВЛЯЕТ Музыку при возобновления работы после свертывании приложения
  /*  public void onStart() {
        super.onStart();
<<<<<<< HEAD:app/src/main/java/com/example/gamememoria/C_MainActivity.java
        if (pologenieKnopkiMute == false) {
            timeOnFonMusik = 3000;
            vklFonMusic();
        }
    }*/

    /*    vklFonMusic();*/


    // Диалоговое окно "Использовать бонусные ходы"
    private void ProdolGameStep() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // Разрешает тускнениия экрана телефона и его выключения во время игры

        // ПРОЗРАЧНОСТЬ КНОПКИ
        igrovoePole.setAlpha(1f);
        igrovoePole.animate().alpha(0.3f).setDuration(1000);


        if (pologenieKnopkiMute == false) {
            fonMusic.stop();
            timeOnFonMusik = 3000;
            vklFonMusicProdolgenGame();

            musicVsplivOknoProdolGame = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1); // ЗАДАТЬ МУЗЫКУ

        }


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

                // Разблокировка КНОПКИ
                igrovoePole.setEnabled(true);

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
        alert.setCanceledOnTouchOutside(false);  // Не даёт закрывать диалоговое окно при нажатии на пустое место экрана
        alert.show();  // показываем окно

        Button nbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.RED);
        nbutton.setTextSize(15);

        Button nbutton2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton2.setTextColor(Color.DKGRAY);
        nbutton2.setTextSize(13);
    }

    // Диалоговое окно "Использовать бонусное время"
    private void ProdolGameTime() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // Разрешает тускнениия экрана телефона и его выключения во время игры

        // ПРОЗРАЧНОСТЬ КНОПКИ
        igrovoePole.setAlpha(1f);
        igrovoePole.animate().alpha(0.3f).setDuration(1500);


        if (pologenieKnopkiMute == false) {
            fonMusic.stop();
            timeOnFonMusik = 3000;
            vklFonMusicProdolgenGame();

            musicVsplivOknoProdolGame = MediaPlayer.create(this, R.raw.zv_perxoda_v_menu_1); // ЗАДАТЬ МУЗЫКУ

        }


        AlertDialog.Builder alertbox = new AlertDialog.Builder(this, R.style.StyleDialogOknaTime);

        // Заголовок и текст
        alertbox.setTitle("\n" + "    ВРЕМЯ ЗАКОНЧИЛОСЬ!");

        String TextToast = "\n" + "\n" + "для продолжения игры" + "\n" + "можно использовать:" + "\n" + "\n" +
                bonusTime + "   секунд" + "\n" + "\n" + "бонусного времени" + "\n" + "\n" + "ХОТИТЕ ПРОДЛИТЬ ВРЕМЯ?" + "\n" + "\n";

        alertbox.setMessage(TextToast);

// Добавляем кнопки
        alertbox.setPositiveButton("ДА" + "\n", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // Разблокировка КНОПКИ
                igrovoePole.setEnabled(true);

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
        alert.setCanceledOnTouchOutside(false);  // Не даёт закрывать диалоговое окно при нажатии на пустое место экрана
        alert.show(); // показываем окно

        Button nbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setTextColor(Color.MAGENTA);
        nbutton.setTextSize(15);

        Button nbutton2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton2.setTextColor(Color.rgb(1, 90, 38));
        nbutton2.setTextSize(13);

        //  УВЕДОМЛЕНИЯ
        // https://stackru.com/questions/54083216/sozdanie-uvedomleniya-android-kotoroe-povtoryaetsya-kazhdyij-den-v-opredelennoe?ysclid=lplngnkswq786714462

        Intent notifyIntent = new Intent(this, ReceiverNapomin.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_REMINDER, notifyIntent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 10);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void GamePobeda() {

        kolvoPobed = kolvoPobed + 1;
        kolvoIgr = kolvoIgr + 1;

        promegutGameOverPodrad = 0;
        StepCountIspolz = StepCountStart - StepCount; // Подсчет количества использованных ходов

        timeIstr = timeItog.getText().toString();


        bonusStep = StepCount + uroven / slognost_game;
        bonusTimeViv = (SystemClock.elapsedRealtime() - timeScreen.getBase()) * (-1) + (2000 * uroven / slognost_game);
        score = score + bonusStep;
        bonusTime = (bonusTime + (int) (bonusTimeViv / 1000));

        uroven = uroven + 1;

        if (uroven == 23) {
            slognostMax = slognostMax + 1;
            slognost_game = slognost_game + 1;
            bonusTimeMax = bonusTime;
            scoreMax = score;
            urovenMax = 1;
            koefPobed = koefPobed + 1;

        } else {
            if (urovenMax < uroven) {
                urovenMax = uroven;
                scoreMax = score;
                bonusTimeMax = bonusTime;
                slognostMax = slognost_game;
                koefDostignSlogn = urovenMax * slognost_game;
                koefPobedMax = koefPobed;
            }
        }

        onPause();

        Intent intent = new Intent(this, H_Pobeda.class);    // Переход на другой класс
        startActivity(intent);
    }

    public void gameOver() {      // Переход на другой класс

        fonMusic.stop();
        kolvoIgr = kolvoIgr + 1;

        onPause();

        Intent intent = new Intent(this, G_GameOver.class);
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

        SharedPreferences.Editor a8 = mSettings.edit();
        a8.putInt(String.valueOf(Key_Koef_Pobed), koefPobed);
        a8.apply();

        SharedPreferences.Editor a9 = mSettings.edit();
        a9.putInt(String.valueOf(Key_Kolvo_Igr), kolvoIgr);
        a9.apply();

        SharedPreferences.Editor a10 = mSettings.edit();
        a10.putInt(String.valueOf(Key_Kolvo_Pobed), kolvoPobed);
        a10.apply();

        SharedPreferences.Editor a11 = mSettings.edit();
        a11.putInt(String.valueOf(Key_Kolvo_Proigr_Step), kolvoProigrStep);
        a11.apply();

        SharedPreferences.Editor a12 = mSettings.edit();
        a12.putInt(String.valueOf(Key_Kolvo_Proigr_Time), kolvoProigrTime);
        a12.apply();

        SharedPreferences.Editor a13 = mSettings.edit();
        a13.putInt(String.valueOf(Key_Koef_Dostign_Slogn), koefDostignSlogn);
        a13.apply();

        SharedPreferences.Editor a14 = mSettings.edit();
        a14.putInt(String.valueOf(Key_Slognost_game), slognost_game);
        a14.apply();

        SharedPreferences.Editor a15 = mSettings.edit();
        a15.putInt(String.valueOf(Key_Koef_Pobed_Max), koefPobedMax);
        a15.apply();
    }

    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Koef_Pobed))) {
            koefPobed = mSettings.getInt(String.valueOf(Key_Koef_Pobed), 0);
        } else koefPobed = 0;

        if (mSettings.contains(String.valueOf(Key_Time))) {
            bonusTime = mSettings.getInt(String.valueOf(Key_Time), 0);
        } else bonusTime = 0;

        if (mSettings.contains(String.valueOf(Key_Slognost_time))) {
            koef_slogn_time = mSettings.getInt(String.valueOf(Key_Slognost_time), 0);
        } else {
            Intent i = new Intent(this, E_Slognost.class);
            startActivity(i);
        }

        if (mSettings.contains(String.valueOf(Key_Slognost_step))) {
            koef_slogn_step = mSettings.getInt(String.valueOf(Key_Slognost_step), 0);
        } else {
            Intent i = new Intent(this, E_Slognost.class);
            startActivity(i);
        }
        if (mSettings.contains(String.valueOf(Key_Slognost_game))) {
            slognost_game = mSettings.getInt(String.valueOf(Key_Slognost_game), 0);
        } else slognost_game = 1;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Igr))) {
            kolvoIgr = mSettings.getInt(String.valueOf(Key_Kolvo_Igr), 0);
        } else kolvoIgr = 0;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Pobed))) {
            kolvoPobed = mSettings.getInt(String.valueOf(Key_Kolvo_Pobed), 0);
        } else kolvoPobed = 0;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Proigr_Step))) {
            kolvoProigrStep = mSettings.getInt(String.valueOf(Key_Kolvo_Proigr_Step), 0);
        } else kolvoProigrStep = 0;

        if (mSettings.contains(String.valueOf(Key_Kolvo_Proigr_Time))) {
            kolvoProigrTime = mSettings.getInt(String.valueOf(Key_Kolvo_Proigr_Time), 0);
        } else kolvoProigrTime = 0;

        if (pologenRegulVolum != 0) {
            zadanUrovVolume = mSettings.getInt(String.valueOf(Key_Urov_Volum), 0);
        }

    }

    // ЗАПУСК ИГРЫ
    public void clickStart(View view) {

        if (pologenieKnopkiMute == false) {

            zvStartGame.start();
            timeOnFonMusik = 6000;

            vklFonMusic();
        }

        timeScreen.setBase(SystemClock.elapsedRealtime() + timeStopped);
        timeScreen.start();

        igrovoePole.setEnabled(true);  // Блокировка КНОПКИ
        igrovoePole.setAlpha(0.5f);    // ПРОЗРАЧНОСТЬ КНОПКИ
        igrovoePole.animate().alpha(1f).setDuration(1000);


        // Блокировка КНОПКИ
        buStart.setEnabled(false);
        // ПРОЗРАЧНОСТЬ КНОПКИ
        buStart.setAlpha(1f);
        buStart.animate().alpha(0f).setDuration(1500);

        // Блокировка КНОПКИ
        buPause.setEnabled(true);
        // ПРОЗРАЧНОСТЬ КНОПКИ
        buPause.setAlpha(0f);
        buPause.animate().alpha(1f).setDuration(2000);

        sostoyniePause = false;

        btn1.clearAnimation();
        buPause.setTextSize(20);
        buPause.setText("" + "ПАУЗА");
        buPause.setTextColor(Color.rgb(98, 0, 234)); // Цвет текста кнопки
        buPause.setBackgroundColor(Color.parseColor("#00DC00"));   // Цвет поля кнопки

        buStart.setLayoutParams(new LinearLayout.LayoutParams(0, 0));   // Уменьшает размер кнопки СТАРТ

    }

// ПАУЗА - ПРОДОЛЖИТЬ

    public void clickPause(View view) {

        if (sostoyniePause == false) {

            fonMusic.pause();

            timeStopped = timeScreen.getBase() - SystemClock.elapsedRealtime();
            timeScreen.stop();
            sostoyniePause = true;
            buPause.setTextSize(16);
            buPause.setText("" + "ПРОДОЛЖИТЬ");
            btn1.startAnimation(animation1);
            buPause.setTextColor(Color.parseColor("#63FF00")); // Цвет текста кнопки
            buPause.setBackgroundColor(Color.parseColor("#FF00B7"));   // Цвет поля кнопки

            igrovoePole.setEnabled(false); // Блокировка КНОПКИ
            igrovoePole.setAlpha(1f);// ПРОЗРАЧНОСТЬ КНОПКИ
            igrovoePole.animate().alpha(0.2f).setDuration(1500);

            if (sostTimeOver == false) {
                zvPause.start();
            } else {
                zvTimeOver.pause();
            }

        } else {
            sostoyniePause = false;
            timeScreen.setBase(SystemClock.elapsedRealtime() + timeStopped);
            timeScreen.start();

            igrovoePole.setEnabled(true);    // Разблокировка нажатия игрового поля
            igrovoePole.setAlpha(0.2f);      // ПРОЗРАЧНОСТЬ игрового поля
            igrovoePole.animate().alpha(1f).setDuration(1500);

            btn1.clearAnimation();
            buPause.setTextSize(20);
            buPause.setText("" + "ПАУЗА");
            buPause.setTextColor(Color.rgb(98, 0, 234)); // Цвет текста кнопки
            buPause.setBackgroundColor(Color.parseColor("#00DC00"));   // Цвет поля кнопки

            if (sostTimeOver == true) {
                zvTimeOver.start();
            } else {
                zvProdolgitGame.start();
                timeOnFonMusik = 2500;
                vklFonMusic();
            }
        }
    }

    public void clickMenu(View view) {

        if (sostTimeOver == true) {
            zvTimeOver.stop();
        } else {

            fonMusic.stop();

        }
        zvPerexV_Menu.start();


        Intent intent = new Intent(this, B_Menu.class);    // Переход на другой класс
        startActivity(intent);
    }

    public void PovtorGame(View view) {

        igrovoePole.setEnabled(true);     // Разблокировка нажатия игрового поля

        Intent intent = new Intent(this, C_MainActivity.class);    // Переход на другой класс
        startActivity(intent);
    }

    private void ParametrUrovneiGame() {
        if (uroven == 1) {

            igrovoePole.setNumColumns(3);
            int c = 3, r = 4;
            mAdapter = new PoleGame(this, c, r);
            koef_timeGame = (c * r) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (c * r) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 320;
            Shirin_fishek = 320;
        }

        if (uroven == 2) {
            igrovoePole.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 4);
            koef_timeGame = (4 * 4) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (4 * 4) * 2 + uroven * 2 + koef_slogn_step; // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 3) {
            igrovoePole.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 5);
            koef_timeGame = (4 * 5) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (4 * 5) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 260;
            Shirin_fishek = 260;
        }

        if (uroven == 4) {
            igrovoePole.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 6);
            koef_timeGame = (4 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (4 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 250;
            Shirin_fishek = 260;
        }

        if (uroven == 5) {
            igrovoePole.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 7);
            koef_timeGame = (4 * 7) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (4 * 7) * 2 + uroven * 2 + koef_slogn_step; // Задаём максимальное количество ходов
            Visot_fishek = 240;
            Shirin_fishek = 250;
        }

        if (uroven == 6) {
            igrovoePole.setNumColumns(5);
            mAdapter = new PoleGame(this, 5, 6);
            koef_timeGame = (5 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (5 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 220;
            Shirin_fishek = 210;
        }

        if (uroven == 7) {
            igrovoePole.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 8);
            koef_timeGame = (4 * 8) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (4 * 8) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 209;
            Shirin_fishek = 220;
        }
        if (uroven == 8) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 6);
            koef_timeGame = (6 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (6 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }
        if (uroven == 9) {
            igrovoePole.setNumColumns(5);
            mAdapter = new PoleGame(this, 5, 8);
            koef_timeGame = (5 * 8) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (5 * 8) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 209;
            Shirin_fishek = 210;
        }
        if (uroven == 10) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 7);
            koef_timeGame = (7 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (7 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }
        if (uroven == 11) {
            igrovoePole.setNumColumns(4);
            mAdapter = new PoleGame(this, 4, 11);
            koef_timeGame = (4 * 11) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (4 * 11) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 160;
        }
        if (uroven == 12) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 8);
            koef_timeGame = (8 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (8 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 180;
            Shirin_fishek = 170;
        }

        if (uroven == 13) {
            igrovoePole.setNumColumns(5);
            mAdapter = new PoleGame(this, 5, 10);
            koef_timeGame = (5 * 10) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (5 * 10) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 190;
        }

        if (uroven == 14) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 9);
            koef_timeGame = (9 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (9 * 6) * 2 + uroven * 2 + koef_slogn_step; // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 160;
        }

        if (uroven == 15) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 10);
            koef_timeGame = (10 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (10 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 170;
            Shirin_fishek = 170;
        }

        if (uroven == 16) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 11);
            koef_timeGame = (11 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (11 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 150;
            Shirin_fishek = 160;
        }

        if (uroven == 17) {
            igrovoePole.setNumColumns(7);
            mAdapter = new PoleGame(this, 7, 10);
            koef_timeGame = (7 * 10) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (7 * 10) * 2 + uroven * 2 + koef_slogn_step; // Задаём максимальное количество ходов
            Visot_fishek = 160;
            Shirin_fishek = 150;
        }

        if (uroven == 18) {
            igrovoePole.setNumColumns(6);
            mAdapter = new PoleGame(this, 6, 12);
            koef_timeGame = (12 * 6) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (12 * 6) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 139;
            Shirin_fishek = 150;
        }

        if (uroven == 19) {
            igrovoePole.setNumColumns(7);
            mAdapter = new PoleGame(this, 7, 12);
            koef_timeGame = (7 * 12) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (7 * 12) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 139;
            Shirin_fishek = 150;
        }
        if (uroven == 20) {
            igrovoePole.setNumColumns(8);
            mAdapter = new PoleGame(this, 8, 11);
            koef_timeGame = (8 * 11) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (8 * 11) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 145;
            Shirin_fishek = 130;
        }

        if (uroven == 21) {
            igrovoePole.setNumColumns(8);
            mAdapter = new PoleGame(this, 8, 12);
            koef_timeGame = (8 * 12) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (8 * 12) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 139;
            Shirin_fishek = 130;
        }

        if (uroven == 22) {
            igrovoePole.setNumColumns(8);
            mAdapter = new PoleGame(this, 8, 13);
            koef_timeGame = (8 * 13) / 2 + uroven / 2 + koef_slogn_time;
            StepCount = (8 * 13) * 2 + uroven * 2 + koef_slogn_step;  // Задаём максимальное количество ходов
            Visot_fishek = 130;
            Shirin_fishek = 130;
        }
    }

    private void ProzrachButton() {
        // ПРОЗРАЧНОСТЬ КНОПКИ
        igrovoePole.setAlpha(0.3f);
        igrovoePole.animate().alpha(1f).setDuration(2000);
    }

    public void clickMute(View view) {

        if (pologenieKnopkiMute == false) {
            urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();

            fonMusic.pause();

            /*    fonMusicGame.pause();*/

            mute.setImageResource(R.drawable.mute);
            pologenieKnopkiMute = true;
            mute.animate().rotationXBy(180).setDuration(500);
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


            if (sostoyniePause == false) {
                timeOnFonMusik = 1500;
                vklFonMusic();
            }

      /*      fonMusicGame.start();
            fonMusicGame.setLooping(true);  // повтор проигрывания плеера*/

            pologenieKnopkiMute = false;
            mute.animate().rotationYBy(180).setDuration(500);
        }
    }

    private void viborFonKartinki() {

        byte kartinka = (byte) (Math.random() * 18); // Случайное число от 0 до 17 -- Для выбора фона

        switch (kartinka) {
            case 0:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_1));  // Фоновая картинка
                break;
            case 1:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_2));  // Фоновая картинка
                break;
            case 2:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_3));  // Фоновая картинка
                break;
            case 3:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_4));  // Фоновая картинка
                break;
            case 4:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_5));  // Фоновая картинка
                break;
            case 5:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_6));  // Фоновая картинка
                break;
            case 6:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_7));  // Фоновая картинка
                break;
            case 7:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_8));  // Фоновая картинка
                break;
            case 8:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_9));  // Фоновая картинка
                break;
            case 9:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_10));  // Фоновая картинка
                break;
            case 10:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_11));  // Фоновая картинка
                break;
            case 11:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_12));  // Фоновая картинка
                break;
            case 12:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_13));  // Фоновая картинка
                break;
            case 13:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_14));  // Фоновая картинка
                break;
            case 14:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_15));  // Фоновая картинка
                break;
            case 15:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_16));  // Фоновая картинка
                break;
            case 16:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_17));  // Фоновая картинка
                break;
            case 17:
                fonKartink.setBackground(getResources().getDrawable(R.drawable.pole_game_18));  // Фоновая картинка
                break;
        }

        fonKartink.getBackground().setAlpha(170);  //  Затемнение только фоновой картинки (от 0 до 255)
    }

    private void regulirovUrovenVolume() {   //Этот код мгновенно установит заданную громкость без обратной связи с пользователем при нажатии кнопок.

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int a = maxVolume * urovenVolume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, a, 0);
    }
}


// https://developer.alexanderklimov.ru/android/games/memoria.php?ysclid=lq527nk13p378087110
// https://ru-code-android.livejournal.com/2665.html

/* Typeface type = Typeface.createFromAsset(getAssets(),"komi.ttf");*/  // Шрифт

// fonKartink.setAlpha(0.1f);  -  Затемнение всего экрана