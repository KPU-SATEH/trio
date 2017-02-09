package com.example.yeo.practice.Common_basic_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_vowel;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_practice;
import com.example.yeo.practice.WHclass;


public class Vowel_service extends Service {
    /*
     숫자 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Vowel_service";
    MediaPlayer a, ae, ja,  eo, e, yeo, je, o, wa, oi, jo, u, ueo, ju,ei,i, ij, jae,wae, we, wi ;
    MediaPlayer vowelfinish;
    MediaPlayer vowel[];// 선언된 음성 변수들을 배열 변수에 저장
    int rawid[];// 음성파일의 id 주소를 배열변수에 저장

    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public Vowel_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        vowelfinish = MediaPlayer.create(this, R.raw.vowelfinish);
        vowelfinish.setLooping(false);

        vowel = new MediaPlayer[] {a, ae, ja,  eo, e, yeo, je, o, wa, oi, jo, u, ueo, ju,ei,i, ij, jae,wae, we, wi};
        // 선언된 음성 변수들을 배열 변수에 저장


        rawid = new int[]{R.raw.vowel_a,R.raw.vowel_ae,R.raw.vowel_ja,R.raw.vowel_eo,R.raw.vowel_e,R.raw.vowel_yeo,R.raw.vowel_je
                ,R.raw.vowel_o,R.raw.vowel_wa,R.raw.vowel_oi,R.raw.vowel_jo,R.raw.vowel_u,R.raw.vowel_ueo,
                R.raw.vowel_ju,R.raw.vowel_ei,R.raw.vowel_ij,R.raw.vowel_i,R.raw.vowel_jae,R.raw.vowel_wae,R.raw.vowel_we,R.raw.vowel_wi,};
        // 음성파일의 id 주소를 배열변수에 저장



        for(int i = 0; i< dot_vowel.vowel_count; i++){
            vowel[i] = MediaPlayer.create(this, rawid[i]);
            vowel[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(vowel[previous].isPlaying()) {
            vowel[previous].reset();
            vowel[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        if(vowelfinish.isPlaying()){
            vowelfinish.reset();
            vowelfinish = MediaPlayer.create(this, R.raw.vowelfinish);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=12;
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
                    vowel[previous].start();
                } else {
                    vowelfinish.start();
                    finish = false;
                }
            } else if (WHclass.Braiile_type == 1) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    vowel[previous].start();
                } else {
                    vowelfinish.start();
                    finish = false;
                }
            }
        }
        vowelfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vowelfinish.reset();
                vowelfinish = MediaPlayer.create(Vowel_service.this, R.raw.vowelfinish);
            }
        });
        return START_NOT_STICKY;
    }
}