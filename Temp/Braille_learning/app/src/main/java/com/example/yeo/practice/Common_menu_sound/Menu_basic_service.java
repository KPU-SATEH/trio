package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_basic_practice_sound.Initial_service;
import com.example.yeo.practice.Common_braille_data.dot_initial;
import com.example.yeo.practice.Common_master_practice_sound.Word_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.WHclass;

// 메뉴에 대한 음성을 출력하는 서비스

public class Menu_basic_service extends Service {
    private static final String TAG = "Menu_basic_service";
    MediaPlayer Init,Vowel,Final,number,alphabet,Sentence,abbreviation,basicfinish;
    MediaPlayer basic[];
    int rawid[];
    public static int menu_page = 0;
    public static boolean finish = false;
    int previous =0 ;
    public Menu_basic_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        basicfinish = MediaPlayer.create(this, R.raw.basicfinish); //기초과정 종료
        basicfinish.setLooping(false);

        basic = new MediaPlayer[]{Init,Vowel,Final,number,alphabet,Sentence,abbreviation};
        // 선언된 음성 변수들을 배열 변수에 저장
        rawid = new int[]{R.raw.initial,R.raw.vowel,R.raw.finalconsonant,R.raw.number,R.raw.alphabet,R.raw.start_sentence,R.raw.abbreviation_start};
        // 선언된 음성 변수 id를 배열변수에 저장
        for(int i = 0; i< 7; i++){
            basic[i] = MediaPlayer.create(this, rawid[i]);
            basic[i].setLooping(false);
        }
    }

    public void init(){
        if(basicfinish.isPlaying()){
            basicfinish.reset();
            basicfinish = MediaPlayer.create(this, R.raw.basicfinish);
        }
        if(basic[previous].isPlaying()) {
            basic[previous].reset();
            basic[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=10;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish==false) {
                previous = menu_page - 1;
                basic[previous].start();
            }
            else{
                basicfinish.start();
                finish=false;
            }
        }
        basic[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                basic[previous].reset();
                basic[previous] = MediaPlayer.create(Menu_basic_service.this, rawid[previous]);
            }
        });
        basicfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                basicfinish.reset();
                basicfinish = MediaPlayer.create(Menu_basic_service.this,R.raw.basicfinish);
            }
        });
        return START_NOT_STICKY;
    }
}
