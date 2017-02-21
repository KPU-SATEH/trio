package com.example.yeo.practice.Talkback_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yeo.practice.Common_basic_practice_sound.Final_service;
import com.example.yeo.practice.Common_basic_practice_sound.Initial_service;
import com.example.yeo.practice.Common_basic_practice_sound.Num_service;
import com.example.yeo.practice.Common_basic_practice_sound.Sentence_service;
import com.example.yeo.practice.Common_basic_practice_sound.Vowel_service;
import com.example.yeo.practice.Common_basic_practice_sound.abbreviation_service;
import com.example.yeo.practice.Common_basic_practice_sound.alphabet_service;
import com.example.yeo.practice.Common_braille_data.dot_abbreviation;
import com.example.yeo.practice.Common_braille_data.dot_alphabet;
import com.example.yeo.practice.Common_braille_data.dot_final;
import com.example.yeo.practice.Common_braille_data.dot_initial;
import com.example.yeo.practice.Common_braille_data.dot_num;
import com.example.yeo.practice.Common_braille_data.dot_sentence;
import com.example.yeo.practice.Common_braille_data.dot_vowel;
import com.example.yeo.practice.Common_mynote_database.Mynote_service;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.Common_sound.slied;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.WHclass;


public class Talk_Braille_short_practice extends FragmentActivity {
/*
3칸 이하의 점자 학습을 진행하는 클래스
 */

    int finger_x[] = new int[3];
    int finger_y[] = new int[3];

    Talk_Braille_short_display m;
    int newdrag, olddrag; //첫번째 손가락과 두번째 손가락의 x좌표를 저장할 변수
    int y1drag, y2drag; //첫번째 손가락과 두번째 손가락의 y좌표를 저장할 변수
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0; //문지르기 기능을 초기화 하기 위한 컨트롤 변수
    boolean click = true;


    boolean lock=false;
    /*
    초성 연습, 모음연습, 종성연습, 숫자연습, 알파벳연습, 문장부호연습, 약자 및 약아 연습 클래스 선언
     */

    public static dot_initial Dot_initial;
    public static dot_vowel Dot_vowel;
    public static dot_final Dot_final;
    public static dot_num Dot_number;
    public static dot_alphabet Dot_alphabet;
    public static dot_sentence Dot_sentence;
    public static dot_abbreviation Dot_abbreviation;

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
            case 1: //초성연습
                Dot_initial = new dot_initial();
                break;
            case 2: //모음연습
                Dot_vowel = new dot_vowel();
                break;
            case 3: //종성연습
                Dot_final = new dot_final();
                break;
            case 4: //숫자연습
                Dot_number = new dot_num();
                break;
            case 5:// 알파벳연습
                Dot_alphabet = new dot_alphabet();
                break;
            case 6: //문장부호 연습
                Dot_sentence = new dot_sentence();
                break;
            case 7: //약자 및 약어연습
                Dot_abbreviation = new dot_abbreviation();
                break;

        }

        m = new Talk_Braille_short_display(this); //화면을 출력하기 위한 클래스 선언
        m.setBackgroundColor(Color.rgb(22,26,44));
        setContentView(m);
        m.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER: //손가락 1개를 화면에 터치하였을 경우
                        startService(new Intent(Talk_Braille_short_practice.this, Sound_Manager.class));
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
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));

                                }
                            } //첫번째 칸 1번 점자
                            else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                                WHclass.number = 2;
                                if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }

                            } //첫번째 칸 2번 점자

                            else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                                WHclass.number = 3;
                                if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }

                            } //첫번째 칸 3번 점자

                            else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                                WHclass.number = 4;
                                if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }

                            } //첫번째 칸 4번 점자

                            else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                                WHclass.number = 5;
                                if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }

                            } //첫번째 칸 5번점자

                            else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                                WHclass.number = 6;
                                if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 첫번째 칸 6번 점자

                            else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                                WHclass.number = 1;
                                if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            } // 두번째 칸 1번 점자

                            else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                                WHclass.number = 2;
                                if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 두번째 칸 2번 점자

                            else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                                WHclass.number = 3;
                                if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 두번째 칸 3번 점자

                            else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                                WHclass.number = 4;
                                if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 두번째 칸 4번 점자

                            else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                                WHclass.number = 5;
                                if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 두번째 칸 5번 점자

                            else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                                WHclass.number = 6;
                                if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 두번째 칸 6번 점자

                            else if (m.x < m.w13 + m.bigcircle && m.x > m.w13 - m.bigcircle && m.y < m.h13 + m.bigcircle && m.y > m.h13 - m.bigcircle) {
                                WHclass.number = 1;
                                if (m.x < m.tw13 + m.bigcircle && m.x > m.tw13 - m.bigcircle && m.y < m.th13 + m.bigcircle && m.y > m.th13 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }// 세번째 칸 1번 점자

                            else if (m.x < m.w14 + m.bigcircle && m.x > m.w14 - m.bigcircle && m.y < m.h14 + m.bigcircle && m.y > m.h14 - m.bigcircle) {
                                WHclass.number = 2;
                                if (m.x < m.tw14 + m.bigcircle && m.x > m.tw14 - m.bigcircle && m.y < m.th14 + m.bigcircle && m.y > m.th14 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }//세번째 칸 2번 점자

                            else if (m.x < m.w15 + m.bigcircle && m.x > m.w15 - m.bigcircle && m.y < m.h15 + m.bigcircle && m.y > m.h15 - m.bigcircle) {
                                WHclass.number = 3;
                                if (m.x < m.tw15 + m.bigcircle && m.x > m.tw15 - m.bigcircle && m.y < m.th15 + m.bigcircle && m.y > m.th15 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }//세번째 칸 3번 점자

                            else if (m.x < m.w16 + m.bigcircle && m.x > m.w16 - m.bigcircle && m.y < m.h16 + m.bigcircle && m.y > m.h16 - m.bigcircle) {
                                WHclass.number = 4;
                                if (m.x < m.tw16 + m.bigcircle && m.x > m.tw16 - m.bigcircle && m.y < m.th16 + m.bigcircle && m.y > m.th16 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }//세번째 칸 4번 점자

                            else if (m.x < m.w17 + m.bigcircle && m.x > m.w17 - m.bigcircle && m.y < m.h17 + m.bigcircle && m.y > m.h17 - m.bigcircle) {
                                WHclass.number = 5;
                                if (m.x < m.tw17 + m.bigcircle && m.x > m.tw17 - m.bigcircle && m.y < m.th17 + m.bigcircle && m.y > m.th17 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }//세번째 칸 5번 점자

                            else if (m.x < m.w18 + m.bigcircle && m.x > m.w18 - m.bigcircle && m.y < m.h18 + m.bigcircle && m.y > m.h18 - m.bigcircle) {
                                WHclass.number = 6;
                                if (m.x < m.tw18 + m.bigcircle && m.x > m.tw18 - m.bigcircle && m.y < m.th18 + m.bigcircle && m.y > m.th18 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                }
                            }//세번째 칸 6번 점자

                        }
                        m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        m.x = (int)event.getX(); // 첫번째 손가락이 터치하고 있는 지점의 x좌표 값을 저장
                        m.y = (int)event.getY(); // 첫번째 손가락이 터치하고 있는 지점의 y좌표 값을 저장

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
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(1);
                                }
                            } //첫번쨰 칸의 1번 점자
                            else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                                WHclass.number = 2;
                                if (result2 == 0) {
                                    if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(2);
                                }
                            }//첫번쨰 칸의 2번 점자
                            else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                                WHclass.number = 3;
                                if (result3 == 0) {
                                    if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(3);
                                }
                            }//첫번쨰 칸의 3번 점자
                            else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                                WHclass.number = 4;
                                if (result4 == 0) {
                                    if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(4);
                                }
                            }//첫번쨰 칸의 4번 점자
                            else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                                WHclass.number = 5;
                                if (result5 == 0) {
                                    if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(5);
                                }
                            }//첫번쨰 칸의 5번 점자
                            else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                                WHclass.number = 6;
                                if (result6 == 0) {
                                    if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(6);
                                }
                            }//첫번쨰 칸의 6번 점자
                            else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                                WHclass.number = 1;
                                if (result1 == 0) {
                                    if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(1);
                                }
                            }//두번째 칸의 1번 점자
                            else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                                WHclass.number = 2;
                                if (result2 == 0) {
                                    if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(2);
                                }
                            }//두번째 칸의 2번 점자
                            else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                                WHclass.number = 3;
                                if (result3 == 0) {
                                    if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(3);
                                }
                            }//두번째 칸의 3번 점자
                            else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                                WHclass.number = 4;
                                if (result4 == 0) {
                                    if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(4);
                                }
                            }//두번째 칸의 4번 점자
                            else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                                WHclass.number = 5;
                                if (result5 == 0) {
                                    if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(5);
                                }
                            }//두번째 칸의 5번 점자
                            else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                                WHclass.number = 6;
                                if (result6 == 0) {
                                    if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(6);
                                }
                            }//두번째 칸의 6번 점자
                            else if (m.x < m.w13 + m.bigcircle && m.x > m.w13 - m.bigcircle && m.y < m.h13 + m.bigcircle && m.y > m.h13 - m.bigcircle) {
                                WHclass.number = 1;
                                if (result1 == 0) {
                                    if (m.x < m.tw13 + m.bigcircle && m.x > m.tw13 - m.bigcircle && m.y < m.th13 + m.bigcircle && m.y > m.th13 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(1);
                                }
                            }//세번째 칸의 1번 점자
                            else if (m.x < m.w14 + m.bigcircle && m.x > m.w14 - m.bigcircle && m.y < m.h14 + m.bigcircle && m.y > m.h14 - m.bigcircle) {
                                WHclass.number = 2;
                                if (result2 == 0) {
                                    if (m.x < m.tw14 + m.bigcircle && m.x > m.tw14 - m.bigcircle && m.y < m.th14 + m.bigcircle && m.y > m.th14 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(2);
                                }
                            }//세번째 칸의 2번 점자
                            else if (m.x < m.w15 + m.bigcircle && m.x > m.w15 - m.bigcircle && m.y < m.h15 + m.bigcircle && m.y > m.h15 - m.bigcircle) {
                                WHclass.number = 3;
                                if (result3 == 0) {
                                    if (m.x < m.tw15 + m.bigcircle && m.x > m.tw15 - m.bigcircle && m.y < m.th15 + m.bigcircle && m.y > m.th15 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(3);
                                }
                            }//세번째 칸의 3번 점자
                            else if (m.x < m.w16 + m.bigcircle && m.x > m.w16 - m.bigcircle && m.y < m.h16 + m.bigcircle && m.y > m.h16 - m.bigcircle) {
                                WHclass.number = 4;
                                if (result4 == 0) {
                                    if (m.x < m.tw16 + m.bigcircle && m.x > m.tw16 - m.bigcircle && m.y < m.th16 + m.bigcircle && m.y > m.th16 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(4);
                                }
                            }//세번째 칸의 4번 점자
                            else if (m.x < m.w17 + m.bigcircle && m.x > m.w17 - m.bigcircle && m.y < m.h17 + m.bigcircle && m.y > m.h17 - m.bigcircle) {
                                WHclass.number = 5;
                                if (result5 == 0) {
                                    if (m.x < m.tw17 + m.bigcircle && m.x > m.tw17 - m.bigcircle && m.y < m.th17 + m.bigcircle && m.y > m.th17 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(5);
                                }
                            }//세번째 칸의 5번 점자
                            else if (m.x < m.w18 + m.bigcircle && m.x > m.w18 - m.bigcircle && m.y < m.h18 + m.bigcircle && m.y > m.h18 - m.bigcircle) {
                                WHclass.number = 6;
                                if (result6 == 0) {
                                    if (m.x < m.tw18 + m.bigcircle && m.x > m.tw18 - m.bigcircle && m.y < m.th18 + m.bigcircle && m.y > m.th18 - m.bigcircle) {
                                        WHclass.target = true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Strong_vibe);
                                    } else {
                                        WHclass.target = false;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                    }
                                    touch_init(6);
                                }
                            }//세번째 칸의 6번 점자
                            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else {// 그외 지점을 터치할 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                                touch_init(0);
                                WHclass.number = 0;
                            }

                            switch(m.dot_count){
                                case 1: //첫번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                    if(m.x > m.width2+m.bigcircle && m.x<m.width2+(m.bigcircle*2)){
                                        WHclass.number=7;
                                        WHclass.target= true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    break;
                                case 2: //두번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                    if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                                        WHclass.number=8;
                                        WHclass.target= true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    else if(m.x > m.width6+m.bigcircle && m.x<m.width6+(m.bigcircle*2)){
                                        WHclass.number=7;
                                        WHclass.target= true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    break;
                                case 3: //세번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                    if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                                        WHclass.number=8;
                                        WHclass.target= true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle){
                                        WHclass.number=8;
                                        WHclass.target= true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    else if(m.x > m.width12+m.bigcircle && m.x<m.width12+(m.bigcircle*2)){
                                        WHclass.number=7;
                                        WHclass.target= true;
                                        startService(new Intent(Talk_Braille_short_practice.this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    break;
                            }
                        }
                        m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT: // 손가락 1개를 화면에서 떨어트렸을 경우
                        touch_init(0);
                        lock=false;
                        break;
                }
                return false;
            }
        });

        switch(WHclass.sel) { //메뉴에 맞는 음성을 출력함
            case 1: //초성연습
                startService(new Intent(this, Initial_service.class));
                break;
            case 2: //모음연습
                startService(new Intent(this, Vowel_service.class));
                break;
            case 3: //종성연습
                startService(new Intent(this, Final_service.class));
                break;
            case 4: //숫자연습
                startService(new Intent(this, Num_service.class));
                break;
            case 5: // 알파벳 연습
                startService(new Intent(this, alphabet_service.class));
                break;
            case 6: // 문장부호 연습
                startService(new Intent(this, Sentence_service.class));
                break;
            case 7: //약자 및 약어 연습
                startService(new Intent(this, abbreviation_service.class));
                break;
        }
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
                        Mynote_service.menu_page=2;
                        startService(new Intent(this, Mynote_service.class));
                    }
                    else if(result.equals("실패")){
                        Mynote_service.menu_page=3;
                        startService(new Intent(this, Mynote_service.class));
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
                        Mynote_service.menu_page=2;
                        startService(new Intent(this, Mynote_service.class));
                    }
                    else if(result.equals("실패")){
                        Mynote_service.menu_page=3;
                        startService(new Intent(this, Mynote_service.class));
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
                        Mynote_service.menu_page=2;
                        startService(new Intent(this, Mynote_service.class));
                    }
                    else if(result.equals("실패")){
                        Mynote_service.menu_page=3;
                        startService(new Intent(this, Mynote_service.class));
                    }
                }
                break;
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:  // 두번째 손가락을 떼었을 경우
                click = true;
                newdrag = (int)event.getX(); //두번째 손가락이 화면에서 떨어질 때의 x좌표값을 저장
                y2drag = (int) event.getY(); //두번째 손가락이 화면에서 떨어질 떄의 y좌표값을 저장
                if(lock==false) {
                    if (olddrag - newdrag > WHclass.Drag_space) {//손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그 하였을 경우
                        slied.slied = Menu_info.next;
                        startService(new Intent(this, slied.class));
                        switch (WHclass.sel) { //학습하려는 점자의 종류에 따라 출력되는 음성을 설정
                            case 1: // 초성연습
                                m.page++;
                                if (m.page >= Dot_initial.Initial_count) {
                                    m.page = 0;
                                    Initial_service.finish = true;
                                    startService(new Intent(this, Initial_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, Initial_service.class));
                                }
                                break;
                            case 2: //모음연습
                                m.page++;
                                if (m.page >= Dot_vowel.vowel_count) {
                                    m.page = 0;
                                    Vowel_service.finish = true;
                                    startService(new Intent(this, Vowel_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, Vowel_service.class));
                                }
                                break;
                            case 3: //종성연습
                                m.page++;
                                if (m.page >= Dot_final.final_count) {
                                    m.page = 0;
                                    Final_service.finish = true;
                                    startService(new Intent(this, Final_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, Final_service.class));
                                }
                                break;
                            case 4: //숫자연습
                                m.page++;
                                if (m.page >= Dot_number.num_count) {
                                    m.page = 0;
                                    Num_service.finish = true;
                                    startService(new Intent(this, Num_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, Num_service.class));
                                }
                                break;
                            case 5: // 알파벳 연습
                                m.page++;
                                if (m.page >= Dot_alphabet.alphabet_count) {
                                    m.page = 0;
                                    alphabet_service.finish = true;
                                    startService(new Intent(this, alphabet_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, alphabet_service.class));
                                }
                                break;
                            case 6: // 문장부호 연습
                                m.page++;
                                if (m.page >= Dot_sentence.sentence_count) {
                                    m.page = 0;
                                    Sentence_service.finish = true;
                                    startService(new Intent(this, Sentence_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, Sentence_service.class));
                                }
                                break;
                            case 7: //약자 및 약어 연습
                                m.page++;
                                if (m.page >= Dot_abbreviation.abbreviation_count) {
                                    m.page = 0;
                                    abbreviation_service.finish = true;
                                    startService(new Intent(this, abbreviation_service.class));
                                    finish();
                                } else {
                                    startService(new Intent(this, abbreviation_service.class));
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
                    } else if (newdrag - olddrag > WHclass.Drag_space) { //손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 하였을 경우
                        slied.slied = Menu_info.pre;
                        startService(new Intent(this, slied.class));
                        switch (WHclass.sel) {//학습하려는 점자의 종류에 따라 출력되는 음성을 설정
                            case 1: //초성연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, Initial_service.class));
                                break;
                            case 2: //모음연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, Vowel_service.class));
                                break;
                            case 3: //종성연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, Final_service.class));
                                break;
                            case 4: // 숫자연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, Num_service.class));
                                break;
                            case 5: //알파벳 연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, alphabet_service.class));
                                break;
                            case 6: //문장부호 연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, Sentence_service.class));
                                break;
                            case 7: //약자 및 약어 연습
                                if (m.page > 0) m.page--;
                                startService(new Intent(this, abbreviation_service.class));
                                break;
                            case 10: //나만의 단어장
                                if (MainActivity.basic_braille_db.basic_db_manager.My_Note_page > 0)
                                    MainActivity.basic_braille_db.basic_db_manager.My_Note_page--;
                                MyNote_Start_service();
                                break;
                        }
                        m.MyView2_init();
                        m.invalidate();
                    } else if (y2drag - y1drag > WHclass.Drag_space) { //손가락 2개를 이용하여 아래로 드래그 하였을 때 현재 점자정보를 다시 들음
                        switch (WHclass.sel) { //점자의 종류에 따라 출력되는 음성을 설정
                            case 1: //초성연습
                                startService(new Intent(this, Initial_service.class));
                                break;
                            case 2: //모음연습
                                startService(new Intent(this, Vowel_service.class));
                                break;
                            case 3: //종성연습
                                startService(new Intent(this, Final_service.class));
                                break;
                            case 4: //숫자연습
                                startService(new Intent(this, Num_service.class));
                                break;
                            case 5: //알파벳 연습
                                startService(new Intent(this, alphabet_service.class));
                                break;
                            case 6: // 문장부호 연습
                                startService(new Intent(this, Sentence_service.class));
                                break;
                            case 7: // 약자 및 약어 연습
                                startService(new Intent(this, abbreviation_service.class));
                                break;
                            case 10: //나만의 단어장
                                MyNote_Start_service();
                                break;
                        }
                    } else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 하단에서 상단으로 드래그하였을 때 점자 정보를 초기화하면서 현재화면을 종료함
                        m.MyView2_init();
                        m.page = 0;
                        switch (WHclass.sel) { // 점자의 종류에 따라 출력되는 음성을 설정함
                            case 1: //초성연습
                                Initial_service.finish = true;
                                startService(new Intent(this, Initial_service.class));
                                break;
                            case 2: //모음연습
                                Vowel_service.finish = true;
                                startService(new Intent(this, Vowel_service.class));
                                break;
                            case 3: //종성연습
                                Final_service.finish = true;
                                startService(new Intent(this, Final_service.class));
                                break;
                            case 4: //숫자연습
                                Num_service.finish = true;
                                startService(new Intent(this, Num_service.class));
                                break;
                            case 5: //알파벳 연습
                                alphabet_service.finish = true;
                                startService(new Intent(this, alphabet_service.class));
                                break;
                            case 6: // 문장부호 연습
                                Sentence_service.finish = true;
                                startService(new Intent(this, Sentence_service.class));
                                break;
                            case 7: // 알파벳 연습
                                abbreviation_service.finish = true;
                                startService(new Intent(this, abbreviation_service.class));
                                break;
                            case 9:
                                break;
                            case 10: //나만의 단어장
                                onBackPressed();
                                break;
                        }
                        finish();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN: // 두번째 손가락이 화면에 터치된 경우
                startService(new Intent(Talk_Braille_short_practice.this, Sound_Manager.class));
                olddrag = (int)event.getX(); // 두번째 손가락이 화면에 터치된 지점의 x좌표 값 저장
                y1drag = (int) event.getY(); // 두번째 손가락이 화면에 터치된 지점의 y좌표 값 저장
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointer_count = event.getPointerCount();
                if(pointer_count==3) {
                    update();
                    lock=true;
                }
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

        switch (reference) {  //나만의 단어장 음성출력
            case 1: //초성연습
                startService(new Intent(Talk_Braille_short_practice.this, Initial_service.class));
                break;
            case 2: //모음연습
                startService(new Intent(Talk_Braille_short_practice.this, Vowel_service.class));
                break;
            case 3: //종성연습
                startService(new Intent(Talk_Braille_short_practice.this, Final_service.class));
                break;
            case 4: //숫자연습
                startService(new Intent(Talk_Braille_short_practice.this, Num_service.class));
                break;
            case 5: // 알파벳 연습
                startService(new Intent(Talk_Braille_short_practice.this, alphabet_service.class));
                break;
            case 6: // 문장부호 연습
                startService(new Intent(Talk_Braille_short_practice.this, Sentence_service.class));
                break;
            case 7: //약자 및 약어 연습
                startService(new Intent(Talk_Braille_short_practice.this, abbreviation_service.class));
                break;
        }
    }
    @Override
    public void onBackPressed() { //뒤로가기 키를 눌렀을 경우, 점자의 종류에따라 음성을 출력하면서 변수들 초기화
        m.page = 0;
        m.MyView2_init();

        switch(WHclass.sel) {
            case 1: //초성연습
                Initial_service.finish = true;
                startService(new Intent(this, Initial_service.class));
                break;
            case 2: //모음연습
                Vowel_service.finish = true;
                startService(new Intent(this, Vowel_service.class));
                break;
            case 3: //종성연습
                Final_service.finish = true;
                startService(new Intent(this, Final_service.class));
                break;
            case 4: //숫자연습
                Num_service.finish = true;
                startService(new Intent(this, Num_service.class));
                break;
            case 5: //알파벳 연습
                alphabet_service.finish = true;
                startService(new Intent(this, alphabet_service.class));
                break;
            case 6: //문장부호 연습
                Sentence_service.finish = true;
                startService(new Intent(this, Sentence_service.class));
                break;
            case 7: //약자 및 약어 연습
                abbreviation_service.finish = true;
                startService(new Intent(this, abbreviation_service.class));
                break;
            case 10: //나만의단어장
                MainActivity.basic_braille_db.basic_db_manager.My_Note_page=0;
                Mynote_service.finish = true;
                startService(new Intent(this, Mynote_service.class));
                break;
            default :
                break;
        }
        finish(); // 화면 종료
    }
}
