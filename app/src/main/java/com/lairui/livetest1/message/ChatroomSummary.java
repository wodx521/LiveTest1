package com.lairui.livetest1.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:Summary", flag = 3)
public class ChatroomSummary extends MessageContent {
    public static final Creator<ChatroomSummary> CREATOR = new Creator<ChatroomSummary>() {
        @Override
        public ChatroomSummary createFromParcel(Parcel source) {
            return new ChatroomSummary(source);
        }

        @Override
        public ChatroomSummary[] newArray(int size) {
            return new ChatroomSummary[size];
        }
    };
    private int online;
    private String extra;

    public ChatroomSummary() {
    }

    public ChatroomSummary(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("online")) {
                online = jsonObj.optInt("online");
            }

            if (jsonObj.has("extra")) {
                extra = jsonObj.optString("extra");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected ChatroomSummary(Parcel in) {


        online = ParcelUtils.readIntFromParcel(in);


        extra = ParcelUtils.readFromParcel(in);


    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.put("online", online);

            jsonObj.put("extra", extra);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        ParcelUtils.writeToParcel(dest, online);


        ParcelUtils.writeToParcel(dest, extra);


    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
