package com.example.yeo.practice.Common_braille_data;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */

/*
 * 알파벳 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_quiz_sentence {
    static public int sen_count = 21; // 알파벳 연습의 갯수
    int period[][] = {{0,0},{1,1},{0,1}}; //.
    int question[][] = {{0,0},{1,0},{1,1}}; //?
    int exclamation[][] = {{0,0},{1,1},{1,0}}; //!
    int comma[][] = {{0,0},{1,0},{0,0}}; //,
    int bilateral[][] = {{0,0},{1,1},{0,0}}; //:
    int semicolon[][] = {{0,0},{1,0},{1,0}}; //;
    int deviant[][] = {{0,1},{0,0},{1,0}}; ///
    int d_quotation_open[][] = {{0,0},{1,0},{1,1}}; //“
    int d_quotation_close[][] = {{0,0},{0,1},{1,1}}; //”
    int quotation_open[][] = {{0,0,0,0},{0,0,1,0},{0,1,1,1}}; //‘
    int quotation_close[][] = {{0,0,0,0},{0,1,0,0},{1,1,1,0}}; //’
    int paretheses[][] = {{0,0},{1,1},{1,1}}; //()
    int bracket_open[][] = {{0,0,0,0},{0,0,1,1},{0,1,1,1}}; //[
    int bracket_close[][] = {{0,0,0,0},{1,1,0,0},{1,1,1,0}}; //]
    int dash[][] = {{0,0,0,0},{0,0,0,0},{1,1,1,1}}; //?
    int ellipsis[][] = {{0,0,0,0,0,0},{0,0,0,0,0,0},{1,0,1,0,1,0}}; //······
    int star[][] = {{0,0,0,0},{0,1,0,1},{1,0,1,0}}; //*
    int apostrophe[][] = {{0,0},{0,0},{1,0}}; //'
    int capital[][] = {{0,0},{0,0},{0,1}}; //대문자 기호
    int italic[][] = {{0,1},{0,0},{0,1}}; //이탤릭 기호
    int spelling[][] = {{0,0},{0,1},{0,1}}; //철자 기호

    public int dot_counter[]={1,1,1,1,1,1,1,1,1,2,2,1,2,2,2,3,2,1,1,1,1}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> sentence_Array  = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> sentence_dot_count    = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public static ArrayList<String> sentence_name   = new ArrayList<>();//점자의 글자를 저장하는 연결리스트

    public static String name [] ={".","?","!",",",":",";","/","“","”","‘","’","()","[","]","--","··", "*","'","Capital Sign", "Italic Sign", "Spelling"};

    public dot_quiz_sentence(){
        sentence_Array.add(period);
        sentence_Array.add(question);
        sentence_Array.add(exclamation);
        sentence_Array.add(comma);
        sentence_Array.add(bilateral);
        sentence_Array.add(semicolon);
        sentence_Array.add(deviant);
        sentence_Array.add(d_quotation_open);
        sentence_Array.add(d_quotation_close);
        sentence_Array.add(quotation_open);
        sentence_Array.add(quotation_close);
        sentence_Array.add(paretheses);
        sentence_Array.add(bracket_open);
        sentence_Array.add(bracket_close);
        sentence_Array.add(dash);
        sentence_Array.add(ellipsis);
        sentence_Array.add(star);
        sentence_Array.add(apostrophe);
        sentence_Array.add(capital);
        sentence_Array.add(italic);
        sentence_Array.add(spelling);

        for(int i = 0 ; i<sen_count ; i++){
            sentence_name.add(name[i]);
            sentence_dot_count.add(dot_counter[i]);
        }
    }
}
