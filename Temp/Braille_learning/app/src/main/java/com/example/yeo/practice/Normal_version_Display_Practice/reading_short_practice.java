package com.example.yeo.practice.Normal_version_Display_Practice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_quiz_sound.quiz_reading_service;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Normal_version_quiz.quiz_score;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.WHclass;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;

import java.util.ArrayList;

public class reading_short_practice extends FragmentActivity implements SpeechRecognizeListener {
    private SpeechRecognizerClient client;
    private SoundPool sound_pool;
    private int sound_beep;
    boolean next = false; // 다음문제로 이동하기 위한 변수

    ArrayList<String> texts;
    /*
    3칸 이하의 점자 퀴즈를 진행하는 클래스
    */
    reading_short_display m;
    int newdrag, olddrag;  //첫번째 손가락과 두번째 손가락의 x좌표를 저장할 변수
    int y1drag, y2drag;//첫번째 손가락과 두번째 손가락의 y좌표를 저장할 변수
    int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, result6 = 0;//문지르기 기능을 초기화 하기 위한 컨트롤 변수
    boolean click = true;
    static int page = 0;
    Intent i;
    int posx1, posx2, posy1, posy2;
    boolean enter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        sound_pool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound_beep = sound_pool.load(this, R.raw.reading_quiz_stt_start, 1);

        quiz_score.score = 0;


        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOption);


        m = new reading_short_display(this);
        m.setBackgroundColor(Color.rgb(22, 26, 44));
        setContentView(m);
        quiz_reading_service.menu_page = m.question;
        startService(new Intent(this, quiz_reading_service.class));
    }

    public void onDestroy() {
        super.onDestroy();
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }

    @Override
    public void onBackPressed() { //종료키를 눌렀을 경우 발생되는 함수
        if(m.question==4){
            quiz_reading_service.finish = true;
            quiz_reading_service.progress = true;
            startService(new Intent(this, quiz_reading_service.class));
        }
        else{
            quiz_reading_service.finish = true;
            startService(new Intent(this, quiz_reading_service.class));
        }
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        if (m.next == false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP: // 마지막 한개의 손가락을 화면에서 떨어트렸을 경우 화면 잠금을 해제함
                    if (click == false) {
                        click = true;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:// 첫번째 손가락을 화면에 터치하였을 경우
                    startService(new Intent(this, Sound_Manager.class));
                    m.x = (int) event.getX();// 현재 좌표의 x좌표 값을 저장
                    m.y = (int) event.getY();
                    if ((m.x == 0) && (m.y == 0)) { //좌표 초기값으로 지정된 곳을 터치하면 반응을 없앰
                        break;
                    } else {
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
                        else { //그외 지점을 터치할 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                            touch_init(0);
                            WHclass.number = 0;
                        }
                        switch (m.dot_count) {
                            case 1: //첫번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width2 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 2://두번째 칸의 구분선과 경고음이 발생되는 영역을 지정
                                if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width6 + (m.bigcircle * 2)) {
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
                        if (next == false) {
                            sound_pool.play(sound_beep, 1, 1, 0, 0, 1);

                            SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
                                    setApiKey(WHclass.APIKEY).
                                    setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);

                            client = builder.build();
                            client.setSpeechRecognizeListener(this);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    client.startRecording(false);
                                }
                            }, 1000);

                            next = true;
                            quiz_reading_service.question++;


                        }
                            /*

                            SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
                                    setApiKey(WHclass.APIKEY).
                                    setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);

                            client = builder.build();

                            client.setSpeechRecognizeListener(this);
                            client.startRecording(false);

                            next=true;
                            quiz_reading_service.question++;
                            */

                    } else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 상단으로 드래그 하는 경우 퀴즈 화면 종료
                        onBackPressed();
                    } else if (olddrag - newdrag > WHclass.Drag_space) { //다음화면으로 이동
                        if (next == true) {
                            next = false;
                            m.quiz_view_init();

                            if (quiz_reading_service.question == 4) {
                                onBackPressed();
                            } else if (quiz_reading_service.question < 4) {
                                quiz_reading_service.menu_page = ++m.question;
                                startService(new Intent(this, quiz_reading_service.class));
                            }
                        }
                    }
                    m.invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    click = false;
                    olddrag = (int) event.getX();
                    y1drag = (int) event.getY();
                    break;
            }
        } /*else { //다음 문제가 존재할 경우
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    posx1 = (int) event.getX();
                    posy1 = (int) event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    posx2 = (int) event.getX();
                    posy2 = (int) event.getY();
                    if (enter == true) {
                        page++;
                        quiz_reading_service.question++;
                        startService(new Intent(this, quiz_reading_service.class));
                        m.quiz_view_init();
                        m.invalidate();
                        m.next = false;
                    } else
                        enter = true;
                    break;
            }
        }*/
        return true;
    }

    public void touch_init(int coordinate) { //문지르기 기능을 위한 컨트롤 변수 초기화 함수
        result1 = 0;
        result2 = 0;
        result3 = 0;
        result4 = 0;
        result5 = 0;
        result6 = 0;

        switch (coordinate) {
            case 1:
                result1 = 1;
                break;
            case 2:
                result2 = 1;
                break;
            case 3:
                result3 = 1;
                break;
            case 4:
                result4 = 1;
                break;
            case 5:
                result5 = 1;
                break;
            case 6:
                result6 = 1;
                break;
            default:
                break;

        }
    }

    @Override
    public void onReady() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onBeginningOfSpeech() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onEndOfSpeech() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        //TODO implement interface DaumSpeechRecognizeListener method
        Log.e("reading_short_practice", "onError");
        next=false;
        MainActivity.Braille_TTS.TTS_Play("음성인식에 실패하였습니다. 다시 시도해주세요.");

        client = null;
    }

    @Override
    public void onPartialResult(String text) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onResults(Bundle results) {
        Log.i("reading_short_practice", "onResults");
        texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);


        final Activity activity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                String answer = "";

                if (m.dot_count == 1) answer = m.textname_1;
                else if (m.dot_count == 2) answer = m.textname_2;
                else if (m.dot_count == 3) answer = m.textname_3;
                else return;

                for (int i = 0; i < texts.size(); i++) {
                    if (answer.equals(texts.get(i)) == true) {
                        result = true;
                        break;
                    } else
                        continue;
                }
                m.print = true;

                // finishing일때는 처리하지 않는다.
                if (activity.isFinishing()) return;
                m.invalidate();
                if (result == true && quiz_reading_service.question != 4) {
                    MainActivity.Braille_TTS.TTS_Play("정답입니다. 다음 화면으로 이동하세요.");
                } else if (result == true && quiz_reading_service.question == 4) {
                    MainActivity.Braille_TTS.TTS_Play("정답입니다. 다음 화면을 이동하면 읽기 퀴즈가 종료됩니다.");
                } else if (result == false && quiz_reading_service.question != 4) {
                    MainActivity.Braille_TTS.TTS_Play("오답입니다. 정답은" + answer + "입니다. 점자를 다시 확인하고 다음 화면으로 이동하세요.");
                } else {
                    MainActivity.Braille_TTS.TTS_Play("오답입니다. 정답은" + answer + "입니다. 점자를 다시 확인하고 다음 화면을 이동하면 읽기 퀴즈가 종료됩니다.");
                }
            }
        });

        client.cancelRecording();
        client = null;
    }

    @Override
    public void onAudioLevel(float v) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onFinished() {
        Log.i("reading_short_practice", "onFinished");
    }
}
