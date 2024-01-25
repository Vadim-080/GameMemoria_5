package com.example.gamememoria;

import static com.example.gamememoria.Zastavka.povtorTriGameOverPodrad;
import static com.example.gamememoria.Zastavka.promegutGameOverPodrad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Menu extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PAPKA_MEMORI_GAME_MEMOR";  // константа для имени файла настроек
    public static SharedPreferences mSettings;  // Создаём переменную, представляющую экземпляр класса SharedPreferences, который отвечает за работу с настройками
    public static int Key_Uroven = 1; //  ключ текущий уровень
    public static int Key_Score = 2; //  ключ текущие очки
    public static int Key_Time = 3; //  ключ бонусное время
    public static int Key_Slognost_time = 4; //  ключ Сложность время
    public static int Key_Slognost_step = 5; //  ключ Сложность ходы
    public static int Key_Slognost_game = 6; //  ключ Сложность Игры
    public static int Key_Koef_Pobed = 7; //  ключ Сложность Игры
    public static int Key_Kolvo_Igr = 8; //  ключ количества сыгранных игр всего Игры
    public static int Key_Kolvo_Pobed = 9; //  ключ количества побед всего
    public static int Key_Kolvo_Proigr = 10; //  ключ количества поражений всего
    public static int Key_Uroven_Max = 11; //  ключ максимального уровня
    public static int Key_Score_Max = 12; //  ключ набранных на максимальн уровне очков
    public static int Key_Time_Max = 13; //  ключ набранного на максимальн уровне времени
    public static int Key_Slognost_Max = 14; //  ключ набранного на максимальн уровне времени
    public static int Key_Koef_Dostign_Slogn = 15; //  ключ коэфиц достигнутой сложности = уровень*сложность

    public static int uroven;  // Задаём уровень в игре

    public static int koefPobed = 0;  // Сколько раз победил всю игру

    public static int kolvoIgr;  // Сколько сыграно всего игр

    public static int kolvoPobed ;  // Сколько всего побед

    public static int kolvoProigr ;  // Сколько всего поражений

    public static int slognost_game;  // Задаём сложность игры

    public static int koefDostignSlogn; // коэфиц достигнутой сложности = уровень*сложность
    public static int score;  // Задаём количество очков в игре
    public static int bonusTime;  // Задаём количество бонусного времени
    public static int urovenMax;  // Для хранения максимального достигнутого в игре уровня
    public static int scoreMax;  // Для хранения набранных на максимальн уровне очков
    public static int bonusTimeMax;  // Для хранения набранного на максимальн уровне времени
    public static int slognostMax;  // Для хранения набранного на максимальн уровне времени
    public static String PrichinGameOver;
    public static int Visot_fishek; // высота фишек
    public static int Shirin_fishek; // высота фишек
    public static int kolvoGameOverPodrad; // количество проигрышей подряд
    public static int koef_slogn_time, koef_slogn_step; // коэф времени и хожов для уровня игры
    private ImageView iconSlogn;
    private ImageView time;

    Button start, start1, exit, newGame, slogn, vosstsnovlMaxGame, dostigen;
    Chronometer timeBonus;
    TextView namberUroven, score_viev, nadpUrovenGame;
    MediaPlayer mediaPlayer1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Задаем цвет верхей строки и строки навигации
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black)); //status bar or the time bar at the top (see example image1)
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series  (see example image2)
        }

// Задаём звуковые сигналы
        mediaPlayer1 = MediaPlayer.create(this, R.raw.elektron1);

        iconSlogn = findViewById(R.id.slogn_viev);
        nadpUrovenGame = findViewById(R.id.nadpUrovenGame_view);
        time = findViewById(R.id.time_viev);
        timeBonus = findViewById(R.id.timeBonus_view);
        score_viev = findViewById(R.id.score_viev);
        start = findViewById(R.id.buStart);
        start1 = findViewById(R.id.buStart1);
        slogn = findViewById(R.id.buSlognost);
        dostigen = findViewById(R.id.buDostigen);
        exit = findViewById(R.id.buExit);
        newGame = findViewById(R.id.buNewGame);
        vosstsnovlMaxGame = findViewById(R.id.buVosstsnovlMaxGame);
        namberUroven = findViewById(R.id.NamberUroven_view);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

// Мигание кнопки

        final Animation animation1 = new AlphaAnimation(1, 0.3f); // Изменение с полностью видимого на невидимый
        animation1.setDuration(1000); // продолжительность
        animation1.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation1.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation1.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        final Button btn1 = (Button) findViewById(R.id.buDostigen);
        btn1.startAnimation(animation1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });

        final Animation animation2 = new AlphaAnimation(1, 0.3f); // Изменение с полностью видимого на невидимый
        animation2.setDuration(1500); // продолжительность
        animation2.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation2.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation2.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        final Button btn2 = (Button) findViewById(R.id.buNewGame);
        btn2.startAnimation(animation2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });

        final Animation animation3 = new AlphaAnimation(1, 0.3f); // Изменение с полностью видимого на невидимый
        animation3.setDuration(2000); // продолжительность
        animation3.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation3.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation3.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        final Button btn3 = (Button) findViewById(R.id.buVosstsnovlMaxGame);
        btn3.startAnimation(animation3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });

        final Animation animation4 = new AlphaAnimation(1, 0.3f); // Изменение с полностью видимого на невидимый
        animation4.setDuration(2500); // продолжительность
        animation4.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation4.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation4.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        final Button btn4 = (Button) findViewById(R.id.buSlognost);
        btn4.startAnimation(animation4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });

        final Animation animation5 = new AlphaAnimation(1, 0.3f); // Изменение с полностью видимого на невидимый
        animation5.setDuration(3000); // продолжительность
        animation5.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation5.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation5.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        final Button btn5 = (Button) findViewById(R.id.buSetting);
        btn5.startAnimation(animation5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });

        final Animation animation6 = new AlphaAnimation(1, 0.3f); // Изменение с полностью видимого на невидимый
        animation6.setDuration(3500); // продолжительность
        animation6.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation6.setRepeatCount(Animation.INFINITE); // Повторяйте анимацию бесконечно
        animation6.setRepeatMode(Animation.REVERSE); // Обратная анимация в конце, чтобы кнопка снова исчезла в
        final Button btn6 = (Button) findViewById(R.id.buPravila);
        btn6.startAnimation(animation6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });

        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.anim_time2);
        time.startAnimation(a2);

        View a3 = findViewById(R.id.monet_viev);                   // ПОВОРОТ КНОПКИ
        a3.animate().rotationYBy(99720).setDuration(300000);

        Animation a4 = AnimationUtils.loadAnimation(this, R.anim.anim_bu_start);
        start.startAnimation(a4);

        Animation a5 = AnimationUtils.loadAnimation(this, R.anim.anim_uroven);
        namberUroven.startAnimation(a5);


        Typeface type1 = getResources().getFont(R.font.komi);    // шрифт
        nadpUrovenGame.setTypeface(type1);
        Typeface type2 = getResources().getFont(R.font.vanowitsch);    // шрифт
        namberUroven.setTypeface(type2);
        Typeface type3 = getResources().getFont(R.font.pacifico_regular);    // шрифт
        start.setTypeface(type3);

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

        score_viev.setText("" + score);
        timeBonus.setBase(SystemClock.elapsedRealtime() - bonusTime * 1000);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        vosstsnovlMaxGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vosstsnovlMaxGame();
            }
        });

        slogn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slognost();
            }
        });

        dostigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dostigen();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void startGame() {
        mediaPlayer1.start();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void Dostigen() {
        Intent i = new Intent(this, Dostigenia.class);
        startActivity(i);
    }

    private void slognost() {
        Intent i = new Intent(this, Slognost.class);
        startActivity(i);
    }

    private void newGame() {
        promegutGameOverPodrad = 0;
        povtorTriGameOverPodrad = false;
        uroven = 1;
        score = 0;
        bonusTime = 0;
        onPause();
        Intent i = new Intent(this, Slognost.class);
        startActivity(i);
    }

    private void vosstsnovlMaxGame() {
        promegutGameOverPodrad = 0;
        povtorTriGameOverPodrad = false;
        onResume();

        uroven = urovenMax;
        score = scoreMax;
        bonusTime = bonusTimeMax;
        slognost_game = slognostMax;

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void finish() {
        this.finishAffinity();            // СВЕРТЫВАЕТ ПРИЛОЖЕНИЕ
    }

    @Override
    public void onResume() {    // Получаем число из настроек
        super.onResume();

        if (mSettings.contains(String.valueOf(Key_Uroven))) {
            uroven = mSettings.getInt(String.valueOf(Key_Uroven), 0);
        } else uroven = 1;

        if (mSettings.contains(String.valueOf(Key_Uroven_Max))) {
            urovenMax = mSettings.getInt(String.valueOf(Key_Uroven_Max), 0);
        } else urovenMax = 1;

        if (mSettings.contains(String.valueOf(Key_Score))) {
            score = mSettings.getInt(String.valueOf(Key_Score), 0);
        } else score = 0;

        if (mSettings.contains(String.valueOf(Key_Time))) {
            bonusTime = mSettings.getInt(String.valueOf(Key_Time), 0);
        } else bonusTime = 0;

        if (mSettings.contains(String.valueOf(Key_Score_Max))) {
            scoreMax = mSettings.getInt(String.valueOf(Key_Score_Max), 0);
        } else scoreMax = 0;

        if (mSettings.contains(String.valueOf(Key_Time_Max))) {
            bonusTimeMax = mSettings.getInt(String.valueOf(Key_Time_Max), 0);
        } else bonusTimeMax = 0;

        if (mSettings.contains(String.valueOf(Key_Slognost_game))) {
            slognost_game = mSettings.getInt(String.valueOf(Key_Slognost_game), 0);
        } else slognost_game = 1;

        if (mSettings.contains(String.valueOf(Key_Slognost_Max))) {
            slognostMax = mSettings.getInt(String.valueOf(Key_Slognost_Max), 0);
        } else slognostMax = 1;
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
    }
}
