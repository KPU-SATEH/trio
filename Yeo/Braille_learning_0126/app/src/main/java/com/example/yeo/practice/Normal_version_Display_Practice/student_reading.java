package com.example.yeo.practice.Normal_version_Display_Practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.yeo.practice.Common_braille_data.dot_student_data;
import com.example.yeo.practice.WHclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 여명 on 2017-01-24.
 */

public class student_reading extends View{
        dot_student_data dot_student_data;
        String myJSON;
        private static final String TAG_RESULTS="result";
        private static final String ID = "id";
        private static final String TAG_ARRAY1 = "array1";
        private static final String TAG_ARRAY2 = "array2";
        private static final String TAG_ARRAY3 ="array3";
        private static final String TAG_LETTER ="letter";

        Timer timer =null;
        private TimerTask second; //타이머
        JSONArray abc_ = null;  //  Mysql에서 불러올 데이터를 저장할 배열
        String room="80";    // 테스트용 방 설정

        private static Context mMain;
        static boolean next = false;
        float width= WHclass.width; //가로
        float height= WHclass.height; //세로
        int x=0, y=0; // 점자를 터치할때 사용할 좌표를 저장할 변수
        Vibrator vibrator; //진동 변수

        TextToSpeech tts;
        int dot_count=7;
        static int Braille_insert[][];

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


        static int page=0;


        public student_reading(Context context) {
            super(context);
            mMain = context;
            tts = new TextToSpeech(mMain, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    tts.setLanguage(Locale.KOREA);
                }
            });
            getData("http://192.168.0.124:10080/import.php", room);
            minicircle = width*(float)0.01; //작은점자  크기 메크로
            bigcircle = width*(float)0.049; //큰 점자 크기 메크로
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            dot_student_data = new dot_student_data();
        }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);//점자의 색을 지정
        paint.setTextSize(width*(float)0.21);//점자를 의미하는 글자의 크기를 지정
        paint.setAntiAlias(true);// 점자의 표면을 부드럽게 그려줌
        //getData("http://192.168.0.124:10080/import.php", room);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 14; j++) {
                switch(dot_student_data.dot_count){
                    case 0:
                        canvas.drawCircle(width_7[i][j], height_7[i][j], minicircle, paint);
                        break;
                    default:
                        if (dot_student_data.student_Array.get(page)[i][j] == 0)
                            canvas.drawCircle(width_7[i][j], height_7[i][j], minicircle, paint);
                        else {
                            canvas.drawCircle(width_7[i][j], height_7[i][j], bigcircle, paint);
                            target7_width[i][j] = width_7[i][j];
                            target7_height[i][j] = height_7[i][j];
                        }
                        notarget7_width[i][j] = width_7[i][j];
                        notarget7_height[i][j] = height_7[i][j];
                        break;
                }
            }
        }
        //dot_student_data.delete_data();

    }
    protected void show(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            abc_ = jsonObj.getJSONArray(TAG_RESULTS);   //  mysql에서 불러온값을 저장.

            for(int i=0;i<abc_.length();i++){
                Braille_insert = new int[3][14];
                JSONObject c = abc_.getJSONObject(i);
                String id = c.getString(ID);
                String array1 = c.getString(TAG_ARRAY1);    // 저장코드들
                String array2 = c.getString(TAG_ARRAY2);
                String array3 = c.getString(TAG_ARRAY3);
                String letter = c.getString(TAG_LETTER);

                for (int a = 0; a < 14; a++) {
                    if (array1.charAt(a) == '0') {
                        Braille_insert[0][a] = 0;
                    }
                    else {
                        Braille_insert[0][a]=1;
                    }
                }
                for (int a = 0; a < 14; a++) {
                    if (array2.charAt(a) == '0')
                        Braille_insert[1][a]=0;
                    else {
                        Braille_insert[1][a]=1;
                    }
                }
                for (int a = 0; a < 14; a++) {
                    if (array3.charAt(a) == '0')
                        Braille_insert[2][a]=0;
                    else {
                        Braille_insert[2][a]=1;
                    }
                }
                dot_student_data.input_data(Braille_insert, letter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url, String room2){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = (String)params[0];
                String room2 = (String)params[1];

                BufferedReader bufferedReader = null;
                try {
                    String data  = URLEncoder.encode("room2", "UTF-8") + "=" + URLEncoder.encode(room2, "UTF-8");
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                    wr.write(data);
                    wr.flush();
                    //$array1 = $_POST['array1'];
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                show();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url, room2);
    }
}