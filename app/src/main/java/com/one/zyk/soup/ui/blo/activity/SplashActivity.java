package com.one.zyk.soup.ui.blo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.one.zyk.soup.MainActivity;
import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.http.NetError;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.utils.DeviceUtils;
import com.one.zyk.soup.utils.LogUtils;
import com.one.zyk.soup.utils.PhoneUtils;

import org.json.JSONObject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        String userId = mUserSp.getString(Constant.useId);
        if (TextUtils.isEmpty(userId)) {
            LogUtils.d(PhoneUtils.getPhoneStatus());
            ServiceRequest.getUserId(this, DeviceUtils.getNativePhoneNumber(), PhoneUtils.getIMEI(), DeviceUtils.getSystemModel());
        } else {
            LogUtils.d("sss", mUserSp.getString(Constant.useId));
            goMainActivity();
        }
    }

    @Override
    protected <T> void onRetrofitCallBack(T t) {
        try {
            JSONObject jsonObject = new JSONObject((String) t);
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
        Toast.makeText(this, netError.getMessage() + "网络在开小差哦,退出后请重试 ...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    private void goMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }

}
