package com.one.zyk.chickensoup.http;

import com.google.gson.Gson;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.bean.SoupBean;
import com.one.zyk.chickensoup.bean.SoupManageBean;
import com.one.zyk.chickensoup.bean.VisitCountBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duan .
 */

public class JsonConverter {
    /**
     * 直播地址
     */
    public static String getLiveUrl(String o) {
        try {
            JSONObject jsonObject = new JSONObject(o);
            return jsonObject.getJSONObject("data").getString("videoUrl");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static SoupBean soupBean(String json) {
        Gson gson = new Gson();
        SoupBean bean = gson.fromJson(json, SoupBean.class);
        return bean;
    }

    public static SoupManageBean soupManageBean(String json) {
        Gson gson = new Gson();
        SoupManageBean bean = gson.fromJson(json, SoupManageBean.class);
        return bean;
    }

    public static CommentBean commentBean(String json) {
        Gson gson = new Gson();
        CommentBean bean = gson.fromJson(json, CommentBean.class);
        return bean;
    }

    public static VisitCountBean visitCountBean(String json) {
        Gson gson = new Gson();
        VisitCountBean bean = gson.fromJson(json, VisitCountBean.class);
        return bean;
    }
}
