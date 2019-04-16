package com.lairui.livetest1.entity.jsonparam;

import com.google.gson.annotations.SerializedName;

public class ApproveParamsBean {
    private String operate = "roomGroup-enter";
    public String name;
    public String phone;
    public String token;
    @SerializedName("card_positive")
    public String cardPositive;
    @SerializedName("card_opposite")
    public String cardOpposite;
    @SerializedName("hand_card")
    public String handCard;
}
