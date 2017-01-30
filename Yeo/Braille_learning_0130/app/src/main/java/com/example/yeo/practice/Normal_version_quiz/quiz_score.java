package com.example.yeo.practice.Normal_version_quiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.yeo.practice.Common_menu_sound.Menu_quiz_service;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_abbreviation;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_alphabet;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_final;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_initial;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_letter;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_number;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_sentence;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_vowel;
import com.example.yeo.practice.Normal_version_menu.Menu_quiz_word;
import com.example.yeo.practice.Common_quiz_sound.score_service;


/*
퀴즈 점수를 출력해주는 클래스
 */
public class quiz_score extends AppCompatActivity {
    public static int score;
    public static int sel;
    TextView tv_score;
    TextView comment;
    Menu_quiz_service quiz_menu_service;
    com.example.yeo.practice.Common_quiz_sound.score_service score_service;
    int posx1,posx2,posy1,posy2;
    boolean enter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        tv_score = (TextView) findViewById(R.id.textView);
        comment = (TextView) findViewById(R.id.textView2);



        tv_score.setText(score*20+"점 입니다.");
        if(score >= 5) {
            score_service.result = 5;
            comment.setText("100점!!! 축하합니다!!! 사랑합니다!!!");
            startService(new Intent(this, com.example.yeo.practice.Common_quiz_sound.score_service.class));
        }
        else if(score >= 4) {
            score_service.result = 4;
            comment.setText("잘하셨습니다!! 다음엔 100점 노려보세요!");
            startService(new Intent(this, score_service.class));
        }
        else if (score >= 3) {
            score_service.result = 3;
            comment.setText("아쉽습니다!! 좀더좀더 힘내세요!!");
            startService(new Intent(this, score_service.class));
        }
        else if (score >=  2) {
            score_service.result = 2;
            comment.setText("분발하세요!! 숙련과정부터 차근차근 공부하시기 바랍니다.");
            startService(new Intent(this, score_service.class));
        }
        else if (score >= 1) {
            score_service.result = 1;
            comment.setText("분발하세요!! 기초과정부터 차근차근 공부하시기 바랍니다.");
            startService(new Intent(this, score_service.class));
        }
        else if(score >= 0 ){
            score_service.result = 0;
            comment.setText("할말이...없습니다... 열심히 공부하세요!");
            startService(new Intent(this, score_service.class));
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                posx1 = (int)event.getX();
                posy1 = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                posx2 = (int)event.getX();
                posy2 = (int)event.getY();
                if(enter == true) {

                    if (posx2 < posx1 + 200 && posx2 > posx1 - 200 && posy1 < posy2 + 200 && posy2 > posy2 - 200) {
                        if(sel ==1) {
                            quiz_menu_service.menu_page=0;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_initial.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==2) {
                            quiz_menu_service.menu_page=1;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_vowel.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==3) {
                            quiz_menu_service.menu_page=2;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_final.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==4) {
                            quiz_menu_service.menu_page=3;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_number.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==5) {
                            quiz_menu_service.menu_page=4;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_alphabet.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==6) {
                            quiz_menu_service.menu_page=5;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_sentence.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==7) {
                            quiz_menu_service.menu_page=6;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_abbreviation.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==8) {
                            quiz_menu_service.menu_page=7;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_letter.class);
                            startActivityForResult(intent, 1);
                        }else if(sel ==9) {
                            quiz_menu_service.menu_page=8;
                            startService(new Intent(this, Menu_quiz_service.class));
                            Intent intent = new Intent(quiz_score.this, Menu_quiz_word.class);
                            startActivityForResult(intent, 1);
                        }
                    }
                }
                else
                    enter = true;
                break;
        }
        return true;
    }
}
