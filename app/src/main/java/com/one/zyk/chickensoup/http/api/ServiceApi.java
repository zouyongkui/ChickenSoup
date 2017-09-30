package com.one.zyk.chickensoup.http.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ServiceApi {
    static int versionCode = 3;

    @GET("/ztcaipiao/app/versionChecker.json")
    Observable<String> versionChecker(@Query("version") String version,
                                      @Query("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("/ztcaipiaomanagement/login")
    Observable<String> login(@Field("username") String account,
                             @Field("password") String passwd);

    //更新鸡汤
    @FormUrlEncoded
    @POST("/soup/updatesoup")
    Observable<String> updateSoup(@Field("content") String content);

    //获取鸡汤
    @GET("/soup/getsoup")
    Observable<String> getSoup(@Query("deviceid") String deviceid, @Query("brandname") String brandname);

    //更新评论
    @FormUrlEncoded
    @POST("/soup/updatecomment")
    Observable<String> updateComment(@Field("userid") String userid, @Field("soupid") String soupid, @Field("content") String content);

    //获取评论
    @GET("/soup/getcomment")
    Observable<String> getcomment(@Query("soupid") String soupid);

    //获取浏览次数
    @GET("/soup/getvisitcount")
    Observable<String> getVisitCount();

    @GET("/soup/getcomment")
    Observable<String> getCommentList(@Query("soupid") String soupId);


    @GET("/soup/getsouplist")
    Observable<String> getSoupList();

    @GET("/soup/delcomment")
    Observable<String> delComment(@Query("commentid") String commentId);
}