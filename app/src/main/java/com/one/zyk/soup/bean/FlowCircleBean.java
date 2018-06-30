package com.one.zyk.soup.bean;

import java.util.List;

public class FlowCircleBean {


    /**
     * msg : suc
     * code : 0
     * data : [{"picUrl":"/1/20180628/b0f9492c7a514121a246c2266f8fe306.png","createTime":1530165055000,"usrId":"1","updateTime":1530165055000,"id":"4a14e632-a6bb-44d8-b22d-a9bdca77ece9","usrName":"","content":"我是第一条菠萝圈","usrFace":""},{"picUrl":"/1/20180628/471e084aba244412a066c6cadd41c501.png","createTime":1530165055000,"usrId":"1","updateTime":1530165055000,"id":"4a3fc369-686f-4f6b-9093-d413b3f60d95","usrName":"","content":"我是第一条菠萝圈","usrFace":""}]
     */

    private String msg;
    private int code;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * picUrl : /1/20180628/b0f9492c7a514121a246c2266f8fe306.png
         * createTime : 1530165055000
         * usrId : 1
         * updateTime : 1530165055000
         * id : 4a14e632-a6bb-44d8-b22d-a9bdca77ece9
         * usrName :
         * content : 我是第一条菠萝圈
         * usrFace :
         */

        private String picUrl;
        private long createTime;
        private String usrId;
        private long updateTime;
        private String id;
        private String usrName;
        private String content;
        private String usrFace;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUsrId() {
            return usrId;
        }

        public void setUsrId(String usrId) {
            this.usrId = usrId;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsrName() {
            return usrName;
        }

        public void setUsrName(String usrName) {
            this.usrName = usrName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsrFace() {
            return usrFace;
        }

        public void setUsrFace(String usrFace) {
            this.usrFace = usrFace;
        }
    }
}
