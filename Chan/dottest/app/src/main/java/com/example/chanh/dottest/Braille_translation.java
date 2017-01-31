package com.example.chanh.dottest;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

/**
 * Created by chanh on 2017-01-26.
 */

public class Braille_translation {

    protected int eng_code = 11;
    String Input_Text="";
    String Kor_text="";

    String chosung[] = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    String moum[] ={"아","애","야","얘","어","에","여","예","오","와","왜","외","요","우","워","웨","위","유","으","의","이"};
    String jongsung[] ={"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ","ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅎ"};
    String unicode="";

    String TTs_text="";

    // ㄲ, ㄳ, ㄵ, ㄶ, ㄺ, ㄻ, ㄼ, ㄽ, ㄾ, ㄿ, ㅀ, ㅄ, ㅆ
    dot_initial dot_initial; //초성 점자 저장 클래스
    dot_vowel dot_vowel; //모음 점자 저장 클래스
    dot_final dot_final; //종성 점자 저장 클래스
    dot_abbreviation dot_abbreviation; //약자 및 약어 점자 저장 클래스
    dot_num dot_number ; //숫자 점자 저장 클래스
    dot_alphabet dot_alphabet;

    int matrix[][];
    int text_length=0;

    int dot_count=0; //점자 칸수 확인
    int matrix_count=0; //7칸의 점자 중 기록된 점자의 칸 수를 카운트하는 변수

    int Text_number=0;

    int type=0; //0=완성형, 1=초성, 2=모음
    static public int abbrevion_check=0; //약자 및 약어에서 글자수가 긴 약자 및 약어를 체크하는 변수
    int Search_stop_number=0 ; // 약자 및 약어의 체크를 위해 현재 위치를 저장하는 변수
    public static boolean abbreviaion_search_check = false; //현재 위치에서 약자 및 약어의 긴 글자를 확인했는지 저장하는 변수
    public static boolean abbreviation_search_success = false; // 약자 및 약어에서 긴 글자를 탐색했는지 저장하는 변수

    boolean number_check = false; //숫자가 나온적이 있는지 체크하는 변수
    boolean alphabet_check = false; //알파벳이 나온적이 있는지 체크하는 변수
    int i=0;

    Braille_translation(){
        dot_initial = new dot_initial();
        dot_vowel = new dot_vowel();
        dot_final = new dot_final();
        dot_abbreviation = new dot_abbreviation();
        dot_number = new dot_num();
        dot_alphabet = new dot_alphabet();


        matrix = new int[3][14];
        matrix_init();
    }

    public void Translation(String text){
        Input_Text = text;
        TTs_text = Input_Text;
        unicode = getUnicode(Input_Text);      //글자의 유니코드를 산출
        Kor_text = UNI_change_KOR(unicode);      // 유니코드를 글로 변환

        text_length = Input_Text.length();
        for(i=0 ; i<text_length ; i++){
            Text_number=i;
            Braille_trans();
        }
    }


    public void Braille_trans(){
        char target = Input_Text.charAt(Text_number);
        if(target>=0xAC00 && target<=0xD7A3) {       //가~힣 사이에 글자일 경우
            if(number_check==true)
                number_check=false;
            else if(alphabet_check==true)
                alphabet_check=false;
            type=0;
            Hangel_Braille_search(Input_Text);
        }
        else if(target>=0x3131 && target<=0x314e){      // 초성일때
            if(number_check==true)
                number_check=false;
            else if(alphabet_check==true)
                alphabet_check=false;
            type=1;
            Hangel_Braille_search(Input_Text);

        }
        else if(target>=0x314f && target<=0x3163){      // 모음일때
            if(number_check==true)
                number_check=false;
            else if(alphabet_check==true)
                alphabet_check=false;
            type=2;
            Hangel_Braille_search(Input_Text);
        }
        else if(target>=0x30 && target<=0x39){ // 숫자일때
            if(alphabet_check==true)
                alphabet_check=false;
            type=3;
            Number_Braille_search(Input_Text);
        }
        else if(target>=0x61 && target<=0x7a){ // 알파벳 소문자
            if(number_check==true)
                number_check=false;
            type=4;
            Alphabet_Braille_search(Input_Text);
        }
        else if(target>=0x41 && target<=0x5a){ // 알파벳 대문자
            if(number_check==true)
                number_check=false;
            type=5;
            Alphabet_Braille_search(Input_Text);
        }
    }


    //영어 점자 탐색 함수
    public void Alphabet_Braille_search(String Alphabet_text){
        int count=0;
        unicode = getUnicode(Alphabet_text);      //숫자의 유니코드를 산출
        Kor_text = UNI_change_KOR(unicode);      // 유니코드를 숫자로 변환
        switch(type){
            case 4: // 알파벳 소문자
                count=dot_alphabet.Name_search(Kor_text);                              //알파벳 탐색 시작
                if (count != -1) {                                   //-1이 아니면 초성탐색 성공
                    Alphabet_search(count);
                }
                break;
            case 5: //알파벳 대문자
                count=dot_alphabet.Name_search(Kor_text);                              //알파벳 탐색 시작
                if (count != -1) {                                   //-1이 아니면 초성탐색 성공
                    Alphabet_search(count);
                }
                break;
        }
    }


    //숫자 점자 탐색 함수
    public void Number_Braille_search(String Number_text){
        int count=0;
        unicode = getUnicode(Number_text);      //숫자의 유니코드를 산출
        Kor_text = UNI_change_KOR(unicode);      // 유니코드를 숫자로 변환
        switch(type){
            case 3:
                count=dot_number.Name_search(Kor_text);                              //숫자 탐색 시작
                if (count != -1) {                                   //-1이 아니면 숫자 탐색 성공
                    Number_search(count);
                }
                break;
        }

    }

    //한글 점자 탐색 함수
    public void Hangel_Braille_search(String Hangel_text){
        int finalcode = 0;               //초성, 모음, 종성의 배열 인덱스를 저장할 변수
        int vowelcode = 0;
        int initcode = 0;
        int count = 0;                      //성공 여부를 확인하는 변수


        unicode = getUnicode(Hangel_text);      //한글의 유니코드를 산출
        Kor_text = UNI_change_KOR(unicode);      // 유니코드를 한글로 변환

        switch(type) {
            case 0:
                finalcode = get_final(Hangel_text);                                   //종성의 배열 인덱스를 산출
                vowelcode = get_vowel(Hangel_text, finalcode);                      //모음의 배열 인덱스를 산출
                initcode = get_initial(Hangel_text, vowelcode, finalcode);          //초성의 배열 인덱스를 산출
                count = dot_abbreviation.Name_search(Kor_text,abbrevion_check); // 약자 및 약어에서 탐색 시작

                if(count == -2){ // 단어 길이가 긴 약자 및 약어 중 글자가 매칭되는게 있다면
                    if(abbrevion_check==0) // 그 글자의 첫번째 글짜일 때
                        Search_stop_number=Text_number; // 약자 및 약어에서 글자수가 많은 약자 및 약어를 발견하였을 때, 일단 현재 글자 위치를 저장
                    abbrevion_check++; //칸 이동
                    break;
                }
                else if (count == -1) {                                             //-1이면 탐색 실패. 글자를 분해하여 글자 서치
                    if(abbrevion_check!=0) {
                        if (abbreviation_search_success == false) {
                            i = Search_stop_number - 1; // 현재위치를 기존의 위치로 되돌림
                            Search_stop_number=0; // 현재 글자위치 저장하는 변수 초기화
                            abbrevion_check=0; // 약자 및 약어 단어 길이 수 저장하는 변수 초기화
                            abbreviaion_search_check = true; // 기존위치의 단어길이가 긴 약자 및 약어를 검사했었다고 체크
                            break;
                        }
                    }
                    else {
                        String initial_vowel = "";                                      //초성+모음을 저장하는 변수
                        initial_vowel = get_initial_vowel(Hangel_text, finalcode);       //초성 + 모음
                        count = dot_abbreviation.Name_search(initial_vowel, abbrevion_check);
                        if (count != -1) {                                                //-1이 아니면 약자 및 약어 탐색 성공
                            Abbreviation_search(count);
                            if (finalcode != 0) {
                                count = dot_final.Name_search(jongsung[finalcode]);          //종성 서치 시작
                                if (count != -1) Final_search(count);
                            }
                        }
                        else {
                            String vowel_final = "";                                      //ㅇ+모음+종성을 저장하는 변수
                            vowel_final = get_vowel_final(Kor_text, finalcode, vowelcode);       //ㅇ+모음+종성
                            count = dot_abbreviation.Name_search(vowel_final, abbrevion_check);
                            if (count != -1) {
                                int count2 = dot_initial.Name_search(chosung[initcode]);         //초성 서치 시작
                                if (count2 != -1)
                                    Initial_search(count2);
                                Abbreviation_search(count);
                            }
                            else {                                         // -1일 경우 약자 및 약어 탐색 실패, 초성과 모음 점자 서치 시작
                                count = dot_initial.Name_search(chosung[initcode]);         //초성 서치 시작
                                if (count != -1)
                                    Initial_search(count);

                                count = dot_vowel.Name_search(moum[vowelcode]);             //모음 서치 시작
                                if (count != -1)
                                    Vowel_search(count);

                                if (finalcode != 0) { //종성이 존재할 때
                                    count = dot_final.Name_search(jongsung[finalcode]);          //종성 서치 시작
                                    if (count != -1)
                                        Final_search(count);
                                }
                            }
                        }
                    }
                }
                else if (count != -1 && count != -2) {                                                     // -1 아니면 탐색 성공. 받침이 있는 글자 서치
                    if(abbrevion_check!=0){
                        if(abbreviation_search_success==true) { // 단어 길이가 긴 약자 및 약어 탐색에 성공했으면
                            int name_length=0;
                            name_length=dot_abbreviation.get_name(count).length();
                            i = Search_stop_number + name_length-1; // 현재위치를 저장했던 변수 + 단어의 길이-1만큼 현재위치를 이동
                            Search_stop_number=0; // 현재 글자위치 저장하는 변수 초기화
                            abbrevion_check=0; // 약자 및 약어 단어 길이 수 저장하는 변수 초기화
                            abbreviaion_search_check = true; // 기존위치의 단어길이가 긴 약자 및 약어를 검사했었다고 체크
                            Abbreviation_search(count);
                        }
                        else {                                   //탐색하지 못했다면
                            i = Search_stop_number - 1; // 현재위치를 기존의 위치로 되돌림
                            Search_stop_number=0; // 현재 글자위치 저장하는 변수 초기화
                            abbrevion_check=0; // 약자 및 약어 단어 길이 수 저장하는 변수 초기화
                            abbreviaion_search_check = true; // 기존위치의 단어길이가 긴 약자 및 약어를 검사했었다고 체크
                            break;
                        }
                    }
                    else {
                        Abbreviation_search(count);
                        abbreviaion_search_check = false; //현재위치에서 단어 길이가 긴 약자 및 약어를 검사하지 않았다고 체크
                    }
                }
                break;
            case 1:
                count=dot_initial.Name_search(Kor_text);                              //초성 탐색 시작
                if (count != -1)                                                        //-1이 아니면 초성탐색 성공
                    Initial_search(count);
                break;
            case 2:
                count = dot_vowel.Name_search(Kor_text);                      //모음 탐색 시작
                if (count != -1)                                                    //-1이 아니면 모음탐색 성공
                    Vowel_search(count);
                break;
        }
    }

    //알파벳 탐색 함수
    public void Alphabet_search(int count){
        if(alphabet_check==false){ // 숫자가 처음 나타난 것이라면
            //숫자 앞에 수표를 붙임
            int roma=0;
            dot_count = dot_alphabet.Dot_count_search(roma);                       // 발견한 점자가 몇칸인지 파악
            int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
            matrix_temp = dot_alphabet.Matrix_search(roma, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
            matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
            matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
            alphabet_check = true;
            Alphabet_search(count);
        }
        else if(alphabet_check==true){
            if(type==5){
                int fortis=1;
                dot_count = dot_alphabet.Dot_count_search(fortis);                       // 발견한 점자가 몇칸인지 파악
                int matrix_temp2[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
                matrix_temp2 = dot_alphabet.Matrix_search(fortis, matrix_temp2);          //임시 행렬에 탐색한 행렬 삽입
                matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
                matrix_print(matrix_temp2);
            }
            dot_count = dot_alphabet.Dot_count_search(count);                       // 발견한 점자가 몇칸인지 파악
            int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
            matrix_temp = dot_alphabet.Matrix_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
            matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
            matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
        }

    }
    //숫자 탐색 함수
    public void Number_search(int count){

        if(number_check==false){ // 숫자가 처음 나타난 것이라면
            //숫자 앞에 수표를 붙임
            dot_count = dot_number.Dot_count_search(0);                       // 발견한 점자가 몇칸인지 파악
            int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
            matrix_temp = dot_number.Matrix_search(0, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
            matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
            matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
            number_check = true;

            Number_search(count);

        }
        else if(number_check==true){
            dot_count = dot_number.Dot_count_search(count);                       // 발견한 점자가 몇칸인지 파악
            int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
            matrix_temp = dot_number.Matrix_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
            matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
            matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
        }

    }

    //약자 및 약어 탐색 함수
    public void Abbreviation_search(int count){
        dot_count = dot_abbreviation.Dot_count_search(count);                       // 발견한 점자가 몇칸인지 파악
        int matrix_temp[][] = new int[3][dot_count * 2];                        //칸수에 맞게 행렬 선언
        matrix_temp = dot_abbreviation.Matrix_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
        matrix_count += dot_count * 2;                                       //삽입한 점자 칸 수만큼 행렬값 증가
        matrix_print(matrix_temp);                                                  //발견한 점자 행렬을 출력
    }


    //초성 탐색 함수
    public void Initial_search(int count){
        dot_count = dot_initial.Dot_count_search(count);
        int matrix_temp[][] = new int[3][dot_count * 2];                    //칸수에 맞게 행렬 선언
        matrix_temp = dot_initial.Matrix_search(count, matrix_temp);            //임시 행렬에 탐색한 행렬 삽입
        matrix_count += dot_count * 2;                                      //삽입한 점자 칸 수만큼 행렬값 증가
        matrix_print(matrix_temp);                                          //발견한 점자 행렬을 출력
    }


    //모음 탐색 함수
    public void Vowel_search(int count){
        dot_count = dot_vowel.Dot_count_search(count);
        int matrix_temp2[][] = new int[3][dot_count * 2];                   //칸수에 맞게 행렬 선언
        matrix_temp2 = dot_vowel.Matrix_search(count, matrix_temp2);        //임시 행렬에 탐색한 행렬 삽입
        matrix_count += dot_count * 2;                                  //삽입한 점자 칸 수만큼 행렬값 증가
        matrix_print(matrix_temp2);                                 //발견한 점자 행렬을 출력
    }


    //종성 탐색 함수
    public void Final_search(int count){
        dot_count = dot_final.Dot_count_search(count);
        int matrix_temp[][] = new int[3][dot_count * 2];                    //칸수에 맞게 행렬 선언
        matrix_temp = dot_final.Matrix_search(count, matrix_temp);          //임시 행렬에 탐색한 행렬 삽입
        matrix_count += dot_count * 2;                      //삽입한 점자 칸 수만큼 행렬값 증가
        matrix_print(matrix_temp);                          //발견한 점자 행렬을 출력
    }



    //초음 + 모음을 얻어오는 함수
    public String get_initial_vowel(String text, int final_code){
        text=Integer.toHexString((text.charAt(Text_number) & 0xFFFF) - final_code);
        text=UNI_change_KOR(text);
        return text;
    }


    //ㅇ + 모음 + 종성을 구하는 함수
    public String get_vowel_final(String text, int finalcode, int vowelcode){
        text = Integer.toHexString(0xAC00+((eng_code*21)+vowelcode)*28+finalcode);  //유니코드 변환 (초성)
        text=UNI_change_KOR(text);

        return text;
    }

    //유니코드로 변환하는 함수
    public String getUnicode(String text){
        text = Integer.toHexString(text.charAt(Text_number) & 0xFFFF);
        return text;
    }

    //초성을 얻어오는 함수
    public int get_initial(String text, int vowel_code, int final_code){
        int code=0;
        text = Integer.toHexString(((((text.charAt(Text_number) & 0xFFFF)- final_code - 0xAC00) / 28) - vowel_code) / 21);  //유니코드 변환 (초성)
        code = Numberchange(text);

        return code;
    }


    //모음을 얻어오는 함수
    public int get_vowel(String text, int final_code){
        int code=0;
        text = Integer.toHexString((((text.charAt(Text_number) & 0xFFFF)- final_code - 0xAC00) / 28) % 21);     //유니코드로 변환해줌 (모음)
        code = Numberchange(text);

        return code;
    }


    //종성을 얻어오는 함수
    public int get_final(String text){
        int code=0;
        text = Integer.toHexString(((text.charAt(Text_number) & 0xFFFF)-0xAC00) % 28);   //유니코드로 변환해줌 (종성)
        code = Numberchange(text);

        return code;
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
        Search_stop_number = 0; // 현재 글자위치 저장하는 변수 초기화
        abbrevion_check = 0; // 약자 및 약어 단어 길이 수 저장하는 변수 초기화
        abbreviaion_search_check = true; // 기존위치의 단어길이가 긴 약자 및 약어를 검사했었다고 체크
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
        Search_stop_number=0; // 약자 및 약어에서 글자수가 많은 약자 및 약어를 발견하였을 때, 일단 현재 글자 위치를 저장하는 변수 초기화
        abbreviaion_search_check = false; //현재 위치에서 약자 및 약어의 긴 글자를 확인했는지 저장하는 변수
        abbreviation_search_success = false; // 약자 및 약어에서 긴 글자를 탐색했는지 저장하는 변수
        abbrevion_check=0;
        number_check=false;
        alphabet_check=false;
        Text_number=0;
    }

    //음성출력을 위한 텍스트를 얻어오는 함수
    public String get_TTs_text(){
        int a=0;
        int b=0;
        int braille_count = matrix_count/2;
        switch(braille_count){
            case 1:
                TTs_text += ", 한칸";
                break;
            case 2:
                TTs_text += ", 두칸";
                break;
            case 3:
                TTs_text += ", 세칸";
                break;
            case 4:
                TTs_text += ", 네칸";
                break;
            case 5:
                TTs_text += ", 다섯칸";
                break;
            case 6:
                TTs_text += ", 여섯칸";
                break;
            case 7:
                TTs_text += ", 일곱칸";
                break;
        }
        for (int i=0; i<matrix_count; i++) {
            for (int j=0 ; j<3 ; j++) {
                if(matrix[j][i]==1) {
                    switch (j) {
                        case 0:
                            if (i % 2 == 0)
                                TTs_text += ", 1";
                            else
                                TTs_text += ", 4";
                            break;
                        case 1:
                            if (i % 2 == 0)
                                TTs_text += ", 2";
                            else
                                TTs_text += ", 5";
                            break;
                        case 2:
                            if (i % 2 == 0)
                                TTs_text += ", 3";
                            else
                                TTs_text += ", 6";
                            break;
                    }
                }

                if(j==2 && i%2!=0){
                    TTs_text += "점";
                }
            }
            a = 0;
            b++;
        }
        return TTs_text;
    }
}
