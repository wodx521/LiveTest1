package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserInfoBean {

    /**
     * id : 2
     * phone : 15238905181
     * email : null
     * invite : 2122
     * nickname : ceshi2
     * username : lk_1550284499929
     * portrait : 、
     * alipay : null
     * sex : 保密
     * "room_id": "",
     * "token": "54Xz7g4RBOXIO8gh1V8/eNeVOL6pZW48Ae5nqY9D13menYVDJyAq9sZgfZodlq2xipG9X9tgqSssgKduzNc5dw=="
     */
    @Id
    public long mainId;
    @SerializedName("id")
    private String userId;
    private String phone;
    private String email;
    private String invite;
    private String nickname;
    private String username;
    private String portrait;
    private String alipay;
    private String sex;
    @SerializedName("room_id")
    private String roomId;
    private String token;
    private String imtoken;

    public void setEmptyData() {
        setAlipay("");
        setEmail("");
        setImtoken("");
        setInvite("");
        setNickname("");
        setPhone("");
        setPortrait("");
        setRoomId("");
        setSex("");
        setToken("");
        setUserId("");
        setUsername("");
    }

    public void setUpdate(UserInfoBean userInfoBean) {
        setAlipay(userInfoBean.getAlipay());
        setEmail(userInfoBean.getEmail());
        setImtoken(userInfoBean.getImtoken());
        setInvite(userInfoBean.getInvite());
        setNickname(userInfoBean.getNickname());
        setPhone(userInfoBean.getPhone());
        setPortrait(userInfoBean.getPortrait());
        setRoomId(userInfoBean.getRoomId());
        setSex(userInfoBean.getSex());
        setToken(userInfoBean.getToken());
        setUserId(userInfoBean.getUserId());
        setUsername(userInfoBean.getUsername());
    }

    public String getAlipay() {
        return alipay == null ? "" : alipay;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImtoken() {
        return imtoken == null ? "" : imtoken;
    }

    public String getInvite() {
        return invite == null ? "" : invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    /**
     * email : null
     * alipay : null
     * room_id : 100007
     * imtoken : OiTaI9VwlHRd8pQpqWQBXNeVOL6pZW48Ae5nqY9D13ltTy1B0ygVmLxYu2U/lERqVgcLscE6l4AsgKduzNc5dw==
     */


    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait == null ? "" : portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getRoomId() {
        return roomId == null ? "" : roomId;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }
}
