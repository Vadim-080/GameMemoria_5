package com.example.gamememoria;

import static com.example.gamememoria.A_Zastavka.pologenieKnopkiMute;
import static com.example.gamememoria.A_Zastavka.povtorTriGameOverPodrad;
import static com.example.gamememoria.A_Zastavka.promegutGameOverPodrad;
import static com.example.gamememoria.RegulirovkiPRG.vklFonMusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.my.target.ads.MyTargetView;
import com.my.target.common.models.IAdLoadingError;

public class B_Menu extends AppCompatActivity {

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
    public static int Key_Kolvo_Proigr_Step = 10; //  ключ количества поражений всего из за ходов
    public static int Key_Uroven_Max = 11; //  ключ максимального уровня
    public static int Key_Score_Max = 12; //  ключ набранных на максимальн уровне очков
    public static int Key_Time_Max = 13; //  ключ набранного на максимальн уровне времени
    public static int Key_Slognost_Max = 14; //  ключ набранного на максимальн уровне времени
    public static int Key_Koef_Dostign_Slogn = 15; //  ключ коэфиц достигнутой сложности = уровень*сложность
    public static int Key_Kolvo_Proigr_Time = 16; //  ключ количества поражений всего из за времени
    public static int Key_Koef_Pobed_Max = 17; //  ключ Сложность Игры Максимальн
    public static int Key_Urov_Volum = 18; //  ключ Уровня громкости
    public static int Key_pologen_regul_Volum = 19; //  ключ положения Уровня громкости
    public static int Key_Urov_Bridgs = 20; //  ключ Уровня Яркости
    public static int Key_pologen_regul_Bridgs = 21; //  ключ положения Уровня Яркости
    public static int pologenRegulVolum;  // Задаём положение Уровня громкости
    public static int zadanUrovVolume;  // Задаём уровень громкости по всей игре
    public static int pologenRegulBridgs;  // Задаём положение Уровня Яркости
    public static int zadanUrovBridgs;  // Задаём уровень Яркости по всей игре
    public static int uroven;  // Задаём уровень в игре
    public static int koefPobed = 0;  // Сколько раз победил всю игру
    public static int koefPobedMax = 0;  // Сколько раз победил всю игру Максимальн
    public static int kolvoIgr;  // Сколько сыграно всего игр
    public static int kolvoPobed;  // Сколько всего побед
    public static int kolvoProigrStep;  // Сколько всего поражений из за ходов
    public static int kolvoProigrTime;  // Сколько всего поражений из за времени
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
    private ImageView iconSlogn, timeZnashok;
    public static int urovenVolume;
    public static int timeOnFonMusik;
    public static boolean pologSostoyanSvernutogoPrilogen = false;

    Button start, start1, exit, newGame, slogn, setting, vosstsnovlMaxGame, dostigen;

    ImageButton mute;
    Chronometer timeBonus;
    TextView namberUroven, scoreBonus, nadpUrovenGame;
    ConstraintLayout KartinraZadnegoPlana;

// Перемен VK рекламы
    private MyTargetView adView; // Рекламный  экземпляр класса
    RelativeLayout layout;
    RelativeLayout.LayoutParams adViewLayoutParams;

    public static MediaPlayer fonMusic; // Фоновая музыка
    MediaPlayer zvStart, zvMute, zvExitGame;
    MediaPlayer mediaMenu1, mediaMenu2, mediaMenu4, mediaMenu6; // Звук кнопок меню

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        onResume(); // Вывод данных из памяти

 // Задаём уровень яркости

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = zadanUrovBridgs;
        getWindow().setAttributes(layoutParams);


// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ

        ConstraintLayout LinearLayout = findViewById(R.id.fon_menu_view);
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

        iconSlogn = findViewById(R.id.slogn_viev);
        nadpUrovenGame = findViewById(R.id.nadpUrovenGame_view);
        timeZnashok = findViewById(R.id.time_viev);
        timeBonus = findViewById(R.id.timeBonus_view);
        scoreBonus = findViewById(R.id.score_viev);
        start = findViewById(R.id.buStart);
        start1 = findViewById(R.id.buStart1);
        dostigen = findViewById(R.id.buDostigen);
        newGame = findViewById(R.id.buNewGame);
        vosstsnovlMaxGame = findViewById(R.id.buVosstsnovlMaxGame);
        slogn = findViewById(R.id.buSlognost);
        setting = findViewById(R.id.buSetting);
        exit = findViewById(R.id.buExit);
        mute = findViewById(R.id.buMute);
        vosstsnovlMaxGame = findViewById(R.id.buVosstsnovlMaxGame);
        namberUroven = findViewById(R.id.NamberUroven_view);
        KartinraZadnegoPlana = findViewById(R.id.fon_menu_view);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // Внутри метода onCreate() вы инициализируете переменную  mSettings

// Задаём звуковые сигналы
        zvStart = MediaPlayer.create(this, R.raw.start1);
        zvMute = MediaPlayer.create(this, R.raw.mute_1);
        zvExitGame = MediaPlayer.create(this, R.raw.exit_game);
        fonMusic = MediaPlayer.create(this, R.raw.legki_jazz);
        mediaMenu1 = MediaPlayer.create(this, R.raw.dostigen_1);
        mediaMenu2 = MediaPlayer.create(this, R.raw.new_game_1);
        mediaMenu4 = MediaPlayer.create(this, R.raw.slognost_1);
        mediaMenu6 = MediaPlayer.create(this, R.raw.zv_menu_6);

        mute.setAlpha(0.7f);    // ПРОЗРАЧНОСТЬ КНОПКИ Mute

        byte kartinka = (byte) (Math.random() * 6); // Случайное число от 0 до 5 -- Для выбора фоновое поле
        switch (kartinka) {
            case 0:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_menu2));
                break;
            case 1:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_menu3));
                break;
            case 2:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_menu4));
                break;
            case 3:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_menu5));
                break;
            case 4:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_menu6));
                break;
            case 5:
                KartinraZadnegoPlana.setBackground(getResources().getDrawable(R.drawable.pole_menu7));
                break;
        }

        animButtonMenu();  // Мигание кнопок Меню
        animZnakov();  // Анимация знаков (монет, часы...)
        shrift();  // Шрифты

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
        scoreBonus.setText("" + score);
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

        dostigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dostigen();
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

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        fonMusic.pause();
    }

    // ВОЗОБНОВЛЯЕТ Музыку при возобновления работы после свертывании приложения

    public void onStart() {
        super.onStart();

        if (pologenieKnopkiMute == true) {
            urovenVolume = 0; // Установка уровня громкости музыки (от 1 до 100) в %
            regulirovUrovenVolume();
            mute.setImageResource(R.drawable.mute);
        } else {
            mute.setImageResource(R.drawable.zvuk);

            if (pologenRegulVolum != 0) {
                urovenVolume = zadanUrovVolume;
            } else {
                urovenVolume = 30; // Установка уровня громкости музыки (от 1 до 100) в %
            }

            regulirovUrovenVolume();
            timeOnFonMusik = 6000;
            vklFonMusic();
        }
    }

    private void startGame() {
        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();
            zvStart.start();
        }

        Intent i = new Intent(this, C_MainActivity.class);
        startActivity(i);
    }

    private void Dostigen() {
        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();
            mediaMenu1.start();
        }

        Intent i = new Intent(this, D_Dostigenia.class);
        startActivity(i);
    }

    private void newGame() {
        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();
            mediaMenu2.start();
        }

        promegutGameOverPodrad = 0;
        povtorTriGameOverPodrad = false;
        uroven = 1;
        score = 0;
        bonusTime = 0;
        koefPobed = 0;
        onPause();

        Intent i = new Intent(this, E_Slognost.class);
        startActivity(i);
    }

    private void vosstsnovlMaxGame() {
        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();
            zvStart.start();
        }

        promegutGameOverPodrad = 0;
        povtorTriGameOverPodrad = false;
        onResume();

        uroven = urovenMax;
        score = scoreMax;
        bonusTime = bonusTimeMax;
        slognost_game = slognostMax;
        koefPobed = koefPobedMax;

        Intent i = new Intent(this, C_MainActivity.class);
        startActivity(i);
    }

    private void slognost() {
        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();
         /*   urovenVolume = 50; // Установка уровня громкости звука кнопки (от 1 до 100) в %
            regulirovUrovenVolume();*/
            mediaMenu4.start();
        }

        Intent i = new Intent(this, E_Slognost.class);
        startActivity(i);
    }

    private void setting() {

        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();
            mediaMenu6.start();
        }

        Intent i = new Intent(this, F_Setting.class);
        startActivity(i);
    }

    // СВЕРТЫВАЕТ ПРИЛОЖЕНИЕ
    public void finish() {
        if (pologenieKnopkiMute == true) {
        } else {
            fonMusic.stop();

            if (pologenRegulVolum != 0) {
                urovenVolume = zadanUrovVolume;
            } else {
                urovenVolume = 40; // Установка уровня громкости музыки (от 1 до 100) в %
            }

            /*   urovenVolume = 40; // Установка уровня громкости звука кнопки (от 1 до 100) в %*/
            regulirovUrovenVolume();
        }

        if (pologenRegulVolum != 0) {
            urovenVolume = zadanUrovVolume;
        } else {
            urovenVolume = 60; // Установка уровня громкости музыки (от 1 до 100) в %
        }
        /*  urovenVolume = 60; // Установка уровня громкости звука кнопки (от 1 до 100) в %*/


        regulirovUrovenVolume();
        zvExitGame.start();
        fonMusic.stop();
        this.finishAffinity();
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
            RegulirovkiPRG.vklFonMusic();
            pologenieKnopkiMute = false;

            mute.animate().rotationXBy(180).setDuration(500);
        }
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
        a4.putInt(String.valueOf(Key_Koef_Pobed), koefPobed);
        a4.apply();
    }

    private void animButtonMenu() {// Мигание кнопок Меню

        final Animation animation1 = new AlphaAnimation(1, 0.2f); // Изменение с полностью видимого на невидимый
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
    }

    // Анимация знаков (монет, часы...)
    private void animZnakov() {
        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.anim_time2);
        timeZnashok.startAnimation(a2);

        View a3 = findViewById(R.id.monet_viev);                   // ПОВОРОТ КНОПКИ
        a3.animate().rotationYBy(99720).setDuration(300000);

        Animation a4 = AnimationUtils.loadAnimation(this, R.anim.anim_bu_game_1);
        start.startAnimation(a4);

        Animation a5 = AnimationUtils.loadAnimation(this, R.anim.anim_uroven);
        namberUroven.startAnimation(a5);
    }

    // Шрифты
    private void shrift() {
        Typeface type1 = getResources().getFont(R.font.komi);    // шрифт
        nadpUrovenGame.setTypeface(type1);
        Typeface type2 = getResources().getFont(R.font.vanowitsch);    // шрифт
        namberUroven.setTypeface(type2);
        Typeface type3 = getResources().getFont(R.font.pacifico_regular);    // шрифт
        start.setTypeface(type3);
    }

    private void regulirovUrovenVolume() {    //Этот код мгновенно установит громкость на уровень заданной переменной urovenVolume

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int a = maxVolume * urovenVolume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, a, 0);
    }

}

// ЗВУКИ из - https://developer.alexanderklimov.ru/android/theory/soundpool.php