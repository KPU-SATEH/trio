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

import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

public class Tutorial_practice extends FragmentActivity {
    static AnimationDrawable prac_speechani;
    static ImageView prac_speechimage;
    static boolean click = false;
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

        WHclass.tutorial_progress = 22;
        startService(new Intent(this, Tutorial_service.class));
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
                    Tutorial_practice_basic.n = 1;
                    onStop();
                    Intent intent = new Intent(Tutorial_practice.this, Tutorial_practice_basic.class);
                    startActivityForResult(intent, 0);
                    enter = true;
                    break;
            }
        }

        return true;
    }
    protected void onStop(){
        super.onStop();
        SharedPreferences sf = getSharedPreferences("save", 0);
        SharedPreferences.Editor editor = sf.edit();
        editor.putInt("b", WHclass.db);
        editor.commit();
    }
}

