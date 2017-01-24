package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Common_braille_data.dot_quiz_number;
import com.example.yeo.practice.R;

/*
숫자 퀴즈에서 출력되는 음성파일을 관리하는 서비스 클래스
 */

public class quiz_number_service extends Service {
    private static final String TAG = "quiz_number_service";

    MediaPlayer num_sign,zero,one,two,three,four,five,six,seven,eight,nine,ten,twofive,fourseven,sixeight,ninenine;
    MediaPlayer number[]; //음성파일을 저장하는 배열 변수
    int rawid[];  //음성파일의 주소를 저장하는 배열 변수
    static boolean finish = false; //점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public quiz_number_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        number = new MediaPlayer[] {num_sign,zero,one,two,three,four,five,six,seven,eight,nine,ten,twofive,fourseven,sixeight,ninenine};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.num_sign0,R.raw.zero0,R.raw.one0,R.raw.two0,R.raw.three0,R.raw.four0,R.raw.five0,R.raw.six0,R.raw.seven0,R.raw.eight0,R.raw.nine0,
                R.raw.ten0,R.raw.twofive0,R.raw.fourseven0,R.raw.sixeight0,R.raw.ninenine0};
        // 음성파일의 id 주소를 배열변수에 저장



        for(int i = 0; i< dot_quiz_number.numbercount; i++){

            number[i] = MediaPlayer.create(this, rawid[i]);
            number[i].setLooping(false);
        }
    }
    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(number[previous].isPlaying()) {
            number[previous].reset();
            number[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
/*
        if(finish == false) {
            if (progress == false) {
                progress = true;
                previous = quiz_number.page;
            } else if (progress == true) {
                init();
                previous = quiz_number.page;
            }
            number[quiz_number.page].start();
        }
        else {
            init();
            finish = false;
        }*/
        return START_NOT_STICKY;
    }
}
