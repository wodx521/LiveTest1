package com.lairui.livetest1.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:Gift", flag = 3)
public class ChatroomGift extends MessageContent {
    public static final Creator<ChatroomGift> CREATOR = new Creator<ChatroomGift>() {
        @Override
        public ChatroomGift createFromParcel(Parcel source) {
            return new ChatroomGift(source);
        }

        @Override
        public ChatroomGift[] newArray(int size) {
            return new ChatroomGift[size];
        }
    };
    private String id;
    private int number;
    private int total;
    private String extra;

    public ChatroomGift() {
    }

    public ChatroomGift(byte[] data) {
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

            if (jsonObj.has("number")) {
                number = jsonObj.optInt("number");
            }

            if (jsonObj.has("total")) {
                total = jsonObj.optInt("total");
            }

            if (jsonObj.has("extra")) {
                extra = jsonObj.optString("extra");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected ChatroomGift(Parcel in) {


        id = ParcelUtils.readFromParcel(in);


        number = ParcelUtils.readIntFromParcel(in);


        total = ParcelUtils.readIntFromParcel(in);


        extra = ParcelUtils.readFromParcel(in);


    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.put("id", id);

            jsonObj.put("number", number);

            jsonObj.put("total", total);

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


        ParcelUtils.writeToParcel(dest, number);


        ParcelUtils.writeToParcel(dest, total);


        ParcelUtils.writeToParcel(dest, extra);


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
