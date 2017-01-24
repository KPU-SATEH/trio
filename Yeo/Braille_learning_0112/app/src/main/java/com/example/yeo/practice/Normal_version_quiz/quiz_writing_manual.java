package com.example.yeo.practice.Normal_version_quiz;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Normal_version_Display_Practice.writing_long_practice;
import com.example.yeo.practice.Normal_version_Display_Practice.writing_short_practice;
import com.example.yeo.practice.R;
import com.example.yeo.practice.*;
/*
퀴즈를 풀어보게 될 때, 퀴즈를 진행하는 간략한 설명이 담긴 음성을 관리하는 서비스
 */

public class quiz_writing_manual extends FragmentActivity {
    final public static int ENTER = 0;
    public int newdrag,olddrag;
    public static int choice;
    public int y1drag,y2drag;
    public int posx1,posx2,posy1,posy2;
    public boolean enter = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_quiz_writing_manual);

        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: //손가락 1개로 터치하였을 때
                posx1 = (int)event.getX(); //현재 좌표의 x좌표값을 저장
                posy1 = (int)event.getY(); //현재 좌표의 y좌표값을 저장
                break;
            case MotionEvent.ACTION_UP: //손가락 1개가 화면에서 떨어졌을 때
                posx2 = (int)event.getX(); //현재 좌표의 x좌표값을 저장
                posy2 = (int)event.getY();  //현재 좌표의 y좌표값을 저장

                if(enter == true) {
                    if (posx2 < posx1 + WHclass.Touch_space && posx2 > posx1 - WHclass.Touch_space && posy1 < posy2 + WHclass.Touch_space && posy2 > posy2 - WHclass.Touch_space) {
                        /*
                        화면을 터치하게 되면, 자신이 풀어보려고 했던 퀴즈가 시작됨
                         */
                        switch(Menu_info.MENU_QUIZ_INFO) {
                            case 0: //초성퀴즈
                                Intent intent0 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent0, ENTER);
                                break;
                            case 1: //모음퀴즈
                                Intent intent1 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent1, ENTER);
                                break;
                            case 2: //종성퀴즈
                                Intent intent2 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent2, ENTER);
                                break;
                            case 3: //숫자퀴즈
                                Intent intent3 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent3, ENTER);
                                break;
                            case 4: //알파벳퀴즈
                                Intent intent4 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent4, ENTER);
                                break;
                            case 5: //문장부호퀴즈
                                Intent intent5 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent5, ENTER);
                                break;
                            case 6: //약자 및 약어퀴즈
                                Intent intent6 = new Intent(quiz_writing_manual.this, writing_short_practice.class);
                                startActivityForResult(intent6, ENTER);
                                break;
                            case 7:
                                Intent intent7 = new Intent(quiz_writing_manual.this, writing_long_practice.class);
                                startActivityForResult(intent7, ENTER);
                                break;
                            case 8:
                                Intent intent8 = new Intent(quiz_writing_manual.this, writing_long_practice.class);
                                startActivityForResult(intent8, ENTER);
                                break;
                        }
                        //MainActivity.Braille_TTS.TTS_Stop();
                        finish();
                    }
                }
                else{
                    enter = true;
                    finish();
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                newdrag = (int)event.getX(); //두번째 손가락이 떨어진 x좌표 저장
                y2drag = (int) event.getY(); //두번째 손가락이 떨어진 y좌표 저장
                if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 하단에서 상단으로 쓸어 올렸을 때 퀴즈 종료
                    finish();
                }
            case MotionEvent.ACTION_POINTER_DOWN: //두번째 손가락이 터치되었을 때
                olddrag = (int)event.getX(); //두번째 손가락이 터치된 지점의 x좌표 저장
                y1drag = (int) event.getY(); //두번째 손가락이 터치된 지점의 y좌표 저장
                break;
        }
        return true;
    }
    public void onBackPressed() {
        finish();
    }
}
