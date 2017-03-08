package com.example.yeo.practice.Common_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;

// 이전화면과 다음화면으로 이동될 때 안내되는 음성을 관리하는 서비스

public class slied extends Service {
    private static final String TAG = "Number";
    MediaPlayer next,previous,stt; //이전과 다음 음성을 저장하는 변수
    MediaPlayer slied_[];
    int rawid[];
    int pre;
    static public int slied ;
    public slied() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        slied_ = new MediaPlayer[]{stt,next,previous};
        rawid = new int[]{R.raw.stt,R.raw.next,R.raw.previous};

        for(int i=0; i<slied_.length ; i++){
            slied_[i] = MediaPlayer.create(this,rawid[i]);
            slied_[i].setLooping(false);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        pre = slied;
        slied_[slied].start();
        slied_[slied].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                slied_[slied].reset();
                slied_[slied] = MediaPlayer.create(slied.this,rawid[slied]);
            }
        });
        return START_NOT_STICKY;
    }
}
