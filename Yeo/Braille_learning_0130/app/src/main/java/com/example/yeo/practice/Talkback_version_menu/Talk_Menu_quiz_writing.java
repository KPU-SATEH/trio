package com.example.yeo.practice.Talkback_version_menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_quiz.Talk_quiz_writing_manual;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_sound.slied;

// 쓰기 퀴즈 메뉴 화면

public class Talk_Menu_quiz_writing extends FragmentActivity {

    int newdrag,olddrag;
    int y1drag,y2drag;
    int posx1,posx2,posy1,posy2;
    boolean enter = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );
        setContentView(R.layout.activity_common_menu_quiz_writing);
        View container = findViewById(R.id.activity_common_menu_quiz_writing);
        container.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_HOVER_EXIT :
                        startService(new Intent(Talk_Menu_quiz_writing.this, Sound_Manager.class));
                        posx2 = (int)event.getX();  //손가락 1개를 화면에서 떨어트린 x좌표값 저장
                        posy2 = (int)event.getY();  //손가락 1개를 화면에서 떨어트린 y좌표값 저장
                        if(enter == true) { //손가락 1개를 떨어트린 x,y좌표 지점에 다시 클릭이 이루어진다면 초성 퀴즈로 접속
                            if (posx2 < posx1 + WHclass.Touch_space && posx2 > posx1 - WHclass.Touch_space && posy1 < posy2 + WHclass.Touch_space && posy2 > posy2 - WHclass.Touch_space) {
                                Intent intent = new Intent(Talk_Menu_quiz_writing.this, Talk_quiz_writing_manual.class);
                                startActivityForResult(intent, Menu_info.MENU_QUIZ_INITIAL);
                                MainActivity.Braille_TTS.TTS_Play("점자 학습을 진행하는 여러분을 응원합니다. 쓰기 퀴즈는, 화면에 구성되어 있는 점자와 글자에 대한 정보를 듣고," +
                                        " 점자를 직접 입력하여 정답을 맞추어 보는 메뉴 입니다. 자신이 입력하기를 원하는 점자로 이동하여, 손가락을 약 3초간 가만히 머무르게 되면," +
                                        " 해당 위치에 점자가 입력됩니다. 문제는 총 세문제로 구성되어 있으며, 좌측 하단부터 시작하여 문제를 풀어나가기를 바랍니다. 화면을 한 번 터치하면, 시작됩니다.");
                            }
                        }
                        else    enter = true;
                        break;
                    case MotionEvent.ACTION_HOVER_ENTER :
                        posx1 = (int)event.getX();  //현재 좌표의 x좌표값 저장
                        posy1 = (int)event.getY();  //현재 좌표의 y좌표값 저장
                        break;
                }
                return false;
            }
        });

    }
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:  // 두번째 손가락을 떼었을 경우
                newdrag = (int)event.getX();  // 두번째 손가락이 떨어진 지점의 x좌표값 저장
                y2drag = (int) event.getY();  // 두번째 손가락이 떨어진 지점의 y좌표값 저장
                if(olddrag-newdrag>WHclass.Drag_space) { //손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그할 경우 다음 메뉴로 이동
                    Intent intent = new Intent(this,Talk_Menu_quiz_reading.class); // 읽기 퀴즈 메뉴로 이동
                    startActivityForResult(intent,Menu_info.MENU_QUIZ_READING);
                    slied.slied = Menu_info.next;
                    startService(new Intent(this, slied.class));
                    MainActivity.Braille_TTS.TTS_Play("읽기 퀴즈");
                    finish();
                }
                else if(newdrag-olddrag>WHclass.Drag_space) {  //손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 할 경우 이전 메뉴로 이동
                    Intent intent = new Intent(this,Talk_Menu_quiz_reading.class);
                    startActivityForResult(intent,Menu_info.MENU_QUIZ_READING);
                    slied.slied = Menu_info.pre;
                    startService(new Intent(this, slied.class));
                    MainActivity.Braille_TTS.TTS_Play("읽기 퀴즈");
                    finish();
                }
                else if(y2drag-y1drag>WHclass.Drag_space) { //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                }
                else if (y1drag - y2drag > WHclass.Drag_space) {  //손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                    finish();
                }
                break;
            case MotionEvent.ACTION_DOWN:  //두번째 손가락이 화면에 터치 될 경우
                enter = false; //손가락 1개를 인지하는 화면을 잠금
                olddrag = (int)event.getX();  // 두번째 손가락이 터지된 지점의 x좌표값 저장
                y1drag = (int) event.getY();  // 두번째 손가락이 터지된 지점의 y좌표값 저장
                break;
        }
        return true;
    }
    public void onBackPressed() {
        finish();
    }
}
