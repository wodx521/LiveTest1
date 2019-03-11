package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

public class LoginBean {

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

    private int id;
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
    /**
     * email : null
     * alipay : null
     * room_id : 100007
     * imtoken : OiTaI9VwlHRd8pQpqWQBXNeVOL6pZW48Ae5nqY9D13ltTy1B0ygVmLxYu2U/lERqVgcLscE6l4AsgKduzNc5dw==
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }
}
