package com.example.yeo.practice.Common_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

/*
점자 학습 기능을 위해 출력되는 음성 및 효과음을 관리하는 서비스
 */
public class Number extends Service {
    private static final String TAG = "Number";
    MediaPlayer mnumber1,mnumber2,mnumber3,mnumber4,mnumber5,mnumber6; //남성의 음성으로 1~6
    MediaPlayer wnumber1,wnumber2,wnumber3,wnumber4,wnumber5,wnumber6; //여성의 음성으로 1~6

    MediaPlayer mwnumber[]; //남자와 여자소리 담는 배열
    MediaPlayer alarm,alarm2; //구분선의 효과음과 이탈방지의 경고음

    int rawid[];

    int pre_number=0;
    int number=0;
    public Number() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate(){
        mwnumber = new MediaPlayer[]{mnumber1,mnumber2,mnumber3,mnumber4,mnumber5,mnumber6,alarm,alarm2,wnumber1,wnumber2,wnumber3,wnumber4,wnumber5,wnumber6};
        rawid = new int[]{R.raw.men_1,R.raw.men_2,R.raw.men_3,R.raw.men_4,R.raw.men_5,R.raw.men_6,R.raw.alarm,R.raw.alarm2,R.raw.women_1,R.raw.women_2,R.raw.women_3,R.raw.women_4,R.raw.women_5,R.raw.women_6};

        for(int i = 0; i< 14; i++){
            mwnumber[i] = MediaPlayer.create(this, rawid[i]);
            mwnumber[i].setLooping(false);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if(WHclass.number!=0) {
            if (WHclass.target == true) {
                number = WHclass.number - 1;
                if (pre_number != number) {
                    if (pre_number == 6 || pre_number == 7) {
                    } else if (mwnumber[pre_number].isPlaying()) {
                        mwnumber[pre_number].reset();
                        mwnumber[pre_number] = MediaPlayer.create(this, rawid[pre_number]);
                    }
                }
                mwnumber[number].start();
            } else if (WHclass.target == false) {
                number = WHclass.number + 7;
                if (pre_number != number) {
                    if (mwnumber[pre_number].isPlaying()) {
                        mwnumber[pre_number].reset();
                        mwnumber[pre_number] = MediaPlayer.create(this, rawid[pre_number]);
                    }
                }
                mwnumber[number].start();
            }
            pre_number = number;
        }
        return super.onStartCommand(intent, flags, startID);
    }


}
