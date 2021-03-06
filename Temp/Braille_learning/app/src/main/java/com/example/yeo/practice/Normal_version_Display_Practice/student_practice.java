package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_letter;
import com.example.yeo.practice.Common_braille_data.dot_student_data;
import com.example.yeo.practice.Common_braille_data.dot_word;
import com.example.yeo.practice.Common_master_practice_sound.Letter_service;
import com.example.yeo.practice.Common_master_practice_sound.Word_service;
import com.example.yeo.practice.Common_mynote_database.Mynote_service;
import com.example.yeo.practice.Common_sound.Braille_Text_To_Speech;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.Common_sound.slied;
import com.example.yeo.practice.Coomon_communication_sound.Communication_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.WHclass;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;
import net.daum.mf.speech.api.impl.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 여명 on 2017-02-01.
 */

public class student_practice extends FragmentActivity implements SpeechRecognizeListener{
    student_display m;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag;
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0;
    boolean click = true;
    boolean next = false; // 다음문제로 이동하기 위한 변수
    com.example.yeo.practice.Common_braille_data.dot_student_data dot_student_data;

    private TimerTask second; //타이머
    private SpeechRecognizerClient client;
    String result;
    ArrayList<String> temp;
    private final Handler handler = new Handler();
    Timer timer =null;
    int time_check=0;

    String array[] = new String[3]; //데이터베이스에 행렬에 대한 정보를 담기 위해 행렬정보를 담는 배열 변수

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

        dot_student_data = new dot_student_data();
        decorView.setSystemUiVisibility( uiOption );

        MainActivity.Braille_TTS.TTS_Play("선생님께서 몇번방에 계신지 말씀해주세요. 삑");
        timer = new Timer();
        SpeechRecognizerManager.getInstance().initializeLibrary(this);
        Timer_Start();

        m = new student_display(this);
        m.setBackgroundColor(Color.rgb(22, 26, 44));
        setContentView(m);
        m.invalidate();
    }
    public void Timer_Stop(){
        if(timer != null){
            timer.cancel();
            timer= null;
        }
    }
    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                if(Braille_Text_To_Speech.tts_check == false) {
                    time_check();
                    Timer_Stop();
                }
            }
        };
        timer.schedule(second,0,1000); //1초의 딜레이시간
    }
    public void time_check(){
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                STT_start();
            }
        };
        handler.post(updater);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        if (m.next == false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP://마지막 손가락을 땠을 때 화면잠금을 품
                    if (click == false)
                        click = true;
                    break;
                case MotionEvent.ACTION_DOWN: // 손가락 1개를 이용하여 터치가 발생하였을 때
                    m.x = (int) event.getX();//x좌표를 저장
                    m.y = (int) event.getY();//y좌표를 저장
                    if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2)))
                        break;
                    else
                        Touch_event();
                    m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                    break;
                case MotionEvent.ACTION_MOVE:// 화면을 터치한 상태로 움직일때 발생되는 이벤트
                    m.x = (int) event.getX();// 현재 터치한 지점의 x좌표를 저장
                    m.y = (int) event.getY();// 현재 터치한 지점의 y좌표를 저장
                    if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2)))
                        break;
                    if (click == true)
                        Touch_event();
                    m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                    break;

                case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 화면에서 떼었을 경우
                    click = true;
                    newdrag = (int) event.getX(); // 두번째 손가락이 화면에서 떨어진 지점의 x 좌표 저장
                    y2drag = (int) event.getY();// 두번째 손가락이 화면에서 떨어진 지점의 y 좌표 저장
                    int pointer_count = event.getPointerCount();
                    if(pointer_count==3) {
                        update();
                    }

                    if (y2drag - y1drag > WHclass.Drag_space) {//손가락 2개를 이용하여 하단으로 드래그 하는경우
                        m.check = true;
                        m.invalidate();
                        MainActivity.Braille_TTS.TTS_Play(m.Grade_speak());
                    } else if (y1drag - y2drag > WHclass.Drag_space) {// 손가락 2개를 이용하여 상단으로 드래그하는 경우 종료
                        dot_student_data.delete_data();
                        onBackPressed();
                    }else if (olddrag - newdrag > WHclass.Drag_space) {//손가락 2개를 이용하여 오른쪽에서 왼쪽으로 드래그 하였을 경우
                        m.check = false;
                        m.getData("http://192.168.0.124:10080/import.php", result);
                        slied.slied = Menu_info.next;
                        startService(new Intent(this, slied.class));
                        m.page++;
                        if(m.page>dot_student_data.dot_count-1)
                            m.page--;
                        if (MainActivity.communication_braille_db.communication_db_manager.My_Note_page > 0)
                            MainActivity.communication_braille_db.communication_db_manager.My_Note_page--;
                        dot_student_data.delete_data();
                    } else if (newdrag - olddrag > WHclass.Drag_space) { //손가락 2개를 이용하여 왼쪽에서 오른쪽으로 드래그 하였을 경우
                        m.check = false;
                        m.getData("http://192.168.0.124:10080/import.php", result);
                        slied.slied = Menu_info.pre;
                        startService(new Intent(this, slied.class));
                        m.page--;
                        if(m.page<0)
                            m.page=0;
                        if (MainActivity.communication_braille_db.communication_db_manager.My_Note_page > 0)
                            MainActivity.communication_braille_db.communication_db_manager.My_Note_page--;
                        dot_student_data.delete_data();
                    }
                case MotionEvent.ACTION_POINTER_DOWN: //두 번째 손가락을 터치하였을 때
                    click = false;// 제스처 기능을 위해 손가락 1개를 인지하는 화면을 잠금
                    olddrag = (int) event.getX();// 두번쨰 손가락이 터치한 지점의 x좌표 저장
                    y1drag = (int) event.getY();// 두번째 손가락이 터치한 지점의 y좌표 저장
                    break;
            }
        }
        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        // API를 더이상 사용하지 않을 때 finalizeLibrary()를 호출한다.
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }

    public void STT_start(){
        String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;
        // 음성인식 버튼 listener

        if(PermissionUtils.checkAudioRecordPermission(this)) {
            SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
                    setApiKey(WHclass.APIKEY).
                    setServiceType(serviceType);
            client = builder.build();
            client.setSpeechRecognizeListener(this);
            client.startRecording(false);
        }

    }

    @Override
    public void onReady() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onBeginningOfSpeech() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onEndOfSpeech() {
        //TODO implement interface DaumSpeechRecognizeListener method

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        //TODO implement interface DaumSpeechRecognizeListener method
        Log.e("SpeechSampleActivity", "onError");
        client = null;
    }

    @Override
    public void onPartialResult(String text) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onResults(Bundle results) {
//        final StringBuilder builder = new StringBuilder();
        Log.i("SpeechSampleActivity", "onResults");
        temp = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);

        if(temp.size()!=0) {
            client.stopRecording();
            getText();
        }
        client = null;
    }

    public void getText(){
        for(int i=0; i<temp.size(); i++) {
            char str;
            str = temp.get(i).charAt(0);
            if(str >= '0' && str <= '9') {
                result = temp.get(i);
                break;
            }
        }
        temp.clear();
        m.getData("http://192.168.0.124:10080/import.php", result);
    }

    @Override
    public void onAudioLevel(float v) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onFinished() {
        Log.i("SpeechSampleActivity", "onFinished");
    }
    public void touch_init(int coordinate_temp){ //문지르기 기능을 위한 컨트롤 변수 초기화 함수
        int coordinate_copy;
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;
        result6=0;

        coordinate_copy = coordinate_temp;

        if(coordinate_temp<7){
        }
        else if(coordinate_temp<13){
            coordinate_copy=coordinate_copy-6;
        }
        else if(coordinate_temp<19){
            coordinate_copy=coordinate_copy-12;
        }
        else if(coordinate_temp<25){
            coordinate_copy=coordinate_copy-18;
        }
        else if(coordinate_temp<31){
            coordinate_copy=coordinate_copy-24;
        }
        else if(coordinate_temp<37){
            coordinate_copy=coordinate_copy-30;
        }
        else if(coordinate_temp<43){
            coordinate_copy=coordinate_copy-36;
        }

        switch(coordinate_copy){
            case 1:
                result1=1;
                break;
            case 2:
                result2=1;
                break;
            case 3:
                result3=1;
                break;
            case 4:
                result4=1;
                break;
            case 5:
                result5=1;
                break;
            case 6:
                result6=1;
                break;
        }
    }

    public void Touch_event(){
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
        } //첫번째 칸의 1번 점자
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
        } //첫번째 칸의 2번 점자

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
        } //첫번째 칸의 3번 점자

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
        } //첫번째 칸의 4번 점자
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
        } //첫번째 칸의 5번 점자
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
        } //첫번째 칸의 6번 점자

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
                touch_init(7);
            }
        } //두번째 칸의 1번 점자
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
                touch_init(8);
            }
        } //두번째 칸의 2번 점자
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
                touch_init(9);
            }
        } //두번째 칸의 3번 점자
        else if (m.x < m.notarget7_width[0][3] + m.bigcircle && m.x > m.notarget7_width[0][3] - m.bigcircle && m.y < m.notarget7_height[0][3] + m.bigcircle && m.y > m.notarget7_height[0][3] - m.bigcircle) {
            WHclass.number = 4;
            if (result4 == 0) {
                if (m.x < m.target7_width[0][3] + m.bigcircle && m.x > m.target7_width[0][3] - m.bigcircle && m.y < m.target7_height[0][3] + m.bigcircle && m.y > m.target7_height[0][3] - m.bigcircle) {
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Strong_vibe);

                } else {
                    WHclass.target = false;
                    startService(new Intent(this, Number.class));

                }
                touch_init(10);
            }
        } //두번째 칸의 4번 점자
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
                touch_init(11);
            }
        } //두번째 칸의 5번 점자
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
                touch_init(12);
            }
        } //두번째 칸의 6번 점자
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
                touch_init(13);
            }
        } //세번째 칸의 1번 점자
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
                touch_init(14);
            }
        } //세번째 칸의 2번 점자
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
                touch_init(15);
            }
        } //세번째 칸의 3번 점자
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
                touch_init(16);
            }
        } //세번째 칸의 4번 점자
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
                touch_init(17);
            }
        } //세번째 칸의 5번 점자
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
                touch_init(18);
            }
        } //세번째 칸의 6번 점자
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
                touch_init(19);
            }
        } //네번째 칸의 1번 점자
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
                touch_init(20);
            } //네번째 칸의 2번 점자
        } else if (m.x < m.notarget7_width[2][6] + m.bigcircle && m.x > m.notarget7_width[2][6] - m.bigcircle && m.y < m.notarget7_height[2][6] + m.bigcircle && m.y > m.notarget7_height[2][6] - m.bigcircle) {
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
                touch_init(21);
            }
        } //네번째 칸의 3번 점자
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
                touch_init(22);
            }
        } //네번째 칸의 4번 점자
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
                touch_init(23);
            }
        } //네번째 칸의 5번 점자
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
                touch_init(24);
            }
        } //네번째 칸의 6번 점자
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
                touch_init(25);
            }
        } //다섯번째 칸의 1번 점자
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
                touch_init(26);
            }
        } //다섯번째 칸의 2번 점자
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
                touch_init(27);
            }
        } //다섯번째 칸의 3번 점자
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
                touch_init(28);
            }
        } //다섯번째 칸의 4번 점자
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
                touch_init(29);
            }
        } //다섯번째 칸의 5번 점자
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
                touch_init(30);
            }
        } //다섯번째 칸의 6번 점자
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
                touch_init(31);
            }
        } //여섯번째 칸의 1번 점자
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
                touch_init(32);
            }
        } //여섯번째 칸의 2번 점자
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
                touch_init(33);
            }
        } //여섯번째 칸의 3번 점자
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
                touch_init(34);
            }
        } //여섯번째 칸의 4번 점자
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
                touch_init(35);
            }
        } //여섯번째 칸의 5번 점자
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
                touch_init(36);
            }
        } //여섯번째 칸의 6번 점자
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
                touch_init(37);
            }
        } //일곱번째 칸의 1번 점자
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
                touch_init(38);
            }
        } //일곱번째 칸의 2번 점자
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
                touch_init(39);
            }
        } //일곱번째 칸의 3번 점자
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
                touch_init(40);
            }
        } //일곱번째 칸의 4번 점자
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
                touch_init(41);
            }
        } //일곱번째 칸의 5번 점자
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
                touch_init(42);
            }
        } //일곱번째 칸의 6번 점자
        else if (m.y > m.height1 - (m.bigcircle * 2) && m.y < m.height1 - m.bigcircle) {
            WHclass.number = 7;
            WHclass.target = true;
            startService(new Intent(this, Number.class));
            m.vibrator.vibrate(WHclass.Weak_vibe);
            touch_init(0);
        } else { //그 외 지점을 터치하였을 경우 문지르기 기능을 위한 컨트롤 변수 초기화
            touch_init(0);
            WHclass.number = 0;
        }
        switch (m.dot_count) {
            case 7:// 점자의 칸수가 일곱 칸일때 구분선 및 경고음 발생 영역 지정
                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width5 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width7 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width9 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width10 + m.bigcircle && m.x < m.width11 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width12 + m.bigcircle && m.x < m.width13 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width14 + m.bigcircle && m.x < m.width14 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
        }
    }
    public void update(){ //1초동안 화면에 연속으로 2번의 터치가 발생됬을 경우 데이터베이스로 현재 단어정보를 전송함
        String result ="";
        array[0]="";
        array[1]="";
        array[2]="";

        for (int i = 0; i < 3; i++) {
            for(int j=0; j<dot_student_data.count_Array.get(m.page) ; j++){
                array[i] = array[i]+Integer.toString(dot_student_data.student_Array.get(m.page)[i][j]); // 3개의 배열에 1행 2행 3행을 집어넣음
            }
        }
        result = MainActivity.communication_braille_db.insert(dot_student_data.count_Array.get(m.page)/2, m.dot_student_data.student_letter.get(m.page), array[0], array[1], array[2], Menu_info.MENU_INFO, m.page);  //데이터베이스에 입력하고, 성공문자를 돌려받음
        if(result.equals("성공")){
            Mynote_service.menu_page=2;
            startService(new Intent(this, Mynote_service.class));
        }
        else if(result.equals("실패")){
            Mynote_service.menu_page=3;
            startService(new Intent(this, Mynote_service.class));
        }


    }
    @Override
    public void onBackPressed() { //종료키를 눌렀을 경우 발생되는 이벤트
        m.page = 0;
        Communication_service.finish2 = true;
        dot_student_data.delete_data();
        startService(new Intent(this, Communication_service.class));
        m.check = false;
        finish();
    }
}