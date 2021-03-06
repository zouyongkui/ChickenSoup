package com.one.zyk.soup.http;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.bean.CommunityBean;
import com.one.zyk.soup.bean.BoloEntity;
import com.one.zyk.soup.bean.SoupListEntity;
import com.one.zyk.soup.bean.VisitCountBean;

import java.lang.reflect.Type;

/**
 * Created by duan .
 */

public class JsonConverter {

    public static <T> T convert2Bean(Class<T> classOfT, String json) {
        Gson gson = new Gson();
        Object object = gson.fromJson(json, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    public static BoloEntity soupBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, BoloEntity.class);
    }

    public static CommunityBean communityBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CommunityBean.class);
    }

    public static SoupListEntity soupManageBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SoupListEntity.class);
    }

    public static CommentBean commentBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CommentBean.class);
    }

    public static VisitCountBean visitCountBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, VisitCountBean.class);
    }
}
