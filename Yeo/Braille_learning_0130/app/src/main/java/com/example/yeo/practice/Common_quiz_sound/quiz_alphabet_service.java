package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_quiz_alphabet;
import com.example.yeo.practice.R;


public class quiz_alphabet_service extends Service {
    private static final String TAG = "quiz_alphabet_service";

/*
알파벳 퀴즈에서 출력되는 음성파일을 관리하는 서비스 클래스
 */


    MediaPlayer roma,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,alphabet_fortis,fortis_a,fortis_b,fortis_c,fortis_d,
            fortis_e,fortis_f,fortis_g,fortis_h,fortis_i,fortis_j,fortis_k,fortis_l,fortis_m,fortis_n,
            fortis_o,fortis_p,fortis_q,fortis_r,fortis_s,fortis_t,fortis_u,fortis_v,fortis_w,fortis_x,fortis_y,fortis_z;
    MediaPlayer alphabet[]; //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    static boolean finish = false;  //점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public quiz_alphabet_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        alphabet = new MediaPlayer[] {roma,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,alphabet_fortis,fortis_a,fortis_b,fortis_c,fortis_d,
                fortis_e,fortis_f,fortis_g,fortis_h,fortis_i,fortis_j,fortis_k,fortis_l,fortis_m,fortis_n,
                fortis_o,fortis_p,fortis_q,fortis_r,fortis_s,fortis_t,fortis_u,fortis_v,fortis_w,fortis_x,fortis_y,fortis_z};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.roma,R.raw.alphabet_a0,R.raw.alphabet_b0,R.raw.alphabet_c0,R.raw.alphabet_d0,R.raw.alphabet_e0,R.raw.alphabet_f0,R.raw.alphabet_g0,R.raw.alphabet_h0,R.raw.alphabet_i0,R.raw.alphabet_j0,
                R.raw.alphabet_k0,R.raw.alphabet_l0,R.raw.alphabet_m0,R.raw.alphabet_n0,R.raw.alphabet_o0,R.raw.alphabet_p0,R.raw.alphabet_q0,R.raw.alphabet_r0,R.raw.alphabet_s0,R.raw.alphabet_t0,R.raw.alphabet_u0,R.raw.alphabet_v0
                ,R.raw.alphabet_w0,R.raw.alphabet_x0,R.raw.alphabet_y0,R.raw.alphabet_z0, R.raw.alphabet_fortis0, R.raw.alphabet_fortis_a0
                , R.raw.alphabet_fortis_b0, R.raw.alphabet_fortis_c0, R.raw.alphabet_fortis_d0, R.raw.alphabet_fortis_e0, R.raw.alphabet_fortis_f0
                , R.raw.alphabet_fortis_g0, R.raw.alphabet_fortis_h0, R.raw.alphabet_fortis_i0, R.raw.alphabet_fortis_j0, R.raw.alphabet_fortis_k0
                , R.raw.alphabet_fortis_l0, R.raw.alphabet_fortis_m0, R.raw.alphabet_fortis_n0, R.raw.alphabet_fortis_o0, R.raw.alphabet_fortis_p0
                , R.raw.alphabet_fortis_q0, R.raw.alphabet_fortis_r0, R.raw.alphabet_fortis_s0, R.raw.alphabet_fortis_t0, R.raw.alphabet_fortis_u0
                , R.raw.alphabet_fortis_v0, R.raw.alphabet_fortis_w0, R.raw.alphabet_fortis_x0, R.raw.alphabet_fortis_y0, R.raw.alphabet_fortis_z0};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_quiz_alphabet.alphabetcount; i++){

            alphabet[i] = MediaPlayer.create(this, rawid[i]);
            alphabet[i].setLooping(false);
        }
    }
    public void init(){  //사용한 음성파일을 재 설정해주는 함수
        if(alphabet[previous].isPlaying()) {
            alphabet[previous].reset();
            alphabet[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
    /*    if(finish == false) {
            if (progress == false) {
                progress = true;
                previous = quiz_alphabet.page;
            } else if (progress == true) {
                init();
                previous = quiz_alphabet.page;
            }
            alphabet[quiz_alphabet.page].start();
        }
        else {
            init();
            finish = false;
        }*/
        return START_NOT_STICKY;
    }
}