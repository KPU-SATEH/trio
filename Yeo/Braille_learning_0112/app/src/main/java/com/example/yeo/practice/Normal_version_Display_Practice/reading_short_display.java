package com.example.yeo.practice.Normal_version_Display_Practice;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_quiz_abbreviation;
import com.example.yeo.practice.Common_braille_data.dot_quiz_alphabet;
import com.example.yeo.practice.Common_braille_data.dot_quiz_final;
import com.example.yeo.practice.Common_braille_data.dot_quiz_initial;
import com.example.yeo.practice.Common_braille_data.dot_quiz_letter;
import com.example.yeo.practice.Common_braille_data.dot_quiz_number;
import com.example.yeo.practice.Common_braille_data.dot_quiz_sentence;
import com.example.yeo.practice.Common_braille_data.dot_quiz_vowel;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Normal_version_quiz.quiz_score;

import java.util.Random;

/**
 * Created by yoonc on 2016-07-25.
 */
class reading_short_display extends View {
    String tv;
    int test = 1;
    quiz_score score;
    Random random;
    TextToSpeech tts;
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
    private static Context mMain;
    static boolean print=false;

    static dot_quiz_initial Dot_quiz_initial;
    static dot_quiz_vowel Dot_quiz_vowel;
    static dot_quiz_final Dot_quiz_final;
    static dot_quiz_number Dot_quiz_number;
    static dot_quiz_alphabet Dot_quiz_alphabet;
    static dot_quiz_sentence Dot_quiz_sentence;
    static dot_quiz_abbreviation Dot_quiz_abbreviation;
    static dot_quiz_letter Dot_quiz_letter;


    int dot_count=0;

    float width1,width2,width3,width4,width5,width6,width7, width8, width9, width10, width11, width12, height1,height2,height3;
    float minicircle, bigcircle;




    static int text_1[][] = new int[3][2]; // 6개 점자 저장 변수
    static int text_2[][] = new int[3][4]; // 12개 점자 저장 변수
    static int text_3[][] = new int[3][6]; // 18개 점자 저장 변수

    static String textname_1; // 6개 점자 이름 저장변수
    static String textname_2; // 12개 점자 이름 저장변수
    static String textname_3; // 18개 점자 이름 저장변수


    public void quiz_view_init(){
        w1=0;w2=0;w3=0;w4=0;w5=0;w6=0;w7=0;w8=0;w9=0;w10=0;w11=0;w12=0;w13=0;w14=0;w15=0;w16=0;w17=0;w18=0; //버튼 가로위치
        h1=0;h2=0;h3=0;h4=0;h5=0;h6=0;h7=0;h8=0;h9=0;h10=0;h11=0;h12=0;h13=0;h14=0;h15=0;h16=0;h17=0;h18=0; //버튼 세로위치
        tw1=0;tw2=0;tw3=0;tw4=0;tw5=0;tw6=0;tw7=0;tw8=0;tw9=0;tw10=0;tw11=0;tw12=0;tw13=0;tw14=0;tw15=0;tw16=0;tw17=0;tw18=0; //타겟 가로위치
        th1=0;th2=0;th3=0;th4=0;th5=0;th6=0;th7=0;th8=0;th9=0;th10=0;th11=0;th12=0;th13=0;th14=0;th15=0;th16=0;th17=0;th18=0; // 타겟 세로위치
        print=false;



        /*
        초성퀴즈, 모음퀴즈, 종성퀴즈, 숫자퀴즈, 알파벳퀴즈, 문자부호퀴즈, 약자및 약어퀴즈, 글자퀴즈
         */
        switch(WHclass.quiz_sel){
            case 1: //
                max = Dot_quiz_initial.Initialcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_initial.Initial_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_initial.Initial_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_initial.Initial_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_initial.Initial_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_initial.Initial_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_initial.Initial_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_initial.Initial_name.get(page);

                        }
                    }
                }
                break;
            case 2: //
                max = Dot_quiz_vowel.vowelcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_vowel.vowel_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_vowel.vowel_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_vowel.vowel_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_vowel.vowel_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_vowel.vowel_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_vowel.vowel_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_vowel.vowel_name.get(page);

                        }
                    }
                }
                break;
            case 3: //
                max = Dot_quiz_final.finalcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_final.final_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_final.final_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_final.final_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_final.final_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_final.final_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_final.final_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_final.final_name.get(page);

                        }
                    }
                }
                break;
            case 4: //
                max = Dot_quiz_number.numbercount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_number.number_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_number.number_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_number.number_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_number.number_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_number.number_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_number.number_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_number.number_name.get(page);

                        }
                    }
                }
                break;
            case 5: //
                max = Dot_quiz_alphabet.alphabetcount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_alphabet.alphabet_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_alphabet.alphabet_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_alphabet.alphabet_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_alphabet.alphabet_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_alphabet.alphabet_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_alphabet.alphabet_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_alphabet.alphabet_name.get(page);

                        }
                    }
                }
                break;
            case 6: //
                max = Dot_quiz_sentence.sentence_count;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_sentence.sentence_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_sentence.sentence_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_sentence.sentence_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_sentence.sentence_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_sentence.sentence_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_sentence.sentence_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_sentence.sentence_name.get(page);

                        }
                    }
                }
                break;
            case 7: //
                max = Dot_quiz_abbreviation.abbreviation_count;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_abbreviation.abbreviation_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_abbreviation.abbreviation_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_abbreviation.abbreviation_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_abbreviation.abbreviation_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_abbreviation.abbreviation_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_abbreviation.abbreviation_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_abbreviation.abbreviation_name.get(page);

                        }
                    }
                }
                break;

            case 8: //
                max = Dot_quiz_letter.lettercount;
                random = new Random();
                page = random.nextInt(max) + min;
                dot_count = Dot_quiz_letter.letter_dot_count.get(page);

                if(dot_count==1) { //점자가 6개일떄
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            text_1[i][j] = Dot_quiz_letter.letter_Array.get(page)[i][j];
                            textname_1 = Dot_quiz_letter.letter_name.get(page);
                        }
                    }
                }
                else if(dot_count==2) { //점자가 12개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
                            text_2[i][j] = Dot_quiz_letter.letter_Array.get(page)[i][j];
                            textname_2 = Dot_quiz_letter.letter_name.get(page);

                        }
                    }
                }
                else if(dot_count==3) { //점자가 18개일때
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 6; j++) {
                            text_3[i][j] = Dot_quiz_letter.letter_Array.get(page)[i][j];
                            textname_3 = Dot_quiz_letter.letter_name.get(page);

                        }
                    }
                }
                break;
        }

    }
    public reading_short_display(Context context) {
        super(context);
        mMain = context;
/*        tts = new TextToSpeech(mMain, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.KOREA);
            }
        });*/

        switch(WHclass.quiz_sel){ //점자의 종류에 따라 점자퀴즈 클래스를 불러옴
            case 1: //초성퀴즈
                Dot_quiz_initial = new dot_quiz_initial();
                break;
            case 2: //모음퀴즈
                Dot_quiz_vowel = new dot_quiz_vowel();
                break;
            case 3: //종성퀴즈
                Dot_quiz_final = new dot_quiz_final();
                break;
            case 4: //숫자퀴즈
                Dot_quiz_number = new dot_quiz_number();
                break;
            case 5: //알파벳 퀴즈
                Dot_quiz_alphabet = new dot_quiz_alphabet();
                break;
            case 6: //문장부호 퀴즈
                Dot_quiz_sentence = new dot_quiz_sentence();
                break;
            case 7: //약자및 약어 퀴즈
                Dot_quiz_abbreviation = new dot_quiz_abbreviation();
                break;
            case 8: //글자 퀴즈
                Dot_quiz_letter = new dot_quiz_letter();
                break;
        }

        quiz_view_init();

        width = WHclass.width; //MainActivity에서 불러온 가로비율을 저장
        height = WHclass.height; //MainActivity에서 불러온 세로비율을 저장

        minicircle = width*(float)0.02; //작은점자  크기 메크로
        bigcircle = width*(float)0.099; //큰 점자 크기 메크로

        width1 = width*(float)0.1; //점자가 6개일때
        width2 = width*(float)0.3;

        width3 = width*(float)0.1; //점자가 12개일때
        width4 = width*(float)0.3;
        width5 = width*(float)0.6;
        width6 = width*(float)0.8;

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
                if(print==true) {
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_1)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_1 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_1+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_1 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_1+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);


                if(text_1[0][0]==0)
                    canvas.drawCircle(width1,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width1,height1,bigcircle,paint);
                    tw1 = width1;
                    th1 = height1;
                }
                w1 = width1;
                h1 = height1;

                if(text_1[1][0]==0)
                    canvas.drawCircle(width1,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width1,height2,bigcircle,paint);
                    tw2 = width1;
                    th2 = height2;
                }
                w2 = width1;
                h2 = height2;

                if(text_1[2][0]==0)
                    canvas.drawCircle(width1,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width1,height3,bigcircle,paint);
                    tw3 = width1;
                    th3 = height3;
                }
                w3 = width1;
                h3 = height3;

                if(text_1[0][1]==0)
                    canvas.drawCircle(width2,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width2,height1,bigcircle,paint);
                    tw4 = width2;
                    th4 = height1;
                }
                w4 = width2;
                h4 = height1;

                if(text_1[1][1]==0)
                    canvas.drawCircle(width2,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width2,height2,bigcircle,paint);
                    tw5 = width2;
                    th5 = height2;
                }
                w5 = width2;
                h5 = height2;

                if(text_1[2][1]==0)
                    canvas.drawCircle(width2,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width2,height3,bigcircle,paint);
                    tw6 = width2;
                    th6 = height3;
                }
                w6 = width2;
                h6 = height3;
                break;




            case 2: //점자의 칸 수가 두 칸 일때
                if(print==true) {
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_2)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_2 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_2+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_2 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_2+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);
                if(text_2[0][0]==0)
                    canvas.drawCircle(width3,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width3,height1,bigcircle,paint);
                    tw1 = width3;
                    th1 = height1;
                }
                w1 = width3;
                h1 = height1;

                if(text_2[1][0]==0)
                    canvas.drawCircle(width3,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width3,height2,bigcircle,paint);
                    tw2 = width3;
                    th2 = height2;
                }
                w2 = width3;
                h2 = height2;

                if(text_2[2][0]==0)
                    canvas.drawCircle(width3,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width3,height3,bigcircle,paint);
                    tw3 = width3;
                    th3 = height3;
                }
                w3 = width3;
                h3 = height3;

                if(text_2[0][1]==0)
                    canvas.drawCircle(width4,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width4,height1,bigcircle,paint);
                    tw4 = width4;
                    th4 = height1;
                }
                w4 = width4;
                h4 = height1;

                if(text_2[1][1]==0)
                    canvas.drawCircle(width4,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width4,height2,bigcircle,paint);
                    tw5 = width4;
                    th5 = height2;
                }
                w5 = width4;
                h5 = height2;

                if(text_2[2][1]==0)
                    canvas.drawCircle(width4,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width4,height3,bigcircle,paint);
                    tw6 = width4;
                    th6 = height3;
                }
                w6 = width4;
                h6 = height3;

                if(text_2[0][2]==0)
                    canvas.drawCircle(width5,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width5,height1,bigcircle,paint);
                    tw7 = width5;
                    th7 = height1;
                }
                w7 = width5;
                h7 = height1;

                if(text_2[1][2]==0)
                    canvas.drawCircle(width5,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width5,height2,bigcircle,paint);
                    tw8 = width5;
                    th8 = height2;
                }
                w8 = width5;
                h8 = height2;

                if(text_2[2][2]==0)
                    canvas.drawCircle(width5,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width5,height3,bigcircle,paint);
                    tw9 = width5;
                    th9 = height3;
                }
                w9 = width5;
                h9 = height3;

                if(text_2[0][3]==0)
                    canvas.drawCircle(width6,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width6,height1,bigcircle,paint);
                    tw10 = width6;
                    th10 = height1;
                }
                w10 = width6;
                h10 = height1;

                if(text_2[1][3]==0)
                    canvas.drawCircle(width6,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width6,height2,bigcircle,paint);
                    tw11 = width6;
                    th11 = height2;
                }
                w11 = width6;
                h11 = height2;

                if(text_2[2][3]==0)
                    canvas.drawCircle(width6,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width6,height3,bigcircle,paint);
                    tw12 = width6;
                    th12 = height3;
                }
                w12 = width6;
                h12 = height3;

                break;




            case 3: //점자의 칸 수가 세 칸일때
                if(print==true) {
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_3)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_3 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_3+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_3 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_3+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);

                if(text_3[0][0]==0)
                    canvas.drawCircle(width7,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height1,bigcircle,paint);
                    tw1 = width7;
                    th1 = height1;
                }
                w1 = width7;
                h1 = height1;

                if(text_3[1][0]==0)
                    canvas.drawCircle(width7,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height2,bigcircle,paint);
                    tw2 = width7;
                    th2 = height2;
                }
                w2 = width7;
                h2 = height2;

                if(text_3[2][0]==0)
                    canvas.drawCircle(width7,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width7,height3,bigcircle,paint);
                    tw3 = width7;
                    th3 = height3;
                }
                w3 = width7;
                h3 = height3;

                if(text_3[0][1]==0)
                    canvas.drawCircle(width8,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height1,bigcircle,paint);
                    tw4 = width8;
                    th4 = height1;
                }
                w4 = width8;
                h4 = height1;

                if(text_3[1][1]==0)
                    canvas.drawCircle(width8,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height2,bigcircle,paint);
                    tw5 = width8;
                    th5 = height2;
                }
                w5 = width8;
                h5 = height2;

                if(text_3[2][1]==0)
                    canvas.drawCircle(width8,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width8,height3,bigcircle,paint);
                    tw6 = width8;
                    th6 = height3;
                }
                w6 = width8;
                h6 = height3;

                if(text_3[0][2]==0)
                    canvas.drawCircle(width9,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height1,bigcircle,paint);
                    tw7 = width9;
                    th7 = height1;
                }
                w7 = width9;
                h7 = height1;

                if(text_3[1][2]==0)
                    canvas.drawCircle(width9,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height2,bigcircle,paint);
                    tw7 = width9;
                    th7 = height2;
                }
                w8 = width9;
                h8 = height2;

                if(text_3[2][2]==0)
                    canvas.drawCircle(width9,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width9,height3,bigcircle,paint);
                    tw9 = width9;
                    th9 = height3;
                }
                w9 = width9;
                h9 = height3;
//////////////////////////////////////////////////////////////////////////////////////////////////////
                if(text_3[0][3]==0)
                    canvas.drawCircle(width10,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height1,bigcircle,paint);
                    tw10 = width10;
                    th10 = height1;
                }
                w10 = width10;
                h10 = height1;

                if(text_3[1][3]==0)
                    canvas.drawCircle(width10,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height2,bigcircle,paint);
                    tw11 = width10;
                    th11 = height2;
                }
                w11 = width10;
                h11 = height2;

                if(text_3[2][3]==0)
                    canvas.drawCircle(width10,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width10,height3,bigcircle,paint);
                    tw12 = width10;
                    th12 = height3;
                }
                w12 = width10;
                h12 = height3;

                if(text_3[0][4]==0)
                    canvas.drawCircle(width11,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width11,height1,bigcircle,paint);
                    tw13 = width11;
                    th13 = height1;
                }
                w13 = width11;
                h13 = height1;

                if(text_3[1][4]==0)
                    canvas.drawCircle(width11,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width11,height2,bigcircle,paint);
                    tw14 = width11;
                    th14 = height2;
                }
                w14 = width11;
                h14 = height2;

                if(text_3[2][4]==0)
                    canvas.drawCircle(width11,height3,minicircle,paint);
                else{
                    canvas.drawCircle(width11,height3,bigcircle,paint);
                    tw15 = width11;
                    th15 = height3;
                }
                w15 = width11;
                h15 = height3;

                if(text_3[0][5]==0)
                    canvas.drawCircle(width12,height1,minicircle,paint);
                else{
                    canvas.drawCircle(width12,height1,bigcircle,paint);
                    tw16 = width12;
                    th16 = height1;
                }
                w16 = width12;
                h16 = height1;

                if(text_3[1][5]==0)
                    canvas.drawCircle(width12,height2,minicircle,paint);
                else{
                    canvas.drawCircle(width12,height2,bigcircle,paint);
                    tw17 = width12;
                    th17 = height2;
                }
                w17 = width12;
                h17 = height2;

                if(text_3[2][5]==0)
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