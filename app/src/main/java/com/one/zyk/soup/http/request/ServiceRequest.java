package com.one.zyk.soup.http.request;

import android.util.Log;

import com.one.zyk.soup.http.Handle;
import com.one.zyk.soup.http.JsonConverter;
import com.one.zyk.soup.http.NetError;
import com.one.zyk.soup.http.api.ServiceApi;
import com.one.zyk.soup.utils.LogUtils;

import java.io.File;

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
    //        ServiceApi serviceApi = getServiceApi(handle.retrofit());
    //        serviceApi.versionChecker(version, versionCode).subscribeOn(Schedulers.io())
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .subscribe(new Subscriber<String>() {
    //                    @Override
    //                    public void onCompleted() {
    //                    }
    //
    //                    @Override
    //                    public void onError(Throwable e) {
    //                        NetError netError = new NetError(e.getMessage(), 500);
    //                        handle.handleObject(netError);
    //                    }
    //
    //                    @Override
    //                    public void onNext(String o) {
    //                        LogUtils.d("v---", o + "  ss");
    //                        handle.handleObject("");
    //                    }
    //                });
        }
    */
    public static void getCommunityList(final Handle handle) {
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

    public static void updateSoup(final Handle handle, String content, File file) {
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
                        LogUtils.d(str + "  ss");
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
                        LogUtils.d(str + "  ss");
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
                        LogUtils.d(str + "  ss");
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
                        LogUtils.d(str + "  ss");
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
                        LogUtils.d("json", e.getMessage());

                        NetError netError = new NetError(e.getMessage(), 500);
                        handle.handleObject(netError);
                    }

                    @Override
                    public void onNext(String json) {
                        LogUtils.d("json", json);
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

    public static void getUserId(final Handle handle, String deviceId, String deviceName, String phoneNum) {
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

    public static void getFloors(final Handle handle, String communityId) {
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
                        handle.handleObject(json);
                    }
                });


    }
}
