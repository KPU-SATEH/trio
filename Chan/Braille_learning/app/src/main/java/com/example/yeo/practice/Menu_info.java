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
    public static int MENU_TRANSLATION = 11; //점자 번역 메뉴


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
    public static int MENU_BRAILLE_TRANSLATION = 3; //점자 번역
    public static int MENU_QUIZ = 4; //퀴즈
    public static int MENU_MYNOTE = 5; //나만의단어장
    public static int MENU_COMMUNICATION = 6; //커뮤니케이션

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

    //점자번역 상수 정의
    public static int TRANS_INFO =0 ; //설명멘트

    //메뉴화면 출력을 위한 모든 메뉴 상수 정의
    public static int DISPLAY=0; //메뉴화면 출력 정보 저장 변수
    public static int DISPLAY_TUTORIAL =0 ; //사용설명서

    public static int DISPLAY_BASIC=1; //기초과정
    public static int DISPLAY_INITIAL=11; //초성연습
    public static int DISPLAY_VOWEL=12; //모음연습
    public static int DISPLAY_FINAL=13; //종성연습
    public static int DISPLAY_NUMBER=14; //숫자연습
    public static int DISPLAY_ALPHABET=15; //알파벳연습
    public static int DISPLAY_SENTENCE=16; //문장부호연습
    public static int DISPLAY_ABBREVIATION=17; //약자 및 약어 연습

    public static int DISPLAY_MASTER=2; //숙련과정
    public static int DISPLAY_LETTER=21; //글자연습
    public static int DISPLAY_WORD=22; //단어연습

    public static int DISPLAY_TRANS=3; //점자번역

    public static int DISPLAY_QUIZ=4; //퀴즈
    public static int DISPLAY_QUIZ_INIT=41; //초성퀴즈
    public static int DISPLAY_QUIZ_VOWEL=42; //모음퀴즈
    public static int DISPLAY_QUIZ_FINAL=43; //종성퀴즈
    public static int DISPLAY_QUIZ_NUMBER=44; //숫자퀴즈
    public static int DISPLAY_QUIZ_ALPHABET=45; //알파벳퀴즈
    public static int DISPLAY_QUIZ_SENTENCE=46; //문장부호퀴즈
    public static int DISPLAY_QUIZ_ABBREVIATION=47; //약자 및 약어 퀴즈
    public static int DISPLAY_QUIZ_LETTER=48; //글자 퀴즈
    public static int DISPLAY_QUIZ_WORD=49; //단어퀴즈
    public static int DISPLAY_QUIZ_READ = 401; //읽기퀴즈
    public static int DISPLAY_QUIZ_WRITING = 402; //쓰기퀴즈

    public static int DISPLAY_MYNOTE=5; //나만의 단어장
    public static int DISPLAY_MYNOTE_BASIC=51; //기초단어장
    public static int DISPLAY_MYNOTE_MASTER=52; //숙련단어장

    public static int DISPLAY_COMUNICATION=6; //선생님과의 대화
    public static int DISPLAY_COMUNICATION_TEAHCER=61; //선생님모드
    public static int DISPLAY_COMUNICATION_STUDENT=62; //학생모드




}
