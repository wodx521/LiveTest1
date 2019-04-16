package com.lairui.livetest1.entity.bean;

import java.io.Serializable;

/**
 * 观众
 */
public class AudienceBean implements Serializable {

    private String anchorId; //主播id
    private String cover;//用户头像地址url
    private String name;  //昵称

    public String getAnchorId() {
        return anchorId == null ? "" : anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
