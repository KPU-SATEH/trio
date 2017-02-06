package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.view.View;

import com.example.yeo.practice.WHclass;

/**
 * Created by 여명 on 2017-02-01.
 */

public class Communication_teacher_display extends View {

    static boolean next = false;
    float width= WHclass.width; //가로
    float height= WHclass.height; //세로
    int x=0, y=0; // 점자를 터치할때 사용할 좌표를 저장할 변수
    Vibrator vibrator; //진동 변수
    int dot_count=7;

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
    static String textname_7="";// 불러온 점자가 의미하는 글자를 저장하는 변수
    static float Braille_insert[][] = new float [3][14] ;

    static int page=0;

    int k=0; //점자 칸을 반영하는 변수

    public void quiz_target_init(){
        for(int i=0 ; i<3; i++){ // 돌출점자와 비돌출점자의 x,y값을 저장하는 배열변수를 초기화함
            for(int j=0 ; j<14; j++) {
                target7_width[i][j] = 0;
                target7_height[i][j] = 0;
                notarget7_width[i][j] = 0;
                notarget7_height[i][j] = 0;
                Braille_insert[i][j] = 0;
            }
        }
    }

    public void teacher_display_init(){//화면 초기화 함수. 화면이 이동될 때 점자를 다시 그려줌.
        for(int i=0 ; i<3; i++){ // 돌출점자와 비돌출점자의 x,y값을 저장하는 배열변수를 초기화함
            for(int j=0 ; j<14; j++) {
                target7_width[i][j] = 0;
                target7_height[i][j] = 0;
                notarget7_width[i][j] = 0;
                notarget7_height[i][j] = 0;
                Braille_insert[i][j] = 0;
            }
        }
    }
    public Communication_teacher_display(Context context) {
        super(context);
        teacher_display_init();
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

        canvas.drawText(textname_7, height * (float) 0.4, width * (float) 0.2, paint);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 14; j++) {
                if (Braille_insert[i][j] == 0)
                    canvas.drawCircle(width_7[i][j], height_7[i][j], minicircle, paint);
                else {
                    canvas.drawCircle(width_7[i][j], height_7[i][j], bigcircle, paint);
                    target7_width[i][j] = width_7[i][j];
                    target7_height[i][j] = height_7[i][j];
                }
                notarget7_width[i][j] = width_7[i][j];
                notarget7_height[i][j] = height_7[i][j];

            }
        }
    }
}


