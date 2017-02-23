package com.example.yeo.practice.Normal_version_tutorial;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial_service extends Service {

    MediaPlayer tutorial_0,tutorial_1, tutorial_2, tutorial_3, tutorial_4,tutorial_5, tutorial_6, tutorial_7, tutorial_8,
            tutorial_9, tutorial_10, tutorial_11, tutorial_12, tutorial_13, tutorial_14, tutorial_15, tutorial_16,
            tutorial_17, tutorial_18, tutorial_19, tutorial_20, tutorial_21;
    MediaPlayer tutorial[];
    int rawid[];
    int count= 22;
    boolean setting[] = new boolean[count];
    int previous=0;
    boolean progress = false;
    public Tutorial_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {return null;  }
    public void onCreate(){

        tutorial = new MediaPlayer[]{tutorial_0,tutorial_1, tutorial_2, tutorial_3, tutorial_4,tutorial_5, tutorial_6, tutorial_7, tutorial_8,
                tutorial_9, tutorial_10, tutorial_11, tutorial_12, tutorial_13, tutorial_14, tutorial_15, tutorial_16,
                tutorial_17,tutorial_18, tutorial_19, tutorial_20, tutorial_21};
        rawid =new int[]{R.raw.tutorial_0,R.raw.tutorial_1, R.raw.tutorial_2, R.raw.tutorial_3, R.raw.tutorial_4, R.raw.tutorial_5, R.raw.tutorial_6, R.raw.tutorial_7,
                R.raw.tutorial_8,R.raw.tutorial_9, R.raw.tutorial_10, R.raw.tutorial_11, R.raw.tutorial_12, R.raw.tutorial_13, R.raw.tutorial_14,
                R.raw.tutorial_15, R.raw.tutorial_16, R.raw.tutorial_17, R.raw.tutorial_18, R.raw.tutorial_19, R.raw.tutorial_20, R.raw.tutorial_21};


        for(int i=0 ; i<count ; i++){
            setting[i]=false;
        }


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if (setting[WHclass.tutorial_progress] == false) {
            setting[WHclass.tutorial_progress] = true;
            tutorial[WHclass.tutorial_progress] = MediaPlayer.create(this, rawid[WHclass.tutorial_progress]);
            tutorial[WHclass.tutorial_progress].setLooping(false);
            tutorial[WHclass.tutorial_progress].start();
            WHclass.touchevent = false;
        }
        else{
            tutorial[WHclass.tutorial_progress].start();
            WHclass.touchevent = false;
        }
        tutorial[WHclass.tutorial_progress].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                tutorial[WHclass.tutorial_progress].reset();
                tutorial[WHclass.tutorial_progress] = MediaPlayer.create(Tutorial_service.this, rawid[WHclass.tutorial_progress]);
                WHclass.tutorial_previous = WHclass.tutorial_progress;

                if (Tutorial.speechani.isRunning()) {
                    Tutorial.speechani.stop();
                }

                if(WHclass.tutorial_progress <20) {
                    WHclass.tutorial_progress++;
                }
                else {
                    WHclass.tutorial_progress = 0;
                    WHclass.tutorial_previous = 0;
                }
                WHclass.touchevent = true;
            }
        });
        return START_NOT_STICKY;
    }


}
