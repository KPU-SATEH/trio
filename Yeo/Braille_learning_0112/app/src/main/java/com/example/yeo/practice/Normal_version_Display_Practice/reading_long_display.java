package com.example.yeo.practice.Normal_version_Display_Practice;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_quiz_word;
import com.example.yeo.practice.WHclass;
import com.example.yeo.practice.Normal_version_quiz.quiz_score;

import java.util.Locale;
import java.util.Random;

/**
 * Created by yoonc on 2016-07-25.
 */
class reading_long_display extends View {
    /*
4칸 이상의 점자를 화면에 출력해주는 클래스
4칸부터 7칸까지 점자를 표현할 수 있도록 설정되어 있음
단, 퀴즈이기 때문에, 점자를 의미하는 글자를 표현하지 않음
 */
    SpeechRecognizer mRecognizer;
    int posx1,posx2,posy1,posy2;
    private static Context mMain;
    Random random;
    int test = 1;
    static boolean next = false;
    int max, min=0; // 랜덤변수 최대값과 최소값
    float width= WHclass.width; //가로
    float height= WHclass.height; //세로
    int x=0, y=0; // 점자를 터치할때 사용할 좌표를 저장할 변수
    Vibrator vibrator; //진동 변수
    static boolean print=false;
    String tv ;
    TextToSpeech tts;
    int dot_count=0;

    //점자를 출력하기 위한 화면의 좌표값을 미리 선언하였고, 돌출점자와 비돌출점자의 크기를 미리 선언하였음
    float width1=width*(float)0.05,width2=width*(float)0.15,width3=width*(float)0.3,width4=width*(float)0.4,width5=width*(float)0.55,width6=width*(float)0.65,width7=width*(float)0.8, width8=width*(float)0.9, width9=width*(float)1.05, width10=width*(float)1.15, width11=width*(float)1.3, width12=width*(float)1.4, width13=width*(float)1.55, width14=width*(float)1.65;
    float height1=width*(float)0.75,height2=width*(float)0.85,height3=width*(float)0.95;
    float minicircle, bigcircle; // 비돌출점자와 돌출점자 메크로

    //점자를 출력하는 좌표값들을 배열에 저장함
    float width_7[][]={{width1,width2,width3,width4,width5,width6,width7,width8,width9,width10,width11,width12,width13,width14},{width1,width2,width3,width4,width5,width6,width7,width8,width9,width10,width11,width12,width13,width14},{width1,width2,width3,width4,width5,width6,width7,width8,width9,width10,width11,width12,width13,width14}};
    float height_7[][]={{height1,height1,height1,height1,height1,height1,height1,height1,height1,height1,height1,height1,height1,height1},
            {height2,height2,height2,height2,height2,height2,height2,height2,height2,height2,height2,height2,height2,height2},
            {height3,height3,height3,height3,height3,height3,height3,height3,height3,height3,height3,height3,height3,height3}};

    static float target7_width[][] = new float[3][14];//돌출된 점자의 x 좌표값을 저장하는 배열 변수
    static float target7_height[][] = new float[3][14];//돌출된 점자의 y 좌표값을 저장하는 배열 변수
    static float notarget7_width[][] = new float[3][14];//비돌출 점자의 x 좌표값을 저장하는 배열 변수
    static float notarget7_height[][] =new float[3][14]; //비돌출 점자의 y 좌표값을 저장하는 배열 변수
    static int text_7[][] = new int[3][14];  // 7칸의 점자를 저장하는 배열 변수. 점자 배열정보가 담긴 클래스로부터 불러온 점자를 해당 배열변수에 저장함
    static String textname_7;// 불러온 점자가 의미하는 글자를 저장하는 변수

    static int page=0;

    public void quiz_view2_init(){//화면 초기화 함수. 화면이 이동될 때 점자를 다시 그려줌.
        print=false;
        for(int i=0 ; i<3; i++){ // 돌출점자와 비돌출점자의 x,y값을 저장하는 배열변수를 초기화함
            for(int j=0 ; j<14; j++) {
                target7_width[i][j] = 0;
                target7_height[i][j] = 0;
                notarget7_width[i][j] = 0;
                notarget7_height[i][j] = 0;
            }
        }


        page = random.nextInt(max) + min;
        dot_count = dot_quiz_word.word_dot_count.get(page);

        if(dot_count==1) { //점자가 6개일떄
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
        else if(dot_count==2) { //점자가 12개일때
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
        else if(dot_count==3) { //점자가 18개일때
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 6; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
        else if(dot_count==4){ //점자가 24개 일때
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 8; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
        else if(dot_count==5){ //점자가 30개 일때
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
        else if(dot_count==6){ //점자가 36개 일때
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 12; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
        else if(dot_count==7){ //점자가 42개 일때
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 14; j++) {
                    text_7[i][j] = dot_quiz_word.word_Array.get(page)[i][j];
                    textname_7 = dot_quiz_word.word_name.get(page);
                }
            }
        }
    }
    public reading_long_display(Context context) {
        super(context);
        max = dot_quiz_word.wordcount;
        random = new Random();
        quiz_view2_init();
        mMain = context;
        tts = new TextToSpeech(mMain, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.KOREA);
            }
        });
        minicircle = width*(float)0.01; //작은점자  크기 메크로
        bigcircle = width*(float)0.049; //큰 점자 크기 메크로
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);//점자의 색을 지정
        paint.setTextSize(width*(float)0.21);//점자를 의미하는 글자의 크기를 지정
        paint.setAntiAlias(true);// 점자의 표면을 부드럽게 그려줌

        switch(dot_count) {

            case 2: //점자의 칸수가 2칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함. 또한 음성출력을 통한 퀴즈메뉴를 진행함
                if(print==true) {
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_7)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);
                for(int i = 0; i<3 ; i++){
                    for(int j=0 ; j<4 ; j++){
                        if(text_7[i][j]==0)
                            canvas.drawCircle(width_7[i][j],height_7[i][j],minicircle,paint);
                        else {
                            canvas.drawCircle(width_7[i][j],height_7[i][j],bigcircle,paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];

                    }
                }
                break;

            case 3://점자의 칸수가 3칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함. 또한 음성출력을 통한 퀴즈메뉴를 진행함
                if(print==true){
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_7)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);

                for(int i = 0; i<3 ; i++){
                    for(int j=0 ; j<6 ; j++){
                        if(text_7[i][j]==0)
                            canvas.drawCircle(width_7[i][j],height_7[i][j],minicircle,paint);
                        else {
                            canvas.drawCircle(width_7[i][j],height_7[i][j],bigcircle,paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];

                    }
                }
                break;


            case 4://점자의 칸수가 4칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함. 또한 음성출력을 통한 퀴즈메뉴를 진행함
                if(print==true){
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_7)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);
                for(int i = 0; i<3 ; i++){
                    for(int j=0 ; j<8 ; j++){
                        if(text_7[i][j]==0)
                            canvas.drawCircle(width_7[i][j],height_7[i][j],minicircle,paint);
                        else {
                            canvas.drawCircle(width_7[i][j],height_7[i][j],bigcircle,paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];

                    }
                }
                break;

            case 5://점자의 칸수가 5칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함. 또한 음성출력을 통한 퀴즈메뉴를 진행함
                if(print==true){
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_7)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);
                for(int i = 0; i<3 ; i++){
                    for(int j=0 ; j<10 ; j++){
                        if(text_7[i][j]==0)
                            canvas.drawCircle(width_7[i][j],height_7[i][j],minicircle,paint);
                        else {
                            canvas.drawCircle(width_7[i][j],height_7[i][j],bigcircle,paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];

                    }
                }
                break;


            case 6://점자의 칸수가 6칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함. 또한 음성출력을 통한 퀴즈메뉴를 진행함
                if(print==true){
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_7)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);

                for(int i = 0; i<3 ; i++){
                    for(int j=0 ; j<12 ; j++){
                        if(text_7[i][j]==0)
                            canvas.drawCircle(width_7[i][j],height_7[i][j],minicircle,paint);
                        else {
                            canvas.drawCircle(width_7[i][j],height_7[i][j],bigcircle,paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];

                    }
                }
                break;

            case 7://점자의 칸수가 7칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함. 또한 음성출력을 통한 퀴즈메뉴를 진행함
                if(print==true){
                    canvas.drawText(tv, height * (float) 0.4, width * (float) 0.2, paint);
                    if(tv.equals(textname_7)){
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("정답 입니다.정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("정답 입니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        quiz_score.score++;
                    }else{
                        print = false;
                        next = true;
                        if(test != 5) {
                            tts.speak("틀렸습니다. 정답은 " + textname_7 + "입니다. 다음 문제로 넘어가시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                            test ++;
                        }else
                            tts.speak("틀렸습니다.정답은 "+textname_7+"입니다. 결과를 확인하시려면 화면을 한번 터치해 주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    canvas.drawText("?", height * (float) 0.4, width * (float) 0.2, paint);
                for(int i = 0; i<3 ; i++){
                    for(int j=0 ; j<14 ; j++){
                        if(text_7[i][j]==0)
                            canvas.drawCircle(width_7[i][j],height_7[i][j],minicircle,paint);
                        else {
                            canvas.drawCircle(width_7[i][j],height_7[i][j],bigcircle,paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];

                    }
                }
                break;
        }

    }
}