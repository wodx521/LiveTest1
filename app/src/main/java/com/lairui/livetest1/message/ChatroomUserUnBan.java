
package com.lairui.livetest1.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:User:UnBan", flag = 3)
public class ChatroomUserUnBan extends MessageContent {
  public ChatroomUserUnBan() {
  }
  public ChatroomUserUnBan(byte[] data) {
    String jsonStr = null;
    try {
        jsonStr = new String(data, "UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    try {
        JSONObject jsonObj = new JSONObject(jsonStr);
        
          if (jsonObj.has("id")){
            id = jsonObj.optString("id");
          }
        
          if (jsonObj.has("extra")){
            extra = jsonObj.optString("extra");
          }
        
    } catch (JSONException e) {
        e.printStackTrace();
    }
  }
  @Override
  public byte[] encode() {
    JSONObject jsonObj = new JSONObject();
    try {
        
            jsonObj.put("id", id);
        
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
      
    
      
         ParcelUtils.writeToParcel(dest, extra);
      
    
  }
  protected ChatroomUserUnBan(Parcel in) {
    
      
        id = ParcelUtils.readFromParcel(in);
      
    
      
        extra = ParcelUtils.readFromParcel(in);
      
    
  }
  public static final Creator<ChatroomUserUnBan> CREATOR = new Creator<ChatroomUserUnBan>() {
    @Override
    public ChatroomUserUnBan createFromParcel(Parcel source) {
        return new ChatroomUserUnBan(source);
    }
    @Override
    public ChatroomUserUnBan[] newArray(int size) {
        return new ChatroomUserUnBan[size];
    }
  };
  
    private String id;
    public void setId(   String id) {
        this.id = id;
    }
    public String getId() {
      return id;
    }
  
    private String extra;
    public void setExtra(   String extra) {
        this.extra = extra;
    }
    public String getExtra() {
      return extra;
    }
  
}
