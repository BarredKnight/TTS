package com.example.andrew.tts;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by andrew on 20.1.16.
 */
public class Speaker implements TextToSpeech.OnInitListener {
    public TextToSpeech tts;


    public void speakOnce(String str){
        tts = new TextToSpeech(MainActivity.context, this);
        tts.speak(str + ". I'm done", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speak(String str){
        tts.speak(str + ".", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("ru"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
}
