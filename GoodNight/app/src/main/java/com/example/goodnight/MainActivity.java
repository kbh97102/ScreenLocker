package com.example.goodnight;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/*
TODO 구조 개선
TODO 코틀린으로 변경
TODO 디자인 개선
TODO 앱 아이콘 설정
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_ENABLE = 11;
    private Button lock, disable, enable, start;
    private TextView remainTimeTextView;
    private EditText setTimerEditText;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName compName;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setButtonEvent();
        setBrightSeekBarEvent();
    }

    private void initialize() {
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, AdminReceiver.class);
        setTimerEditText = (EditText) findViewById(R.id.setTime);
        remainTimeTextView = (TextView) findViewById(R.id.remainTime);
        start = (Button) findViewById(R.id.start);
        lock = (Button) findViewById(R.id.lock);
        enable = (Button) findViewById(R.id.enableBtn);
        disable = (Button) findViewById(R.id.disableBtn);
        timer = new Timer(this::lockScreen, remainTimeTextView, getApplicationContext());
    }

    private void setButtonEvent() {
        lock.setOnClickListener(this);
        enable.setOnClickListener(this);
        disable.setOnClickListener(this);
        start.setOnClickListener(view -> startTimer());
    }

    private void setBrightSeekBarEvent(){
        SeekBar brightSeekBar = findViewById(R.id.brightSeekBar);
        brightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeScreenBrightness(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void displayToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startTimer() {
        changeScreenBrightness(1);
        if (setTimerEditText.getText() == null) {
            displayToastMessage("시간을 입력해라 애송이");
            return;
        }
        timer.startCountdown(Integer.parseInt(setTimerEditText.getText().toString()));
    }

    //밝기 범위 0 ~ 255, 시스템 설정값 -1
    private void changeScreenBrightness(int value){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.screenBrightness = value / 100f;
        window.setAttributes(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isActive = devicePolicyManager.isAdminActive(compName);
        disable.setVisibility(isActive ? View.VISIBLE : View.GONE);
        enable.setVisibility(isActive ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view == lock) {
            lockScreen();
        } else if (view == enable) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Additional text explaining why we need this permission");
            startActivityForResult(intent, RESULT_ENABLE);

        } else if (view == disable) {
            devicePolicyManager.removeActiveAdmin(compName);
            disable.setVisibility(View.GONE);
            enable.setVisibility(View.VISIBLE);
        }
    }


    private void lockScreen() {
        boolean active = devicePolicyManager.isAdminActive(compName);

        if (active) {
            devicePolicyManager.lockNow();
        } else {
            displayToastMessage("You need to enable the Admin Device Features");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    displayToastMessage("You have enabled the Admin Device features");
                } else {
                    displayToastMessage("Problem to enable the Admin Device features");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
