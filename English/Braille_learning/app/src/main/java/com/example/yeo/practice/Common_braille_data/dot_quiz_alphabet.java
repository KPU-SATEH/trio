package com.example.yeo.practice.Common_braille_data;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */

/*
 * 알파벳 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_quiz_alphabet {
    static public int alphabet_count = 52; // 알파벳 연습의 갯수
    int a[][] = {{1,0}, {0,0}, {0,0}};
    int b[][] = {{1,0}, {1,0}, {0,0}};
    int c[][] = {{1,1}, {0,0}, {0,0}};
    int d[][] = {{1,1}, {0,1}, {0,0}};
    int e[][] = {{1,0}, {0,1}, {0,0}};
    int f[][] = {{1,1}, {1,0}, {0,0}};
    int g[][] = {{1,1}, {1,1}, {0,0}};
    int h[][] = {{1,0}, {1,1}, {0,0}};
    int i[][] = {{0,1}, {1,0}, {0,0}};
    int j[][] = {{0,1}, {1,1}, {0,0}};
    int k[][] = {{1,0}, {0,0}, {1,0}};
    int l[][] = {{1,0}, {1,0}, {1,0}};
    int m[][] = {{1,1}, {0,0}, {1,0}};
    int n[][] = {{1,1}, {0,1}, {1,0}};
    int o[][] = {{1,0}, {0,1}, {1,0}};
    int p[][] = {{1,1}, {1,0}, {1,0}};
    int q[][] = {{1,1}, {1,1}, {1,0}};
    int r[][] = {{1,0}, {1,1}, {1,0}};
    int s[][] = {{0,1}, {1,0}, {1,0}};
    int t[][] = {{0,1}, {1,1}, {1,0}};
    int u[][] = {{1,0}, {0,0}, {1,1}};
    int v[][] = {{1,0}, {1,0}, {1,1}};
    int w[][] = {{0,1}, {1,1}, {0,1}};
    int x[][] = {{1,1}, {0,0}, {1,1}};
    int y[][] = {{1,1}, {0,1}, {1,1}};
    int z[][] = {{1,0}, {0,1}, {1,1}};
    int capital_a[][] = {{0,0,1,0},{0,0,0,0},{0,1,0,0}}; //A
    int capital_b[][] = {{0,0,1,0},{0,0,1,0},{0,1,0,0}};
    int capital_c[][] = {{0,0,1,1},{0,0,0,0},{0,1,0,0}};
    int capital_d[][] = {{0,0,1,1},{0,0,0,1},{0,1,0,0}};
    int capital_e[][] = {{0,0,1,0},{0,0,0,1},{0,1,0,0}};
    int capital_f[][] = {{0,0,1,1},{0,0,1,0},{0,1,0,0}};
    int capital_g[][] = {{0,0,1,1},{0,0,1,1},{0,1,0,0}};
    int capital_h[][] = {{0,0,1,0},{0,0,1,1},{0,1,0,0}};
    int capital_i[][] = {{0,0,0,1},{0,0,1,0},{0,1,0,0}};
    int capital_j[][] = {{0,0,0,1},{0,0,1,1},{0,1,0,0}};
    int capital_k[][] = {{0,0,1,0},{0,0,0,0},{0,1,1,0}};
    int capital_l[][] = {{0,0,1,0},{0,0,1,0},{0,1,1,0}};
    int capital_m[][] = {{0,0,1,1},{0,0,0,0},{0,1,1,0}};
    int capital_n[][] = {{0,0,1,1},{0,0,0,1},{0,1,1,0}};
    int capital_o[][] = {{0,0,1,0},{0,0,0,1},{0,1,1,0}};
    int capital_p[][] = {{0,0,1,1},{0,0,1,0},{0,1,1,0}};
    int capital_q[][] = {{0,0,1,1},{0,0,1,1},{0,1,1,0}};
    int capital_r[][] = {{0,0,1,0},{0,0,1,1},{0,1,1,0}};
    int capital_s[][] = {{0,0,0,1},{0,0,1,0},{0,1,1,0}};
    int capital_t[][] = {{0,0,0,1},{0,0,1,1},{0,1,1,0}};
    int capital_u[][] = {{0,0,1,0},{0,0,0,0},{0,1,1,1}};
    int capital_v[][] = {{0,0,1,0},{0,0,1,0},{0,1,1,1}};
    int capital_w[][] = {{0,0,0,1},{0,0,1,1},{0,1,0,1}};
    int capital_x[][] = {{0,0,1,1},{0,0,0,0},{0,1,1,1}};
    int capital_y[][] = {{0,0,1,1},{0,0,0,1},{0,1,1,1}};
    int capital_z[][] = {{0,0,1,0},{0,0,0,1},{0,1,1,1}};

    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> alphabet_Array = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> alphabet_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public static ArrayList<String> alphabet_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트

    public static String name [] ={"a","b","c","d","e","f","g","h","i","j","k","l","m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public dot_quiz_alphabet(){
        alphabet_Array.add(a);
        alphabet_Array.add(b);
        alphabet_Array.add(c);
        alphabet_Array.add(d);
        alphabet_Array.add(e);
        alphabet_Array.add(f);
        alphabet_Array.add(g);
        alphabet_Array.add(h);
        alphabet_Array.add(i);
        alphabet_Array.add(j);
        alphabet_Array.add(k);
        alphabet_Array.add(l);
        alphabet_Array.add(m);
        alphabet_Array.add(n);
        alphabet_Array.add(o);
        alphabet_Array.add(p);
        alphabet_Array.add(q);
        alphabet_Array.add(r);
        alphabet_Array.add(s);
        alphabet_Array.add(t);
        alphabet_Array.add(u);
        alphabet_Array.add(v);
        alphabet_Array.add(w);
        alphabet_Array.add(x);
        alphabet_Array.add(y);
        alphabet_Array.add(z);
        alphabet_Array.add(capital_a);
        alphabet_Array.add(capital_b);
        alphabet_Array.add(capital_c);
        alphabet_Array.add(capital_d);
        alphabet_Array.add(capital_e);
        alphabet_Array.add(capital_f);
        alphabet_Array.add(capital_g);
        alphabet_Array.add(capital_h);
        alphabet_Array.add(capital_i);
        alphabet_Array.add(capital_j);
        alphabet_Array.add(capital_k);
        alphabet_Array.add(capital_l);
        alphabet_Array.add(capital_m);
        alphabet_Array.add(capital_n);
        alphabet_Array.add(capital_o);
        alphabet_Array.add(capital_p);
        alphabet_Array.add(capital_q);
        alphabet_Array.add(capital_r);
        alphabet_Array.add(capital_s);
        alphabet_Array.add(capital_t);
        alphabet_Array.add(capital_u);
        alphabet_Array.add(capital_v);
        alphabet_Array.add(capital_w);
        alphabet_Array.add(capital_x);
        alphabet_Array.add(capital_y);
        alphabet_Array.add(capital_z);


        for(int i = 0 ; i<alphabet_count ; i++){
            alphabet_name.add(name[i]);
            alphabet_dot_count.add(dot_counter[i]);
        }
    }
}
