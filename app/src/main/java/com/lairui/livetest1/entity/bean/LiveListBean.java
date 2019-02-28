package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveListBean {

    /**
     * total : 4
     * per_page : 15
     * current_page : 1
     * last_page : 1
     * data : [{"id":1,"uid":2,"log_id":null,"category":1,"title":"ceshi","cover":"","notice":"212","status":2},{"id":2,"uid":-1,"log_id":0,"category":1,"title":"ceshi","cover":"","notice":"212","status":2},{"id":3,"uid":1,"log_id":0,"category":1,"title":"ceshi","cover":"","notice":"212","status":2},{"id":100001,"uid":21,"log_id":21,"category":0,"title":"","cover":"","notice":null,"status":2}]
     */
    // 总数据个数
    private int total;
    // 每页数据
    @SerializedName("per_page")
    private int perPage;
    // 当前页数
    @SerializedName("current_page")
    private int currentPage;
    // 最后页数
    @SerializedName("last_page")
    private int lastPage;
    // 数据集合
    private List<LiveRoomBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<LiveRoomBean> getData() {
        return data;
    }

    public void setData(List<LiveRoomBean> data) {
        this.data = data;
    }
}
