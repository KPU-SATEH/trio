package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_quiz_final;
import com.example.yeo.practice.R;


public class quiz_final_service extends Service {
    private static final String TAG = "quiz_final_service";

/*
종성 퀴즈에서 출력되는 음성파일을 관리하는 서비스 클래스
 */



    MediaPlayer final_giyeok,final_nieun,final_digeud,final_nieul,final_mieum,final_bieub,
            final_siot,final_eng,final_zieut,final_chieut,final_kieuk,final_tieut,final_pieup, final_hieut;
    MediaPlayer Final[];  //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    static boolean finish = false; //점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public quiz_final_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        Final = new MediaPlayer[] {final_giyeok,final_nieun,final_digeud,final_nieul,final_mieum,final_bieub,
                final_siot,final_eng,final_zieut,final_chieut,final_kieuk,final_tieut,final_pieup, final_hieut};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.final_giyeok0,R.raw.final_nieun0,R.raw.final_digeud0,R.raw.final_nieul0,R.raw.final_mieum0,
                R.raw.final_bieub0, R.raw.final_siot0,R.raw.final_eng0,R.raw.final_zieut0,R.raw.final_chieut0,R.raw.final_kieuk0,
                R.raw.final_tieut0,R.raw.final_pieup0,R.raw.final_hieut0};
        // 음성파일의 id 주소를 배열변수에 저장



        for(int i = 0; i< dot_quiz_final.finalcount; i++){
            Final[i] = MediaPlayer.create(this, rawid[i]);
            Final[i].setLooping(false);
        }
    }
    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(Final[previous].isPlaying()) {
            Final[previous].reset();
            Final[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        /*if(finish == false) {
            if (progress == false) {
                progress = true;
                previous = quiz_final.page;
            } else if (progress == true) {
                init();
                previous = quiz_final.page;
            }
            Final[quiz_final.page].start();
        }
        else {
            init();
            finish = false;

        }*/
        return START_NOT_STICKY;
    }
}