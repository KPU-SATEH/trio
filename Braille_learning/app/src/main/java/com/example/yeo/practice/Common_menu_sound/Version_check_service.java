package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;


// 메뉴에 대한 음성을 출력하는 서비스


public class Version_check_service extends Service {
    private static final String TAG = "version_check_service";
    MediaPlayer versionfinish;
    MediaPlayer check,start,restart,reset,onefinger,blind_person,nromal;
    MediaPlayer version[];
    int rawid[];
    public static int menu_page = 0;
    public static boolean finish = false;
    int previous =0 ;
    public Version_check_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        versionfinish = MediaPlayer.create(this, R.raw.mainfinish);
        versionfinish.setLooping(false);

        version = new MediaPlayer[]{check,start,restart,reset,onefinger,blind_person,nromal};
        // 선언된 음성 변수들을 배열 변수에 저장
        rawid = new int[]{R.raw.version_check,R.raw.version_check_start,R.raw.version_check_restart,R.raw.version_check_reset,R.raw.version_check_onefinger,
                R.raw.version_blind_person,R.raw.version_normal};
        // 선언된 음성 변수 id를 배열변수에 저장
        for(int i = 0; i< 7; i++){
            version[i] = MediaPlayer.create(this, rawid[i]);
            version[i].setLooping(false);
        }
    }

    public void init(){
        if(versionfinish.isPlaying()){
            versionfinish.reset();
            versionfinish = MediaPlayer.create(this, R.raw.mainfinish);
        }
        if(version[previous].isPlaying()) {
            version[previous].reset();
            version[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=1;

        if(Sound_Manager.stop==true)
            init();
        else {
            if(finish==false) {
                init();
                previous = menu_page;
                version[previous].start();
            }
            else if(finish ==true){
                init();
                versionfinish.start();
                finish=false;
            }
        }

        version[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                version[previous].reset();
                version[previous] = MediaPlayer.create(Version_check_service.this,rawid[previous]);
            }
        });

        versionfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                versionfinish.reset();
                versionfinish = MediaPlayer.create(Version_check_service.this,R.raw.mainfinish);
            }
        });

        return START_NOT_STICKY;
    }
}
