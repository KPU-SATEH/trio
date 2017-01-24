package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_menu_sound.Menu_detail_service;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_sound.slied;

public class Tutorial_tutorial extends FragmentActivity {

    static boolean click = false;
    int posx1,posx2,posy1,posy2;
    int olddragx,newdragx,olddragy,newdragy;
    boolean enter = false;
    static int previous =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_tutorial);
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
            if (WHclass.mainmenuprogress == false) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        enter = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(enter==false) {
                            if (Tutorial_dot_practice.stage[4] == true) {
                                startService(new Intent(this, Tutorial_service.class));
                                finish();
                            }
                        }
                        else enter = true;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                        newdragx = (int) event.getX();
                        newdragy = (int) event.getY();

                        if (olddragx - newdragx > WHclass.width * (float) 0.2) {
                            slied.slied = 1;
                            startService(new Intent(this, slied.class));
                            startService(new Intent(this, Tutorial_service.class));
                            Intent intent = new Intent(Tutorial_tutorial.this, Tutorial_basic_practice.class);
                            startActivityForResult(intent, 0);
                            WHclass.db=2;
                            finish();
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
            } else {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                        newdragx = (int) event.getX(0);
                        newdragy = (int) event.getY(0);
                        if (olddragx - newdragx > WHclass.width * (float) 0.2) {
                            Intent intent = new Intent(this, Tutorial_basic_practice.class);
                            startActivityForResult(intent, 0);
                            slied.slied = 1;
                            Menu_main_service.menu_page = 2;
                            startService(new Intent(this, slied.class));
                            startService(new Intent(this, Menu_main_service.class));
                            if (WHclass.mainmenusuccess == false) {
                                WHclass.mainmenusuccess = true;
                                startService(new Intent(this, Tutorial_service.class));
                            }
                            finish();
                        } else if (newdragx - olddragx > WHclass.width * (float) 0.2) {
                            Intent intent = new Intent(this, Tutorial_quiz.class);
                            startActivityForResult(intent, 0);
                            Menu_main_service.menu_page = 4;
                            slied.slied = 2;
                            startService(new Intent(this, slied.class));
                            startService(new Intent(this, Menu_main_service.class));
                            finish();
                        } else if (newdragy - olddragy > MainActivity.width * (float) 0.2) {
                            Menu_detail_service.menu_page = 1;
                            startService(new Intent(this, Menu_detail_service.class));
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        olddragx = (int) event.getX(0);
                        olddragy = (int) event.getY(0);
                        break;
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
