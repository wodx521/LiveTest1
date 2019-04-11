package com.lairui.livetest1.entity.jsonparam;

import com.google.gson.annotations.SerializedName;

public class AttentionParams {
    public String token;
    @SerializedName("anchor_uid")
    public String anchorUid;
    private String operate = "favorGroup-saveFavor";

}
