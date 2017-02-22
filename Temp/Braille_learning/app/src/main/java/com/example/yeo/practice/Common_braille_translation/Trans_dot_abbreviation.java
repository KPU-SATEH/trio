package com.example.yeo.practice.Common_braille_translation;


import java.util.ArrayList;

/*
 * 약자 및 약어 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class Trans_dot_abbreviation {

    int long_abbreviation_index =0 ;

    static public int abbreviation_count = 34; // 약자 및 약어연습의 갯수
    int ga[][]={{1,1},{1,0},{0,1}}; //가
    int na[][]={{1,1},{0,0},{0,0}}; //나
    int da[][]={{0,1},{1,0},{0,0}}; //다
    int ma[][]={{1,0},{0,1},{0,0}}; //마
    int ba[][]={{0,1},{0,1},{0,0}}; //바
    int sa[][]={{1,0},{1,0},{1,0}}; //사
    int ja[][]={{0,1},{0,0},{0,1}}; //자
    int ka[][]={{1,1},{1,0},{0,0}}; //카
    int ta[][]={{1,0},{1,1},{0,0}}; //타
    int pa[][]={{1,1},{0,1},{0,0}}; //파
    int ha[][]={{0,1},{1,1},{0,0}}; //하
    int uk[][]={{1,1},{0,1},{0,1}}; //억
    int un[][]={{0,1},{1,1},{1,1}};//언
    int ul[][]={{0,1},{1,1},{1,0}};//얼
    int yun[][]={{1,0},{0,0},{0,1}}; //연
    int yul[][]={{1,0},{1,1},{0,1}};//열
    int young[][]={{1,1},{1,1},{0,1}};//영
    int ok[][]={{1,1},{0,0},{1,1}};//옥
    int on[][]={{1,0},{1,1},{1,1}};//온
    int yong[][]={{1,1},{1,1},{1,1}};//옹
    int woon[][]={{1,1},{1,1},{0,0}};//운
    int wool[][]={{1,1},{1,0},{1,1}};//울
    int eun[][]={{1,0},{0,1},{1,1}};//은
    int eul[][]={{0,1},{1,0},{1,1}};//을
    int in[][]={{1,1},{1,1},{1,0}};//인
    int fortis_siot[][] = {{0,1},{0,0},{1,0}}; // 받침 ㅆ
    int gut[][]={{0,1,0,1},{0,1,1,0},{0,1,1,0}};//것
    int so[][] = {{1,0,0,1},{0,0,1,0},{0,0,1,0}}; //그래서
    int but[][] ={{1,0,1,1},{0,0,0,0},{0,0,0,0}}; //그러나
    int and[][] ={{1,0,0,0},{0,0,1,1},{0,0,0,0}}; //그러면
    int bytheway[][] ={{1,0,1,1},{0,0,0,1},{0,0,1,0}}; //그런데
    int and2 [][] = {{1,0,1,0},{0,0,0,0},{0,0,1,1}}; //그리고
    int so3 [][] = {{1,0,1,0},{0,0,0,1},{0,0,0,1}}; // 그리하여
    int so2 [][] ={{1,0,0,0},{0,0,1,0},{0,0,0,1}}; // 그러므로


    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static String name [] ={"가","나","다","마","바","사","자","카","타","파","하","억","언","얼","연","열","영","옥","온","옹","운","울","은",
            "을","인","받침 ㅆ","것","그래서","그러나","그러면","그런데","그리고","그리하여","그러므로"};
    public static ArrayList<int[][]> abbreviation_Array = new ArrayList<>(); //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<String> abbreviation_name = new ArrayList<>(); //점자의 글자를 저장하는 연결리스트
    public static ArrayList<Integer> abbreviation_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public Trans_dot_abbreviation(){
        abbreviation_Array.add(ga);
        abbreviation_Array.add(na);
        abbreviation_Array.add(da);
        abbreviation_Array.add(ma);
        abbreviation_Array.add(ba);
        abbreviation_Array.add(sa);
        abbreviation_Array.add(ja);
        abbreviation_Array.add(ka);
        abbreviation_Array.add(ta);
        abbreviation_Array.add(pa);
        abbreviation_Array.add(ha);
        abbreviation_Array.add(uk);
        abbreviation_Array.add(un);
        abbreviation_Array.add(ul);
        abbreviation_Array.add(yun);
        abbreviation_Array.add(yul);
        abbreviation_Array.add(young);
        abbreviation_Array.add(ok);
        abbreviation_Array.add(on);
        abbreviation_Array.add(yong);
        abbreviation_Array.add(woon);
        abbreviation_Array.add(wool);
        abbreviation_Array.add(eun);
        abbreviation_Array.add(eul);
        abbreviation_Array.add(in);
        abbreviation_Array.add(fortis_siot);
        abbreviation_Array.add(gut);
        abbreviation_Array.add(so);
        abbreviation_Array.add(but);
        abbreviation_Array.add(and);
        abbreviation_Array.add(bytheway);
        abbreviation_Array.add(and2);
        abbreviation_Array.add(so3);
        abbreviation_Array.add(so2);

        for(int i = 0 ; i<abbreviation_count ; i++){
            abbreviation_name.add(name[i]);
            abbreviation_dot_count.add(dot_counter[i]);
        }
    }

    public String get_name(int index){
        return abbreviation_name.get(index);
    }

    public int Name_search(String name, int check_number){
        int index=0;
        boolean check=false;
        boolean temp_check=false;
        char temp;
        for(int i=0; i<abbreviation_name.size() ; i++){
            if(Braille_translation.abbreviaion_search_check!=true) {
                if (27 <= i) {
                    if (check_number < 3) {
                        temp = abbreviation_name.get(i).charAt(check_number);
                        if (name.charAt(0) == temp) {
                            temp_check = true;
                            if (check_number == 2) {
                                index = i;
                                check = true;
                                Braille_translation.abbreviation_search_success = true;
                            }
                            break;
                        }
                        if (i == abbreviation_name.size() - 1) {
                            if (temp_check == false) {
                                index = -1;
                                check = true;
                                Braille_translation.abbreviation_search_success = false;
                            }
                        }
                    }

                }
            }
            if(Braille_translation.abbrevion_check<=0) {
                if (name.equals(abbreviation_name.get(i)) == true) {
                    index = i;
                    check = true;
                    break;
                }
                else
                    continue;
            }
        }

        if(check==false) {
            if(temp_check==true)
                index=-2;
            else
                index=-1;
        }
        temp_check=false;
        check = false;
        return index;
    }

    public int Dot_count_search(int index){
        int count=0;
        count = abbreviation_dot_count.get(index);
        return count;
    }


    public int[][] Matrix_search(int index, int[][] matrix){
        int count = abbreviation_dot_count.get(index);
        count = count *2;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<count ; j++){
                matrix[i][j]=abbreviation_Array.get(index)[i][j];
            }
        }

        return matrix;
    }
}
