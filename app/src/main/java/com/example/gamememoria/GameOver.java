package com.example.gamememoria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import static com.example.gamememoria.Menu.PrichinGameOver;

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

    }

    public void clickMenu(View view) {

        Intent intent = new Intent(this, Menu.class);    // Переход на другой класс (сдесь класс Vvod)
        startActivity(intent);
    }
}

