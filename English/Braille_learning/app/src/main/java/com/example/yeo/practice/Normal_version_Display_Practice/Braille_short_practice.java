package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_alphabet;
import com.example.yeo.practice.Common_braille_data.dot_number;
import com.example.yeo.practice.Common_braille_data.dot_sentence;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.WHclass;


public class Braille_short_practice extends FragmentActivity {
/*
3칸 이하의 점자 학습을 진행하는 클래스
 */



    int finger_x[] = new int[3];
    int finger_y[] = new int[3];

    Braille_short_display m;
    int newdrag, olddrag; //첫번째 손가락과 두번째 손가락의 x좌표를 저장할 변수
    int y1drag, y2drag; //첫번째 손가락과 두번째 손가락의 y좌표를 저장할 변수
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0; //문지르기 기능을 초기화 하기 위한 컨트롤 변수
    boolean click = true;


    boolean lock=false;
    /*
    초성 연습, 모음연습, 종성연습, 숫자연습, 알파벳연습, 문장부호연습, 약자 및 약아 연습 클래스 선언
     */

    public static dot_alphabet Dot_alphabet;
    public static dot_number Dot_number;
    public static dot_sentence Dot_sentence;
    String array[] = new String[3]; //데이터베이스에 행렬에 대한 정보를 담기 위해 행렬정보를 담는 배열 변수
    public static int reference; //나만의 단어장에 들어온 단어의 주소
    public static int reference_index; //나만의 단어장에 들어온 단어의 순서
    int previous_reference=0; //나만의 단어장에서 이전에 출력됬던 음성을 초기화 시키기 위한 변수
    static public boolean pre_reference = false; //이전에 음성이 출력되었는지를 체크하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );


        //학습하려는 점자에 따라 클래스를 선언함
        switch(WHclass.sel) {
            case 4: //숫자연습
                Dot_number = new dot_number();
                break;
            case 5:// 알파벳연습
                Dot_alphabet = new dot_alphabet();
                break;
            case 6: //문장부호 연습
                Dot_sentence = new dot_sentence();
                break;

        }

        m = new Braille_short_display(this); //화면을 출력하기 위한 클래스 선언
        m.setBackgroundColor(Color.rgb(22,26,44));
        setContentView(m);

        MainActivity.TTS.speak(m.get_TTs_text(), TextToSpeech.QUEUE_FLUSH, null);

    }

    public void update(){ //1초동안 화면에 연속으로 2번의 터치가 발생됬을 경우 데이터베이스로 현재 단어정보를 전송함
                String result ="";
                array[0]="";
                array[1]="";
                array[2]="";
        switch(m.dot_count){
            case 1: //한 칸 일때
                if(WHclass.sel==Menu_info.MENU_NOTE) {
                    MainActivity.basic_braille_db.delete(MainActivity.basic_braille_db.basic_db_manager.getId(MainActivity.basic_braille_db.basic_db_manager.My_Note_page));
                    result = MainActivity.basic_braille_db.getResult();
                    if(MainActivity.basic_braille_db.basic_db_manager.size_count==0)
                        onBackPressed();
                    m.MyView2_init();
                    m.invalidate();
                    MyNote_Start_service();
                }
                else {
                    for (int i = 0; i < 3; i++) {
                        for(int j=0; j<m.dot_count*2 ; j++){
                            array[i] = array[i] + Integer.toString(m.text_1[i][j]); // 3개의 배열에 1행 2행 3행을 집어넣음
                        }
                    }
                    result = MainActivity.basic_braille_db.insert(m.dot_count, m.textname_1, array[0], array[1], array[2], Menu_info.MENU_INFO, m.page);  //데이터베이스에 입력하고, 성공문자를 돌려받음
                    if(result.equals("성공")){
                        MainActivity.TTS.speak("Success", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.equals("실패")){
                        MainActivity.TTS.speak("Fail", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
            case 2://두 칸 일때
                if(WHclass.sel==Menu_info.MENU_NOTE) {
                    MainActivity.basic_braille_db.delete(MainActivity.basic_braille_db.basic_db_manager.getId(MainActivity.basic_braille_db.basic_db_manager.My_Note_page));
                    result = MainActivity.basic_braille_db.getResult();
                    m.MyView2_init();
                    m.invalidate();}
                else {
                    for (int i = 0; i < 3; i++) {
                        for(int j=0; j<m.dot_count*2 ; j++){
                            array[i] = array[i] + Integer.toString(m.text_2[i][j]); // 3개의 배열에 1행 2행 3행을 집어넣음
                        }
                    }
                    result = MainActivity.basic_braille_db.insert(m.dot_count, m.textname_2, array[0], array[1], array[2], Menu_info.MENU_INFO, m.page);  //데이터베이스에 입력하고, 성공문자를 돌려받음
                    if(result.equals("성공")){
                        MainActivity.TTS.speak("Success", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.equals("실패")){
                        MainActivity.TTS.speak("Fail", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
            case 3://세 칸 일때
                if(WHclass.sel==Menu_info.MENU_NOTE) {
                    MainActivity.basic_braille_db.delete(MainActivity.basic_braille_db.basic_db_manager.getId(MainActivity.basic_braille_db.basic_db_manager.My_Note_page));
                    result = MainActivity.basic_braille_db.getResult();
                    m.MyView2_init();
                    m.invalidate();
                }
                else {
                    for (int i = 0; i < 3; i++) {
                        for(int j=0; j<m.dot_count*2 ; j++){
                            array[i] = array[i] + Integer.toString(m.text_3[i][j]); // 3개의 배열에 1행 2행 3행을 집어넣음
                        }
                    }
                    result = MainActivity.basic_braille_db.insert(m.dot_count, m.textname_3, array[0], array[1], array[2], Menu_info.MENU_INFO, m.page);  //데이터베이스에 입력하고, 성공문자를 돌려받음
                    if(result.equals("성공")){
                        MainActivity.TTS.speak("Success", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.equals("실패")){
                        MainActivity.TTS.speak("Fail", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP: //마지막 손가락을 땠을 때 화면잠금을 품
                for(int j=0 ; j<3 ; j++){
                    finger_x[j] = -100;
                    finger_y[j] = -100;
                }
                m.finger_set(finger_x[0],finger_y[0],finger_x[1],finger_y[1],finger_x[2],finger_y[2]);
                if (click == false) {
                    click = true;
                }
                lock=false;
                break;
            case MotionEvent.ACTION_DOWN: // 첫번째 손가락을 화면에 터치하였을 경우
                m.x = (int) event.getX(); // 현재 좌표의 x좌표 값을 저장
                m.y = (int) event.getY(); // 현재 좌표의 y좌표 값을 저장

                if ((m.x == 0) && (m.y == 0)) { //좌표 초기값으로 지정된 곳을 터치하면 반응을 없앰
                    break;
                }
                else {
                    /*
                    자신이 터치한 지점의 좌표값의 점자가 돌출된 점자이면 남성의 음성으로 점자번호를 안내하면서 강한 진동 발생
                    자신이 터치한 지점의 좌표값의 점자가 비돌출된 점자이면 여성의 음성으로 점자번호를 안내함
                     */
                    if (m.x < m.w1 + m.bigcircle && m.x > m.w1 - m.bigcircle && m.y < m.h1 + m.bigcircle && m.y > m.h1 - m.bigcircle) {
                        WHclass.number = 1;
                        if (m.x < m.tw1 + m.bigcircle && m.x > m.tw1 - m.bigcircle && m.y < m.th1 + m.bigcircle && m.y > m.th1 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);

                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));

                        }
                    } //첫번째 칸 1번 점자
                    else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                        WHclass.number = 2;
                        if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸 2번 점자

                    else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                        WHclass.number = 3;
                        if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸 3번 점자

                    else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                        WHclass.number = 4;
                        if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸 4번 점자

                    else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                        WHclass.number = 5;
                        if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸 5번점자

                    else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                        WHclass.number = 6;
                        if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 첫번째 칸 6번 점자

                    else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                        WHclass.number = 1;
                        if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    } // 두번째 칸 1번 점자

                    else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                        WHclass.number = 2;
                        if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 두번째 칸 2번 점자

                    else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                        WHclass.number = 3;
                        if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 두번째 칸 3번 점자

                    else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                        WHclass.number = 4;
                        if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 두번째 칸 4번 점자

                    else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                        WHclass.number = 5;
                        if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 두번째 칸 5번 점자

                    else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                        WHclass.number = 6;
                        if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 두번째 칸 6번 점자

                    else if (m.x < m.w13 + m.bigcircle && m.x > m.w13 - m.bigcircle && m.y < m.h13 + m.bigcircle && m.y > m.h13 - m.bigcircle) {
                        WHclass.number = 1;
                        if (m.x < m.tw13 + m.bigcircle && m.x > m.tw13 - m.bigcircle && m.y < m.th13 + m.bigcircle && m.y > m.th13 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }// 세번째 칸 1번 점자

                    else if (m.x < m.w14 + m.bigcircle && m.x > m.w14 - m.bigcircle && m.y < m.h14 + m.bigcircle && m.y > m.h14 - m.bigcircle) {
                        WHclass.number = 2;
                        if (m.x < m.tw14 + m.bigcircle && m.x > m.tw14 - m.bigcircle && m.y < m.th14 + m.bigcircle && m.y > m.th14 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸 2번 점자

                    else if (m.x < m.w15 + m.bigcircle && m.x > m.w15 - m.bigcircle && m.y < m.h15 + m.bigcircle && m.y > m.h15 - m.bigcircle) {
                        WHclass.number = 3;
                        if (m.x < m.tw15 + m.bigcircle && m.x > m.tw15 - m.bigcircle && m.y < m.th15 + m.bigcircle && m.y > m.th15 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸 3번 점자

                    else if (m.x < m.w16 + m.bigcircle && m.x > m.w16 - m.bigcircle && m.y < m.h16 + m.bigcircle && m.y > m.h16 - m.bigcircle) {
                        WHclass.number = 4;
                        if (m.x < m.tw16 + m.bigcircle && m.x > m.tw16 - m.bigcircle && m.y < m.th16 + m.bigcircle && m.y > m.th16 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸 4번 점자

                    else if (m.x < m.w17 + m.bigcircle && m.x > m.w17 - m.bigcircle && m.y < m.h17 + m.bigcircle && m.y > m.h17 - m.bigcircle) {
                        WHclass.number = 5;
                        if (m.x < m.tw17 + m.bigcircle && m.x > m.tw17 - m.bigcircle && m.y < m.th17 + m.bigcircle && m.y > m.th17 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸 5번 점자

                    else if (m.x < m.w18 + m.bigcircle && m.x > m.w18 - m.bigcircle && m.y < m.h18 + m.bigcircle && m.y > m.h18 - m.bigcircle) {
                        WHclass.number = 6;
                        if (m.x < m.tw18 + m.bigcircle && m.x > m.tw18 - m.bigcircle && m.y < m.th18 + m.bigcircle && m.y > m.th18 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸 6번 점자
                    switch(m.dot_count){
                        case 1: //첫번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if(m.x > m.width2+m.bigcircle && m.x<m.width2+(m.bigcircle*2) && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle && m.x<m.width2+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 2: //두번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle  && m.y > m.height1-(m.bigcircle*2) ){
                                WHclass.number=8;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width6+m.bigcircle && m.x<m.width6+(m.bigcircle*2) && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle && m.x<m.width6+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 3: //세번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=8;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=8;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width12+m.bigcircle && m.x<m.width12+(m.bigcircle*2) && m.y > m.height1-(m.bigcircle*2) ){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle && m.x<m.width12+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                    }
                }
                m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                break;
            case MotionEvent.ACTION_MOVE : // 첫번째 손가락을 화면에 터치한 상태에서 드래그를 할 경우
                m.x = (int)event.getX(); // 첫번째 손가락이 터치하고 있는 지점의 x좌표 값을 저장
                m.y = (int)event.getY(); // 첫번째 손가락이 터치하고 있는 지점의 y좌표 값을 저장

                int pointer_count2 = event.getPointerCount();
                for(int j=0 ; j<3 ; j++){
                    finger_x[j] = -100;
                    finger_y[j] = -100;
                }
                for(int i=0 ; i<pointer_count2 ; i++) {
                    finger_x[i] = (int) event.getX(i);
                    finger_y[i] = (int) event.getY(i);
                }
                m.finger_set(finger_x[0],finger_y[0],finger_x[1],finger_y[1],finger_x[2],finger_y[2]);


                /*
                자신이 터치하고 있는 지점의 점자가 돌출된 점자이면 남성의 음성으로 점자번호를 안내하면서 강한 진동 발생
                자신이 터치하고 있는 지점의 점자가 비돌출된 점자이면 여성의 음성으로 점자번호를 안내함
                 */
                if(click==true) {
                    if (m.x < m.w1 + m.bigcircle && m.x > m.w1 - m.bigcircle && m.y < m.h1 + m.bigcircle && m.y > m.h1 - m.bigcircle) {
                        WHclass.number = 1;
                        if (result1 == 0) {
                            if (m.x < m.tw1 + m.bigcircle && m.x > m.tw1 - m.bigcircle && m.y < m.th1 + m.bigcircle && m.y > m.th1 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(1);
                        }
                    } //첫번쨰 칸의 1번 점자
                    else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                        WHclass.number = 2;
                        if (result2 == 0) {
                            if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(2);
                        }
                    }//첫번쨰 칸의 2번 점자
                    else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                        WHclass.number = 3;
                        if (result3 == 0) {
                            if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(3);
                        }
                    }//첫번쨰 칸의 3번 점자
                    else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                        WHclass.number = 4;
                        if (result4 == 0) {
                            if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(4);
                        }
                    }//첫번쨰 칸의 4번 점자
                    else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                        WHclass.number = 5;
                        if (result5 == 0) {
                            if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(5);
                        }
                    }//첫번쨰 칸의 5번 점자
                    else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                        WHclass.number = 6;
                        if (result6 == 0) {
                            if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(6);
                        }
                    }//첫번쨰 칸의 6번 점자
                    else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                        WHclass.number = 1;
                        if (result1 == 0) {
                            if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(1);
                        }
                    }//두번째 칸의 1번 점자
                    else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                        WHclass.number = 2;
                        if (result2 == 0) {
                            if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(2);
                        }
                    }//두번째 칸의 2번 점자
                    else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                        WHclass.number = 3;
                        if (result3 == 0) {
                            if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(3);
                        }
                    }//두번째 칸의 3번 점자
                    else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                        WHclass.number = 4;
                        if (result4 == 0) {
                            if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(4);
                        }
                    }//두번째 칸의 4번 점자
                    else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                        WHclass.number = 5;
                        if (result5 == 0) {
                            if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(5);
                        }
                    }//두번째 칸의 5번 점자
                    else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                        WHclass.number = 6;
                        if (result6 == 0) {
                            if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(6);
                        }
                    }//두번째 칸의 6번 점자
                    else if (m.x < m.w13 + m.bigcircle && m.x > m.w13 - m.bigcircle && m.y < m.h13 + m.bigcircle && m.y > m.h13 - m.bigcircle) {
                        WHclass.number = 1;
                        if (result1 == 0) {
                            if (m.x < m.tw13 + m.bigcircle && m.x > m.tw13 - m.bigcircle && m.y < m.th13 + m.bigcircle && m.y > m.th13 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(1);
                        }
                    }//세번째 칸의 1번 점자
                    else if (m.x < m.w14 + m.bigcircle && m.x > m.w14 - m.bigcircle && m.y < m.h14 + m.bigcircle && m.y > m.h14 - m.bigcircle) {
                        WHclass.number = 2;
                        if (result2 == 0) {
                            if (m.x < m.tw14 + m.bigcircle && m.x > m.tw14 - m.bigcircle && m.y < m.th14 + m.bigcircle && m.y > m.th14 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(2);
                        }
                    }//세번째 칸의 2번 점자
                    else if (m.x < m.w15 + m.bigcircle && m.x > m.w15 - m.bigcircle && m.y < m.h15 + m.bigcircle && m.y > m.h15 - m.bigcircle) {
                        WHclass.number = 3;
                        if (result3 == 0) {
                            if (m.x < m.tw15 + m.bigcircle && m.x > m.tw15 - m.bigcircle && m.y < m.th15 + m.bigcircle && m.y > m.th15 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(3);
                        }
                    }//세번째 칸의 3번 점자
                    else if (m.x < m.w16 + m.bigcircle && m.x > m.w16 - m.bigcircle && m.y < m.h16 + m.bigcircle && m.y > m.h16 - m.bigcircle) {
                        WHclass.number = 4;
                        if (result4 == 0) {
                            if (m.x < m.tw16 + m.bigcircle && m.x > m.tw16 - m.bigcircle && m.y < m.th16 + m.bigcircle && m.y > m.th16 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(4);
                        }
                    }//세번째 칸의 4번 점자
                    else if (m.x < m.w17 + m.bigcircle && m.x > m.w17 - m.bigcircle && m.y < m.h17 + m.bigcircle && m.y > m.h17 - m.bigcircle) {
                        WHclass.number = 5;
                        if (result5 == 0) {
                            if (m.x < m.tw17 + m.bigcircle && m.x > m.tw17 - m.bigcircle && m.y < m.th17 + m.bigcircle && m.y > m.th17 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(5);
                        }
                    }//세번째 칸의 5번 점자
                    else if (m.x < m.w18 + m.bigcircle && m.x > m.w18 - m.bigcircle && m.y < m.h18 + m.bigcircle && m.y > m.h18 - m.bigcircle) {
                        WHclass.number = 6;
                        if (result6 == 0) {
                            if (m.x < m.tw18 + m.bigcircle && m.x > m.tw18 - m.bigcircle && m.y < m.th18 + m.bigcircle && m.y > m.th18 - m.bigcircle) {
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Strong_vibe);
                            } else {
                                WHclass.target = false;
                                startService(new Intent(this, Number.class));
                            }
                            touch_init(6);
                        }
                    }//세번째 칸의 6번 점자

                    else {// 그외 지점을 터치할 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                        touch_init(0);
                        WHclass.number = 0;
                    }

                    switch(m.dot_count){
                        case 1: //첫번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if(m.x > m.width2+m.bigcircle && m.x<m.width2+(m.bigcircle*2) && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle && m.x<m.width2+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 2: //두번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle  && m.y > m.height1-(m.bigcircle*2) ){
                                WHclass.number=8;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width6+m.bigcircle && m.x<m.width6+(m.bigcircle*2) && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle && m.x<m.width6+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 3: //세번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=8;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle && m.y > m.height1-(m.bigcircle*2)){
                                WHclass.number=8;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width12+m.bigcircle && m.x<m.width12+(m.bigcircle*2) && m.y > m.height1-(m.bigcircle*2) ){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle && m.x<m.width12+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                    }
                }
                m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                break;

            case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                click = true;
                newdrag = (int)event.getX(); //두번째 손가락이 화면에서 떨어질 때의 x좌표값을 저장
                y2drag = (int) event.getY(); //두번째 손가락이 화면에서 떨어질 떄의 y좌표값을 저장
                int pointer_count = event.getPointerCount();
                if(pointer_count==3) {
                    update();
                    lock=true;
                }
                else if(lock==false) {
                    if (olddrag - newdrag > WHclass.Drag_space) {//손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그 하였을 경우
                        switch (WHclass.sel) { //학습하려는 점자의 종류에 따라 출력되는 음성을 설정
                            case 4: //숫자연습
                                m.page++;
                                if (m.page >= Dot_number.num_count) {
                                    m.page = 0;
                                    finish();
                                }
                                MainActivity.TTS.speak("Next", TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 5: // Alphabet Practice
                                m.page++;
                                if (m.page >= Dot_alphabet.alphabet_count) {
                                    m.page = 0;
                                    finish();
                                } else {
                                    MainActivity.TTS.speak("Next", TextToSpeech.QUEUE_FLUSH, null);
                                }
                                break;
                            case 6: // Sentence Practice
                                m.page++;
                                if (m.page >= Dot_sentence.sen_count) {
                                    m.page = 0;
                                    finish();
                                } else {
                                    MainActivity.TTS.speak("Next", TextToSpeech.QUEUE_FLUSH, null);
                                }
                                break;
                            case 10: //나만의 단어장
                                MainActivity.basic_braille_db.basic_db_manager.My_Note_page++;
                                if (MainActivity.basic_braille_db.basic_db_manager.My_Note_page >= MainActivity.basic_braille_db.basic_db_manager.size_count)
                                    onBackPressed();
                                else
                                    MyNote_Start_service(); //현재 점자 음성 출력
                                break;
                        }
                        m.MyView2_init();
                        m.invalidate();
                        MainActivity.TTS.speak(m.get_TTs_text(), TextToSpeech.QUEUE_FLUSH, null);

                    } else if (newdrag - olddrag > WHclass.Drag_space) { //손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 하였을 경우
                        switch (WHclass.sel) { //학습하려는 점자의 종류에 따라 출력되는 음성을 설정
                            case 4: // Num Practice
                                if (m.page > 0)
                                    m.page--;
                                MainActivity.TTS.speak("Previous", TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 5: // Alphabet Practice
                                if (m.page > 0) m.page--;
                                MainActivity.TTS.speak("Previous", TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 6: // Alphabet Practice
                                if (m.page > 0) m.page--;
                                MainActivity.TTS.speak("Previous", TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 10: //나만의 단어장
                                if (MainActivity.basic_braille_db.basic_db_manager.My_Note_page > 0)
                                    MainActivity.basic_braille_db.basic_db_manager.My_Note_page--;
                                MyNote_Start_service();
                                break;
                        }
                        m.MyView2_init();
                        m.invalidate();
                        MainActivity.TTS.speak(m.get_TTs_text(), TextToSpeech.QUEUE_FLUSH, null);

                    } else if (y2drag - y1drag > WHclass.Drag_space) { //손가락 2개를 이용하여 아래로 드래그 하였을 때 현재 점자정보를 다시 들음
                        MainActivity.TTS.speak(m.get_TTs_text(), TextToSpeech.QUEUE_FLUSH, null);

                    } else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 하단에서 상단으로 드래그하였을 때 점자 정보를 초기화하면서 현재화면을 종료함
                        m.MyView2_init();
                        m.page = 0;
                        finish();
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // 두번째 손가락이 화면에 터치된 경우
                click = false; // 손가락 1개로 사용하는 문지르기 기능을 잠금
                olddrag = (int)event.getX(); // 두번째 손가락이 화면에 터치된 지점의 x좌표 값 저장
                y1drag = (int) event.getY(); // 두번째 손가락이 화면에 터치된 지점의 y좌표 값 저장
                break;
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

    public void MyNote_Start_service(){
        reference = MainActivity.basic_braille_db.basic_db_manager.getReference(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
        reference_index = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);

    }
    @Override
    public void onBackPressed() { //뒤로가기 키를 눌렀을 경우, 점자의 종류에따라 음성을 출력하면서 변수들 초기화
        m.page = 0;
        m.MyView2_init();
        MainActivity.TTS2.speak("Back", TextToSpeech.QUEUE_FLUSH, null);
        finish(); // 화면 종료
    }
}
