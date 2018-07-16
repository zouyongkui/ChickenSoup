package com.one.zyk.soup;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.BoloEntity;
import com.one.zyk.soup.http.NetError;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.utils.DeviceUtils;
import com.one.zyk.soup.utils.LogUtils;
import com.one.zyk.soup.utils.PhoneUtils;
import com.one.zyk.soup.weight.CustomPopWindow;

import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    private static final int RC_PHONE_STATE = 621;

    private String Tag = getClass().getSimpleName();
    private String[] mPerms = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private CustomPopWindow mPopWindow;

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
        String userId = mUserSp.getString(Constant.sp_usrId);
        if (TextUtils.isEmpty(userId)) {
            registerUsr();
        } else {
            goMainActivity();
        }
    }

    @Subscribe
    public void onSuccess(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 0) {
                String userId = jsonObject.getString("usrId");
                String usrName = jsonObject.getString("usrName");
                String usrFace = jsonObject.getString("usrFace");
                mUserSp.put(Constant.sp_usrId, userId);
                mUserSp.put(Constant.sp_usrName, usrName);
                mUserSp.put(Constant.sp_usrFace, usrFace);
                goMainActivity();
            } else {
                Toast.makeText(this, "ERROR : " + json, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void getError(NetError netError) {
        //出现错误
        Toast.makeText(this, netError.getMessage(), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        LogUtils.d(PhoneUtils.getPhoneStatus(), "授权成功！");
        String phone = DeviceUtils.getNativePhoneNumber();
        mUserSp.put("phone", phone);
        ServiceRequest.getUserId(this, phone, PhoneUtils.getIMEI(), DeviceUtils.getSystemModel());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e(Tag, "onPermissionsDenied: ");
    }

    public void registerUsr() {
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            // Already have permission, do the thing
            LogUtils.d("拥有权限！", PhoneUtils.getPhoneStatus());
            String phone = DeviceUtils.getNativePhoneNumber();
            mUserSp.put("phone", phone);
            ServiceRequest.getUserId(this, phone, PhoneUtils.getIMEI(), DeviceUtils.getSystemModel());
        } else {
            // Do not have permissions, request them now
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPopWindow = new CustomPopWindow.PopupWindowBuilder(SplashActivity.this)
                            .setView(R.layout.pop_request_permission)//显示的布局，还可以通过设置一个View
                            .setFocusable(false)//是否获取焦点，默认为ture
                            .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                            .create();
                    mPopWindow.getContentView().findViewById(R.id.tv_ok).setOnClickListener(SplashActivity.this);
                    mPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }
            }, 300);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onClick(View v) {
        mPopWindow.dissmiss();
        EasyPermissions.requestPermissions(this, "需要读取您的设备ID号以生成唯一标识", RC_PHONE_STATE, mPerms);
    }
}
