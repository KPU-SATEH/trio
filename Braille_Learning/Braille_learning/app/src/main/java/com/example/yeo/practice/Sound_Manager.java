package com.example.yeo.practice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.yeo.practice.Common_basic_practice_sound.Final_service;
import com.example.yeo.practice.Common_basic_practice_sound.Initial_service;
import com.example.yeo.practice.Common_basic_practice_sound.Num_service;
import com.example.yeo.practice.Common_basic_practice_sound.Sentence_service;
import com.example.yeo.practice.Common_basic_practice_sound.Vowel_service;
import com.example.yeo.practice.Common_basic_practice_sound.abbreviation_service;
import com.example.yeo.practice.Common_basic_practice_sound.alphabet_service;
import com.example.yeo.practice.Common_master_practice_sound.Letter_service;
import com.example.yeo.practice.Common_master_practice_sound.Word_service;
import com.example.yeo.practice.Common_menu_sound.Menu_basic_service;
import com.example.yeo.practice.Common_menu_sound.Menu_comunication_service;
import com.example.yeo.practice.Common_menu_sound.Menu_detail_service;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_menu_sound.Menu_master_service;
import com.example.yeo.practice.Common_menu_sound.Menu_mynote_service;
import com.example.yeo.practice.Common_menu_sound.Menu_quiz_inside_service;
import com.example.yeo.practice.Common_menu_sound.Menu_quiz_service;
import com.example.yeo.practice.Common_menu_sound.Version_check_service;
import com.example.yeo.practice.Common_mynote_database.Mynote_service;
import com.example.yeo.practice.Common_quiz_sound.quiz_reading_service;
import com.example.yeo.practice.Common_quiz_sound.quiz_writing_service;
import com.example.yeo.practice.Common_trans_sound.Braille_trans_service;
import com.example.yeo.practice.Coomon_communication_sound.Communication_service;

public class Sound_Manager extends Service {
      /*

    Braille_trans_service = 0; //대메뉴 음성
    Version_check_service=1; //버전 체크 음성
    Menu_detail_service=2; // 상세내용 출력
    Menu_basic_service = 10; //기초과정 음성
    Menu_master_service =  20; //숙련과정 음성
    Menu_quiz_service = 30; //퀴즈 음성
    Menu_mynote_service = 40; //나만의 단어장 음성
    Mynote_service = 41; //나만의 단어장 내부 음성
    Menu_Braille_translation = 50; //점자 번역 음성
    Menu_comunication = 60; //선생님과의 대화 내부 음성

    Common_Tutorial_service = 11; //초성연습 음성
    Vowel_service = 12; //모음연습 음성
    Final_service = 13; //종성연습 음성
    Num_service = 14; //숫자연습 음성
    alphabet_service = 15; //알파벳 연습 음성
    Sentence_service = 16; //문장부호 연습 음성
    abbreviation_service = 17; //알파벳 연습 음성

    Letter_service = 21; //글자연습 음성
    Word_service = 22; //단어연습 음성


    //읽기 퀴즈 // 31
    //쓰기 퀴즈 // 32
    //퀴즈 안쪽 // 33

    quiz_reading_service = 310; //읽기 퀴즈 메뉴얼
    reading_initial_service = 311; //초성 읽기퀴즈
    reading_vowel_service = 312; //모음 읽기 퀴즈
    reading_final_service = 313; // 종성 읽기 퀴즈
    reading_number_service = 314; // 숫자 읽기 퀴즈
    reading_alphabet_service = 315; //알파벳 읽기 퀴즈
    reading_sentence_service = 316; //문장부호 읽기 퀴즈
    reading_abbreviation_service = 317; //약자 및 약어 읽기 퀴즈

    quiiz_writing_service = 320; //쓰기 퀴즈 메뉴얼
    menu_quiz_inside_service = 33;


     */


    public static int Service_address=0;
    public static boolean stop = false;

    public Sound_Manager() {
    }

    public void Sound_Stop(){
        stop = true;
        switch(Service_address){
            case 0:
                startService(new Intent(this,Menu_main_service.class));
                break;
            case 1:
                startService(new Intent(this, Version_check_service.class));
                break;
            case 2:
                startService(new Intent(this, Menu_detail_service.class));
                break;
            case 10:
                startService(new Intent(this,Menu_basic_service.class));
                break;
            case 11:
                startService(new Intent(this,Initial_service.class));
                break;
            case 12:
                startService(new Intent(this,Vowel_service.class));
                break;
            case 13:
                startService(new Intent(this,Final_service.class));
                break;
            case 14:
                startService(new Intent(this,Num_service.class));
                break;
            case 15:
                startService(new Intent(this,alphabet_service.class));
                break;
            case 16:
                startService(new Intent(this,Sentence_service.class));
                break;
            case 17:
                startService(new Intent(this,abbreviation_service.class));
                break;
            case 20:
                startService(new Intent(this,Menu_master_service.class));
                break;
            case 21:
                startService(new Intent(this,Letter_service.class));
                break;
            case 22:
                startService(new Intent(this,Word_service.class));
                break;
            case 30:
                startService(new Intent(this,Menu_quiz_service.class));
                break;
            case 320:
                startService(new Intent(this,quiz_writing_service.class));
                break;
            case 321:
                startService(new Intent(this,quiz_reading_service.class));
                break;
            case 33:
                startService(new Intent(this, Menu_quiz_inside_service.class));
                break;
            case 40:
                startService(new Intent(this,Menu_mynote_service.class));
                break;
            case 41:
                startService(new Intent(this, Mynote_service.class));
                break;
            case 50:
                startService(new Intent(this, Braille_trans_service.class));
                break;
            case 60:
                startService(new Intent(this, Menu_comunication_service.class));
                break;
            case 70:
                startService(new Intent(this, Communication_service.class));
                break;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Stop();
        return START_NOT_STICKY;
    }
}
