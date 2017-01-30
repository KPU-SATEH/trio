package com.example.yeo.practice.Common_sound;

/**
 * Created by chanh on 2017-01-05.
 */

import android.util.Log;

import com.example.yeo.practice.WHclass;
import net.daum.mf.speech.api.TextToSpeechClient;
import net.daum.mf.speech.api.TextToSpeechListener;

public class Braille_Text_To_Speech implements TextToSpeechListener {
    String TAG = "Braille_Text_To_Speech";
    private TextToSpeechClient TTS;

    public Braille_Text_To_Speech(){
        TTS = new TextToSpeechClient.Builder()
                .setApiKey(WHclass.APIKEY)              // 발급받은 api key
                .setSpeechMode(TextToSpeechClient.NEWTONE_TALK_2)            // 음성합성방식
                .setSpeechSpeed(1.0)            // 발음 속도(0.5~4.0)
                .setSpeechVoice(TextToSpeechClient.VOICE_MAN_READ_CALM)  //TTS 음색 모드 설정(여성 차분한 낭독체)
                .setListener(this)
                .build();
    }

    public void TTS_Play(String text){
        if(TTS.isPlaying())
            TTS.stop();

        TTS.play(text);
    }

    public void TTS_Stop(){
        if(TTS.isPlaying())
            TTS.stop();
    }

    @Override
    public void onError(int code, String message) { //에러처리
        handleError(code);
    }

    private void handleError(int errorCode) {
        String errorText;
        switch (errorCode) {
            case TextToSpeechClient.ERROR_NETWORK:
                errorText = "네트워크 오류";
                break;
            case TextToSpeechClient.ERROR_NETWORK_TIMEOUT:
                errorText = "네트워크 지연";
                break;
            case TextToSpeechClient.ERROR_CLIENT_INETRNAL:
                errorText = "음성합성 클라이언트 내부 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_INTERNAL:
                errorText = "음성합성 서버 내부 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_TIMEOUT:
                errorText = "음성합성 서버 최대 접속시간 초과";
                break;
            case TextToSpeechClient.ERROR_SERVER_AUTHENTICATION:
                errorText = "음성합성 인증 실패";
                break;
            case TextToSpeechClient.ERROR_SERVER_SPEECH_TEXT_BAD:
                errorText = "음성합성 텍스트 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_SPEECH_TEXT_EXCESS:
                errorText = "음성합성 텍스트 허용 길이 초과";
                break;
            case TextToSpeechClient.ERROR_SERVER_UNSUPPORTED_SERVICE:
                errorText = "음성합성 서비스 모드 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_ALLOWED_REQUESTS_EXCESS:
                errorText = "허용 횟수 초과";
                break;
            default:
                errorText = "정의하지 않은 오류";
                break;
        }

        final String statusMessage = errorText + " (" + errorCode + ")";

        Log.i(TAG, statusMessage);
    }

    @Override
    public void onFinished() { //음성합성이 종료될 때 호출된다.
        int intSentSize = TTS.getSentDataSize();      //세션 중에 전송한 데이터 사이즈
        int intRecvSize = TTS.getReceivedDataSize();  //세션 중에 전송받은 데이터 사이즈

        final String strInacctiveText = "handleFinished() SentSize : " + intSentSize + "     RecvSize : " + intRecvSize;

        Log.i(TAG, strInacctiveText);
    }

}
