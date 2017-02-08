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

import com.example.yeo.practice.Common_braille_data.dot_quiz_letter;
import com.example.yeo.practice.Common_braille_data.dot_quiz_word;
import com.example.yeo.practice.Common_quiz_sound.quiz_reading_service;
import com.example.yeo.practice.Common_quiz_sound.quiz_writing_service;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;

import java.util.ArrayList;

public class reading_long_practice extends FragmentActivity implements SpeechRecognizeListener {
    private SpeechRecognizerClient client;
    reading_long_display m;
    private SoundPool sound_pool;
    private int sound_beep;
    boolean next = false; // 다음문제로 이동하기 위한 변수
    ArrayList<String> texts;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag;
    int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, result6 = 0;
    boolean click = true;
    static int page = 0;
    int posx1, posx2, posy1, posy2;
    boolean enter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        sound_pool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound_beep = sound_pool.load(this, R.raw.reading_quiz_stt_start, 1);

        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOption);

        dot_quiz_letter dot1 = new dot_quiz_letter();
        dot_quiz_word dot2 = new dot_quiz_word(); // 단어퀴즈 단위의 점자 클래스 선언

        m = new reading_long_display(this);
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
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        if (m.next == false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP://마지막 손가락을 땠을 때 화면잠금을 품
                    if (click == false) {
                        click = true;
                    }
                case MotionEvent.ACTION_DOWN: // 손가락 1개를 이용하여 터치가 발생하였을 때
                    m.x = (int) event.getX();//x좌표를 저장
                    m.y = (int) event.getY();//y좌표를 저장
                    if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2))) {
                        break;
                    } else {
                         /*
                        현재 터치가 발생 된 좌표가 만약 돌출된 점자라면, 해당 점자번호를 남성의 음성으로 안내하면서 강한 진동을 발생시킴
                        현재 터치가 발생 된 좌표가 만약 비돌출된 점자라면, 해당 점자번호를 여성의 음성으로 안내함
                         */
                        if (m.x < m.notarget7_width[0][0] + m.bigcircle && m.x > m.notarget7_width[0][0] - m.bigcircle && m.y < m.notarget7_height[0][0] + m.bigcircle && m.y > m.notarget7_height[0][0] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][0] + m.bigcircle && m.x > m.target7_width[0][0] - m.bigcircle && m.y < m.target7_height[0][0] + m.bigcircle && m.y > m.target7_height[0][0] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));

                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //첫번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][0] + m.bigcircle && m.x > m.notarget7_width[1][0] - m.bigcircle && m.y < m.notarget7_height[1][0] + m.bigcircle && m.y > m.notarget7_height[1][0] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][0] + m.bigcircle && m.x > m.target7_width[1][0] - m.bigcircle && m.y < m.target7_height[1][0] + m.bigcircle && m.y > m.target7_height[1][0] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //첫번째 칸의 2번 점자

                        else if (m.x < m.notarget7_width[2][0] + m.bigcircle && m.x > m.notarget7_width[2][0] - m.bigcircle && m.y < m.notarget7_height[2][0] + m.bigcircle && m.y > m.notarget7_height[2][0] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][0] + m.bigcircle && m.x > m.target7_width[2][0] - m.bigcircle && m.y < m.target7_height[2][0] + m.bigcircle && m.y > m.target7_height[2][0] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //첫번째 칸의 3번 점자

                        else if (m.x < m.notarget7_width[0][1] + m.bigcircle && m.x > m.notarget7_width[0][1] - m.bigcircle && m.y < m.notarget7_height[0][1] + m.bigcircle && m.y > m.notarget7_height[0][1] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][1] + m.bigcircle && m.x > m.target7_width[0][1] - m.bigcircle && m.y < m.target7_height[0][1] + m.bigcircle && m.y > m.target7_height[0][1] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //첫번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][1] + m.bigcircle && m.x > m.notarget7_width[1][1] - m.bigcircle && m.y < m.notarget7_height[1][1] + m.bigcircle && m.y > m.notarget7_height[1][1] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][1] + m.bigcircle && m.x > m.target7_width[1][1] - m.bigcircle && m.y < m.target7_height[1][1] + m.bigcircle && m.y > m.target7_height[1][1] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //첫번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][1] + m.bigcircle && m.x > m.notarget7_width[2][1] - m.bigcircle && m.y < m.notarget7_height[2][1] + m.bigcircle && m.y > m.notarget7_height[2][1] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][1] + m.bigcircle && m.x > m.target7_width[2][1] - m.bigcircle && m.y < m.target7_height[2][1] + m.bigcircle && m.y > m.target7_height[2][1] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //첫번째 칸의 6번 점자

                        else if (m.x < m.notarget7_width[0][2] + m.bigcircle && m.x > m.notarget7_width[0][2] - m.bigcircle && m.y < m.notarget7_height[0][2] + m.bigcircle && m.y > m.notarget7_height[0][2] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][2] + m.bigcircle && m.x > m.target7_width[0][2] - m.bigcircle && m.y < m.target7_height[0][2] + m.bigcircle && m.y > m.target7_height[0][2] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //두번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][2] + m.bigcircle && m.x > m.notarget7_width[1][2] - m.bigcircle && m.y < m.notarget7_height[1][2] + m.bigcircle && m.y > m.notarget7_height[1][2] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][2] + m.bigcircle && m.x > m.target7_width[1][2] - m.bigcircle && m.y < m.target7_height[1][2] + m.bigcircle && m.y > m.target7_height[1][2] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //두번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][2] + m.bigcircle && m.x > m.notarget7_width[2][2] - m.bigcircle && m.y < m.notarget7_height[2][2] + m.bigcircle && m.y > m.notarget7_height[2][2] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][2] + m.bigcircle && m.x > m.target7_width[2][2] - m.bigcircle && m.y < m.target7_height[2][2] + m.bigcircle && m.y > m.target7_height[2][2] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //두번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][3] + m.bigcircle && m.x > m.notarget7_width[0][3] - m.bigcircle && m.y < m.notarget7_height[0][3] + m.bigcircle && m.y > m.notarget7_height[0][3] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][3] + m.bigcircle && m.x > m.target7_width[0][3] - m.bigcircle && m.y < m.target7_height[0][3] + m.bigcircle && m.y > m.target7_height[0][3] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //두번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][3] + m.bigcircle && m.x > m.notarget7_width[1][3] - m.bigcircle && m.y < m.notarget7_height[1][3] + m.bigcircle && m.y > m.notarget7_height[1][3] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][3] + m.bigcircle && m.x > m.target7_width[1][3] - m.bigcircle && m.y < m.target7_height[1][3] + m.bigcircle && m.y > m.target7_height[1][3] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //두번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][3] + m.bigcircle && m.x > m.notarget7_width[2][3] - m.bigcircle && m.y < m.notarget7_height[2][3] + m.bigcircle && m.y > m.notarget7_height[2][3] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][3] + m.bigcircle && m.x > m.target7_width[2][3] - m.bigcircle && m.y < m.target7_height[2][3] + m.bigcircle && m.y > m.target7_height[2][3] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //두번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][4] + m.bigcircle && m.x > m.notarget7_width[0][4] - m.bigcircle && m.y < m.notarget7_height[0][4] + m.bigcircle && m.y > m.notarget7_height[0][4] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][4] + m.bigcircle && m.x > m.target7_width[0][4] - m.bigcircle && m.y < m.target7_height[0][4] + m.bigcircle && m.y > m.target7_height[0][4] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //세번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][4] + m.bigcircle && m.x > m.notarget7_width[1][4] - m.bigcircle && m.y < m.notarget7_height[1][4] + m.bigcircle && m.y > m.notarget7_height[1][4] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][4] + m.bigcircle && m.x > m.target7_width[1][4] - m.bigcircle && m.y < m.target7_height[1][4] + m.bigcircle && m.y > m.target7_height[1][4] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //세번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][4] + m.bigcircle && m.x > m.notarget7_width[2][4] - m.bigcircle && m.y < m.notarget7_height[2][4] + m.bigcircle && m.y > m.notarget7_height[2][4] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][4] + m.bigcircle && m.x > m.target7_width[2][4] - m.bigcircle && m.y < m.target7_height[2][4] + m.bigcircle && m.y > m.target7_height[2][4] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //세번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][5] + m.bigcircle && m.x > m.notarget7_width[0][5] - m.bigcircle && m.y < m.notarget7_height[0][5] + m.bigcircle && m.y > m.notarget7_height[0][5] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][5] + m.bigcircle && m.x > m.target7_width[0][5] - m.bigcircle && m.y < m.target7_height[0][5] + m.bigcircle && m.y > m.target7_height[0][5] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //세번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][5] + m.bigcircle && m.x > m.notarget7_width[1][5] - m.bigcircle && m.y < m.notarget7_height[1][5] + m.bigcircle && m.y > m.notarget7_height[1][5] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][5] + m.bigcircle && m.x > m.target7_width[1][5] - m.bigcircle && m.y < m.target7_height[1][5] + m.bigcircle && m.y > m.target7_height[1][5] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //세번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][5] + m.bigcircle && m.x > m.notarget7_width[2][5] - m.bigcircle && m.y < m.notarget7_height[2][5] + m.bigcircle && m.y > m.notarget7_height[2][5] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][5] + m.bigcircle && m.x > m.target7_width[2][5] - m.bigcircle && m.y < m.target7_height[2][5] + m.bigcircle && m.y > m.target7_height[2][5] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //세번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][6] + m.bigcircle && m.x > m.notarget7_width[0][6] - m.bigcircle && m.y < m.notarget7_height[0][6] + m.bigcircle && m.y > m.notarget7_height[0][6] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][6] + m.bigcircle && m.x > m.target7_width[0][6] - m.bigcircle && m.y < m.target7_height[0][6] + m.bigcircle && m.y > m.target7_height[0][6] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //네번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][6] + m.bigcircle && m.x > m.notarget7_width[1][6] - m.bigcircle && m.y < m.notarget7_height[1][6] + m.bigcircle && m.y > m.notarget7_height[1][6] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][6] + m.bigcircle && m.x > m.target7_width[1][6] - m.bigcircle && m.y < m.target7_height[1][6] + m.bigcircle && m.y > m.target7_height[1][6] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //네번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][6] + m.bigcircle && m.x > m.notarget7_width[2][6] - m.bigcircle && m.y < m.notarget7_height[2][6] + m.bigcircle && m.y > m.notarget7_height[2][6] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][6] + m.bigcircle && m.x > m.target7_width[2][6] - m.bigcircle && m.y < m.target7_height[2][6] + m.bigcircle && m.y > m.target7_height[2][6] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //네번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][7] + m.bigcircle && m.x > m.notarget7_width[0][7] - m.bigcircle && m.y < m.notarget7_height[0][7] + m.bigcircle && m.y > m.notarget7_height[0][7] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][7] + m.bigcircle && m.x > m.target7_width[0][7] - m.bigcircle && m.y < m.target7_height[0][7] + m.bigcircle && m.y > m.target7_height[0][7] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //네번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][7] + m.bigcircle && m.x > m.notarget7_width[1][7] - m.bigcircle && m.y < m.notarget7_height[1][7] + m.bigcircle && m.y > m.notarget7_height[1][7] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][7] + m.bigcircle && m.x > m.target7_width[1][7] - m.bigcircle && m.y < m.target7_height[1][7] + m.bigcircle && m.y > m.target7_height[1][7] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //네번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][7] + m.bigcircle && m.x > m.notarget7_width[2][7] - m.bigcircle && m.y < m.notarget7_height[2][7] + m.bigcircle && m.y > m.notarget7_height[2][7] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][7] + m.bigcircle && m.x > m.target7_width[2][7] - m.bigcircle && m.y < m.target7_height[2][7] + m.bigcircle && m.y > m.target7_height[2][7] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //네번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][8] + m.bigcircle && m.x > m.notarget7_width[0][8] - m.bigcircle && m.y < m.notarget7_height[0][8] + m.bigcircle && m.y > m.notarget7_height[0][8] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][8] + m.bigcircle && m.x > m.target7_width[0][8] - m.bigcircle && m.y < m.target7_height[0][8] + m.bigcircle && m.y > m.target7_height[0][8] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //다섯번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][8] + m.bigcircle && m.x > m.notarget7_width[1][8] - m.bigcircle && m.y < m.notarget7_height[1][8] + m.bigcircle && m.y > m.notarget7_height[1][8] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][8] + m.bigcircle && m.x > m.target7_width[1][8] - m.bigcircle && m.y < m.target7_height[1][8] + m.bigcircle && m.y > m.target7_height[1][8] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //다섯번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][8] + m.bigcircle && m.x > m.notarget7_width[2][8] - m.bigcircle && m.y < m.notarget7_height[2][8] + m.bigcircle && m.y > m.notarget7_height[2][8] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][8] + m.bigcircle && m.x > m.target7_width[2][8] - m.bigcircle && m.y < m.target7_height[2][8] + m.bigcircle && m.y > m.target7_height[2][8] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //다섯번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][9] + m.bigcircle && m.x > m.notarget7_width[0][9] - m.bigcircle && m.y < m.notarget7_height[0][9] + m.bigcircle && m.y > m.notarget7_height[0][9] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][9] + m.bigcircle && m.x > m.target7_width[0][9] - m.bigcircle && m.y < m.target7_height[0][9] + m.bigcircle && m.y > m.target7_height[0][9] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //다섯번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][9] + m.bigcircle && m.x > m.notarget7_width[1][9] - m.bigcircle && m.y < m.notarget7_height[1][9] + m.bigcircle && m.y > m.notarget7_height[1][9] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][9] + m.bigcircle && m.x > m.target7_width[1][9] - m.bigcircle && m.y < m.target7_height[1][9] + m.bigcircle && m.y > m.target7_height[1][9] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //다섯번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][9] + m.bigcircle && m.x > m.notarget7_width[2][9] - m.bigcircle && m.y < m.notarget7_height[2][9] + m.bigcircle && m.y > m.notarget7_height[2][9] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][9] + m.bigcircle && m.x > m.target7_width[2][9] - m.bigcircle && m.y < m.target7_height[2][9] + m.bigcircle && m.y > m.target7_height[2][9] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //다섯번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][10] + m.bigcircle && m.x > m.notarget7_width[0][10] - m.bigcircle && m.y < m.notarget7_height[0][10] + m.bigcircle && m.y > m.notarget7_height[0][10] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][10] + m.bigcircle && m.x > m.target7_width[0][10] - m.bigcircle && m.y < m.target7_height[0][10] + m.bigcircle && m.y > m.target7_height[0][10] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //여섯번쨰 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][10] + m.bigcircle && m.x > m.notarget7_width[1][10] - m.bigcircle && m.y < m.notarget7_height[1][10] + m.bigcircle && m.y > m.notarget7_height[1][10] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][10] + m.bigcircle && m.x > m.target7_width[1][10] - m.bigcircle && m.y < m.target7_height[1][10] + m.bigcircle && m.y > m.target7_height[1][10] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //여섯번쨰 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][10] + m.bigcircle && m.x > m.notarget7_width[2][10] - m.bigcircle && m.y < m.notarget7_height[2][10] + m.bigcircle && m.y > m.notarget7_height[2][10] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][10] + m.bigcircle && m.x > m.target7_width[2][10] - m.bigcircle && m.y < m.target7_height[2][10] + m.bigcircle && m.y > m.target7_height[2][10] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //여섯번쨰 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][11] + m.bigcircle && m.x > m.notarget7_width[0][11] - m.bigcircle && m.y < m.notarget7_height[0][11] + m.bigcircle && m.y > m.notarget7_height[0][11] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][11] + m.bigcircle && m.x > m.target7_width[0][11] - m.bigcircle && m.y < m.target7_height[0][11] + m.bigcircle && m.y > m.target7_height[0][11] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //여섯번쨰 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][11] + m.bigcircle && m.x > m.notarget7_width[1][11] - m.bigcircle && m.y < m.notarget7_height[1][11] + m.bigcircle && m.y > m.notarget7_height[1][11] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][11] + m.bigcircle && m.x > m.target7_width[1][11] - m.bigcircle && m.y < m.target7_height[1][11] + m.bigcircle && m.y > m.target7_height[1][11] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //여섯번쨰 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][11] + m.bigcircle && m.x > m.notarget7_width[2][11] - m.bigcircle && m.y < m.notarget7_height[2][11] + m.bigcircle && m.y > m.notarget7_height[2][11] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][11] + m.bigcircle && m.x > m.target7_width[2][11] - m.bigcircle && m.y < m.target7_height[2][11] + m.bigcircle && m.y > m.target7_height[2][11] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //여섯번쨰 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][12] + m.bigcircle && m.x > m.notarget7_width[0][12] - m.bigcircle && m.y < m.notarget7_height[0][12] + m.bigcircle && m.y > m.notarget7_height[0][12] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][12] + m.bigcircle && m.x > m.target7_width[0][12] - m.bigcircle && m.y < m.target7_height[0][12] + m.bigcircle && m.y > m.target7_height[0][12] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //일곱번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][12] + m.bigcircle && m.x > m.notarget7_width[1][12] - m.bigcircle && m.y < m.notarget7_height[1][12] + m.bigcircle && m.y > m.notarget7_height[1][12] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][12] + m.bigcircle && m.x > m.target7_width[1][12] - m.bigcircle && m.y < m.target7_height[1][12] + m.bigcircle && m.y > m.target7_height[1][12] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //일곱번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][12] + m.bigcircle && m.x > m.notarget7_width[2][12] - m.bigcircle && m.y < m.notarget7_height[2][12] + m.bigcircle && m.y > m.notarget7_height[2][12] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][12] + m.bigcircle && m.x > m.target7_width[2][12] - m.bigcircle && m.y < m.target7_height[2][12] + m.bigcircle && m.y > m.target7_height[2][12] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //일곱번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][13] + m.bigcircle && m.x > m.notarget7_width[0][13] - m.bigcircle && m.y < m.notarget7_height[0][13] + m.bigcircle && m.y > m.notarget7_height[0][13] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][13] + m.bigcircle && m.x > m.target7_width[0][13] - m.bigcircle && m.y < m.target7_height[0][13] + m.bigcircle && m.y > m.target7_height[0][13] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //일곱번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][13] + m.bigcircle && m.x > m.notarget7_width[1][13] - m.bigcircle && m.y < m.notarget7_height[1][13] + m.bigcircle && m.y > m.notarget7_height[1][13] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][13] + m.bigcircle && m.x > m.target7_width[1][13] - m.bigcircle && m.y < m.target7_height[1][13] + m.bigcircle && m.y > m.target7_height[1][13] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //일곱번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][13] + m.bigcircle && m.x > m.notarget7_width[2][13] - m.bigcircle && m.y < m.notarget7_height[2][13] + m.bigcircle && m.y > m.notarget7_height[2][13] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][13] + m.bigcircle && m.x > m.target7_width[2][13] - m.bigcircle && m.y < m.target7_height[2][13] + m.bigcircle && m.y > m.target7_height[2][13] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //일곱번째 칸의 6번 점자
                        else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle) {
                            WHclass.number = 7;
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Weak_vibe);
                            touch_init(0);
                        } else { //그 외 지점을 터치할 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                            touch_init(0);
                        }
                        switch (m.dot_count) {
                            case 1://점자의 칸 수가 한 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width2 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 2://점자의 칸 수가 두 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width4 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 3://점자의 칸 수가 세 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
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
                            case 4://점자의 칸 수가 네 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width8 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 5://점자의 칸 수가 다섯 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
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
                            case 6://점자의 칸 수가 여섯 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
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
                            case 7://점자의 칸 수가 일곱 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
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
                                } else if (m.x > m.width12 + m.bigcircle && m.x < m.width13 - m.bigcircle) {
                                    WHclass.number = 8;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width14 + m.bigcircle && m.x < m.width14 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                        }

                    }
                    m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                    break;

                case MotionEvent.ACTION_MOVE:// 화면을 터치한 상태로 움직일때 발생되는 이벤트
                    m.x = (int) event.getX();// 현재 터치한 지점의 x좌표를 저장
                    m.y = (int) event.getY();// 현재 터치한 지점의 y좌표를 저장
                    if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2))) {
                        break;
                    }

                     /*
                    터치한 지점의 좌표가 돌출된 점자일 경우 남성의 음성으로 점자번호를 안내하면서 강한진동이 발생
                    터치한 지점의 좌표가 비돌출된 점자일 경우 여성의 음성으로 점자번호를 안내함
                     */
                    if (click == true) {
                        if (m.x < m.notarget7_width[0][0] + m.bigcircle && m.x > m.notarget7_width[0][0] - m.bigcircle && m.y < m.notarget7_height[0][0] + m.bigcircle && m.y > m.notarget7_height[0][0] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][0] + m.bigcircle && m.x > m.target7_width[0][0] - m.bigcircle && m.y < m.target7_height[0][0] + m.bigcircle && m.y > m.target7_height[0][0] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //첫번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][0] + m.bigcircle && m.x > m.notarget7_width[1][0] - m.bigcircle && m.y < m.notarget7_height[1][0] + m.bigcircle && m.y > m.notarget7_height[1][0] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][0] + m.bigcircle && m.x > m.target7_width[1][0] - m.bigcircle && m.y < m.target7_height[1][0] + m.bigcircle && m.y > m.target7_height[1][0] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //첫번째 칸의 2번 점자

                        else if (m.x < m.notarget7_width[2][0] + m.bigcircle && m.x > m.notarget7_width[2][0] - m.bigcircle && m.y < m.notarget7_height[2][0] + m.bigcircle && m.y > m.notarget7_height[2][0] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][0] + m.bigcircle && m.x > m.target7_width[2][0] - m.bigcircle && m.y < m.target7_height[2][0] + m.bigcircle && m.y > m.target7_height[2][0] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //첫번째 칸의 3번 점자

                        else if (m.x < m.notarget7_width[0][1] + m.bigcircle && m.x > m.notarget7_width[0][1] - m.bigcircle && m.y < m.notarget7_height[0][1] + m.bigcircle && m.y > m.notarget7_height[0][1] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][1] + m.bigcircle && m.x > m.target7_width[0][1] - m.bigcircle && m.y < m.target7_height[0][1] + m.bigcircle && m.y > m.target7_height[0][1] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //첫번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][1] + m.bigcircle && m.x > m.notarget7_width[1][1] - m.bigcircle && m.y < m.notarget7_height[1][1] + m.bigcircle && m.y > m.notarget7_height[1][1] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][1] + m.bigcircle && m.x > m.target7_width[1][1] - m.bigcircle && m.y < m.target7_height[1][1] + m.bigcircle && m.y > m.target7_height[1][1] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //첫번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][1] + m.bigcircle && m.x > m.notarget7_width[2][1] - m.bigcircle && m.y < m.notarget7_height[2][1] + m.bigcircle && m.y > m.notarget7_height[2][1] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][1] + m.bigcircle && m.x > m.target7_width[2][1] - m.bigcircle && m.y < m.target7_height[2][1] + m.bigcircle && m.y > m.target7_height[2][1] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //첫번째 칸의 6번 점자

                        else if (m.x < m.notarget7_width[0][2] + m.bigcircle && m.x > m.notarget7_width[0][2] - m.bigcircle && m.y < m.notarget7_height[0][2] + m.bigcircle && m.y > m.notarget7_height[0][2] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][2] + m.bigcircle && m.x > m.target7_width[0][2] - m.bigcircle && m.y < m.target7_height[0][2] + m.bigcircle && m.y > m.target7_height[0][2] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //두번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][2] + m.bigcircle && m.x > m.notarget7_width[1][2] - m.bigcircle && m.y < m.notarget7_height[1][2] + m.bigcircle && m.y > m.notarget7_height[1][2] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][2] + m.bigcircle && m.x > m.target7_width[1][2] - m.bigcircle && m.y < m.target7_height[1][2] + m.bigcircle && m.y > m.target7_height[1][2] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //두번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][2] + m.bigcircle && m.x > m.notarget7_width[2][2] - m.bigcircle && m.y < m.notarget7_height[2][2] + m.bigcircle && m.y > m.notarget7_height[2][2] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][2] + m.bigcircle && m.x > m.target7_width[2][2] - m.bigcircle && m.y < m.target7_height[2][2] + m.bigcircle && m.y > m.target7_height[2][2] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //두번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][3] + m.bigcircle && m.x > m.notarget7_width[0][3] - m.bigcircle && m.y < m.notarget7_height[0][3] + m.bigcircle && m.y > m.notarget7_height[0][3] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][3] + m.bigcircle && m.x > m.target7_width[0][3] - m.bigcircle && m.y < m.target7_height[0][3] + m.bigcircle && m.y > m.target7_height[0][3] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //두번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][3] + m.bigcircle && m.x > m.notarget7_width[1][3] - m.bigcircle && m.y < m.notarget7_height[1][3] + m.bigcircle && m.y > m.notarget7_height[1][3] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][3] + m.bigcircle && m.x > m.target7_width[1][3] - m.bigcircle && m.y < m.target7_height[1][3] + m.bigcircle && m.y > m.target7_height[1][3] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //두번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][3] + m.bigcircle && m.x > m.notarget7_width[2][3] - m.bigcircle && m.y < m.notarget7_height[2][3] + m.bigcircle && m.y > m.notarget7_height[2][3] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][3] + m.bigcircle && m.x > m.target7_width[2][3] - m.bigcircle && m.y < m.target7_height[2][3] + m.bigcircle && m.y > m.target7_height[2][3] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //두번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][4] + m.bigcircle && m.x > m.notarget7_width[0][4] - m.bigcircle && m.y < m.notarget7_height[0][4] + m.bigcircle && m.y > m.notarget7_height[0][4] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][4] + m.bigcircle && m.x > m.target7_width[0][4] - m.bigcircle && m.y < m.target7_height[0][4] + m.bigcircle && m.y > m.target7_height[0][4] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //세번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][4] + m.bigcircle && m.x > m.notarget7_width[1][4] - m.bigcircle && m.y < m.notarget7_height[1][4] + m.bigcircle && m.y > m.notarget7_height[1][4] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][4] + m.bigcircle && m.x > m.target7_width[1][4] - m.bigcircle && m.y < m.target7_height[1][4] + m.bigcircle && m.y > m.target7_height[1][4] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //세번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][4] + m.bigcircle && m.x > m.notarget7_width[2][4] - m.bigcircle && m.y < m.notarget7_height[2][4] + m.bigcircle && m.y > m.notarget7_height[2][4] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][4] + m.bigcircle && m.x > m.target7_width[2][4] - m.bigcircle && m.y < m.target7_height[2][4] + m.bigcircle && m.y > m.target7_height[2][4] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //세번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][5] + m.bigcircle && m.x > m.notarget7_width[0][5] - m.bigcircle && m.y < m.notarget7_height[0][5] + m.bigcircle && m.y > m.notarget7_height[0][5] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][5] + m.bigcircle && m.x > m.target7_width[0][5] - m.bigcircle && m.y < m.target7_height[0][5] + m.bigcircle && m.y > m.target7_height[0][5] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //세번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][5] + m.bigcircle && m.x > m.notarget7_width[1][5] - m.bigcircle && m.y < m.notarget7_height[1][5] + m.bigcircle && m.y > m.notarget7_height[1][5] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][5] + m.bigcircle && m.x > m.target7_width[1][5] - m.bigcircle && m.y < m.target7_height[1][5] + m.bigcircle && m.y > m.target7_height[1][5] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //세번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][5] + m.bigcircle && m.x > m.notarget7_width[2][5] - m.bigcircle && m.y < m.notarget7_height[2][5] + m.bigcircle && m.y > m.notarget7_height[2][5] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][5] + m.bigcircle && m.x > m.target7_width[2][5] - m.bigcircle && m.y < m.target7_height[2][5] + m.bigcircle && m.y > m.target7_height[2][5] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //세번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][6] + m.bigcircle && m.x > m.notarget7_width[0][6] - m.bigcircle && m.y < m.notarget7_height[0][6] + m.bigcircle && m.y > m.notarget7_height[0][6] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][6] + m.bigcircle && m.x > m.target7_width[0][6] - m.bigcircle && m.y < m.target7_height[0][6] + m.bigcircle && m.y > m.target7_height[0][6] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //네번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][6] + m.bigcircle && m.x > m.notarget7_width[1][6] - m.bigcircle && m.y < m.notarget7_height[1][6] + m.bigcircle && m.y > m.notarget7_height[1][6] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][6] + m.bigcircle && m.x > m.target7_width[1][6] - m.bigcircle && m.y < m.target7_height[1][6] + m.bigcircle && m.y > m.target7_height[1][6] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            } //네번째 칸의 2번 점자
                        } else if (m.x < m.notarget7_width[2][6] + m.bigcircle && m.x > m.notarget7_width[2][6] - m.bigcircle && m.y < m.notarget7_height[2][6] + m.bigcircle && m.y > m.notarget7_height[2][6] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][6] + m.bigcircle && m.x > m.target7_width[2][6] - m.bigcircle && m.y < m.target7_height[2][6] + m.bigcircle && m.y > m.target7_height[2][6] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //네번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][7] + m.bigcircle && m.x > m.notarget7_width[0][7] - m.bigcircle && m.y < m.notarget7_height[0][7] + m.bigcircle && m.y > m.notarget7_height[0][7] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][7] + m.bigcircle && m.x > m.target7_width[0][7] - m.bigcircle && m.y < m.target7_height[0][7] + m.bigcircle && m.y > m.target7_height[0][7] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //네번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][7] + m.bigcircle && m.x > m.notarget7_width[1][7] - m.bigcircle && m.y < m.notarget7_height[1][7] + m.bigcircle && m.y > m.notarget7_height[1][7] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][7] + m.bigcircle && m.x > m.target7_width[1][7] - m.bigcircle && m.y < m.target7_height[1][7] + m.bigcircle && m.y > m.target7_height[1][7] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //네번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][7] + m.bigcircle && m.x > m.notarget7_width[2][7] - m.bigcircle && m.y < m.notarget7_height[2][7] + m.bigcircle && m.y > m.notarget7_height[2][7] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][7] + m.bigcircle && m.x > m.target7_width[2][7] - m.bigcircle && m.y < m.target7_height[2][7] + m.bigcircle && m.y > m.target7_height[2][7] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //네번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][8] + m.bigcircle && m.x > m.notarget7_width[0][8] - m.bigcircle && m.y < m.notarget7_height[0][8] + m.bigcircle && m.y > m.notarget7_height[0][8] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][8] + m.bigcircle && m.x > m.target7_width[0][8] - m.bigcircle && m.y < m.target7_height[0][8] + m.bigcircle && m.y > m.target7_height[0][8] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //다섯번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][8] + m.bigcircle && m.x > m.notarget7_width[1][8] - m.bigcircle && m.y < m.notarget7_height[1][8] + m.bigcircle && m.y > m.notarget7_height[1][8] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][8] + m.bigcircle && m.x > m.target7_width[1][8] - m.bigcircle && m.y < m.target7_height[1][8] + m.bigcircle && m.y > m.target7_height[1][8] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //다섯번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][8] + m.bigcircle && m.x > m.notarget7_width[2][8] - m.bigcircle && m.y < m.notarget7_height[2][8] + m.bigcircle && m.y > m.notarget7_height[2][8] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][8] + m.bigcircle && m.x > m.target7_width[2][8] - m.bigcircle && m.y < m.target7_height[2][8] + m.bigcircle && m.y > m.target7_height[2][8] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //다섯번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][9] + m.bigcircle && m.x > m.notarget7_width[0][9] - m.bigcircle && m.y < m.notarget7_height[0][9] + m.bigcircle && m.y > m.notarget7_height[0][9] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][9] + m.bigcircle && m.x > m.target7_width[0][9] - m.bigcircle && m.y < m.target7_height[0][9] + m.bigcircle && m.y > m.target7_height[0][9] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //다섯번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][9] + m.bigcircle && m.x > m.notarget7_width[1][9] - m.bigcircle && m.y < m.notarget7_height[1][9] + m.bigcircle && m.y > m.notarget7_height[1][9] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][9] + m.bigcircle && m.x > m.target7_width[1][9] - m.bigcircle && m.y < m.target7_height[1][9] + m.bigcircle && m.y > m.target7_height[1][9] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //다섯번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][9] + m.bigcircle && m.x > m.notarget7_width[2][9] - m.bigcircle && m.y < m.notarget7_height[2][9] + m.bigcircle && m.y > m.notarget7_height[2][9] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][9] + m.bigcircle && m.x > m.target7_width[2][9] - m.bigcircle && m.y < m.target7_height[2][9] + m.bigcircle && m.y > m.target7_height[2][9] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //다섯번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][10] + m.bigcircle && m.x > m.notarget7_width[0][10] - m.bigcircle && m.y < m.notarget7_height[0][10] + m.bigcircle && m.y > m.notarget7_height[0][10] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][10] + m.bigcircle && m.x > m.target7_width[0][10] - m.bigcircle && m.y < m.target7_height[0][10] + m.bigcircle && m.y > m.target7_height[0][10] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //여섯번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][10] + m.bigcircle && m.x > m.notarget7_width[1][10] - m.bigcircle && m.y < m.notarget7_height[1][10] + m.bigcircle && m.y > m.notarget7_height[1][10] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][10] + m.bigcircle && m.x > m.target7_width[1][10] - m.bigcircle && m.y < m.target7_height[1][10] + m.bigcircle && m.y > m.target7_height[1][10] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //여섯번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][10] + m.bigcircle && m.x > m.notarget7_width[2][10] - m.bigcircle && m.y < m.notarget7_height[2][10] + m.bigcircle && m.y > m.notarget7_height[2][10] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][10] + m.bigcircle && m.x > m.target7_width[2][10] - m.bigcircle && m.y < m.target7_height[2][10] + m.bigcircle && m.y > m.target7_height[2][10] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //여섯번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][11] + m.bigcircle && m.x > m.notarget7_width[0][11] - m.bigcircle && m.y < m.notarget7_height[0][11] + m.bigcircle && m.y > m.notarget7_height[0][11] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][11] + m.bigcircle && m.x > m.target7_width[0][11] - m.bigcircle && m.y < m.target7_height[0][11] + m.bigcircle && m.y > m.target7_height[0][11] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //여섯번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][11] + m.bigcircle && m.x > m.notarget7_width[1][11] - m.bigcircle && m.y < m.notarget7_height[1][11] + m.bigcircle && m.y > m.notarget7_height[1][11] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][11] + m.bigcircle && m.x > m.target7_width[1][11] - m.bigcircle && m.y < m.target7_height[1][11] + m.bigcircle && m.y > m.target7_height[1][11] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //여섯번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][11] + m.bigcircle && m.x > m.notarget7_width[2][11] - m.bigcircle && m.y < m.notarget7_height[2][11] + m.bigcircle && m.y > m.notarget7_height[2][11] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][11] + m.bigcircle && m.x > m.target7_width[2][11] - m.bigcircle && m.y < m.target7_height[2][11] + m.bigcircle && m.y > m.target7_height[2][11] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //여섯번째 칸의 6번 점자
                        else if (m.x < m.notarget7_width[0][12] + m.bigcircle && m.x > m.notarget7_width[0][12] - m.bigcircle && m.y < m.notarget7_height[0][12] + m.bigcircle && m.y > m.notarget7_height[0][12] - m.bigcircle) {
                            WHclass.number = 1;
                            if (result1 == 0) {
                                if (m.x < m.target7_width[0][12] + m.bigcircle && m.x > m.target7_width[0][12] - m.bigcircle && m.y < m.target7_height[0][12] + m.bigcircle && m.y > m.target7_height[0][12] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(1);
                            }
                        } //일곱번째 칸의 1번 점자
                        else if (m.x < m.notarget7_width[1][12] + m.bigcircle && m.x > m.notarget7_width[1][12] - m.bigcircle && m.y < m.notarget7_height[1][12] + m.bigcircle && m.y > m.notarget7_height[1][12] - m.bigcircle) {
                            WHclass.number = 2;
                            if (result2 == 0) {
                                if (m.x < m.target7_width[1][12] + m.bigcircle && m.x > m.target7_width[1][12] - m.bigcircle && m.y < m.target7_height[1][12] + m.bigcircle && m.y > m.target7_height[1][12] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(2);
                            }
                        } //일곱번째 칸의 2번 점자
                        else if (m.x < m.notarget7_width[2][12] + m.bigcircle && m.x > m.notarget7_width[2][12] - m.bigcircle && m.y < m.notarget7_height[2][12] + m.bigcircle && m.y > m.notarget7_height[2][12] - m.bigcircle) {
                            WHclass.number = 3;
                            if (result3 == 0) {
                                if (m.x < m.target7_width[2][12] + m.bigcircle && m.x > m.target7_width[2][12] - m.bigcircle && m.y < m.target7_height[2][12] + m.bigcircle && m.y > m.target7_height[2][12] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(3);
                            }
                        } //일곱번째 칸의 3번 점자
                        else if (m.x < m.notarget7_width[0][13] + m.bigcircle && m.x > m.notarget7_width[0][13] - m.bigcircle && m.y < m.notarget7_height[0][13] + m.bigcircle && m.y > m.notarget7_height[0][13] - m.bigcircle) {
                            WHclass.number = 4;
                            if (result4 == 0) {
                                if (m.x < m.target7_width[0][13] + m.bigcircle && m.x > m.target7_width[0][13] - m.bigcircle && m.y < m.target7_height[0][13] + m.bigcircle && m.y > m.target7_height[0][13] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(4);
                            }
                        } //일곱번째 칸의 4번 점자
                        else if (m.x < m.notarget7_width[1][13] + m.bigcircle && m.x > m.notarget7_width[1][13] - m.bigcircle && m.y < m.notarget7_height[1][13] + m.bigcircle && m.y > m.notarget7_height[1][13] - m.bigcircle) {
                            WHclass.number = 5;
                            if (result5 == 0) {
                                if (m.x < m.target7_width[1][13] + m.bigcircle && m.x > m.target7_width[1][13] - m.bigcircle && m.y < m.target7_height[1][13] + m.bigcircle && m.y > m.target7_height[1][13] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(5);
                            }
                        } //일곱번째 칸의 5번 점자
                        else if (m.x < m.notarget7_width[2][13] + m.bigcircle && m.x > m.notarget7_width[2][13] - m.bigcircle && m.y < m.notarget7_height[2][13] + m.bigcircle && m.y > m.notarget7_height[2][13] - m.bigcircle) {
                            WHclass.number = 6;
                            if (result6 == 0) {
                                if (m.x < m.target7_width[2][13] + m.bigcircle && m.x > m.target7_width[2][13] - m.bigcircle && m.y < m.target7_height[2][13] + m.bigcircle && m.y > m.target7_height[2][13] - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);

                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));

                                }
                                touch_init(6);
                            }
                        } //일곱번째 칸의 6번 점자
                        else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle) {
                            WHclass.number = 7;
                            WHclass.target = true;
                            startService(new Intent(this, Number.class));
                            m.vibrator.vibrate(WHclass.Weak_vibe);
                            touch_init(0);
                        } else { //그 외 지점을 터치하였을 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                            touch_init(0);
                            WHclass.number = 0;
                        }
                        switch (m.dot_count) {
                            case 1:// 점자의 칸수가 한 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width2 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 2:// 점자의 칸수가 두 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width4 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 3:// 점자의 칸수가 세 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 7;
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
                            case 4:// 점자의 칸수가 네 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width8 + (m.bigcircle * 2)) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                }
                                break;
                            case 5:// 점자의 칸수가 다섯 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
                                    WHclass.number = 7;
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
                            case 6:// 점자의 칸수가 여섯 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width10 + m.bigcircle && m.x < m.width11 - m.bigcircle) {
                                    WHclass.number = 7;
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
                            case 7:// 점자의 칸수가 일곱 칸일때 구분선 및 경고음 발생 영역 지정
                                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width10 + m.bigcircle && m.x < m.width11 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width12 + m.bigcircle && m.x < m.width13 - m.bigcircle) {
                                    WHclass.number = 7;
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Weak_vibe);
                                    touch_init(0);
                                } else if (m.x > m.width14 + m.bigcircle && m.x < m.width14 + (m.bigcircle * 2)) {
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

                case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 화면에서 떼었을 경우
                    click = true;
                    newdrag = (int) event.getX(); // 두번째 손가락이 화면에서 떨어진 지점의 x 좌표 저장
                    y2drag = (int) event.getY();// 두번째 손가락이 화면에서 떨어진 지점의 y 좌표 저장
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
                    } else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 상단으로 드래그 하는 경우 퀴즈 화면 종료
                        onBackPressed();
                    }
                    else if(olddrag - newdrag > WHclass.Drag_space){ //다음화면으로 이동
                        if(next==true) {
                            next = false;
                            m.quiz_view_init();

                            if(quiz_reading_service.question==4) {
                                onBackPressed();
                            }
                            else if(quiz_reading_service.question<4){
                                quiz_reading_service.menu_page=++m.question;
                                startService(new Intent(this, quiz_reading_service.class));
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN: //두 번째 손가락을 터치하였을 때
                    click = false;// 제스처 기능을 위해 손가락 1개를 인지하는 화면을 잠금
                    olddrag = (int) event.getX();// 두번쨰 손가락이 터치한 지점의 x좌표 저장
                    y1drag = (int) event.getY();// 두번째 손가락이 터치한 지점의 y좌표 저장
                    break;

            }
        } /*else { //다음 문제가 존재할 경우
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: //손가락 1개를 이용하여 화면을 터치하였을 경우
                    posx1 = (int) event.getX(); //현재 터치한 지점의 x좌표 저장
                    posy1 = (int) event.getY(); //현재 터치한 지점의 y좌표 저장
                    break;
                case MotionEvent.ACTION_UP:
                    posx2 = (int) event.getX();
                    posy2 = (int) event.getY();
                    if (enter == true) {
                        if (page == 4) { //만약 마지막 문제였을 경우 점수 화면으로 이동
                            //   Intent in = new Intent(this, quiz_score.class);
                            //    startActivity(in);
                            onBackPressed();
                            break;
                        }
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
        Log.e("reading_long_practice", "onError");

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
        m.print=true;

        final Activity activity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                String answer = m.textname_7;

                for (int i = 0; i < texts.size(); i++) {
                    if (answer.equals(texts.get(i)) == true) {
                        result = true;
                        break;
                    } else
                        continue;
                }

                // finishing일때는 처리하지 않는다.
                if (activity.isFinishing()) return;
                m.invalidate();
                if (result == true&&quiz_reading_service.question!=4) {
                    MainActivity.Braille_TTS.TTS_Play("정답입니다. 다음 화면으로 이동하세요.");
                }
                else if(result == true&&quiz_reading_service.question==4){
                    MainActivity.Braille_TTS.TTS_Play("정답입니다. 모든 문제가 끝났습니다.");
                }
                else if(result == false&&quiz_reading_service.question!=4) {
                    MainActivity.Braille_TTS.TTS_Play("오답입니다. 정답은"+answer+"입니다. 점자를 다시 읽어본 후에 다음 화면으로 이동하세요.");
                }
                else{
                    MainActivity.Braille_TTS.TTS_Play("오답입니다. 정답은"+answer+"입니다. 모든 문제가 끝났습니다.");
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
        Log.i("reading_long_practice", "onFinished");
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
    public void onBackPressed() { //종료키를 눌렀을 경우 발생되는 함수
        if(m.question==4){
            quiz_writing_service.finish = true;
            quiz_writing_service.progress = true;
            startService(new Intent(this, quiz_writing_service.class));
        }
        else{
            quiz_writing_service.finish = true;
            startService(new Intent(this, quiz_writing_service.class));
        }
        finish();
    }
}