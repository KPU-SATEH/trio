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

import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial extends FragmentActivity {

    int oldDragX,oldDragY,newDragX,newDragY;
    static AnimationDrawable speechani;
    static ImageView speechimage;
    static boolean click = false;
    int posx1,posx2,posy1,posy2;
    int olddragx,newdragx,olddragy,newdragy;
    boolean enter = false;
    static int previous =0 ;
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



        MainActivity.Braille_TTS.Tutorial_lock();
        MainActivity.Braille_TTS.TTS_Play("시작");
        /*MainActivity.Braille_TTS.TTS_Play("점자 학습을 진행하는 여러분을 응원합니다. 지금부터, 점자 학습 어플리케이션의 조작법과 메뉴에 대한 간략한 설명을 시작하겠습니다." +
                "점자 학습 어플리케이션은, 손가락 한 개, 두 개, 그리고 세 개를 이용하여, 모든 기능을 사용할 수 있으며, 7개의 대 메뉴가 존재합니다. 먼저, 손가락 1개를 이용하는 기능부터 설명하겠습니다." +
                "준비되었으면 손가락 한 개를 이용하여 화면을 한 번 터치하시기 바랍니다. 다시 듣기를 희망하면, 화면에 손가락 2개를 얹고, 상단에서 하단으로 쓸어 내리시기 바랍니다. 종료하기를 희망하면,"+
                "화면에 손가락 2개를 얹고, 하단에서 상단으로 쓸어 올리시기 바랍니다.");
                */


        //startService(new Intent(this, Tutorial_service.class));
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
      //  speechani.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(MainActivity.Braille_TTS.Tutorial_lock==false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    Intent tutorial = new Intent(Tutorial.this, Tutorial_tutorial.class);
                    startActivityForResult(tutorial, 0);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDragX = (int)event.getX();
                    oldDragY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    newDragX = (int)event.getX();
                    newDragY = (int)event.getY();
                    if(oldDragX-newDragX> WHclass.Drag_space) {  //손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그할 경우 다음 메뉴로 이동

                    }
                    else if(newDragX-oldDragX>WHclass.Drag_space) {//손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 할 경우 이전 메뉴로 이동

                    }
                    else if(newDragY-oldDragY> WHclass.Drag_space) {  //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                        MainActivity.Braille_TTS.Tutorial_lock();
                        MainActivity.Braille_TTS.TTS_Play("점자 학습을 진행하는 여러분을 응원합니다. 지금부터, 점자 학습 어플리케이션의 조작법과 메뉴에 대한 간략한 설명을 시작하겠습니다." +
                                "점자 학습 어플리케이션은, 손가락 한개, 두개, 그리고 세개를 이용하여, 모든 기능을 사용할 수 있으며, 7개의 대 메뉴가 존재합니다. 먼저, 손가락 1개를 이용하는 기능부터 설명하겠습니다." +
                                "준비되었으면 손가락 한 개를 이용하여 화면을 한 번 터치하시기 바랍니다. 다시 듣기를 희망하면, 화면에 손가락 2개를 얹고, 상단에서 하단으로 쓸어 내리시기 바랍니다. 종료하기를 희망하면,"+
                                "화면에 손가락 2개를 얹고, 하단에서 상단으로 쓸어 올리시기 바랍니다.");
                    }
                    else if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                        onBackPressed();
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
