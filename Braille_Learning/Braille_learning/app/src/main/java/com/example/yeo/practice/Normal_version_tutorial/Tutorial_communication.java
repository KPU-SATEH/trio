package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_menu_display.Common_menu_display;
import com.example.yeo.practice.Common_menu_sound.Menu_detail_service;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_sound.slied;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_menu.Menu_Braille_translation;
import com.example.yeo.practice.Normal_version_menu.Menu_Tutorial;
import com.example.yeo.practice.Normal_version_menu.Menu_master_practice;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial_communication extends AppCompatActivity {
    Common_menu_display m;
    int oldDragX,newDragX;
    int oldDragY,newDragY;

    private SoundThread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        Menu_main_service.menu_page = Menu_info.MENU_COMMUNICATION;
        startService(new Intent(this, Menu_main_service.class));

        thread = new SoundThread();
        thread.start();


    }



    class SoundThread extends Thread{
        @Override
        public void run(){
            super.run();
            while(true){
                if(WHclass.SoundCheck==true){
                    MainActivity.Braille_TTS.Tutorial_lock();
                    MainActivity.Braille_TTS.TTS_Play("잘하셨습니다. 현재 메뉴는 7개의 대 메뉴 중, 마지막 메뉴인 선생님과의 대화 메뉴 입니다. 현재 메뉴는, 점자 강의를 진행하는 선생님께서 스마트폰에 점자를 입력한 뒤" +
                            "점자를 전송하게 되면, 학생의 스마트폰에 전송됩니다. 학생은 선생님이 전송한 점자를 화면을 문지르며 학습을 진행할 수 있고, 또한 선생님께서는 전송한 점자를 이용하여 강의를 진행할 수 있습니다." +
                            "즉, 손으로 만지는 칠판을 여러분들에게 제공하는 메뉴 입니다. 선생님모드로 접속을 하게 되면, 방이 생성되며, 방 번호를 학생들에게 알려주시기 바랍니다. 학생은, 선생님께서 알려주신 방 번호를" +
                            "음성인식을 활용하여 선생님이 계신 방으로 입장하시기 바랍니다. 이렇게 모든 메뉴를 살펴 보았습니다. 모든 메뉴에는 간략한 사용설명이 포함되어 있으니, 점자 학습을 진행하면서 차근차근" +
                            "배워가시기 바랍니다. 이제, 사용설명서 메뉴를 종료하며 점자 학습 어플리케이션을 시작하겠습니다. 화면에 손가락 2개를 얹고, 하단에서 상단으로 쓸어 올리게 되면 현재 메뉴가 종료됩니다." +
                            "다시 듣기를 희망하면 화면에 손가락 2개를 얹고, 상단에서 하단으로 쓸어내리시기 바랍니다.");
                    WHclass.SoundCheck=false;
                    break;
                }
            }
        }
    }

    public void init(){
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );
        Menu_info.DISPLAY = Menu_info.DISPLAY_COMUNICATION;
        m = new Common_menu_display(this);
        m.setBackgroundColor(Color.rgb(22,26,44));

        setContentView(m);
    }

    @Override
    public void onPause(){
        super.onPause();
        m.free();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(MainActivity.Braille_TTS.Tutorial_lock==false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDragX = (int)event.getX();
                    oldDragY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    newDragX = (int)event.getX();
                    newDragY = (int)event.getY();
                    if(newDragY-oldDragY> WHclass.Drag_space) {  //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                        MainActivity.Braille_TTS.Tutorial_lock();
                        MainActivity.Braille_TTS.TTS_Play("잘하셨습니다. 현재 메뉴는 7개의 대 메뉴 중, 여섯번째 메뉴인 나만의 단어장 메뉴 입니다. 점자 학습을 진행하다가, 손가락 3개를 이용하여 화면을 터치하게 되면," +
                                "해당 점자가 나만의 단어장에 저장됩니다. 나만의 단어장에서 손가락 3개를 이용하여 화면을 터치하게 되면, 저장되어 있는 점자가 삭제 됩니다."+
                                "마지막 메뉴인 선생님과의 대화 메뉴로 이동하겠습니다. 다음 메뉴로 이동하기 위해서는, 화면에 손가락 2개를 얹고, 책장을 넘기듯이 쓸어 넘기시면 됩니다. " +
                                "준비되었으면, 다음 메뉴로 이동하시기 바랍니다. 다시 듣기를 희망하면," +
                                "화면에 손가락 2개를 얹고, 상단에서 하단으로 쓸어내리시기 바랍니다. 종료하기를 희망하면, 하단에서 상단으로 쓸어 올리시기 바랍니다.");
                    }
                    else if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                        Intent quiz = new Intent(Tutorial_communication.this, Menu_Tutorial.class);
                        startActivity(quiz);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        Menu_main_service.finish=true;
        startService(new Intent(this,Menu_main_service.class));
        finish();
    }

}
