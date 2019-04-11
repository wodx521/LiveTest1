package com.lairui.livetest1.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:User:Block", flag = 3)
public class ChatroomUserBlock extends MessageContent {
    public static final Creator<ChatroomUserBlock> CREATOR = new Creator<ChatroomUserBlock>() {
        @Override
        public ChatroomUserBlock createFromParcel(Parcel source) {
            return new ChatroomUserBlock(source);
        }

        @Override
        public ChatroomUserBlock[] newArray(int size) {
            return new ChatroomUserBlock[size];
        }
    };
    private String id;
    private int duration;
    private String extra;

    public ChatroomUserBlock() {
    }

    public ChatroomUserBlock(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("id")) {
                id = jsonObj.optString("id");
            }

            if (jsonObj.has("duration")) {
                duration = jsonObj.optInt("duration");
            }

            if (jsonObj.has("extra")) {
                extra = jsonObj.optString("extra");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected ChatroomUserBlock(Parcel in) {


        id = ParcelUtils.readFromParcel(in);


        duration = ParcelUtils.readIntFromParcel(in);


        extra = ParcelUtils.readFromParcel(in);


    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.put("id", id);

            jsonObj.put("duration", duration);

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


        ParcelUtils.writeToParcel(dest, id);


        ParcelUtils.writeToParcel(dest, duration);


        ParcelUtils.writeToParcel(dest, extra);


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
