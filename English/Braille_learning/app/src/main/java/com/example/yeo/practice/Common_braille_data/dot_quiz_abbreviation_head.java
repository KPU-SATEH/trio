package com.example.yeo.practice.Common_braille_data;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */

/*
 * 알파벳 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_quiz_abbreviation_head {
    static public int abbreviation_head_count = 33; // 알파벳 연습의 갯수
    int day[][] = {{0,0,1,1},{0,1,0,1},{0,0,0,0}};
    int ever[][] = {{0,0,1,0},{0,1,0,1},{0,0,0,0}};
    int father[][] = {{0,0,1,1},{0,1,1,0},{0,0,0,0}};
    int here[][] = {{0,0,1,0},{0,1,1,1},{0,0,0,0}};
    int know[][] = {{0,0,1,0},{0,1,0,0},{0,1,0,0}};
    int lord[][] = {{0,0,1,0},{0,1,1,0},{0,0,1,0}};
    int mother[][] = {{0,0,1,1},{0,1,0,0},{0,0,1,0}};
    int under[][] = {{0,0,1,0},{0,1,0,0},{0,0,1,1}};
    int name_[][] = {{0,0,1,1},{0,1,0,1},{0,0,1,0}};
    int one[][] = {{0,0,1,0},{0,1,0,1},{0,0,1,0}};
    int part[][] = {{0,0,1,1},{0,1,1,0},{0,0,1,0}};
    int question[][] = {{0,0,1,1},{0,1,1,1},{0,0,1,0}};
    int right[][] = {{0,0,1,0},{0,1,1,1},{0,0,1,0}};
    int some[][] = {{0,0,0,1},{0,1,1,0},{0,0,1,0}};
    int time[][] = {{0,0,0,1},{0,1,1,1},{0,0,1,0}};
    int work[][] = {{0,0,0,1},{0,1,1,1},{0,0,0,1}};
    int young[][] = {{0,0,1,1},{0,1,0,1},{0,0,1,1}};
    int there[][] = {{0,0,0,1},{0,1,1,0},{0,0,1,1}};
    int character[][] = {{0,0,1,0},{0,1,0,0},{0,0,0,1}};
    int through[][] = {{0,0,1,1},{0,1,0,1},{0,0,0,1}};
    int where[][] = {{0,0,1,0},{0,1,0,1},{0,0,0,1}};
    int ought[][] = {{0,0,1,0},{0,1,1,1},{0,0,0,1}};

    int upon[][] = {{0,1,1,0},{0,1,0,0},{0,0,1,1}};
    int word[][] = {{0,1,0,1},{0,1,1,1},{0,0,0,1}};
    int these[][] = {{0,1,0,1},{0,1,1,0},{0,0,1,1}};
    int those[][] = {{0,1,1,1},{0,1,0,1},{0,0,0,1}};
    int whose[][] = {{0,1,1,0},{0,1,0,1},{0,0,0,1}};

    int cannot[][] = {{0,1,1,1},{0,1,0,0},{0,1,0,0}};
    int had[][] = {{0,1,1,0},{0,1,0,0},{0,1,0,0}};
    int many[][] = {{0,1,1,1},{0,1,0,0},{0,1,1,0}};
    int spirit[][] = {{0,1,0,1},{0,1,1,0},{0,1,1,0}};
    int world[][] = {{0,1,0,1},{0,1,1,1},{0,1,0,1}};
    int their[][] = {{0,1,0,1},{0,1,1,0},{0,1,1,1}};

    public int dot_counter[]={2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> abbreviation_head_Array = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> abbreviation_head_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public static ArrayList<String> abbreviation_head_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트

    public static String name [] ={"day", "ever", "father", "here", "know", "lord", "mother", "under", "name_", "one", "part", "question", "right", "some", "time",
            "work", "young", "there", "character", "through", "where", "ought", "upon", "word", "these", "those", "whose", "cannot", "had", "many", "spirit", "world", "their" };

    public dot_quiz_abbreviation_head(){
        abbreviation_head_Array.add(day);
        abbreviation_head_Array.add(ever);
        abbreviation_head_Array.add(father);
        abbreviation_head_Array.add(here);
        abbreviation_head_Array.add(know);
        abbreviation_head_Array.add(lord);
        abbreviation_head_Array.add(mother);
        abbreviation_head_Array.add(under);
        abbreviation_head_Array.add(name_);
        abbreviation_head_Array.add(one);
        abbreviation_head_Array.add(part);
        abbreviation_head_Array.add(question);
        abbreviation_head_Array.add(right);
        abbreviation_head_Array.add(some);
        abbreviation_head_Array.add(time);
        abbreviation_head_Array.add(work);
        abbreviation_head_Array.add(young);
        abbreviation_head_Array.add(there);
        abbreviation_head_Array.add(character);
        abbreviation_head_Array.add(through);
        abbreviation_head_Array.add(where);
        abbreviation_head_Array.add(ought);
        abbreviation_head_Array.add(upon);
        abbreviation_head_Array.add(word);
        abbreviation_head_Array.add(these);
        abbreviation_head_Array.add(those);
        abbreviation_head_Array.add(whose);
        abbreviation_head_Array.add(cannot);
        abbreviation_head_Array.add(had);
        abbreviation_head_Array.add(many);
        abbreviation_head_Array.add(spirit);
        abbreviation_head_Array.add(world);
        abbreviation_head_Array.add(their);

        for(int i = 0 ; i<abbreviation_head_count ; i++){
            abbreviation_head_name.add(name[i]);
            abbreviation_head_dot_count.add(dot_counter[i]);
        }
    }
}
