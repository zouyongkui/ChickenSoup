package com.one.zyk.soup.http.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface ServiceApi {
    //更新鸡汤
    @FormUrlEncoded
    @POST("bolo/api/updateSoup")
    Observable<String> updateSoup(@Field("content") String content, @Field("pic") File file);

    //获取鸡汤
    @GET("bolo/api/getSoup")
    Observable<String> getSoup();

    //更新评论
    @FormUrlEncoded
    @POST("bolo/api/updateComment")
    Observable<String> updateComment(@Field("userId") String userId, @Field("soupId") String soupId, @Field("content") String content);

    //获取评论
    @GET("bolo/api/getComment")
    Observable<String> getComment(@Query("soupId") String soupId);

    //获取浏览次数
    @GET("bolo/api/getVisitCount")
    Observable<String> getVisitCount();

    @GET("bolo/api/getComment")
    Observable<String> getCommentList(@Query("soupId") String soupId);


    @GET("bolo/api/getSoupList")
    Observable<String> getSoupList();

    @GET("bolo/api/delComment")
    Observable<String> delComment(@Query("id") String commentId);

    @GET("bolo/api/getUserId")
    Observable<String> getUserId(@Query("phoneNum") String phoneNum, @Query("deviceId") String deviceId
            , @Query("brandName") String brandName);

    @GET("bolo/community/getFlowCircleList")
    Observable<String> getCommunityList();

    @FormUrlEncoded
    @POST("bolo/community/updateCommunity")
    Observable<String> updateCommunity(@Field("userId") String userId, @Field("content") String content, @Field("title") String title);

    @GET("bolo/community/getFloors")
    Observable<String> getFloors(@Query("communityId") String communityId);

    @FormUrlEncoded
    @POST("bolo/community/updateFloor")
    Observable<String> updateFloor(@Field("content") String content, @Field("userId") String userId, @Field("communityId") String communityId);

    @GET("bolo/api/boloCircle/getCircleList")
    Observable<String> getFlowCircleList(@Query("page") int page, @Query("size") int size);
}