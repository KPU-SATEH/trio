package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.Tutorial_dot_data;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_short_display;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Normal_version_menu.Menu_Tutorial;
import com.example.yeo.practice.Common_sound.slied;

public class Tutorial_dot_practice extends FragmentActivity {
    Braille_short_display m;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag;
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0;
    boolean click = true;
    int vibrate1=0, vibrate2=0, vibrate3=0, vibrate4=0, vibrate5=0, vibrate6=0, vibrateresult=0;
    int sound1 = 0, sound2 = 0, sound3 = 0, sound4=0, sound5=0, sound6=0, soundresult=0;
    static Tutorial_dot_data dot;
    static boolean stage[] = new boolean[]{false, false, false,false,false, false};
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
        dot = new Tutorial_dot_data();
        m = new Braille_short_display(this);
        m.setBackgroundColor(Color.rgb(22,26,44));
        setContentView(m);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(WHclass.touchevent == true) {
            // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    if (click == false) {
                        click = true;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    m.x = (int) event.getX();
                    m.y = (int) event.getY();

                    if (stage[0] == true && stage[1] == true && stage[2] == true && stage[3] == true && stage[4] == true) {
                        Intent intent = new Intent(this, Menu_Tutorial.class);
                        startActivityForResult(intent, 0);
                        startService(new Intent(this, Tutorial_service.class));
                        WHclass.db=7;
                        finish();
                    } else {
                        if ((m.x == 0) && (m.y == 0)) {
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
                                if (stage[0] == false) {
                                    vibrate1 = 1;
                                    vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                    if (vibrateresult == 6) {
                                        stage[0] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                    sound1 = 1;
                                    soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                    if (soundresult == 6) {
                                        stage[1] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                }

                            } //1번 점자
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
                                if (stage[0] == false) {
                                    vibrate2 = 1;
                                    vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                    if (vibrateresult == 6) {
                                        stage[0] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                    sound2 = 1;
                                    soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                    if (soundresult == 6) {
                                        stage[1] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                }

                            } //2번 점자

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
                                if (stage[0] == false) {
                                    vibrate3 = 1;
                                    vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                    if (vibrateresult == 6) {
                                        stage[0] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                    sound3 = 1;
                                    soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                    if (soundresult == 6) {
                                        stage[1] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                }

                            } //3번 점자

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
                                if (stage[0] == false) {
                                    vibrate4 = 1;
                                    vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                    if (vibrateresult == 6) {
                                        stage[0] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                    sound4 = 1;
                                    soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                    if (soundresult == 6) {
                                        stage[1] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                }

                            } //4번 점자

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
                                if (stage[0] == false) {
                                    vibrate5 = 1;
                                    vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                    if (vibrateresult == 6) {
                                        stage[0] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                    sound5 = 1;
                                    soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                    if (soundresult == 6) {
                                        stage[1] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                }

                            } //5번점자

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
                                if (stage[0] == false) {
                                    vibrate6 = 1;
                                    vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                    if (vibrateresult == 6) {
                                        stage[0] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                    sound6 = 1;
                                    soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                    if (soundresult == 6) {
                                        stage[1] = true;
                                        startService(new Intent(this, Tutorial_service.class));
                                    }
                                }

                            } else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
                                WHclass.number = 1;
                                if (m.x < m.tw7 + m.bigcircle && m.x > m.tw7 - m.bigcircle && m.y < m.th7 + m.bigcircle && m.y > m.th7 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));
                                }
                            } else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
                                WHclass.number = 2;
                                if (m.x < m.tw8 + m.bigcircle && m.x > m.tw8 - m.bigcircle && m.y < m.th8 + m.bigcircle && m.y > m.th8 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));
                                }
                            } else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
                                WHclass.number = 3;
                                if (m.x < m.tw9 + m.bigcircle && m.x > m.tw9 - m.bigcircle && m.y < m.th9 + m.bigcircle && m.y > m.th9 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));
                                }
                            } else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
                                WHclass.number = 4;
                                if (m.x < m.tw10 + m.bigcircle && m.x > m.tw10 - m.bigcircle && m.y < m.th10 + m.bigcircle && m.y > m.th10 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));
                                }
                            } else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
                                WHclass.number = 5;
                                if (m.x < m.tw11 + m.bigcircle && m.x > m.tw11 - m.bigcircle && m.y < m.th11 + m.bigcircle && m.y > m.th11 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));
                                }
                            } else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
                                WHclass.number = 6;
                                if (m.x < m.tw12 + m.bigcircle && m.x > m.tw12 - m.bigcircle && m.y < m.th12 + m.bigcircle && m.y > m.th12 - m.bigcircle) {
                                    WHclass.target = true;
                                    startService(new Intent(this, Number.class));
                                    m.vibrator.vibrate(WHclass.Strong_vibe);
                                } else {
                                    WHclass.target = false;
                                    startService(new Intent(this, Number.class));
                                }
                            }
                        }
                        m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    m.x = (int) event.getX();
                    m.y = (int) event.getY();
                    if (stage[0] == true && stage[1] == true && stage[2] == true && stage[3] == true && stage[4] == true) {
                        Intent intent = new Intent(this, Tutorial_tutorial.class);
                        startActivityForResult(intent, 0);
                        startService(new Intent(this, Tutorial_service.class));
                        finish();
                    } else {
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
                                    if (stage[0] == false) {
                                        vibrate1 = 1;
                                        vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                        if (vibrateresult == 6) {
                                            stage[0] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                        sound1 = 1;
                                        soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                        if (soundresult == 6) {
                                            stage[1] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    }

                                }
                            } else if (m.x < m.w2 + m.bigcircle && m.x > m.w2 - m.bigcircle && m.y < m.h2 + m.bigcircle && m.y > m.h2 - m.bigcircle) {
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
                                    if (stage[0] == false) {
                                        vibrate2 = 1;
                                        vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                        if (vibrateresult == 6) {
                                            stage[0] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                        sound2 = 1;
                                        soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                        if (soundresult == 6) {
                                            stage[1] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    }

                                }
                            } else if (m.x < m.w3 + m.bigcircle && m.x > m.w3 - m.bigcircle && m.y < m.h3 + m.bigcircle && m.y > m.h3 - m.bigcircle) {
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
                                    if (stage[0] == false) {
                                        vibrate3 = 1;
                                        vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                        if (vibrateresult == 6) {
                                            stage[0] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                        sound3 = 1;
                                        soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                        if (soundresult == 6) {
                                            stage[1] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    }

                                }
                            } else if (m.x < m.w4 + m.bigcircle && m.x > m.w4 - m.bigcircle && m.y < m.h4 + m.bigcircle && m.y > m.h4 - m.bigcircle) {
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
                                    if (stage[0] == false) {
                                        vibrate4 = 1;
                                        vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                        if (vibrateresult == 6) {
                                            stage[0] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                        sound4 = 1;
                                        soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                        if (soundresult == 6) {
                                            stage[1] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    }

                                }
                            } else if (m.x < m.w5 + m.bigcircle && m.x > m.w5 - m.bigcircle && m.y < m.h5 + m.bigcircle && m.y > m.h5 - m.bigcircle) {
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
                                    if (stage[0] == false) {
                                        vibrate5 = 1;
                                        vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                        if (vibrateresult == 6) {
                                            stage[0] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                        sound5 = 1;
                                        soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                        if (vibrateresult == 6) {
                                            stage[1] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    }

                                }
                            } else if (m.x < m.w6 + m.bigcircle && m.x > m.w6 - m.bigcircle && m.y < m.h6 + m.bigcircle && m.y > m.h6 - m.bigcircle) {
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
                                    if (stage[0] == false) {
                                        vibrate6 = 1;
                                        vibrateresult = vibrate1 + vibrate2 + vibrate3 + vibrate4 + vibrate5 + vibrate6;
                                        if (vibrateresult == 6) {
                                            stage[0] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    } else if (stage[0] == true && stage[1] == false && stage[2] == false && stage[3] == false) {
                                        sound6 = 1;
                                        soundresult = sound1 + sound2 + sound3 + sound4 + sound5 + sound6;
                                        if (soundresult == 6) {
                                            stage[1] = true;
                                            startService(new Intent(this, Tutorial_service.class));
                                        }
                                    }

                                }
                            } else if (m.x < m.w7 + m.bigcircle && m.x > m.w7 - m.bigcircle && m.y < m.h7 + m.bigcircle && m.y > m.h7 - m.bigcircle) {
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
                            } else if (m.x < m.w8 + m.bigcircle && m.x > m.w8 - m.bigcircle && m.y < m.h8 + m.bigcircle && m.y > m.h8 - m.bigcircle) {
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
                            } else if (m.x < m.w9 + m.bigcircle && m.x > m.w9 - m.bigcircle && m.y < m.h9 + m.bigcircle && m.y > m.h9 - m.bigcircle) {
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
                            } else if (m.x < m.w10 + m.bigcircle && m.x > m.w10 - m.bigcircle && m.y < m.h10 + m.bigcircle && m.y > m.h10 - m.bigcircle) {
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
                            } else if (m.x < m.w11 + m.bigcircle && m.x > m.w11 - m.bigcircle && m.y < m.h11 + m.bigcircle && m.y > m.h11 - m.bigcircle) {
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
                            } else if (m.x < m.w12 + m.bigcircle && m.x > m.w12 - m.bigcircle && m.y < m.h12 + m.bigcircle && m.y > m.h12 - m.bigcircle) {
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
                            } else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle) {
                                WHclass.number = 7;
                                WHclass.target = true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                                if (stage[0] == true && stage[1] == true && stage[2] == false && stage[3] == false) {
                                    startService(new Intent(this, Tutorial_service.class));
                                    stage[2] = true;
                                }
                            } else {
                                touch_init(0);
                                WHclass.number = 0;
                            }
                            switch (m.dot_count) {
                                case 1:
                                    if (m.x > m.width2 + m.bigcircle && m.x < m.width2 + (m.bigcircle * 2)) {
                                        WHclass.number = 7;
                                        WHclass.target = true;
                                        startService(new Intent(this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                        if (stage[0] == true && stage[1] == true && stage[2] == false && stage[3] == false) {
                                            startService(new Intent(this, Tutorial_service.class));
                                            stage[2] = true;
                                        }
                                    }
                                    break;
                                case 2:
                                    if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                                        WHclass.number = 7;
                                        WHclass.target = true;
                                        startService(new Intent(this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                        if (stage[0] == true && stage[1] == true && stage[2] == true && stage[3] == true && stage[4] == false) {
                                            startService(new Intent(this, Tutorial_service.class));
                                            stage[4] = true;
                                        }

                                    } else if (m.x > m.width6 + m.bigcircle && m.x < m.width6 + (m.bigcircle * 2)) {
                                        WHclass.number = 7;
                                        WHclass.target = true;
                                        startService(new Intent(this, Number.class));
                                        m.vibrator.vibrate(WHclass.Weak_vibe);
                                        touch_init(0);
                                    }
                                    break;
                            }
                        }
                    }
                    m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                    break;

                case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                    newdrag = (int) event.getX();
                    y2drag = (int) event.getY();
                    if (olddrag - newdrag > m.width * (float) 0.2) {
                        m.page++;
                        if (m.page >= dot.Initial_count) {
                            m.page--;
                        } else {
                            if (stage[0] == true && stage[1] == true && stage[2] == true && stage[3] == false) {
                                slied.slied = 1;
                                startService(new Intent(this, slied.class));
                                m.MyView2_init();
                                m.invalidate();
                                startService(new Intent(this, Tutorial_service.class));
                                stage[3] = true;
                            }
                        }
                    } else if (newdrag < olddrag + WHclass.width * (float) 0.1 && newdrag > olddrag - WHclass.width * (float) 0.1 && y2drag < y1drag + WHclass.width * (float) 0.1 && y2drag > y1drag - WHclass.width * (float) 0.1) {
                        WHclass.tutorial_progress = WHclass.tutorial_previous;
                        startService(new Intent(this, Tutorial_service.class));
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    click = false;
                    olddrag = (int) event.getX();
                    y1drag = (int) event.getY();
                    break;

            }
        }
        return true;
    }

    public void touch_init(int coordinate){
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
    protected void onStop(){
        super.onStop();
        SharedPreferences sf = getSharedPreferences("save", 0);
        SharedPreferences.Editor editor = sf.edit();
        //Intent intent = getIntent();
        //int a=intent.getExtras().getInt("a");
        editor.putInt("b", WHclass.db);
        editor.commit();
    }
}
