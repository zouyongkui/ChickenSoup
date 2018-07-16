package com.one.zyk.soup.http.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.one.zyk.soup.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/4/11 17:38
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class HttpRequest {
    private static final int ON_RESPONSE = 485;
    private static final int ON_FAIL = 828;
    private static OkHttpClient sOkHttpClient = new OkHttpClient();
    private static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_RESPONSE:
                    break;
                case ON_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    public interface MyCallBack<T> {

        void onSuccess(String str);

        void onFail(IOException e);

    }


    public static void post(HashMap<String, Object> map, String url, final MyCallBack callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
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
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull final Response response) throws IOException {
                final String str = response.body() != null ? response.body().string() : "";
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(str);
                        LogUtils.d(str);
                    }
                });
            }
        });
    }
}
