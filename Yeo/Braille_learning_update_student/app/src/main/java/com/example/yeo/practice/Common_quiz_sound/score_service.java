package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;

/*
퀴즈메뉴 종료후 점수를 출력하는 음성파일을 관리하는 서비스 클래스
 */
public class score_service extends Service {
    private static final String TAG = "Number";
    MediaPlayer zeroscore, onescore, twoscore, threescore, fourscore, fivescore;
    public static int result = 0 ;
    public score_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        zeroscore= MediaPlayer.create(this, R.raw.zeroscore); //0개 맞췄을 때
        onescore= MediaPlayer.create(this, R.raw.onescore); //1개 맞췃을 때
        twoscore= MediaPlayer.create(this, R.raw.twoscore); //2개 맞췃을 때
        threescore= MediaPlayer.create(this, R.raw.threescore); //3개 맞췃을 때
        fourscore= MediaPlayer.create(this, R.raw.fourscore); //4개 맞췄을 때
        fivescore= MediaPlayer.create(this, R.raw.fivescore); //5개 맞췄을 때


        //반복제거
        zeroscore.setLooping(false);
        onescore.setLooping(false);
        twoscore.setLooping(false);
        threescore.setLooping(false);
        fourscore.setLooping(false);
        fivescore.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        switch(result){
            case 0: //0개 맞췃을 때
                if(zeroscore.isPlaying()){
                    zeroscore.reset();
                    zeroscore=MediaPlayer.create(this,R.raw.zeroscore);
                }
                zeroscore.start();
                break;
            case 1: //1개 맞췃을 때
                if(onescore.isPlaying()){
                    onescore.reset();
                    onescore=MediaPlayer.create(this,R.raw.onescore);
                }
                onescore.start();
                break;
            case 2: //2개 맞췃을 때
                if(twoscore.isPlaying()){
                    twoscore.reset();
                    twoscore=MediaPlayer.create(this,R.raw.twoscore);
                }
                twoscore.start();
                break;
            case 3: //3개 맞췃을 때
                if(threescore.isPlaying()){
                    threescore.reset();
                    threescore=MediaPlayer.create(this,R.raw.threescore);
                }
                threescore.start();
                break;
            case 4: //4개 맞췃을 때
                if(fourscore.isPlaying()){
                    fourscore.reset();
                    fourscore=MediaPlayer.create(this,R.raw.fourscore);
                }
                fourscore.start();
                break;
            case 5: //5개 맞췃을 때
                if(fivescore.isPlaying()){
                    fivescore.reset();
                    fivescore=MediaPlayer.create(this,R.raw.fivescore);
                }
                fivescore.start();
                break;
        }
        return START_NOT_STICKY;
    }
}
