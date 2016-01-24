package com.example.andrew.tts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class VolumeReceiver extends BroadcastReceiver {
    static int timezone = 300;
    static int doublzone = 5000;

    static int vol = 0;
    static long time = 0;
    static int prev = 1; // 1-^  2-v  3-^-v  4-v-^



    @Override
    public void onReceive(Context context, Intent intent) {

        handle(intent);


    }

    private static void handle(Intent intent){
        int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        long curTime = Calendar.getInstance().getTimeInMillis();
        boolean pass = curTime - time > timezone;
        boolean doubl = curTime - time < doublzone;

        if (prev == 2 && volume > vol && doubl){    // down-up  out
            Controller.stateIn = false;
            Controller.rememberedPlace = Controller.current;
            Controller.stop();



            prev = 4;
            vol = volume;
            time = curTime;
            Log.i("Thatsme", "down-up");
        }else if (prev == 1 && volume < vol && doubl){     // up-down   in
            Controller.stateIn = true;
            Controller.mode = 0;

            if (!Controller.rememberedPlace.equals("None")){
                Controller.play(Controller.rememberedPlace);
            }else {
                Controller.play(Controller.prev());
            }



            prev = 3;
            vol = volume;
            time = curTime;
            Log.i("Thatsme", "up-down");
        }else if ((vol == volume && vol == 15 && pass) || (volume > vol)){        // up

            switch(Controller.mode){
                case -1:
                    Controller.play("One way down. Hehe.");
                    break;
                default:
                    if (Controller.hasPrev()){
                        Controller.play(Controller.prev());
                    }else {
                        Controller.endUp();
                    }
                    break;

            }



            prev = 1;
            vol = volume;
            time = curTime;
            Log.i("Thatsme", "up");
        }else if((vol == volume && vol == 0 && pass) || (volume < vol)){    // down

            switch(Controller.mode){
                case -1:
                        Controller.enter();
                        break;
                default:
                        if (Controller.hasNext()){
                            Controller.play(Controller.next());
                        }else {
                            Controller.endDown();
                        }
                        break;

            }

            prev = 2;
            vol = volume;
            time = curTime;
            Log.i("Thatsme", "down");
        }
    }



}