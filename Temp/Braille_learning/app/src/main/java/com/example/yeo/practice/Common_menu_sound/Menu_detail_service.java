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
            explain_abbreviation_quiz, explain_letter_quiz, explain_word_quiz, explain_mynote, explain_basic_note,explain_master_note,explain_comunication,  explain_student, explain_teacher, explain_communication_detail;

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
                explain_abbreviation_quiz, explain_letter_quiz, explain_word_quiz, explain_mynote, explain_basic_note,explain_master_note,explain_comunication, explain_student, explain_teacher, explain_communication_detail};

        rawid = new int[]{R.raw.tutorial_detail,R.raw.basic_detail,R.raw.master_detail,R.raw.quiz_detail,R.raw.initial_detail,R.raw.vowel_detail,R.raw.final_detail,
                R.raw.number_detail,R.raw.alphabet_detail,R.raw.sentence_detail,R.raw.abbreviation_detail,R.raw.letter_detail,R.raw.word_detail,R.raw.quiz_initial_detail,
                R.raw.quiz_vowel_detail,R.raw.quiz_final_detail,R.raw.quiz_number_detail,R.raw.quiz_alphabet_detail,R.raw.quiz_sentence_detail,R.raw.quiz_abbreviation_detail,
                R.raw.quiz_letter_detail,R.raw.quiz_word_detail,R.raw.mynote_detail, R.raw.basic_detail, R.raw.master_detail, R.raw.comunication_detail,
                R.raw.communication_student_detail, R.raw.communication_teacher_detail, R.raw.mynote_communication_detail};

        for(int i=0; i<detail.length ; i++){
            detail[i] = MediaPlayer.create(this, rawid[i]);
            detail[i].setLooping(false);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=2;

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
