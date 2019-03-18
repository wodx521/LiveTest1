package com.lairui.livetest1.entity.jsonparam;

import com.google.gson.annotations.SerializedName;

public class SendCommentParams {
    public String operate;
    public String userid;
    public String atuserid;
    @SerializedName("video_id")
    public String videoId;
    public String content;
    public String id;
    public String token;

}
