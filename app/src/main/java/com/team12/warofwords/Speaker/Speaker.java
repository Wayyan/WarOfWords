package com.team12.warofwords.Speaker;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Way yan on 11/4/2018.
 */

public class Speaker {
    private TextToSpeech textToSpeech;
    private Context context;

    public Speaker(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }

    public void speak(String word) {
        textToSpeech.setSpeechRate((float) 0.9);
        textToSpeech.setPitch((float)1.35);
        textToSpeech.speak(word, TextToSpeech.QUEUE_ADD, null);

    }

    public void destroySpeaker() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
