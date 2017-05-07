package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_menu_display.Common_menu_display;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_menu.Menu_Tutorial;
import com.example.yeo.practice.Normal_version_menu.Menu_master_practice;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial_tutorial extends AppCompatActivity {
    Common_menu_display m;
    boolean touch_lock = false;

    int oldDragX,newDragX;
    int oldDragY,newDragY;
    int finger_x[] = new int[3];
    int finger_y[] = new int[3];

    private SoundThread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thread = new SoundThread();
        init();
        TTS();

    }

    public void TTS(){
        MainActivity.TTS.speak("Good job. You just heard a voice called Tutorial. Like this, " +
                "all menus will guide you through the current menu. You can view the menu currently " +
                "positioned via the guided voice. If you touch the screen using one finger just " +
                "like you've done, you can enter that menu. And the current menu is the first menu " +
                "of the seven main menus. Now we will move to the next menu. To move to the next menu, " +
                "please put two fingers on the screen and drag to the left. Now, please proceed to the " +
                "next menu.", TextToSpeech.QUEUE_FLUSH, null);

        thread.start();
        touch_lock = true;
    }


    class SoundThread extends Thread{
        @Override
        public void run(){
            super.run();
            while(true){
                if(!MainActivity.TTS.isSpeaking()) {
                    touch_lock = false;
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

        Menu_info.DISPLAY = Menu_info.DISPLAY_TUTORIAL;
        m = new Common_menu_display(this);
        m.setBackgroundColor(Color.rgb(22,26,44));

        setContentView(m);
    }

    @Override
    public void onPause(){
        super.onPause();
        m.free();
        if(MainActivity.TTS.isSpeaking())
            MainActivity.TTS.stop();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touch_lock==false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    for (int j = 0; j < 3; j++) {
                        finger_x[j] = -100;
                        finger_y[j] = -100;
                    }
                    m.finger_set(finger_x[0], finger_y[0], finger_x[1], finger_y[1], finger_x[2], finger_y[2]);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointer_count2 = event.getPointerCount();
                    for (int j = 0; j < 3; j++) {
                        finger_x[j] = -100;
                        finger_y[j] = -100;
                    }
                    for (int i = 0; i < pointer_count2; i++) {
                        finger_x[i] = (int) event.getX(i);
                        finger_y[i] = (int) event.getY(i);
                    }
                    m.finger_set(finger_x[0], finger_y[0], finger_x[1], finger_y[1], finger_x[2], finger_y[2]);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDragX = (int) event.getX();
                    oldDragY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    newDragX = (int) event.getX();
                    newDragY = (int) event.getY();
                    if (oldDragX - newDragX > WHclass.Drag_space) {  //손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그할 경우 다음 메뉴로 이동
                        Intent basic_practice = new Intent(Tutorial_tutorial.this, Tutorial_basic_practice.class);
                        startActivityForResult(basic_practice, 0);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    } else if (newDragY - oldDragY > WHclass.Drag_space) {  //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                        TTS();
                    } else if (oldDragY - newDragY > WHclass.Drag_space) {//손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                        onBackPressed();
                    }
                    break;
            }
        }

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

        return true;
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
