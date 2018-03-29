package com.one.zyk.soup.http;

import retrofit2.Retrofit;

/**
 * Created by duan .
 */

public interface RetrofitCallBack {
    void loading();

    Retrofit retrofit();

    <T> void handleObject(T t);
}
