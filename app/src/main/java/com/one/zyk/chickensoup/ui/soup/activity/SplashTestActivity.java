package com.one.zyk.chickensoup.ui.soup.activity;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.one.zyk.chickensoup.app.Constant;
import com.one.zyk.chickensoup.utils.DeviceUtils;
import com.one.zyk.chickensoup.utils.SPUtils;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.inapp.UmengSplashMessageActivity;

/**
 * Created by ykco on 2018/1/2.
 */

public class SplashTestActivity extends UmengSplashMessageActivity {
    /**
     * 获取用户ID
     */
    private void getUserId() {
        String userId = SPUtils.getInstance(Constant.db_user).getString(Constant.useId);
        if (!TextUtils.isEmpty(userId)) {
            return;
        }
        String url = "http://47.94.130.167:8081/soup/api2/getUserId" +
                "?phoneNum=" + DeviceUtils.getNativePhoneNumber()
                + "&brandname=" + DeviceUtils.getDeviceBrand()
                + "&deviceid=" + DeviceUtils.getIMEI();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    @Override
    public boolean onCustomPretreatment() {
        getUserId();
        InAppMessageManager mInAppMessageManager = InAppMessageManager.getInstance(this);
        //设置应用内消息为Debug模式
        mInAppMessageManager.setInAppMsgDebugMode(true);
        //参数为Activity的完整包路径
        mInAppMessageManager.setMainActivityPath("com.one.zyk.chickensoup.ui.soup.activity.TabActivity");
        return super.onCustomPretreatment();
    }
}
