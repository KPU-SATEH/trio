package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;

/**
 * Created by yoonc on 2016-08-05.
 */
public class dot_quiz_word {
    /*
* 단어 퀴즈에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
*/
    static public int wordcount = 19; // 단어 퀴즈의 갯수


    int toilet[][]={{0,1,1,0,0,1,0,0,0,0,1,0,0,0},{1,1,1,0,0,0,1,1,0,0,0,1,1,0},{0,0,1,1,0,1,1,1,0,1,1,0,0,0}}; //화장실
    int love[][]={{1,0,0,0,1,0,0,0},{1,0,0,1,1,0,1,1},{1,0,0,0,0,1,1,1}}; //사랑
    int exit[][]={{0,0,1,1,0,1,1,1},{0,1,1,0,0,0,0,0},{0,1,1,1,0,0,1,0}}; //출구
    int enterance[][] = {{1,0,1,0,0,1,1,1},{0,1,1,0,0,0,0,0},{1,0,0,0,0,0,1,0}}; //입구
    int subway[][]={{0,1,1,0,0,1,0,0,0,1},{0,0,0,1,1,1,0,1,1,1},{0,1,1,0,0,0,0,1,1,0}}; //지하철
    int korea[][]={{0,1,0,0,0,1,1,1,1,0},{1,1,1,1,0,0,0,0,0,0},{0,0,0,0,0,0,1,0,0,0}}; //한국
    int computer[][]={{1,1,0,1,0,0,1,1,1,1,1,0,0,1},{1,0,1,0,1,0,0,1,0,0,1,1,1,0},{0,0,1,0,0,1,0,0,0,1,0,0,1,0}}; //컴퓨터
    int dot[][]={{0,1,0,1,0,0,0,1},{0,0,1,0,1,0,0,0},{0,1,1,0,0,1,0,1}};//점자
    int mom[][]={{0,1,0,0,1,0},{1,0,1,0,0,1},{1,0,0,1,0,0}}; //엄마
    int dad[][]={{1,0,0,0,0,1},{1,0,0,0,0,1},{0,1,0,1,0,0}}; //아빠
    int seoul[][]={{0,0,0,1,1,1},{0,0,1,0,1,0},{0,1,1,0,1,1}};//서울
    int right[][]={{1,0,0,0,1,0,0,0,0,1,1,1},{0,0,0,1,0,1,0,0,0,0,0,0},{1,1,0,0,1,1,0,1,0,1,1,1}};//오른쪽
    int left[][]={{1,1,0,0,0,0,0,1,1,1},{0,1,1,1,0,0,0,0,0,0},{1,1,0,0,0,1,0,1,1,1}}; //왼쪽
    int direction[][]={{0,1,0,0,0,1,0,1,0,0},{0,1,1,1,1,1,0,1,1,1},{0,0,1,1,0,0,1,0,1,1}};//방향
    int seat[][]={{0,1,1,0,0,0,1,1},{0,0,1,0,0,0,0,1},{0,1,1,1,0,1,0,1}};//좌석
    int smartphone[][]={{0,0,0,1,1,0,1,0,0,1,1,1,1,0},{0,0,1,0,0,1,1,1,1,0,0,1,1,1},{0,1,0,1,0,0,0,0,0,1,0,0,1,1}};//스마트폰
    int stair[][]={{0,1,0,1,0,1,0,0},{0,0,0,0,1,0,1,1},{0,0,1,0,0,0,0,0}};//계단
    int handphone[][]={{0,1,1,0,0,0,0,1,0,1,1,1,1,0},{1,1,1,1,1,1,1,0,1,0,0,1,1,1},{0,0,1,0,0,0,0,0,0,1,0,0,1,1}};//핸드폰
    int bookstore[][]={{0,0,0,1,0,1,0,1,0,0},{0,0,1,0,0,0,1,0,1,0},{0,1,1,0,0,1,1,0,0,1}}; //서점



    public int dot_counter[] = {7,4,4,4,5,5,7,4,3,3,3,6,5,5,4,7,4,7,5}; // 몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public String name [] ={"화장실","사랑","출구","입구","지하철","한국","컴퓨터","점자","엄마","아빠","서울","오른쪽","왼쪽","방향","좌석","스마트폰","계단","핸드폰","서점"};
    public static ArrayList<int[][]> word_Array = new ArrayList<>(); // 점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> word_dot_count = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<String> word_name = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트


    public dot_quiz_word(){
        word_Array.add(toilet);
        word_Array.add(love);
        word_Array.add(exit);
        word_Array.add(enterance);
        word_Array.add(subway);
        word_Array.add(korea);
        word_Array.add(computer);
        word_Array.add(dot);
        word_Array.add(mom);
        word_Array.add(dad);
        word_Array.add(seoul);
        word_Array.add(right);
        word_Array.add(left);
        word_Array.add(direction);
        word_Array.add(seat);
        word_Array.add(smartphone);
        word_Array.add(stair);
        word_Array.add(handphone);
        word_Array.add(bookstore);


        for(int i = 0 ; i<wordcount ; i++){
            word_name.add(name[i]);
            word_dot_count.add(dot_counter[i]);
        }
    }
}
