package com.example.yeo.practice.Normal_version_menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_master_practice_sound.Letter_service;
import com.example.yeo.practice.Common_master_practice_sound.Word_service;
import com.example.yeo.practice.Common_menu_display.Common_menu_display;
import com.example.yeo.practice.Common_menu_sound.Menu_detail_service;
import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_menu_sound.Menu_mynote_service;
import com.example.yeo.practice.Common_mynote_database.Communication_DB_manager;
import com.example.yeo.practice.Common_mynote_database.Master_DB_manager;
import com.example.yeo.practice.Common_mynote_database.Mynote_service;
import com.example.yeo.practice.Common_sound.Braille_Text_To_Speech;
import com.example.yeo.practice.Common_sound.slied;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Normal_version_Display_Practice.Braille_long_practice;
import com.example.yeo.practice.Normal_version_Display_Practice.student_display;
import com.example.yeo.practice.Normal_version_Display_Practice.student_practice;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.WHclass;

public class Menu_Mynote_communication extends AppCompatActivity {
    Common_menu_display m;
    int finger_x[] = new int[3];
    int finger_y[] = new int[3];

    int newdrag,olddrag;
    int posx1,posx2,posy1,posy2;
    int y1drag,y2drag;
    boolean enter = true;
    String result="";
    public static int reference2; //나만의 단어장에 들어온 단어의 주소
    public static int reference_index2; //나만의 단어장에 들어온 단어의 순서
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

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
        Menu_info.DISPLAY = Menu_info.DISPLAY_MYNOTE_COMMUNICATION;
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
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: //손가락 1개를 화면에 터치하였을 경우
                startService(new Intent(this, Sound_Manager.class));
                posx1 = (int)event.getX(); //현재 좌표의 x좌표값 저장
                posy1 = (int)event.getY(); //현재 좌표의 y좌표값 저장

                break;
            case MotionEvent.ACTION_UP: // 손가락 1개를 화면에서 떨어트렸을 경우
                for(int j=0 ; j<3 ; j++){
                    finger_x[j] = -100;
                    finger_y[j] = -100;
                }
                m.finger_set(finger_x[0],finger_y[0],finger_x[1],finger_y[1],finger_x[2],finger_y[2]);
                posx2 = (int)event.getX(); //손가락 1개를 화면에서 떨어트린 x좌표값 저장
                posy2 = (int)event.getY();  //손가락 1개를 화면에서 떨어트린 y좌표값 저장

                if(enter == true) {  //손가락 1개를 떨어트린 x,y좌표 지점에 다시 클릭이 이루어진다면 나만의 단어장으로 접속
                    if (posx2 < posx1 + WHclass.Touch_space && posx2 > posx1 - WHclass.Touch_space && posy1 < posy2 + WHclass.Touch_space && posy2 > posy2 - WHclass.Touch_space) {
                        WHclass.sel = 12 ;
                        result= MainActivity.communication_braille_db.getResult();
                        Mynote_service.menutype=2;
                        if(MainActivity.communication_braille_db.communication_db_manager.size_count!=0) {
                            Intent intent = new Intent(Menu_Mynote_communication.this, Braille_long_practice.class);
                            startActivityForResult(intent, Menu_info.MENU_NOTE);
                            overridePendingTransition(R.anim.fade, R.anim.hold);
                            Mynote_service.menu_page=1;
                            startService(new Intent(this, Mynote_service.class));
                            Communication_DB_manager.MyNote_down=true;
                            reference2 = MainActivity.communication_braille_db.communication_db_manager.getReference(MainActivity.communication_braille_db.communication_db_manager.My_Note_page);
                            reference_index2 = MainActivity.communication_braille_db.communication_db_manager.getReference_index(MainActivity.communication_braille_db.communication_db_manager.My_Note_page);
                        }
                        else {//단어장에 단어가 추가되어 있지 않은경우 접속 차단
                            Mynote_service.menu_page=0;
                            startService(new Intent(this, Mynote_service.class));
                        }

                    }
                }
                else    enter = true;


                break;

            case MotionEvent.ACTION_MOVE :
                int pointer_count2 = event.getPointerCount();
                for(int j=0 ; j<3 ; j++){
                    finger_x[j] = -100;
                    finger_y[j] = -100;
                }
                for(int i=0 ; i<pointer_count2 ; i++) {
                    finger_x[i] = (int) event.getX(i);
                    finger_y[i] = (int) event.getY(i);
                }
                m.finger_set(finger_x[0],finger_y[0],finger_x[1],finger_y[1],finger_x[2],finger_y[2]);
                break;

            case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                newdrag = (int)event.getX();  // 두번째 손가락이 떨어진 지점의 x좌표값 저장
                y2drag = (int)event.getY(); // 두번째 손가락이 떨어진 지점의 y좌표값 저장
                if(olddrag-newdrag>WHclass.Drag_space) { //손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그할 경우 다음 메뉴로 이동
                    Intent intent = new Intent(this,Menu_Mynote_basic.class);
                    startActivityForResult(intent,Menu_info.MENU_MYNOTE_BASIC);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    Menu_main_service.menu_page = Menu_info.MENU_MYNOTE_BASIC;
                    slied.slied =Menu_info.next;
                    startService(new Intent(this, slied.class));
                    Menu_mynote_service.menu_page=Menu_info.MENU_MYNOTE_BASIC;
                    startService(new Intent(this,Menu_mynote_service.class));
                    finish();
                }
                else if(newdrag-olddrag>WHclass.Drag_space) { //손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 할 경우 이전 메뉴로 이동
                    Intent intent = new Intent(this,Menu_Mynote_master.class);
                    startActivityForResult(intent,Menu_info.MENU_MYNOTE_MASTER);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    Menu_main_service.menu_page = Menu_info.MENU_MYNOTE_MASTER;
                    slied.slied = Menu_info.pre;
                    startService(new Intent(this, slied.class));
                    Menu_mynote_service.menu_page=Menu_info.MENU_MYNOTE_MASTER;
                    startService(new Intent(this,Menu_mynote_service.class));
                    finish();
                }
                else if(y2drag-y1drag> WHclass.Drag_space) { //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                    Menu_detail_service.menu_page=29;
                    startService(new Intent(this, Menu_detail_service.class));
                }else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                    onBackPressed();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:  //두번째 손가락이 화면에 터치 될 경우
                enter = false; //손가락 1개를 인지하는 화면을 잠금
                olddrag = (int)event.getX(); // 두번째 손가락이 터지된 지점의 x좌표값 저장
                y1drag = (int) event.getY(); // 두번째 손가락이 터지된 지점의 y좌표값 저장
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Menu_mynote_service.finish = true;
        startService(new Intent(this, Menu_mynote_service.class));
        finish();
    }
}
