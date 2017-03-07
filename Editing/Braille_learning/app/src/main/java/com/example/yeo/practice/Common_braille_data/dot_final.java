package com.example.yeo.practice.Common_braille_data;


import java.util.ArrayList;
/*
 * 종성 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_final {
    static public int final_count = 14; //종성 연습의 갯수
    int giyeok_[][] = {{1,0},{0,0},{0,0}}; //종성 기억
    int nieun_[][] = {{0,0},{1,1},{0,0}}; //종성 니은
    int digeud_[][] = {{0,0},{0,1},{1,0}}; //종성 디귿
    int nieul_[][] = {{0,0},{1,0},{0,0}}; //종성 리을
    int mieum_[][] = {{0,0},{1,0},{0,1}}; // 종성 미음
    int bieub_[][] = {{1,0},{1,0},{0,0}}; // 종성 비읍
    int siot_[][] = {{0,0},{0,0},{1,0}}; // 종성 시옷
    int eng_[][] = {{0,0},{1,1},{1,1}}; //종성 이응
    int zieut_[][] = {{1,0},{0,0},{1,0}}; //종성 지읒
    int chieut_[][] = {{0,0},{1,0},{1,0}}; //종성 치읓
    int kieuk_[][] = {{0,0},{1,1},{1,0}}; //종성 키읔
    int tieut_[][] = {{0,0},{1,0},{1,1}}; //종성 티읕
    int pieup_[][] = {{0,0},{1,1},{0,1}}; // 종성 피읖
    int hieut_[][] = {{0,0},{0,1},{1,1}}; // 종성 히흫

    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={ "ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ", "ㅎ"};
    public static ArrayList<int[][]> final_Array = new ArrayList<>(); //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> final_name = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> final_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트

    public dot_final(){
        final_Array.add(giyeok_);
        final_Array.add(nieun_);
        final_Array.add(digeud_);
        final_Array.add(nieul_);
        final_Array.add(mieum_);
        final_Array.add(bieub_);
        final_Array.add(siot_);
        final_Array.add(eng_);
        final_Array.add(zieut_);
        final_Array.add(chieut_);
        final_Array.add(kieuk_);
        final_Array.add(tieut_);
        final_Array.add(pieup_);
        final_Array.add(hieut_);

        for(int i = 0 ; i<final_count ; i++){
            final_name.add(name[i]);
            final_dot_count.add(dot_counter[i]);
        }
    }
}
