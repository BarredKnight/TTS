package com.example.andrew.tts;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by andrew on 21.1.16.
 */
public class Controller extends Service {
    public static ArrayList<ArrayList<String>> whole;
    private static Speaker speaker = new Speaker();
    private static Listener listener = new Listener();
    public static String current;
    private static int delayBetween = 0;    //ms
    public static int mode = -1;    // -1-out  0-without pause  1-with  2-once
    public static boolean stateIn;
    public static String rememberedPlace = "None";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        whole = Parser.parse();
        speaker.tts.setOnUtteranceProgressListener(listener);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(new VolumeReceiver(), filter);

        sayHello();


        return super.onStartCommand(intent, flags, startId);
    }


    private void sayHello(){
        speaker.speakOnce("Hello, dear. Glad to see you. Press down to start. I will wait.");
    }

    public static void enter(){
        current = whole.get(0).get(1);
        mode = 2;
        stateIn = false;

        speaker.speakOnce(current);
    }

    public static void play(String str){
        current = str;
        speaker.speak(current);
    }

    public static boolean hasNext(){
        if (stateIn == true) {
            for (ArrayList<String> fir: whole){
                for (String in : fir){
                    if (in.equals(current)){
                        int next = fir.indexOf(in) + 1;
                        if (next + 1 > fir.size()){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }
            }
        }else{
            for (ArrayList<String> fir: whole) {
                if (fir.get(1).equals(current)) {
                    return (whole.indexOf(fir) + 1) <= whole.size()-1;
                }
            }
        }

        return false;
    }

    private static int nextIndex(){
        for (ArrayList<String> fir: whole){
            for (String in : fir){
                if (in.equals(current)){
                    int next = fir.indexOf(in) + 1;
                    return next;
                }
            }
        }
        return -1;  //never reached
    }   // not used

    public static String next(){
        if (stateIn == true) {
            for (ArrayList<String> fir: whole){
                for (String in : fir){
                    if (in.equals(current)){
                        int next = fir.indexOf(in) + 1;
                        return fir.get(next);
                    }
                }
            }
        }
        else {
            for (ArrayList<String> fir : whole) {
                if (fir.get(1).equals(current)) {
                    int nextInd = whole.indexOf(fir) + 1;
                    return whole.get(nextInd).get(1);
                }
            }
        }

        return null;
    }

    public static boolean hasPrev(){
        if (stateIn == true) {
            for (ArrayList<String> fir: whole){
                for (String in : fir){
                    if (in.equals(current)){
                        return fir.indexOf(in) - 1 >= 0;
                    }
                }
            }
        }else{
            for (ArrayList<String> fir: whole) {
                if (fir.get(1).equals(current)) {
                    return (whole.indexOf(fir) - 1) >= 0;
                }
            }
        }

        return true;    //for crush
    }

    public static String prev(){
        if (stateIn == true) {
            for (ArrayList<String> fir: whole){
                for (String in : fir){
                    if (in.equals(current)){
                        int prev = fir.indexOf(in) - 1;
                        return fir.get(prev);
                    }
                }
            }
        }
        else {
            for (ArrayList<String> fir : whole) {
                if (fir.get(1).equals(current)) {
                    int prevInd = whole.indexOf(fir) - 1;
                    return whole.get(prevInd).get(1);
                }
            }
        }

        return null;
    }

    public static void endDown(){
        mode = 2;
        speaker.tts.stop();
        speaker.speak("Нижний край. Это конец. Нижний.");
    }

    public static void endUp(){
        mode = 2;
        speaker.tts.stop();
        speaker.speak("Верхний край. Это конец. Верхний.");
    }

    public static void stop(){
        mode = 2;
        speaker.speak("Stopped");
    }

}
