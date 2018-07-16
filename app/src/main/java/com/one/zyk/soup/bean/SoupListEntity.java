package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/9/27.
 */

public class SoupListEntity {


    /**
     * msg : suc
     * code : 0
     * data : [{"id":"402881fd64a1b72c0164a1c1074d0000","createtime":"2018-07-16T06:22:19.000+0000","content":"我是一条测试内容","istop":false,"visitcount":0,"pic":null}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 402881fd64a1b72c0164a1c1074d0000
         * createtime : 2018-07-16T06:22:19.000+0000
         * content : 我是一条测试内容
         * istop : false
         * visitcount : 0
         * pic : null
         */

        private String id;
        private String createtime;
        private String content;
        private boolean istop;
        private int visitcount;
        private Object pic;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isIstop() {
            return istop;
        }

        public void setIstop(boolean istop) {
            this.istop = istop;
        }

        public int getVisitcount() {
            return visitcount;
        }

        public void setVisitcount(int visitcount) {
            this.visitcount = visitcount;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }
    }
}
