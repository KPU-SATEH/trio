package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

//숙련과정 메뉴 음성 출력 서비스

public class Menu_mynote_service extends Service {
    private static final String TAG = "Menu_mynote_service";
    MediaPlayer basic,master,communication,mynotefinish;
    MediaPlayer mynote[];
    int rawid[];
    static public int menu_page = 0;
    public static boolean finish = false;
    int previous=0;
    public Menu_mynote_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        mynotefinish = MediaPlayer.create(this, R.raw.mynotefinish);
        mynotefinish.setLooping(false);

        mynote = new MediaPlayer[]{basic, master, communication};
        rawid = new int[]{R.raw.mynote_basic, R.raw.mynote_master, R.raw.mynote_communication};
        for(int i=0 ; i<3 ; i++){
            mynote[i] = MediaPlayer.create(this, rawid[i]);
            mynote[i].setLooping(false);
        }
    }
    public void init(){
        if(mynotefinish.isPlaying()){
            mynotefinish.reset();
            mynotefinish = MediaPlayer.create(this, R.raw.mynotefinish);

        }
        if(mynote[previous].isPlaying()){
            mynote[previous].reset();
            mynote[previous] = MediaPlayer.create(this, rawid[previous]);
        }

        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=40;
        if(Sound_Manager.stop==true)
            init();
        else{
            init();
            if(finish==false) {
                previous = menu_page;
                mynote[previous].start();
            }
            else{
                mynotefinish.start();
                finish=false;
            }
        }
        mynote[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mynote[previous].reset();
                mynote[previous] = MediaPlayer.create(Menu_mynote_service.this, rawid[previous]);
            }
        });
        mynotefinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mynotefinish.reset();
                mynotefinish = MediaPlayer.create(Menu_mynote_service.this,R.raw.mynotefinish);
            }
        });

        return START_NOT_STICKY;
    }
}
