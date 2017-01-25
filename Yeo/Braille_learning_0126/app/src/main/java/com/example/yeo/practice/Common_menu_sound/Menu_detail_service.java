package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;

/**
 * Created by 이명진 on 2016-09-02.
 * 메뉴의 상세정보를 출력하는 클래스
 */

public class Menu_detail_service extends Service {
    //private static final String TAG = "Menu_basic_service";
    MediaPlayer explain_tutorial, explain_basic, explain_master, explain_quiz, explain_initial, explain_vowel, explain_consonant, explain_number, explain_alphabet, explain_abbreviation,
            explain_punctuation, explain_letter,explain_word,
            explain_initial_quiz,explain_vowel_quiz, explain_consonant_quiz, explain_number_quiz, explain_alphabet_quiz,explain_punctuation_quiz,explain_abbreviation_quiz,
            explain_letter_quiz, explain_word_quiz;
    int rawid[];
    MediaPlayer detail[];
    public static int menu_page = 1;
    static boolean finish = false;
    public Menu_detail_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){

        explain_tutorial=MediaPlayer.create(this, R.raw.explain_directions); //사용설명서
        explain_basic=MediaPlayer.create(this,R.raw.explain_basic); //기초과정
        explain_master=MediaPlayer.create(this,R.raw.explain_master); //숙련과정
        explain_quiz=MediaPlayer.create(this,R.raw.explain_quiz); //퀴즈
        explain_initial=MediaPlayer.create(this,R.raw.explain_initial);//초성연습
        explain_vowel=MediaPlayer.create(this,R.raw.explain_vowel); //모음연습
        explain_consonant=MediaPlayer.create(this,R.raw.explain_consonant); //종성연습
        explain_number=MediaPlayer.create(this,R.raw.explain_number); //숫자 연습
        explain_alphabet=MediaPlayer.create(this,R.raw.explain_alphabet); //알파벳 연습
        explain_punctuation=MediaPlayer.create(this,R.raw.explain_punctuation); // 문장부호 연습
        explain_abbreviation=MediaPlayer.create(this,R.raw.explain_abbreviation); //약자 및 약어연습
        explain_letter=MediaPlayer.create(this,R.raw.explain_letter); //글자연습
        explain_word=MediaPlayer.create(this,R.raw.explain_word); //단어 연습

        explain_initial_quiz=MediaPlayer.create(this,R.raw.explain_initial_quiz); //초성퀴즈
        explain_vowel_quiz=MediaPlayer.create(this,R.raw.explain_vowel_quiz); //모음 퀴즈
        explain_consonant_quiz=MediaPlayer.create(this,R.raw.explain_consonant_quiz); //종성 퀴즈
        explain_number_quiz=MediaPlayer.create(this,R.raw.explain_number_quiz); // 숫자퀴즈
        explain_alphabet_quiz=MediaPlayer.create(this,R.raw.explain_alphabet_quiz); //알파벳 퀴즈
        explain_punctuation_quiz=MediaPlayer.create(this,R.raw.explain_punctuation_quiz); //문장부호 퀴즈
        explain_abbreviation_quiz=MediaPlayer.create(this,R.raw.explain_abbreviation_quiz); //약자 및 약어 퀴즈
        explain_letter_quiz=MediaPlayer.create(this,R.raw.explain_letter_quiz); //글자 퀴즈
        explain_word_quiz=MediaPlayer.create(this,R.raw.explain_word_quiz); // 단어퀴즈

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        switch(menu_page){
            case 1: //사용설명서
                if(explain_quiz.isPlaying()){
                    explain_quiz.reset();
                    explain_quiz=MediaPlayer.create(this,R.raw.explain_quiz);
                }
                if(explain_basic.isPlaying()){
                    explain_basic.reset();
                    explain_basic=MediaPlayer.create(this,R.raw.explain_basic);
                }

                explain_tutorial.start();
                break;
            case 2: //기초과정
                if(explain_tutorial.isPlaying()){
                    explain_tutorial.reset();
                    explain_tutorial=MediaPlayer.create(this,R.raw.explain_directions);
                }
                if(explain_master.isPlaying()){
                    explain_master.reset();
                    explain_master=MediaPlayer.create(this,R.raw.explain_master);
                }
                explain_basic.start();
                break;
            case 3: //숙련과정
                if(explain_basic.isPlaying()){
                    explain_basic.reset();
                    explain_basic=MediaPlayer.create(this,R.raw.explain_basic);
                }
                if(explain_quiz.isPlaying()){
                    explain_quiz.reset();
                    explain_quiz=MediaPlayer.create(this,R.raw.explain_quiz);
                }
                explain_master.start();
                break;
            case 4: //퀴즈
                if(explain_master.isPlaying()){
                    explain_master.reset();
                    explain_master=MediaPlayer.create(this,R.raw.explain_master);
                }
                if(explain_tutorial.isPlaying()){
                    explain_tutorial.reset();
                    explain_tutorial=MediaPlayer.create(this,R.raw.explain_directions);
                }
                explain_quiz.start();
                break;
            case 5: //초성연습
                if(explain_basic.isPlaying()){
                    explain_basic.reset();
                    explain_basic=MediaPlayer.create(this,R.raw.explain_basic);
                }
                if(explain_abbreviation.isPlaying()){
                    explain_abbreviation.reset();
                    explain_abbreviation=MediaPlayer.create(this,R.raw.explain_abbreviation);
                }
                if(explain_vowel.isPlaying()){
                    explain_vowel.reset();
                    explain_vowel=MediaPlayer.create(this,R.raw.explain_vowel);
                }
                explain_initial.start();
                break;
            case 6: //모음연습
                if(explain_initial.isPlaying()){
                    explain_initial.reset();
                    explain_initial=MediaPlayer.create(this,R.raw.explain_initial);
                }
                if(explain_consonant.isPlaying()){
                    explain_consonant.reset();
                    explain_consonant=MediaPlayer.create(this,R.raw.explain_consonant);
                }
                explain_vowel.start();
                break;
            case 7://종성연습
                if(explain_vowel.isPlaying()){
                    explain_vowel.reset();
                    explain_vowel=MediaPlayer.create(this,R.raw.explain_vowel);
                }
                if(explain_number.isPlaying()){
                    explain_number.reset();
                    explain_number=MediaPlayer.create(this,R.raw.explain_number);
                }
                explain_consonant.start();
                break;
            case 8: //숫자연습
                if(explain_consonant.isPlaying()){
                    explain_consonant.reset();
                    explain_consonant=MediaPlayer.create(this,R.raw.explain_consonant);
                }
                if(explain_alphabet.isPlaying()){
                    explain_alphabet.reset();
                    explain_alphabet=MediaPlayer.create(this,R.raw.explain_alphabet);
                }
                explain_number.start();
                break;
            case 9: //알파벳 연습
                if(explain_number.isPlaying()){
                    explain_number.reset();
                    explain_number=MediaPlayer.create(this,R.raw.explain_number);
                }
                if(explain_punctuation.isPlaying()){
                    explain_punctuation.reset();
                    explain_punctuation=MediaPlayer.create(this,R.raw.explain_punctuation);
                }
                explain_alphabet.start();
                break;
            case 10: //문장부호 연습
                if(explain_alphabet.isPlaying()){
                    explain_alphabet.reset();
                    explain_alphabet=MediaPlayer.create(this,R.raw.explain_alphabet);
                }
                if(explain_abbreviation.isPlaying()){
                    explain_abbreviation.reset();
                    explain_abbreviation=MediaPlayer.create(this,R.raw.explain_abbreviation);
                }
                explain_punctuation.start();
                break;
            case 11: //약자 및 약어 연습
                if(explain_punctuation.isPlaying()){
                    explain_punctuation.reset();
                    explain_punctuation=MediaPlayer.create(this,R.raw.explain_punctuation);
                }
                if(explain_initial.isPlaying()){
                    explain_initial.reset();
                    explain_initial=MediaPlayer.create(this,R.raw.explain_initial);
                }
                explain_abbreviation.start();
                break;
            case 12: //글자 연습
                if(explain_master.isPlaying()){
                    explain_master.reset();
                    explain_master=MediaPlayer.create(this,R.raw.explain_master);
                }
                if(explain_word.isPlaying()){
                    explain_word.reset();
                    explain_word=MediaPlayer.create(this,R.raw.explain_word);
                }
                explain_letter.start();
                break;
            case 13: //단어 연습
                if(explain_letter.isPlaying()){
                    explain_letter.reset();
                    explain_letter=MediaPlayer.create(this,R.raw.explain_letter);
                }
                explain_word.start();
                break;
            case 14: //초성 퀴즈
                if(explain_quiz.isPlaying()){
                    explain_quiz.reset();
                    explain_quiz=MediaPlayer.create(this,R.raw.explain_quiz);
                }
                if(explain_word_quiz.isPlaying()){
                    explain_word_quiz.reset();
                    explain_word_quiz=MediaPlayer.create(this,R.raw.explain_word_quiz);
                }
                if(explain_vowel_quiz.isPlaying()){
                    explain_vowel_quiz.reset();
                    explain_vowel_quiz=MediaPlayer.create(this,R.raw.explain_vowel_quiz);
                }
                explain_initial_quiz.start();
                break;
            case 15: //모음 퀴즈
                if(explain_initial_quiz.isPlaying()){
                    explain_initial_quiz.reset();
                    explain_initial_quiz=MediaPlayer.create(this,R.raw.explain_initial_quiz);
                }
                if(explain_consonant_quiz.isPlaying()){
                    explain_consonant_quiz.reset();
                    explain_consonant_quiz=MediaPlayer.create(this,R.raw.explain_consonant_quiz);
                }
                explain_vowel_quiz.start();
                break;
            case 16: //종성 퀴즈
                if(explain_vowel_quiz.isPlaying()){
                    explain_vowel_quiz.reset();
                    explain_vowel_quiz=MediaPlayer.create(this,R.raw.explain_vowel_quiz);
                }
                if(explain_number_quiz.isPlaying()){
                    explain_number_quiz.reset();
                    explain_number_quiz=MediaPlayer.create(this,R.raw.explain_number_quiz);
                }
                explain_consonant_quiz.start();
                break;
            case 17: //숫자 퀴즈
                if(explain_consonant_quiz.isPlaying()){
                    explain_consonant_quiz.reset();
                    explain_consonant_quiz=MediaPlayer.create(this,R.raw.explain_consonant_quiz);
                }
                if(explain_alphabet_quiz.isPlaying()){
                    explain_alphabet_quiz.reset();
                    explain_alphabet_quiz=MediaPlayer.create(this,R.raw.explain_alphabet_quiz);
                }
                explain_number_quiz.start();
                break;
            case 18: //알파벳 퀴즈
                if(explain_number_quiz.isPlaying()){
                    explain_number_quiz.reset();
                    explain_number_quiz=MediaPlayer.create(this,R.raw.explain_number_quiz);
                }
                if(explain_punctuation_quiz.isPlaying()){
                    explain_punctuation_quiz.reset();
                    explain_punctuation_quiz=MediaPlayer.create(this,R.raw.explain_punctuation_quiz);
                }
                explain_alphabet_quiz.start();
                break;
            case 19: //문장부호 퀴즈
                if(explain_alphabet_quiz.isPlaying()){
                    explain_alphabet_quiz.reset();
                    explain_alphabet_quiz=MediaPlayer.create(this,R.raw.explain_alphabet_quiz);
                }
                if(explain_abbreviation_quiz.isPlaying()){
                    explain_abbreviation_quiz.reset();
                    explain_abbreviation_quiz=MediaPlayer.create(this,R.raw.explain_abbreviation_quiz);
                }
                explain_punctuation_quiz.start();
                break;
            case 21://글자 퀴즈
                if(explain_abbreviation_quiz.isPlaying()){
                    explain_abbreviation_quiz.reset();
                    explain_abbreviation_quiz=MediaPlayer.create(this,R.raw.explain_abbreviation_quiz);
                }
                if(explain_word_quiz.isPlaying()){
                    explain_word_quiz.reset();
                    explain_word_quiz=MediaPlayer.create(this,R.raw.explain_word_quiz);
                }
                explain_letter_quiz.start();
                break;
            case 22: //단어퀴즈
                if(explain_letter_quiz.isPlaying()){
                    explain_letter_quiz.reset();
                    explain_letter_quiz=MediaPlayer.create(this,R.raw.explain_letter_quiz);
                }
                if(explain_initial_quiz.isPlaying()){
                    explain_initial_quiz.reset();
                    explain_initial_quiz=MediaPlayer.create(this,R.raw.explain_initial_quiz);
                }
                explain_word_quiz.start();
                break;
            case 23: //약자 및 약어 퀴즈
                if(explain_punctuation_quiz.isPlaying()){
                    explain_punctuation_quiz.reset();
                    explain_punctuation_quiz=MediaPlayer.create(this,R.raw.explain_punctuation_quiz);
                }
                if(explain_letter_quiz.isPlaying()){
                    explain_letter_quiz.reset();
                    explain_letter_quiz=MediaPlayer.create(this,R.raw.explain_letter_quiz);
                }
                explain_abbreviation_quiz.start();
                break;
        }

        return START_NOT_STICKY;

    }
}
