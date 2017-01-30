package com.example.yeo.practice.Talkback_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yeo.practice.Common_braille_data.dot_letter;
import com.example.yeo.practice.Common_braille_data.dot_word;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_master_practice_sound.Letter_service;
import com.example.yeo.practice.Common_master_practice_sound.Word_service;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.Common_sound.slied;

public class Talk_Braille_long_practice extends FragmentActivity {
    Talk_Braille_long_display m;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag; // 손가락 1개를 터치하였을 때  y좌표와 손가락 2개를 터치하였을 때 y좌표를 저장하는 변수
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0; // 화면을 문지르며 학습을 하기 위한 컨트롤 변수
    boolean click = true;

    public static dot_letter Dot_letter;
    public static dot_word Dot_word;

    boolean lock = false;




    String array[] = new String[3]; //데이터베이스에 행렬에 대한 정보를 담기 위해 행렬정보를 담는 배열 변수
    public static int reference2; //나만의 단어장에 들어온 단어의 주소
    public static int reference_index2; //나만의 단어장에 들어온 단어의 순서
    int previous_reference=0; //나만의 단어장에서 이전에 출력됬던 음성을 초기화 시키기 위한 변수
    static public boolean pre_reference2 = false; //이전에 음성이 출력되었는지를 체크하는 변수

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



        switch(WHclass.sel){
            case 8: //글자연습
                Dot_letter = new dot_letter();
                break;
            case 9: //단어연습
                Dot_word = new dot_word(); // 단어 단위의 점자 클래스 선언
                break;
        }

        m = new Talk_Braille_long_display(this);
        m.setBackgroundColor(Color.rgb(22,26,44));
        setContentView(m);

        switch(WHclass.sel){
            case 8: //글자연습
                startService(new Intent(this, Letter_service.class));
                break;
            case 9: //단어연습
                startService(new Intent(this, Word_service.class));
                break;
        }

        View container = m;
        container.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        startService(new Intent(Talk_Braille_long_practice.this, Sound_Manager.class));
                        m.x = (int) event.getX(); //x좌표를 저장
                        m.y = (int) event.getY(); //y좌표를 저장
                        touch_init(0);
                        TouchEvent();
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        if (click == false) {
                            click = true;
                        }
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        m.x = (int)event.getX(); // 현재 터치한 지점의 x좌표를 저장
                        m.y = (int)event.getY(); // 현재 터치한 지점의 y좌표를 저장
                        TouchEvent();
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:  // 두번째 손가락을 화면에서 떼었을 경우
                click = true;
                if(lock==false) {
                    newdrag = (int) event.getX(); // 두번째 손가락이 화면에서 떨어진 지점의 x 좌표 저장
                    y2drag = (int) event.getY();// 두번째 손가락이 화면에서 떨어진 지점의 y 좌표 저장

                    if (olddrag - newdrag > WHclass.Drag_space) { // 다음 화면의 점자 학습 진행
                        slied.slied = Menu_info.next;
                        startService(new Intent(this, slied.class));
                        switch (WHclass.sel) {
                            case 8: //글자연습
                                startService(new Intent(this, Letter_service.class));
                                break;
                            case 9: //단어 연습
                                startService(new Intent(this, Word_service.class));
                                break;
                            case 10:
                                MyNote_Stop_service();
                                MainActivity.master_braille_db.master_db_manager.My_Note_page++;
                                if (MainActivity.master_braille_db.master_db_manager.My_Note_page >= MainActivity.master_braille_db.master_db_manager.size_count) //가장 마지막 학습내용까지 진행됬다면
                                    onBackPressed(); //종료
                                else  //아직 학습이 진행중이면
                                    MyNote_Start_service();
                                break;
                        }
                        m.MyView3_init();
                        m.invalidate();
                    } else if (newdrag - olddrag > WHclass.Drag_space) { // 이전 화면의 점자 학습 진행
                        slied.slied = Menu_info.pre;
                        startService(new Intent(this, slied.class));
                        switch (WHclass.sel) {
                            case 8: //글자연습
                                startService(new Intent(this, Letter_service.class));
                                break;
                            case 9: //단어연습
                                startService(new Intent(this, Word_service.class));
                                break;
                            case 10:
                                MyNote_Stop_service(); // 이전에 실행되던 음성을 중지하는 함수

                                if (MainActivity.master_braille_db.master_db_manager.My_Note_page > 0)
                                    MainActivity.master_braille_db.master_db_manager.My_Note_page--;
                                MyNote_Start_service(); //현재 화면의 음성 출력
                                break;
                        }
                        m.MyView3_init();
                        m.invalidate();
                    } else if (y2drag - y1drag > WHclass.Drag_space) { // 현재 화면 점자 정보 다시 듣기
                        switch (WHclass.sel) {
                            case 8: //글자연습
                                startService(new Intent(this, Letter_service.class));
                                break;
                            case 9: //단어연습
                                startService(new Intent(this, Word_service.class));
                                break;
                            case 10:
                                MyNote_Start_service();
                                break;
                        }
                    } else if (y1drag - y2drag > WHclass.Drag_space) { // 현재 점자 학습 종료
                        m.page = 0;
                        m.MyView3_init();
                        switch (WHclass.sel) {
                            case 8: //글자연습
                                Letter_service.finish = true;
                                startService(new Intent(this, Letter_service.class));
                                break;
                            case 9: //단어연습
                                Word_service.finish = true;
                                startService(new Intent(this, Word_service.class));
                                break;
                            case 10:
                                onBackPressed();
                        }
                        finish();
                    }
                }
                else
                    lock = false;
                break;
            case MotionEvent.ACTION_DOWN: //두 번째 손가락을 터치하였을 때
                click = false; // 제스처 기능을 위해 손가락 1개를 인지하는 화면을 잠금
                olddrag = (int)event.getX(); // 두번쨰 손가락이 터치한 지점의 x좌표 저장
                y1drag = (int) event.getY(); // 두번째 손가락이 터치한 지점의 y좌표 저장
                break;
            case MotionEvent.ACTION_POINTER_UP :
                int pointer_count = event.getPointerCount();
                if(pointer_count==2)
                    update();
                lock=true;
                break;

        }
        return true;
    }
    public void update(){ //1초동안 화면에 연속으로 2번의 터치가 발생됬을 경우 데이터베이스로 현재 단어정보를 전송함
                String result ="";
                array[0]="";
                array[1]="";
                array[2]="";
                if(WHclass.sel==Menu_info.MENU_NOTE) {
                    MyNote_Stop_service();
                    MainActivity.master_braille_db.delete(MainActivity.master_braille_db.master_db_manager.getId(MainActivity.master_braille_db.master_db_manager.My_Note_page));
                    result = MainActivity.master_braille_db.getResult();

                    if(MainActivity.master_braille_db.master_db_manager.size_count==0)
                        onBackPressed();
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    m.MyView3_init();
                    m.invalidate();
                    MyNote_Start_service();
                }
                else {
                    for (int i = 0; i < 3; i++) {
                        for(int j=0; j<m.dot_count*2 ; j++){
                            array[i] = array[i]+Integer.toString(m.text_7[i][j]); // 3개의 배열에 1행 2행 3행을 집어넣음
                        }
                    }
                    result = MainActivity.master_braille_db.insert(m.dot_count, m.textname_7, array[0], array[1], array[2], Menu_info.MENU_INFO, m.page);  //데이터베이스에 입력하고, 성공문자를 돌려받음
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show(); //성공했다는 메시지를 출력
                }

    }




    public void MyNote_Stop_service(){
        if(pre_reference2==true) { //이전에 출력되었던 학습 음성 종료
            previous_reference = MainActivity.master_braille_db.master_db_manager.getReference(MainActivity.master_braille_db.master_db_manager.My_Note_page);
            switch (previous_reference) {
                case 8: //글자연습
                    startService(new Intent(this, Letter_service.class));
                    break;
                case 9: //단어연습
                    startService(new Intent(this, Word_service.class));
                    break;
            }
        }
    }
    public void MyNote_Start_service(){
        reference2 = MainActivity.master_braille_db.master_db_manager.getReference(MainActivity.master_braille_db.master_db_manager.My_Note_page);
        reference_index2 = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);

        switch (reference2) {  //나만의 단어장 음성출력
            case 8: //글자연습
                startService(new Intent(this, Letter_service.class));
                break;
            case 9: //단어연습
                startService(new Intent(this, Word_service.class));
                break;
        }
    }
    public void touch_init(int coordinate){ //문지르기 기능을 위한 컨트롤 변수 초기화
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;
        result6=0;

        switch(coordinate){
            case 1: //1번점자
                result1=1;
                break;
            case 2: //2번점자
                result2=1;
                break;
            case 3: //3번점자
                result3=1;
                break;
            case 4: //4번점자
                result4=1;
                break;
            case 5: //5번점자
                result5=1;
                break;
            case 6: //6번점자
                result6=1;
                break;
            default: //그외
                break;

        }
    }

    public void TouchEvent(){
        if ((m.x <m.bigcircle*2) && (m.x>m.bigcircle*(-2))&&(m.y >m.bigcircle*(-2))&&(m.y <(m.bigcircle*2))) {
        }
                /*
                터치한 지점의 좌표가 돌출된 점자일 경우 남성의 음성으로 점자번호를 안내하면서 강한진동이 발생
                터치한 지점의 좌표가 비돌출된 점자일 경우 여성의 음성으로 점자번호를 안내함
                 */
        if(click==true) {
            if (m.x < m.notarget7_width[0][0] + m.bigcircle && m.x > m.notarget7_width[0][0] - m.bigcircle && m.y < m.notarget7_height[0][0] + m.bigcircle && m.y > m.notarget7_height[0][0] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][0] + m.bigcircle && m.x > m.target7_width[0][0] - m.bigcircle && m.y < m.target7_height[0][0] + m.bigcircle && m.y > m.target7_height[0][0] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //첫번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][0] + m.bigcircle && m.x > m.notarget7_width[1][0] - m.bigcircle && m.y < m.notarget7_height[1][0] + m.bigcircle && m.y > m.notarget7_height[1][0] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][0] + m.bigcircle && m.x > m.target7_width[1][0] - m.bigcircle && m.y < m.target7_height[1][0] + m.bigcircle && m.y > m.target7_height[1][0] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //첫번째 칸 2번 점자

            else if (m.x < m.notarget7_width[2][0] + m.bigcircle && m.x > m.notarget7_width[2][0] - m.bigcircle && m.y < m.notarget7_height[2][0] + m.bigcircle && m.y > m.notarget7_height[2][0] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][0] + m.bigcircle && m.x > m.target7_width[2][0] - m.bigcircle && m.y < m.target7_height[2][0] + m.bigcircle && m.y > m.target7_height[2][0] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //첫번째 칸 3번 점자

            else if (m.x < m.notarget7_width[0][1] + m.bigcircle && m.x > m.notarget7_width[0][1] - m.bigcircle && m.y < m.notarget7_height[0][1] + m.bigcircle && m.y > m.notarget7_height[0][1] - m.bigcircle) {
                WHclass.number = 4;
                if (result4 == 0) {
                    if (m.x < m.target7_width[0][1] + m.bigcircle && m.x > m.target7_width[0][1] - m.bigcircle && m.y < m.target7_height[0][1] + m.bigcircle && m.y > m.target7_height[0][1] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //첫번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][1] + m.bigcircle && m.x > m.notarget7_width[1][1] - m.bigcircle && m.y < m.notarget7_height[1][1] + m.bigcircle && m.y > m.notarget7_height[1][1] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][1] + m.bigcircle && m.x > m.target7_width[1][1] - m.bigcircle && m.y < m.target7_height[1][1] + m.bigcircle && m.y > m.target7_height[1][1] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //첫번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][1] + m.bigcircle && m.x > m.notarget7_width[2][1] - m.bigcircle && m.y < m.notarget7_height[2][1] + m.bigcircle && m.y > m.notarget7_height[2][1] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][1] + m.bigcircle && m.x > m.target7_width[2][1] - m.bigcircle && m.y < m.target7_height[2][1] + m.bigcircle && m.y > m.target7_height[2][1] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //첫번째 칸 6번 점자

            else if (m.x < m.notarget7_width[0][2] + m.bigcircle && m.x > m.notarget7_width[0][2] - m.bigcircle && m.y < m.notarget7_height[0][2] + m.bigcircle && m.y > m.notarget7_height[0][2] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][2] + m.bigcircle && m.x > m.target7_width[0][2] - m.bigcircle && m.y < m.target7_height[0][2] + m.bigcircle && m.y > m.target7_height[0][2] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //두번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][2] + m.bigcircle && m.x > m.notarget7_width[1][2] - m.bigcircle && m.y < m.notarget7_height[1][2] + m.bigcircle && m.y > m.notarget7_height[1][2] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][2] + m.bigcircle && m.x > m.target7_width[1][2] - m.bigcircle && m.y < m.target7_height[1][2] + m.bigcircle && m.y > m.target7_height[1][2] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //두번째 칸 2번 점자
            else if (m.x < m.notarget7_width[2][2] + m.bigcircle && m.x > m.notarget7_width[2][2] - m.bigcircle && m.y < m.notarget7_height[2][2] + m.bigcircle && m.y > m.notarget7_height[2][2] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][2] + m.bigcircle && m.x > m.target7_width[2][2] - m.bigcircle && m.y < m.target7_height[2][2] + m.bigcircle && m.y > m.target7_height[2][2] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //두번째 칸 3번 점자
            else if (m.x < m.notarget7_width[0][3] + m.bigcircle && m.x > m.notarget7_width[0][3] - m.bigcircle && m.y < m.notarget7_height[0][3] + m.bigcircle && m.y > m.notarget7_height[0][3] - m.bigcircle) {
                WHclass.number = 4;
                if (result4== 0) {
                    if (m.x < m.target7_width[0][3] + m.bigcircle && m.x > m.target7_width[0][3] - m.bigcircle && m.y < m.target7_height[0][3] + m.bigcircle && m.y > m.target7_height[0][3] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //두번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][3] + m.bigcircle && m.x > m.notarget7_width[1][3] - m.bigcircle && m.y < m.notarget7_height[1][3] + m.bigcircle && m.y > m.notarget7_height[1][3] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][3] + m.bigcircle && m.x > m.target7_width[1][3] - m.bigcircle && m.y < m.target7_height[1][3] + m.bigcircle && m.y > m.target7_height[1][3] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //두번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][3] + m.bigcircle && m.x > m.notarget7_width[2][3] - m.bigcircle && m.y < m.notarget7_height[2][3] + m.bigcircle && m.y > m.notarget7_height[2][3] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][3] + m.bigcircle && m.x > m.target7_width[2][3] - m.bigcircle && m.y < m.target7_height[2][3] + m.bigcircle && m.y > m.target7_height[2][3] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //두번째 칸 6번 점자
            else if (m.x < m.notarget7_width[0][4] + m.bigcircle && m.x > m.notarget7_width[0][4] - m.bigcircle && m.y < m.notarget7_height[0][4] + m.bigcircle && m.y > m.notarget7_height[0][4] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][4] + m.bigcircle && m.x > m.target7_width[0][4] - m.bigcircle && m.y < m.target7_height[0][4] + m.bigcircle && m.y > m.target7_height[0][4] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //세 번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][4] + m.bigcircle && m.x > m.notarget7_width[1][4] - m.bigcircle && m.y < m.notarget7_height[1][4] + m.bigcircle && m.y > m.notarget7_height[1][4] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][4] + m.bigcircle && m.x > m.target7_width[1][4] - m.bigcircle && m.y < m.target7_height[1][4] + m.bigcircle && m.y > m.target7_height[1][4] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //세 번째 칸 2번 점자
            else if (m.x < m.notarget7_width[2][4] + m.bigcircle && m.x > m.notarget7_width[2][4] - m.bigcircle && m.y < m.notarget7_height[2][4] + m.bigcircle && m.y > m.notarget7_height[2][4] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][4] + m.bigcircle && m.x > m.target7_width[2][4] - m.bigcircle && m.y < m.target7_height[2][4] + m.bigcircle && m.y > m.target7_height[2][4] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //세 번째 칸 3번 점자
            else if (m.x < m.notarget7_width[0][5] + m.bigcircle && m.x > m.notarget7_width[0][5] - m.bigcircle && m.y < m.notarget7_height[0][5] + m.bigcircle && m.y > m.notarget7_height[0][5] - m.bigcircle) {
                WHclass.number = 4;
                if (result4 == 0) {
                    if (m.x < m.target7_width[0][5] + m.bigcircle && m.x > m.target7_width[0][5] - m.bigcircle && m.y < m.target7_height[0][5] + m.bigcircle && m.y > m.target7_height[0][5] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //세 번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][5] + m.bigcircle && m.x > m.notarget7_width[1][5] - m.bigcircle && m.y < m.notarget7_height[1][5] + m.bigcircle && m.y > m.notarget7_height[1][5] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][5] + m.bigcircle && m.x > m.target7_width[1][5] - m.bigcircle && m.y < m.target7_height[1][5] + m.bigcircle && m.y > m.target7_height[1][5] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //세 번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][5] + m.bigcircle && m.x > m.notarget7_width[2][5] - m.bigcircle && m.y < m.notarget7_height[2][5] + m.bigcircle && m.y > m.notarget7_height[2][5] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][5] + m.bigcircle && m.x > m.target7_width[2][5] - m.bigcircle && m.y < m.target7_height[2][5] + m.bigcircle && m.y > m.target7_height[2][5] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //세 번째 칸 6번 점자
            else if (m.x < m.notarget7_width[0][6] + m.bigcircle && m.x > m.notarget7_width[0][6] - m.bigcircle && m.y < m.notarget7_height[0][6] + m.bigcircle && m.y > m.notarget7_height[0][6] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][6] + m.bigcircle && m.x > m.target7_width[0][6] - m.bigcircle && m.y < m.target7_height[0][6] + m.bigcircle && m.y > m.target7_height[0][6] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //네번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][6] + m.bigcircle && m.x > m.notarget7_width[1][6] - m.bigcircle && m.y < m.notarget7_height[1][6] + m.bigcircle && m.y > m.notarget7_height[1][6] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][6] + m.bigcircle && m.x > m.target7_width[1][6] - m.bigcircle && m.y < m.target7_height[1][6] + m.bigcircle && m.y > m.target7_height[1][6] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //네번째 칸 2번 점자
            else if (m.x < m.notarget7_width[2][6] + m.bigcircle && m.x > m.notarget7_width[2][6] - m.bigcircle && m.y < m.notarget7_height[2][6] + m.bigcircle && m.y > m.notarget7_height[2][6] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][6] + m.bigcircle && m.x > m.target7_width[2][6] - m.bigcircle && m.y < m.target7_height[2][6] + m.bigcircle && m.y > m.target7_height[2][6] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //네번째 칸 3번 점자
            else if (m.x < m.notarget7_width[0][7] + m.bigcircle && m.x > m.notarget7_width[0][7] - m.bigcircle && m.y < m.notarget7_height[0][7] + m.bigcircle && m.y > m.notarget7_height[0][7] - m.bigcircle) {
                WHclass.number = 4;
                if (result4 == 0) {
                    if (m.x < m.target7_width[0][7] + m.bigcircle && m.x > m.target7_width[0][7] - m.bigcircle && m.y < m.target7_height[0][7] + m.bigcircle && m.y > m.target7_height[0][7] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //네번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][7] + m.bigcircle && m.x > m.notarget7_width[1][7] - m.bigcircle && m.y < m.notarget7_height[1][7] + m.bigcircle && m.y > m.notarget7_height[1][7] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][7] + m.bigcircle && m.x > m.target7_width[1][7] - m.bigcircle && m.y < m.target7_height[1][7] + m.bigcircle && m.y > m.target7_height[1][7] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //네번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][7] + m.bigcircle && m.x > m.notarget7_width[2][7] - m.bigcircle && m.y < m.notarget7_height[2][7] + m.bigcircle && m.y > m.notarget7_height[2][7] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][7] + m.bigcircle && m.x > m.target7_width[2][7] - m.bigcircle && m.y < m.target7_height[2][7] + m.bigcircle && m.y > m.target7_height[2][7] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //네번째 칸 6번 점자
            else if (m.x < m.notarget7_width[0][8] + m.bigcircle && m.x > m.notarget7_width[0][8] - m.bigcircle && m.y < m.notarget7_height[0][8] + m.bigcircle && m.y > m.notarget7_height[0][8] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][8] + m.bigcircle && m.x > m.target7_width[0][8] - m.bigcircle && m.y < m.target7_height[0][8] + m.bigcircle && m.y > m.target7_height[0][8] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //다섯번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][8] + m.bigcircle && m.x > m.notarget7_width[1][8] - m.bigcircle && m.y < m.notarget7_height[1][8] + m.bigcircle && m.y > m.notarget7_height[1][8] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][8] + m.bigcircle && m.x > m.target7_width[1][8] - m.bigcircle && m.y < m.target7_height[1][8] + m.bigcircle && m.y > m.target7_height[1][8] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //다섯번째 칸 2번 점자
            else if (m.x < m.notarget7_width[2][8] + m.bigcircle && m.x > m.notarget7_width[2][8] - m.bigcircle && m.y < m.notarget7_height[2][8] + m.bigcircle && m.y > m.notarget7_height[2][8] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][8] + m.bigcircle && m.x > m.target7_width[2][8] - m.bigcircle && m.y < m.target7_height[2][8] + m.bigcircle && m.y > m.target7_height[2][8] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //다섯번째 칸 3번 점자
            else if (m.x < m.notarget7_width[0][9] + m.bigcircle && m.x > m.notarget7_width[0][9] - m.bigcircle && m.y < m.notarget7_height[0][9] + m.bigcircle && m.y > m.notarget7_height[0][9] - m.bigcircle) {
                WHclass.number = 4;
                if (result4 == 0) {
                    if (m.x < m.target7_width[0][9] + m.bigcircle && m.x > m.target7_width[0][9] - m.bigcircle && m.y < m.target7_height[0][9] + m.bigcircle && m.y > m.target7_height[0][9] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //다섯번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][9] + m.bigcircle && m.x > m.notarget7_width[1][9] - m.bigcircle && m.y < m.notarget7_height[1][9] + m.bigcircle && m.y > m.notarget7_height[1][9] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][9] + m.bigcircle && m.x > m.target7_width[1][9] - m.bigcircle && m.y < m.target7_height[1][9] + m.bigcircle && m.y > m.target7_height[1][9] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //다섯번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][9] + m.bigcircle && m.x > m.notarget7_width[2][9] - m.bigcircle && m.y < m.notarget7_height[2][9] + m.bigcircle && m.y > m.notarget7_height[2][9] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][9] + m.bigcircle && m.x > m.target7_width[2][9] - m.bigcircle && m.y < m.target7_height[2][9] + m.bigcircle && m.y > m.target7_height[2][9] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //다섯번째 칸 6번 점자
            else if (m.x < m.notarget7_width[0][10] + m.bigcircle && m.x > m.notarget7_width[0][10] - m.bigcircle && m.y < m.notarget7_height[0][10] + m.bigcircle && m.y > m.notarget7_height[0][10] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][10] + m.bigcircle && m.x > m.target7_width[0][10] - m.bigcircle && m.y < m.target7_height[0][10] + m.bigcircle && m.y > m.target7_height[0][10] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //여섯번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][10] + m.bigcircle && m.x > m.notarget7_width[1][10] - m.bigcircle && m.y < m.notarget7_height[1][10] + m.bigcircle && m.y > m.notarget7_height[1][10] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][10] + m.bigcircle && m.x > m.target7_width[1][10] - m.bigcircle && m.y < m.target7_height[1][10] + m.bigcircle && m.y > m.target7_height[1][10] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //여섯번째 칸 2번 점자
            else if (m.x < m.notarget7_width[2][10] + m.bigcircle && m.x > m.notarget7_width[2][10] - m.bigcircle && m.y < m.notarget7_height[2][10] + m.bigcircle && m.y > m.notarget7_height[2][10] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][10] + m.bigcircle && m.x > m.target7_width[2][10] - m.bigcircle && m.y < m.target7_height[2][10] + m.bigcircle && m.y > m.target7_height[2][10] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //여섯번째 칸 3번 점자
            else if (m.x < m.notarget7_width[0][11] + m.bigcircle && m.x > m.notarget7_width[0][11] - m.bigcircle && m.y < m.notarget7_height[0][11] + m.bigcircle && m.y > m.notarget7_height[0][11] - m.bigcircle) {
                WHclass.number = 4;
                if (result4 == 0) {
                    if (m.x < m.target7_width[0][11] + m.bigcircle && m.x > m.target7_width[0][11] - m.bigcircle && m.y < m.target7_height[0][11] + m.bigcircle && m.y > m.target7_height[0][11] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //여섯번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][11] + m.bigcircle && m.x > m.notarget7_width[1][11] - m.bigcircle && m.y < m.notarget7_height[1][11] + m.bigcircle && m.y > m.notarget7_height[1][11] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][11] + m.bigcircle && m.x > m.target7_width[1][11] - m.bigcircle && m.y < m.target7_height[1][11] + m.bigcircle && m.y > m.target7_height[1][11] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //여섯번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][11] + m.bigcircle && m.x > m.notarget7_width[2][11] - m.bigcircle && m.y < m.notarget7_height[2][11] + m.bigcircle && m.y > m.notarget7_height[2][11] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][11] + m.bigcircle && m.x > m.target7_width[2][11] - m.bigcircle && m.y < m.target7_height[2][11] + m.bigcircle && m.y > m.target7_height[2][11] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //여섯번째 칸 6번 점자
            else if (m.x < m.notarget7_width[0][12] + m.bigcircle && m.x > m.notarget7_width[0][12] - m.bigcircle && m.y < m.notarget7_height[0][12] + m.bigcircle && m.y > m.notarget7_height[0][12] - m.bigcircle) {
                WHclass.number = 1;
                if (result1 == 0) {
                    if (m.x < m.target7_width[0][12] + m.bigcircle && m.x > m.target7_width[0][12] - m.bigcircle && m.y < m.target7_height[0][12] + m.bigcircle && m.y > m.target7_height[0][12] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(1);
                }
            } //일곱번째 칸 1번 점자
            else if (m.x < m.notarget7_width[1][12] + m.bigcircle && m.x > m.notarget7_width[1][12] - m.bigcircle && m.y < m.notarget7_height[1][12] + m.bigcircle && m.y > m.notarget7_height[1][12] - m.bigcircle) {
                WHclass.number = 2;
                if (result2 == 0) {
                    if (m.x < m.target7_width[1][12] + m.bigcircle && m.x > m.target7_width[1][12] - m.bigcircle && m.y < m.target7_height[1][12] + m.bigcircle && m.y > m.target7_height[1][12] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(2);
                }
            } //일곱번째 칸 2번 점자
            else if (m.x < m.notarget7_width[2][12] + m.bigcircle && m.x > m.notarget7_width[2][12] - m.bigcircle && m.y < m.notarget7_height[2][12] + m.bigcircle && m.y > m.notarget7_height[2][12] - m.bigcircle) {
                WHclass.number = 3;
                if (result3 == 0) {
                    if (m.x < m.target7_width[2][12] + m.bigcircle && m.x > m.target7_width[2][12] - m.bigcircle && m.y < m.target7_height[2][12] + m.bigcircle && m.y > m.target7_height[2][12] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(3);
                }
            } //일곱번째 칸 3번 점자
            else if (m.x < m.notarget7_width[0][13] + m.bigcircle && m.x > m.notarget7_width[0][13] - m.bigcircle && m.y < m.notarget7_height[0][13] + m.bigcircle && m.y > m.notarget7_height[0][13] - m.bigcircle) {
                WHclass.number = 4;
                if (result4 == 0) {
                    if (m.x < m.target7_width[0][13] + m.bigcircle && m.x > m.target7_width[0][13] - m.bigcircle && m.y < m.target7_height[0][13] + m.bigcircle && m.y > m.target7_height[0][13] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(4);
                }
            } //일곱번째 칸 4번 점자
            else if (m.x < m.notarget7_width[1][13] + m.bigcircle && m.x > m.notarget7_width[1][13] - m.bigcircle && m.y < m.notarget7_height[1][13] + m.bigcircle && m.y > m.notarget7_height[1][13] - m.bigcircle) {
                WHclass.number = 5;
                if (result5 == 0) {
                    if (m.x < m.target7_width[1][13] + m.bigcircle && m.x > m.target7_width[1][13] - m.bigcircle && m.y < m.target7_height[1][13] + m.bigcircle && m.y > m.target7_height[1][13] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(5);
                }
            } //일곱번째 칸 5번 점자
            else if (m.x < m.notarget7_width[2][13] + m.bigcircle && m.x > m.notarget7_width[2][13] - m.bigcircle && m.y < m.notarget7_height[2][13] + m.bigcircle && m.y > m.notarget7_height[2][13] - m.bigcircle) {
                WHclass.number = 6;
                if (result6 == 0) {
                    if (m.x < m.target7_width[2][13] + m.bigcircle && m.x > m.target7_width[2][13] - m.bigcircle && m.y < m.target7_height[2][13] + m.bigcircle && m.y > m.target7_height[2][13] - m.bigcircle) {
                        WHclass.target = true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Strong_vibe);

                    } else {
                        WHclass.target = false;
                        startService(new Intent(this, Number.class));

                    }
                    touch_init(6);
                }
            } //일곱번째 칸 6번 점자
            else if(m.y > m.height1-(m.bigcircle*2) && m.y<m.height1-m.bigcircle){
                WHclass.number=7;
                WHclass.target= true;
                startService(new Intent(this, Number.class));
                m.vibrator.vibrate(WHclass.Weak_vibe);
                touch_init(0);
            }
            else { // 그외 지점을 터치하였을 경우 문지르기 기능을 위한 컨트롤 변수 초기화
                touch_init(0);
                WHclass.number=0;
            }
            switch(m.dot_count){
                case 1: // 점자의 칸수가 한 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width2+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
                case 2:// 점자의 칸수가 두 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width4+m.bigcircle && m.x<m.width4+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
                case 3:// 점자의 칸수가 세 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width6+m.bigcircle && m.x<m.width6+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
                case 4:// 점자의 칸수가 네 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width8+m.bigcircle && m.x<m.width8+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
                case 5:// 점자의 칸수가 다섯 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width10+m.bigcircle && m.x<m.width10+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
                case 6:// 점자의 칸수가 여섯 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width12+m.bigcircle && m.x<m.width12+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
                case 7:// 점자의 칸수가 일곱 칸일때 구분선 및 경고음 발생 영역 지정
                    if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width12+m.bigcircle && m.x<m.width13-m.bigcircle){
                        WHclass.number=8;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    else if(m.x > m.width14+m.bigcircle && m.x<m.width14+(m.bigcircle*2)){
                        WHclass.number=7;
                        WHclass.target= true;
                        startService(new Intent(this, Number.class));
                        m.vibrator.vibrate(WHclass.Weak_vibe);
                        touch_init(0);
                    }
                    break;
            }
        }

        m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
    }
    @Override
    public void onBackPressed() { // 뒤로가기 키를 눌렀을때 점자 학습을 위한 변수 초기화 및 종료
        m.page = 0;
        m.MyView3_init();

        switch(WHclass.sel){
            case 8:
                Letter_service.finish = true;
                startService(new Intent(this, Letter_service.class));
            case 9: //단어연습
                Word_service.finish = true;
                startService(new Intent(this, Word_service.class));
                break;
            case 10:
                MyNote_Stop_service();
                MainActivity.master_braille_db.master_db_manager.My_Note_page=0;
                break;
        }
        finish();
    }
}
