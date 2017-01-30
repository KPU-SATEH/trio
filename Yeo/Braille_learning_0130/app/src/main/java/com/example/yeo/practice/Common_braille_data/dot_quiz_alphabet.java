package com.example.yeo.practice.Common_braille_data;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */
/*
 * 알파벳 퀴즈에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_quiz_alphabet {
    static public int alphabetcount = 54; //알파벳 퀴즈의 갯수
    int roma[][]={{0,0},{0,1},{1,1}}; // 로마자표
    int a[][]={{1,0},{0,0},{0,0}};// a
    int b[][]={{1,0},{1,0},{0,0,}};// b
    int c[][]={{1,1},{0,0},{0,0}};// c
    int d[][]={{1,1},{0,1},{0,0}};// d
    int e[][]={{1,0},{0,1},{0,0}};// e
    int f[][]={{1,1},{1,0},{0,0}};// f
    int g[][]={{1,1},{1,1},{0,0}};// g
    int h[][]={{1,0},{1,1},{0,0}};// h
    int i[][]={{0,1},{1,0},{0,0}};// i
    int j[][]={{0,1},{1,1},{0,0}};// j
    int k[][]={{1,0},{0,0},{1,0}};// k
    int l[][]={{1,0},{1,0},{1,0}};// l
    int m[][]={{1,1},{0,0},{1,0}};// m
    int n[][]={{1,1},{0,1},{1,0}};// n
    int o[][]={{1,0},{0,1},{1,0}};// o
    int p[][]={{1,1},{1,0},{1,0}};// p
    int q[][]={{1,1},{1,1},{1,0}};// q
    int r[][]={{1,0},{1,1},{1,0}};// r
    int s[][]={{0,1},{1,0},{1,0}};// s
    int t[][]={{0,1},{1,1},{1,0}};// t
    int u[][]={{1,0},{0,0},{1,1}};// u
    int v[][]={{1,0},{1,0},{1,1}};// v
    int w[][]={{0,1},{1,1},{0,1}};// w
    int x[][]={{1,1},{0,0},{1,1}};// x
    int y[][]={{1,1},{0,1},{1,1}};// y
    int z[][]={{1,0},{0,1},{1,1}};// z
    int fortis[][]={{0,0},{0,0},{0,1}};// 대문자 표
    int fortis_a[][]={{0,0,1,0},{0,0,0,0},{0,1,0,0}};// A
    int fortis_b[][]={{0,0,1,0},{0,0,1,0},{0,1,0,0}};// B
    int fortis_c[][]={{0,0,1,1},{0,0,0,0},{0,1,0,0}};// C
    int fortis_d[][]={{0,0,1,1},{0,0,0,1},{0,1,0,0}};// D
    int fortis_e[][]={{0,0,1,0},{0,0,0,1},{0,1,0,0}};// E
    int fortis_f[][]={{0,0,1,1},{0,0,1,0},{0,1,0,0}};// F
    int fortis_g[][]={{0,0,1,1},{0,0,1,1},{0,1,0,0}};// G
    int fortis_h[][]={{0,0,1,0},{0,0,1,1},{0,1,0,0}};// H
    int fortis_i[][]={{0,0,0,1},{0,0,1,0},{0,1,0,0}};// I
    int fortis_j[][]={{0,0,0,1},{0,0,1,1},{0,1,0,0}};// J
    int fortis_k[][]={{0,0,1,0},{0,0,0,0},{0,1,1,0}};// K
    int fortis_l[][]={{0,0,1,0},{0,0,1,0},{0,1,1,0}};// L
    int fortis_m[][]={{0,0,1,1},{0,0,0,0},{0,1,1,0}};// M
    int fortis_n[][]={{0,0,1,1},{0,0,0,1},{0,1,1,0}};// N
    int fortis_o[][]={{0,0,1,0},{0,0,0,1},{0,1,1,0}};// O
    int fortis_p[][]={{0,0,1,1},{0,0,1,0},{0,1,1,0}};// P
    int fortis_q[][]={{0,0,1,1},{0,0,1,1},{0,1,1,0}};// Q
    int fortis_r[][]={{0,0,1,0},{0,0,1,1},{0,1,1,0}};// R
    int fortis_s[][]={{0,0,0,1},{0,0,1,0},{0,1,1,0}};// S
    int fortis_t[][]={{0,0,0,1},{0,0,1,1},{0,1,1,0}};// T
    int fortis_u[][]={{0,0,1,0},{0,0,0,0},{0,1,1,1}};// U
    int fortis_v[][]={{0,0,1,0},{0,0,1,0},{0,1,1,1}};// V
    int fortis_w[][]={{0,0,0,1},{0,0,1,1},{0,1,0,1}};// W
    int fortis_x[][]={{0,0,1,1},{0,0,0,0},{0,1,1,1}};// X
    int fortis_y[][]={{0,0,1,1},{0,0,0,1},{0,1,1,1}};// Y
    int fortis_z[][]={{0,0,1,0},{0,0,0,1},{0,1,1,1}};// Z
    public static String name [] ={"로마자표", "a","b","c","d","e","f","g","h","i","j","k","l","m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","대문자표",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public int dot_count[] ={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> alphabet_Array = new ArrayList<>();//점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> alphabet_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> alphabet_dot_count = new ArrayList<>();// 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public dot_quiz_alphabet(){
        alphabet_Array.add(roma);
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
        alphabet_Array.add(fortis);

        alphabet_Array.add(fortis_a);
        alphabet_Array.add(fortis_b);
        alphabet_Array.add(fortis_c);
        alphabet_Array.add(fortis_d);
        alphabet_Array.add(fortis_e);
        alphabet_Array.add(fortis_f);
        alphabet_Array.add(fortis_g);
        alphabet_Array.add(fortis_h);
        alphabet_Array.add(fortis_i);
        alphabet_Array.add(fortis_j);
        alphabet_Array.add(fortis_k);
        alphabet_Array.add(fortis_l);
        alphabet_Array.add(fortis_m);
        alphabet_Array.add(fortis_n);
        alphabet_Array.add(fortis_o);
        alphabet_Array.add(fortis_p);
        alphabet_Array.add(fortis_q);
        alphabet_Array.add(fortis_r);
        alphabet_Array.add(fortis_s);
        alphabet_Array.add(fortis_t);
        alphabet_Array.add(fortis_u);
        alphabet_Array.add(fortis_v);
        alphabet_Array.add(fortis_w);
        alphabet_Array.add(fortis_x);
        alphabet_Array.add(fortis_y);
        alphabet_Array.add(fortis_z);


        for(int i = 0 ; i<alphabetcount ; i++){
            alphabet_name.add(name[i]);
            alphabet_dot_count.add(dot_count[i]);
        }
    }
}
