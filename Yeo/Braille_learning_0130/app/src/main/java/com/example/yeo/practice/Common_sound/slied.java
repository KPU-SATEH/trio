package com.example.yeo.practice.Common_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;

// 이전화면과 다음화면으로 이동될 때 안내되는 음성을 관리하는 서비스

public class slied extends Service {
    private static final String TAG = "Number";
    MediaPlayer next,previous; //이전과 다음 음성을 저장하는 변수
    static public int slied = 0;
    public slied() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        next= MediaPlayer.create(this, R.raw.next);
        previous= MediaPlayer.create(this, R.raw.previous);
        next.setLooping(false);
        previous.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if(slied == 1) { //다음
            next.start();
        }
        else if(slied==2) { //이전
            previous.start();
        }
        return super.onStartCommand(intent, flags, startID);
    }
}
