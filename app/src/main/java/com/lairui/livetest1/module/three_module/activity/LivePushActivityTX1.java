package com.lairui.livetest1.module.three_module.activity;

import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lairui.livetest1.R;
import com.lairui.livetest1.dialog.BaseToolDialog;
import com.lairui.livetest1.dialog.BeautySettingDialog;
import com.lairui.livetest1.dialog.ExitLiveDialog;
import com.lairui.livetest1.dialog.MsgDialog;
import com.lairui.livetest1.dialog.MsgListDialog;
import com.lairui.livetest1.dialog.ShareDialog;
import com.lairui.livetest1.entity.bean.ShareBean;
import com.lairui.livetest1.entity.jsonparam.BaseParams;
import com.lairui.livetest1.entity.livebean.BeautyEffectBean;
import com.lairui.livetest1.module.three_module.presenter.LivePushPresenterTX;
import com.lairui.livetest1.module.three_module.presenter.LivePushPresenterTX1;
import com.lairui.livetest1.utils.ObjectBox;
import com.lairui.livetest1.widget.CountDownTimerUtils;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.objectbox.Box;

import static com.tencent.rtmp.TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO;

/**
 * 腾讯直播Sdk
 */
public class LivePushActivityTX1 extends BaseMvpActivity<LivePushPresenterTX1> implements View.OnClickListener {
    private TXCloudVideoView videoView;
    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private ImageView ivCloseLive;
    private TextView tvCountDownTime;
    private ImageView ivSendMsg;
    private ImageView ivOperate;
    private ImageView ivMessageList;
    private ImageView ivShare;
    private ConstraintLayout clBottomView;
    private String pushUrl;
    private boolean frontCamera = true;
    //
    private boolean isFlashLightOn = false;
    // true 为观众与主播同视角,  false 反视角
    private boolean isMirrorOn = false;
    private boolean isMicOn = false;
    private BaseParams baseParams = new BaseParams();
    private CountDownTimerUtils countDownTimerUtils;
    private List<ShareBean> shareBeanList = new ArrayList<>();
    private List<String> tabTitleList;
    private List<String> baseToolList;
    private List<String> baseToolList1;
    private int[] shareImageResArr = {
            R.drawable.umeng_socialize_wechat,
            R.drawable.umeng_socialize_wxcircle,
            R.drawable.umeng_socialize_qq,
            R.drawable.umeng_socialize_qzone,
            R.drawable.umeng_socialize_sina};

    private Integer[] baseToolImageArr = {
            R.drawable.selector_music,
            R.drawable.selector_beauty,
            R.drawable.selector_mike,
            R.drawable.selector_change_camera,
            R.drawable.selector_mirror};

    private Integer[] baseToolImageArr1 = {
            R.drawable.selector_music,
            R.drawable.selector_beauty,
            R.drawable.selector_mike,
            R.drawable.selector_change_camera,
            R.drawable.selector_flash};
    private List<Integer> baseToolImageList;
    private List<Integer> baseToolImageList1;
    private List<String> beautyTypeList;
    private int mBeautyLevel = 5;
    private int mWhiteningLevel = 3;
    private int mRuddyLevel = 2;
    private BeautyEffectBean beautyEffectBean;

    @Override
    protected LivePushPresenterTX1 getPresenter() {
        return new LivePushPresenterTX1();
    }

    @Override
    protected void initData() {
        shareBeanList.clear();
        String[] stringArray = UiTools.getStringArray(R.array.shareList);
        for (int i = 0; i < stringArray.length; i++) {
            shareBeanList.add(new ShareBean(stringArray[i], shareImageResArr[i]));
        }
        // 滤镜选项
        String[] beautyTypeArr = UiTools.getStringArray(R.array.beautyType);
        beautyTypeList = Arrays.asList(beautyTypeArr);
        // 消息列表tablayout TITLE
        String[] tabTitleArr = UiTools.getStringArray(R.array.tabTitle);
        tabTitleList = Arrays.asList(tabTitleArr);
        // 基础设置列表
        String[] baseToolArr = UiTools.getStringArray(R.array.baseToolList);
        String[] baseToolArr1 = UiTools.getStringArray(R.array.baseToolList1);
        baseToolList = Arrays.asList(baseToolArr);
        baseToolList1 = Arrays.asList(baseToolArr1);
        // 基础设置图片
        baseToolImageList = Arrays.asList(baseToolImageArr);
        baseToolImageList1 = Arrays.asList(baseToolImageArr1);
        Box<BeautyEffectBean> beautyEffectBeanBox = ObjectBox.getBoxStore().boxFor(BeautyEffectBean.class);
        if (beautyEffectBeanBox.isEmpty()) {
            beautyEffectBean = new BeautyEffectBean();
            long beautyId = beautyEffectBeanBox.put(beautyEffectBean);
            SpUtils.put("beautyId", beautyId);
        } else {
            long beautyId = (long) SpUtils.get("beautyId", -1L);
            if (beautyId != -1) {
                beautyEffectBean = beautyEffectBeanBox.get(beautyId);
            }
        }


        mLivePusher = new TXLivePusher(this);
        // 设置推流视频质量
        setVideoQuality();

        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.startCameraPreview(videoView);
        if (mBundle != null) {
            pushUrl = mBundle.getString("pushUrl", "");
        }

        countDownTimerUtils = new CountDownTimerUtils(3, tvCountDownTime);
        countDownTimerUtils.start();
        countDownTimerUtils.setOnCountDownFinish(new CountDownTimerUtils.OnCountDownFinish() {
            @Override
            public void finish() {
                if (UiTools.noEmpty(pushUrl)) {
                    mLivePusher.startPusher(pushUrl);
                }
            }
        });
    }


    private void setVideoQuality() {
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        // 后台时只采集音频
        mLivePushConfig.setPauseFlag(PAUSE_FLAG_PAUSE_VIDEO);
        mLivePushConfig.setFrontCamera(frontCamera);
        mLivePushConfig.setTouchFocus(false);
        mLivePushConfig.setBeautyFilter(mBeautyLevel, mWhiteningLevel, mRuddyLevel);
        mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION, false, false);
        mLivePushConfig.setVideoBitrate(1000); // 初始码率
        mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE); // 软硬解码
        mLivePushConfig.setAutoAdjustBitrate(false); // 码率自适应
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerUtils.cancel();
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }

    @Override
    protected int getResId() {
        return R.layout.activity_live_push1;
    }


    @Override
    protected void initView() {
        videoView = findViewById(R.id.video_view);
        ivCloseLive = findViewById(R.id.ivCloseLive);
        tvCountDownTime = findViewById(R.id.tvCountDownTime);
        ivSendMsg = findViewById(R.id.ivSendMsg);
        ivOperate = findViewById(R.id.ivOperate);
        ivMessageList = findViewById(R.id.ivMessageList);
        ivShare = findViewById(R.id.ivShare);
        clBottomView = findViewById(R.id.clBottomView);

        initClick();
    }

    private void initClick() {
        ivCloseLive.setOnClickListener(this);
        ivSendMsg.setOnClickListener(this);
        ivOperate.setOnClickListener(this);
        ivMessageList.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSendMsg:
                clBottomView.setVisibility(View.INVISIBLE);
                MsgDialog.getDialog(LivePushActivityTX1.this, false);
                MsgDialog.setmSendClickListener(new MsgDialog.SendClickListener() {
                    @Override
                    public void onSendClickListener(String content, boolean isNormalMsg) {
                        if (isNormalMsg) {
                            UiTools.showToast("弹幕消息" + content);
                        } else {
                            UiTools.showToast("普通消息" + content);
                        }
                    }

                    @Override
                    public void onDismissChange() {
                        clBottomView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case R.id.ivOperate:
                clBottomView.setVisibility(View.INVISIBLE);
                if (frontCamera) {
                    BaseToolDialog.getDialog(LivePushActivityTX1.this, baseToolList, baseToolImageList, null, null, false);
                } else {
                    BaseToolDialog.getDialog(LivePushActivityTX1.this, baseToolList1, baseToolImageList1, null, null, false);
                }
                BaseToolDialog.setItemClickListener(new BaseToolDialog.ItemClickListener() {
                    @Override
                    public void baseToolItemListener(int position) {
                        switch (position) {
                            case 0:

                                break;
                            case 1:
                                clBottomView.setVisibility(View.INVISIBLE);
                                BeautySettingDialog.getDialog(LivePushActivityTX1.this, beautyTypeList);
                                BeautySettingDialog.setmItemClickListener(new BeautySettingDialog.ItemClickListener() {
                                    @Override
                                    public void beautyItemClickListener(int position, Bitmap filterBitmap) {
                                        // 滤镜设置, 根据不同选择设置不同滤镜
                                        mLivePusher.setFilter(filterBitmap);
                                    }

                                    @Override
                                    public void dismissListener() {
                                        clBottomView.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void beautyEffectChangeListener(int style, int beautyProgress, int whiteningProgress, int ruddyProgress) {
                                        mLivePusher.setBeautyFilter(style, beautyProgress, whiteningProgress, ruddyProgress);
                                    }
                                });
                                break;
                            case 2:
                                isMicOn = !isMicOn;
                                mLivePusher.setMute(isMicOn);
                                break;
                            case 3:
                                frontCamera = !frontCamera;
                                mLivePusher.switchCamera();
                                isFlashLightOn = false;
                                isMirrorOn = false;
                                break;
                            case 4:
                                if (!frontCamera) {
                                    if (!mLivePusher.turnOnFlashLight(!isFlashLightOn)) {
                                        UiTools.showToast("打开闪光灯失败:绝大部分手机不支持前置闪光灯!");
                                    }
                                    isFlashLightOn = !isFlashLightOn;
                                } else {
                                    mLivePusher.setMirror(!isMirrorOn);
                                    isMirrorOn = !isMirrorOn;
                                    if (isMirrorOn) {
                                        UiTools.showToast("观众与你看到的是一样的");
                                    } else {
                                        UiTools.showToast("观众与你看到的是相反的");
                                    }
                                }
                                break;
                            default:
                        }
                    }

                    @Override
                    public void featuresItemListener(int position) {

                    }

                    @Override
                    public void dismissListener() {
                        clBottomView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case R.id.ivMessageList:
                MsgListDialog.getDialog(LivePushActivityTX1.this, tabTitleList, null, null, null);
                MsgListDialog.setmIgnoreClickListener(new MsgListDialog.IgnoreClickListener() {
                    @Override
                    public void onIgnoreClickListener() {
                        UiTools.showToast("忽略未读");
                    }
                });
                break;
            case R.id.ivShare:
                ShareDialog.getDialog(LivePushActivityTX1.this, shareBeanList);
                ShareDialog.setOnItemClickListener(new ShareDialog.OnItemClickListener() {
                    @Override
                    public void itemClickListener(int position) {
                        ShareBean shareBean = shareBeanList.get(position);
                        UiTools.showToast(shareBean.shareName);
                    }
                });
                break;
            case R.id.ivCloseLive:
                ExitLiveDialog.getDialog(LivePushActivityTX1.this, "提示", "确定要结束直播吗?");
                ExitLiveDialog.setOnConfirmClickListener(new ExitLiveDialog.OnConfirmClickListener() {
                    @Override
                    public void confirmClickListener() {
                        String token = ObjectBox.getToken();
                        baseParams.operate = "roomGroup-close";
                        baseParams.token = token;
                        mPresenter.exitLive(GsonUtils.toJson(baseParams));
                    }
                });
                break;
            default:
        }
    }

    public void exitSuccess() {
        startActivity(LivePushActivityTX1.this, null, LiveFinishActivity.class);
        finish();
    }


}
