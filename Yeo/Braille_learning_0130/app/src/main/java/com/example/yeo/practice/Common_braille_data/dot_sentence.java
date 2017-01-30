package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-28.
 */
public class dot_sentence{
    /*
* 문장부호 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
*/
    static public int sentence_count = 18; // 문장부호 연습의 갯수
    int ssangopen[][] = {{0,0},{1,0},{1,1}};// "
    int ssangclose[][] = {{0,0},{0,1},{1,1}};// "
    int gualhoopen[][] = {{0,0},{0,0},{1,1}};// (
    int gualhoclose[][] = {{0,0},{0,0},{1,1}};// )
    int surprise[][] = {{0,0},{1,1},{1,0}};// !
    int finish_dot[][] = {{0,0},{1,1},{0,1}};// .
    int rest_dot[][] = {{0,0},{0,1},{0,0}};// ,
    int plus[][] = {{0,0},{1,0},{0,1}};// +
    int minus[][] = {{0,0},{0,1},{1,0}};// -
    int multiple[][] = {{1,0},{0,0},{0,1}};// *

    int divide[][] = {{0,1,0,0},{1,0,0,1},{0,0,1,0}};// /
    int equal[][] = {{0,0,1,1},{0,0,0,0},{1,1,0,0}};// =
    int sangopen[][] = {{0,0,0,0},{0,1,0,0},{1,0,1,1}};// '
    int sangclose[][] = {{0,0,0,1},{1,1,0,0},{0,0,0,1}};// '
    int wave[][] = {{0,0,0,0},{1,1,0,0},{0,0,1,1}};// ~
    int twodot[][] = {{0,0,0,1},{0,0,0,0},{1,0,0,0}};// :
    int sweat[][] = {{0,0,0,1},{0,1,0,0},{1,0,1,0}};// ;
    int billiboard[][] = {{0,0,0,1},{1,0,0,0},{0,1,1,0}};// ;


    int dot_counter[]={1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2}; // 몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수

    public static String name [] ={ "“","”","(",")","!",".",",","+","-","*","/","=","'","'","~",":",";","※"};
    public static ArrayList<int[][]> sentence_Array = new ArrayList<>(); // 점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> sentence_name = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> sentence_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트

    public dot_sentence(){
        sentence_Array.add(ssangopen);
        sentence_Array.add(ssangclose);
        sentence_Array.add(gualhoopen);
        sentence_Array.add(gualhoclose);
        sentence_Array.add(surprise);
        sentence_Array.add(finish_dot);
        sentence_Array.add(rest_dot);
        sentence_Array.add(plus);
        sentence_Array.add(minus);
        sentence_Array.add(multiple);
        sentence_Array.add(divide);
        sentence_Array.add(equal);
        sentence_Array.add(sangopen);
        sentence_Array.add(sangclose);
        sentence_Array.add(wave);
        sentence_Array.add(twodot);
        sentence_Array.add(sweat);
        sentence_Array.add(billiboard);

        for(int i = 0 ; i<sentence_count ; i++){
            sentence_name.add(name[i]);
            sentence_dot_count.add(dot_counter[i]);
        }
    }
}
