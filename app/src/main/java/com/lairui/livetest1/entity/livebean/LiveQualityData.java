package com.lairui.livetest1.entity.livebean;

import android.os.Bundle;

import com.tencent.rtmp.TXLiveConstants;
import com.wanou.framelibrary.utils.AppInfoUtils;
import com.wanou.framelibrary.utils.ParseIntUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LiveQualityData {
    /**
     * 正常级别
     */
    public static final int QUALITY_NORMAL = 0;
    /**
     * 警告级别
     */
    public static final int QUALITY_WARNING = 1;
    /**
     * 糟糕级别
     */
    public static final int QUALITY_BAD = 2;

    private String device = "Android";
    /**
     * 发送丢包率
     */
    private double sendLossRate;
    /**
     * 接收丢包率
     */
    private double recvLossRate;
    /**
     * app的cpu使用率
     */
    private double appCPURate;
    /**
     * 系统cpu使用率
     */
    private double sysCPURate;
    /**
     * 发送码率
     */
    private double sendKBps;
    /**
     * 接收码率
     */
    private double recvKBps;
    /**
     * 视频帧率
     */
    private int fps;

    public LiveQualityData() {
        String deviceValue = "Android_" + AppInfoUtils.getLocalVersionName() + "_" + android.os.Build.MODEL;
        setDevice(deviceValue);
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public double getSendLossRate() {
        return sendLossRate;
    }

    public void setSendLossRate(double sendLossRate) {
        this.sendLossRate = sendLossRate;
    }

    public double getRecvLossRate() {
        return recvLossRate;
    }

    public void setRecvLossRate(double recvLossRate) {
        this.recvLossRate = recvLossRate;
    }

    public double getAppCPURate() {
        return appCPURate;
    }

    public void setAppCPURate(double appCPURate) {
        this.appCPURate = appCPURate;
    }

    public double getSysCPURate() {
        return sysCPURate;
    }

    public void setSysCPURate(double sysCPURate) {
        this.sysCPURate = sysCPURate;
    }

    public double getSendKBps() {
        return sendKBps;
    }

    public void setSendKBps(double sendKBps) {
        this.sendKBps = sendKBps;
    }

    public double getRecvKBps() {
        return recvKBps;
    }

    public void setRecvKBps(double recvKBps) {
        this.recvKBps = recvKBps;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * 获得发送丢包率质量级别
     *
     * @return LiveQualityData.QUALITY_XXXX
     */
    public int getSendLossRateQuality() {
        if (sendLossRate <= 0) {
            return QUALITY_NORMAL;
        } else if (sendLossRate <= 20) {
            return QUALITY_NORMAL;
        } else if (sendLossRate <= 30) {
            return QUALITY_WARNING;
        } else {
            return QUALITY_BAD;
        }
    }

    public void parseBundle(Bundle data, boolean isCreater) {
        try {
            String cpu = data.getString(TXLiveConstants.NET_STATUS_CPU_USAGE);
            if (cpu != null) {
                if (cpu.contains("%")) {
                    cpu = cpu.replace("%", "");
                }
                if (cpu.contains("/")) {
                    String[] arrCpu = cpu.split("/");
                    if (arrCpu != null && arrCpu.length == 2) {
                        this.appCPURate = ParseIntUtils.getInt(arrCpu[0]);
                        this.sysCPURate = ParseIntUtils.getInt(arrCpu[1]);
                    }
                }
            }

            int netSpeed = data.getInt(TXLiveConstants.NET_STATUS_NET_SPEED);
            int ara = data.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE);
            int vra = data.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE);
            int total = ara + vra;
            int differ = total - netSpeed < 0 ? 0 : total - netSpeed;

            if (isCreater) {
                if (total > 0) {
                    this.sendLossRate = (new BigDecimal(String.valueOf(differ * 100))).divide(new BigDecimal(String.valueOf(total)), 1, RoundingMode.HALF_UP).doubleValue();
                }
                this.sendKBps = formatSpeed(netSpeed);
            } else {
                this.recvKBps = formatSpeed(netSpeed);
            }
            this.fps = data.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double formatSpeed(double Kbps) {
        double result = 0.0d;
        if (Kbps > 0) {
            result = (new BigDecimal(String.valueOf(Kbps))).divide(new BigDecimal(String.valueOf(8)), 1, RoundingMode.HALF_UP).doubleValue();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\r\n");
        sb.append("sendLossRate:").append(sendLossRate).append("\r\n");
        sb.append("recvLossRate:").append(recvLossRate).append("\r\n");
        sb.append("appCPURate:").append(appCPURate).append("\r\n");
        sb.append("sysCPURate:").append(sysCPURate).append("\r\n");
        sb.append("sendKBps:").append(sendKBps).append("\r\n");
        sb.append("recvKBps:").append(recvKBps).append("\r\n");
        sb.append("fps:").append(fps);
        return sb.toString();
    }
}
