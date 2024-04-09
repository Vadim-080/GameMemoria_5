package com.example.gamememoria;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class J_PravilaGame extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pravila);

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

        TextView textPravil1 = findViewById(R.id.text_pravil1);
        TextView textPravil2 = findViewById(R.id.text_pravil2);
        TextView textPravil3 = findViewById(R.id.text_pravil3);
        TextView textPravil4 = findViewById(R.id.text_pravil4);

        textPravil1.setText("  Чем лучше память, тем быстрее работает интеллект."+ "\n" +
                "  Специальные упражнения помогут прокачать свою память."+ "\n" +
                "  Эта Игра разработана по специальной методике, позволяющей каждый день тренировать свою память,"+ "\n" +
                "чтобы повысить свои интеллектуальные способности");

        textPravil2.setText("  На игровом поле размещены фишки с закрытыми картинками"+ "\n" +
                "  Каждая картинка имеет только одну свою пару."+ "\n" +
                "  Необходимо поочередно открывать фишки и запоминать какие картинки там находятся"+ "\n" +
                "  Одновременно могут быть открыты только две фишки"+ "\n" +
                "  При открытии двух фишек с одинаковыми картинками они исчезают" + "\n" +
                "  Победа наступает при очистки от фишек всего игрового поля, после чего Вы переходите на следующий уровень" + "\n" +
                "  С каждым уровнем число фишек на поле увеличивается");

        textPravil3.setText("  В любое время можно начать игру с первого уровня нажав в Меню «Новая игра», а также восстановить " +
                "все свои достижения нажав «Восстановить игру»."+ "\n" +
                "  Помните, что количество ходов и время игры ограничены.");

        textPravil4.setText("ПРИЯТНОЙ ИГРЫ!");

        Typeface type1 = getResources().getFont(R.font.far);    // шрифт
        textPravil1.setTypeface(type1);
        textPravil2.setTypeface(type1);
        textPravil3.setTypeface(type1);
        textPravil4.setTypeface(type1);
    }

    public void clickMenu(View view) {

        Intent intent = new Intent(this, B_Menu.class);    // Переход на другой класс
        startActivity(intent);
    }
}
