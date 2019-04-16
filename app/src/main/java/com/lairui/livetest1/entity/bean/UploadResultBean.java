package com.lairui.livetest1.entity.bean;

import com.google.gson.annotations.SerializedName;

public class UploadResultBean {

    /**
     * getExtension : png
     * getSaveName : upload\video\20190219\b807e73a322a3541b0ced2edd3fcfe6b.png
     * savename : b807e73a322a3541b0ced2edd3fcfe6b.png
     */

    @SerializedName("getExtension")
    private String getExtension;
    @SerializedName("getSaveName")
    private String getSaveName;
    @SerializedName("savename")
    private String savename;

    public String getGetExtension() {
        return getExtension == null ? "" : getExtension;
    }

    public void setGetExtension(String getExtension) {
        this.getExtension = getExtension;
    }

    public String getGetSaveName() {
        return getSaveName == null ? "" : getSaveName;
    }

    public void setGetSaveName(String getSaveName) {
        this.getSaveName = getSaveName;
    }

    public String getSavename() {
        return savename == null ? "" : savename;
    }

    public void setSavename(String savename) {
        this.savename = savename;
    }
}
