package com.example.yeo.practice.Common_basic_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_abbreviation;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.WHclass;


public class abbreviation_service extends Service {
    /*
     약자 및 약어 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "abbreviation_service";
    MediaPlayer ga, na, da, ma, ba, sa, ja, ka, ta, pa, ha, uk, un,wool,ul, yun, yul, young, ok, on, yong, woon, woo, eun, eul, in, fortis_siot, gut,so,but,and,so2,bytheway,and2,so3;
    MediaPlayer Abbreviation[]; //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    MediaPlayer abbreviationfinish; //점자 학습의 종료를 알리는 변수

    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        abbreviationfinish = MediaPlayer.create(this, R.raw.abbreviationfinish);
        abbreviationfinish.setLooping(false);

        Abbreviation = new MediaPlayer[] {ga, na, da, ma, ba, sa, ja, ka, ta, pa, ha, uk, un,wool,ul, yun, yul, young, ok, on, yong, woon, woo, eun, eul, in, fortis_siot, gut,so,but,and,so2,bytheway,and2,so3};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[]{R.raw.abbreviation_ga,R.raw.abbreviation_na,R.raw.abbreviation_da,R.raw.abbreviation_ma,R.raw.abbreviation_ba,
                R.raw.abbreviation_sa,R.raw.abbreviation_ja,R.raw.abbreviation_ka,R.raw.abbreviation_ta,R.raw.abbreviation_pa,
                R.raw.abbreviation_ha,R.raw.abbreviation_uk,R.raw.abbreviation_un,R.raw.abbreviation_ul,R.raw.abbreviation_yun,
                R.raw.abbreviation_yul,R.raw.abbreviation_young,R.raw.abbreviation_ok,R.raw.abbreviation_on,R.raw.abbreviation_ong,
                R.raw.abbreviation_woon,R.raw.abbreviation_wool,R.raw.abbreviation_eun,R.raw.abbreviation_eul,
                R.raw.abbreviation_in,R.raw.abbreviation_fortis_siot,R.raw.abbreviation_gut,R.raw.abbreviation_so,R.raw.abbreviation_but,
                R.raw.abbreviation_and,R.raw.abbreviation_therefore,R.raw.abbreviation_well,R.raw.abbreviation_then,R.raw.abbreviation_accordingly};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_abbreviation.abbreviation_count; i++){
            Abbreviation[i] = MediaPlayer.create(this, rawid[i]);
            Abbreviation[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(Abbreviation[previous].isPlaying()) {
            Abbreviation[previous].reset();
            Abbreviation[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        if(abbreviationfinish.isPlaying()){
            abbreviationfinish.reset();
            abbreviationfinish = MediaPlayer.create(this, R.raw.abbreviationfinish);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=17;
        if(Sound_Manager.stop==true)
            init();
        else {
            init();
            if (WHclass.Braiile_type == 2) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Braille_short_display.page;
                    Abbreviation[previous].start();
                } else {
                    abbreviationfinish.start();
                    finish = false;
                }
            } else if (WHclass.Braiile_type == 1) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    Abbreviation[previous].start();
                } else {
                    abbreviationfinish.start();
                    finish = false;
                }
            }
        }
        Abbreviation[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Abbreviation[previous].reset();
                Abbreviation[previous] = MediaPlayer.create(abbreviation_service.this, rawid[previous]);
            }
        });
        abbreviationfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                abbreviationfinish.reset();
                abbreviationfinish = MediaPlayer.create(abbreviation_service.this, R.raw.abbreviationfinish);
            }
        });
        return START_NOT_STICKY;
    }
}