package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_quiz.quiz_reading_manual;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

/*
퀴즈가 진행될때 출력되는 음성을 관리하는 서비스
 */

public class quiz_reading_service extends Service {
    private static final String TAG = "quiz_reading_service";

/*
쓰기 퀴즈 음성을 관리하는 클래스
 */

    MediaPlayer readingfinish;
    public static int question = 0;
    MediaPlayer direction, first, second, last, success, fail, all_finish;
    MediaPlayer reading[];  //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    static public boolean finish = false; //점자 학습의 종료를 알리는 변수
    static public int menu_page = 1;
    int previous=0;
    static public boolean progress = false;
    static public int page=0;
    public quiz_reading_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        readingfinish = MediaPlayer.create(this, R.raw.readingfinish);
        readingfinish.setLooping(false);
        all_finish = MediaPlayer.create(this, R.raw.readingfinish2);
        all_finish.setLooping(false);

        reading = new MediaPlayer[] {direction, first, second, last, success, fail};         // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.reading_quiz_manual, R.raw.reading_quiz_first, R.raw.reading_quiz_second, R.raw.reading_quiz_third, R.raw.writing_quiz_success, R.raw.writing_quiz_fail};
        // 음성파일의 id 주소를 배열변수에 저장



        for(int i = 0; i< 6; i++){
            reading[i] = MediaPlayer.create(this, rawid[i]);
            reading[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(readingfinish.isPlaying()){
            readingfinish.reset();
            readingfinish = MediaPlayer.create(this, R.raw.readingfinish);
        }
        if(all_finish.isPlaying()){
            all_finish.reset();
            all_finish = MediaPlayer.create(this, R.raw.readingfinish2);
        }
        if(reading[previous].isPlaying()) {
            reading[previous].reset();
            reading[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=321;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish==false) {
                previous = menu_page;
                reading[previous].start();
            }
            else{
                if(progress==true)
                    all_finish.start();
                else
                    readingfinish.start();
                progress=false;
                finish=false;
            }
        }

        all_finish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                all_finish.reset();
                all_finish = MediaPlayer.create(quiz_reading_service.this,R.raw.readingfinish2);
            }
        });

        readingfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                readingfinish.reset();
                readingfinish = MediaPlayer.create(quiz_reading_service.this,R.raw.readingfinish);
            }
        });

        reading[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                reading[previous].reset();
                reading[previous] = MediaPlayer.create(quiz_reading_service.this,rawid[previous]);
                switch(Menu_info.MENU_QUIZ_INFO){
                    case 0:
                        startService(new Intent(quiz_reading_service.this, reading_initial_service.class));
                        break;
                    case 1:
                        startService(new Intent(quiz_reading_service.this, reading_vowel_service.class));
                        break;
                    case 2:
                        startService(new Intent(quiz_reading_service.this, reading_final_service.class));
                        break;
                    case 3:
                        startService(new Intent(quiz_reading_service.this, reading_number_service.class));
                        break;
                    case 4:
                        startService(new Intent(quiz_reading_service.this, reading_alphabet_service.class));
                        break;
                    case 5:
                        startService(new Intent(quiz_reading_service.this, reading_sentence_service.class));
                        break;
                    case 6:
                        startService(new Intent(quiz_reading_service.this, reading_abbreviation_service.class));
                        break;
                    case 7:
                        startService(new Intent(quiz_reading_service.this, reading_letter_service.class));
                        break;
                    case 8:
                        startService(new Intent(quiz_reading_service.this, reading_word_service.class));
                        break;
                }
            }
        });
        return START_NOT_STICKY;
    }
}