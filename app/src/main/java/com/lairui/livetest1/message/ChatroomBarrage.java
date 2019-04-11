package com.lairui.livetest1.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:Barrage", flag = 3)
public class ChatroomBarrage extends MessageContent {
    public static final Creator<ChatroomBarrage> CREATOR = new Creator<ChatroomBarrage>() {
        @Override
        public ChatroomBarrage createFromParcel(Parcel source) {
            return new ChatroomBarrage(source);
        }

        @Override
        public ChatroomBarrage[] newArray(int size) {
            return new ChatroomBarrage[size];
        }
    };
    private int type;
    private String content;
    private String extra;

    public ChatroomBarrage() {
    }

    public ChatroomBarrage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("type")) {
                type = jsonObj.optInt("type");
            }

            if (jsonObj.has("content")) {
                content = jsonObj.optString("content");
            }

            if (jsonObj.has("extra")) {
                extra = jsonObj.optString("extra");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected ChatroomBarrage(Parcel in) {


        type = ParcelUtils.readIntFromParcel(in);


        content = ParcelUtils.readFromParcel(in);


        extra = ParcelUtils.readFromParcel(in);


    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.put("type", type);

            jsonObj.put("content", content);

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


        ParcelUtils.writeToParcel(dest, type);


        ParcelUtils.writeToParcel(dest, content);


        ParcelUtils.writeToParcel(dest, extra);


    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
