package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

public class VideoBean{

    /**
     * id : 1
     * userid : 1
     * title : 首发
     * video : www.baidu.com
     * praise : {"user_array":"[1,2]","num":2}
     * create_time : 2019-02-27 14:12:55
     * update_time : 2019-02-27 14:12:55
     * nickname : ceshi2
     * portrait : /static/img/12.png
     */

    private int id;
    private int userid;
    private String title;
    private String video;
    private PraiseBean praise;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("update_time")
    private String updateTime;
    private String nickname;
    private String portrait;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public PraiseBean getPraise() {
        return praise;
    }

    public void setPraise(PraiseBean praise) {
        this.praise = praise;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public static class PraiseBean {
        /**
         * user_array : [1,2]
         * num : 2
         */

        private String user_array;
        private int num;

        public String getUser_array() {
            return user_array;
        }

        public void setUser_array(String user_array) {
            this.user_array = user_array;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
