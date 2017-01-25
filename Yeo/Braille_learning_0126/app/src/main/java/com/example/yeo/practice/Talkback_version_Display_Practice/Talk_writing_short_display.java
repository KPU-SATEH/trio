package com.example.yeo.practice.Talkback_version_Display_Practice;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_quiz_abbreviation;
import com.example.yeo.practice.Common_braille_data.dot_quiz_alphabet;
import com.example.yeo.practice.Common_braille_data.dot_quiz_final;
import com.example.yeo.practice.Common_braille_data.dot_quiz_initial;
import com.example.yeo.practice.Common_braille_data.dot_quiz_number;
import com.example.yeo.practice.Common_braille_data.dot_quiz_sentence;
import com.example.yeo.practice.Common_braille_data.dot_quiz_vowel;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.WHclass;

import java.util.Random;

/**
 * Created by yoonc on 2016-07-25.
 */
class Talk_writing_short_display extends View {

    Random random;

    int max, min=0; // 랜덤변수 최대값과 최소값
    float width; //가로
    float height; //세로
    int x=0, y=0; // 점자를 터치할때 사용할 좌표를 저장할 변수
    Vibrator vibrator; //진동 변수
    float w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13,w14,w15,w16,w17,w18; //버튼 가로위치
    float h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18; //버튼 세로위치
    static float tw1,tw2,tw3,tw4,tw5,tw6,tw7,tw8,tw9,tw10,tw11,tw12,tw13,tw14,tw15,tw16,tw17,tw18; //타겟 가로위치
    static float th1,th2,th3,th4,th5,th6,th7,th8,th9,th10,th11,th12,th13,th14,th15,th16,th17,th18; // 타겟 세로위치
    static int page = 0 ;
    static boolean next = false;
    static boolean print=false;

    static dot_quiz_initial Dot_quiz_initial;
    static dot_quiz_vowel Dot_quiz_vowel;
    static dot_quiz_final Dot_quiz_final;
    static dot_quiz_number Dot_quiz_number;
    static dot_quiz_alphabet Dot_quiz_alphabet;
    static dot_quiz_sentence Dot_quiz_sentence;
    static dot_quiz_abbreviation Dot_quiz_abbreviation;
    int dot_count=0;


    int question = 0; // 문제 수를 지정할 변수



    float width7, width8, width9, width10, width11, width12, height1,height2,height3;
    float minicircle, bigcircle;


    int k = 0;



    static int text_3[][] = new int[3][6]; // 18개 점자 저장 변수
    static int Braille_insert[][] = new int [3][6] ;

    static String textname_3; // 18개 점자 이름 저장변수
    String type="";

    public void quiz_target_init(){
        w1=0;w2=0;w3=0;w4=0;w5=0;w6=0;w7=0;w8=0;w9=0;w10=0;w11=0;w12=0;w13=0;w14=0;w15=0;w16=0;w17=0;w18=0; //버튼 가로위치
        h1=0;h2=0;h3=0;h4=0;h5=0;h6=0;h7=0;h8=0;h9=0;h10=0;h11=0;h12=0;h13=0;h14=0;h15=0;h16=0;h17=0;h18=0; //버튼 세로위치
        tw1=0;tw2=0;tw3=0;tw4=0;tw5=0;tw6=0;tw7=0;tw8=0;tw9=0;tw10=0;tw11=0;tw12=0;tw13=0;tw14=0;tw15=0;tw16=0;tw17=0;tw18=0; //타겟 가로위치
        th1=0;th2=0;th3=0;th4=0;th5=0;th6=0;th7=0;th8=0;th9=0;th10=0;th11=0;th12=0;th13=0;th14=0;th15=0;th16=0;th17=0;th18=0; // 타겟 세로위치
    }

    public void quiz_view_init(){

        w1=0;w2=0;w3=0;w4=0;w5=0;w6=0;w7=0;w8=0;w9=0;w10=0;w11=0;w12=0;w13=0;w14=0;w15=0;w16=0;w17=0;w18=0; //버튼 가로위치
        h1=0;h2=0;h3=0;h4=0;h5=0;h6=0;h7=0;h8=0;h9=0;h10=0;h11=0;h12=0;h13=0;h14=0;h15=0;h16=0;h17=0;h18=0; //버튼 세로위치
        tw1=0;tw2=0;tw3=0;tw4=0;tw5=0;tw6=0;tw7=0;tw8=0;tw9=0;tw10=0;tw11=0;tw12=0;tw13=0;tw14=0;tw15=0;tw16=0;tw17=0;tw18=0; //타겟 가로위치
        th1=0;th2=0;th3=0;th4=0;th5=0;th6=0;th7=0;th8=0;th9=0;th10=0;th11=0;th12=0;th13=0;th14=0;th15=0;th16=0;th17=0;th18=0; // 타겟 세로위치
        print=false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                Braille_insert[i][j] = 0;
            }
        }


        /*
        초성퀴즈, 모음퀴즈, 종성퀴즈, 숫자퀴즈, 알파벳퀴즈, 문자부호퀴즈, 약자및 약어퀴즈, 글자퀴즈
         */


        switch(Menu_info.MENU_QUIZ_INFO) {
            case 0 :
                max = Dot_quiz_initial.Initialcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_initial.Initial_dot_count.get(page);
                if (dot_count == 1) k = 2; // 점자가 1칸 일 때
                else if (dot_count == 2) k = 4; //점자가 2칸일 때
                else if (dot_count == 3) k = 6; //점자가 3칸일 떄
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_initial.Initial_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_initial.Initial_name.get(page);
                type="초성 ";
                break;
            case 1 :
                max = Dot_quiz_vowel.vowelcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_vowel.vowel_dot_count.get(page);
                if (dot_count == 1) k = 2; // 점자가 1칸 일 때
                else if (dot_count == 2) k = 4; //점자가 2칸일 때
                else if (dot_count == 3) k = 6; //점자가 3칸일 떄
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_vowel.vowel_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_vowel.vowel_name.get(page);
                type="모음 ";
                break;
            case 2 :
                max = Dot_quiz_final.finalcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_final.final_dot_count.get(page);
                if (dot_count == 1) k = 2; // 점자가 1칸 일 때
                else if (dot_count == 2) k = 4; //점자가 2칸일 때
                else if (dot_count == 3) k = 6; //점자가 3칸일 떄
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_final.final_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_final.final_name.get(page);
                type="종성 ";
                break;
            case 3 :
                max = Dot_quiz_number.numbercount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_number.number_dot_count.get(page);
                if (dot_count == 1) k = 2; // 점자가 1칸 일 때
                else if (dot_count == 2) k = 4; //점자가 2칸일 때
                else if (dot_count == 3) k = 6; //점자가 3칸일 떄
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_number.number_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_number.number_name.get(page);
                type="숫자 ";
                break;
            case 4 :
                max = Dot_quiz_alphabet.alphabetcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_alphabet.alphabet_dot_count.get(page);
                if (dot_count == 1) k = 2; // 점자가 1칸 일 때
                else if (dot_count == 2) k = 4; //점자가 2칸일 때
                else if (dot_count == 3) k = 6; //점자가 3칸일 떄
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_alphabet.alphabet_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_alphabet.alphabet_name.get(page);
                type="알파벳 ";
                break;
            case 5 :
                max = Dot_quiz_sentence.sentence_count;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_sentence.sentence_dot_count.get(page);
                if (dot_count == 1) k = 2; // 점자가 1칸 일 때
                else if (dot_count == 2) k = 4; //점자가 2칸일 때
                else if (dot_count == 3) k = 6; //점자가 3칸일 떄
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_sentence.sentence_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_sentence.sentence_name.get(page);
                type="문장부호 ";
                break;
            case 6 :
                max = Dot_quiz_abbreviation.abbreviation_count;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_abbreviation.abbreviation_dot_count.get(page);
                k = dot_count*2;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < k; j++)
                        text_3[i][j] = Dot_quiz_abbreviation.abbreviation_Array.get(page)[i][j];
                }
                textname_3 = Dot_quiz_abbreviation.abbreviation_name.get(page);
                type="알파벳 ";
                break;
        }




        switch(question){
            case 0:
                String text="첫번째 문제 입니다. 점자를 입력하여 정답을 맞추어 보세요. "+type+textname_3+"! "+dot_count+"칸, ";
                MainActivity.Braille_TTS.TTS_Play(text);
                break;
            case 1:
                String text2="두번째 문제 입니다. 점자를 입력하여 정답을 맞추어 보세요. "+type+textname_3+"! "+dot_count+"칸, ";
                MainActivity.Braille_TTS.TTS_Play(text2);
                break;
            case 2:
                String text3="마지막 문제 입니다. 점자를 입력하여 정답을 맞추어 보세요. "+type+textname_3+"! "+dot_count+"칸, ";
                MainActivity.Braille_TTS.TTS_Play(text3);
                break;
        }

    }
    public Talk_writing_short_display(Context context) {
        super(context);
        Dot_quiz_initial = new dot_quiz_initial();
        Dot_quiz_vowel = new dot_quiz_vowel();
        Dot_quiz_final = new dot_quiz_final();
        Dot_quiz_number = new dot_quiz_number();
        Dot_quiz_alphabet = new dot_quiz_alphabet();
        Dot_quiz_sentence = new dot_quiz_sentence();
        Dot_quiz_abbreviation = new dot_quiz_abbreviation();

        quiz_view_init();

        width = WHclass.width; //MainActivity에서 불러온 가로비율을 저장
        height = WHclass.height; //MainActivity에서 불러온 세로비율을 저장

        minicircle = width*(float)0.02; //작은점자  크기 메크로
        bigcircle = width*(float)0.099; //큰 점자 크기 메크로



        width7 = width*(float)0.1; //점자가 18개일때
        width8 = width*(float)0.3;
        width9 = width*(float)0.6;
        width10 = width*(float)0.8;
        width11 = width*(float)1.1;
        width12 = width*(float)1.3;

        height1 = width*(float)0.5; //점자 세로 높이
        height2 = width*(float)0.7;
        height3 = width*(float)0.9;

        // 125 275    425 575    725  875

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(width*(float)0.21);
        paint.setAntiAlias(true);

        switch(dot_count) { //점자의 칸수에 따라 글자 위치를 설정함. 또한 음성출력을 통한 퀴즈메뉴를 진행하고 화면에 점자를 출력함
            case 1: //점자의 칸 수가 한 칸 일때
                canvas.drawText(textname_3, height * (float) 0.4, width * (float) 0.2, paint);


                if(Braille_insert[0][0]==0)
                    canvas.drawCircle(width7,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height1,bigcircle,paint);
                    tw1 = width7;
                    th1 = height1;
                }
                w1 = width7;
                h1 = height1;

                if(Braille_insert[1][0]==0)
                    canvas.drawCircle(width7,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height2,bigcircle,paint);
                    tw2 = width7;
                    th2 = height2;
                }
                w2 = width7;
                h2 = height2;

                if(Braille_insert[2][0]==0)
                    canvas.drawCircle(width7,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height3,bigcircle,paint);
                    tw3 = width7;
                    th3 = height3;
                }
                w3 = width7;
                h3 = height3;

                if(Braille_insert[0][1]==0)
                    canvas.drawCircle(width8,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height1,bigcircle,paint);
                    tw4 = width8;
                    th4 = height1;
                }
                w4 = width8;
                h4 = height1;

                if(Braille_insert[1][1]==0)
                    canvas.drawCircle(width8,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height2,bigcircle,paint);
                    tw5 = width8;
                    th5 = height2;
                }
                w5 = width8;
                h5 = height2;

                if(Braille_insert[2][1]==0)
                    canvas.drawCircle(width8,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height3,bigcircle,paint);
                    tw6 = width8;
                    th6 = height3;
                }
                w6 = width8;
                h6 = height3;
                break;




            case 2: //점자의 칸 수가 두 칸 일때
                canvas.drawText(textname_3, height * (float) 0.4, width * (float) 0.2, paint);
                if(Braille_insert[0][0]==0)
                    canvas.drawCircle(width7,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height1,bigcircle,paint);
                    tw1 = width7;
                    th1 = height1;
                }
                w1 = width7;
                h1 = height1;

                if(Braille_insert[1][0]==0)
                    canvas.drawCircle(width7,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height2,bigcircle,paint);
                    tw2 = width7;
                    th2 = height2;
                }
                w2 = width7;
                h2 = height2;

                if(Braille_insert[2][0]==0)
                    canvas.drawCircle(width7,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height3,bigcircle,paint);
                    tw3 = width7;
                    th3 = height3;
                }
                w3 = width7;
                h3 = height3;

                if(Braille_insert[0][1]==0)
                    canvas.drawCircle(width8,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height1,bigcircle,paint);
                    tw4 = width8;
                    th4 = height1;
                }
                w4 = width8;
                h4 = height1;

                if(Braille_insert[1][1]==0)
                    canvas.drawCircle(width8,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height2,bigcircle,paint);
                    tw5 = width8;
                    th5 = height2;
                }
                w5 = width8;
                h5 = height2;

                if(Braille_insert[2][1]==0)
                    canvas.drawCircle(width8,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height3,bigcircle,paint);
                    tw6 = width8;
                    th6 = height3;
                }
                w6 = width8;
                h6 = height3;

                if(Braille_insert[0][2]==0)
                    canvas.drawCircle(width9,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height1,bigcircle,paint);
                    tw7 = width9;
                    th7 = height1;
                }
                w7 = width9;
                h7 = height1;

                if(Braille_insert[1][2]==0)
                    canvas.drawCircle(width9,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height2,bigcircle,paint);
                    tw8 = width9;
                    th8 = height2;
                }
                w8 = width9;
                h8 = height2;

                if(Braille_insert[2][2]==0)
                    canvas.drawCircle(width9,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height3,bigcircle,paint);
                    tw9 = width9;
                    th9 = height3;
                }
                w9 = width9;
                h9 = height3;

                if(Braille_insert[0][3]==0)
                    canvas.drawCircle(width10,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height1,bigcircle,paint);
                    tw10 = width10;
                    th10 = height1;
                }
                w10 = width10;
                h10 = height1;

                if(Braille_insert[1][3]==0)
                    canvas.drawCircle(width10,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height2,bigcircle,paint);
                    tw11 = width10;
                    th11 = height2;
                }
                w11 = width10;
                h11 = height2;

                if(Braille_insert[2][3]==0)
                    canvas.drawCircle(width10,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height3,bigcircle,paint);
                    tw12 = width10;
                    th12 = height3;
                }
                w12 = width10;
                h12 = height3;

                break;




            case 3: //점자의 칸 수가 세 칸일때
                canvas.drawText(textname_3, height * (float) 0.4, width * (float) 0.2, paint);

                if(Braille_insert[0][0]==0)
                    canvas.drawCircle(width7,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height1,bigcircle,paint);
                    tw1 = width7;
                    th1 = height1;
                }
                w1 = width7;
                h1 = height1;

                if(Braille_insert[1][0]==0)
                    canvas.drawCircle(width7,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height2,bigcircle,paint);
                    tw2 = width7;
                    th2 = height2;
                }
                w2 = width7;
                h2 = height2;

                if(Braille_insert[2][0]==0)
                    canvas.drawCircle(width7,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height3,bigcircle,paint);
                    tw3 = width7;
                    th3 = height3;
                }
                w3 = width7;
                h3 = height3;

                if(Braille_insert[0][1]==0)
                    canvas.drawCircle(width8,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height1,bigcircle,paint);
                    tw4 = width8;
                    th4 = height1;
                }
                w4 = width8;
                h4 = height1;

                if(Braille_insert[1][1]==0)
                    canvas.drawCircle(width8,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height2,bigcircle,paint);
                    tw5 = width8;
                    th5 = height2;
                }
                w5 = width8;
                h5 = height2;

                if(Braille_insert[2][1]==0)
                    canvas.drawCircle(width8,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height3,bigcircle,paint);
                    tw6 = width8;
                    th6 = height3;
                }
                w6 = width8;
                h6 = height3;

                if(Braille_insert[0][2]==0)
                    canvas.drawCircle(width9,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height1,bigcircle,paint);
                    tw7 = width9;
                    th7 = height1;
                }
                w7 = width9;
                h7 = height1;

                if(Braille_insert[1][2]==0)
                    canvas.drawCircle(width9,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height2,bigcircle,paint);
                    tw7 = width9;
                    th7 = height2;
                }
                w8 = width9;
                h8 = height2;

                if(Braille_insert[2][2]==0)
                    canvas.drawCircle(width9,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height3,bigcircle,paint);
                    tw9 = width9;
                    th9 = height3;
                }
                w9 = width9;
                h9 = height3;
//////////////////////////////////////////////////////////////////////////////////////////////////////
                if(Braille_insert[0][3]==0)
                    canvas.drawCircle(width10,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height1,bigcircle,paint);
                    tw10 = width10;
                    th10 = height1;
                }
                w10 = width10;
                h10 = height1;

                if(Braille_insert[1][3]==0)
                    canvas.drawCircle(width10,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height2,bigcircle,paint);
                    tw11 = width10;
                    th11 = height2;
                }
                w11 = width10;
                h11 = height2;

                if(Braille_insert[2][3]==0)
                    canvas.drawCircle(width10,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height3,bigcircle,paint);
                    tw12 = width10;
                    th12 = height3;
                }
                w12 = width10;
                h12 = height3;

                if(Braille_insert[0][4]==0)
                    canvas.drawCircle(width11,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width11,height1,bigcircle,paint);
                    tw13 = width11;
                    th13 = height1;
                }
                w13 = width11;
                h13 = height1;

                if(Braille_insert[1][4]==0)
                    canvas.drawCircle(width11,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width11,height2,bigcircle,paint);
                    tw14 = width11;
                    th14 = height2;
                }
                w14 = width11;
                h14 = height2;

                if(Braille_insert[2][4]==0)
                    canvas.drawCircle(width11,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width11,height3,bigcircle,paint);
                    tw15 = width11;
                    th15 = height3;
                }
                w15 = width11;
                h15 = height3;

                if(Braille_insert[0][5]==0)
                    canvas.drawCircle(width12,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width12,height1,bigcircle,paint);
                    tw16 = width12;
                    th16 = height1;
                }
                w16 = width12;
                h16 = height1;

                if(Braille_insert[1][5]==0)
                    canvas.drawCircle(width12,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width12,height2,bigcircle,paint);
                    tw17 = width12;
                    th17 = height2;
                }
                w17 = width12;
                h17 = height2;

                if(Braille_insert[2][5]==0)
                    canvas.drawCircle(width12,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width12,height3,bigcircle,paint);
                    tw18 = width12;
                    th18 = height3;
                }
                w18 = width12;
                h18 = height3;

                break;
        }
    }
}