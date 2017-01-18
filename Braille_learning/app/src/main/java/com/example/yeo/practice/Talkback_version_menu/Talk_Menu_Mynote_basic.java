package com.example.yeo.practice.Talkback_version_menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_menu_sound.Menu_mynote_service;
import com.example.yeo.practice.Common_mynote_database.Mynote_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Common_mynote_database.Basic_DB_manager;
import com.example.yeo.practice.Common_menu_sound.Menu_detail_service;
import com.example.yeo.practice.R;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.Talkback_version_Display_Practice.Talk_Braille_short_practice;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_basic_practice_sound.Final_service;
import com.example.yeo.practice.Common_basic_practice_sound.Initial_service;
import com.example.yeo.practice.Common_basic_practice_sound.Num_service;
import com.example.yeo.practice.Common_basic_practice_sound.Sentence_service;
import com.example.yeo.practice.Common_basic_practice_sound.Vowel_service;
import com.example.yeo.practice.Common_basic_practice_sound.abbreviation_service;
import com.example.yeo.practice.Common_basic_practice_sound.alphabet_service;
import com.example.yeo.practice.Common_sound.slied;

//나만의 단어장 메뉴 화면

public class Talk_Menu_Mynote_basic extends FragmentActivity {


    int newdrag,olddrag;
    int posx1,posx2,posy1,posy2;
    int y1drag,y2drag;
    boolean enter = true;

    String result=""; // 나만의단어장의 결과내용을 받아오는 변수
    public static int reference; //나만의 단어장에 들어온 단어의 주소
    public static int reference_index; //나만의 단어장에 들어온 단어의 순서


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
        setContentView(R.layout.activity_common_menu__mynote_basic);

        View container = findViewById(R.id.activity_common_menu__mynote_basic);
        container.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER: //손가락 1개를 화면에 터치하였을 경우
                        startService(new Intent(Talk_Menu_Mynote_basic.this, Sound_Manager.class));
                        posx1 = (int)event.getX();  //현재 좌표의 x좌표값 저장
                        posy1 = (int)event.getY();  //현재 좌표의 y좌표값 저장
                        enter= true;
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT: // 손가락 1개를 화면에서 떨어트렸을 경우
                        posx2 = (int)event.getX(); //손가락 1개를 화면에서 떨어트린 x좌표값 저장
                        posy2 = (int)event.getY(); //손가락 1개를 화면에서 떨어트린 y좌표값 저장
                        if(enter == true) {  //손가락 1개를 떨어트린 x,y좌표 지점에 다시 클릭이 이루어진다면 나만의 단어장으로 접속
                            if (posx2 < posx1 + WHclass.Touch_space && posx2 > posx1 - WHclass.Touch_space && posy1 < posy2 + WHclass.Touch_space && posy2 > posy2 - WHclass.Touch_space) {
                                WHclass.sel =Menu_info.MENU_NOTE ;

                                result= MainActivity.basic_braille_db.getResult();
                                if(MainActivity.basic_braille_db.basic_db_manager.size_count!=0) {
                                    Intent intent = new Intent(Talk_Menu_Mynote_basic.this, Talk_Braille_short_practice.class);
                                    startActivityForResult(intent, Menu_info.MENU_NOTE);
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    Basic_DB_manager.MyNote_down=true;
                                    reference = MainActivity.basic_braille_db.basic_db_manager.getReference(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                                    reference_index = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);

                                    switch(reference){
                                        case 1: //초성연습
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, Initial_service.class));
                                            break;
                                        case 2: //모음연습
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, Vowel_service.class));
                                            break;
                                        case 3:
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, Final_service.class));
                                            break;
                                        case 4: //숫자연습
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, Num_service.class));
                                            break;
                                        case 5: // 알파벳 연습
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, alphabet_service.class));
                                            break;
                                        case 6: // 문장부호 연습
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, Sentence_service.class));
                                            break;
                                        case 7: //약자 및 약어 연습
                                            startService(new Intent(Talk_Menu_Mynote_basic.this, abbreviation_service.class));
                                            break;
                                    }
                                }
                                else {
                                    Mynote_service.menu_page=0;
                                    startService(new Intent(Talk_Menu_Mynote_basic.this, Mynote_service.class));
                                }
                            }
                        }
                        else    enter = true;
                        break;
                }
                return false;
            }
        });






    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:  // 두번째 손가락을 떼었을 경우
                newdrag = (int)event.getX();  // 두번째 손가락이 떨어진 지점의 x좌표값 저장
                y2drag = (int)event.getY(); // 두번째 손가락이 떨어진 지점의 y좌표값 저장
                if(olddrag-newdrag>WHclass.Drag_space) { //손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그할 경우 다음 메뉴로 이동
                    Intent intent = new Intent(this,Talk_Menu_Mynote_master.class);
                    startActivityForResult(intent,Menu_info.MENU_MYNOTE_MASTER);
                    Menu_main_service.menu_page = Menu_info.MENU_MYNOTE_MASTER;
                    slied.slied =Menu_info.next;
                    startService(new Intent(this, slied.class));
                    Menu_mynote_service.menu_page=Menu_info.MENU_MYNOTE_MASTER;
                    startService(new Intent(this,Menu_mynote_service.class));

                    finish();
                }
                else if(newdrag-olddrag>WHclass.Drag_space) { //손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 할 경우 이전 메뉴로 이동
                    Intent intent = new Intent(this,Talk_Menu_Mynote_master.class);
                    startActivityForResult(intent,Menu_info.MENU_MYNOTE_MASTER);
                    Menu_main_service.menu_page = Menu_info.MENU_MYNOTE_MASTER;
                    slied.slied = Menu_info.pre;
                    startService(new Intent(this, slied.class));
                    Menu_mynote_service.menu_page=Menu_info.MENU_MYNOTE_MASTER;
                    startService(new Intent(this,Menu_mynote_service.class));

                    finish();
                }
                else if(y2drag-y1drag> WHclass.Drag_space) { //손가락 2개를 이용하여 상단에서 하단으로 드래그할 경우 현재 메뉴의 상세정보 음성 출력
                    Menu_detail_service.menu_page=4;
                    startService(new Intent(this, Menu_detail_service.class));
                }else if (y1drag - y2drag > WHclass.Drag_space) { //손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                    onBackPressed();
                }
                break;
            case MotionEvent.ACTION_DOWN:  //두번째 손가락이 화면에 터치 될 경우
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
