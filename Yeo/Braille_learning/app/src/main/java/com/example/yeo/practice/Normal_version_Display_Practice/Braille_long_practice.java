package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeo.practice.Common_braille_data.dot_letter;
import com.example.yeo.practice.Common_braille_data.dot_word;
import com.example.yeo.practice.Common_braille_translation.Braille_translation;
import com.example.yeo.practice.Common_mynote_database.Mynote_service;
import com.example.yeo.practice.Common_trans_sound.Braille_trans_service;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.Sound_Manager;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Common_master_practice_sound.Letter_service;
import com.example.yeo.practice.Common_master_practice_sound.Word_service;
import com.example.yeo.practice.Common_sound.Number;
import com.example.yeo.practice.Common_sound.slied;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;
import net.daum.mf.speech.api.impl.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Braille_long_practice extends FragmentActivity implements SpeechRecognizeListener {
    Braille_long_display m;
    int newdrag, olddrag; //화면전환시 이용될 좌표 2개를 저장할 변수
    int y1drag, y2drag; // 손가락 1개를 터치하였을 때  y좌표와 손가락 2개를 터치하였을 때 y좌표를 저장하는 변수
    int result1 = 0,result2=0, result3=0, result4=0, result5=0, result6=0; // 화면을 문지르며 학습을 하기 위한 컨트롤 변수
    boolean click = true;

    public static dot_letter Dot_letter;
    public static dot_word Dot_word;

    boolean lock=false;

    String array[] = new String[3]; //데이터베이스에 행렬에 대한 정보를 담기 위해 행렬정보를 담는 배열 변수
    public static int reference2; //나만의 단어장에 들어온 단어의 주소
    public static int reference_index2; //나만의 단어장에 들어온 단어의 순서
    int previous_reference=0; //나만의 단어장에서 이전에 출력됬던 음성을 초기화 시키기 위한 변수
    static public boolean pre_reference2 = false; //이전에 음성이 출력되었는지를 체크하는 변수



    String hangel=""; //점자 번역을 위한 글자를 저장하는 변수
    private TimerTask second; // 버전확인을 위한 타이머
    Timer timer =null;
    String Translation_text="";
    public static boolean Trans_success=false;
    public static int Trans_dot_count=0;
    public static String Trans_dot_name="";
    public static int matrix[][];
    Braille_translation Translation;
    boolean Matrix_check=false;


    private SpeechRecognizerClient client;
    ArrayList<String> texts;
    boolean check= false;


    String text="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();

        SpeechRecognizerManager.getInstance().initializeLibrary(this);


        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );

        matrix = new int[3][14];
        Translation = new Braille_translation();


        switch(WHclass.sel){
            case 8: //글자연습
                Dot_letter = new dot_letter();
                break;
            case 9: //단어연습
                Dot_word = new dot_word(); // 단어 단위의 점자 클래스 선언
                break;
            case 10:
                reference2 = MainActivity.master_braille_db.master_db_manager.getReference(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                reference_index2 = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                switch(reference2){
                    case 8: //글자연습
                        startService(new Intent(this, Letter_service.class));
                        break;
                    case 9: //단어연습
                        startService(new Intent(this, Word_service.class));
                        break;
                    case 11:
                        String dbtext = "";
                        dbtext = MainActivity.master_braille_db.master_db_manager.getName(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                        matrix_init();
                        hangel = dbtext;
                        Translation.Translation(hangel);
                        Matrix_check=Matrix_copy();
                        if(Matrix_check==false) {
                            Trans_success=false;
                            Translation_text ="해당 단어는 7칸이 넘어서, 번역이 불가능합니다.";
                            MainActivity.Braille_TTS.TTS_Play(Translation_text);
                            matrix_init();
                        }
                        else {
                            Trans_success=true;
                            Translation_text = Translation.get_TTs_text();
                            Trans_dot_count=Translation.get_dotcount();
                            Trans_dot_name=Translation.get_dotname();
                            MainActivity.Braille_TTS.TTS_Play(Translation_text);
                        }
                        break;
                }
                break;
        }

        m = new Braille_long_display(this);
        m.setBackgroundColor(Color.rgb(22,26,44));
        setContentView(m);

        switch(WHclass.sel){
            case 8: //글자연습
                startService(new Intent(this, Letter_service.class));
                break;
            case 9: //단어연습
                startService(new Intent(this, Word_service.class));
                break;
            case 11:
                m.invalidate();
                break;
        }
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
        texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        if(texts.size()!=0) {
            client.stopRecording();
            getText();
        }


/*
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(check==true) {
                    getText();
                    check=false;
                }

            }
        });
*/
        client = null;
    }

    public void getText(){
        text = texts.get(0);
        check=true;
/*        for(int i=0; i<texts.size() ; i++) {
            text=text+texts.get(i)+" ";
        }
        */
        texts.clear();
    }

    @Override
    public void onAudioLevel(float v) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onFinished() {
        Log.i("SpeechSampleActivity", "onFinished");
    }

    public void update(){ //1초동안 화면에 연속으로 2번의 터치가 발생됬을 경우 데이터베이스로 현재 단어정보를 전송함
        String result ="";
        array[0]="";
        array[1]="";
        array[2]="";

        if(WHclass.sel==Menu_info.MENU_NOTE) {
            MainActivity.master_braille_db.delete(MainActivity.master_braille_db.master_db_manager.getId(MainActivity.master_braille_db.master_db_manager.My_Note_page));
            result = MainActivity.master_braille_db.getResult();
            if(MainActivity.master_braille_db.master_db_manager.size_count==0)
                onBackPressed();
            m.MyView3_init();
            m.invalidate();
            MyNote_Start_service();
            if(result.equals("삭제")){
                //  Mynote_service.menu_page=1;
                //  startService(new Intent(this, Mynote_service.class));
            }

        }
        else {
            for (int i = 0; i < 3; i++) {
                for(int j=0; j<m.dot_count*2 ; j++){
                    array[i] = array[i]+Integer.toString(m.text_7[i][j]); // 3개의 배열에 1행 2행 3행을 집어넣음
                }
            }
            result = MainActivity.master_braille_db.insert(m.dot_count, m.textname_7, array[0], array[1], array[2], Menu_info.MENU_INFO, m.page);  //데이터베이스에 입력하고, 성공문자를 돌려받음
            if(result.equals("성공")){
                Mynote_service.menu_page=2;
                startService(new Intent(this, Mynote_service.class));
            }
            else if(result.equals("실패")){
                Mynote_service.menu_page=3;
                startService(new Intent(this, Mynote_service.class));
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 화면에 터치가 발생했을 때 호출되는 콜백 메서드
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP: //마지막 손가락을 땠을 때 화면잠금을 품
                if (click == false) {
                    click = true;
                }
                lock=false;
                break;
            case MotionEvent.ACTION_DOWN: // 손가락 1개를 이용하여 터치가 발생하였을 때
                startService(new Intent(this, Sound_Manager.class));
                m.x = (int) event.getX(); //x좌표를 저장
                m.y = (int) event.getY(); //y좌표를 저장

                if ((m.x <m.bigcircle*2) && (m.x>m.bigcircle*(-2))&&(m.y >m.bigcircle*(-2))&&(m.y <(m.bigcircle*2))) {
                    break;
                }
                else{
                    /*
                    현재 터치가 발생 된 좌표가 만약 돌출된 점자라면, 해당 점자번호를 남성의 음성으로 안내하면서 강한 진동을 발생시킴
                    현재 터치가 발생 된 좌표가 만약 비돌출된 점자라면, 해당 점자번호를 여성의 음성으로 안내함
                     */
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
                        if (result4 == 0) {
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
                    } //세번째 칸 1번 점자
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
                    } //세번째 칸 2번 점자
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
                    } //세번째 칸 3번 점자
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
                    } //세번째 칸 4번 점자
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
                    } //세번째 칸 5번 점자
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
                    } //세번째 칸 6번 점자
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
                            touch_init(6);
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
                    else { //그외의 공간을 터치하였을 때는 문지르기 기능을 위한 컨트롤 변수 초기화
                        touch_init(0);
                    }
                    switch(m.dot_count){
                        case 1: //점자의 칸 수가 한 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width2+(m.bigcircle*2)){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            break;
                        case 2://점자의 칸 수가 두 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                                WHclass.number=7;
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
                        case 3://점자의 칸 수가 세 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                                WHclass.number=7;
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
                        case 4://점자의 칸 수가 네 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                                WHclass.number=7;
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
                        case 5://점자의 칸 수가 다섯 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                                WHclass.number=7;
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
                        case 6://점자의 칸 수가 여섯 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle){
                                WHclass.number=7;
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
                        case 7://점자의 칸 수가 일곱 칸일 때의 구분선 및 외벽 경고음 가이드 영역을 설정함
                            if(m.x > m.width2+m.bigcircle && m.x<m.width3-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width4+m.bigcircle && m.x<m.width5-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width6+m.bigcircle && m.x<m.width7-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width8+m.bigcircle && m.x<m.width9-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width10+m.bigcircle && m.x<m.width11-m.bigcircle){
                                WHclass.number=7;
                                WHclass.target= true;
                                startService(new Intent(this, Number.class));
                                m.vibrator.vibrate(WHclass.Weak_vibe);
                                touch_init(0);
                            }
                            else if(m.x > m.width12+m.bigcircle && m.x<m.width13-m.bigcircle){
                                WHclass.number=7;
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
                m.invalidate(); // 화면을 다시 그려줘라 => onDraw() 호출해준다//// break;
                break;

            case MotionEvent.ACTION_MOVE : // 화면을 터치한 상태로 움직일때 발생되는 이벤트
                m.x = (int)event.getX(); // 현재 터치한 지점의 x좌표를 저장
                m.y = (int)event.getY(); // 현재 터치한 지점의 y좌표를 저장
                if ((m.x <m.bigcircle*2) && (m.x>m.bigcircle*(-2))&&(m.y >m.bigcircle*(-2))&&(m.y <(m.bigcircle*2))) {
                    break;
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
                break;

            case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 화면에서 떼었을 경우
                click = true;
                newdrag = (int)event.getX(); // 두번째 손가락이 화면에서 떨어진 지점의 x 좌표 저장
                y2drag = (int) event.getY();// 두번째 손가락이 화면에서 떨어진 지점의 y 좌표 저장
                int pointer_count = event.getPointerCount();
                if(pointer_count==3) {
                    update();
                    lock=true;
                }
                else if(lock==false) {
                    if (olddrag - newdrag > WHclass.Drag_space) { // 다음 화면의 점자 학습 진행
                        switch (WHclass.sel) {
                            case 8: //글자연습
                                slied.slied = Menu_info.next;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Letter_service.class));
                                m.MyView3_init();
                                m.invalidate();
                                break;
                            case 9: //단어 연습
                                slied.slied = Menu_info.next;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Word_service.class));
                                m.MyView3_init();
                                m.invalidate();
                                break;
                            case 10:
                                slied.slied = Menu_info.next;
                                startService(new Intent(this, slied.class));
                                MainActivity.master_braille_db.master_db_manager.My_Note_page++;
                                if (MainActivity.master_braille_db.master_db_manager.My_Note_page >= MainActivity.master_braille_db.master_db_manager.size_count) //가장 마지막 학습내용까지 진행됬다면
                                    onBackPressed(); //종료
                                else  //아직 학습이 진행중이면
                                    MyNote_Start_service();
                                m.MyView3_init();
                                m.invalidate();
                                break;
                        }

                    } else if (newdrag - olddrag > WHclass.Drag_space) { // 이전 화면의 점자 학습 진행
                        switch (WHclass.sel) {
                            case 8: //글자연습
                                slied.slied = Menu_info.pre;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Letter_service.class));
                                m.MyView3_init();
                                m.invalidate();
                                break;
                            case 9: //단어연습
                                slied.slied = Menu_info.pre;
                                startService(new Intent(this, slied.class));
                                startService(new Intent(this, Word_service.class));
                                m.MyView3_init();
                                m.invalidate();
                                break;
                            case 10:
                                slied.slied = Menu_info.pre;
                                startService(new Intent(this, slied.class));
                                if (MainActivity.master_braille_db.master_db_manager.My_Note_page > 0)
                                    MainActivity.master_braille_db.master_db_manager.My_Note_page--;
                                MyNote_Start_service(); //현재 화면의 음성 출력

                                break;
                        }

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
                            case 11:
                                slied.slied=0;
                                startService(new Intent(this, slied.class));
                                STT_start();
                                Timer_Reset();
                                break;
                        }
                    } else if (y1drag - y2drag > WHclass.Drag_space) { // 현재 점자 학습 종료
                        onBackPressed();
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //두 번째 손가락을 터치하였을 때
                click = false; // 제스처 기능을 위해 손가락 1개를 인지하는 화면을 잠금
                olddrag = (int)event.getX(); // 두번쨰 손가락이 터치한 지점의 x좌표 저장
                y1drag = (int) event.getY(); // 두번째 손가락이 터치한 지점의 y좌표 저장
                break;


        }
        return true;
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
            case 11:
                String dbtext = "";
                dbtext = MainActivity.master_braille_db.master_db_manager.getName(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                matrix_init();
                hangel = dbtext;
                Translation.Translation(hangel);
                Matrix_check=Matrix_copy();
                if(Matrix_check==false) {
                    Trans_success=false;
                    Translation_text ="해당 단어는 7칸이 넘어서, 번역이 불가능합니다.";
                    MainActivity.Braille_TTS.TTS_Play(Translation_text);
                    matrix_init();
                }
                else {
                    Trans_success=true;
                    Translation_text = Translation.get_TTs_text();
                    Trans_dot_count=Translation.get_dotcount();
                    Trans_dot_name=Translation.get_dotname();
                    MainActivity.Braille_TTS.TTS_Play(Translation_text);
                }
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


    public boolean Matrix_copy(){
        int Matrix_sum=42;
        int sum=0;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                matrix[i][j]= Translation.getMatrix()[i][j];
                if(matrix[i][j]==1)
                    sum+=1;
            }
        }
        if(sum==Matrix_sum)
            return false;
        else
            return true;
    }

    public void matrix_init(){                      //점자를 초기화 하는 함수
        Translation.matrix_init();
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                matrix[i][j]=0;
            }
        }
    }




    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                if(check==true)
                    STT_check();
            }
        };
        timer.schedule(second,0,100); //0.3초의 딜레이시간

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
    }

    public void STT_check(){
        check=false;
        Timer_Stop();
        matrix_init();
        hangel =text;
        Translation.Translation(hangel);
        Matrix_check=Matrix_copy();

        if(Matrix_check==false) {
            Trans_success=false;
            Translation_text ="해당 단어는 7칸이 넘어서, 번역이 불가능합니다.";
            MainActivity.Braille_TTS.TTS_Play(Translation_text);
            matrix_init();
        }
        else {
            Trans_success=true;
            Translation_text = Translation.get_TTs_text();
            Trans_dot_count=Translation.get_dotcount();
            Trans_dot_name=Translation.get_dotname();
            MainActivity.Braille_TTS.TTS_Play(Translation_text);
        }

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                m.MyView3_init();
                m.invalidate();
            }
        });

    }



    @Override
    public void onBackPressed() { // 뒤로가기 키를 눌렀을때 점자 학습을 위한 변수 초기화 및 종료
        m.page = 0;
        m.MyView3_init();

        switch(WHclass.sel){
            case 8:
                Letter_service.finish = true;
                startService(new Intent(this, Letter_service.class));
                break;
            case 9: //단어연습
                Word_service.finish = true;
                startService(new Intent(this, Word_service.class));
                break;
            case 10:
                MainActivity.master_braille_db.master_db_manager.My_Note_page=0;
                Mynote_service.finish = true;
                startService(new Intent(this, Mynote_service.class));
                break;
            case 11:
                Braille_trans_service.finish = true;
                startService(new Intent(this, Braille_trans_service.class));
                break;
        }
        finish();
    }
}
