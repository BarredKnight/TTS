package com.example.andrew.tts;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andrew on 20.1.16.
 */
public class Parser {


//    public static ArrayList<String> parse(){
//        String in = getText();
//        ArrayList<String> out = new ArrayList<>();
//        String outStr;
//        int startIndex = 0;
//        while((startIndex = in.indexOf("==КОНЕЦ==")) != -1){
//            outStr = in.substring(0, startIndex);
//            out.add(outStr);
//            in = in.substring(startIndex + 9);
//        }
//
//        return out;
//
//    }

    private static String getText() {

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "1.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            Log.e("Thatsme", "Error during getting text");
        }

        return text.toString();
    }

    public static ArrayList<ArrayList<String>> parse(){
        String in = getText();
        ArrayList<ArrayList<String>> out = new ArrayList<>();
        ArrayList<String> buff = new ArrayList<>();
        String outStr;
        int startIndex = 0;
        while((startIndex = in.indexOf("==КОНЕЦ==")) != -1){
            outStr = in.substring(0, startIndex);
            buff.add(outStr);
            in = in.substring(startIndex + 9);
        }

        for (String str : buff){
            out.add(parseTicket(str));
        }

        return out;

    }

    private static ArrayList<String> parseTicket(String in){
        int startIndex = 0;
        ArrayList<String> buff = new ArrayList<>();
        ArrayList<String> out = new ArrayList<>();

        String outStr;
        while((startIndex = in.indexOf("\n")) != -1){
            outStr = in.substring(0, startIndex);
            if (!outStr.isEmpty()){
                buff.add(outStr);
            }
            in = in.substring(startIndex+1);
        }
        for (String str : buff){
            if (str.length() > 3800){
                out.addAll(crushFourK(str));
            }else {
                out.add(str);
            }
        }

        return out;
    }

    private static ArrayList<String> crushFourK(String in){
        String buff;
        ArrayList<String> out = new ArrayList<>();
        while(in.length() > 3800){
            buff = in.substring(0, 3801);
            buff = buff + "TO BE CONTINUED";
            out.add(buff);
            in = in.substring(3801);
        }
        if (!out.isEmpty()){
            in = in + "THAT'S END";
            out.add(in);
        }else{
            out.add(in);
        }
        return out;
    }
}

/*
up down
--  -
    -
    -
    -

--  -
    -
    -
    -






 */