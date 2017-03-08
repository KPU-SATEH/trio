package com.example.yeo.practice.Coomon_communication_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;


public class Communication_service extends Service {
    /*
      종성 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
      */
    private static final String TAG = "Common_Tutorial_service";
    MediaPlayer teacherfinish, studentfinish;

    public static boolean finish1 = false;
    public static boolean finish2 = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;
    public Communication_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        // 선언된 음성 변수들을 배열 변수에 저장
        teacherfinish = MediaPlayer.create(this, R.raw.teacher_finish);
        studentfinish = MediaPlayer.create(this, R.raw.student_finish);

    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(teacherfinish.isPlaying()){
            teacherfinish.reset();
            teacherfinish = MediaPlayer.create(this, R.raw.teacher_finish);
        }
        if(studentfinish.isPlaying()){
            studentfinish.reset();
            studentfinish = MediaPlayer.create(this, R.raw.student_finish);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=70;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();

            if (finish1 == true) {
                teacherfinish.start();
                finish1 = false;
            } else if(finish2 == true){
                studentfinish.start();
                finish2 = false;
            }
        }

        teacherfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                teacherfinish.reset();
                teacherfinish = MediaPlayer.create(Communication_service.this, R.raw.teacher_finish);
            }
        });

        studentfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                studentfinish.reset();
                studentfinish = MediaPlayer.create(Communication_service.this, R.raw.student_finish);
            }
        });
        return START_NOT_STICKY;
    }
}
