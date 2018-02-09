package com.one.zyk.soup.http;

import com.google.gson.Gson;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.bean.CommunityBean;
import com.one.zyk.soup.bean.SoupBean;
import com.one.zyk.soup.bean.SoupManageBean;
import com.one.zyk.soup.bean.VisitCountBean;

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
        return gson.fromJson(json, SoupBean.class);
    }

    public static CommunityBean communityBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CommunityBean.class);
    }

    public static SoupManageBean soupManageBean(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SoupManageBean.class);
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
