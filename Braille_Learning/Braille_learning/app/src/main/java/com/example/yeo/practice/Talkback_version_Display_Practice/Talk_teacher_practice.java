package com.example.yeo.practice.Talkback_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.Coomon_communication_sound.Communication_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.WHclass;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;
import net.daum.mf.speech.api.impl.util.PermissionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 여명 on 2017-02-01.
 */

public class Talk_teacher_practice extends FragmentActivity implements SpeechRecognizeListener {
    int finger_x[] = new int[3];
    int finger_y[] = new int[3];

    Talk_teacher_display m;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag;
    int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0, result6 = 0;
    boolean click = true;

    String myJSON;
    private static final String ROOM = "room";

    JSONArray abc = null;
    String array1 = "";
    String array2 = "";
    String array3 = "";
    String room = "0";
    private TimerTask second; //타이머
    private final Handler handler = new Handler();
    Timer timer = null;

    private SpeechRecognizerClient client;
    ArrayList<String> temp;
    String result;

    int touch_check = 0;  // 시간 초기화를 위한 변수
    int coordinate = 0; //현재 점자 위치를 저장하는 변수

    boolean next = false; // 다음문제로 이동하기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        MainActivity.Braille_TTS.TTS_Play("점자를 입력하신 후 상단에서 하단으로 긁어 해당점자의 글자를 말씀해주세요.");

        decorView.setSystemUiVisibility(uiOption);
        m = new Talk_teacher_display(this);
        m.setBackgroundColor(Color.rgb(22, 26, 44));
        setContentView(m);

        m.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_HOVER_ENTER:
                        m.x = (int) event.getX();//x좌표를 저장
                        m.y = (int) event.getY();//y좌표를 저장
                        if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2)))
                            break;
                        else
                            Touch_event();
                        m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        m.x = (int) event.getX();// 현재 터치한 지점의 x좌표를 저장
                        m.y = (int) event.getY();// 현재 터치한 지점의 y좌표를 저장
                        if ((m.x < m.bigcircle * 2) && (m.x > m.bigcircle * (-2)) && (m.y > m.bigcircle * (-2)) && (m.y < (m.bigcircle * 2)))
                            break;
                     /*
                    터치한 지점의 좌표가 돌출된 점자일 경우 남성의 음성으로 점자번호를 안내하면서 강한진동이 발생
                    터치한 지점의 좌표가 비돌출된 점자일 경우 여성의 음성으로 점자번호를 안내함
                     */
                        Touch_event();
                        m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        Timer_Stop();
                        touch_init(0);
                        if (click == false)
                            click = true;

                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // API를 더이상 사용하지 않을 때 finalizeLibrary()를 호출한다.
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }

    public void STT_start() {
        String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;
        // 음성인식 버튼 listener

        if (PermissionUtils.checkAudioRecordPermission(this)) {
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
        result = temp.get(0);
        temp.clear();
        insert();
    }
    public void onAudioLevel(float v) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }


    @Override
    public void onFinished() {
        Log.i("SpeechSampleActivity", "onFinished");
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        if (m.next == false) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:  // 두번째 손가락을 화면에서 떼었을 경우
                    click = true;
                    newdrag = (int) event.getX(); // 두번째 손가락이 화면에서 떨어진 지점의 x 좌표 저장
                    y2drag = (int) event.getY();// 두번째 손가락이 화면에서 떨어진 지점의 y 좌표 저장
                    if (y2drag - y1drag > WHclass.Drag_space) { //데이터 전송
                        STT_start();
                    }

                    else if (y1drag - y2drag > WHclass.Drag_space) {// 손가락 2개를 이용하여 상단으로 드래그하는 경우 종료
                        onBackPressed();
                    }
                    else if(olddrag - newdrag > WHclass.Drag_space){
                        if(next==true) {
                            next = false;
                            finish();
                        }
                    }
                    m.invalidate();
                    break;
                case MotionEvent.ACTION_DOWN: //두 번째 손가락을 터치하였을 때
                    click = false;// 제스처 기능을 위해 손가락 1개를 인지하는 화면을 잠금
                    olddrag = (int) event.getX();// 두번쨰 손가락이 터치한 지점의 x좌표 저장
                    y1drag = (int) event.getY();// 두번째 손가락이 터치한 지점의 y좌표 저장
                    break;
            }
        }
        return true;
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
        Timer_Reset(coordinate_temp);
    }

    public void Timer_Stop(){
        if(timer != null){
            timer.cancel();
            timer= null;
        }
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;
        result6=0;
    }

    public void Timer_Reset(int coordinate_temp){
        coordinate = coordinate_temp;
        touch_check=0;
        if(coordinate==0)
            Timer_Stop();
        else {
            if (timer != null) {
                timer.cancel();
                timer = null;
                timer = new Timer();
                Timer_Start();
            } else if (timer == null) {
                timer = new Timer();
                Timer_Start();
            }
        }
    }

    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                Touch_check();
            }
        };

        timer.schedule(second,0,1000); //1초의 딜레이시간
    }

    public void Touch_check(){
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                touch_check++;
                if(touch_check>2){
                    touch_insert_check(coordinate);
                    touch_check=0;
                }
            }
        };
        handler.post(updater);
    }

    public void touch_insert_check(int coordinate_temp){
        if(next==false) {
            int coordinate_x = -1;
            int coordinate_y = -1;

            if (coordinate_temp == 1) {
                coordinate_x = 0;
                coordinate_y = 0;
            } else if (coordinate_temp == 2) {
                coordinate_x = 0;
                coordinate_y = 1;
            } else if (coordinate_temp == 3) {
                coordinate_x = 0;
                coordinate_y = 2;
            } else if (coordinate_temp == 4) {
                coordinate_x = 1;
                coordinate_y = 0;
            } else if (coordinate_temp == 5) {
                coordinate_x = 1;
                coordinate_y = 1;
            } else if (coordinate_temp == 6) {
                coordinate_x = 1;
                coordinate_y = 2;
            } else if (coordinate_temp == 7) {
                coordinate_x = 2;
                coordinate_y = 0;
            } else if (coordinate_temp == 8) {
                coordinate_x = 2;
                coordinate_y = 1;
            } else if (coordinate_temp == 9) {
                coordinate_x = 2;
                coordinate_y = 2;
            } else if (coordinate_temp == 10) {
                coordinate_x = 3;
                coordinate_y = 0;
            } else if (coordinate_temp == 11) {
                coordinate_x = 3;
                coordinate_y = 1;
            } else if (coordinate_temp == 12) {
                coordinate_x = 3;
                coordinate_y = 2;
            } else if (coordinate_temp == 13) {
                coordinate_x = 4;
                coordinate_y = 0;
            } else if (coordinate_temp == 14) {
                coordinate_x = 4;
                coordinate_y = 1;
            } else if (coordinate_temp == 15) {
                coordinate_x = 4;
                coordinate_y = 2;
            } else if (coordinate_temp == 16) {
                coordinate_x = 5;
                coordinate_y = 0;
            } else if (coordinate_temp == 17) {
                coordinate_x = 5;
                coordinate_y = 1;
            } else if (coordinate_temp == 18) {
                coordinate_x = 5;
                coordinate_y = 2;
            } else if (coordinate_temp == 19) {
                coordinate_x = 6;
                coordinate_y = 0;
            } else if (coordinate_temp == 20) {
                coordinate_x = 6;
                coordinate_y = 1;
            } else if (coordinate_temp == 21) {
                coordinate_x = 6;
                coordinate_y = 2;
            } else if (coordinate_temp == 22) {
                coordinate_x = 7;
                coordinate_y = 0;
            } else if (coordinate_temp == 23) {
                coordinate_x = 7;
                coordinate_y = 1;
            } else if (coordinate_temp == 24) {
                coordinate_x = 7;
                coordinate_y = 2;
            } else if (coordinate_temp == 25) {
                coordinate_x = 8;
                coordinate_y = 0;
            } else if (coordinate_temp == 26) {
                coordinate_x = 8;
                coordinate_y = 1;
            } else if (coordinate_temp == 27) {
                coordinate_x = 8;
                coordinate_y = 2;
            } else if (coordinate_temp == 28) {
                coordinate_x = 9;
                coordinate_y = 0;
            } else if (coordinate_temp == 29) {
                coordinate_x = 9;
                coordinate_y = 1;
            } else if (coordinate_temp == 30) {
                coordinate_x = 9;
                coordinate_y = 2;
            } else if (coordinate_temp == 31) {
                coordinate_x = 10;
                coordinate_y = 0;
            } else if (coordinate_temp == 32) {
                coordinate_x = 10;
                coordinate_y = 1;
            } else if (coordinate_temp == 33) {
                coordinate_x = 10;
                coordinate_y = 2;
            } else if (coordinate_temp == 34) {
                coordinate_x = 11;
                coordinate_y = 0;
            } else if (coordinate_temp == 35) {
                coordinate_x = 11;
                coordinate_y = 1;
            } else if (coordinate_temp == 36) {
                coordinate_x = 11;
                coordinate_y = 2;
            } else if (coordinate_temp == 37) {
                coordinate_x = 12;
                coordinate_y = 0;
            } else if (coordinate_temp == 38) {
                coordinate_x = 12;
                coordinate_y = 1;
            } else if (coordinate_temp == 39) {
                coordinate_x = 12;
                coordinate_y = 2;
            } else if (coordinate_temp == 40) {
                coordinate_x = 13;
                coordinate_y = 0;
            } else if (coordinate_temp == 41) {
                coordinate_x = 13;
                coordinate_y = 1;
            } else if (coordinate_temp == 42) {
                coordinate_x = 13;
                coordinate_y = 2;
            }

            if (coordinate_x != (-1) && coordinate_y != (-1)) {
                if (m.target7_width[coordinate_y][coordinate_x] == 0 && m.target7_height[coordinate_y][coordinate_x] == 0) {
                    m.target7_width[coordinate_y][coordinate_x] = m.width_7[coordinate_y][coordinate_x];
                    m.target7_height[coordinate_y][coordinate_x] = m.height_7[coordinate_y][coordinate_x];
                } else {
                    m.target7_width[coordinate_y][coordinate_x] = 0;
                    m.target7_height[coordinate_y][coordinate_x] = 0;
                }

                if (m.Braille_insert[coordinate_y][coordinate_x] == 0)
                    m.Braille_insert[coordinate_y][coordinate_x] = 1;
                else if (m.Braille_insert[coordinate_y][coordinate_x] == 1)
                    m.Braille_insert[coordinate_y][coordinate_x] = 0;
            }
            result1 = 0;
            result2 = 0;
            result3 = 0;
            result4 = 0;
            result5 = 0;
            result6 = 0;
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
            case 1:// 점자의 칸수가 한 칸일때 구분선 및 경고음 발생 영역 지정
                if (m.x > m.width2 + m.bigcircle && m.x < m.width2 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
            case 2:// 점자의 칸수가 두 칸일때 구분선 및 경고음 발생 영역 지정
                if (m.x > m.width2 + m.bigcircle && m.x < m.width3 - m.bigcircle) {
                    WHclass.number = 8;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                } else if (m.x > m.width4 + m.bigcircle && m.x < m.width4 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
            case 3:// 점자의 칸수가 세 칸일때 구분선 및 경고음 발생 영역 지정
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
                } else if (m.x > m.width6 + m.bigcircle && m.x < m.width6 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
            case 4:// 점자의 칸수가 네 칸일때 구분선 및 경고음 발생 영역 지정
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
                } else if (m.x > m.width8 + m.bigcircle && m.x < m.width8 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
            case 5:// 점자의 칸수가 다섯 칸일때 구분선 및 경고음 발생 영역 지정
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
                } else if (m.x > m.width10 + m.bigcircle && m.x < m.width10 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
            case 6:// 점자의 칸수가 여섯 칸일때 구분선 및 경고음 발생 영역 지정
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
                } else if (m.x > m.width12 + m.bigcircle && m.x < m.width12 + (m.bigcircle * 2)) {
                    WHclass.number = 7;
                    WHclass.target = true;
                    startService(new Intent(this, Number.class));
                    m.vibrator.vibrate(WHclass.Weak_vibe);
                    touch_init(0);
                }
                break;
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
    public void insert(){
        for (int j=0; j<14; j++){
            if(m.Braille_insert[0][j]==0){
                array1+="0";
            }
            else
                array1+="1";
        }
        for (int j=0; j<14; j++){
            if(m.Braille_insert[1][j]==0){
                array2+="0";
            }
            else
                array2+="1";
        }
        for (int j=0; j<14; j++){
            if(m.Braille_insert[2][j]==0){
                array3+="0";
            }
            else
                array3+="1";
        }
        insertToDatabase(array1, array2, array3, result, room);
        for(int k =0; k<3; k++){
            for(int p=0; p<14; p++){
                m.Braille_insert[k][p]=0;
            }
        }
        array1="";
        array2="";
        array3="";

    }
    protected void show(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            abc = jsonObj.getJSONArray("result");   //  mysql에서 불러온값을 저장.

            for(int i=0;i<abc.length();i++){
                JSONObject c = abc.getJSONObject(i);
                String str = c.getString("id");    // 저장코드들
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                String text=str+"번방에 입장하셨습니다. 학생들에게 방 번호를 알려주세요.";
                MainActivity.Braille_TTS.TTS_Play(text);
                room = str;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void insertToDatabase(final String array1, String array2, String array3, String character, String room){
        class InsertData extends AsyncTask<String, Void, String> {

            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                myJSON=result;
                show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String array1 = (String)params[0];
                    String array2 = (String)params[1];
                    String array3 = (String)params[2];
                    String letter = (String)params[3];
                    String room = (String)params[4];

                    String link="http://192.168.0.124:10080/insert.php";
                    String data  = URLEncoder.encode("array1", "UTF-8") + "=" + URLEncoder.encode(array1, "UTF-8");
                    data += "&" + URLEncoder.encode("array2", "UTF-8") + "=" + URLEncoder.encode(array2, "UTF-8");
                    data += "&" + URLEncoder.encode("array3", "UTF-8") + "=" + URLEncoder.encode(array3, "UTF-8");
                    data += "&" + URLEncoder.encode("letter", "UTF-8") + "=" + URLEncoder.encode(letter, "UTF-8");
                    data += "&" + URLEncoder.encode("room", "UTF-8") + "=" + URLEncoder.encode(room, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(array1,array2,array3,character,room);

    }
    @Override
    public void onBackPressed() { //종료키를 눌렀을 경우 발생되는 이벤트
        m.page = 0;
        Communication_service.finish1 = true;
        startService(new Intent(this, Communication_service.class));
        finish();
    }
}
