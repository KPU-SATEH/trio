package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_sound.Number;

public class writing_short_practice2 extends FragmentActivity{
    /*
    3칸 이하의 점자 쓰기퀴즈를 진행하는 클래스 두번째 버전
    */
    writing_short_display m;
    int newdrag, olddrag;  //첫번째 손가락과 두번째 손가락의 x좌표를 저장할 변수
    int y1drag, y2drag;//첫번째 손가락과 두번째 손가락의 y좌표를 저장할 변수
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0;//문지르기 기능을 초기화 하기 위한 컨트롤 변수
    boolean click = true;
    static int page = 0;
    boolean Braille_coordinate[] = new boolean[18];


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


        m = new writing_short_display(this);
        m.setBackgroundColor(Color.rgb(22, 26, 44));
        setContentView(m);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP: // 마지막 한개의 손가락을 화면에서 떨어트렸을 경우 화면 잠금을 해제함
                m.x = (int) event.getX();// 현재 좌표의 x좌표 값을 저장
                m.y = (int) event.getY();// 현재 좌표의 y좌표 값을 저장
                if (click == false) click = true;
                if (m.x < m.w1 + m.bigcircle && m.x > m.w1 - m.bigcircle && m.y < m.h1 + m.bigcircle && m.y > m.h1 - m.bigcircle) {
                    WHclass.number = 1;
                    if (m.x < m.tw1 + m.bigcircle && m.x > m.tw1 - m.bigcircle && m.y < m.th1 + m.bigcircle && m.y > m.th1 - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);
                    }
                    else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));
                    }
                    touch_insert(1);
                } //첫번째 칸의 1번 점자
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
                    touch_insert(2);
                } //첫번째 칸의 2번 점자

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
                    touch_insert(3);
                } //첫번째 칸의 3번 점자

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
                    touch_insert(4);
                } //첫번째 칸의 4번 점자

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
                    touch_insert(5);
                } //첫번째 칸의 5번점자

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
                    touch_insert(6);
                } //첫번째 칸의 6번 점자
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
                    touch_insert(7);
                }//두번째 칸의 1번 점자
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
                    touch_insert(8);
                }//두번째 칸의 2번 점자
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
                    touch_insert(9);
                }//두번째 칸의 3번 점자
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
                    touch_insert(10);
                }//두번째 칸의 4번 점자
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
                    touch_insert(11);
                }//두번째 칸의 5번 점자
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
                    touch_insert(12);
                }//두번째 칸의 6번 점자
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
                    touch_insert(13);
                }//세번째 칸의 1번 점자
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
                    touch_insert(14);
                }//세번째 칸의 2번 점자
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
                    touch_insert(15);
                }//세번째 칸의 3번 점자
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
                    touch_insert(16);
                }//세번째 칸의 4번 점자
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
                    touch_insert(17);
                }//세번째 칸의 5번 점자
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
                    touch_insert(18);
                }//세번째 칸의 6번 점자
                break;
            case MotionEvent.ACTION_DOWN:// 첫번째 손가락을 화면에 터치하였을 경우
                m.x = (int) event.getX();// 현재 좌표의 x좌표 값을 저장
                m.y = (int) event.getY();// 현재 좌표의 y좌표 값을 저장

                if ((m.x == 0) && (m.y == 0)) break;
                else {
                     /*
                    자신이 터치한 지점의 좌표값의 점자가 돌출된 점자이면 남성의 음성으로 점자번호를 안내하면서 강한 진동 발생
                    자신이 터치한 지점의 좌표값의 점자가 비돌출된 점자이면 여성의 음성으로 점자번호를 안내함
                     */
                    if (m.x < m.w1 + m.bigcircle && m.x > m.w1 - m.bigcircle && m.y < m.h1 + m.bigcircle && m.y > m.h1 - m.bigcircle) {
                        touch_insert_check(1);
                        WHclass.number = 1;
                        if (m.x < m.tw1 + m.bigcircle && m.x > m.tw1 - m.bigcircle && m.y < m.th1 + m.bigcircle && m.y > m.th1 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    } //첫번째 칸의 1번 점자
                    else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
                        touch_insert_check(2);
                        WHclass.number = 2;
                        if (m.x < m.tw2 + m.bigcircle && m.x > m.tw2 - m.bigcircle && m.y < m.th2 + m.bigcircle && m.y > m.th2 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸의 2번 점자

                    else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
                        touch_insert_check(3);
                        WHclass.number = 3;
                        if (m.x < m.tw3 + m.bigcircle && m.x > m.tw3 - m.bigcircle && m.y < m.th3 + m.bigcircle && m.y > m.th3 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸의 3번 점자

                    else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
                        touch_insert_check(4);
                        WHclass.number = 4;
                        if (m.x < m.tw4 + m.bigcircle && m.x > m.tw4 - m.bigcircle && m.y < m.th4 + m.bigcircle && m.y > m.th4 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸의 4번 점자

                    else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
                        touch_insert_check(5);
                        WHclass.number = 5;
                        if (m.x < m.tw5 + m.bigcircle && m.x > m.tw5 - m.bigcircle && m.y < m.th5 + m.bigcircle && m.y > m.th5 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }

                    } //첫번째 칸의 5번점자

                    else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
                        touch_insert_check(6);
                        WHclass.number = 6;
                        if (m.x < m.tw6 + m.bigcircle && m.x > m.tw6 - m.bigcircle && m.y < m.th6 + m.bigcircle && m.y > m.th6 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    } //첫번째 칸의 6번 점자
                    else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                        touch_insert_check(7);
                        WHclass.number = 1;
                        if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//두번째 칸의 1번 점자
                    else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                        touch_insert_check(8);
                        WHclass.number = 2;
                        if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//두번째 칸의 2번 점자
                    else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                        touch_insert_check(9);
                        WHclass.number = 3;
                        if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//두번째 칸의 3번 점자
                    else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                        touch_insert_check(10);
                        WHclass.number = 4;
                        if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//두번째 칸의 4번 점자
                    else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                        touch_insert_check(11);
                        WHclass.number = 5;
                        if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//두번째 칸의 5번 점자
                    else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                        touch_insert_check(12);
                        WHclass.number = 6;
                        if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//두번째 칸의 6번 점자
                    else if (m.x < m.w13 + m.bigcircle && m.x > m.w13 - m.bigcircle && m.y < m.h13 + m.bigcircle && m.y > m.h13 - m.bigcircle) {
                        touch_insert_check(13);
                        WHclass.number = 1;
                        if (m.x < m.tw13 + m.bigcircle && m.x > m.tw13 - m.bigcircle && m.y < m.th13 + m.bigcircle && m.y > m.th13 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸의 1번 점자
                    else if (m.x < m.w14 + m.bigcircle && m.x > m.w14 - m.bigcircle && m.y < m.h14 + m.bigcircle && m.y > m.h14 - m.bigcircle) {
                        touch_insert_check(14);
                        WHclass.number = 2;
                        if (m.x < m.tw14 + m.bigcircle && m.x > m.tw14 - m.bigcircle && m.y < m.th14 + m.bigcircle && m.y > m.th14 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸의 2번 점자
                    else if (m.x < m.w15 + m.bigcircle && m.x > m.w15 - m.bigcircle && m.y < m.h15 + m.bigcircle && m.y > m.h15 - m.bigcircle) {
                        touch_insert_check(15);
                        WHclass.number = 3;
                        if (m.x < m.tw15 + m.bigcircle && m.x > m.tw15 - m.bigcircle && m.y < m.th15 + m.bigcircle && m.y > m.th15 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸의 3번 점자
                    else if (m.x < m.w16 + m.bigcircle && m.x > m.w16 - m.bigcircle && m.y < m.h16 + m.bigcircle && m.y > m.h16 - m.bigcircle) {
                        touch_insert_check(16);
                        WHclass.number = 4;
                        if (m.x < m.tw16 + m.bigcircle && m.x > m.tw16 - m.bigcircle && m.y < m.th16 + m.bigcircle && m.y > m.th16 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸의 4번 점자
                    else if (m.x < m.w17 + m.bigcircle && m.x > m.w17 - m.bigcircle && m.y < m.h17 + m.bigcircle && m.y > m.h17 - m.bigcircle) {
                        touch_insert_check(17);
                        WHclass.number = 5;
                        if (m.x < m.tw17 + m.bigcircle && m.x > m.tw17 - m.bigcircle && m.y < m.th17 + m.bigcircle && m.y > m.th17 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸의 5번 점자
                    else if (m.x < m.w18 + m.bigcircle && m.x > m.w18 - m.bigcircle && m.y < m.h18 + m.bigcircle && m.y > m.h18 - m.bigcircle) {
                        touch_insert_check(18);
                        WHclass.number = 6;
                        if (m.x < m.tw18 + m.bigcircle && m.x > m.tw18 - m.bigcircle && m.y < m.th18 + m.bigcircle && m.y > m.th18 - m.bigcircle) {
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Strong_vibe);
                        } else {
                            WHclass.target = false;
                            startService(new Intent(this, Number.class));
                        }
                    }//세번째 칸의 6번 점자

                }
                m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                break;
            case MotionEvent.ACTION_MOVE: // 첫번째 손가락을 화면에 터치한 상태에서 드래그를 할 경우
                m.x = (int) event.getX();  // 첫번째 손가락이 터치하고 있는 지점의 x좌표 값을 저장
                m.y = (int) event.getY(); // 첫번째 손가락이 터치하고 있는 지점의 y좌표 값을 저장
                /*
                자신이 터치하고 있는 지점의 점자가 돌출된 점자이면 남성의 음성으로 점자번호를 안내하면서 강한 진동 발생
                자신이 터치하고 있는 지점의 점자가 비돌출된 점자이면 여성의 음성으로 점자번호를 안내함
                 */
                if (click == true) {
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
                    }//첫번쨰 칸의 1번 점자
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
                    else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle) {
                        WHclass.number = 7;
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    } else { //그외 지점을 터치할 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                        touch_init(0);
                        WHclass.number = 0;
                    }
                    switch (m.dot_count) {
                        case 1: //첫번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if (m.x > m.width8 + m.bigcircle && m.x < m.width8 + (m.bigcircle * 2)) {
                                WHclass.number = 7;
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 2://두번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
                                WHclass.number = 8;
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            } else if (m.x > m.width10 + m.bigcircle && m.x < m.width10 + (m.bigcircle * 2)) {
                                WHclass.number = 7;
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 3://세번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                            if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
                                WHclass.number = 8;
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            } else if (m.x > m.width10 + m.bigcircle && m.x < m.width11 - m.bigcircle) {
                                WHclass.number = 8;
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            } else if (m.x > m.width12 + m.bigcircle && m.x < m.width12 + (m.bigcircle * 2)) {
                                WHclass.number = 7;
                                WHclass.target = true;
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
                newdrag = (int) event.getX();//두번째 손가락이 화면에서 떨어질 때의 x좌표값을 저장
                y2drag = (int) event.getY();//두번째 손가락이 화면에서 떨어질 떄의 y좌표값을 저장
                if (y2drag - y1drag > WHclass.Drag_space) {//손가락 2개를 이용하여 하단으로 드래그 하는 경우 음성인식 실행

                } else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 상단으로 드래그 하는 경우 퀴즈 화면 종료
                    onBackPressed();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                click = false;
                olddrag = (int) event.getX();
                y1drag = (int) event.getY();
                break;

        }
        return true;
    }

    public void touch_insert_check(int coordinate){
        int coordinate_x=0;
        int coordinate_y=0;
        if(Braille_coordinate[coordinate]==true){
                switch(coordinate){
                    case 1:
                        coordinate_x=0; coordinate_y=0;
                        if(m.tw1==0 && m.th1==0) {
                            m.tw1 = m.width7;
                            m.th1 = m.height1;
                        }
                        else {
                            m.tw1 = 0;
                            m.th1 = 0;
                        }
                        break;
                    case 2:
                        if(m.tw2==0 && m.th2==0) {
                            m.tw2 = m.width7;
                            m.th2 = m.height2;
                        }
                        else {
                            m.tw2 = 0;
                            m.th2 = 0;
                        }
                        coordinate_x=1; coordinate_y=0;
                        break;
                    case 3:
                        if(m.tw3==0 && m.th3==0) {
                            m.tw3 = m.width7;
                            m.th3 = m.height3;
                        }
                        else {
                            m.tw3 = 0;
                            m.th3 = 0;
                        }
                        coordinate_x=2; coordinate_y=0;
                        break;
                    case 4:
                        if(m.tw4==0 && m.th4==0) {
                            m.tw4 = m.width8;
                            m.th4 = m.height1;
                        }
                        else {
                            m.tw4 = 0;
                            m.th4 = 0;
                        }
                        coordinate_x=0; coordinate_y=1;
                        break;
                    case 5:
                        if(m.tw5==0 && m.th5==0) {
                            m.tw5 = m.width8;
                            m.th5 = m.height2;
                        }
                        else {
                            m.tw5 = 0;
                            m.th5 = 0;
                        }
                        coordinate_x=1; coordinate_y=1;
                        break;
                    case 6:
                        if(m.tw6==0 && m.th6==0) {
                            m.tw6 = m.width8;
                            m.th6 = m.height3;
                        }
                        else {
                            m.tw6 = 0;
                            m.th6 = 0;
                        }
                        coordinate_x=2; coordinate_y=1;
                        break;
                    case 7:
                        if(m.tw7==0 && m.th7==0) {
                            m.tw7 = m.width9;
                            m.th7 = m.height1;
                        }
                        else {
                            m.tw7 = 0;
                            m.th7 = 0;
                        }
                        coordinate_x=0; coordinate_y=2;
                        break;
                    case 8:
                        if(m.tw8==0 && m.th8==0) {
                            m.tw8 = m.width9;
                            m.th8 = m.height2;
                        }
                        else {
                            m.tw8 = 0;
                            m.th8 = 0;
                        }
                        coordinate_x=1; coordinate_y=2;
                        break;
                    case 9:
                        if(m.tw9==0 && m.th9==0) {
                            m.tw9 = m.width9;
                            m.th9 = m.height3;
                        }
                        else {
                            m.tw9 = 0;
                            m.th9 = 0;
                        }
                        coordinate_x=2; coordinate_y=2;
                        break;
                    case 10:
                        if(m.tw10==0 && m.th10==0) {
                            m.tw10 = m.width10;
                            m.th10 = m.height1;
                        }
                        else {
                            m.tw10 = 0;
                            m.th10 = 0;
                        }
                        coordinate_x=0; coordinate_y=3;
                        break;
                    case 11:
                        if(m.tw11==0 && m.th11==0) {
                            m.tw11 = m.width10;
                            m.th11 = m.height2;
                        }
                        else {
                            m.tw11 = 0;
                            m.th11 = 0;
                        }
                        coordinate_x=1; coordinate_y=3;
                        break;
                    case 12:
                        if(m.tw12==0 && m.th12==0) {
                            m.tw12 = m.width10;
                            m.th12 = m.height3;
                        }
                        else {
                            m.tw12 = 0;
                            m.th12 = 0;
                        }
                        coordinate_x=2; coordinate_y=3;
                        break;
                    case 13:
                        if(m.tw13==0 && m.th13==0) {
                            m.tw13 = m.width11;
                            m.th13 = m.height1;
                        }
                        else {
                            m.tw13 = 0;
                            m.th13 = 0;
                        }
                        coordinate_x=0; coordinate_y=4;
                        break;
                    case 14:
                        if(m.tw14==0 && m.th14==0) {
                            m.tw14 = m.width11;
                            m.th14 = m.height2;
                        }
                        else {
                            m.tw14 = 0;
                            m.th14 = 0;
                        }
                        coordinate_x=1; coordinate_y=4;
                        break;
                    case 15:
                        if(m.tw15==0 && m.th15==0) {
                            m.tw15 = m.width11;
                            m.th15 = m.height3;
                        }
                        else {
                            m.tw15 = 0;
                            m.th15 = 0;
                        }
                        coordinate_x=2; coordinate_y=4;
                        break;
                    case 16:
                        if(m.tw16==0 && m.th16==0) {
                            m.tw16 = m.width12;
                            m.th16 = m.height1;
                        }
                        else {
                            m.tw16 = 0;
                            m.th16 = 0;
                        }
                        coordinate_x=0; coordinate_y=5;
                        break;
                    case 17:
                        if(m.tw17==0 && m.th17==0) {
                            m.tw17 = m.width12;
                            m.th17 = m.height2;
                        }
                        else {
                            m.tw17 = 0;
                            m.th17 = 0;
                        }
                        coordinate_x=1; coordinate_y=5;
                        break;
                    case 18:
                        if(m.tw18==0 && m.th18==0) {
                            m.tw18 = m.width12;
                            m.th18 = m.height3;
                        }
                        else {
                            m.tw18 = 0;
                            m.th18 = 0;
                        }
                        coordinate_x=2; coordinate_y=5;
                        break;
                }
                if(m.Braille_insert[coordinate_x][coordinate_y]==0)
                    m.Braille_insert[coordinate_x][coordinate_y] = 1;
                else if (m.Braille_insert[coordinate_x][coordinate_y]==1)
                    m.Braille_insert[coordinate_x][coordinate_y] = 0;
        }
        else
            touch_insert_init();
    }

    public void touch_insert(int coordinate){
        for(int i = 0 ; i<18 ; i++)
            Braille_coordinate[i]=false;

        Braille_coordinate[coordinate]=true;
    }

    public void touch_insert_init(){
        for(int i = 0 ; i<18 ; i++)
            Braille_coordinate[i]=false;
    }
    public void touch_init(int coordinate){ //문지르기 기능을 위한 컨트롤 변수 초기화 함수
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
    public void onBackPressed() { //종료키를 눌렀을 경우 발생되는 함수
        m.quiz_view_init();
        m.page = 0;
        page = 0;
        finish();
    }
}
