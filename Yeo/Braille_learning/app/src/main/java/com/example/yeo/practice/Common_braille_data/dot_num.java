package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;
/*
 * 숫자 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_num {
    static public int num_count = 16;//숫자 연습의 갯수
    int num_sign[][] = {{0,1},{0,1},{1,1}}; //수표
    int zero[][] = {{0,1,0,1},{0,1,1,1},{1,1,0,0}}; //0
    int one[][] = {{0,1,1,0},{0,1,0,0},{1,1,0,0}}; //1
    int two[][] = {{0,1,1,0},{0,1,1,0},{1,1,0,0}}; //2
    int three[][] = {{0,1,1,1},{0,1,0,0},{1,1,0,0}}; //3
    int four[][] = {{0,1,1,1},{0,1,0,1},{1,1,0,0}}; //4
    int five[][] = {{0,1,1,0},{0,1,0,1},{1,1,0,0}}; //5
    int six[][] = {{0,1,1,1},{0,1,1,0},{1,1,0,0}}; //6
    int seven[][] = {{0,1,1,1},{0,1,1,1},{1,1,0,0}}; //7
    int eight[][] = {{0,1,1,0},{0,1,1,1},{1,1,0,0}}; //8
    int nine[][] = {{0,1,0,1},{0,1,1,0},{1,1,0,0}}; //9

    int ten[][] = {{0,1,1,0,0,1},{0,1,0,0,1,1},{1,1,0,0,0,0}}; //10
    int twofive[][] = {{0,1,1,0,1,0},{0,1,1,0,0,1},{1,1,0,0,0,0}}; //25
    int fourseven[][] = {{0,1,1,1,1,1},{0,1,0,1,1,1},{1,1,0,0,0,0}}; //47
    int sixeight[][] = {{0,1,1,1,1,0},{0,1,1,0,1,1},{1,1,0,0,0,0}}; //68
    int ninenine[][] = {{0,1,0,1,0,1},{0,1,1,0,1,0},{1,1,0,0,0,0}}; //99

    public int dot_counter[]={1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={ "수표","0","1","2","3","4","5","6","7","8","9","10","25","47","68", "99"};
    public static ArrayList<int[][]> num_Array = new ArrayList<>();//점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> num_dot_count = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<String> num_name = new ArrayList<>();// 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public dot_num(){
        num_Array.add(num_sign);
        num_Array.add(zero);
        num_Array.add(one);
        num_Array.add(two);
        num_Array.add(three);
        num_Array.add(four);
        num_Array.add(five);
        num_Array.add(six);
        num_Array.add(seven);
        num_Array.add(eight);
        num_Array.add(nine);
        num_Array.add(ten);
        num_Array.add(twofive);
        num_Array.add(fourseven);
        num_Array.add(sixeight);
        num_Array.add(ninenine);

        for(int i = 0 ; i<num_count ; i++){
            num_name.add(name[i]);
            num_dot_count.add(dot_counter[i]);

        }

    }
}
