package com.example.yeo.practice.Common_sound;

import android.os.Bundle;
import android.util.Log;

import com.example.yeo.practice.WHclass;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;

import java.util.ArrayList;

/**
 * Created by myeongjin on 2017-01-31.
 */

public class Braille_Speech_To_Text implements SpeechRecognizeListener {
    static public SpeechRecognizerClient client;
    public static ArrayList<String> texts;
    public static boolean result = false;
    //public static String answer = "";

    public Braille_Speech_To_Text(){

    }
    public void STT_Start(){
        SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
                setApiKey(WHclass.APIKEY).
                setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);

        client = builder.build();

        client.setSpeechRecognizeListener(this);
        client.startRecording(false);
    }

    @Override
    public void onReady() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onBeginningOfSpeech() {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onEndOfSpeech() {
        //TODO implement interface DaumSpeechRec
        // ognizeListener method
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        //TODO implement interface DaumSpeechRecognizeListener method
        Log.e("Braille_Speech_To_Text", "onError");

        client = null;
    }

    @Override
    public void onPartialResult(String text) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onResults(Bundle results) {
        Log.i("Braille_Speech_To_Text", "onResults");

        //result = false;

        texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);

        /*
        for (int i = 0; i < texts.size(); i++) {
            if (answer.equals(texts.get(i)) == true) {
                result = true;
                break;
            } else
                continue;
        }

        if (result == true) {
            MainActivity.Braille_TTS.TTS_Play("축하합니다. 정답이에요~");
        } else {
            MainActivity.Braille_TTS.TTS_Play("오답입니다. 당신이 말한 단어는" + texts.get(0) + "입니다. 정답은"+answer+"입니다.");
        }
        */

        client = null;
    }

    @Override
    public void onAudioLevel(float v) {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onFinished() {
        Log.i("Braille_Speech_To_Text", "onFinished");
    }
}
