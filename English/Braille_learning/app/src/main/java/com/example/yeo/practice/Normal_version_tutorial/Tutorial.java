package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial extends FragmentActivity {

    int oldDragX,oldDragY,newDragX,newDragY;
    static AnimationDrawable speechani;
    static ImageView speechimage;
    public Ani_Thread thread;
    boolean lock=false;
    boolean enter = false;
    boolean touch_lock = true;
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
        thread = new Ani_Thread();

        init();
        TTS();





    }

    public void TTS(){
        MainActivity.TTS.speak("From now on, we will begin a brief description of the operation and the menu." +
                " First, you need to listen to your smart phone in a horizontal direction. The Braille learning" +
                " application can operate within three fingers. And there are seven menus. Let's start with the function of using one finger. " +
                "Now, use a finger to touch the screen. If you want to hear it again, please put two fingers on the screen and drag it down from the top. " +
                "And if you want to end it, please put two fingers on the screen and drag it from the bottom to the top.", TextToSpeech.QUEUE_FLUSH, null);
        thread.start();
        touch_lock=true;

    }

    class Ani_Thread extends Thread{
        @Override
        public void run(){
            super.run();
            while(true){
                if(!MainActivity.TTS.isSpeaking()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Ani_stop();
                            touch_lock = false;
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
        if(MainActivity.TTS.isSpeaking())
            MainActivity.TTS.stop();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        init();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(lock==false) {
            speechani.start();
            lock = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touch_lock==false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    if (enter == false) {
                        Intent tutorial = new Intent(Tutorial.this, Tutorial_tutorial.class);
                        startActivityForResult(tutorial, 0);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    } else
                        enter = false;

                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    enter = true;
                    oldDragX = (int) event.getX();
                    oldDragY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    newDragX = (int) event.getX();
                    newDragY = (int) event.getY();
                    if (newDragY - oldDragY > WHclass.Drag_space) {  //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                        TTS();
                    } else if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                        onBackPressed();
                    }
                    break;
            }
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDragX = (int) event.getX();
                oldDragY = (int) event.getY();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                newDragX = (int) event.getX();
                newDragY = (int) event.getY();
                if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                    onBackPressed();
                }
                break;
        }


        return true;
    }

    @Override
    public void onBackPressed(){
        lock=true;
        finish();
    }
}
