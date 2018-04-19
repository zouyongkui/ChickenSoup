package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/9/27.
 */

public class SoupManageBean {


    private int code;
    private List<SoupListBean> soupList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SoupListBean> getSoupList() {
        return soupList;
    }

    public void setSoupList(List<SoupListBean> soupList) {
        this.soupList = soupList;
    }

    public static class SoupListBean {


        private String id;
        private String createtime;
        private String content;
        private Object istop;
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

        public Object getIstop() {
            return istop;
        }

        public void setIstop(Object istop) {
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
