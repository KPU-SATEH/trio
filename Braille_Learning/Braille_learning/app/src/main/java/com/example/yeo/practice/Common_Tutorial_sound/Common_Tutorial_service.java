package com.example.yeo.practice.Common_Tutorial_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_initial;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_practice;
import com.example.yeo.practice.WHclass;


public class Common_Tutorial_service extends Service {
    /*
     초성 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Common_Tutorial_service";
    MediaPlayer tutorial1, tutorial2, tutorial3, tutorial4, tutorial5, tutorial6, tutorial7, tutorial8;
    MediaPlayer tutorial[];// 선언된 음성 변수들을 배열 변수에 저장
    MediaPlayer tutorial_finish1 ;
    MediaPlayer tutorial_finish2;
    int rawid[];// 음성파일의 id 주소를 배열변수에 저장

    public static boolean finish = false;
    public static int previous=0;
    public static boolean Touch_lock=false;
    public Common_Tutorial_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        tutorial_finish1 = MediaPlayer.create(this, R.raw.mainfinish);
        tutorial_finish1.setLooping(false);
        tutorial_finish2 = MediaPlayer.create(this, R.raw.tutorial_finish);
        tutorial_finish2.setLooping(false);

        tutorial = new MediaPlayer[] {tutorial1, tutorial2, tutorial3, tutorial4, tutorial5, tutorial6, tutorial7, tutorial8};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[]{R.raw.tutorial1, R.raw.tutorial2, R.raw.tutorial3, R.raw.tutorial4, R.raw.tutorial5, R.raw.tutorial6, R.raw.tutorial7, R.raw.tutorial8};
        // 음성파일의 id 주소를 배열변수에 저장


        for(int i = 0; i< tutorial.length; i++){
            tutorial[i] = MediaPlayer.create(this, rawid[i]);
            tutorial[i].setLooping(false);
        }
    }

    public void init(){
        if(tutorial_finish1.isPlaying()){
            tutorial_finish1.reset();
            tutorial_finish1 = MediaPlayer.create(this, R.raw.mainfinish);
        }
        if(tutorial_finish2.isPlaying()){
            tutorial_finish2.reset();
            tutorial_finish2 = MediaPlayer.create(this, R.raw.tutorial_finish);
        }
        if(tutorial[previous].isPlaying()) {
            tutorial[previous].reset();
            tutorial[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        //Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        //Sound_Manager.Service_address=11;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if(finish ==false) {
                Touch_lock = true;
                tutorial[previous].start();
            }
            else if(finish==true){
                Touch_lock = true;
                if(MainActivity.tutorial.equals("0")==true) {
                    tutorial_finish1.start();
                }
                else if(MainActivity.tutorial.equals("0")==false){
                    tutorial_finish2.start();
                }
            }
        }

        tutorial[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                tutorial[previous].reset();
                tutorial[previous] = MediaPlayer.create(Common_Tutorial_service.this, rawid[previous]);
                Touch_lock = false;
                WHclass.SoundCheck=false;
            }
        });

        tutorial_finish1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tutorial_finish1.reset();
                tutorial_finish1 = MediaPlayer.create(Common_Tutorial_service.this, R.raw.mainfinish);
                finish = false;
                Touch_lock = false;
                WHclass.SoundCheck=true;
            }
        });

        tutorial_finish2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tutorial_finish2.reset();
                tutorial_finish2 = MediaPlayer.create(Common_Tutorial_service.this, R.raw.tutorial_finish);
                finish = false;
                Touch_lock = false;
                WHclass.SoundCheck=true;
            }
        });

        return START_NOT_STICKY;
    }

}
