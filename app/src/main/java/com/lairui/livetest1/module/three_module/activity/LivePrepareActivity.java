package com.lairui.livetest1.module.three_module.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.dialog.ChoosePopup;
import com.lairui.livetest1.dialog.LiveCreateRoomShareTipsPop;
import com.lairui.livetest1.entity.bean.CategoryBean;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.entity.bean.PushAddressBean;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.lairui.livetest1.entity.jsonparam.BaseParams;
import com.lairui.livetest1.module.three_module.presenter.LivePreparePresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.ui.adapter.ShareListAdapter;
import com.lairui.livetest1.utils.ChoosePicture;
import com.lairui.livetest1.utils.ObjectBox;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.UiTools;
import com.wanou.framelibrary.weight.RatioImageView;
import com.zhihu.matisse.Matisse;

import java.util.List;

import io.objectbox.Box;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LivePrepareActivity extends BaseMvpActivity<LivePreparePresenter> implements View.OnClickListener {
    private boolean isChooseCategory = false;
    private CheckBox cbRoomLock;
    private TextView tvChooseSort, tvStartLive;
    private EditText etLiveTitle;
    private ImageView ivRoomLock,ivClose;
    private LinearLayout llAddImage;
    private RecyclerView rvShareList;
    private RatioImageView ivAddImage;
    private ShareListAdapter shareListAdapter;
    private LiveCreateRoomShareTipsPop mPopTips;
    private List<Uri> mSelected;
    private boolean isInAddVideo = false;//是否已经发起直播请
    private boolean isPrivate = false;//是否私密直播
    private Bundle bundle = new Bundle();
    private BaseParams baseParams = new BaseParams();
    private long mTime = 0;

    @Override
    protected int getResId() {
        return R.layout.activity_live_prepare;
    }

    @Override
    protected void initView() {
        ivRoomLock = findViewById(R.id.ivRoomLock);
        cbRoomLock = findViewById(R.id.cbRoomLock);
        tvChooseSort = findViewById(R.id.tvChooseSort);
        etLiveTitle = findViewById(R.id.etLiveTitle);
        llAddImage = findViewById(R.id.llAddImage);
        ivClose = findViewById(R.id.ivClose);
        ivAddImage = findViewById(R.id.ivAddImage);
        rvShareList = findViewById(R.id.rvShareList);
        tvStartLive = findViewById(R.id.tvStartLive);
        mPopTips = new LiveCreateRoomShareTipsPop(this);

        initListener();
    }

    private void initListener() {
        cbRoomLock.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvChooseSort.setOnClickListener(this);
        llAddImage.setOnClickListener(this);
        tvStartLive.setOnClickListener(this);
    }

    @Override
    protected LivePreparePresenter getPresenter() {
        return new LivePreparePresenter();
    }

    @Override
    protected void initData() {
        shareListAdapter = new ShareListAdapter(LivePrepareActivity.this);
        rvShareList.setAdapter(shareListAdapter);
        shareListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (shareListAdapter.getSelect() == position) {
                    shareListAdapter.setSelect(-1);
                } else {
                    shareListAdapter.setSelect(position);
                    String shareTips = "";
                    switch (position) {
                        case 0:
                            shareTips = UiTools.getString(R.string.QQShare);
                            break;
                        case 1:
                            shareTips = UiTools.getString(R.string.wechatShare);
                            break;
                        case 2:
                            shareTips = UiTools.getString(R.string.commentsShare);
                            break;
                        case 3:
                            shareTips = UiTools.getString(R.string.qqZoneShare);
                            break;
                        case 4:
                            shareTips = UiTools.getString(R.string.weiboShare);
                            break;
                        default:
                    }
                    mPopTips.showPopTips(shareTips, view);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbRoomLock:
                boolean checked = cbRoomLock.isChecked();
                ivRoomLock.setSelected(checked);
                if (checked) {
                    viewInvisible(rvShareList);
                    shareListAdapter.setSelect(-1);
                } else {
                    viewVisible(rvShareList);
                }
                break;
            case R.id.tvChooseSort:
                Box<CategoryBean> categoryBeanBox = ObjectBox.getBoxStore().boxFor(CategoryBean.class);
                List<CategoryBean> categoryListBean = categoryBeanBox.getAll();
                if (categoryListBean != null && categoryListBean.size() > 0) {
                    ChoosePopup.getPopup(LivePrepareActivity.this, categoryListBean, tvChooseSort);
                    ChoosePopup.setOnChooseContentListener(new ChoosePopup.OnChooseContentListener() {
                        @Override
                        public void onChooseClickListener(int position) {
                            isChooseCategory = true;
                            CategoryBean categoryBean = categoryListBean.get(position);
                            tvChooseSort.setText(categoryBean.getName());
                        }
                    });
                }
                break;
            case R.id.ivClose:
                finish();
                break;
            case R.id.llAddImage:
                // 检查权限
                LivePrepareActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(LivePrepareActivity.this);
                break;
            case R.id.tvStartLive:
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - mTime > 1000) {
                    mTime = currentTimeMillis;
                    // 如果已经申请直播
                    if (isInAddVideo) {
                        return;
                    }
                    if (!isChooseCategory) {
                        UiTools.showToast(R.string.chooseCategory);
                        return;
                    }
                    if (!UiTools.noEmpty(UiTools.getText(etLiveTitle))) {
                        UiTools.showToast(R.string.inputLiveTitle);
                        return;
                    }
                    // 私密直播
                    if (cbRoomLock.isChecked()) {
                        isPrivate = true;
                    } else {
                        isPrivate = false;
                    }
                    UserInfoBean currentUserInfo = ObjectBox.getCurrentUserInfo();
                    if (currentUserInfo != null) {
                        String token = currentUserInfo.getToken();
                        baseParams.operate = "roomGroup-start";
                        baseParams.token = token;
                        mPresenter.getPushAddress(GsonUtils.toJson(baseParams));
                    } else {
                        startActivity(LivePrepareActivity.this, null, LoginActivity.class);
                        ActivityManage.getInstance().finishAll();
                    }
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Uri uri = mSelected.get(0);
            GlideApp.with(MyApplication.getContext())
                    .load(uri)
                    .into(ivAddImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LivePrepareActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermission() {
        ChoosePicture.choosePicture(LivePrepareActivity.this,AppConstant.REQUEST_CODE_CHOOSE_COVER);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denyPermission() {
        UiTools.showToast("权限被拒绝,无法获取图片信息");
    }


    public void setPushAddress(PushAddressBean pushAddress) {
        String rtmpurl = pushAddress.getRtmpurl();
        bundle.clear();
        bundle.putString("pushUrl", rtmpurl);
        startActivity(LivePrepareActivity.this, bundle, LivePushActivityTX.class);
        finish();
    }

    public void setPushAddressError(SimpleResponse simpleResponse) {
        if (simpleResponse != null) {
            if (simpleResponse.code == -1) {
                startActivity(LivePrepareActivity.this, null, LoginActivity.class);
                ActivityManage.getInstance().finishAll();
            } else {
                startActivity(LivePrepareActivity.this, null, LiveApproveActivity.class);
            }
        } else {
            UiTools.showToast("获取信息失败, 稍后重试");
        }
    }
}
