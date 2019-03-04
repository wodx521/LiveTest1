package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

public class LiveRoomBean {
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

    private int id;
    private int uid;
    @SerializedName("log_id")
    private String logId;
    // 分类
    private String category;
    // 直播标题
    private String title;
    // 直播封面
    private String cover;
    //
    private String notice;
    // 状态
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
