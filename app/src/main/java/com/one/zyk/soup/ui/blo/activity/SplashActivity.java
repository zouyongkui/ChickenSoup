package com.one.zyk.soup.ui.blo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.http.NetError;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.utils.DeviceUtils;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initView() {
        String userId = mUserSp.getString(Constant.useId);
        if (TextUtils.isEmpty(userId)) {
            ServiceRequest.getUserId(this, DeviceUtils.getIMEI(), DeviceUtils.getDeviceBrand() + DeviceUtils.getSystemModel(), DeviceUtils.getNativePhoneNumber());
        } else {
            Log.e(TAG, userId);
            goMainActivity();
        }
    }

    @Subscribe
    public void handleObject(String js) {
        try {
            JSONObject jsonObject = new JSONObject(js);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                String userId = jsonObject.getString("userId");
                mUserSp.put(Constant.useId, userId);
                goMainActivity();
            } else {
                String msg = jsonObject.getString("msg");
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void getError(NetError netError) {
        //出现错误
        Toast.makeText(this, netError.getMessage() + "网络在开小差哦...", Toast.LENGTH_SHORT).show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    private void goMainActivity() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, TabActivity.class));
                finish();
            }
        }, 1000);
    }
}
