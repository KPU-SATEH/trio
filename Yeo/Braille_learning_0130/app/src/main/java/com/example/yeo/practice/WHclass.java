package com.example.yeo.practice;

import android.support.v7.app.ActionBarActivity;

/**
 * Created by Yeo on 2016-08-02.
 */
public class WHclass extends ActionBarActivity {

    /*
    모든 클래스에서 사용되는 변수들을 관리하는 클래스
     */
    static public String APIKEY = "bdc8f8fd6290a94adce3bce29b48f575"; //Daum TTS APIKEY

    static public float width; //스마트폰의 가로 해상도를 저장하는 변수
    static public float height; //스마트폰의 세로 해상도를 저장하는 변수
    static public int sel; //자신이 학습하려는 학습 종류를 저장하는 변수
    static public boolean target=false; //점자의 돌출 유무를 저장한 변수
    static public int number = 0; // 문지르기 기능을 위한 점자 번호 저장 변수
    static public int quiz_sel = 0; //자신이 풀어보려는 퀴즈의 종류를 저장하는 변수
    static public int tutorial_progress = 0; //사용설명서를 진행하기 위한 단계를 저장하는 변수
    static public int tutorial_previous = -1;
    static public boolean touchevent = true;
    static public boolean mainmenuprogress = false; //사용설명서 중 메뉴의 진행과정을 저장하는 변수
    static public boolean mainmenusuccess = false; //사용설명서 중 메뉴의 실습 성공 여부를 저장하는 변수
    static public boolean basicsuccess = false; //사용설명서 중 기초과정 메뉴 실습 성공 여부를 저장하는 변수
    static public int[] basicprogress = new int[]{0,0,0,0,0,0,0}; //사용 설명서 중 기초과정 메뉴 실습의 단계를 저장하는 변수
    static public int basicprogressresult = 0;
    static public int db;

    static public float Touch_space ; //터치 영역을 저장하는 메크로 = MainActivity.width * (float) 0.1
    static public float Drag_space ; //드래그 영역을 저장하는 메크로 = MainActivity.width * (float) 0.2
    static public int Strong_vibe = 250; //강한 진동의 세기를 저장하는 메크로
    static public int Weak_vibe = 50; //약한 진동의 세기를 저장하는 메크로

    static public int Braiile_type = 0; //Braille_type이 2이면 일반사용자 버전, 1이면 시각장애인버전
}
