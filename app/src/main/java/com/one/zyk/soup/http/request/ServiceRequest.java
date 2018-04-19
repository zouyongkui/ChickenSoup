package com.one.zyk.soup.http.request;

import android.text.TextUtils;

import com.one.zyk.soup.bean.FloorBean;
import com.one.zyk.soup.http.RetrofitCallBack;
import com.one.zyk.soup.http.JsonConverter;
import com.one.zyk.soup.http.NetError;
import com.one.zyk.soup.http.api.ServiceApi;
import com.one.zyk.soup.utils.LogUtils;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ServiceRequest {
    private static ServiceApi serviceApi;
    private static JsonConverter sJsonConverter = new JsonConverter();

    private static ServiceApi getServiceApi(Retrofit retrofit) {
        if (serviceApi == null) {
            synchronized (ServiceRequest.class) {
                if (serviceApi == null) {
                    serviceApi = retrofit.create(ServiceApi.class);
                }
            }
        }
        return serviceApi;
    }

    public static void getCommunityList(final RetrofitCallBack handle) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getCommunityList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String str) {
                        LogUtils.d(str + "  ss");
                        handle.handleObject(JsonConverter.communityBean(str));
                    }
                });
    }

    public static void updateSoup(final RetrofitCallBack handle, String content, File file) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.updateSoup(content, file).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String str) {

                    }
                });
    }

    public static void getSoup(final RetrofitCallBack handle, String deviceId, String deviceName) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getSoup().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String str) {
                        handle.handleObject(JsonConverter.soupBean(str));
                    }
                });
    }

    public static void getSoupList(final RetrofitCallBack handle) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getSoupList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String str) {
                        handle.handleObject(JsonConverter.soupManageBean(str));
                    }
                });
    }

    public static void delCommentList(final RetrofitCallBack handle, String commentId) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.delComment(commentId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String str) {
                        handle.handleObject(str);
                    }
                });
    }

    public static void postComment(final RetrofitCallBack handle, String userId, String soupId, String comment) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.updateComment(userId, soupId, comment).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("json", e.getMessage());

                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(json);
                    }
                });
    }

    public static void getCommentList(final RetrofitCallBack handle, String soupId) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getCommentList(soupId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(JsonConverter.commentBean(json));
                    }
                });
    }

    public static void getVisitCount(final RetrofitCallBack handle) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getVisitCount().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(JsonConverter.visitCountBean(json));
                    }
                });
    }

    public static void getUserId(final RetrofitCallBack handle, String phoneNum, String deviceId, String deviceName) {
        if (TextUtils.isEmpty(phoneNum)) {
            phoneNum = "-----------";
        }
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getUserId(phoneNum, deviceId, deviceName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(json);
                    }
                });
    }

    public static void getFloors(final RetrofitCallBack handle, String communityId) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getFloors(communityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(sJsonConverter.convert2Bean(FloorBean.class, json));
                    }
                });
    }

    public static void updateFloor(final RetrofitCallBack handle, String communityId, String userId, String content) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.updateFloor(content, userId, communityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(json);
                    }
                });
    }

    public static void updateCommunity(final RetrofitCallBack handle, String userId, String title, String content, MultipartBody.Part body) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.updateCommunity(userId, content, title).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        handle.handleObject(json);
                    }
                });
    }
}
