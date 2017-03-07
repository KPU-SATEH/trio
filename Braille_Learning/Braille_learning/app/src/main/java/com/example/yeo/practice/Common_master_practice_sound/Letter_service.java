package com.example.yeo.practice.Common_master_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_basic_practice_sound.Num_service;
import com.example.yeo.practice.Common_braille_data.dot_letter;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_long_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_long_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_long_practice;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_long_display;
import com.example.yeo.practice.WHclass;


public class Letter_service extends Service {
/*
글자 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
 */
    private static final String TAG = "Menu_basic_service";
    MediaPlayer letterfinish, ham, kal, ang, hwa, dae, han, gang, nam, s, il, jak, eom, bba, sam, gu, bak, su, dok, ri, gi, si, gak, jang, hak, gyo, sil,
            big, ryun, gue, min, kuk,chui;
    MediaPlayer Letter[] ; //음성파일을 저장하는 배열 변수
    int setting[] = new int[dot_letter.lettercount];
    int rawid[];//음성파일의 주소를 저장하는 배열 변수
    public static boolean finish = false; //점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public Letter_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        letterfinish=MediaPlayer.create(this, R.raw.letterfinish);

        Letter = new MediaPlayer[] {ham,kal,ang,hwa,dae,han,gang,nam,s,il,jak,eom,bba,sam,gu,bak,su,dok,ri,gi,si,gak,jang,hak,
                gyo,sil,chui,big,ryun,gue,min,kuk};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] { R.raw.letter_ham,R.raw.letter_kal,R.raw.letter_ang,R.raw.letter_hwa,R.raw.letter_dae,R.raw.letter_han,
            R.raw.letter_gang,R.raw.letter_nam,R.raw.letter_s,R.raw.letter_il,R.raw.letter_jak,R.raw.letter_eom,R.raw.letter_bba
                ,R.raw.letter_sam,R.raw.letter_gu,R.raw.letter_bak,R.raw.letter_su,R.raw.letter_dok,R.raw.letter_ri,R.raw.letter_gi,
                R.raw.letter_si,R.raw.letter_gak,R.raw.letter_jang,R.raw.letter_hak,R.raw.letter_gyo,R.raw.letter_sil,R.raw.letter_chui,
                R.raw.letter_big,R.raw.letter_ryun,R.raw.letter_gue,R.raw.letter_min,R.raw.letter_kuk};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_letter.lettercount; i++){
           setting[i] = 0;
        }


    }
    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(letterfinish.isPlaying()){
            letterfinish.reset();
            letterfinish = MediaPlayer.create(this,R.raw.letterfinish);
        }
        if(Letter[previous].isPlaying()) {
            Letter[previous].reset();
            Letter[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }

    public void setting(){
        if (setting[previous] == 0) {
            Letter[previous] =
                    MediaPlayer.create(this, rawid[previous]);
            Letter[previous].setLooping(false);
            setting[previous] = 1;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=21;
        if(Sound_Manager.stop==true)
            init();
        else {

            if (WHclass.Braiile_type == 2) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                    else
                        previous = Braille_long_display.page;
                    setting();
                    Letter[previous].start();
                }
                else {
                    letterfinish.start();
                    finish = false;
                }
            } else if (WHclass.Braiile_type == 1) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_long_display.page;
                    setting();
                    Letter[previous].start();

                } else {
                    letterfinish.start();
                    finish = false;
                }
            }
        }
        Letter[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Letter[previous].reset();
                Letter[previous] = MediaPlayer.create(Letter_service.this, rawid[previous]);
            }
        });

        letterfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                letterfinish.reset();
                letterfinish = MediaPlayer.create(Letter_service.this, R.raw.letterfinish);
            }
        });
        return START_NOT_STICKY;
    }
}
