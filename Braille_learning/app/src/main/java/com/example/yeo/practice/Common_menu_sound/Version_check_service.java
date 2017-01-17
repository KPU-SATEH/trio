package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_basic_practice_sound.Initial_service;
import com.example.yeo.practice.Common_braille_data.dot_initial;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.WHclass;

// 메뉴에 대한 음성을 출력하는 서비스


public class Version_check_service extends Service {
    private static final String TAG = "version_check_service";
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
            init();
            previous = menu_page;
            version[previous].start();
        }

        version[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                version[previous].reset();
                version[previous] = MediaPlayer.create(Version_check_service.this,rawid[previous]);
            }
        });

        return START_NOT_STICKY;
    }
}
