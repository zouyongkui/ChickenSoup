package com.one.zyk.soup.http.request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/4/11 17:38
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class HttpRequest {

    private static void init() {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3000l, TimeUnit.MILLISECONDS)
                .readTimeout(3000l, TimeUnit.MILLISECONDS).build();


    }

    public static void post() {


    }


}
