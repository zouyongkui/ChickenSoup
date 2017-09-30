package com.one.zyk.chickensoup;

import android.app.Application;
import android.content.Context;
import android.provider.CalendarContract;

import com.one.zyk.chickensoup.http.PublicParamInterceptor;
import com.one.zyk.chickensoup.http.Urls;
import com.one.zyk.chickensoup.utils.DeviceUtils;

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
    public static Context context;
    private Retrofit retrofit;
    public static String IMEI = "";
    public static String BRAND = "";

    public Retrofit retrofit() {
        return retrofit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initNetConfig();
        IMEI = DeviceUtils.getIMEI();
        BRAND = DeviceUtils.getDeviceBrand() + DeviceUtils.getSystemModel();
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
                .baseUrl(Urls.BASEURL)
                //httpclient
                .client(client)
                //请求对象转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //返回对象转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
