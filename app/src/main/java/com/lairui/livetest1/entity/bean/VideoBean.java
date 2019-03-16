package com.lairui.livetest1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VideoBean implements Parcelable {

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
    // 用户id
    private int userid;
    // 用户标题
    private String title;
    // 视频地址
    private String video;
    // 点赞信息
    private PraiseBean praise;
    // 创建时间
    @SerializedName("create_time")
    private String createTime;
    // 更新时间
    @SerializedName("update_time")
    private String updateTime;
    // 用户昵称
    private String nickname;
    // 用户头像
    private String portrait;

    protected VideoBean(Parcel in) {
        id = in.readInt();
        userid = in.readInt();
        title = in.readString();
        video = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
        nickname = in.readString();
        portrait = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userid);
        dest.writeString(title);
        dest.writeString(video);
        dest.writeString(createTime);
        dest.writeString(updateTime);
        dest.writeString(nickname);
        dest.writeString(portrait);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

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

    public static class PraiseBean implements Parcelable {
        /**
         * user_array : [1,2]
         * num : 2
         */
        @SerializedName("user_array")
        private String userArray;
        private String num;

        protected PraiseBean(Parcel in) {
            userArray = in.readString();
            num = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(userArray);
            dest.writeString(num);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PraiseBean> CREATOR = new Creator<PraiseBean>() {
            @Override
            public PraiseBean createFromParcel(Parcel in) {
                return new PraiseBean(in);
            }

            @Override
            public PraiseBean[] newArray(int size) {
                return new PraiseBean[size];
            }
        };

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUserArray() {
            return userArray;
        }

        public void setUserArray(String userArray) {
            this.userArray = userArray;
        }
    }
}
