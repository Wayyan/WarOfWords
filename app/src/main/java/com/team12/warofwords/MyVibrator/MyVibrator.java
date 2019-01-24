package com.team12.warofwords.MyVibrator;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * Created by Way yan on 11/9/2018.
 */

public class MyVibrator {
    private Context context;
    private Vibrator vibrator;
    public MyVibrator(Context context){
        this.context=context;
        vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void startVibrate(int t){
        for(int i=0;i<t;i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }
        }
    }
}
