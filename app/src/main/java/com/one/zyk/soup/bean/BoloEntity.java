package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/9/24.
 */

public class BoloEntity {

    /**
     * msg : suc
     * code : 0
     * data : {"commentList":[{"id":"402881fd64a1b72c0164a1c453ed0001","content":"好巧 我是测试评论","createtime":"2018-07-16T06:25:56.000+0000","usrid":"0001","replyid":null,"circleid":null,"subjectid":"402881fd64a1b72c0164a1c1074d0000"}],"bolo":{"id":"402881fd64a1b72c0164a1c1074d0000","createtime":"2018-07-16T06:22:19.000+0000","content":"我是一条测试内容","istop":false,"visitcount":0,"pic":null}}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * commentList : [{"id":"402881fd64a1b72c0164a1c453ed0001","content":"好巧 我是测试评论","createtime":"2018-07-16T06:25:56.000+0000","usrid":"0001","replyid":null,"circleid":null,"subjectid":"402881fd64a1b72c0164a1c1074d0000"}]
         * bolo : {"id":"402881fd64a1b72c0164a1c1074d0000","createtime":"2018-07-16T06:22:19.000+0000","content":"我是一条测试内容","istop":false,"visitcount":0,"pic":null}
         */

        private BoloBean bolo;
        private List<CommentListBean> commentList;

        public BoloBean getBolo() {
            return bolo;
        }

        public void setBolo(BoloBean bolo) {
            this.bolo = bolo;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class BoloBean {
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

        public static class CommentListBean {
            /**
             * id : 402881fd64a1b72c0164a1c453ed0001
             * content : 好巧 我是测试评论
             * createtime : 2018-07-16T06:25:56.000+0000
             * usrid : 0001
             * replyid : null
             * circleid : null
             * subjectid : 402881fd64a1b72c0164a1c1074d0000
             */

            private String id;
            private String content;
            private String createtime;
            private String usrid;
            private Object replyid;
            private Object circleid;
            private String subjectid;

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

            public String getUsrid() {
                return usrid;
            }

            public void setUsrid(String usrid) {
                this.usrid = usrid;
            }

            public Object getReplyid() {
                return replyid;
            }

            public void setReplyid(Object replyid) {
                this.replyid = replyid;
            }

            public Object getCircleid() {
                return circleid;
            }

            public void setCircleid(Object circleid) {
                this.circleid = circleid;
            }

            public String getSubjectid() {
                return subjectid;
            }

            public void setSubjectid(String subjectid) {
                this.subjectid = subjectid;
            }
        }
    }
}
