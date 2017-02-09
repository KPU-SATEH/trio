package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Common_menu_sound.Menu_detail_service;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_sound.slied;

public class Tutorial_basic_practice extends FragmentActivity {

    static boolean click = false;
    int posx1,posx2,posy1,posy2;
    int olddragx,newdragx,olddragy,newdragy;
    boolean enter = false;
    boolean detail = false;
    static int previous =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_basic);
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOption);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(WHclass.touchevent == true) {
            if (WHclass.basicsuccess == true) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        posx1 = (int) event.getX();
                        posy1 = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        posx2 = (int) event.getX();
                        posy2 = (int) event.getY();
                        if (enter == true) {
                            if (posx2 < posx1 + WHclass.width * (float) 0.1 && posx2 > posx1 - WHclass.width * (float) 0.1 && posy1 < posy2 + WHclass.width * (float) 0.1 && posy2 > posy2 - WHclass.width * (float) 0.1) {
                                Intent intent = new Intent(this, Tutorial_dot_lecture.class);
                                startActivityForResult(intent, 0);
                                startService(new Intent(this, Tutorial_service.class));
                                WHclass.db=5;
                                finish();
                            }
                        }
                        else enter = true;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                        newdragx = (int) event.getX();
                        newdragy = (int) event.getY();
                        if (newdragx < olddragx + WHclass.width * (float) 0.1 && newdragx > olddragx - WHclass.width * (float) 0.1 && newdragy < olddragy + WHclass.width * (float) 0.1 && newdragy > olddragy - WHclass.width * (float) 0.1) {
                            WHclass.tutorial_progress = WHclass.tutorial_previous;
                            startService(new Intent(this, Tutorial_service.class));
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        olddragx = (int) event.getX();
                        olddragy = (int) event.getY();
                        enter = false;
                        break;
                }
            }

            if (WHclass.mainmenuprogress == false) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                        newdragx = (int) event.getX();
                        newdragy = (int) event.getY();
                        if (detail == true) {
                            if (olddragx - newdragx > WHclass.width * (float) 0.2) {
                                slied.slied = 1;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Tutorial_service.class));
                                Intent intent = new Intent(Tutorial_basic_practice.this, Tutorial_master_practice.class);
                                WHclass.basicprogress[0] = 1;
                                startActivityForResult(intent, 0);
                                WHclass.db=3;
                                finish();
                            }
                            else if (newdragx < olddragx + WHclass.width * (float) 0.1 && newdragx > olddragx - WHclass.width * (float) 0.1 && newdragy < olddragy + WHclass.width * (float) 0.1 && newdragy > olddragy - WHclass.width * (float) 0.1) {
                                WHclass.tutorial_progress = WHclass.tutorial_previous;
                                startService(new Intent(this, Tutorial_service.class));
                            }
                        }
                        else if (detail == false) {
                            if (newdragy - olddragy > WHclass.width * (float) 0.2) {
                                startService(new Intent(this, Tutorial_service.class));
                                detail = true;
                            } else if (newdragx < olddragx + WHclass.width * (float) 0.1 && newdragx > olddragx - WHclass.width * (float) 0.1 && newdragy < olddragy + WHclass.width * (float) 0.1 && newdragy > olddragy - WHclass.width * (float) 0.1) {
                                WHclass.tutorial_progress = WHclass.tutorial_previous;
                                startService(new Intent(this, Tutorial_service.class));
                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        olddragx = (int) event.getX();
                        olddragy = (int) event.getY();
                        break;
                }
            }
            else {
                if (WHclass.basicsuccess == false) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            posx1 = (int) event.getX();
                            posy1 = (int) event.getY();
                            enter = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            posx2 = (int) event.getX();
                            posy2 = (int) event.getY();
                            if (enter == true) {
                                if (posx2 < posx1 + WHclass.width * (float) 0.1 && posx2 > posx1 - WHclass.width * (float) 0.1 && posy1 < posy2 + WHclass.width * (float) 0.1 && posy2 > posy2 - WHclass.width * (float) 0.1) {
                                    Intent intent = new Intent(this, Tutorial_basic_init.class);
                                    startActivityForResult(intent, 0);
                                    WHclass.basicprogress[0] = 1;
                                    startService(new Intent(this, Tutorial_service.class));
                                }
                            }
                            else enter = true;
                            break;
                        case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                            newdragx = (int) event.getX();
                            newdragy = (int) event.getY();
                            if (olddragx - newdragx > WHclass.width * (float) 0.2) {
                                Intent intent = new Intent(this, Tutorial_master_practice.class);
                                startActivityForResult(intent, 0);
                                slied.slied = 1;
                                Menu_main_service.menu_page = 3;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Menu_main_service.class));
                                finish();
                            } else if (newdragx - olddragx > WHclass.width * (float) 0.2) {
                                Intent intent = new Intent(this, Tutorial_tutorial.class);
                                startActivityForResult(intent, 0);
                                Menu_main_service.menu_page = 1;
                                slied.slied = 2;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Menu_main_service.class));
                                finish();
                            } else if (newdragy - olddragy > MainActivity.width * (float) 0.2) {
                                Menu_detail_service.menu_page = 1;
                                startService(new Intent(this, Menu_detail_service.class));
                            } else if (newdragx < olddragx + WHclass.width * (float) 0.1 && newdragx > olddragx - WHclass.width * (float) 0.1 && newdragy < olddragy + WHclass.width * (float) 0.1 && newdragy > olddragy - WHclass.width * (float) 0.1) {
                                WHclass.tutorial_progress = WHclass.tutorial_previous;
                                startService(new Intent(this, Tutorial_service.class));
                            }
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            olddragx = (int) event.getX();
                            olddragy = (int) event.getY();
                            enter = false;
                            break;
                    }
                }
            }
        }
        return true;
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
