package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;

// 메뉴에 대한 음성을 출력하는 서비스

public class Menu_service extends Service {
    private static final String TAG = "Menu_service";
    MediaPlayer Init,Final,Vowel,tutorial,number,alphabet, Sentence,abbreviation,basicfinish;
    public static int menu_page = 1;
    static boolean finish = false;
    public Menu_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        tutorial = MediaPlayer.create(this, R.raw.directions); //사용설명서
        Init = MediaPlayer.create(this, R.raw.initial); //초성연습
        Vowel = MediaPlayer.create(this, R.raw.vowel); //모음연습
        Final =MediaPlayer.create(this, R.raw.finalconsonant); //종성연습
        number = MediaPlayer.create(this, R.raw.number); //숫자연습
        alphabet = MediaPlayer.create(this, R.raw.alphabet); //알파벳 연습
        Sentence = MediaPlayer.create(this, R.raw.start_sentence); //문장부호
        abbreviation = MediaPlayer.create(this, R.raw.abbreviation_start); // 약자 및 약어 연습
        basicfinish = MediaPlayer.create(this, R.raw.basicfinish); //기초과정 종료

        //반복 제거
        Init.setLooping(false);
        Final.setLooping(false);
        Vowel.setLooping(false);
        basicfinish.setLooping(false);
        tutorial.setLooping(false);
        number.setLooping(false);
        alphabet.setLooping(false);
        abbreviation.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if(finish == false) {
            switch (menu_page) {
                case 1: //초성 연습
                    if (abbreviation.isPlaying()) {
                        abbreviation.reset();
                        abbreviation = MediaPlayer.create(this, R.raw.abbreviation_start);
                    }
                    if (Vowel.isPlaying()) {
                        Vowel.reset();
                        Vowel = MediaPlayer.create(this, R.raw.vowel);
                    }
                    Init.start();
                    break;
                case 2: //모음 연습
                    if (Init.isPlaying()) {
                        Init.reset();
                        Init = MediaPlayer.create(this, R.raw.initial);
                    }
                    if (Final.isPlaying()) {
                        Final.reset();
                        Final = MediaPlayer.create(this, R.raw.finalconsonant);
                    }
                    Vowel.start();
                    break;
                case 3: //종성 연습
                    if (Vowel.isPlaying()) {
                        Vowel.reset();
                        Vowel = MediaPlayer.create(this, R.raw.vowel);
                    }
                    if (number.isPlaying()) {
                        number.reset();
                        number = MediaPlayer.create(this, R.raw.number);
                    }
                    Final.start();
                    break;
                case 4: //숫자 연습
                    if (Final.isPlaying()) {
                        Final.reset();
                        Final = MediaPlayer.create(this, R.raw.finalconsonant);
                    }
                    if (alphabet.isPlaying()) {
                        alphabet.reset();
                        alphabet = MediaPlayer.create(this, R.raw.alphabet);
                    }
                    number.start();
                    break;
                case 5: //알파벳 연습
                    if (number.isPlaying()) {
                        number.reset();
                        number = MediaPlayer.create(this, R.raw.number);
                    }
                    if (Sentence.isPlaying()) {
                        Sentence.reset();
                        Sentence = MediaPlayer.create(this, R.raw.start_sentence);
                    }
                    alphabet.start();
                    break;
                case 6: //문장부호 연습
                    if (alphabet.isPlaying()) {
                        alphabet.reset();
                        alphabet = MediaPlayer.create(this, R.raw.alphabet);
                    }
                    if (abbreviation.isPlaying()) {
                        abbreviation.reset();
                        abbreviation = MediaPlayer.create(this, R.raw.abbreviation_start);
                    }
                    Sentence.start();
                    break;
                case 7: //약자 및 약어 연습
                    if (Sentence.isPlaying()) {
                        Sentence.reset();
                        Sentence = MediaPlayer.create(this, R.raw.start_sentence);
                    }
                    if (Init.isPlaying()) {
                        Init.reset();
                        Init = MediaPlayer.create(this, R.raw.initial);
                    }
                    abbreviation.start();
                    break;

            }
        }
        else{ //기초과정 종료
            basicfinish.start();
            finish=false;
            basicfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    basicfinish.reset();
                    basicfinish = MediaPlayer.create(Menu_service.this,R.raw.basicfinish);
                }
            });
        }
        return START_NOT_STICKY;
    }
}
