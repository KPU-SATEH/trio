package com.example.yeo.practice.Common_mynote_database;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;

//숙련과정 메뉴 음성 출력 서비스

public class Mynote_service extends Service {
    private static final String TAG = "mynote_service";
    MediaPlayer basicfinish,masterfinish,nodata,loaddata,inputdata,datafull;
    MediaPlayer mynote[];
    int rawid[];
    static public int menu_page = 0;
    static public int menutype=0;
    public static boolean finish = false;
    int previous=0;
    public Mynote_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        basicfinish = MediaPlayer.create(this, R.raw.basicnotefinish);
        basicfinish.setLooping(false);

        masterfinish = MediaPlayer.create(this, R.raw.masternotefinish);
        masterfinish.setLooping(false);

        mynote = new MediaPlayer[]{nodata,loaddata,inputdata,datafull};
        rawid = new int[]{R.raw.no_input_data,R.raw.load_data_success,R.raw.success_input_data, R.raw.data_full};
        for(int i=0 ; i<4 ; i++){
            mynote[i] = MediaPlayer.create(this, rawid[i]);
            mynote[i].setLooping(false);
        }
    }
    public void init(){
        if(masterfinish.isPlaying()){
            masterfinish.reset();
            masterfinish = MediaPlayer.create(this, R.raw.masternotefinish);
        }

        if(basicfinish.isPlaying()){
            basicfinish.reset();
            basicfinish = MediaPlayer.create(this, R.raw.basicnotefinish);

        }

        if(mynote[previous].isPlaying()){
            mynote[previous].reset();
            mynote[previous].setLooping(false);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=40;
        if(Sound_Manager.stop==true)
            init();
        else{
            if(finish==false) {
                previous = menu_page;
                mynote[previous].start();
            }
            else{
                if(menutype==0)
                    basicfinish.start();
                else if(menutype==1)
                    masterfinish.start();
                finish=false;
            }
        }
        basicfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                basicfinish.reset();
                basicfinish = MediaPlayer.create(Mynote_service.this,R.raw.basicnotefinish);
            }
        });

        masterfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                masterfinish.reset();
                masterfinish = MediaPlayer.create(Mynote_service.this,R.raw.masternotefinish);
            }
        });

        return START_NOT_STICKY;
    }
}
