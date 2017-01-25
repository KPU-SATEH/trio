package com.example.yeo.practice;

/**
 * Created by chanh on 2016-12-21.
 */

public class Menu_info {

    /*
    각각의 메뉴들을 구분하기 위해 고정적으로 사용되는 상수들을 관리하는 클래스
     */
    //각각의 메뉴 상수 정의

    public static int MENU_TUTO = 0; //사용설명서
    public static int MENU_INITIAL = 1; //초성연습
    public static int MENU_VOWEL = 2; //모음연습
    public static int MENU_FINAL = 3; //종성연습
    public static int MENU_NUMBER = 4; //숫자연습
    public static int MENU_ALPHABET = 5; //알파벳 연습
    public static int MENU_SENTENS = 6; //문장부호연습
    public static int MENU_ABBREVIATION = 7; // 약자 및 약어 연습
    public static int MENU_LETTER = 8; //글자 연습
    public static int MENU_WORD = 9; // 단어연습
    public static int MENU_NOTE = 10; //나만의단어장


    public static int MENU_INFO = 0;

    public static int MENU_MASTER_LETTER= 0; // 글자연습
    public static int MENU_MASTER_WORD = 1; //단어 연습

    public static int MENU_QUIZ_INITIAL = 0; //초성퀴즈
    public static int MENU_QUIZ_VOWEL = 1; //모음 퀴즈
    public static int MENU_QUIZ_FINAL = 2; //종성퀴즈
    public static int MENU_QUIZ_NUMBER= 3; //숫자퀴즈
    public static int MENU_QUIZ_ALPHABET = 4; //알파벳 퀴즈
    public static int MENU_QUIZ_SENTENS = 5; //문장부호 퀴즈
    public static int MENU_QUIZ_ABBREVIATION = 6; //약자 및 약어 퀴즈
    public static int MENU_QUIZ_LETTER= 7; //글자 퀴즈
    public static int MENU_QUIZ_WORD = 8; //단어 퀴즈

    public static int MENU_MYNOTE_BASIC = 0; //기초단어장
    public static int MENU_MYNOTE_MASTER = 1; //숙련단어장

    public static int MENU_TUTORIAL = 0; //사용설명서
    public static int MENU_BASIC_PRACTICE = 1; //기초과정
    public static int MENU_MASTER_PRACTICE = 2; //숙련과정
    public static int MENU_QUIZ = 3; //퀴즈
    public static int MENU_MYNOTE = 4; //나만의단어장
    public static int MENU_COMMUNICATION = 5; //커뮤니케이션

    public static int MENU_QUIZ_READING = 1;
    public static int MENU_QUIZ_WRITING = 2;
    public static int MENU_QUIZ_INFO = 0; //퀴즈 메뉴 정보

    public static int MENU_COMMUNICATION_TEACHER = 0;//made by yeo
    public static int MENU_COMMUNICATION_STUDENT = 1; //made by yeo

    public static int COMMUNICATION_TEACHER_MODE=0; //made by yeo
    public static int COMMUNICATION_STUDENT_MODE=1; //made by yeo

    // 이전, 다음 소리 출력을 위한 상수 정의
    public static int next = 1; //다음
    public static int pre = 2; //이전
}
