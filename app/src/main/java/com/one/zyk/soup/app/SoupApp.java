package com.one.zyk.soup.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;


import com.one.zyk.soup.http.PublicParamInterceptor;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.utils.LogUtils;

import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zyk on 2017/9/22.
 */

public class SoupApp extends Application {
    public static boolean DEBUG = true;
    public static SoupApp sSoupApp;
    private Retrofit retrofit;


    public Retrofit retrofit() {
        return retrofit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSoupApp = this;
        initNetConfig();
//        initPush();
//        initHx();
    }

    private void initNetConfig() {
        //网络请求初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        //公共参数拦截器
        builder.addInterceptor(new PublicParamInterceptor(this));
        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                //请求地址
                .baseUrl(Urls.BASE_URL)
                //httpclient
                .client(client)
                //请求对象转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //返回对象转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

//    private void initPush() {
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//                LogUtils.d("YM", deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                LogUtils.e("YM", s + "  " + s1);
//
//            }
//        });
//    }


    public static Context getInstance() {
        return sSoupApp;
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am != null ? am.getRunningAppProcesses() : null;
        Iterator i = l != null ? l.iterator() : null;
        PackageManager pm = this.getPackageManager();
        while (i != null && i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
