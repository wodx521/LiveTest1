package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchListBean {

    /**
     * total : 1
     * per_page : 15
     * current_page : 1
     * last_page : 1
     * data : [{"phone":"15238905181","email":null,"portrait":"/static/img/12.png","alipay":null,"sex":"男"}]
     */
    // 总页数
    private int total;
    // 每页数量
    @SerializedName("per_page")
    private int perPage;
    // 当前页数
    @SerializedName("current_page")
    private int currentPage;
    // 最后页
    @SerializedName("last_page")
    private int lastPage;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
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

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * phone : 15238905181
         * email : null
         * portrait : /static/img/12.png
         * alipay : null
         * sex : 男
         */
        // 手机号
        private String phone;
        // email
        private String email;
        // 头像
        private String portrait;
        // 支付宝
        private String alipay;
        // 性别
        private String sex;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
