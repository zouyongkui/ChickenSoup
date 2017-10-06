package com.one.zyk.chickensoup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/10/6.
 */

public class CommunityBean {

    /**
     * msg :
     * code : 1
     * communityList : [{"id":"6f4ce39f1c2e4d0aa764bab18722f7d4","createtime":1507299540000,"userid":"asf as s","replytime":1507299540000,"title":"biaot","istop":0,"isonline":1,"visitcount":0,"content":null}]
     */

    private String msg;
    private int code;
    private List<CommunityListBean> communityList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CommunityListBean> getCommunityList() {
        return communityList;
    }

    public void setCommunityList(List<CommunityListBean> communityList) {
        this.communityList = communityList;
    }

    public static class CommunityListBean {
        /**
         * id : 6f4ce39f1c2e4d0aa764bab18722f7d4
         * createtime : 1507299540000
         * userid : asf as s
         * replytime : 1507299540000
         * title : biaot
         * istop : 0
         * isonline : 1
         * visitcount : 0
         * content : null
         */

        private String id;
        private long createtime;
        private String userid;
        private long replytime;
        private String title;
        private int istop;
        private int isonline;
        private int visitcount;
        private Object content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public long getReplytime() {
            return replytime;
        }

        public void setReplytime(long replytime) {
            this.replytime = replytime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIstop() {
            return istop;
        }

        public void setIstop(int istop) {
            this.istop = istop;
        }

        public int getIsonline() {
            return isonline;
        }

        public void setIsonline(int isonline) {
            this.isonline = isonline;
        }

        public int getVisitcount() {
            return visitcount;
        }

        public void setVisitcount(int visitcount) {
            this.visitcount = visitcount;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }
    }
}
