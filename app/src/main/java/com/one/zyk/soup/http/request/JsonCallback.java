package com.one.zyk.soup.http.request;

import okhttp3.Call;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/4/11 17:34
 * Company ：北京虹宇科技有限公司
 * Function
 */
public interface JsonCallback {
    void onResponse(Call call, String json);
    void onFailure();
}
