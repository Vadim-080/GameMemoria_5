package com.example.gamememoria;

import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class J_PravilaGame extends AppCompatActivity {

    Button zabiv, novi, opit, mast, buMenu;
    MediaPlayer zvPerexV_Menu;

    AudioManager urovVolumR;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pravila);

        TextView textPravil1 = findViewById(R.id.text_pravil1);
        TextView textPravil2 = findViewById(R.id.text_pravil2);
        TextView textPravil3 = findViewById(R.id.text_pravil3);

        textPravil1.setText("Чем лучше память, тем быстрее работает интеллект."+ "\n" +
                "Специальные упражнения помогут прокачать свою память."+ "\n" +
                "Эта Игра разработана по специальной методике, позволяющей каждый день тренировать свою память,"+ "\n" +
                "чтобы повысить свои интеллектуальные способности");

        textPravil2.setText("На игровом поле размещены фишки с закрытыми картинками"+ "\n" +
                "Каждая картинка имеет только одну свою пару."+ "\n" +
                "Необходимо поочередно открывать фишки и запоминать какие картинки там находятся"+ "\n" +
                "Одновременно могут быть открыты только две фишки"+ "\n" +
                "При открытии двух фишек с одинаковыми картинками они исчезают" + "\n" +
                "Победа наступает при очистки от фишек всего игрового поля, после чего Вы переходите на следующий уровень" + "\n" +
                "С каждым уровнем число фишек на поле увеличивается");

        textPravil3.setText("Чем лучше память, тем быстрее работает интеллект."+ "\n" +
                "Специальные упражнения помогут прокачать свою память."+ "\n" +
                "Эта Игра разработана по специальной методике, позволяющей каждый день тренировать свою память,"+ "\n" +
                "чтобы повысить свои интеллектуальные способности");


       /* textPravil.setTextColor(R.color.CV8);*/
        Typeface type1 = getResources().getFont(R.font.starin_1);    // шрифт
        textPravil1.setTypeface(type1);

// СКРЫВАЕМ ВЕРХНЮЮ И НИЖНЮЮ СТРОКИ НАВИГАЦИИ
        ConstraintLayout LinearLayout = findViewById(R.id.fon_pravil_view);
        int currentVis = LinearLayout.getSystemUiVisibility();
        int newVis;
        if ((currentVis & View.SYSTEM_UI_FLAG_LOW_PROFILE) == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            newVis = View.SYSTEM_UI_FLAG_VISIBLE;
        } else {
            newVis = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        LinearLayout.setSystemUiVisibility(newVis);
    }
}
