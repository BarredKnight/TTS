package com.example.andrew.tts;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private Button btnSpeak;
    private Button btnLaunch;
    private Button btnParse;
    private Button btnNext;
    private Button btnPrev;
    public static EditText txtText;

    public static Context context;

    private ArrayList<ArrayList<String>> whole = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getBaseContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        btnSpeak = (Button) findViewById(R.id.btnSpeak);
        btnLaunch = (Button) findViewById(R.id.btnLaunch);
        btnParse = (Button) findViewById(R.id.btnParse);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        txtText = (EditText) findViewById(R.id.txtText);


        // call service <<
        btnLaunch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent startIntent = new Intent(MainActivity.this, Controller.class);
                startService(startIntent);
            }

        });

        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });
        btnLaunch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                breakIt();
            }

        });
        btnParse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                whole = Parser.parse();
                txtText.setText(whole.get(0).get(0));
            }

        });

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                for (ArrayList<String> fir: whole){
                    for (String in : fir){
                        String curr = txtText.getText().toString();
                        if (in.equals(curr)){
                            int currInd = fir.indexOf(in);
                            if (currInd + 1 + 1 < fir.size()){
                                txtText.setText(fir.get(currInd + 1));
                                break;
                            }else{
                                int nextTick = whole.indexOf(fir) + 1;
                                if (nextTick + 1 <= whole.size()){
                                    txtText.setText(whole.get(nextTick).get(0));
                                }else{
                                    txtText.setText(whole.get(0).get(0));
                                }
                            }
                        }
                    }
                }
            }

        });

        btnPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                tts.playSilentUtterance(3000, TextToSpeech.QUEUE_FLUSH, "1");
//                tts.playSilence(3000, TextToSpeech.QUEUE_FLUSH, null);
//                try {
//                    tts.wait(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

        });


//        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

//        am.setSpeakerphoneOn(false);
//        am.startBluetoothSco();
//        am.setBluetoothScoOn(true);
//        if (am.isBluetoothA2dpOn()){
//            Log.i("Thatsme", "A2dp is on!");
//        }
//        if (am.isBluetoothScoOn()){
//            Log.i("Thatsme", "Sco is on!");
//        }
//        if (am.isBluetoothScoAvailableOffCall()){
//            Log.i("Thatsme", "Sco available off call");
//        }

//        am.setMode(AudioManager.MODE_IN_CALL);
//        am.setMode(AudioManager.MODE_IN_CALL);
//        am.setWiredHeadsetOn(true);
//        am.setSpeakerphoneOn(false);

    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("ru"));
//            tts.setPitch((float)0.5);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }


    }

    public void speakOut(){
        String text = txtText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void breakIt(){
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        Log.i("Thatsme", "BREAKED!");
//        if (am.isBluetoothA2dpOn()){
//            Log.i("Thatsme", "A2dp is on!");
//        }
//        if (am.isBluetoothScoOn()){
//            Log.i("Thatsme", "Sco is on!");
//        }
//        if (am.isBluetoothScoAvailableOffCall()){
//            Log.i("Thatsme", "Sco available off call");
//        }

        am.setMode(AudioManager.MODE_NORMAL);
        am.setWiredHeadsetOn(true);
    }
}
