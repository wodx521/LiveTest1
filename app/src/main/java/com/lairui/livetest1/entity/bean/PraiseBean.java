package com.lairui.livetest1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PraiseBean implements Parcelable {
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

    public String getUserArray() {
        return userArray;
    }

    public void setUserArray(String userArray) {
        this.userArray = userArray;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
