package com.example.yeo.practice.Common_menu_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

/*
대메뉴 화면 음성 출력 서비스
 */
public class Menu_main_service extends Service {
    private static final String TAG = "Menu_basic_service";
    MediaPlayer basic,master,quiz, tutorial,mynote,braille,mainfinish;
    MediaPlayer main[];
    int rawid[];

    public static int menu_page = 0;
    public static boolean finish = false;
    int previous =0 ;
    public Menu_main_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mainfinish = MediaPlayer.create(this,R.raw.mainfinish);
        mainfinish.setLooping(false);

        main = new MediaPlayer[]{tutorial,basic,master,braille,quiz,mynote};
        rawid = new int[]{R.raw.directions,R.raw.basic,R.raw.master,R.raw.master,R.raw.quiz,R.raw.mynote};

        for(int i=0 ; i<6 ; i++){
            main[i]=MediaPlayer.create(this,rawid[i]);
            main[i].setLooping(false);
        }
    }
    public void init(){

        if(mainfinish.isPlaying()) {
            mainfinish.reset();
            mainfinish = MediaPlayer.create(this, R.raw.mainfinish);
        }

        if(main[previous].isPlaying()) {
            main[previous].reset();
            main[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=0;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish==false) {
                previous = menu_page;
                main[previous].start();
            }
            else{
                mainfinish.start();
                finish=false;
            }
        }

        mainfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mainfinish.reset();
                mainfinish = MediaPlayer.create(Menu_main_service.this,R.raw.mainfinish);
            }
        });

        return START_NOT_STICKY;

    }
}
