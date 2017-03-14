package com.example.yeo.practice.Talkback_version_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.yeo.practice.Common_Tutorial_sound.Common_Tutorial_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Normal_version_menu.Menu_Initial_Consonant;
import com.example.yeo.practice.Normal_version_tutorial.Tutorial_service;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Talk_Tutorial_pra extends FragmentActivity {
    static AnimationDrawable prac_speechani;
    static ImageView prac_speechimage;
    static boolean click = false;
    boolean enter = false;
    static int previous =0 ;
    boolean lock = false;

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

        WHclass.tutorial_progress = 22;
        startService(new Intent(this, Tutorial_service.class));
        decorView.setSystemUiVisibility(uiOption);
        init();
    }

    public void init(){
        prac_speechimage = (ImageView) findViewById(R.id.imageView33);
        prac_speechimage.setBackgroundResource(R.drawable.speechani);
        prac_speechani = (AnimationDrawable) prac_speechimage.getBackground();
        View container = findViewById(R.id.activity_tutorial);
        container.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                if(Common_Tutorial_service.Touch_lock==false) {
                    if(WHclass.touchevent==true) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_HOVER_EXIT:
                                if (lock == false) {
                                    Talk_Tutorial_pra_basic.n = 1;
                                    onStop();
                                    Intent intent = new Intent(Talk_Tutorial_pra.this, Talk_Tutorial_pra_basic.class);
                                    startActivityForResult(intent, 0);
                                    overridePendingTransition(R.anim.fade, R.anim.hold);
                                    finish();
                                    lock = true;
                                }
                                break;
                        }
                    }
                }
                return false;
            }
        });


    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(lock==false) {
            prac_speechani.start();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        findViewById(R.id.imageView33).setBackground(null);
        prac_speechimage=null;
        prac_speechani=null;
        System.gc();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        init();
    }
    protected void onStop(){
        super.onStop();
        SharedPreferences sf = getSharedPreferences("save", 0);
        SharedPreferences.Editor editor = sf.edit();
        editor.putInt("b", WHclass.db);
        editor.commit();
    }
}

