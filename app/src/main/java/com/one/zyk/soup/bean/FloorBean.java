package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/24 14:58
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class FloorBean {

    private String msg;
    private int code;
    private int visitCount;
    private CommunityBean community;
    private List<FloorsBean> floors;

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

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public CommunityBean getCommunity() {
        return community;
    }

    public void setCommunity(CommunityBean community) {
        this.community = community;
    }

    public List<FloorsBean> getFloors() {
        return floors;
    }

    public void setFloors(List<FloorsBean> floors) {
        this.floors = floors;
    }

    public static class CommunityBean {


        private String id;
        private String createtime;
        private String userid;
        private String replytime;
        private String title;
        private int istop;
        private int isonline;
        private String content;
        private int visitcount;
        private int floorsCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getReplytime() {
            return replytime;
        }

        public void setReplytime(String replytime) {
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getVisitcount() {
            return visitcount;
        }

        public void setVisitcount(int visitcount) {
            this.visitcount = visitcount;
        }

        public int getFloorsCount() {
            return floorsCount;
        }

        public void setFloorsCount(int floorsCount) {
            this.floorsCount = floorsCount;
        }
    }

    public static class FloorsBean {


        private String id;
        private String createtime;
        private String userid;
        private String flcontent;
        private int isonline;
        private String communityid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFlcontent() {
            return flcontent;
        }

        public void setFlcontent(String flcontent) {
            this.flcontent = flcontent;
        }

        public int getIsonline() {
            return isonline;
        }

        public void setIsonline(int isonline) {
            this.isonline = isonline;
        }

        public String getCommunityid() {
            return communityid;
        }

        public void setCommunityid(String communityid) {
            this.communityid = communityid;
        }
    }
}
