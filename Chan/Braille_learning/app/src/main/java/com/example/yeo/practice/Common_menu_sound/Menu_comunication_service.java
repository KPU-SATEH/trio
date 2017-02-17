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
public class Menu_comunication_service extends Service {
    private static final String TAG = "Menu_basic_service";
    MediaPlayer teacher, student;
    MediaPlayer comunication[];
    int rawid[];

    public static int menu_page = 0;
    public static boolean finish = false;
    int previous =0 ;
    public Menu_comunication_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        comunication = new MediaPlayer[]{teacher, student};
        rawid = new int[]{R.raw.comunication_teacher, R.raw.comunication_student};

        for(int i=0 ; i<comunication.length ; i++){
            comunication[i]=MediaPlayer.create(this,rawid[i]);
            comunication[i].setLooping(false);
        }
    }
    public void init(){
/*
        if(mainfinish.isPlaying()) {
            mainfinish.reset();
            mainfinish = MediaPlayer.create(this, R.raw.mainfinish);
        }
*/
        if(comunication[previous].isPlaying()) {
            comunication[previous].reset();
            comunication[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=60;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish==false) {
                previous = menu_page;
                comunication[previous].start();
            }
            else{
                /*
                mainfinish.start();
                finish=false;
                */
            }
        }
        comunication[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                comunication[previous].reset();
                comunication[previous] = MediaPlayer.create(Menu_comunication_service.this, rawid[previous]);
            }
        });
/*
        mainfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mainfinish.reset();
                mainfinish = MediaPlayer.create(Menu_main_service.this,R.raw.mainfinish);
            }
        });
*/
        return START_NOT_STICKY;

    }
}
