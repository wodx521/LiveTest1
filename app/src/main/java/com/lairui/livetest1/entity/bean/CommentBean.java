package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommentBean {

    /**
     * id : 1
     * video_id : 1
     * userid : {"id":1,"nickname":"12","portrait":"、"}
     * atuserid : null
     * content : 我来了
     * parent_id : 0
     * praise : [2,3,5]
     * create_time : 1970-01-01 08:00:00
     * children : [{"id":5,"video_id":1,"userid":{"id":2,"nickname":"ceshi2","portrait":"、"},"atuserid":{"id":1,"nickname":"12","portrait":"、"},"content":"我又来了","parent_id":1,"praise":null,"create_time":"2019-02-19 14:56:53","children":[]}]
     */

    private int id;
    @SerializedName("video_id")
    private int videoId;
    private UseridBean userid;
    private UseridBean atuserid;
    private String content;
    @SerializedName("parent_id")
    private String parentId;
    private String praise;
    @SerializedName("create_time")
    private String createTime;
    private List<CommentBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public UseridBean getUserid() {
        return userid;
    }

    public void setUserid(UseridBean userid) {
        this.userid = userid;
    }

    public UseridBean getAtuserid() {
        return atuserid;
    }

    public void setAtuserid(UseridBean atuserid) {
        this.atuserid = atuserid;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId == null ? "" : parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPraise() {
        return praise == null ? "" : praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CommentBean> getChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<CommentBean> children) {
        this.children = children;
    }
}
