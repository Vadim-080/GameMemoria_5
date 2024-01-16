package com.example.gamememoria;

import static com.example.gamememoria.MainActivity.StepCountIspolz;
import static com.example.gamememoria.MainActivity.bonusStep;
import static com.example.gamememoria.MainActivity.bonusTimeViv;
import static com.example.gamememoria.MainActivity.timeIstr;
import static com.example.gamememoria.Menu.uroven;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pobeda extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pobeda);

        result = findViewById(R.id.Result_view);

        result.setText("ВЫ ПОБЕДИЛИ" + "\n \n" + "Открыт уровень: " + uroven + " \n \n" + "Сделано ходов: " + StepCountIspolz + " \n\n " +
                "Истрачено времени: " + timeIstr + " \n\n " + "Бонусные очки: " + bonusStep + " \n\n " + "Бонусное время: " + (bonusTimeViv / 1000) + " сек");

    }

    public void clickMenu(View v) {
        Intent intent = new Intent(this, Menu.class);   // Переход на другой класс
        startActivity(intent);
    }

    public void clickProdolgitGame(View v) {
        Intent intent = new Intent(this, MainActivity.class);   // Переход на другой класс
        startActivity(intent);
    }
}

