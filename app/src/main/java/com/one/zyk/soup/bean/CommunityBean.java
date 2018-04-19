package com.one.zyk.soup.bean;

import java.util.List;

/**
 * Created by zyk on 2017/10/6.
 */

public class CommunityBean {


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
         * id : ac96ad8542834a4280121caa740f98b1
         * createtime : 2017-12-18 07:46:18
         * userid : 4484cf0f84d44502839cd50e366478ef
         * replytime : 2017-12-19 18:38:27
         * title : 《愿你不再被扎心》
         * istop : 0
         * isonline : 1
         * content : 你无需加班到深夜，
         骑着共享单车在寒风中瑟瑟发抖，
         也不见得会让自己的钱包变得更鼓。

         你无需讨好身边的每一个领导，
         忍辱负重，
         也不见得升职加薪会有你一个名额。

         你无需吃着路边摊8块一份的麻辣烫，
         幻想着餐厅里的西餐红酒与烛光。

         你无需穿着淘宝网最便宜的衣裳，
         期望着可以成为遇见王子的灰姑娘。

         你无需喝酒喝到胃穿孔，呕吐不止，
         也不见得会真的得到尊重与赏识。

         你无需躺在旧房子的床上，
         眺望着远方高档小区的彻夜灯光。

         你无需踩着已经不是很灵光的刹车，
         咒骂着已经甩你很远的豪华跑车和来往车辆。

         你无需拿着从微商买来的A款名包，
         装作好像真的去过了巴黎世家买了普拉达。

         你无需牵着女朋友的手，
         和他讲述你未来会如何成功，
         送她的礼物会多么令人羡慕与豪华。

         你无需向别人讲述你谈了多少恋爱，
         以此证明你的前半生活得还算有些魅力。

         你无需因为沉重的房租水电账单，
         在深夜里一个人想着如何逃离北上广。

         你无需看着携程上的打折机票
         憧憬着迈阿密海滩的温暖日光与荷兰的风车。

         你无需等到双11，双12，看看哪些东西会打折，
         才有勇气去清空购物车

         你无需害怕明天的早餐没有着落，
         拖着疲惫不堪的身躯在深夜里忍饥挨饿。

         你无需时隔很久后的归家。
         不经意间看到父母吃着白天的剩菜，
         而觉得自卑自责因此潸然泪下。
         * visitcount : 48
         * floorsCount : 0
         */

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
}
