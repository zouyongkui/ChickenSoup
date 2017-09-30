package com.one.zyk.chickensoup.http;

import retrofit2.Retrofit;

/**
 * Created by duan .
 */

public interface Handle {
    void loading();

    Retrofit retrofit();

    void handleObject(Object o);
}
