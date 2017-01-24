package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_letter;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

/**
 * Created by 이명진 on 2016-09-02.
 * 메뉴의 상세정보를 출력하는 클래스
 */

public class Menu_detail_service extends Service {
    //private static final String TAG = "Menu_basic_service";
    MediaPlayer explain_tutorial, explain_basic, explain_master, explain_quiz, explain_initial, explain_vowel, explain_consonant, explain_number, explain_alphabet, explain_abbreviation,
            explain_punctuation, explain_letter,explain_word, explain_initial_quiz,explain_vowel_quiz, explain_consonant_quiz, explain_number_quiz, explain_alphabet_quiz,explain_punctuation_quiz,
            explain_abbreviation_quiz, explain_letter_quiz, explain_word_quiz;

    int detail_size = 22;
    int rawid[];
    MediaPlayer detail[];
    public static int menu_page = 1;
    static boolean finish = false;
    int previous=0;


    public Menu_detail_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void init(){
        if(detail[previous].isPlaying()){
            detail[previous].reset();
            detail[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }

    @Override
    public void onCreate(){
        detail = new MediaPlayer[]{explain_tutorial, explain_basic, explain_master, explain_quiz, explain_initial, explain_vowel, explain_consonant, explain_number, explain_alphabet, explain_punctuation,
                explain_abbreviation, explain_letter,explain_word, explain_initial_quiz,explain_vowel_quiz, explain_consonant_quiz, explain_number_quiz, explain_alphabet_quiz,explain_punctuation_quiz,
                explain_abbreviation_quiz, explain_letter_quiz, explain_word_quiz};

        rawid = new int[]{R.raw.explain_directions,R.raw.explain_basic,R.raw.explain_master,R.raw.explain_quiz,R.raw.explain_initial,R.raw.explain_vowel,R.raw.explain_consonant,
                R.raw.explain_number,R.raw.explain_alphabet,R.raw.explain_punctuation,R.raw.explain_abbreviation,R.raw.explain_letter,R.raw.explain_word,R.raw.explain_initial_quiz,
                R.raw.explain_vowel_quiz,R.raw.explain_consonant_quiz,R.raw.explain_number_quiz,R.raw.explain_alphabet_quiz,R.raw.explain_punctuation_quiz,R.raw.explain_abbreviation_quiz,
                R.raw.explain_letter_quiz,R.raw.explain_word_quiz};

        for(int i=0; i<detail_size ; i++){
            detail[i] = MediaPlayer.create(this, rawid[i]);
            detail[i].setLooping(false);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=1;

        if(Sound_Manager.stop==true)
            init();
        else{
            init();
            previous=menu_page-1;
            detail[previous].start();
        }

        detail[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                detail[previous].reset();
                detail[previous] = MediaPlayer.create(Menu_detail_service.this, rawid[previous]);
            }
        });
        return START_NOT_STICKY;

    }
}
