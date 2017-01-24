package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;

/**
 * Created by yoonc on 2016-08-10.
 */
public class dot_quiz_number {
    /*
     * 숫자 퀴즈에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
     */
    static public int numbercount = 16; // 숫자 퀴즈의 갯수
    int num_sign[][] = {{0,1},{0,1},{1,1}}; // 수표
    int zero[][] = {{0,1},{1,1},{0,0}}; // 0
    int one[][] = {{1,0},{0,0},{0,0}}; // 1
    int two[][] = {{1,0},{1,0},{0,0}}; // 2
    int three[][] = {{1,1},{0,0},{0,0}}; // 3
    int four[][] = {{1,1},{0,1},{0,0}}; // 4
    int five[][] = {{1,0},{0,1},{0,0}}; // 5
    int six[][] = {{1,1},{1,0},{0,0}}; // 6
    int seven[][] = {{1,1},{1,1},{0,0}}; // 7
    int eight[][] = {{1,0},{1,1},{0,0}}; // 8
    int nine[][] = {{0,1},{1,0},{0,0}}; //  9

    int ten[][] = {{1,0,0,1},{0,0,1,1},{0,0,0,0}}; // 10
    int twofive[][] = {{1,0,1,0},{1,0,0,1},{0,0,0,0}}; // 25
    int fourseven[][] = {{1,1,1,1},{0,1,1,1},{0,0,0,0}}; // 47
    int sixeight[][] = {{1,1,1,0},{1,0,1,1},{0,0,0,0}}; // 68
    int ninenine[][] = {{0,1,0,1},{1,0,1,0},{0,0,0,0}}; // 99

    public static String name [] ={ "수표","0","1","2","3","4","5","6","7","8","9","10","25","47","68","99"};
    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2}; // 몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> number_Array = new ArrayList<>(); // 점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> number_name = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> number_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public dot_quiz_number(){
        number_Array.add(num_sign);
        number_Array.add(zero);
        number_Array.add(one);
        number_Array.add(two);
        number_Array.add(three);
        number_Array.add(four);
        number_Array.add(five);
        number_Array.add(six);
        number_Array.add(seven);
        number_Array.add(eight);
        number_Array.add(nine);

        number_Array.add(ten);
        number_Array.add(twofive);
        number_Array.add(fourseven);
        number_Array.add(sixeight);
        number_Array.add(ninenine);

        for(int i = 0 ; i<numbercount ; i++){
            number_name.add(name[i]);
            number_dot_count.add(dot_counter[i]);
        }

    }
}
