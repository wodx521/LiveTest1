package com.lairui.livetest1.entity.jsonparam;

import com.google.gson.annotations.SerializedName;

public class CommentParams {
    public String operate;
    @SerializedName("video_id")
    public String videoId;
    public String token;
}
