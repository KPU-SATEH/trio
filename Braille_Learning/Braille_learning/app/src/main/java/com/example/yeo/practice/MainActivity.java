package com.example.yeo.practice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yeo.practice.Common_menu_sound.Menu_main_service;
import com.example.yeo.practice.Common_menu_sound.Version_check_service;
import com.example.yeo.practice.Common_mynote_database.Basic_Braille_DB;
import com.example.yeo.practice.Common_mynote_database.Communication_Braille_DB;
import com.example.yeo.practice.Common_mynote_database.Master_Braille_DB;
import com.example.yeo.practice.Common_sound.Braille_Text_To_Speech;
import com.example.yeo.practice.Normal_version_menu.Menu_Tutorial;
import com.example.yeo.practice.Normal_version_tutorial.Tutorial;
import com.example.yeo.practice.Talkback_version_menu.Talk_Menu_tutorial;

import junit.runner.Version;

import net.daum.mf.speech.api.SpeechRecognizerManager;
import net.daum.mf.speech.api.TextToSpeechManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/*
Braile_learning Application이 시작되면 가장먼저 실행되는 Main 클래스

 */
public class MainActivity extends FragmentActivity {
    static public AnimationDrawable versionani;
    static public ImageView versionimage;

    static public float width,height;
    final static int CODE = 1;
    static public Basic_Braille_DB basic_braille_db; // 나만의 기초 단어장을 위한 데이터베이스
    static public Master_Braille_DB master_braille_db; // 나만의 숙련 단어장을 위한 데이터베이스
    static public Communication_Braille_DB communication_braille_db; // 나만의 선생님의 단어장을 위한 데이터베이스

    static public Braille_Text_To_Speech Braille_TTS = new Braille_Text_To_Speech();

    private TimerTask second; // 버전확인을 위한 타이머
    private final Handler handler = new Handler();

    boolean one_finger=false; //hover event 발생시 적용될 변수
    int type=-1; //버전확인을 위한 변수
    int HOVER = 1; // 시각장애인 전용버전
    int NORMAL = 2; // 일반사용자 전용 버전
    int INIT = 0; //버전 초기화 변수
    int hover_count = 0 ; // 시각장애인 전용버전 카운트 변수
    int normal_count = 0; // 일반사용자 전용버전 카운트 변수

    int time_check=0; // 타입 카운트 변수
    Timer timer =null;

    boolean  Blind_person=false; //토크백이 활성화 되어있을 경우 변수가 true로 바뀜

    boolean test= false;

    int oldx,oldy,newx,newy;

    public static boolean version_finish=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //스마트폰 가로로 하고, 상단바와 하단바 가림
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );
        setContentView(R.layout.activity_common_main);

        if(Build.VERSION.SDK_INT>=14)

        {
            android.view.Display display = ((WindowManager) this.getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
            Point realSize = new Point();
            try {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            width=realSize.y;
            height=realSize.x;
        }
        else{
            Display display = getWindowManager().getDefaultDisplay(); //해상도 불러오는 함수
            Point size = new Point();
            display.getSize(size);
            width = size.y;   // 스마트폰 가로 해상도
            height = size.x;  // 스마트폰 세로 해상도
        }

        //뉴톤 라이브러리 초기화
        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());
        SpeechRecognizerManager.getInstance().initializeLibrary(this);


        WHclass.height = height;  //WHclass height에 세로 해상도 저장
        WHclass.width = width;  //WHclass width에 가로 해상도 저장
        WHclass.Touch_space = width * (float) 0.1; //터치 영역을 저장하는 메크로
        WHclass.Drag_space = width * (float) 0.2; //드래그 영역을 저장하는 메크로
        // WidthHeight WH = new WidthHeight(width,height);
        //SharedPreferences sf= getSharedPreferences("save", 0);
        //int i = sf.getInt("b", 0);

        basic_braille_db = new Basic_Braille_DB(getApplicationContext(),"BRAILLE.db",null,1); //BRAILLE 라는 이름을 가진 테이블
        master_braille_db = new Master_Braille_DB(getApplicationContext(),"BRAILLE2.db",null,1); //BRAILLE2 라는 이름을 가진 테이블
        communication_braille_db = new Communication_Braille_DB(getApplicationContext(),"BRAILLE3.db",null,1); //BRAILLE2 라는 이름을 가진 테이블

        Version_check_service.menu_page=Menu_info.version_check;
        startService(new Intent(this, Version_check_service.class));







        View container = findViewById(R.id.activity_main);

        container.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                if(test==false) {
                    if (Blind_person == true) { // 시각장애인 전용버전이라면
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_HOVER_ENTER: //손가락 1개를 화면에 터치하였을 경우
                                if(test==false) {
                                    one_finger = true;
                                    type = HOVER; // 시각장애인 전용버전으로 타입 변경
                                    Version_check_service.menu_page=Menu_info.version_start;
                                    startService(new Intent(MainActivity.this, Version_check_service.class));
                                    Timer_Reset(); //시간 카운트 시작
                                    versionani.start();

                                }
                                break;
                            case MotionEvent.ACTION_HOVER_MOVE:
                                type = HOVER;
                                break;
                            case MotionEvent.ACTION_HOVER_EXIT: // 손가락 1개를 화면에서 떨어트렸을 경우
                                if (test == false) {//아직 버전 판별이 되지 않았을 경우
                                    Version_check_service.menu_page=Menu_info.version_reset;
                                    startService(new Intent(MainActivity.this, Version_check_service.class));
                                    type = INIT; //타입 초기화
                                    Timer_Stop(); //시간 카운트 정지
                                    if(versionani.isRunning()){
                                        versionani.stop();
                                        versionani.start();
                                        versionani.stop();
                                    }
                                }
                                else {
                                    test = false; //버전 판별이 끝났을 경우
                                }
                                one_finger = false;

                                break;
                        }
                    }
                }
                return false;
            }
        });



/*
        switch(i){ //Database 에 저장된 값을 읽어들여, 시작지점을 결정함
            case 0:
                Intent i0 = new Intent(MainActivity.this, Menu_Tutorial.class);
                //Intent i0 = new Intent(MainActivity.this, Tutorial.class);
                startActivityForResult(i0,CODE);
                startService(new Intent(this, Menu_main_service.class)); //메뉴 음성 출력 서비스
                //startService(new Intent(this, Tutorial_service.class)); // 사용설명 서비스
                finish();
                //WHclass.db=1;
                break;
            case 1:
                Intent i1 = new Intent(MainActivity.this, Tutorial_tutorial.class);
                startActivityForResult(i1, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
            case 2:
                Intent i2 = new Intent(MainActivity.this, Tutorial_basic_practice.class);
                startActivityForResult(i2, 0);
                finish();
                break;
            case 3:
                Intent i3 = new Intent(MainActivity.this, Tutorial_master_practice.class);
                WHclass.basicprogress[0] = 1;
                startActivityForResult(i3, 0);
                finish();
                break;
            case 4:
                Intent i4 = new Intent(MainActivity.this, Tutorial_quiz.class);
                startActivityForResult(i4, 0);
                WHclass.mainmenuprogress = true;
                finish();
                break;
            case 5:
                Intent i5 = new Intent(MainActivity.this, Tutorial_dot_lecture.class);
                startActivityForResult(i5, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
            case 6:
                Intent i6 = new Intent(MainActivity.this, Tutorial_dot_practice.class);
                WHclass.sel = 9;
                startActivityForResult(i6, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
            case 7:
                Intent i7 = new Intent(MainActivity.this, Menu_Tutorial.class);
                startActivityForResult(i7, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
        }
*/
        //Intent intent = new Intent(MainActivity.this, Menu_Tutorial.class); // 대 메뉴 사용설명서 화면
        //Intent intent = new Intent(MainActivity.this, Tutorial.class); //여자 스피커 사용설명 화면
        //startActivityForResult(intent,CODE);
        //startService(new Intent(this, Tutorial_service.class)); // 사용설명 서비스
        //startService(new Intent(this, Menu_main_service.class)); //메뉴 음성 출력 서비스
      //  finish();

        versionimage = (ImageView) findViewById(R.id.imageView37);
        versionimage.setMaxHeight((int)WHclass.height);
        versionimage.setMaxWidth((int)WHclass.width);
        versionimage.setBackgroundResource(R.drawable.versionani);
        versionani = (AnimationDrawable) versionimage.getBackground();

    }

    @Override
    public void onRestart(){
        super.onRestart();
        Ani_reset();
    }

    @Override
    public void onPause(){
        super.onPause();
        Ani_memory_clear();
    }

    public void Ani_memory_clear(){
        findViewById(R.id.imageView37).setBackground(null);
        versionimage=null;
        versionani=null;
        System.gc();
    }

    public void Ani_reset(){
        versionimage = (ImageView) findViewById(R.id.imageView37);
        versionimage.setMaxHeight((int)WHclass.height);
        versionimage.setMaxWidth((int)WHclass.width);
        versionimage.setBackgroundResource(R.drawable.versionani);
        versionani = (AnimationDrawable) versionimage.getBackground();
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event){ //토크백 활성화 되었을 시 호출되는 함수
        Blind_person = super.dispatchPopulateAccessibilityEvent(event);
        return Blind_person;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(test==false) {
            if (Blind_person == true) { //시각장애인 전용버전일경우
                if (one_finger == false) { // hover이벤트가 발생되지 않았을 경우
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            oldx = (int)event.getX();  // 두번째 손가락이 터지된 지점의 x좌표값 저장
                            oldy = (int) event.getY();  // 두번째 손가락이 터지된 지점의 y좌표값 저장
                            if(test==false) {
                                type = INIT;
                                Version_check_service.menu_page=Menu_info.version_restart;
                                startService(new Intent(MainActivity.this, Version_check_service.class));
                                Timer_Stop();

                            }

                            break;
                        case MotionEvent.ACTION_UP:  // 두번째 손가락을 떼었을 경우
                            newx = (int)event.getX();  // 두번째 손가락이 떨어진 지점의 x좌표값 저장
                            newy = (int) event.getY();  // 두번째 손가락이 떨어진 지점의 y좌표값 저장
                            if (oldy - newy > WHclass.Drag_space) {  //손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                                onBackPressed();
                            }
                            break;
                    }
                } else if (one_finger == true) { //hover이벤트가 발생됬을 경우
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            oldx = (int)event.getX();  // 두번째 손가락이 터지된 지점의 x좌표값 저장
                            oldy = (int) event.getY();  // 두번째 손가락이 터지된 지점의 y좌표값 저장
                            if(test==false) {
                                type = INIT;
                                Version_check_service.menu_page=Menu_info.version_onefinger;
                                startService(new Intent(MainActivity.this, Version_check_service.class));
                                Timer_Stop();
                            }
                            break;
                        case MotionEvent.ACTION_UP:  // 두번째 손가락을 떼었을 경우
                            newx = (int)event.getX();  // 두번째 손가락이 떨어진 지점의 x좌표값 저장
                            newy = (int) event.getY();  // 두번째 손가락이 떨어진 지점의 y좌표값 저장
                            if (oldy - newy> WHclass.Drag_space) { //손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                                onBackPressed();
                            }
                            break;
                    }
                }
            } else if (Blind_person == false) { //일반사용자 버전일 경우
                switch(event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        versionani.start();
                        type = NORMAL;
                        Version_check_service.menu_page=Menu_info.version_start;
                        startService(new Intent(MainActivity.this, Version_check_service.class));
                        Timer_Reset();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        type = NORMAL;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (test == false) {
                            Version_check_service.menu_page=Menu_info.version_reset;
                            startService(new Intent(MainActivity.this, Version_check_service.class));
                            type = INIT;
                            Timer_Stop();

                            if(versionani.isRunning()){
                                versionani.stop();
                                versionani.start();
                                versionani.stop();
                            }

                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
                        newx = (int)event.getX();  // 두번째 손가락이 떨어진 지점의 x좌표값 저장
                        newy = (int)event.getY();  // 두번째 손가락이 떨어진 지점의 y좌표값 저장
                        if (oldy - newy > WHclass.Drag_space) {  //손가락 2개를 이용하여 하단에서 상단으로 드래그할 경우 현재 메뉴를 종료
                            onBackPressed();

                        }

                            break;
                    case MotionEvent.ACTION_POINTER_DOWN:  //두번째 손가락이 화면에 터치 될 경우
                        oldx = (int)event.getX();  // 두번째 손가락이 터지된 지점의 x좌표값 저장
                        oldy = (int)event.getY();  // 두번째 손가락이 터지된 지점의 y좌표값 저장
                        break;
                }
            }
        }


        return true;
    }


    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        timer.schedule(second,0,300); //0.3초의 딜레이시간
    }

    public void update(){ //일정시간마다 타이머 함수에 의해 불려짐
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                time_check++; //시간 카운트 변수
                if(time_check<=10) { //3초동안 총 10번의 확인
                    switch (type) {
                        case 1: //시각장애인버전일경우
                            hover_count++; //시각장애인 카운트 변수 증가
                            normal_count=0; //일반사용자 버전 카운트 변수 초기화
                            break;
                        case 2: //일반사용자버전일경우
                            normal_count++; //일반사용자 카운트 변수 증가
                            hover_count=0; //시각장애인 버전 카운트 변수 초기화
                            break;
                    }
                }
                else{
                    if(time_check>=25){ //모든음성이 출력되고 나면 메뉴 접속
                        if(test==true) {
                            switch(type){
                                case 1 :
                                    Intent i1 = new Intent(MainActivity.this, Talk_Menu_tutorial.class);
                                    startActivityForResult(i1, CODE);
                                    overridePendingTransition(R.anim.fade, R.anim.hold);
                                    finish();
                                    Timer_Stop();
                                    WHclass.Braiile_type=1;

                                    break;
                                case 2:
                                    //Intent i2 = new Intent(MainActivity.this, Tutorial.class);
                                    Intent i2 = new Intent(MainActivity.this, Menu_Tutorial.class);
                                    startActivityForResult(i2, CODE);
                                    overridePendingTransition(R.anim.fade, R.anim.hold);
                                    WHclass.Braiile_type=2;
                                    finish();
                                    Timer_Stop();
                                    break;
                            }

                        }
                    }
                    else if(hover_count>=9) { //10번의 체크동안 모두 확인되었을 경우
                        Version_check_service.menu_page=Menu_info.version_blind_person;
                        startService(new Intent(MainActivity.this, Version_check_service.class));
                        type=HOVER;
                        hover_count=0;
                        normal_count=0;
                        test = true;
                    }
                    else if(normal_count>=9) { //10번의 체크동안 모두 확인되었을 경우
                        Version_check_service.menu_page=Menu_info.version_normal;
                        startService(new Intent(MainActivity.this, Version_check_service.class));
                        type=NORMAL;
                        hover_count=0;
                        normal_count=0;
                        test = true;
                    }
                    one_finger = false;
                }
            }
        };
        handler.post(updater);
    }

    public void Timer_Reset(){
        if(timer != null){
            timer.cancel();
            timer= null;
            timer = new Timer();
            Timer_Start();
        }
        else if(timer==null){
            timer = new Timer();
            Timer_Start();
        }
    }

    public void Timer_Stop(){
        if(timer != null){
            timer.cancel();
            timer= null;
        }
        time_check=0;
    }
    //뉴톤 라이브러리를 해제
    public void onDestroy() {
        super.onDestroy();
        TextToSpeechManager.getInstance().finalizeLibrary();
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }
    @Override
    public void onBackPressed() { //종료키를 눌렀을 경우
        Version_check_service.finish=true;
        startService(new Intent(this, Version_check_service.class));
        Timer_Stop();
        test=true;
        finish();

    }
}

