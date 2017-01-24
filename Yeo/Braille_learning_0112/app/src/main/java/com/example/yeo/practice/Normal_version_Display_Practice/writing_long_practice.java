package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_quiz_word;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.WHclass;

import java.util.Timer;
import java.util.TimerTask;

public class writing_long_practice extends FragmentActivity {
    writing_long_display m;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag;
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0;
    boolean click = true;


    private TimerTask second; //타이머
    private final Handler handler = new Handler();
    Timer timer =null;

    int touch_check=0;  // 시간 초기화를 위한 변수
    int coordinate=0; //현재 점자 위치를 저장하는 변수

    boolean next = false; // 다음문제로 이동하기 위한 변수

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
        dot_quiz_word dot = new dot_quiz_word(); // 단어퀴즈 단위의 점자 클래스 선언
        m = new writing_long_display(this);
        m.setBackgroundColor(Color.rgb(22, 26, 44));
        setContentView(m);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        if (m.next == false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP://마지막 손가락을 땠을 때 화면잠금을 품
                    Timer_Stop();
                    if (click == false)
                        click = true;
                    break;
                case MotionEvent.ACTION_DOWN: // 손가락 1개를 이용하여 터치가 발생하였을 때
                    m.x = (int) event.getX();//x좌표를 저장
                    m.y = (int) event.getY();//y좌표를 저장
                    if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2)))
                        break;
                    else
                       Touch_event();
                    m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                    break;
                case MotionEvent.ACTION_MOVE:// 화면을 터치한 상태로 움직일때 발생되는 이벤트
                    m.x = (int) event.getX();// 현재 터치한 지점의 x좌표를 저장
                    m.y = (int) event.getY();// 현재 터치한 지점의 y좌표를 저장
                    if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2)))
                        break;
                     /*
                    터치한 지점의 좌표가 돌출된 점자일 경우 남성의 음성으로 점자번호를 안내하면서 강한진동이 발생
                    터치한 지점의 좌표가 비돌출된 점자일 경우 여성의 음성으로 점자번호를 안내함
                     */
                    if (click == true)
                        Touch_event();
                    m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                    break;

                case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 화면에서 떼었을 경우
                    click = true;
                    newdrag = (int) event.getX(); // 두번째 손가락이 화면에서 떨어진 지점의 x 좌표 저장
                    y2drag = (int) event.getY();// 두번째 손가락이 화면에서 떨어진 지점의 y 좌표 저장
                    if (y2drag - y1drag > WHclass.Drag_space) { //손가락 2개를 이용하여 하단으로 드래그 하는경우 정답 채점
                        if(next==false) {
                            String temp="";
                            temp=Grading();
                            MainActivity.Braille_TTS.TTS_Play(temp);
                        }
                    }
                    else if (y1drag - y2drag > WHclass.Drag_space) {// 손가락 2개를 이용하여 상단으로 드래그하는 경우 종료
                        MainActivity.Braille_TTS.TTS_Play("쓰기 퀴즈를 종료하고 상위 메뉴로 이동합니다. 쓰기 퀴즈");
                        onBackPressed();
                    }
                    else if(olddrag - newdrag > WHclass.Drag_space){
                        if(next==true) {
                            next = false;
                            m.quiz_view2_init();
                            if(m.question==3) {
                                MainActivity.Braille_TTS.TTS_Play("모든 문제가 끝났으므로, 쓰기 퀴즈를 종료합니다. 쓰기 퀴즈");
                                finish();
                            }
                        }
                    }
                    m.invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN: //두 번째 손가락을 터치하였을 때
                    click = false;// 제스처 기능을 위해 손가락 1개를 인지하는 화면을 잠금
                    olddrag = (int) event.getX();// 두번쨰 손가락이 터치한 지점의 x좌표 저장
                    y1drag = (int) event.getY();// 두번째 손가락이 터치한 지점의 y좌표 저장
                    break;

            }
        }
        return true;
    }

    public void touch_init(int coordinate_temp){ //문지르기 기능을 위한 컨트롤 변수 초기화 함수
        int coordinate_copy;
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;
        result6=0;

        coordinate_copy = coordinate_temp;

        if(coordinate_temp<7){
        }
        else if(coordinate_temp<13){
            coordinate_copy=coordinate_copy-6;
        }
        else if(coordinate_temp<19){
            coordinate_copy=coordinate_copy-12;
        }
        else if(coordinate_temp<25){
            coordinate_copy=coordinate_copy-18;
        }
        else if(coordinate_temp<31){
            coordinate_copy=coordinate_copy-24;
        }
        else if(coordinate_temp<37){
            coordinate_copy=coordinate_copy-30;
        }
        else if(coordinate_temp<43){
            coordinate_copy=coordinate_copy-36;
        }

        switch(coordinate_copy){
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
        }

        Timer_Reset(coordinate_temp);
    }

    public void Timer_Stop(){
        if(timer != null){
            timer.cancel();
            timer= null;
        }
    }

    public void Timer_Reset(int coordinate_temp){
        coordinate = coordinate_temp;
        touch_check=0;
        if(coordinate==0)
            Timer_Stop();
        else {
            if (timer != null) {
                timer.cancel();
                timer = null;
                timer = new Timer();
                Timer_Start();
            } else if (timer == null) {
                timer = new Timer();
                Timer_Start();
            }
        }
    }

    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                Touch_check();
            }
        };

        timer.schedule(second,0,1000); //1초의 딜레이시간
    }

    public void Touch_check(){
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                touch_check++;
                if(touch_check>2){
                    touch_insert_check(coordinate);
                    touch_check=0;
                }
            }
        };
        handler.post(updater);
    }

    public void touch_insert_check(int coordinate_temp){
        if(next==false) {
            int coordinate_x = -1;
            int coordinate_y = -1;

            if (coordinate_temp == 1) {
                coordinate_x = 0;
                coordinate_y = 0;
            } else if (coordinate_temp == 2) {
                coordinate_x = 0;
                coordinate_y = 1;
            } else if (coordinate_temp == 3) {
                coordinate_x = 0;
                coordinate_y = 2;
            } else if (coordinate_temp == 4) {
                coordinate_x = 1;
                coordinate_y = 0;
            } else if (coordinate_temp == 5) {
                coordinate_x = 1;
                coordinate_y = 1;
            } else if (coordinate_temp == 6) {
                coordinate_x = 1;
                coordinate_y = 2;
            } else if (coordinate_temp == 7) {
                coordinate_x = 2;
                coordinate_y = 0;
            } else if (coordinate_temp == 8) {
                coordinate_x = 2;
                coordinate_y = 1;
            } else if (coordinate_temp == 9) {
                coordinate_x = 2;
                coordinate_y = 2;
            } else if (coordinate_temp == 10) {
                coordinate_x = 3;
                coordinate_y = 0;
            } else if (coordinate_temp == 11) {
                coordinate_x = 3;
                coordinate_y = 1;
            } else if (coordinate_temp == 12) {
                coordinate_x = 3;
                coordinate_y = 2;
            } else if (coordinate_temp == 13) {
                coordinate_x = 4;
                coordinate_y = 0;
            } else if (coordinate_temp == 14) {
                coordinate_x = 4;
                coordinate_y = 1;
            } else if (coordinate_temp == 15) {
                coordinate_x = 4;
                coordinate_y = 2;
            } else if (coordinate_temp == 16) {
                coordinate_x = 5;
                coordinate_y = 0;
            } else if (coordinate_temp == 17) {
                coordinate_x = 5;
                coordinate_y = 1;
            } else if (coordinate_temp == 18) {
                coordinate_x = 5;
                coordinate_y = 2;
            } else if (coordinate_temp == 19) {
                coordinate_x = 6;
                coordinate_y = 0;
            } else if (coordinate_temp == 20) {
                coordinate_x = 6;
                coordinate_y = 1;
            } else if (coordinate_temp == 21) {
                coordinate_x = 6;
                coordinate_y = 2;
            } else if (coordinate_temp == 22) {
                coordinate_x = 7;
                coordinate_y = 0;
            } else if (coordinate_temp == 23) {
                coordinate_x = 7;
                coordinate_y = 1;
            } else if (coordinate_temp == 24) {
                coordinate_x = 7;
                coordinate_y = 2;
            } else if (coordinate_temp == 25) {
                coordinate_x = 8;
                coordinate_y = 0;
            } else if (coordinate_temp == 26) {
                coordinate_x = 8;
                coordinate_y = 1;
            } else if (coordinate_temp == 27) {
                coordinate_x = 8;
                coordinate_y = 2;
            } else if (coordinate_temp == 28) {
                coordinate_x = 9;
                coordinate_y = 0;
            } else if (coordinate_temp == 29) {
                coordinate_x = 9;
                coordinate_y = 1;
            } else if (coordinate_temp == 30) {
                coordinate_x = 9;
                coordinate_y = 2;
            } else if (coordinate_temp == 31) {
                coordinate_x = 10;
                coordinate_y = 0;
            } else if (coordinate_temp == 32) {
                coordinate_x = 10;
                coordinate_y = 1;
            } else if (coordinate_temp == 33) {
                coordinate_x = 10;
                coordinate_y = 2;
            } else if (coordinate_temp == 34) {
                coordinate_x = 11;
                coordinate_y = 0;
            } else if (coordinate_temp == 35) {
                coordinate_x = 11;
                coordinate_y = 1;
            } else if (coordinate_temp == 36) {
                coordinate_x = 11;
                coordinate_y = 2;
            } else if (coordinate_temp == 37) {
                coordinate_x = 12;
                coordinate_y = 0;
            } else if (coordinate_temp == 38) {
                coordinate_x = 12;
                coordinate_y = 1;
            } else if (coordinate_temp == 39) {
                coordinate_x = 12;
                coordinate_y = 2;
            } else if (coordinate_temp == 40) {
                coordinate_x = 13;
                coordinate_y = 0;
            } else if (coordinate_temp == 41) {
                coordinate_x = 13;
                coordinate_y = 1;
            } else if (coordinate_temp == 42) {
                coordinate_x = 13;
                coordinate_y = 2;
            }

            if (coordinate_x != (-1) && coordinate_y != (-1)) {
                if (m.target7_width[coordinate_y][coordinate_x] == 0 && m.target7_height[coordinate_y][coordinate_x] == 0) {
                    m.target7_width[coordinate_y][coordinate_x] = m.width_7[coordinate_y][coordinate_x];
                    m.target7_height[coordinate_y][coordinate_x] = m.height_7[coordinate_y][coordinate_x];
                } else {
                    m.target7_width[coordinate_y][coordinate_x] = 0;
                    m.target7_height[coordinate_y][coordinate_x] = 0;
                }

                if (m.Braille_insert[coordinate_y][coordinate_x] == 0)
                    m.Braille_insert[coordinate_y][coordinate_x] = 1;
                else if (m.Braille_insert[coordinate_y][coordinate_x] == 1)
                    m.Braille_insert[coordinate_y][coordinate_x] = 0;
            }
            result1 = 0;
            result2 = 0;
            result3 = 0;
            result4 = 0;
            result5 = 0;
            result6 = 0;
            Touch_event();
        }
    }

    public void Touch_event(){
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
                touch_init(7);
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
                touch_init(8);
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
                touch_init(9);
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
                touch_init(10);
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
                touch_init(11);
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
                touch_init(12);
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
                touch_init(13);
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
                touch_init(14);
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
                touch_init(15);
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
                touch_init(16);
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
                touch_init(17);
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
                touch_init(18);
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
                touch_init(19);
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
                touch_init(20);
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
                touch_init(21);
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
                touch_init(22);
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
                touch_init(23);
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
                touch_init(24);
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
                touch_init(25);
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
                touch_init(26);
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
                touch_init(27);
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
                touch_init(28);
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
                touch_init(29);
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
                touch_init(30);
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
                touch_init(31);
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
                touch_init(32);
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
                touch_init(33);
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
                touch_init(34);
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
                touch_init(35);
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
                touch_init(36);
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
                touch_init(37);
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
                touch_init(38);
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
                touch_init(39);
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
                touch_init(40);
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
                touch_init(41);
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
                touch_init(42);
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
            case 3:// 점자의 칸수가 세 칸일때 구분선 및 경고음 발생 영역 지정
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
            case 4:// 점자의 칸수가 네 칸일때 구분선 및 경고음 발생 영역 지정
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
            case 5:// 점자의 칸수가 다섯 칸일때 구분선 및 경고음 발생 영역 지정
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
            case 6:// 점자의 칸수가 여섯 칸일때 구분선 및 경고음 발생 영역 지정
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
            case 7:// 점자의 칸수가 일곱 칸일때 구분선 및 경고음 발생 영역 지정
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


    public String Grading(){
        boolean result= false;
        String result_return="";

        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                if(m.Braille_insert[i][j]==m.text_7[i][j])
                    result = true;
                else
                    result = false;

                if(result==false)
                    break;
            }
            if (result == false)
                break;
        }

        if(result==true) {
            result_return="정답입니다. 다음 화면으로 이동하시기 바랍니다.";
        }
        else if(result==false){
            m.quiz_target_init();

            for(int i=0 ; i<3 ; i++) {
                for (int j = 0; j < 14; j++) {
                    m.Braille_insert[i][j] = m.text_7[i][j];
                }
            }
            result_return="오답입니다. 화면을 문지르며 정답을 확인해보세요. 정답을 확인한 뒤, 다음 화면으로 이동하시기 바랍니다.";
        }

        next = true;
        m.question++;


        return result_return;

    }

    @Override
    public void onBackPressed() { //종료키를 눌렀을 경우 발생되는 이벤트
        m.page = 0;
        finish();
    }
}
