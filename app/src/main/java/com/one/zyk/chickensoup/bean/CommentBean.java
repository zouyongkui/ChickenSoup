package com.one.zyk.chickensoup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/9/23.
 */

public class CommentBean {

    /**
     * code : 1
     * data : [{"id":"9e809ae6ecc5492e90ac2113cc43d8d9","content":"添加了一体哦啊新平路那","createtime":1506260420000,"soupid":"1ac211c05f204e2ba129442759235624","userid":"sss"},{"id":"4f795ed0defa46b1b71d7ff2d64cdb45","content":"添加了一体哦啊新平路那","createtime":1506260431000,"soupid":"1ac211c05f204e2ba129442759235624","userid":"sss"},{"id":"e598897fdc38498985a6971a5b2ae001","content":"fa sa ","createtime":1506262121000,"soupid":"1ac211c05f204e2ba129442759235624","userid":"fas f"}]
     */

    private int code;
    private List<DataBean> data;

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
         * id : 9e809ae6ecc5492e90ac2113cc43d8d9
         * content : 添加了一体哦啊新平路那
         * createtime : 1506260420000
         * soupid : 1ac211c05f204e2ba129442759235624
         * userid : sss
         */

        private String id;
        private String content;
        private long createtime;
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

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
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
