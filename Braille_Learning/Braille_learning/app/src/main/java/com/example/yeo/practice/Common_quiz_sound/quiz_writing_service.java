package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;


public class quiz_writing_service extends Service {
    private static final String TAG = "quiz_writing_service";

/*
쓰기 퀴즈 음성을 관리하는 클래스
 */

    MediaPlayer writingfinish;

    MediaPlayer direction, first, second, last, success, fail, all_finish;
    MediaPlayer writing[];  //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    static public boolean finish = false; //점자 학습의 종료를 알리는 변수
    static public int menu_page = 1;
    int previous=0;
    static public boolean progress = false;
    static public int page=0;
    public quiz_writing_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        writingfinish = MediaPlayer.create(this, R.raw.writingfinish);
        writingfinish.setLooping(false);
        all_finish = MediaPlayer.create(this, R.raw.writingfinish2);
        all_finish.setLooping(false);

       // writing = new MediaPlayer[] {direction, first, second, last, success, fail};         // 선언된 음성 변수들을 배열 변수에 저장
        writing = new MediaPlayer[] {direction,success, fail};         // 선언된 음성 변수들을 배열 변수에 저장
       // rawid = new int[] {R.raw.writing_direction, R.raw.writing_quiz_first, R.raw.wrting_quiz_second, R.raw.writing_quiz_last, R.raw.writing_quiz_success, R.raw.writing_quiz_fail};
        rawid = new int[] {R.raw.writing_direction, R.raw.writing_quiz_success, R.raw.writing_quiz_fail};
        // 음성파일의 id 주소를 배열변수에 저장



        for(int i = 0; i< writing.length; i++){
            writing[i] = MediaPlayer.create(this, rawid[i]);
            writing[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(writingfinish.isPlaying()){
            writingfinish.reset();
            writingfinish = MediaPlayer.create(this, R.raw.writingfinish);
        }
        if(all_finish.isPlaying()){
            all_finish.reset();
            all_finish = MediaPlayer.create(this, R.raw.writingfinish2);
        }
        if(writing[previous].isPlaying()) {
            writing[previous].reset();
            writing[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=320;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish==false) {
                previous = menu_page;
                writing[previous].start();
            }
            else{
                if(progress==true)
                    all_finish.start();
                else
                    writingfinish.start();
                progress=false;
                finish=false;
            }
        }

        all_finish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                all_finish.reset();
                all_finish = MediaPlayer.create(quiz_writing_service.this,R.raw.writingfinish2);
            }
        });

        writingfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                writingfinish.reset();
                writingfinish = MediaPlayer.create(quiz_writing_service.this,R.raw.writingfinish);
            }
        });

        writing[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                writing[previous].reset();
                writing[previous] = MediaPlayer.create(quiz_writing_service.this,rawid[previous]);
            }
        });

        return START_NOT_STICKY;
    }
}