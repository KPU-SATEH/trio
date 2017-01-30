package com.example.yeo.practice.Common_mynote_database;

/**
 * Created by chanh on 2016-12-28.
 */

/*
나만의 단어장 기능을 위해 데이터베이스로부터 불러온 값을 관리하고 매칭해주는 클래스, 나만의 숙련단어장 데이터베이스 매니저
 */
public class Master_DB_manager {


    int MAXSIZE = 50; //나만의 단어장의 최대 갯수

    public int size_count = 0; // 사이즈를 카운트 하는 변수

    int id[] = new int[MAXSIZE]; //데이터베이스 id(기본키)
    int count[] = new int [MAXSIZE]; //점자 칸의 갯수
    String name[] = new String [MAXSIZE]; //점자 이름
    String matrix1[] = new String [MAXSIZE]; // 점자 행렬의 첫번째 행
    String matrix2[] = new String [MAXSIZE]; // 점자 행렬의 두번째 행
    String matrix3[] = new String [MAXSIZE]; // 점자 행렬의 세번째 행
    int reference[] = new int [MAXSIZE]; // 점자의 주소
    int reference_index[] = new int [MAXSIZE]; //점자의 순서번호
    int datacount=0;


    static public boolean MyNote_down = false; //어플리케이션이 실행되고 다운받은적이 있는지에 대한 여부

    public int My_Note_page = 0;


    Master_DB_manager(){}

    public void DB_All_insert(int id_temp,int count_temp, String name_temp, String matrix1_temp, String matrix2_temp, String matrix3_temp, int reference_temp, int reference_index_temp){
        if(size_count<MAXSIZE) {
            id[size_count]= id_temp;
            count[size_count] = count_temp; //점자의 칸 수 저장
            name[size_count] = name_temp; //점자의 이름 저장
            matrix1[size_count] = matrix1_temp; //점자의 첫번째 행 저장
            matrix2[size_count] = matrix2_temp; //점자의 두번째 행 저장
            matrix3[size_count] = matrix3_temp; //점자의 세번째 행 저장
            reference[size_count] = reference_temp; //점자의 음성파일 주소 저장
            reference_index[size_count] = reference_index_temp; //점자의 음성파일 순서
            size_count++; // 새로 데이터베이스에 추가되었는지 알수있는 변수
        }
    }

    public int getId(int index){return id[index];}
    public int getCount(int index){
        return count[index];
    }
    public String getName(int index){
        return name[index];
    }
    public String getMatrix_1(int index){
        return matrix1[index];
    }
    public String getMatrix_2(int index){
        return matrix2[index];
    }
    public String getMatrix_3(int index){
        return matrix3[index];
    }
    public int getReference(int index){
        return reference[index];
    }
    public int getReference_index(int index){
        return reference_index[index];
    }

    public void DB_init(){
        for(int i=0 ; i<size_count ; i++){
            id[i]=0;
            count[i]=0;
            name[i] = ""; //점자의 이름 저장
            matrix1[i] = ""; //점자의 첫번째 행 저장
            matrix2[i] = ""; //점자의 두번째 행 저장
            matrix3[i] = ""; //점자의 세번째 행 저장
            reference[i]=0;
            reference_index[i]=0;
        }
        size_count=0;

    }


}
