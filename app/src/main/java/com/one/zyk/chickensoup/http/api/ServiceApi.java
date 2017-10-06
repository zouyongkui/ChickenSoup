package com.one.zyk.chickensoup.http.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ServiceApi {
    //更新鸡汤
    @FormUrlEncoded
    @POST("api2api2/soup/updatesoup")
    Observable<String> updateSoup(@Field("content") String content);

    //获取鸡汤
    @GET("api2/soup/getsoup")
    Observable<String> getSoup(@Query("deviceid") String deviceid, @Query("brandname") String brandname);

    //更新评论
    @FormUrlEncoded
    @POST("api2/soup/updatecomment")
    Observable<String> updateComment(@Field("userid") String userid, @Field("soupid") String soupid, @Field("content") String content);

    //获取评论
    @GET("api2/soup/getcomment")
    Observable<String> getcomment(@Query("soupid") String soupid);

    //获取浏览次数
    @GET("api2/soup/getvisitcount")
    Observable<String> getVisitCount();

    @GET("api2/soup/getcomment")
    Observable<String> getCommentList(@Query("soupid") String soupId);


    @GET("api2/soup/getsouplist")
    Observable<String> getSoupList();

    @GET("api2/soup/delcomment")
    Observable<String> delComment(@Query("commentid") String commentId);

    @GET("api2/soup/getUserId")
    Observable<String> delComment(@Query("deviceid") String deviceId, @Query("brandname") String brandname);
}