package com.example.yeo.practice.Common_mynote_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chanh on 2016-12-28.
 */

// 나만의 단어장을 위해 점자정보를 저장하는 데이터베이스 클래스 최대 50개의 단어 저장 가능. 나만의 숙련 단어장 데이터베이스

public class Master_Braille_DB extends SQLiteOpenHelper {
    int id=0; //데이터베이스의 기본 키
    int count = 0 ; //점자 칸의 갯수
    String name=""; //점자 이름
    String matrix1=""; // 점자 행렬의 첫번째 행
    String matrix2=""; // 점자 행렬의 두번째 행
    String matrix3=""; // 점자 행렬의 세번째 행
    int reference; // 점자의 주소
    int reference_index; //점자의 순서번호
    public Master_DB_manager master_db_manager;


    public Master_Braille_DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        master_db_manager = new Master_DB_manager();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //기본키(ID), 칸의 숫자(count), 점자이름(name), 행렬의 첫번째 행(matrix1), 행렬의 두번째 행(matrix2), 행렬의 세번째 행(matrix3), 점자주소(reference), 점자의 순서번호(reference_index)
        db.execSQL("CREATE TABLE BRAILLE2( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + "count INTEGER," + "name STRING," + "matrix1 TEXT," +
                "matrix2 TEXT," + "matrix3 TEXT," + "reference INTEGER," + "reference_index INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //버전이 올라갔을 때 기존의 테이블을 삭제하고 다시 만듦
        db.execSQL("DROP TABLE IF EXISTS BRAILLE2");
        onCreate(db);
    }

    public String insert(int count, String name, String matrix1, String matrix2, String matrix3, int reference, int reference_index){ //데이터베이스에 데이터 저장
        //칸의 숫자(count), 점자이름(name), 행렬의 첫번째 행(matrix1), 행렬의 두번째 행(matrix2), 행렬의 세번째 행(matrix3), 점자주소(reference), 점자의 순서번호(reference_index)
        SQLiteDatabase db = getWritableDatabase();
        String result="";

        Cursor cursor = db.rawQuery("SELECT * FROM BRAILLE2", null);
        while(cursor.moveToNext()){
            master_db_manager.datacount++;
        }

        if(master_db_manager.datacount<master_db_manager.MAXSIZE) {
            db.execSQL("INSERT INTO BRAILLE2 VALUES(null,'" + count + "', '" + name + "', '" + matrix1 + "', '" + matrix2 + "', '" + matrix3 + "', '" + reference + "', '" + reference_index + "');");
            result = "나만의 단어장으로 전송 성공";
            master_db_manager.MyNote_down = false;
        }
        else{
            result = "나만의 단어장 용량 초과";
        }
        db.close();

        return result;
    }

    public void delete(int number){ //데이터베이스 삭제
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM BRAILLE2 WHERE _id="+number;
        db.execSQL(sql);


        if(master_db_manager.My_Note_page>0) master_db_manager.My_Note_page--;

         db.close();


    }
    public String getResult(){  //데이터베이스로부터 값을 읽어옴
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor  = db.rawQuery("SELECT * FROM BRAILLE2",null);
        master_db_manager.DB_init(); //현재까지 읽어들인 데이터베이스 초기화

        while(cursor.moveToNext()){
            id = cursor.getInt(0); //데이터베이스의 기본키
            count = cursor.getInt(1); //점자의 칸 수 저장
            name = cursor.getString(2); //점자의 이름 저장

            /*
            matrix1 = 점자의 첫번째 행
            matrix2 = 점자의 두번째 행
            matrix3 = 점자의 세번째 행
             */
            switch(count){
                case 1:
                    matrix1 = String.format("%2s",cursor.getString(3));
                    matrix2 = String.format("%2s",cursor.getString(4));
                    matrix3 = String.format("%2s",cursor.getString(5));
                    break;
                case 2:
                    matrix1 = String.format("%4s",cursor.getString(3));
                    matrix2 = String.format("%4s",cursor.getString(4));
                    matrix3 = String.format("%4s",cursor.getString(5));
                    break;
                case 3:
                    matrix1 = String.format("%6s",cursor.getString(3));
                    matrix2 = String.format("%6s",cursor.getString(4));
                    matrix3 = String.format("%6s",cursor.getString(5));
                    break;
                case 4:
                    matrix1 = String.format("%8s",cursor.getString(3));
                    matrix2 = String.format("%8s",cursor.getString(4));
                    matrix3 = String.format("%8s",cursor.getString(5));
                    break;
                case 5:
                    matrix1 = String.format("%10s",cursor.getString(3));
                    matrix2 = String.format("%10s",cursor.getString(4));
                    matrix3 = String.format("%10s",cursor.getString(5));
                    break;
                case 6:
                    matrix1 = String.format("%12s",cursor.getString(3));
                    matrix2 = String.format("%12s",cursor.getString(4));
                    matrix3 = String.format("%12s",cursor.getString(5));
                    break;
                case 7:
                    matrix1 = String.format("%14s",cursor.getString(3));
                    matrix2 = String.format("%14s",cursor.getString(4));
                    matrix3 = String.format("%14s",cursor.getString(5));
                    break;
            }

            reference = cursor.getInt(6); //점자의 음성파일 주소
            reference_index = cursor.getInt(7); //점자의 음성파일 순서
            master_db_manager.DB_All_insert(id,count, name, matrix1, matrix2, matrix3, reference, reference_index); //데이터베이스에 저장
        }

        result = "단어장 불러오기 성공";
        db.close();

        return result;

    }
}