package com.example.yeo.practice.Common_quiz_sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;
import com.example.yeo.practice.Normal_version_quiz.quiz_reading_manual;

/*
퀴즈가 진행될때 출력되는 음성을 관리하는 서비스
 */

public class quiz_reading_service extends Service {
    static public int menu_page = 1;
    static public boolean finish = false; //점자 학습의 종료를 알리는 변수
    static public boolean progress = false;
    private static final String TAG = "Number";
    public static MediaPlayer first, second, third, forth, last, quizmanual,quiz_finish, initial_quiz_finish, vowel_quiz_finish, final_quiz_finish,
            num_quiz_finish, alphabet_quiz_finish, sentence_quiz_finish, abbreviation_quiz_finish, word_quiz_finish, letter_quiz_finish;
    public static int question =0 ;
    public static int finish_n = 0;
    public quiz_reading_service() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        quizmanual= MediaPlayer.create(this, R.raw.quiz_manual); //퀴즈 메뉴얼
        first= MediaPlayer.create(this, R.raw.first_quiz); //첫번째 문제
        second= MediaPlayer.create(this, R.raw.second_quiz); //두번째 문제
        third= MediaPlayer.create(this, R.raw.third_quiz); //세번째 문제
        forth= MediaPlayer.create(this, R.raw.forth_quiz); //네번째 문제
        last= MediaPlayer.create(this, R.raw.last_quiz); //마지막 문제

        initial_quiz_finish = MediaPlayer.create(this, R.raw.initial_quiz_finish); //초성퀴즈 종료
        vowel_quiz_finish = MediaPlayer.create(this, R.raw.vowel_quiz_finish ); //모음퀴즈 종료
        final_quiz_finish = MediaPlayer.create(this, R.raw.final_quiz_finish); //종성 퀴즈 종료
        num_quiz_finish  = MediaPlayer.create(this, R.raw.num_quiz_finish ); //숫자 퀴즈 종료
        alphabet_quiz_finish  = MediaPlayer.create(this, R.raw.alphabet_quiz_finish ); //알파벳 퀴즈 종료
        sentence_quiz_finish  = MediaPlayer.create(this, R.raw.sentence_quiz_finish ); //문장부호 퀴즈 종료
        abbreviation_quiz_finish  = MediaPlayer.create(this, R.raw.abbreviation_quiz_finish ); //약자 및 약어 퀴즈 종료
        word_quiz_finish  = MediaPlayer.create(this, R.raw.word_quiz_finish ); //단어 퀴즈 종료
        letter_quiz_finish = MediaPlayer.create(this, R.raw.letter_quiz_finish); //글자 퀴즈 종료

        //반복제거
        initial_quiz_finish .setLooping(false);
        vowel_quiz_finish .setLooping(false);
        final_quiz_finish.setLooping(false);
        num_quiz_finish .setLooping(false);
        alphabet_quiz_finish .setLooping(false);
        sentence_quiz_finish .setLooping(false);
        abbreviation_quiz_finish .setLooping(false);
        word_quiz_finish.setLooping(false);
        letter_quiz_finish.setLooping(false);

        quizmanual.setLooping(false);
        first.setLooping(false);
        second.setLooping(false);
        third.setLooping(false);
        forth.setLooping(false);
        last.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        switch(question){
            case 0: //퀴즈메뉴얼
                quizmanual.start();
                break;
            case 1: //첫번째 문제
                if(quizmanual.isPlaying()){
                    quizmanual.reset();
                    quizmanual=MediaPlayer.create(this,R.raw.quiz_manual);
                }
                first.start();
                break;
            case 2: //두번째 문제
                if(first.isPlaying()){
                    first.reset();
                    first=MediaPlayer.create(this,R.raw.first_quiz);
                }
                second.start();
                break;
            case 3: //세번째 문제
                if(second.isPlaying()){
                    second.reset();
                    second=MediaPlayer.create(this,R.raw.second_quiz);
                }
                third.start();
                break;
            case 4: //네번째 문제
                if(third.isPlaying()){
                    third.reset();
                    third=MediaPlayer.create(this,R.raw.third_quiz);
                }
                forth.start();
                break;
            case 6: //퀴즈종료 후 점수 출력
                if(quizmanual.isPlaying()){
                    quizmanual.reset();
                    quizmanual=MediaPlayer.create(this,R.raw.quiz_manual);
                    question = 0;
                    if(finish_n == 0){
                        initial_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 1){
                        vowel_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 2){
                        final_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 3){
                        num_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 4){
                        alphabet_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 5){
                        sentence_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 6){
                        abbreviation_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 7){
                        letter_quiz_finish.start();
                        finish_n = 0;
                    }else if(finish_n == 8){
                        word_quiz_finish.start();
                        finish_n = 0;
                    }
                }
                break;
            case 7: //퀴즈 종료
                quiz_finish.start();
                question = 0;
                break;
        }

        quizmanual.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (quiz_reading_manual.speechani.isRunning()) {
                    quiz_reading_manual.speechani.stop();
                }
                quizmanual.reset();
                quizmanual=MediaPlayer.create(quiz_reading_service.this,R.raw.quiz_manual);
            }
        });

        return START_NOT_STICKY;

    }
}
