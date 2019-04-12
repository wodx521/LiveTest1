package com.lairui.livetest1.module.three_module.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
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
import com.lairui.livetest1.module.three_module.presenter.LivePreparePresenter;
import com.lairui.livetest1.ui.adapter.ShareListAdapter;
import com.lairui.livetest1.utils.ChoosePicture;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;
import com.wanou.framelibrary.weight.RatioImageView;
import com.zhihu.matisse.Matisse;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LivePrepareActivity extends BaseMvpActivity<LivePreparePresenter> implements View.OnClickListener {
    private boolean isChooseCategory = false;
    private CheckBox cbRoomLock;
    private TextView tvChooseSort, tvStartLive;
    private EditText etLiveTitle;
    private ImageView ivRoomLock;
    private LinearLayout llAddImage;
    private RecyclerView rvShareList;
    private RatioImageView ivAddImage;
    private ImageView ivClose, ivQQ, ivWechat, ivMoments, ivWeibo, ivQQZone;
    private ShareListAdapter shareListAdapter;
    private LiveCreateRoomShareTipsPop mPopTips;
    private HttpParams httpParams = new HttpParams();
    private List<Uri> mSelected;
    private boolean isInAddVideo = false;//是否已经发起直播请
    private boolean isPrivate = false;//是否私密直播

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
        ivQQ = findViewById(R.id.ivQQ);
        ivWechat = findViewById(R.id.ivWechat);
        ivMoments = findViewById(R.id.ivMoments);
        ivWeibo = findViewById(R.id.ivWeibo);
        ivQQZone = findViewById(R.id.ivQQZone);
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
        ivQQ.setOnClickListener(this);
        ivWechat.setOnClickListener(this);
        ivMoments.setOnClickListener(this);
        ivWeibo.setOnClickListener(this);
        ivQQZone.setOnClickListener(this);
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
                httpParams.clear();
                httpParams.put("operate", "roomGroup-categoryList");
                mPresenter.getCategoryList(httpParams);
                break;
            case R.id.ivClose:
                finish();
                break;
            case R.id.llAddImage:
                // 检查权限
                LivePrepareActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(LivePrepareActivity.this);
                break;
            case R.id.ivQQ:
                ivQQ.setSelected(!ivQQ.isSelected());
                break;
            case R.id.ivWechat:
                ivWechat.setSelected(!ivWechat.isSelected());
                break;
            case R.id.ivMoments:
                ivMoments.setSelected(!ivMoments.isSelected());
                break;
            case R.id.ivWeibo:
                ivWeibo.setSelected(!ivWeibo.isSelected());
                break;
            case R.id.ivQQZone:
                ivQQZone.setSelected(!ivQQZone.isSelected());
                break;
            case R.id.tvStartLive:
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

                startActivity(LivePrepareActivity.this, null, LivePushActivityAli.class);
                finish();
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

    public void setCategorySuccess(List<CategoryBean> categoryListBean) {
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
    }

    public void setCategoryError() {

    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermission() {
        ChoosePicture.choosePicture(LivePrepareActivity.this);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denyPermission() {
        UiTools.showToast("权限被拒绝,无法获取图片信息");
    }


    public void setPushAddress(LiveAddressBean liveAddressBean) {
        // 获取直播推流地址
        LiveAddressBean.PushBean push = liveAddressBean.getPush();
        String rtmpurl = push.getRtmpurl();


    }
}