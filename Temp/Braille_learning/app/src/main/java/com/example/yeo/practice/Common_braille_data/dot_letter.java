package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;

/**
 * Created by yoonc on 2016-08-05.
 */
/*
 * 글자 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_letter {
    static public int lettercount = 32;//글자 연습의 갯수

    int ham[][] = {{0,1,0,0},{1,1,1,0},{0,0,0,1}}; //함 2
    int kal[][] = {{1,1,0,0},{1,0,1,0},{0,0,0,0}}; //칼 3
    int ang[][] = {{1,0,0,0},{1,0,1,1},{0,1,1,1}}; //앙 4
    int hwa[][] = {{0,1,1,0},{1,1,1,0},{0,0,1,1}}; //화 5
    int dae[][] = {{0,1,1,0},{1,0,1,1},{0,0,1,0}}; //대 6
    int han[][]= {{0,1,0,0},{1,1,1,1},{0,0,0,0}}; //한 7
    int gang[][] = {{1,1,0,0},{1,0,1,1},{0,1,1,1}}; //강 8
    int nam[][] = {{1,1,0,0},{0,0,1,0},{0,0,0,1}}; //남 9
    int s[][] = {{0,0,0,1},{0,0,1,0},{0,1,0,1}}; //스 10
    int il[][] = {{1,0,0,0},{0,1,1,0},{1,0,0,0}}; //일 11
    int jak[][] = {{0,1,1,0},{0,0,0,0},{0,1,0,0}}; //작 12
    int eom[][] = {{0,1,0,0},{1,0,1,0},{1,0,0,1}}; //엄 13
    int bba[][] = {{0,0,0,1},{0,0,0,1},{0,1,0,0}}; //빠 14
    int sam[][] = {{1,0,0,0},{1,0,1,0},{1,0,0,1}}; //삼 15
    int gu[][] = {{0,1,1,1},{0,0,0,0},{0,0,1,0}}; //구 16
    int bak[][] = {{0,1,1,0},{0,1,0,0},{0,0,0,0}}; //박 17
    int su[][] = {{0,0,1,1},{0,0,0,0},{0,1,1,0}}; //수 18
    int dok[][] = {{0,1,1,1},{1,0,0,0},{0,0,1,1}}; //독 19
    int ri[][] = {{0,0,1,0},{0,1,0,1},{0,0,1,0}}; //리 20
    int gi[][] = {{0,1,1,0},{0,0,0,1},{0,0,1,0}}; //기 21
    int si[][] = {{0,0,1,0},{0,0,0,1},{0,1,1,0}}; //시 22
    int gak[][] = {{1,1,1,0},{1,0,0,0},{0,1,0,0}}; //각 23
    int jang[][] = {{0,1,0,0},{0,0,1,1},{0,1,1,1}}; //장 24
    int hak[][] = {{0,1,1,0},{1,1,0,0},{0,0,0,0}}; //학 25
    int gyo[][] = {{0,1,0,1},{0,0,0,0},{0,0,1,1}}; //교 26
    int sil[][] = {{1,0,0,0},{0,1,1,0},{1,0,0,0}}; //실 27
    int chui[][] = {{0,0,1,1,1,0},{0,1,0,0,1,1},{0,1,1,0,1,0}};//취 1
    int big [][]= {{0,1,1,0,1,0},{0,1,0,1,0,0},{0,0,1,0,0,0}};//빅 2
    int ryun[][] = {{0,0,1,1,0,0},{0,1,0,0,1,1},{0,0,0,1,0,0}};//륜 3
    int gue[][] = {{0,1,1,1,1,0},{0,0,1,0,1,1},{0,0,1,0,1,0}};//궤 4
    int min[][] = {{1,0,1,0,0,0},{0,1,0,1,1,1},{0,0,1,0,0,0}};//민 5
    int kuk[][] = {{0,1,1,1,1,0},{0,0,0,0,0,0},{0,0,1,0,0,0}};//국 6

    public int dot_counter[] = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={ "함","칼","앙","화","대","한","강","남","스","일","작","엄","빠","삼","구","박","수","독","리","기","시","각","장","학",
            "교","실", "취","빅","륜","궤","민","국"}; // 글자 이름
    public static ArrayList<int[][]> letter_Array = new ArrayList<>();//점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> letter_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public static ArrayList<String> letter_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트


    public dot_letter(){

        letter_Array.add(ham);
        letter_Array.add(kal);
        letter_Array.add(ang);
        letter_Array.add(hwa);
        letter_Array.add(dae);
        letter_Array.add(han);
        letter_Array.add(gang);
        letter_Array.add(nam);
        letter_Array.add(s);
        letter_Array.add(il);
        letter_Array.add(jak);
        letter_Array.add(eom);
        letter_Array.add(bba);
        letter_Array.add(sam);
        letter_Array.add(gu);
        letter_Array.add(bak);
        letter_Array.add(su);
        letter_Array.add(dok);
        letter_Array.add(ri);
        letter_Array.add(gi);
        letter_Array.add(si);
        letter_Array.add(gak);
        letter_Array.add(jang);
        letter_Array.add(hak);
        letter_Array.add(gyo);
        letter_Array.add(sil);

        letter_Array.add(chui);
        letter_Array.add(big);
        letter_Array.add(ryun);
        letter_Array.add(gue);
        letter_Array.add(min);
        letter_Array.add(kuk);

        for(int i = 0 ; i<lettercount ; i++){
            letter_name.add(name[i]);
            letter_dot_count.add(dot_counter[i]);
        }
    }
}
