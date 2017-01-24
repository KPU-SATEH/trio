package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_quiz_initial;
import com.example.yeo.practice.R;

/*
초성 퀴즈에서 출력되는 음성파일을 관리하는 서비스 클래스
 */
public class quiz_initial_service extends Service {
    private static final String TAG = "quiz_initial_service";
    MediaPlayer giyeok,nieun,digeud,nieul,mieum,bieub,siot,zieut,chieut,kieuk,tieut,pieup,hieut,fortis,fgiyeok, fdigued, fbieub, fsiot, fzieut,initfinish;
    MediaPlayer Initial[]; //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    static boolean finish = false; //점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public quiz_initial_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        Initial = new MediaPlayer[] {giyeok,nieun,digeud,nieul,mieum,bieub,siot,zieut,chieut,kieuk,tieut,pieup,hieut,fortis,fgiyeok, fdigued, fbieub, fsiot, fzieut};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.giyeok0,R.raw.nieun0,R.raw.digued0,R.raw.nieul0,R.raw.mieum0,R.raw.bieub0,R.raw.siot0,R.raw.zieut0,R.raw.chieut0,R.raw.kieuk0,
                R.raw.tieut0,R.raw.pieup0,R.raw.hieut0,R.raw.fortis0,R.raw.fortis_giyuck0,R.raw.fortis_digued0,R.raw.fortis_bieub0,R.raw.fortis_siot0,R.raw.fortis_zieut0};
        // 음성파일의 id 주소를 배열변수에 저장



        for(int i = 0; i< dot_quiz_initial.Initialcount; i++){
            Initial[i] = MediaPlayer.create(this, rawid[i]);
            Initial[i].setLooping(false);
        }
    }
    public void init(){  //사용한 음성파일을 재 설정해주는 함수
        if(Initial[previous].isPlaying()) {
            Initial[previous].reset();
            Initial[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        /*if(finish == false) {
            if (progress == false) {
                progress = true;
                previous = quiz_initial.page;
            } else if (progress == true) {
                init();
                previous = quiz_initial.page;
            }
            Initial[quiz_initial.page].start();
        }
        else {
            init();
            finish = false;
        }*/
        return START_NOT_STICKY;
    }
}
