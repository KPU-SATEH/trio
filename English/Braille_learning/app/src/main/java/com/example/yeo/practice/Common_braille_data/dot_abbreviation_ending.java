package com.example.yeo.practice.Common_braille_data;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */

/*
 * 알파벳 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_abbreviation_ending {
    static public int abbreviation_ending_count = 14; // 알파벳 연습의 갯수
    int ound[][] = {{0,1,1,1},{0,0,0,1},{0,1,0,0}};
    int ance[][] = {{0,1,1,0},{0,0,0,1},{0,1,0,0}};
    int sion[][] = {{0,1,1,1},{0,0,0,1},{0,1,1,0}};
    int less[][] = {{0,1,0,1},{0,0,1,0},{0,1,1,0}};
    int ount[][] = {{0,1,0,1},{0,0,1,1},{0,1,1,0}};
    int ence[][] = {{0,0,1,0},{0,1,0,1},{0,1,0,0}};
    int ong[][] = {{0,0,1,1},{0,1,1,1},{0,1,0,0}};
    int ness[][] = {{0,0,0,1},{0,1,1,0},{0,1,1,0}};
    int ful[][] = {{0,0,1,0},{0,1,1,0},{0,1,1,0}};
    int tion[][] = {{0,0,1,1},{0,1,0,1},{0,1,1,0}};
    int ment[][] = {{0,0,0,1},{0,1,1,1},{0,1,1,0}};
    int ity[][] = {{0,0,1,1},{0,1,0,1},{0,1,1,1}};
    int ation[][] = {{0,0,1,1},{0,0,0,1},{0,1,1,0}};
    int ally[][] = {{0,0,1,1},{0,0,0,1},{0,1,1,1}};


    public int dot_counter[]={2,2,2,2,2,2,2,2,2,2,2,2,2,2}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> abbreviation_ending_Array = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> abbreviation_ending_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public static ArrayList<String> abbreviation_ending_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트

    public static String name [] ={"ound", "ance", "sion", "less", "ount", "ence", "ong", "ness", "ful", "tion", "ment", "ity", "ation", "ally"};

    public dot_abbreviation_ending(){
        abbreviation_ending_Array.add(ound);
        abbreviation_ending_Array.add(ance);
        abbreviation_ending_Array.add(sion);
        abbreviation_ending_Array.add(less);
        abbreviation_ending_Array.add(ount);
        abbreviation_ending_Array.add(ence);
        abbreviation_ending_Array.add(ong);
        abbreviation_ending_Array.add(ness);
        abbreviation_ending_Array.add(ful);
        abbreviation_ending_Array.add(tion);
        abbreviation_ending_Array.add(ment);
        abbreviation_ending_Array.add(ity);
        abbreviation_ending_Array.add(ation);
        abbreviation_ending_Array.add(ally);



        for(int i = 0 ; i<abbreviation_ending_count ; i++){
            abbreviation_ending_name.add(name[i]);
            abbreviation_ending_dot_count.add(dot_counter[i]);
        }
    }
}
