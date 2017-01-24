package com.example.yeo.practice.Common_basic_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_final;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_practice;
import com.example.yeo.practice.WHclass;

public class Final_service extends Service {
    /*
     종성 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Initial_service";
    MediaPlayer giyeok,nieun,digeud,nieul,mieum,bieub,siot,eng,zieut,chieut,kieuk,tieut,pieup,hieut;
    MediaPlayer finalfinish;
    MediaPlayer Final[];// 선언된 음성 변수들을 배열 변수에 저장
    int rawid[];// 음성파일의 id 주소를 배열변수에 저장

    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;
    public Final_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Final = new MediaPlayer[]{giyeok,nieun,digeud,nieul,mieum,bieub,siot,eng,zieut,chieut,kieuk,tieut,pieup,hieut};
        // 선언된 음성 변수들을 배열 변수에 저장

        finalfinish = MediaPlayer.create(this, R.raw.finalfinish);

        rawid = new int[]{R.raw.final_giyeok,R.raw.final_nieun,R.raw.final_digeud,R.raw.final_nieul,R.raw.final_mieum,R.raw.final_bieub,R.raw.final_siot
                ,R.raw.final_eng,R.raw.final_zieut,R.raw.final_chieut,R.raw.final_kieuk,R.raw.final_tieut,R.raw.final_pieup,R.raw.final_hieut};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_final.final_count; i++){
            Final[i] = MediaPlayer.create(this, rawid[i]);
            Final[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(finalfinish.isPlaying()){
            finalfinish.reset();
            finalfinish = MediaPlayer.create(this, R.raw.finalfinish);
        }
        if(Final[previous].isPlaying()) {
            Final[previous].reset();
            Final[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=13;

        if(Sound_Manager.stop==true)
            init();
        else {
            if (WHclass.Braiile_type == 2) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Braille_short_display.page;
                    Final[previous].start();
                } else {
                    finalfinish.start();
                    finish = false;
                }
            } else if (WHclass.Braiile_type == 1) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    Final[previous].start();
                } else {
                    finalfinish.start();
                    finish = false;
                }
            }
        }

        finalfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finalfinish.reset();
                finalfinish = MediaPlayer.create(Final_service.this, R.raw.finalfinish);
            }
        });

        return START_NOT_STICKY;
    }
}
