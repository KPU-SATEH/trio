package com.example.yeo.practice.Normal_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Normal_version_menu.Menu_Initial_Consonant;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial_practice extends FragmentActivity {
    static AnimationDrawable prac_speechani;
    static ImageView prac_speechimage;
    static boolean click = false;
    int posx1,posx2,posy1,posy2;
    int olddragx,newdragx,olddragy,newdragy;
    boolean enter = false;
    static int previous =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        prac_speechimage = (ImageView) findViewById(R.id.imageView33);
        prac_speechimage.setBackgroundResource(R.drawable.speechani);
        prac_speechani = (AnimationDrawable) prac_speechimage.getBackground();


        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        MainActivity.Braille_TTS.TTS_Play("기초과정에 앞서 점자 학습 방식에 대해 설명");
        decorView.setSystemUiVisibility(uiOption);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        prac_speechani.start();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(WHclass.touchevent == true) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    enter = true;
                    break;
                case MotionEvent.ACTION_UP:
                    if (WHclass.tutorial_progress == 1) {
                            //startService(new Intent(this, Tutorial_service.class));
                        Tutorial_practice_basic.n = 1;
                        onStop();
                        Intent intent = new Intent(Tutorial_practice.this, Tutorial_practice_basic.class);
                        startActivityForResult(intent, 0);
                    }
                    else enter = true;
                    break;
                case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우

                    WHclass.tutorial_progress = WHclass.tutorial_previous;
                    startService(new Intent(this, Tutorial_service.class));
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    enter = false;
                    break;
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

