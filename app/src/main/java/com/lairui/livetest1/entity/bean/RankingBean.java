package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingBean {
    /**
     * list : [{"total":"10.00","id":3,"uid":{"id":1,"nickname":"ceshi2","portrait":"/static/img/12.png","sex":"男"}}]
     * count : 2
     * page_mum : 1
     */
    // 总条数
    private String count;
    // 总页数
    @SerializedName("page_mum")
    private String pageNum;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * total : 10.00
         * id : 3
         * uid : {"id":1,"nickname":"ceshi2","portrait":"/static/img/12.png","sex":"男"}
         */
        // 用户总收入/支出
        private String total;
        private int id;
        private UidBean uid;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public UidBean getUid() {
            return uid;
        }

        public void setUid(UidBean uid) {
            this.uid = uid;
        }

        public static class UidBean {
            /**
             * id : 1
             * nickname : ceshi2
             * portrait : /static/img/12.png
             * sex : 男
             */
            // 用户id
            private String id;
            // 用户昵称
            private String nickname;
            // 用户头像
            private String portrait;
            // 用户性别
            private String sex;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }
    }
}
