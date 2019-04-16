package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

public class PushAddressBean {

    /**
     * rtmpurl : rtmp://live.push.shienkeji.com/lairui/100009?auth_key=1555396031-0-0-b19b91e83598fd0df227a2e3ee15d3d9
     */

    @SerializedName("rtmpurl")
    private String rtmpurl;

    public String getRtmpurl() {
        return rtmpurl == null ? "" : rtmpurl;
    }

    public void setRtmpurl(String rtmpurl) {
        this.rtmpurl = rtmpurl;
    }
}
