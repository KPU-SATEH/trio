package com.example.yeo.practice.Common_basic_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_num;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_practice;
import com.example.yeo.practice.WHclass;


public class Num_service extends Service {
    /*
     숫자 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Num_service";
    MediaPlayer ns, zero0, one1, two2, three3, four4, five5, six6, seven7, eight8, nine9, ten10, twofive25, fourseven47, sixeight68, ninenine99;
    MediaPlayer numfinish;
    MediaPlayer Num[]; // 선언된 음성 변수들을 배열 변수에 저장
    int rawid[]; // 음성파일의 id 주소를 배열변수에 저장
    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;
    public Num_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Num = new MediaPlayer[] {ns,zero0,one1,two2,three3,four4,five5,six6,seven7,eight8,nine9,ten10,twofive25,fourseven47,sixeight68,ninenine99};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.num_sign,R.raw.zero,R.raw.one,R.raw.two,R.raw.three,R.raw.four,R.raw.five,R.raw.six,R.raw.seven,R.raw.eight,R.raw.nine,R.raw.ten
                ,R.raw.twofive,R.raw.fourseven,R.raw.sixeight,R.raw.ninenine};
        // 음성파일의 id 주소를 배열변수에 저장


        numfinish = MediaPlayer.create(this,R.raw.numfinish);
        numfinish.setLooping(false);

        for(int i = 0; i< dot_num.num_count; i++){
            Num[i] = MediaPlayer.create(this, rawid[i]);
            Num[i].setLooping(false);
        }
    }

    public void init(){
        if(numfinish.isPlaying()){
            numfinish.reset();
            numfinish = MediaPlayer.create(this, R.raw.initfinish);
        }
        if(Num[previous].isPlaying()) {
            Num[previous].reset();
            Num[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=14;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if (WHclass.Braiile_type == 2) { //일반버전
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Braille_short_display.page;
                    Num[previous].start();
                }
                else {
                    numfinish.start();
                    finish = false;
                }
            }
            else if (WHclass.Braiile_type == 1) { //시각장애인버전
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    Num[previous].start();
                }
                else {
                    numfinish.start();
                    finish = false;
                }
            }
        }

        numfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                numfinish.reset();
                numfinish = MediaPlayer.create(Num_service.this, R.raw.numfinish);
            }
        });
        return START_NOT_STICKY;
    }

}