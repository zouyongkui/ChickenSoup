package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/24 14:58
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class FloorBean {


    /**
     * msg :
     * floors : []
     * code : 1
     * visitCount : 3
     * community : {"id":"164495cdbdb1491b9db829b7d4a056d4","createtime":1519383703000,"userid":"21bb5796b12a451a82b4343751d53abd","replytime":1519383703000,"title":"新年除暮气，更上一层楼","istop":0,"isonline":1,"content":"又要上班了，好好的，变得更好，做的更好，成为更好(づ ●─● )づ","visitcount":3,"floorsCount":0}
     */

    private String msg;
    private int code;
    private int visitCount;
    private Community community;
    private List<?> floors;

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

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public List<?> getFloors() {
        return floors;
    }

    public void setFloors(List<?> floors) {
        this.floors = floors;
    }

    public static class Community {
        /**
         * id : 164495cdbdb1491b9db829b7d4a056d4
         * createtime : 1519383703000
         * userid : 21bb5796b12a451a82b4343751d53abd
         * replytime : 1519383703000
         * title : 新年除暮气，更上一层楼
         * istop : 0
         * isonline : 1
         * content : 又要上班了，好好的，变得更好，做的更好，成为更好(づ ●─● )づ
         * visitcount : 3
         * floorsCount : 0
         */

        private String id;
        private long createtime;
        private String userid;
        private long replytime;
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
}
