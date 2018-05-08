package com.one.zyk.soup.http.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/4/11 17:38
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class HttpRequest {

    private static OkHttpClient sOkHttpClient = new OkHttpClient();


    public static void post(HashMap<String, Object> map, String url, Callback callback) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() instanceof File) {
                File file = (File) entry.getValue();
                builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            } else if (entry.getValue() instanceof String) {
                builder.addFormDataPart(key, String.valueOf(entry.getValue()));
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


}
