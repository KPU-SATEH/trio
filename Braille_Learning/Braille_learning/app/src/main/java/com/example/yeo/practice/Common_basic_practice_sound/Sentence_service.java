package com.example.yeo.practice.Common_basic_practice_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_sentence;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.WHclass;


public class Sentence_service extends Service {
    /*
     문장부호 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Sentence_service";
    MediaPlayer ssangopen, ssangclose, gualhoopen, gualhoclose, surprise, finish_dot, rest_dot, plus, minus, multiple, divide, equal, sangopen, sangclose, wave, twodot, sweat, billiboard;
    MediaPlayer sentencefinish;
    MediaPlayer sentence[];// 선언된 음성 변수들을 배열 변수에 저장
    int rawid[];// 음성파일의 id 주소를 배열변수에 저장

    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public Sentence_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){

        sentencefinish = MediaPlayer.create(this, R.raw.sentence_finish);
        sentencefinish.setLooping(false);

        sentence = new MediaPlayer[]{ssangopen, ssangclose, gualhoopen, gualhoclose, surprise, finish_dot, rest_dot, plus, minus, multiple,
                divide, equal, sangopen, sangclose, wave, twodot, sweat, billiboard};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid =new int[]{R.raw.sentence_d_quotation_open,R.raw.sentence_d_quotation_close,R.raw.sentence_parenthesis_open,
                R.raw.sentence_parenthesis_close,R.raw.sentence_exclamation,R.raw.sentence_period,R.raw.sentence_comma,R.raw.sentence_add,
                R.raw.sentence_sub,R.raw.sentence_mul,R.raw.sentence_div,R.raw.sentence_same,R.raw.sentence_s_quotation_open,
                R.raw.sentence_s_quotation_close, R.raw.sentence_swung,R.raw.sentence_colon,
                R.raw.sentence_s_colon,R.raw.sentence_reference};
        // 음성파일의 id 주소를 배열변수에 저장


        for(int i = 0; i< dot_sentence.sentence_count; i++){
            sentence[i] = MediaPlayer.create(this, rawid[i]);
            sentence[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(sentence[previous].isPlaying()) {
            sentence[previous].reset();
            sentence[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        if(sentencefinish.isPlaying()){
            sentencefinish.reset();
            sentencefinish = MediaPlayer.create(this, R.raw.sentence_finish);
        }
        Sound_Manager.stop = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=16;
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
                    sentence[previous].start();
                } else {
                    sentencefinish.start();
                    finish = false;
                }
            } else if (WHclass.Braiile_type == 1) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    sentence[previous].start();
                } else {
                    sentencefinish.start();
                    finish = false;
                }
            }
        }

        sentence[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sentence[previous].reset();
                sentence[previous] = MediaPlayer.create(Sentence_service.this, rawid[previous]);
            }
        });

        sentencefinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sentencefinish.reset();
                sentencefinish = MediaPlayer.create(Sentence_service.this, R.raw.sentence_finish);
            }
        });
        return START_NOT_STICKY;
    }
}