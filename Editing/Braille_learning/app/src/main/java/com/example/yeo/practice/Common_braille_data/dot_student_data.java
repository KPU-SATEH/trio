package com.example.yeo.practice.Common_braille_data;

import java.util.ArrayList;

/**
 * Created by 여명 on 2017-01-25.
 */

public class dot_student_data {
    /*
   * 학생모드에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
   */
    public static int dot_count = 0;
    public static int max=0;
    public static ArrayList<int[][]> student_Array = new ArrayList<>(); // 점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> student_letter = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> count_Array = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public dot_student_data(){
    }

    public void input_data(int[][] Braille_insert, String letter){
        student_Array.add(Braille_insert);
        student_letter.add(letter);
        dot_count++;
    }
    public void input_count(int count){
        count_Array.add(count);
    }
    public void delete_data(){
        student_letter.clear();
        student_Array.clear();
        count_Array.clear();
        dot_count=0;
    }
}
