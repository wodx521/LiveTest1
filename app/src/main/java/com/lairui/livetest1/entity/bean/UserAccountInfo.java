package com.lairui.livetest1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccountInfo implements Parcelable {
    /**
     * id : 20
     * phone : 13200000005
     * email : null
     * Stringegral : 0
     * nickname : 测试5
     * portrait : http://192.168.1.109:86/static/img/1.png
     * alipay : null
     * sex : 保密
     * grade : 新人
     */
    // 用户Id
    private String id;
    // 手机号
    private String phone;
    // 邮箱
    private String email;
    // 经验值
    private String integral;
    // 昵称
    private String nickname;
    // 头像
    private String portrait;
    // 支付宝
    private String alipay;
    // 性别
    private String sex;
    // 级别
    private String grade;

    protected UserAccountInfo(Parcel in) {
        id = in.readString();
        phone = in.readString();
        email = in.readString();
        integral = in.readString();
        nickname = in.readString();
        portrait = in.readString();
        alipay = in.readString();
        sex = in.readString();
        grade = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(integral);
        dest.writeString(nickname);
        dest.writeString(portrait);
        dest.writeString(alipay);
        dest.writeString(sex);
        dest.writeString(grade);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserAccountInfo> CREATOR = new Creator<UserAccountInfo>() {
        @Override
        public UserAccountInfo createFromParcel(Parcel in) {
            return new UserAccountInfo(in);
        }

        @Override
        public UserAccountInfo[] newArray(int size) {
            return new UserAccountInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
