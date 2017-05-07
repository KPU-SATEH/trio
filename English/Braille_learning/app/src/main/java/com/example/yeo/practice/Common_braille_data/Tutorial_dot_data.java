package com.example.yeo.practice.Common_braille_data;


import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-25.
 */
public class Tutorial_dot_data {
        /*
* 사용설명서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
*/

    public static int Initial_count = 2; // 사용설명서 점자 연습의 갯수
    public int giyeok[][] = {{0,1},{0,0},{0,0}}; // 초성 기억
    public int fortis_giyeok[][] = {{0,0,0,1},{0,0,0,0},{0,1,0,0}}; // 초성 쌍기억

    public int dot_counter[] = {1,2}; // 몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={"ㄱ","ㄲ"};
    public static ArrayList<int[][]> Initial_Array = new ArrayList<>(); // 점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> Initial_name = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> Initial_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public Tutorial_dot_data(){
        Initial_Array.add(giyeok);
        Initial_Array.add(fortis_giyeok);


        for(int i = 0 ; i<Initial_count ; i++) {
            Initial_name.add(name[i]);
            Initial_dot_count.add(dot_counter[i]);
        }
    }
}
