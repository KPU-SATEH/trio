package com.example.yeo.practice.Common_basic_practice_sound;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_alphabet;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_display;
import com.example.yeo.practice.WHclass;

public class alphabet_service extends Service {
    /*
     알파벳 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Initial_service";
    MediaPlayer roma,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,fortis,fa,fb,fc,fd,fe,ff,fg,fh,fi,fj,fk,fl,fm,fn,fo,fp,fq,fr,fs,ft,fu,fv,fw,fx,fy,fz, alphabetfinish;
    MediaPlayer alphabet[]; //음성파일을 저장하는 배열 변수
    int rawid[];  //음성파일의 주소를 저장하는 배열 변수
    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public alphabet_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        alphabetfinish = MediaPlayer.create(this, R.raw.alphabetfinish);
        alphabetfinish.setLooping(false);
        alphabet = new MediaPlayer[] {roma,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,fortis,fa,fb,fc,fd,fe,ff,fg,fh,fi,fj,fk,fl,fm,fn,fo,fp,fq,fr,fs,ft,fu,fv,fw,fx,fy,fz};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.alphabet_roma,R.raw.alphabet_a,R.raw.alphabet_b,R.raw.alphabet_c,R.raw.alphabet_d,
                R.raw.alphabet_e,R.raw.alphabet_f,R.raw.alphabet_g,R.raw.alphabet_h, R.raw.alphabet_i,R.raw.alphabet_j,
                R.raw.alphabet_k,R.raw.alphabet_l,R.raw.alphabet_m,R.raw.alphabet_n,R.raw.alphabet_o,R.raw.alphabet_p,
                R.raw.alphabet_q,R.raw.alphabet_r,R.raw.alphabet_s,R.raw.alphabet_t,R.raw.alphabet_u,R.raw.alphabet_v,
                R.raw.alphabet_w,R.raw.alphabet_x,R.raw.alphabet_y,R.raw.alphabet_z,R.raw.alphabet_capital,R.raw.alphabet_capital_a,
                R.raw.alphabet_capital_b,R.raw.alphabet_capital_c,R.raw.alphabet_capital_d,R.raw.alphabet_capital_e,
                R.raw.alphabet_capital_f,R.raw.alphabet_capital_g,R.raw.alphabet_capital_h,R.raw.alphabet_capital_i,
                R.raw.alphabet_capital_j,R.raw.alphabet_capital_k,R.raw.alphabet_capital_l,R.raw.alphabet_capital_m,
                R.raw.alphabet_capital_n,R.raw.alphabet_capital_o,R.raw.alphabet_capital_p,R.raw.alphabet_capital_q,
                R.raw.alphabet_capital_r,R.raw.alphabet_capital_s,R.raw.alphabet_capital_t,R.raw.alphabet_capital_u,
                R.raw.alphabet_capital_v,R.raw.alphabet_capital_w,R.raw.alphabet_capital_x,R.raw.alphabet_capital_y,R.raw.alphabet_capital_z};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_alphabet.alphabet_count; i++){
            alphabet[i] = MediaPlayer.create(this, rawid[i]);
            alphabet[i].setLooping(false);
        }
    }

    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(alphabetfinish.isPlaying()){
            alphabetfinish.reset();
            alphabetfinish = MediaPlayer.create(this, R.raw.alphabetfinish);
        }
        if(alphabet[previous].isPlaying()) {
            alphabet[previous].reset();
            alphabet[previous] = MediaPlayer.create(this, rawid[previous]);
        }
        Sound_Manager.stop=false;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Sound_Manager.Service_address=15;

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
                    alphabet[previous].start();
                } else {
                    alphabetfinish.start();
                    finish = false;
                }
            } else if (WHclass.Braiile_type == 1) {
                if (finish == false) {
                    if (WHclass.sel == Menu_info.MENU_NOTE)
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    else
                        previous = Talk_Braille_short_display.page;
                    alphabet[previous].start();
                } else {
                    alphabetfinish.start();
                    finish = false;
                }
            }
        }
        alphabet[previous].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                alphabet[previous].reset();
                alphabet[previous] = MediaPlayer.create(alphabet_service.this, rawid[previous]);
            }
        });
        alphabetfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                alphabetfinish.reset();
                alphabetfinish = MediaPlayer.create(alphabet_service.this, R.raw.alphabetfinish);
            }
        });

        return START_NOT_STICKY;
    }
}
