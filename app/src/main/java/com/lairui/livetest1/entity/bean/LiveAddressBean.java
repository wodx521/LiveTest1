package com.lairui.livetest1.entity.bean;

public class LiveAddressBean {

    /**
     * push : {"rtmpurl":"rtmp://heh.play.htcrm.net/lairui/3?auth_key=1551424554-0-0-a06c6ca0fc85b0e40d6c3d06d61b4874"}
     * pull : {"rtmpurl":"rtmp://heh.push.htcrm.net/lairui/3?auth_key=1551424554-0-0-d2c169c5442136ee4e766cc2c7dbb454","flvurl":"http://heh.push.htcrm.net/lairui/3.flv?auth_key=1551424554-0-0-d84a7dceba246ca5a145771402a68cb0","m398url":"http://heh.push.htcrm.net/lairui/3.m3u8?auth_key=1551424554-0-0-3c604bf5c58193205b534f1ae290d4b2"}
     */

    private PushBean push;
    private PullBean pull;

    public PushBean getPush() {
        return push;
    }

    public void setPush(PushBean push) {
        this.push = push;
    }

    public PullBean getPull() {
        return pull;
    }

    public void setPull(PullBean pull) {
        this.pull = pull;
    }

    public static class PushBean {
        /**
         * rtmpurl : rtmp://heh.play.htcrm.net/lairui/3?auth_key=1551424554-0-0-a06c6ca0fc85b0e40d6c3d06d61b4874
         */

        private String rtmpurl;

        public String getRtmpurl() {
            return rtmpurl == null ? "" : rtmpurl;
        }

        public void setRtmpurl(String rtmpurl) {
            this.rtmpurl = rtmpurl;
        }
    }

    public static class PullBean {
        /**
         * rtmpurl : rtmp://heh.push.htcrm.net/lairui/3?auth_key=1551424554-0-0-d2c169c5442136ee4e766cc2c7dbb454
         * flvurl : http://heh.push.htcrm.net/lairui/3.flv?auth_key=1551424554-0-0-d84a7dceba246ca5a145771402a68cb0
         * m398url : http://heh.push.htcrm.net/lairui/3.m3u8?auth_key=1551424554-0-0-3c604bf5c58193205b534f1ae290d4b2
         */

        private String rtmpurl;
        private String flvurl;
        private String m398url;

        public String getRtmpurl() {
            return rtmpurl == null ? "" : rtmpurl;
        }

        public void setRtmpurl(String rtmpurl) {
            this.rtmpurl = rtmpurl;
        }

        public String getFlvurl() {
            return flvurl == null ? "" : flvurl;
        }

        public void setFlvurl(String flvurl) {
            this.flvurl = flvurl;
        }

        public String getM398url() {
            return m398url == null ? "" : m398url;
        }

        public void setM398url(String m398url) {
            this.m398url = m398url;
        }
    }
}
