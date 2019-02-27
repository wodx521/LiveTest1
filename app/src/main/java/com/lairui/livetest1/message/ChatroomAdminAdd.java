
package com.lairui.livetest1.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:Admin:Add", flag = 3)
public class ChatroomAdminAdd extends MessageContent {
  public ChatroomAdminAdd() {
  }
  public ChatroomAdminAdd(byte[] data) {
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
        
          if (jsonObj.has("counts")){
            counts = jsonObj.optInt("counts");
          }
        
          if (jsonObj.has("level")){
            level = jsonObj.optInt("level");
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
        
            jsonObj.put("counts", counts);
        
            jsonObj.put("level", level);
        
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
      
    
      
         ParcelUtils.writeToParcel(dest, counts);
      
    
      
         ParcelUtils.writeToParcel(dest, level);
      
    
      
         ParcelUtils.writeToParcel(dest, extra);
      
    
  }
  protected ChatroomAdminAdd(Parcel in) {
    
      
        id = ParcelUtils.readFromParcel(in);
      
    
      
        
          counts = ParcelUtils.readIntFromParcel(in);
        
      
    
      
        
          level = ParcelUtils.readIntFromParcel(in);
        
      
    
      
        extra = ParcelUtils.readFromParcel(in);
      
    
  }
  public static final Creator<ChatroomAdminAdd> CREATOR = new Creator<ChatroomAdminAdd>() {
    @Override
    public ChatroomAdminAdd createFromParcel(Parcel source) {
        return new ChatroomAdminAdd(source);
    }
    @Override
    public ChatroomAdminAdd[] newArray(int size) {
        return new ChatroomAdminAdd[size];
    }
  };
  
    private String id;
    public void setId(   String id) {
        this.id = id;
    }
    public String getId() {
      return id;
    }
  
    private int counts;
    public void setCounts( int    counts) {
        this.counts = counts;
    }
    public  int getCounts() {
      return counts;
    }
  
    private int level;
    public void setLevel( int    level) {
        this.level = level;
    }
    public  int getLevel() {
      return level;
    }
  
    private String extra;
    public void setExtra(   String extra) {
        this.extra = extra;
    }
    public String getExtra() {
      return extra;
    }
  
}
