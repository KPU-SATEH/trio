package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

// 퀴즈 메뉴에 대한 음성을 출력해주는 서비스

public class Menu_quiz_inside_service extends Service {
    private static final String TAG = "Menu_quiz_inside_service";
    MediaPlayer quizinitfinish,quizvowelfinish,quizfinalfinish,quiznumberfinish,quizalphabetfinish,
            quizsentencefinish,quizabbreviationfinish,quizletterfinish,quizwordfinish;
    MediaPlayer read,write;
    MediaPlayer quiz_inside[]; //퀴즈 메뉴 음성변수들을 저장하는 배열 변수
    int quiz_menu_count=9;  // 퀴즈메뉴의 갯수
    int rawid[]; //퀴즈 메뉴의 음성 id를 저장하는 배열 변수
    public static int menu_page = 0;
    public static boolean finish = false;
    int previous = 0;
    public Menu_quiz_inside_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        //음성변수들과 id를 배열변수에 저장
        read = MediaPlayer.create(this, R.raw.quiz_reading);
        read.setLooping(false);
        write = MediaPlayer.create(this, R.raw.quiz_writing);
        write.setLooping(false);

        quiz_inside = new MediaPlayer[]{quizinitfinish,quizvowelfinish,quizfinalfinish,quiznumberfinish,quizalphabetfinish,
                                            quizsentencefinish,quizabbreviationfinish,quizletterfinish,quizwordfinish};
        rawid = new int[] {R.raw.quiz_initial_finish, R.raw.quiz_vowel_finish, R.raw.quiz_final_finish, R.raw.quiz_number_finish, R.raw.quiz_alphabet_finish,
                R.raw.quiz_sentence_finish, R.raw.quiz_abbreviation_finish, R.raw.quiz_letter_quiz, R.raw.quiz_word_finish};

        for(int i = 0; i< quiz_menu_count; i++){
            quiz_inside[i] = MediaPlayer.create(this, rawid[i]);
            quiz_inside[i].setLooping(false);
        }
    }
    public void init(){ //음성파일 초기화 함수
        if(read.isPlaying()){
           read.reset();
           read = MediaPlayer.create(this, R.raw.quiz_reading);
        }

        if(write.isPlaying()){
           write.reset();
           write = MediaPlayer.create(this, R.raw.quiz_writing);
        }

        if(quiz_inside[previous].isPlaying()){
           quiz_inside[previous].reset();
           quiz_inside[previous] = MediaPlayer.create(this, rawid[previous]);
        }

        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=33;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            previous = menu_page;
            if (finish == false) {
                if(menu_page==1) read.start();
                else if(menu_page==2) write.start();
            }
            else {
                quiz_inside[menu_page].start();
                finish = false;
            }
        }

        read.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                read.reset();
                read = MediaPlayer.create(Menu_quiz_inside_service.this, R.raw.quiz_reading);
            }
        });

        write.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                write.reset();
                write = MediaPlayer.create(Menu_quiz_inside_service.this, R.raw.quiz_writing);
            }
        });

        quiz_inside[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                quiz_inside[previous].reset();
                quiz_inside[previous] = MediaPlayer.create(Menu_quiz_inside_service.this, rawid[previous]);
            }
        });

        return START_NOT_STICKY;
    }
}
