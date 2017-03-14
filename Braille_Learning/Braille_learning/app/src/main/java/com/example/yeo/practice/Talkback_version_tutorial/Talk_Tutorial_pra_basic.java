package com.example.yeo.practice.Talkback_version_tutorial;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.yeo.practice.Common_Tutorial_sound.Common_Tutorial_service;
import com.example.yeo.practice.Common_basic_practice_sound.Initial_service;
import com.example.yeo.practice.Common_braille_data.dot_initial;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.Common_sound.slied;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_practice;
import com.example.yeo.practice.Normal_version_menu.Menu_basic_practice;
import com.example.yeo.practice.Normal_version_tutorial.Tutorial_practice_basic_display;
import com.example.yeo.practice.Normal_version_tutorial.Tutorial_service;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.WHclass;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 여명 on 2017-02-28.
 */

public class Talk_Tutorial_pra_basic extends FragmentActivity {



    private TimerTask second; //타이머
    private final Handler handler = new Handler();
    Timer timer =null;

    int touch_check=0;  // 시간 초기화를 위한 변수
    Tutorial_practice_basic_display m;
    int newdrag, olddrag; //첫번째 손가락과 두번째 손가락의 x좌표를 저장할 변수
    int y1drag, y2drag; //첫번째 손가락과 두번째 손가락의 y좌표를 저장할 변수
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0; //문지르기 기능을 초기화 하기 위한 컨트롤 변수
    boolean click = true;
    boolean Speak_check=true;
    boolean[] click_check = new boolean[12]; // 점자를 모두 터치하였는지 확인하는 배열
    int check_timer=0;
    boolean lock=true;
    /*
    초성 연습, 모음연습, 종성연습, 숫자연습, 알파벳연습, 문장부호연습, 약자 및 약아 연습 클래스 선언
     */
    public static dot_initial Dot_initial;
    public static int n;
    public static boolean check = false;
    public static int reference; //나만의 단어장에 들어온 단어의 주소
    public static int reference_index; //나만의 단어장에 들어온 단어의 순서

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new Timer();
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );

        Dot_initial = new dot_initial();
        WHclass.touchevent = false;
        init();


        startService(new Intent(this, Initial_service.class));
        Timer_Start();
    }
    public void Timer_Stop(){
        if(timer != null){
            timer.cancel();
            timer= null;
            timer = new Timer();
        }
    }
    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                if(n==1)
                    first();
                else if(n==2)
                    click_check();
                else if(n==3)
                    click_check2();

            }
        };
        timer.schedule(second,0,1000); //1초의 딜레이시간
    }

    public void first(){
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                touch_check++;
                if(touch_check>4){
                    n=2;
                    lock=true;
                    touch_check=0;
                    speak(1);
                }
            }
        };
        handler.post(updater);
    }
    public void click_check(){
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<6; i++){
                    if(click_check[i]==true){
                        check_timer++;
                        if(check_timer==6){
                            n=3;
                            speak(2);
                            check_timer=0;
                            for(int j=0; j<6; j++){
                                click_check[j]=false;
                            }
                        }
                    }else{
                        check_timer=0;
                        break;
                    }
                }
            }
        };
        handler.post(updater);
    }
    public void click_check2(){
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                if(m.page ==1) {
                    touch_check++;
                    if (touch_check > 5) {
                        if (Speak_check == true) {
                            speak(3);
                            Speak_check = false;
                            touch_check=0;
                        }
                    }
                }
                for(int i=7; i<12; i++){
                    if(click_check[i]==true){
                        check_timer++;
                        if(check_timer==2){
                            speak(4);
                            n=4;
                            for(int j=7; j<12; j++){
                                click_check[j]=false;
                            }
                        }
                    }else
                        break;
                }
            }
        };
        handler.post(updater);
    }
    public void speak(int n){
        if(n==1) {
            WHclass.tutorial_progress = 23;
            startService(new Intent(this, Tutorial_service.class));
            lock = false;
        }else if(n==2){
            WHclass.tutorial_progress = 24;
            startService(new Intent(this, Tutorial_service.class));
        }else if(n==3){
            WHclass.tutorial_progress = 25;
            startService(new Intent(this, Tutorial_service.class));
        }else if(n==4){
            WHclass.tutorial_progress = 26;
            startService(new Intent(this, Tutorial_service.class));
        }

    }


    public void init(){
        m = new Tutorial_practice_basic_display(this); //화면을 출력하기 위한 클래스 선언
        m.setBackgroundColor(Color.rgb(22,26,44));
        setContentView(m);
        m.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                if(WHclass.touchevent==false) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_HOVER_ENTER:
                            startService(new Intent(Talk_Tutorial_pra_basic.this, Sound_Manager.class));
                            m.x = (int) event.getX(); // 현재 좌표의 x좌표 값을 저장
                            m.y = (int) event.getY(); // 현재 좌표의 y좌표 값을 저장

                            if ((m.x == 0) && (m.y == 0)) { //좌표 초기값으로 지정된 곳을 터치하면 반응을 없앰
                                break;
                            } else {
                    /*
                    자신이 터치한 지점의 좌표값의 점자가 돌출된 점자이면 남성의 음성으로 점자번호를 안내하면서 강한 진동 발생
                    자신이 터치한 지점의 좌표값의 점자가 비돌출된 점자이면 여성의 음성으로 점자번호를 안내함
                     */
                                if (m.x < m.w1 + m.bigcircle && m.x > m.w1 - m.bigcircle && m.y < m.h1 + m.bigcircle && m.y > m.h1 - m.bigcircle) {
                                    WHclass.number = 1;
                                    if (m.x < m.tw1 + m.bigcircle && m.x > m.tw1 - m.bigcircle && m.y < m.th1 + m.bigcircle && m.y > m.th1 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[0] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[0] = true;
                                    }
                                } //첫번째 칸 1번 점자
                                else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                                    WHclass.number = 2;
                                    if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[1] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[1] = true;
                                    }

                                } //첫번째 칸 2번 점자

                                else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                                    WHclass.number = 3;
                                    if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[2] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[2] = true;
                                    }

                                } //첫번째 칸 3번 점자

                                else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                                    WHclass.number = 4;
                                    if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[3] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[3] = true;
                                    }

                                } //첫번째 칸 4번 점자

                                else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                                    WHclass.number = 5;
                                    if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[4] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[4] = true;
                                    }

                                } //첫번째 칸 5번점자

                                else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                                    WHclass.number = 6;
                                    if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[5] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[5] = true;
                                    }
                                }// 첫번째 칸 6번 점자

                                else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                                    WHclass.number = 1;
                                    if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[6] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[6] = true;
                                    }
                                } // 두번째 칸 1번 점자

                                else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                                    WHclass.number = 2;
                                    if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[7] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[7] = true;
                                    }
                                }// 두번째 칸 2번 점자

                                else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                                    WHclass.number = 3;
                                    if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[8] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[8] = true;
                                    }
                                }// 두번째 칸 3번 점자

                                else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                                    WHclass.number = 4;
                                    if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[9] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[9] = true;
                                    }
                                }// 두번째 칸 4번 점자

                                else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                                    WHclass.number = 5;
                                    if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[10] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[10] = true;
                                    }
                                }// 두번째 칸 5번 점자

                                else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                                    WHclass.number = 6;
                                    if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                        click_check[11] = true;
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                        click_check[11] = true;
                                    }
                                }// 두번째 칸 6번 점자
                            }
                            m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                            break;
                        case MotionEvent.ACTION_HOVER_MOVE:
                            m.x = (int) event.getX(); // 첫번째 손가락이 터치하고 있는 지점의 x좌표 값을 저장
                            m.y = (int) event.getY(); // 첫번째 손가락이 터치하고 있는 지점의 y좌표 값을 저장


                /*
                자신이 터치하고 있는 지점의 점자가 돌출된 점자이면 남성의 음성으로 점자번호를 안내하면서 강한 진동 발생
                자신이 터치하고 있는 지점의 점자가 비돌출된 점자이면 여성의 음성으로 점자번호를 안내함
                 */
                            if (lock == false) {
                                if (click == true) {
                                    if (m.x < m.w1 + m.bigcircle && m.x > m.w1 - m.bigcircle && m.y < m.h1 + m.bigcircle && m.y > m.h1 - m.bigcircle) {
                                        WHclass.number = 1;
                                        if (result1 == 0) {
                                            if (m.x < m.tw1 + m.bigcircle && m.x > m.tw1 - m.bigcircle && m.y < m.th1 + m.bigcircle && m.y > m.th1 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[0] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[0] = true;
                                            }
                                            touch_init(1);
                                        }
                                    } //첫번쨰 칸의 1번 점자
                                    else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                                        WHclass.number = 2;
                                        if (result2 == 0) {
                                            if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[1] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[1] = true;
                                            }
                                            touch_init(2);
                                        }
                                    }//첫번쨰 칸의 2번 점자
                                    else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                                        WHclass.number = 3;
                                        if (result3 == 0) {
                                            if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[2] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[2] = true;
                                            }
                                            touch_init(3);
                                        }
                                    }//첫번쨰 칸의 3번 점자
                                    else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                                        WHclass.number = 4;
                                        if (result4 == 0) {
                                            if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[3] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[3] = true;
                                            }
                                            touch_init(4);
                                        }
                                    }//첫번쨰 칸의 4번 점자
                                    else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                                        WHclass.number = 5;
                                        if (result5 == 0) {
                                            if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[4] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[4] = true;
                                            }
                                            touch_init(5);
                                        }
                                    }//첫번쨰 칸의 5번 점자
                                    else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                                        WHclass.number = 6;
                                        if (result6 == 0) {
                                            if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[5] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[5] = true;
                                            }
                                            touch_init(6);
                                        }
                                    }//첫번쨰 칸의 6번 점자
                                    else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                                        WHclass.number = 1;
                                        if (result1 == 0) {
                                            if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[6] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[6] = true;
                                            }
                                            touch_init(1);
                                        }
                                    }//두번째 칸의 1번 점자
                                    else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                                        WHclass.number = 2;
                                        if (result2 == 0) {
                                            if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[7] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[7] = true;
                                            }
                                            touch_init(2);
                                        }
                                    }//두번째 칸의 2번 점자
                                    else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                                        WHclass.number = 3;
                                        if (result3 == 0) {
                                            if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[8] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[8] = true;
                                            }
                                            touch_init(3);
                                        }
                                    }//두번째 칸의 3번 점자
                                    else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                                        WHclass.number = 4;
                                        if (result4 == 0) {
                                            if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[9] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[9] = true;
                                            }
                                            touch_init(4);
                                        }
                                    }//두번째 칸의 4번 점자
                                    else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                                        WHclass.number = 5;
                                        if (result5 == 0) {
                                            if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[10] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[10] = true;
                                            }
                                            touch_init(5);
                                        }
                                    }//두번째 칸의 5번 점자
                                    else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                                        WHclass.number = 6;
                                        if (result6 == 0) {
                                            if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Strong_vibe);
                                                click_check[11] = true;
                                            } else {
                                                WHclass.target = false;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                click_check[11] = true;
                                            }
                                            touch_init(6);
                                        }
                                    }//두번째 칸의 6번 점자
                                    else {// 그외 지점을 터치할 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                                        touch_init(0);
                                        WHclass.number = 0;
                                    }

                                    switch (m.page) {
                                        case 0: //첫번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                            if (m.x > m.width2 + m.bigcircle && m.x < m.width2 + (m.bigcircle * 2) && m.y > m.height1 - (m.bigcircle * 2)) {
                                                WHclass.number = 7;
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                                touch_init(0);
                                            } else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle && m.x < m.width2 + (m.bigcircle * 2)) {
                                                WHclass.number = 7;
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                                touch_init(0);
                                            }
                                            break;
                                        case 1: //두번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                            if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle && m.y > m.height1 - (m.bigcircle * 2)) {
                                                WHclass.number = 8;
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                                touch_init(0);
                                            } else if (m.x > m.width6 + m.bigcircle && m.x < m.width6 + (m.bigcircle * 2) && m.y > m.height1 - (m.bigcircle * 2)) {
                                                WHclass.number = 7;
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                                touch_init(0);
                                            } else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle && m.x < m.width6 + (m.bigcircle * 2)) {
                                                WHclass.number = 7;
                                                WHclass.target = true;
                                                startService(new Intent(Talk_Tutorial_pra_basic.this, Number.class));
                                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                                touch_init(0);
                                            }
                                            break;
                                    }
                                }
                            }
                            m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                            break;
                        case MotionEvent.ACTION_HOVER_EXIT:
                            if (click == false) {
                                click = true;
                            }
                            break;
                    }
                }
                return false;
            }
        });


    }
    @Override
    public void onRestart(){
        super.onRestart();
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        if (WHclass.touchevent == true) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:  // 두번째 손가락을 떼었을 경우
                    newdrag = (int) event.getX(); //두번째 손가락이 화면에서 떨어질 때의 x좌표값을 저장
                    y2drag = (int) event.getY(); //두번째 손가락이 화면에서 떨어질 떄의 y좌표값을 저장
                    if (lock == false) {
                        if (olddrag - newdrag > WHclass.Drag_space) {//손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그 하였을 경우
                            if(m.page<1) {
                                slied.slied = Menu_info.next;
                                startService(new Intent(this, slied.class));
                                ++m.page;
                                Braille_short_display.page = 14;
                                startService(new Intent(this, Initial_service.class));
                                m.MyView2_init();
                                m.invalidate();
                            }
                        } else if (y2drag - y1drag > WHclass.Drag_space) { //손가락 2개를 이용하여 아래로 드래그 하였을 때 현재 점자정보를 다시 들음
                            startService(new Intent(this, Initial_service.class));
                        } else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 하단에서 상단으로 드래그하였을 때 점자 정보를 초기화하면서 현재화면을 종료함
                            onBackPressed();
                        }
                    }
                case MotionEvent.ACTION_DOWN: // 두번째 손가락이 화면에 터치된 경우
                    olddrag = (int) event.getX(); // 두번째 손가락이 화면에 터치된 지점의 x좌표 값 저장
                    y1drag = (int) event.getY(); // 두번째 손가락이 화면에 터치된 지점의 y좌표 값 저장
                    break;
            }
        }
        return true;
    }


    public void touch_init(int coordinate){ // 문지르기 기능을 위한 컨트롤 변수들 초기화
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;
        result6=0;

        switch(coordinate){
            case 1:
                result1=1;
                break;
            case 2:
                result2=1;
                break;
            case 3:
                result3=1;
                break;
            case 4:
                result4=1;
                break;
            case 5:
                result5=1;
                break;
            case 6:
                result6=1;
                break;
            default:
                break;

        }
    }
    @Override
    public void onBackPressed() { //뒤로가기 키를 눌렀을 경우, 점자의 종류에따라 음성을 출력하면서 변수들 초기화
        if(MainActivity.basic.equals("0")==true) {
            MainActivity.basic="1";
            MainActivity.PrefEditor2.putString("BASIC","1");
            MainActivity.PrefEditor2.commit();
        }
        m.page = 0;
        m.MyView2_init();
        Braille_short_display.page = 0;
        WHclass.tutorial_progress = 27;
        startService(new Intent(this, Tutorial_service.class));
        Intent intent = new Intent(Talk_Tutorial_pra_basic.this, Menu_basic_practice.class);
        startActivityForResult(intent, 0);
    }
}

