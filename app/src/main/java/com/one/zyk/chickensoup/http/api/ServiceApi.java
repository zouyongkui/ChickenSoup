package com.one.zyk.chickensoup.http.api;

import java.io.File;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ServiceApi {
    //更新鸡汤
    @FormUrlEncoded
    @POST("soup/api2/updatesoup")
    Observable<String> updateSoup(@Field("content") String content, @Field("pic") File file);

    //获取鸡汤
    @GET("soup/api2/getsoup")
    Observable<String> getSoup(@Query("deviceid") String deviceid, @Query("brandname") String brandname);

    //更新评论
    @FormUrlEncoded
    @POST("soup/api2/updatecomment")
    Observable<String> updateComment(@Field("userid") String userid, @Field("soupid") String soupid, @Field("content") String content);

    //获取评论
    @GET("soup/api2/getcomment")
    Observable<String> getcomment(@Query("soupid") String soupid);

    //获取浏览次数
    @GET("soup/api2/getvisitcount")
    Observable<String> getVisitCount();

    @GET("soup/api2/getcomment")
    Observable<String> getCommentList(@Query("soupid") String soupId);


    @GET("soup/api2/getsouplist")
    Observable<String> getSoupList();

    @GET("soup/api2/delcomment")
    Observable<String> delComment(@Query("commentid") String commentId);

    @GET("soup/api2/getUserId")
    Observable<String> getUserId(@Query("phoneNum") String phoneNum, @Query("deviceid") String deviceId, @Query("brandname") String brandName);
}