package com.one.zyk.chickensoup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.constant.Constant;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, TabActivity.class));
                finish();
            }
        }, 1000);
    }

    @Override
    protected void initView() {
        String userId = mUserSp.getString(Constant.useId);
        if (TextUtils.isEmpty(userId)) {

        }
    }
}
