package com.example.yeo.practice.Common_basic_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_initial;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_practice;
import com.example.yeo.practice.WHclass;


public class Initial_service extends Service {
    /*
     초성 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Initial_service";
    MediaPlayer giyeok,nieun,digeud,nieul,mieum,bieub,siot,zieut,chieut,kieuk,tieut,pieup,hieut,fortis,fgiyeok, fdigued, fbieub, fsiot, fzieut;
    MediaPlayer initfinish;
    MediaPlayer Initial[];// 선언된 음성 변수들을 배열 변수에 저장
    int rawid[];// 음성파일의 id 주소를 배열변수에 저장

    public static boolean finish = false;
    int previous=0;
    public Initial_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        initfinish = MediaPlayer.create(this, R.raw.initfinish);
        initfinish.setLooping(false);
        Initial = new MediaPlayer[] {giyeok,nieun,digeud,nieul,mieum,bieub,siot,zieut,chieut,kieuk,tieut,pieup,hieut,fortis,fgiyeok, fdigued, fbieub, fsiot, fzieut};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[]{R.raw.giyeok,R.raw.nieun,R.raw.digeud,R.raw.nieul,R.raw.mieum,R.raw.bieub,R.raw.siot,R.raw.zieut,R.raw.chieut,R.raw.kieuk,R.raw.tieut
                ,R.raw.pieup,R.raw.hieut,R.raw.fortis,R.raw.fortis_giyeok,R.raw.fortis_digeud,R.raw.fortis_bieub,R.raw.fortis_siot,R.raw.fortis_zieut};
        // 음성파일의 id 주소를 배열변수에 저장


        for(int i = 0; i< dot_initial.Initial_count; i++){
            Initial[i] = MediaPlayer.create(this, rawid[i]);
            Initial[i].setLooping(false);
        }
    }

    public void init(){
        if(initfinish.isPlaying()){
            initfinish.reset();
            initfinish = MediaPlayer.create(this, R.raw.initfinish);
        }
        if(Initial[previous].isPlaying()) {
            Initial[previous].reset();
            Initial[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=11;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if (WHclass.Braiile_type == 2) { //일반버전
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Braille_short_display.page;
                    Initial[previous].start();
                }
                else {
                    initfinish.start();
                    finish = false;
                }
            }
            else if (WHclass.Braiile_type == 1) { //시각장애인버전
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    Initial[previous].start();
                }
                else {
                    initfinish.start();
                    finish = false;
                }
            }
        }

        Initial[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Initial[previous].reset();
                Initial[previous] = MediaPlayer.create(Initial_service.this, rawid[previous]);
            }
        });

        initfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                initfinish.reset();
                initfinish = MediaPlayer.create(Initial_service.this, R.raw.initfinish);
            }
        });
        return START_NOT_STICKY;
    }

}
