package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

// 퀴즈 메뉴에 대한 음성을 출력해주는 서비스

public class Menu_quiz_service extends Service {
    private static final String TAG = "Menu_basic_service";
    MediaPlayer init_quiz,vowel_quiz,final_quiz,number_quiz,alphabet_quiz,sentence_quiz,abbreviation_quiz,letter_quiz,word_quiz;
    MediaPlayer quizfinish;
    MediaPlayer quiz_menu[]; //퀴즈 메뉴 음성변수들을 저장하는 배열 변수
    int quiz_menu_count=9;  // 퀴즈메뉴의 갯수
    int rawid[]; //퀴즈 메뉴의 음성 id를 저장하는 배열 변수
    public static int menu_page = 0;
    public static boolean finish = false;
    int previous = 0;
    public Menu_quiz_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        //quizfinish = MediaPlayer.create(this, R.raw.quiz)
        //퀴즈메뉴의 음성변수들과 id를 배열변수에 저장
        quizfinish = MediaPlayer.create(this, R.raw.quiz_finish); //퀴즈 종료
        quiz_menu = new MediaPlayer[] {init_quiz,vowel_quiz,final_quiz,number_quiz,alphabet_quiz,sentence_quiz,abbreviation_quiz,letter_quiz,word_quiz};
        rawid = new int[] {R.raw.initial_quiz, R.raw.vowel_quiz, R.raw.final_quiz, R.raw.num_quiz, R.raw.alphabet_quiz, R.raw.sentence_quiz, R.raw.abbreviation_quiz,
                R.raw.letter_quiz, R.raw.verb_quiz};

        for(int i = 0; i< quiz_menu_count; i++){
            quiz_menu[i] = MediaPlayer.create(this, rawid[i]);
            quiz_menu[i].setLooping(false);
        }

    }
    public void init(){ //음성파일 초기화 함수
        if(quizfinish.isPlaying()){
            quizfinish.reset();
            quizfinish = MediaPlayer.create(this, R.raw.quiz_finish);
        }
        if(quiz_menu[previous].isPlaying()) {
            quiz_menu[previous].reset();
            quiz_menu[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=30;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if (finish == false) {
                previous = menu_page;
                quiz_menu[previous].start();
            }
            else {
                quizfinish.start();
                finish = false;
            }
        }
        quizfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                quizfinish.reset();
                quizfinish = MediaPlayer.create(Menu_quiz_service.this, R.raw.quiz_finish);
            }
        });
        return START_NOT_STICKY;
    }
}
