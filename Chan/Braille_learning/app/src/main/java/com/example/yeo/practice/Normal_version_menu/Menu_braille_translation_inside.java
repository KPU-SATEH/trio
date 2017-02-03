package com.example.yeo.practice.Normal_version_menu;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeo.practice.Common_braille_translation.Braille_translation;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.R;

import java.util.Timer;
import java.util.TimerTask;


public class Menu_braille_translation_inside extends FragmentActivity {


    String hangel="";

    private TimerTask second; // 버전확인을 위한 타이머
    private final Handler handler = new Handler();
    Timer timer =null;
    int timer_check=0;
    String Translation_text="";


    int matrix[][];
    Braille_translation Translation;
    boolean Matrix_check=false;


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

        decorView.setSystemUiVisibility( uiOption );      //  setContentView(R.layout.activity_menu_braille_translation_inside);


        matrix = new int[3][14];
        Translation = new Braille_translation();
        matrix_init();
        hangel ="교육";
        Translation.Translation(hangel);
        Matrix_check=Matrix_copy();
        if(Matrix_check==false) {
            Toast.makeText(Menu_braille_translation_inside.this, "7칸이 넘어서 번역이 불가능합니다.", Toast.LENGTH_SHORT).show();
            matrix_init();
        }
        else {
            Translation_text = Translation.get_TTs_text();
            matrix_print();
            MainActivity.Braille_TTS.TTS_Play(Translation_text);
                Timer_Reset();

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
    public void matrix_print(){                 //점자를 출력하는 함수
        int a=0;
        int b=0;
        /*
        braille.setText(matrix[0][0] + " " + matrix[0][1] + "   " + matrix[0][2] + " " + matrix[0][3] + "   " + matrix[0][4] + " " + matrix[0][5] + "   " + matrix[0][6] + " " + matrix[0][7] + "   " + matrix[0][8] + " " + matrix[0][9] + "   " + matrix[0][10] + " " + matrix[0][11] + "   " + matrix[0][12] + " " + matrix[0][13] + "\n"
                + matrix[1][0] + " " + matrix[1][1] + "   " + matrix[1][2] + " " + matrix[1][3] + "   " + matrix[1][4] + " " + matrix[1][5] + "   " + matrix[1][6] + " " + matrix[1][7] + "   " + matrix[1][8] + " " + matrix[1][9] + "   " + matrix[1][10] + " " + matrix[1][11] + "   " + matrix[1][12] + " " + matrix[1][13] + "\n"
                + matrix[2][0] + " " + matrix[2][1] + "   " + matrix[2][2] + " " + matrix[2][3] + "   " + matrix[2][4] + " " + matrix[2][5] + "   " + matrix[2][6] + " " + matrix[2][7] + "   " + matrix[2][8] + " " + matrix[2][9] + "   " + matrix[2][10] + " " + matrix[2][11] + "   " + matrix[2][12] + " " + matrix[2][13]);
                */
    }

    public void matrix_init(){                      //점자를 초기화 하는 함수
        Translation.matrix_init();
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                matrix[i][j]=0;
            }
        }
        /*
        braille.setText(matrix[0][0]+" "+matrix[0][1]+"   "+matrix[0][2]+" "+matrix[0][3]+"   "+matrix[0][4]+" "+matrix[0][5]+"   "+matrix[0][6]+" "+matrix[0][7]+"   "+matrix[0][8]+" "+matrix[0][9]+"   "+matrix[0][10]+" "+matrix[0][11]+"   "+matrix[0][12]+" "+matrix[0][13]+"\n"
                +matrix[1][0]+" "+matrix[1][1]+"   "+matrix[1][2]+" "+matrix[1][3]+"   "+matrix[1][4]+" "+matrix[1][5]+"   "+matrix[1][6]+" "+matrix[1][7]+"   "+matrix[1][8]+" "+matrix[1][9]+"   "+matrix[1][10]+" "+matrix[1][11]+"   "+matrix[1][12]+" "+matrix[1][13]+"\n"
                +matrix[2][0]+" "+matrix[2][1]+"   "+matrix[2][2]+" "+matrix[2][3]+"   "+matrix[2][4]+" "+matrix[2][5]+"   "+matrix[2][6]+" "+matrix[2][7]+"   "+matrix[2][8]+" "+matrix[2][9]+"   "+matrix[2][10]+" "+matrix[2][11]+"   "+matrix[2][12]+" "+matrix[2][13]);
                */

    }



    public void Timer_Start(){ //1초의 딜레이 시간을 갖는 함수
        second = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        timer.schedule(second,0,1500); //0.3초의 딜레이시간

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

    public void update(){ //일정시간마다 타이머 함수에 의해 불려짐
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                timer_check++;
                if(timer_check==2) {
                    MainActivity.Braille_TTS.TTS_Play(Translation_text);
                    Timer_Stop();
                    timer_check=0;
                }
            }
        };
        handler.post(updater);
    }
}

