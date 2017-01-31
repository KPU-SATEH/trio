package com.example.yeo.practice.Common_braille_translation;

import java.util.ArrayList;

/*
 * 숫자 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class Trans_dot_num {
    static public int num_count = 11;//숫자 연습의 갯수
    int num_sign[][] = {{0,1},{0,1},{1,1}}; //수표
    int zero[][] = {{0,1},{1,1},{0,0}}; //0
    int one[][] = {{1,0},{0,0},{0,0}}; //1
    int two[][] = {{1,0},{1,0},{0,0}}; //2
    int three[][] = {{1,1},{0,0},{0,0}}; //3
    int four[][] = {{1,1},{0,1},{0,0}}; //4
    int five[][] = {{1,0},{0,1},{0,0}}; //5
    int six[][] = {{1,1},{1,0},{0,0}}; //6
    int seven[][] = {{1,1},{1,1},{0,0}}; //7
    int eight[][] = {{1,0},{1,1},{0,0}}; //8
    int nine[][] = {{0,1},{1,0},{0,0}}; //9



    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={ "숫자","0","1","2","3","4","5","6","7","8","9"};
    public static ArrayList<int[][]> num_Array = new ArrayList<>();//점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> num_dot_count = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<String> num_name = new ArrayList<>();// 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public Trans_dot_num(){
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
        for(int i = 0 ; i<num_count ; i++){
            num_name.add(name[i]);
            num_dot_count.add(dot_counter[i]);
        }
    }

    public int Dot_count_search(int index){
        int count=0;
        count = num_dot_count.get(index);
        return count;
    }

    public int Name_search(String name){
        int index=0;
        boolean check = false;
        for(int i=0; i<num_name.size() ; i++){
            if(name.equals(num_name.get(i))==true){
                index=i;
                check = true;
                break;
            }
            else
                continue;
        }
        if(check == false)
            index = -1;
        check = false;
        return index;
    }

    public int[][] Matrix_search(int index, int[][] matrix){
        int count = num_dot_count.get(index);
        count = count *2;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<count ; j++){
                matrix[i][j]=num_Array.get(index)[i][j];
            }
        }

        return matrix;
    }
}
