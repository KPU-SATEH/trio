package com.example.chanh.dottest;


import java.util.ArrayList;

/*
 * 종성 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_final {
    static public int final_count = 27; //종성 연습의 갯수
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
    int fortis_siot[][]={{0,1},{0,0},{1,0}}; // ㅆ
    int fortis_giyeok[][]={{0,0,1,0},{0,0,0,0},{0,1,0,0}}; //ㄲ
    int giyeok_siot[][]={{1,0,0,0},{0,0,0,0},{0,0,1,0}}; //ㄳ
    int nieun_zieut[][]={{0,0,1,0},{1,1,0,0},{0,0,1,0}}; //ㄵ
    int nieun_hieut[][]={{0,0,0,0},{1,1,0,1},{0,0,1,1}}; //ㄶ
    int nieul_giyeok[][]={{0,0,1,0},{1,0,0,0},{0,0,0,0}}; //ㄺ
    int nieul_mieum[][]={{0,0,0,0},{1,0,1,0},{0,0,0,1}}; //ㄻ
    int nieul_bieub[][]={{0,0,1,0},{1,0,1,0},{0,0,0,0}}; // ㄼ
    int nieul_siot[][]={{0,0,0,0},{1,0,0,0},{0,0,1,0}}; // ㄽ
    int nieul_tieut[][]={{0,0,0,0},{1,0,1,0},{0,0,1,1}}; // ㄾ
    int nieul_pieup[][]={{0,0,0,0},{1,0,1,1},{0,0,0,1}}; // ㄿ
    int nieul_hieut[][]={{0,0,0,0},{1,0,0,1},{0,0,1,1}}; // ㅀ
    int bieub_siot[][]={{1,0,0,0},{1,0,0,0},{0,0,1,0}}; // ㅄ


    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={ "ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ", "ㅎ", "ㅆ", "ㄲ", "ㄳ", "ㄵ", "ㄶ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅄ"};
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
        final_Array.add(fortis_siot);
        final_Array.add(fortis_giyeok);
        final_Array.add(giyeok_siot);
        final_Array.add(nieun_zieut);
        final_Array.add(nieun_hieut);
        final_Array.add(nieul_giyeok);
        final_Array.add(nieul_mieum);
        final_Array.add(nieul_bieub);
        final_Array.add(nieul_siot);
        final_Array.add(nieul_tieut);
        final_Array.add(nieul_pieup);
        final_Array.add(nieul_hieut);
        final_Array.add(bieub_siot);

        for(int i = 0 ; i<final_count ; i++){
            final_name.add(name[i]);
            final_dot_count.add(dot_counter[i]);
        }
    }


    public int Name_search(String name){
        int index=0;
        boolean check = false;
        for(int i=0; i<final_name.size() ; i++){
            if(name.equals(final_name.get(i))==true){
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
        int count = final_dot_count.get(index);
        count = count *2;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<count ; j++){
                matrix[i][j]=final_Array.get(index)[i][j];
            }
        }

        return matrix;
    }

    public int Dot_count_search(int index){
        int count=0;
        count = final_dot_count.get(index);
        return count;
    }
}
