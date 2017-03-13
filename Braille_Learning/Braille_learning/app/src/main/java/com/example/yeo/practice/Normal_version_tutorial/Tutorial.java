package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.yeo.practice.Common_Tutorial_sound.Common_Tutorial_service;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Talkback_version_menu.Talk_Menu_tutorial;
import com.example.yeo.practice.Talkback_version_tutorial.Talk_Tutorial;
import com.example.yeo.practice.WHclass;

public class Tutorial extends FragmentActivity {

    int oldDragX,oldDragY,newDragX,newDragY;
    static AnimationDrawable speechani;
    static ImageView speechimage;
    public Ani_Thread thread;
    boolean lock=false;
    boolean enter = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOption);
        init();

        startService();

        thread = new Ani_Thread();
        thread.start();


    }

    class Ani_Thread extends Thread{
        @Override
        public void run(){
            super.run();
            while(true){
                if(WHclass.SoundCheck==false){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Ani_stop();
                        }
                    });
                    break;
                }
            }
        }
    }

    public void Ani_stop(){
        if(speechani.isRunning()){
            speechani.stop();
            speechani.start();
            speechani.stop();
        }
    }

    public void startService(){
        Common_Tutorial_service.previous=0;
        startService(new Intent(this, Common_Tutorial_service.class));
    }

    public void init(){
        speechimage = (ImageView) findViewById(R.id.imageView33);
        speechimage.setBackgroundResource(R.drawable.speechani);
        speechani = (AnimationDrawable) speechimage.getBackground();
    }

    @Override
    public void onPause(){
        super.onPause();
        findViewById(R.id.imageView33).setBackground(null);
        speechimage=null;
        speechani=null;
        System.gc();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        init();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(lock==false)
            speechani.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(Common_Tutorial_service.Touch_lock==false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    if(enter==false) {
                        Intent tutorial = new Intent(Tutorial.this, Tutorial_tutorial.class);
                        startActivityForResult(tutorial, 0);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        lock=true;
                        finish();
                    }
                    else
                        enter = false;

                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    enter = true;
                    oldDragX = (int)event.getX();
                    oldDragY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
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
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDragX = (int)event.getX();
                    oldDragY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
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
        Common_Tutorial_service.finish=true;
        startService(new Intent(this,Common_Tutorial_service.class));
        lock=true;
        finish();
    }
}
