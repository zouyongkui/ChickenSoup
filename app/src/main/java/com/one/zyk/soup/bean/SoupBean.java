package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/9/24.
 */

public class SoupBean {


    /**
     * picurl : /20180205/3c7586c4c4ca43c2a5d46aff22f194bd.jpeg
     * soup : 愿你寂寞的时候仍自由 上马杀敌 下马饮酒 快意恩仇
     * soupid : 5f23f10dbd5747d2b5a510375e9f2f79
     * code : 1
     * data : [{"id":"9d40032e82f54512bdb825561c3817b7","content":"还疼吗","createtime":"2018-02-05 19:23:38","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"ff3b77bb40164d05ad2d46a1b611aa42","content":"屁股疼","createtime":"2018-02-05 15:55:12","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"754d3608c3e04e35bebe112ce22da001","content":"这难道是传说中的过年综合征？","createtime":"2018-02-05 15:51:26","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"735c736c4a0a48b2a9bae081f5895426","content":"你们都怎么了朋友。","createtime":"2018-02-05 15:00:35","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"9e9c9cf3464b42cf94a654fe8e6c18df","content":"我听说这个世界上痛苦的数量是一定的，它不会增加也不会减少，只能会从一个人身上转移到另外一个人身上。","createtime":"2018-02-05 13:31:38","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"e752dd91b72e404fad41725d59b7073e","content":"看过武侠吗，有恩必偿，有仇必报。有酒喝酒，有肉吃肉，有女人就泡，有热闹就去凑，有不平就去打抱。不为昨天纠结，不为明天忧愁。天地何变，今昔何兮，我自潇洒恣意。","createtime":"2018-02-05 13:27:28","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"2454631ae7d14796851a8a6d2c928665","content":"楼下你怎么了","createtime":"2018-02-05 13:19:40","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"c1fab259ecdb4f909a8b3c287601292b","content":"我该怎么办","createtime":"2018-02-05 12:44:56","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"f65aa079e25147b88fac6af75c553d0e","content":"总而言之 开心就好啦啦啦","createtime":"2018-02-05 10:51:07","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""},{"id":"462aaf95de4e44b9ac55bbea6ec54242","content":"快意恩仇什么意思😮","createtime":"2018-02-05 10:33:19","soupid":"5f23f10dbd5747d2b5a510375e9f2f79","userid":""}]
     */

    private String picurl;
    private String soup;
    private String soupid;
    private int code;
    private List<DataBean> data;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    public String getSoupid() {
        return soupid;
    }

    public void setSoupid(String soupid) {
        this.soupid = soupid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 9d40032e82f54512bdb825561c3817b7
         * content : 还疼吗
         * createtime : 2018-02-05 19:23:38
         * soupid : 5f23f10dbd5747d2b5a510375e9f2f79
         * userid :
         */

        private String id;
        private String content;
        private String createtime;
        private String soupid;
        private String userid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getSoupid() {
            return soupid;
        }

        public void setSoupid(String soupid) {
            this.soupid = soupid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
