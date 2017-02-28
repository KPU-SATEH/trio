package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
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
import com.example.yeo.practice.Normal_version_menu.Menu_Tutorial;
import com.example.yeo.practice.Normal_version_menu.Menu_master_practice;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial_tutorial extends AppCompatActivity {
    Common_menu_display m;


    int oldDragX,newDragX;
    int oldDragY,newDragY;

    private SoundThread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu_main_service.menu_page = 0;
        startService(new Intent(this, Menu_main_service.class)); //메뉴 음성 출력 서비스
        init();

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
                    MainActivity.Braille_TTS.TTS_Play("잘하셨습니다. 방금, 사용설명서 라는 음성을 들은것과 같이, 모든 메뉴에서는 현재 메뉴의 이름을 음성으로 안내합니다. 안내되는 음성을 통해," +
                            "현재 위치하고 있는 메뉴를 인지하시기 바랍니다. 또한 방금 실습해본 것과 같이, 손가락 1개를 이용하여 화면을 터치하게 되면, 메뉴를 선택할 수 있습니다." +
                            "현재 메뉴는 7개의 대 메뉴 중, 첫번째 메뉴인 사용설명서 메뉴이며, 현재 여러분들께서 설명을 듣고 있는 메뉴 입니다. 이제 다음 메뉴로 이동하겠습니다." +
                            "다음 메뉴로 이동하기 위해서는, 화면에 손가락 2개를 얹고, 책장을 넘기듯이 쓸어 넘기시면 됩니다. 준비되었으면, 다음 메뉴로 이동하시기 바랍니다. ");
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

        Menu_info.DISPLAY = Menu_info.DISPLAY_TUTORIAL;
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
                    if(oldDragX-newDragX> WHclass.Drag_space) {  //손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그할 경우 다음 메뉴로 이동
                        Intent basic_practice = new Intent(Tutorial_tutorial.this, Tutorial_basic_practice.class);
                        startActivityForResult(basic_practice, 0);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        slied.slied = Menu_info.next;
                        startService(new Intent(this, slied.class));
                        finish();
                    }
                    else if(newDragY-oldDragY> WHclass.Drag_space) {  //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                        MainActivity.Braille_TTS.Tutorial_lock();
                        MainActivity.Braille_TTS.TTS_Play("잘하셨습니다. 방금, 사용설명서 라는 음성을 들은것과 같이, 모든 메뉴에서는 현재 메뉴의 이름을 음성으로 안내합니다. 안내되는 음성을 통해," +
                                "현재 위치하고 있는 메뉴를 인지하시기 바랍니다. 또한 방금 실습해본 것과 같이, 손가락 1개를 이용하여 화면을 터치하게 되면, 메뉴를 선택할 수 있습니다." +
                                "현재 메뉴는 7개의 대 메뉴 중, 첫번째 메뉴인 사용설명서 메뉴이며, 현재 여러분들께서 설명을 듣고 있는 메뉴 입니다. 이제 다음 메뉴로 이동하겠습니다." +
                                "다음 메뉴로 이동하기 위해서는, 화면에 손가락 2개를 얹고, 책장을 넘기듯이 쓸어 넘기시면 됩니다. 준비되었으면, 다음 메뉴로 이동하시기 바랍니다. ");
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
