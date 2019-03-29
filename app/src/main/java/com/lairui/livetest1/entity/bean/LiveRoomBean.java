package com.lairui.livetest1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LiveRoomBean implements Parcelable {
    /**
     * id : 1
     * uid : 2
     * log_id : null
     * category : 1
     * title : ceshi
     * cover :
     * notice : 212
     * status : 2
     */
    // 房间id
    private String id;
    // 主播id
    private String uid;
    // 房间标题
    private String title;
    // 直播封面
    private String cover;
    // 公告
    private String notice;
    // 状态
    private String status;
    /**
     * notice : null
     * pull : {"rtmpurl":"rtmp://heh.push.htcrm.net/lairui/2121?auth_key=1551696493-0-0-2e1389e5fbb18480c191e79fb506021a","flvurl":"http://heh.push.htcrm.net/lairui/2121.flv?auth_key=1551696493-0-0-1884ff4408b2990c8254c554755a85e5","m398url":"http://heh.push.htcrm.net/lairui/2121.m3u8?auth_key=1551696493-0-0-ca1741efbde8f83a626cf049812945a7"}
     */
    // 推流地址
    private PullBean pull;
    /**
     * portrait : http://192.168.1.106:86/static/img/12.png
     * userNum : 0
     * city : 郑州
     */
    // 头像地址
    private String portrait;
    // 用户数
    private String userNum;
    // 所在城市
    private String city;


    protected LiveRoomBean(Parcel in) {
        id = in.readString();
        uid = in.readString();
        title = in.readString();
        cover = in.readString();
        notice = in.readString();
        status = in.readString();
        pull = in.readParcelable(PullBean.class.getClassLoader());
        portrait = in.readString();
        userNum = in.readString();
        city = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(title);
        dest.writeString(cover);
        dest.writeString(notice);
        dest.writeString(status);
        dest.writeParcelable(pull, flags);
        dest.writeString(portrait);
        dest.writeString(userNum);
        dest.writeString(city);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveRoomBean> CREATOR = new Creator<LiveRoomBean>() {
        @Override
        public LiveRoomBean createFromParcel(Parcel in) {
            return new LiveRoomBean(in);
        }

        @Override
        public LiveRoomBean[] newArray(int size) {
            return new LiveRoomBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PullBean getPull() {
        return pull;
    }

    public void setPull(PullBean pull) {
        this.pull = pull;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static class PullBean implements Parcelable {
        /**
         * rtmpurl : rtmp://heh.push.htcrm.net/lairui/2121?auth_key=1551696493-0-0-2e1389e5fbb18480c191e79fb506021a
         * flvurl : http://heh.push.htcrm.net/lairui/2121.flv?auth_key=1551696493-0-0-1884ff4408b2990c8254c554755a85e5
         * m398url : http://heh.push.htcrm.net/lairui/2121.m3u8?auth_key=1551696493-0-0-ca1741efbde8f83a626cf049812945a7
         */

        private String rtmpurl;
        private String flvurl;
        private String m398url;

        protected PullBean(Parcel in) {
            rtmpurl = in.readString();
            flvurl = in.readString();
            m398url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(rtmpurl);
            dest.writeString(flvurl);
            dest.writeString(m398url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PullBean> CREATOR = new Creator<PullBean>() {
            @Override
            public PullBean createFromParcel(Parcel in) {
                return new PullBean(in);
            }

            @Override
            public PullBean[] newArray(int size) {
                return new PullBean[size];
            }
        };

        public String getRtmpurl() {
            return rtmpurl;
        }

        public void setRtmpurl(String rtmpurl) {
            this.rtmpurl = rtmpurl;
        }

        public String getFlvurl() {
            return flvurl;
        }

        public void setFlvurl(String flvurl) {
            this.flvurl = flvurl;
        }

        public String getM398url() {
            return m398url;
        }

        public void setM398url(String m398url) {
            this.m398url = m398url;
        }
    }
}
