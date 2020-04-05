package com.example.goodnight;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Timer {

    private static final int COUNTDOWN_INTERVAL = 1000;
    private CountDownTimer countDownTimer;
    private Runnable lockScreen;
    private TextView remainTimeTextView;
    private Context context;

    public Timer(Runnable lockScreen, TextView remainTimeTextView, Context context) {
        this.lockScreen = lockScreen;
        this.remainTimeTextView = remainTimeTextView;
        this.context = context;
    }

    public void startCountdown(int requestedTime) {
        if (requestedTime <= 0) {
            Toast.makeText(context, "시간을 넣어라 애송이", Toast.LENGTH_SHORT).show();
            return;
        }
        countDownTimer = new CountDownTimer(requestedTime * 1000 * 60, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainTimeTextView.setText(String.format(Locale.getDefault(), "%d min.", millisUntilFinished / 1000L / 60));
            }

            @Override
            public void onFinish() {
                lockScreen.run();
                remainTimeTextView.setText("");
            }
        }.start();
    }
}
