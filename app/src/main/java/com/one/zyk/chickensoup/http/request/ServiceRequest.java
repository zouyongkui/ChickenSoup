package com.one.zyk.chickensoup.http.request;

import android.util.Log;

import com.one.zyk.chickensoup.http.Handle;
import com.one.zyk.chickensoup.http.JsonConverter;
import com.one.zyk.chickensoup.http.NetError;
import com.one.zyk.chickensoup.http.api.ServiceApi;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ServiceRequest {
    private static ServiceApi serviceApi;

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


/*
    public static void versionChecker(final Handle handle, String version, int versionCode) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.versionChecker(version, versionCode).subscribeOn(Schedulers.io())
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
                    public void onNext(String o) {
                        Log.e("v---", o + "  ss");
                        handle.handleObject("");
                    }
                });
    }
*/

    public static void updateSoup(final Handle handle, String content) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.updateSoup(content).subscribeOn(Schedulers.io())
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
                        Log.e("v---", str + "  ss");
                    }
                });
    }

    public static void getSoup(final Handle handle, String deviceId, String deviceName) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.getSoup(deviceId, deviceName).subscribeOn(Schedulers.io())
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
                        Log.e("v---", str + "  ss");
                        handle.handleObject(JsonConverter.soupBean(str));
                    }
                });
    }

    public static void getSoupList(final Handle handle) {
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
                        Log.e("v---", str + "  ss");
                        handle.handleObject(JsonConverter.soupManageBean(str));
                    }
                });
    }

    public static void delCommentList(final Handle handle, String commentId) {
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
                        Log.e("v---", str + "  ss");
                        handle.handleObject(str);
                    }
                });
    }

    public static void postComment(final Handle handle, String userId, String soupId, String comment) {
        ServiceApi serviceApi = getServiceApi(handle.retrofit());
        serviceApi.updateComment(userId, soupId, comment).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("json", e.getMessage());

                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        Log.e("json", json);
                        handle.handleObject(JsonConverter.commentBean(json));
                    }
                });
    }

    public static void getCommentList(final Handle handle, String soupId) {
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

    public static void getVisitCount(final Handle handle) {
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
}
