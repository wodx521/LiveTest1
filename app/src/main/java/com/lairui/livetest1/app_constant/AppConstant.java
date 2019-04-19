package com.lairui.livetest1.app_constant;

public class AppConstant {
    // 融云唯一标识App Key
    public static final String RONG_CLOUD_APP_KEY = "cpj2xarlct7pn";
    // 自测appkey
//    public static final String RONG_CLOUD_APP_KEY = "82hegw5u8xypx";

    //    public static final String BASE_URL = "http://192.168.1.109:86";
    public static final String BASE_URL = "http://live.qilinpz.com/";
    public static final int REGISTER = 1;
    public static final int EDIT_INFO = 2;
    public static final int CHANG_INFO = 3;
    public static final int REQUEST_CODE_CHOOSE = 4;
    public static final int REQUEST_CODE_CHOOSE_FRONT = 5;
    public static final int REQUEST_CODE_CHOOSE_BACK = 6;
    public static final int REQUEST_CODE_CHOOSE_HAND = 7;
    public static final int REQUEST_CODE_CHOOSE_COVER = 8;

    public static final int CLICK_TIME_OUT = 1000;

    /**
     * 推流视频质量
     */
    public static final class VideoQualityType {
        /**
         * 标清
         */
        public static final int VIDEO_QUALITY_STANDARD = 0;
        /**
         * 高清
         */
        public static final int VIDEO_QUALITY_HIGH = 1;
        /**
         * 超清
         */
        public static final int VIDEO_QUALITY_SUPER = 2;
    }

}
