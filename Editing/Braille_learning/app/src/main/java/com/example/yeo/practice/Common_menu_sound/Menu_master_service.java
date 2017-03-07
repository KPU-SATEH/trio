package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

//숙련과정 메뉴 음성 출력 서비스

public class Menu_master_service extends Service {
    private static final String TAG = "Menu_basic_service";
    MediaPlayer letter, word,masterfinish;
    MediaPlayer master[];
    int rawid[];
    static public int menu_page = 0;
    public static boolean finish = false;
    int previous=0;
    public Menu_master_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        masterfinish = MediaPlayer.create(this, R.raw.masterfinish);
        masterfinish.setLooping(false);

        master = new MediaPlayer[]{letter, word};
        rawid = new int[]{R.raw.letter, R.raw.word};
        for(int i=0 ; i<2 ; i++){
            master[i] = MediaPlayer.create(this, rawid[i]);
            master[i].setLooping(false);
        }
    }
    public void init(){
        if(masterfinish.isPlaying()){
            masterfinish.reset();
            masterfinish = MediaPlayer.create(this, R.raw.masterfinish);
        }
        if(master[previous].isPlaying()){
            master[previous].reset();
            master[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=20;
        if(Sound_Manager.stop==true)
            init();
        else{
            init();
            if(finish==false) {
                previous = menu_page;
                master[previous].start();
            }
            else{
                masterfinish.start();
                finish=false;
            }
        }
        master[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                master[previous].reset();
                master[previous] = MediaPlayer.create(Menu_master_service.this, rawid[previous]);
            }
        });
        masterfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                masterfinish.reset();
                masterfinish = MediaPlayer.create(Menu_master_service.this,R.raw.masterfinish);
            }
        });

        return START_NOT_STICKY;
    }
}
