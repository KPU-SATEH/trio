package com.example.yeo.practice.Common_trans_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

/*
점자번역 음성 출력 서비스
 */
public class Braille_trans_service extends Service {
    MediaPlayer trans_info,transfinish;
    MediaPlayer trans[];
    int rawid[];

    public static int menu_page = 0;
    public static boolean finish = false;
    int previous =0 ;
    public Braille_trans_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        transfinish = MediaPlayer.create(this, R.raw.braille_finish);
        transfinish.setLooping(false);

        trans = new MediaPlayer[]{trans_info};
        rawid = new int[]{R.raw.braille_trans_info_};

        for(int i=0 ; i<trans.length ; i++){
            trans[i]=MediaPlayer.create(this,rawid[i]);
            trans[i].setLooping(false);
        }
    }
    public void init(){

        if(transfinish.isPlaying()) {
            transfinish.reset();
            transfinish = MediaPlayer.create(this, R.raw.braille_finish);
        }

        if(trans[previous].isPlaying()) {
            trans[previous].reset();
            trans[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=50;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish==false) {
                previous = menu_page;
                trans[previous].start();
            }
            else{
                transfinish.start();
                finish=false;
            }
        }

        trans[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                trans[previous].reset();
                trans[previous] = MediaPlayer.create(Braille_trans_service.this,rawid[previous]);
            }
        });

        transfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                transfinish.reset();
                transfinish = MediaPlayer.create(Braille_trans_service.this,R.raw.braille_finish);
            }
        });

        return START_NOT_STICKY;

    }
}
