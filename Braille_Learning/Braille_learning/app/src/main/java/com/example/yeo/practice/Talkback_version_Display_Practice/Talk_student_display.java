package com.example.yeo.practice.Talkback_version_Display_Practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.yeo.practice.Common_braille_data.dot_student_data;
import com.example.yeo.practice.MainActivity;
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
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 여명 on 2017-02-01.
 */

public class Talk_student_display extends View {
    com.example.yeo.practice.Common_braille_data.dot_student_data dot_student_data;
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

    private static Context mMain;
    static boolean next = false;
    float width= WHclass.width; //가로
    float height= WHclass.height; //세로
    int x=0, y=0; // 점자를 터치할때 사용할 좌표를 저장할 변수
    Vibrator vibrator; //진동 변수

    int dot_count=7;
    static int Braille_insert[][];
    static int count=14;
    String TTs_text="";

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


    public static int page=0;
    public static boolean check = false;
    public static String text = "";

    public Talk_student_display(Context context) {
        super(context);
        mMain = context;
        minicircle = width*(float)0.01; //작은점자  크기 메크로
        bigcircle = width*(float)0.049; //큰 점자 크기 메크로
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        dot_student_data = new dot_student_data();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);//점자의 색을 지정
        paint.setTextSize(width * (float) 0.21);//점자를 의미하는 글자의 크기를 지정
        paint.setAntiAlias(true);// 점자의 표면을 부드럽게 그려줌
        if (check == true) {
            text = dot_student_data.student_letter.get(page);
            switch (dot_student_data.count_Array.get(page)/2) {
                case 1: //점자의 칸수가 1칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;
                case 2: //점자의 칸수가 2칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;
                case 3: //점자의 칸수가 3칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;
                case 4: //점자의 칸수가 4칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;
                case 5://점자의 칸수가 5칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;
                case 6:  //점자의 칸수가 6칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;
                case 7: //점자의 칸수가 7칸일 경우 글자의 길이에 따라 글자의 출력위치를 조정함
                    if (text.length() <= 1)
                        canvas.drawText(text, height * (float) 0.45, width * (float) 0.3, paint);
                    else if (text.length() <= 2)
                        canvas.drawText(text, height * (float) 0.4, width * (float) 0.3, paint);
                    else if (text.length() <= 3)
                        canvas.drawText(text, height * (float) 0.35, width * (float) 0.3, paint);
                    else if (text.length() <= 4)
                        canvas.drawText(text, height * (float) 0.3, width * (float) 0.3, paint);
                    break;

            }
        }
        if (dot_student_data.count_Array.size() != 0)
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < dot_student_data.count_Array.get(page); j++) {
                    switch (dot_student_data.dot_count) {
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
    }
    protected void show(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            abc_ = jsonObj.getJSONArray(TAG_RESULTS);   //  mysql에서 불러온값을 저장.
            if(abc_.length() == 0){
                MainActivity.Braille_TTS.TTS_Play("해당 방이 존재하지 않습니다.");
            }
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
                for(int a=13; a>0; a=a-2){
                    int b = 0;
                    if(Braille_insert[b][a] == 0 && Braille_insert[b+1][a] == 0 && Braille_insert[b+2][a] == 0 && Braille_insert[b][a-1] == 0 && Braille_insert[b+1][a-1] == 0 && Braille_insert[b+2][a-1] == 0) {
                        count = a - 1;
                    } else {
                        dot_student_data.input_count(count);
                        count = 14;
                        break;
                    }
                }
                dot_student_data.input_data(Braille_insert, letter);
                invalidate();
            }//page값이 --됨;;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //if(page==dot_student_data.dot_count-2)
        //  page++;
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
    public String Grade_speak(){
        int a=0;
        int b=0;
        String letter = dot_student_data.student_letter.get(page);
        TTs_text = "";
        int braille_count = dot_student_data.count_Array.get(page)/2;
        switch(braille_count){
            case 1:
                TTs_text += " 한칸,";
                break;
            case 2:
                TTs_text += " 두칸,";
                break;
            case 3:
                TTs_text += " 세칸,";
                break;
            case 4:
                TTs_text += " 네칸,";
                break;
            case 5:
                TTs_text += " 다섯칸,";
                break;
            case 6:
                TTs_text += " 여섯칸,";
                break;
            case 7:
                TTs_text += " 일곱칸,";
                break;
        }
        for (int i=0; i<braille_count*2; i++) {
            for (int j=0 ; j<3 ; j++) {
                if(dot_student_data.student_Array.get(page)[j][i]==1) {
                    switch (j) {
                        case 0:
                            if (i % 2 == 0)
                                TTs_text += "1 ";
                            else
                                TTs_text += "4 ";
                            break;
                        case 1:
                            if (i % 2 == 0)
                                TTs_text += "2 ";
                            else
                                TTs_text += "5 ";
                            break;
                        case 2:
                            if (i % 2 == 0)
                                TTs_text += "3 ";
                            else
                                TTs_text += "6 ";
                            break;
                    }
                }

                if(j==2 && i%2!=0){
                    TTs_text += "점, ";
                }
            }
            a = 0;
            b++;
        }
        return letter+TTs_text;
    }
}
