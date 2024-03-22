package com.example.gamememoria;

import android.animation.ValueAnimator;

import static com.example.gamememoria.B_Menu.fonMusic;
import static com.example.gamememoria.B_Menu.timeOnFonMusik;
import static com.example.gamememoria.C_MainActivity.musicVsplivOknoProdolGame;

public class RegulirovkiPRG {

    static void vklFonMusic() {  //Этот код делает анимацию плавного включения фоновой музыки

        ValueAnimator volumeAnimator = ValueAnimator.ofFloat(0f, 0.6f);
        volumeAnimator.setDuration(timeOnFonMusik); // Длительность анимации в миллисекундах
        volumeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float volume = (float) animation.getAnimatedValue();
                fonMusic.setVolume(volume, volume);
            }
        });
        volumeAnimator.start();

        fonMusic.start();
        fonMusic.setLooping(true);  // повтор проигрывания плеера
    }

    static void vklFonMusicProdolgenGame() {  //Этот код делает анимацию плавного включения фоновой музыки

        ValueAnimator volumeAnimator = ValueAnimator.ofFloat(0f, 0.6f);
        volumeAnimator.setDuration(timeOnFonMusik); // Длительность анимации в миллисекундах
        volumeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float volume = (float) animation.getAnimatedValue();
                musicVsplivOknoProdolGame.setVolume(volume, volume);
            }
        });
        volumeAnimator.start();

        musicVsplivOknoProdolGame.start();
        musicVsplivOknoProdolGame.setLooping(true);  // повтор проигрывания плеера
    }





    /*public  static void regulirovUrovenVolume() {    //Этот код мгновенно установит громкость на уровень заданной переменной urovenVolume

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // определение кол-во ступеней регулир громкости устройства
        int a = maxVolume * urovenVolume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, a, 0);
    }*/

}
