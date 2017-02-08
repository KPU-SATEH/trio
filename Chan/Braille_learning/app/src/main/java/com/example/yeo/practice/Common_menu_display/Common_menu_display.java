package com.example.yeo.practice.Common_menu_display;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;

/**
 * Created by chanh on 2017-02-08.
 */
/*
    DISPLAY=0; //메뉴화면 출력 정보 저장 변수

    DISPLAY_TUTORIAL =0 ; //사용설명서

    DISPLAY_BASIC=1; //기초과정
    DISPLAY_INITIAL=11; //초성연습
    DISPLAY_VOWEL=12; //모음연습
    DISPLAY_FINAL=13; //종성연습
    DISPLAY_NUMBER=14; //숫자연습
    DISPLAY_ALPHABET=15; //알파벳연습
    DISPLAY_SENTENCE=16; //문장부호연습
    DISPLAY_ABBREVIATION=17; //약자 및 약어 연습

    DISPLAY_MASTER=2; //숙련과정
    DISPLAY_LETTER=21; //글자연습
    DISPLAY_WORD=22; //단어연습

    DISPLAY_TRANS=3; //점자번역

    DISPLAY_QUIZ=4; //퀴즈
    DISPLAY_QUIZ_INIT=41; //초성퀴즈
    DISPLAY_QUIZ_VOWEL=42; //모음퀴즈
    DISPLAY_QUIZ_FINAL=43; //종성퀴즈
    DISPLAY_QUIZ_NUMBER=44; //숫자퀴즈
    DISPLAY_QUIZ_ALPHABET=45; //알파벳퀴즈
    DISPLAY_QUIZ_SENTENCE=46; //문장부호퀴즈
    DISPLAY_QUIZ_ABBREVIATION=47; //약자 및 약어 퀴즈
    DISPLAY_QUIZ_LETTER=48; //글자 퀴즈
    DISPLAY_QUIZ_WORD=49; //단어퀴즈

    DISPLAY_MYNOTE=5; //나만의 단어장
    DISPLAY_MYNOTE_BASIC=51; //기초단어장
    DISPLAY_MYNOTE_MASTER=52; //숙련단어장

    DISPLAY_COMUNICATION=6; //선생님과의 대화
    DISPLAY_COMUNICATION_TEAHCER=61; //선생님모드
    DISPLAY_COMUNICATION_STUDENT=62; //학생모드
*/

public class Common_menu_display extends View {
        /*
        메뉴화면을 출력해주는 클래스
        */

    private Bitmap image;

    public float width= WHclass.width; //가로
    public int finger_x[] = new int[3];
    public int finger_y[] = new int[3];
    public float finger_circle=width*(float)0.02; // 동그라미 크기 메크로


    public void Finger_init(){ //화면 초기화 함수. 화면이 이동될 때 점자를 다시 그려줌.
        for(int i=0 ; i<3 ; i++){
            finger_x[i] = -100;
            finger_y[i] = -100;
        }
    }



    public Common_menu_display(Context context) {
        super(context);



        switch(Menu_info.DISPLAY){
            case 0:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial);
                break;
            case 1:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.basic_practice);
                break;
            case 11:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.first);
                break;
            case 12:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.vowel);
                break;
            case 13:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.last);
                break;
            case 14:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.number);
                break;
            case 15:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.alphabet);
                break;
            case 16:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.sentence);
                break;
            case 17:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.promise);
                break;
            case 2:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.master_practice);
                break;
            case 21:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.oneword);
                break;
            case 22:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.word);
                break;
            case 3:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.translation);
                break;
            case 4:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.quiz);
                break;
            case 41:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.init_quiz);
                break;
            case 42:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.vowel_quiz);
                break;
            case 43:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.final_quiz);
                break;
            case 44:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.number_quiz);
                break;
            case 45:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.alphabet_quiz);
                break;
            case 46:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.sentence_quiz);
                break;
            case 47:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.promise_quiz);
                break;
            case 48:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.letter_quiz);
                break;
            case 49:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.word_quiz);
                break;
            case 5:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.mynote);
                break;
            case 51:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.mynote_basic);
                break;
            case 52:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.mynote_master);
                break;
            case 6:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.comunication);
                break;
            case 61:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.teacher);
                break;
            case 62:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.student);
                break;
        }


        Finger_init();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int width = (int)WHclass.width;
        int height = (int)WHclass.height;
        Rect dst = new Rect(0, 0, height, width);
        canvas.drawBitmap(image, null, dst, null);


        Paint finger = new Paint();
        finger.setARGB(180,255,00,00);
        finger.setAntiAlias(true);
        for(int i=0 ; i<3 ; i++){
            canvas.drawCircle(finger_x[i],finger_y[i],finger_circle,finger);
        }
    }

    public void finger_set(int x1, int y1, int x2, int y2, int x3, int y3){
        finger_x[0]=x1;
        finger_y[0]=y1;
        finger_x[1]=x2;
        finger_y[1]=y2;
        finger_x[2]=y3;
        finger_y[2]=y3;
        invalidate();
    }

}
