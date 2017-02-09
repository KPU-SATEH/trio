package com.example.yeo.practice;

/**
 * Created by chanh on 2016-12-21.
 */

public class Menu_info {

    /*
    고정적으로 사용되는 상수들을 관리하는 클래스
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

    public static int MENU_QUIZ_READING = 1;
    public static int MENU_QUIZ_WRITING = 2;
    public static int MENU_QUIZ_INFO = 0; //퀴즈 메뉴 정보

    // 이전, 다음 소리 출력을 위한 상수 정의
    public static int next = 1; //다음
    public static int pre = 2; //이전

    //버전 확인을 위한 상수 정의
    public static int version_check=0; //버전 확인 멘트
    public static int version_start=1; //버전 확인 시작
    public static int version_restart=2; // 다시 시도 멘트
    public static int version_reset=3; //다시 시도 멘트 2
    public static int version_onefinger=4; //손가락을 1개만 얹어달라는 멘트
    public static int version_blind_person=5; //시각장애인 전용버전 멘트
    public static int version_normal=6; //일반 사용자 전용 버전 멘트

    //쓰기 퀴즈 상수정의
    public static int writing_direction = 0; // 설명멘트
    public static int writing_first = 1; //첫번째 문제
    public static int writing_second = 2; //두번째 문제
    public static int writing_last = 3; //마지막 문제
    public static int writing_success = 4; //정답 맞춤
    public static int writing_fail = 5; //정답 틀림
    public static int all_finish = 6; //모든 문제 풀고 종료
    public static int writingfinish =  7; //중간 종료
}
