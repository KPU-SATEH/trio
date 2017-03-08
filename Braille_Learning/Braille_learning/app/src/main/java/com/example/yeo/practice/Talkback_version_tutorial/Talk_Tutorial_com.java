package com.example.yeo.practice.Talkback_version_tutorial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_Tutorial_sound.Common_Tutorial_service;
import com.example.yeo.practice.Common_menu_display.Common_menu_display;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Talkback_version_menu.Talk_Menu_tutorial;
import com.example.yeo.practice.WHclass;

public class Talk_Tutorial_com extends AppCompatActivity {
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

    public void startService(){
        Common_Tutorial_service.previous=7;
        startService(new Intent(this, Common_Tutorial_service.class));
    }



    class SoundThread extends Thread{
        @Override
        public void run(){
            super.run();
            while(true){
                if(WHclass.SoundCheck==true){
                    startService();
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
        if(Common_Tutorial_service.Touch_lock==false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    oldDragX = (int)event.getX();
                    oldDragY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    newDragX = (int)event.getX();
                    newDragY = (int)event.getY();
                    if(newDragY-oldDragY> WHclass.Drag_space) {  //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                        startService();
                    }
                    else if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                        onBackPressed();
                    }
                    break;
            }
        }
        else{
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    oldDragX = (int)event.getX();
                    oldDragY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    newDragX = (int)event.getX();
                    newDragY = (int)event.getY();
                    if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                        onBackPressed();
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        if(MainActivity.tutorial.equals("0")==true) {
            Intent quiz = new Intent(Talk_Tutorial_com.this, Talk_Menu_tutorial.class);
            startActivity(quiz);
            overridePendingTransition(R.anim.fade, R.anim.hold);
            MainActivity.PrefEditor.putString("TUTORIAL","1");
            MainActivity.PrefEditor.commit();
        }
        Common_Tutorial_service.finish=true;
        startService(new Intent(this,Common_Tutorial_service.class));
        finish();
    }

}
