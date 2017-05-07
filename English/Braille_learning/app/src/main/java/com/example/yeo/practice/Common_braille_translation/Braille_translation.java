package com.example.yeo.practice.Common_braille_translation;

import android.renderscript.ScriptGroup;

import java.util.StringTokenizer;

/**
 * Created by chanh on 2017-01-26.
 */

public class Braille_translation {
    String Braille_text="";
    String Input_Text="";
    String Trans_text="";

    String unicode="";

    String TTs_text="";

    Trans_dot_num transDot_number; //숫자 점자 저장 클래스
    com.example.yeo.practice.Common_braille_translation.Trans_dot_alphabet Trans_dot_alphabet; //알파벳 점자 저장 클래스

    int matrix[][];
    int text_length=0;

    int dot_count=0; //점자 칸수 확인
    int matrix_count=0; //7칸의 점자 중 기록된 점자의 칸 수를 카운트하는 변수

    public static int Text_number=0;
    public static boolean same_data_check=false;


    boolean number_check = false; //숫자가 나온적이 있는지 체크하는 변수
    boolean alphabet_check = false; //알파벳이 나온적이 있는지 체크하는 변수
    int text_i=0;

    int Trans_dotcount=0;

    public static int abbreviation_start_point=0;

    public Braille_translation(){
        transDot_number = new Trans_dot_num();
        Trans_dot_alphabet = new Trans_dot_alphabet();

        matrix = new int[3][14];

    }

    public void Translation(String text){

        matrix_init();
        String value[] = text.split(" ");
        Braille_text=text;
        TTs_text = text+",";
        Input_Text = text;

        for(int i=0 ; i<value.length ; i++) {
            Input_Text = value[i];
            unicode = getUnicode(Input_Text);      //글자의 유니코드를 산출
            Trans_text = UNI_change_KOR(unicode);      // 유니코드를 글로 변환

            text_length = Input_Text.length();
            for (text_i = 0; text_i < text_length; text_i++) {
                if (same_data_check == true) {
                    text_i--;
                    same_data_check = false;
                }
                if (text_i == text_length - 1)
                    Trans_dot_alphabet.finish = true;

                Text_number = text_i;
                Braille_trans();
            }
            Trans_dot_alphabet.search_count = 0;
            Trans_dot_alphabet.position_check = false;
            Trans_dot_alphabet.first = true;
            Trans_dot_alphabet.samedata = false;
            Trans_dot_alphabet.samedata_index = 0;
            same_data_check = false;
            Text_number = 0;
            Trans_dot_alphabet.finish = false;
            Trans_dot_alphabet.real_finish = false;
            Trans_dot_alphabet.check = false;
            Trans_dot_alphabet.alphabet_temp_Array.clear();
        }

    }



    public void Braille_trans(){
        int eng_count=0;
        int abbreviation_check=-1;
        for(int i=0 ; i<Input_Text.length() ; i++){
            char Input_temp = Input_Text.charAt(i);
            if((Input_temp>=0x61 && Input_temp<=0x7a) || (Input_temp>=0x41 && Input_temp<=0x5a)){
                eng_count++;
            }
        }

        if(eng_count == Input_Text.length()){
            abbreviation_check=Trans_dot_alphabet.name_abbreviation_search(Input_Text);
        }

        if(abbreviation_check!=-1){
            Alphabet_abbreviation_search(abbreviation_check);
            text_i = text_length-1;

        }
        else {
            char target = Input_Text.charAt(Text_number);
            if (target >= 0x30 && target <= 0x39) { // 숫자일때
                if (alphabet_check == true)
                    alphabet_check = false;
                Number_Braille_search(Input_Text);
            } else if ((target >= 0x61 && target <= 0x7a) || (target >= 0x41 && target <= 0x5a)) { // 알파벳 소문자
                if (number_check == true)
                    number_check = false;
                Alphabet_Braille_search(Input_Text);
            }
        }
    }


    //영어 점자 탐색 함수
    public void Alphabet_Braille_search(String Alphabet_text){
        int count=0;
        unicode = getUnicode(Alphabet_text);      //숫자의 유니코드를 산출
        Trans_text = UNI_change_KOR(unicode);      // 유니코드를 숫자로 변환
        count= Trans_dot_alphabet.Name_search(Trans_text);                              //알파벳 탐색 시작
        if (count == -1) {  //-1이면 영어 탐색 성공
            if(Trans_dot_alphabet.search_count==1){
                abbreviation_start_point=text_i;
            }
        }
        else if(count==-2){ //약자가 존재하지 않음
            text_i=abbreviation_start_point-1;
            Trans_dot_alphabet.alphabet_temp_Array.clear();
            Trans_dot_alphabet.position_check = true;
            Trans_dot_alphabet.finish = false;
        }
        else if(count== -3){ //  마지막 글자를 의미함
            if(Trans_dot_alphabet.search_count==0) {
                text_i = text_i - 1;
                Trans_dot_alphabet.real_finish = true;
            }
            else {
                text_i = abbreviation_start_point - 1;
                Trans_dot_alphabet.alphabet_temp_Array.clear();
            }
            Trans_dot_alphabet.position_check = true;
        }
        else if(count >= 0){ // 0보다 크면 약자가 존재하지 않음
            Trans_dot_alphabet.samedata=false;
            Alphabet_search(count);
            Trans_dot_alphabet.search_count=0;
            Trans_dot_alphabet.alphabet_temp_Array.clear();
        }
    }


    //숫자 점자 탐색 함수
    public void Number_Braille_search(String Number_text){
        int count=0;
        unicode = getUnicode(Number_text);      //숫자의 유니코드를 산출
        Trans_text = UNI_change_KOR(unicode);      // 유니코드를 숫자로 변환
        count= transDot_number.Name_search(Trans_text);                              //숫자 탐색 시작
        if (count != -1) {                                   //-1이 아니면 숫자 탐색 성공
            Number_search(count);
        }
    }

    //알파벳 탐색 함수
    public void Alphabet_search(int count){
        dot_count = Trans_dot_alphabet.Dot_count_search(count);                       // 발견한 점자가 몇칸인지 파악
        int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
        matrix_temp = Trans_dot_alphabet.Matrix_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
        matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
        matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
    }

    //알파벳 축어 탐색 함수
    public void Alphabet_abbreviation_search(int count){
        dot_count = Trans_dot_alphabet.Dot_count_abbreviation_search(count);                       // 발견한 점자가 몇칸인지 파악
        int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
        matrix_temp = Trans_dot_alphabet.Matrix_abbreviation_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
        matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
        matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
    }
    //숫자 탐색 함수
    public void Number_search(int count){

        if(number_check==false){ // 숫자가 처음 나타난 것이라면
            //숫자 앞에 수표를 붙임
            dot_count = transDot_number.Dot_count_search(0);                       // 발견한 점자가 몇칸인지 파악
            int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
            matrix_temp = transDot_number.Matrix_search(0, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
            matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
            matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
            number_check = true;

            Number_search(count);

        }
        else if(number_check==true){
            dot_count = transDot_number.Dot_count_search(count);                       // 발견한 점자가 몇칸인지 파악
            int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
            matrix_temp = transDot_number.Matrix_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
            matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
            matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
        }

    }


    //유니코드로 변환하는 함수
    public String getUnicode(String text){
        if(text.equals("")==false)
            text = Integer.toHexString(text.charAt(Text_number) & 0xFFFF);
        return text;
    }

    //16진수를 10진수로 바꾸어주는 함수
    public int Numberchange(String number){
        int return_result=0;
        return_result = Integer.parseInt(number,16);

        return return_result;
    }

    //유니코드를 한글로 바꿔주는 함수
    public String UNI_change_KOR(String unicode){
        String Kor="";
        StringTokenizer str1 = new StringTokenizer(unicode,"\\u");
        while(str1.hasMoreTokens()){
            String str2 = str1.nextToken();
            int j = Integer.parseInt(str2,16);
            Kor += (char)j;
        }
        return Kor;
    }

    //점자 행렬을 얻어오는 함수수
    public int[][] getMatrix(){
        return matrix;
    }


    //점자를 출력하는 함수
    public void matrix_print(int [][] matrix_temp) {
        int a = 0;
        int b = 0;
        if (matrix_count < 15) {
            for (int i=matrix_count-(dot_count*2); i<matrix_count; i++) {
                for (int j=0 ; j<3 ; j++) {
                    matrix[j][i] = matrix_temp[a][b];
                    a++;
                }
                a = 0;
                b++;
            }
        }
        else{
            for(int i=0 ; i<3 ; i++){
                for(int j=0 ; j<14 ; j++){
                    matrix[i][j]=1;
                }
            }
        }
    }

    //점자를 초기화 하는 함수
    public void matrix_init(){
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                matrix[i][j]=0;
            }
        }

        dot_count=0;
        matrix_count=0;
        number_check=false;
        alphabet_check=false;
        Text_number=0;
    }

    //음성출력을 위한 텍스트를 얻어오는 함수
    public String get_TTs_text(){
        int a=0;
        int b=0;
        int braille_count = matrix_count/2;
        Trans_dotcount=braille_count;
        switch(braille_count){
            case 1:
                TTs_text += " one sell,";
                break;
            case 2:
                TTs_text += " two sell,";
                break;
            case 3:
                TTs_text += " three sell,";
                break;
            case 4:
                TTs_text += " four sell,";
                break;
            case 5:
                TTs_text += " five sell,";
                break;
            case 6:
                TTs_text += " six sell,";
                break;
            case 7:
                TTs_text += " seven sell,";
                break;
        }
        for (int i=0; i<matrix_count; i++) {
            for (int j=0 ; j<3 ; j++) {
                if(matrix[j][i]==1) {
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
                    TTs_text += "dot, ";
                }
            }
            a = 0;
            b++;
        }
        return TTs_text;
    }

    public int get_dotcount(){
        return Trans_dotcount;
    }

    public String get_dotname(){
        return Braille_text;
    }
}
