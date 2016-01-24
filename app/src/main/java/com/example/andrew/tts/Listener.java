package com.example.andrew.tts;

import android.speech.tts.UtteranceProgressListener;

/**
 * Created by andrew on 21.1.16.
 */
public class Listener extends UtteranceProgressListener {
    @Override
    public void onStart(String utteranceId) {

    }

    @Override
    public void onDone(String utteranceId) {
        switch(Controller.mode){
            case 0:
                if (Controller.hasNext()){
                    Controller.play(Controller.next());
                }else {
                    Controller.endDown();
                }
                break;
            case 1:

                break;
            case 2:

                break;
        }
    }

    @Override
    public void onError(String utteranceId) {

    }
}
