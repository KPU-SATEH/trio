package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;

/**
 * Created by yoonc on 2016-08-10.
 */
public class dot_quiz_initial {
    /*
     * 초성 퀴즈에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
     */
    static public int Initialcount=19; // 초성 퀴즈의 갯수
    int giyeok[][] = {{0,1},{0,0},{0,0}}; // 초성 기억
    int nieun[][] = {{1,1},{0,0},{0,0}}; // 초성 니은
    int digeud[][] = {{0,1},{1,0},{0,0}}; // 초성 디귿
    int nieul[][] = {{0,0},{0,1},{0,0}}; // 초성 리을
    int mieum[][] = {{1,0},{0,1},{0,0}}; // 초성 미음
    int bieub[][] = {{0,1},{0,1},{0,0}}; // 초성 비읍
    int siot[][] = {{0,0},{0,0},{0,1}}; // 초성 시옷
    int zieut[][] = {{0,1},{0,0},{0,1}}; // 초성 지읒
    int chieut[][] = {{0,0},{0,1},{0,1}}; // 초성 치읓
    int kieuk[][] = {{1,1},{1,0},{0,0}}; // 초성 키읔
    int tieut[][] = {{1,0},{1,1},{0,0}}; // 초성 티읕
    int pieup[][] = {{1,1},{0,1},{0,0}}; // 초성 피읖
    int hieut[][] = {{0,1},{1,1},{0,0}}; // 초성 히흫
    int fortis[][] = {{0,0},{0,0},{0,1}}; // 된소리표
    int fortis_giyeok[][] = {{0,0,0,1},{0,0,0,0},{0,1,0,0}}; // 초성 쌍기억
    int fortis_digeud[][] = {{0,0,0,1},{0,0,1,0},{0,1,0,0}}; // 초성 쌍디귿
    int fortis_bieub[][] = {{0,0,0,1},{0,0,0,1},{0,1,0,0}}; // 초성 쌍비읍
    int fortis_siot[][] = {{0,0,0,0},{0,0,0,0},{0,1,0,1}}; // 초성 쌍시옷
    int fortis_zieut[][] = {{0,0,0,1},{0,0,0,0},{0,1,0,1}}; // 초성 쌍지읒

    public static String name [] ={ "기역","니은","디귿","리을","미음","비읍","시옷","지읒","치읓","키읔","티읕","피읖","히읗","된소리표","쌍기억","쌍디귿","쌍비읍","쌍시옷","쌍지읒"};
    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2}; // 몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> Initial_Array = new ArrayList<>(); // 점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> Initial_name = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> Initial_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public dot_quiz_initial(){
        Initial_Array.add(giyeok);
        Initial_Array.add(nieun);
        Initial_Array.add(digeud);
        Initial_Array.add(nieul);
        Initial_Array.add(mieum);
        Initial_Array.add(bieub);
        Initial_Array.add(siot);
        Initial_Array.add(zieut);
        Initial_Array.add(chieut);
        Initial_Array.add(kieuk);
        Initial_Array.add(tieut);
        Initial_Array.add(pieup);
        Initial_Array.add(hieut);
        Initial_Array.add(fortis);
        Initial_Array.add(fortis_giyeok);
        Initial_Array.add(fortis_digeud);
        Initial_Array.add(fortis_bieub);
        Initial_Array.add(fortis_siot);
        Initial_Array.add(fortis_zieut);

        for(int i = 0 ; i<Initialcount ; i++){
            Initial_name.add(name[i]);
            Initial_dot_count.add(dot_counter[i]);
        }

    }
}
