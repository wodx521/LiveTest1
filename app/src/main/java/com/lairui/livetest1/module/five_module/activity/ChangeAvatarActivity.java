package com.lairui.livetest1.module.five_module.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.module.five_module.presenter.ChangeAvatarPresenter;
import com.lairui.livetest1.utils.ChoosePicture;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;
import com.zhihu.matisse.Matisse;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ChangeAvatarActivity extends BaseMvpActivity<ChangeAvatarPresenter> implements View.OnClickListener {
    private List<Uri> mSelected;
    private ImageView ivImage, ivBack;
    private TextView tvChooseAlbum;
    private TextView tvChooseCamera;

    @Override
    protected ChangeAvatarPresenter getPresenter() {
        return new ChangeAvatarPresenter();
    }

    @Override
    protected void initData() {
        if (mBundle != null) {
            String userIconUrl = mBundle.getString("userIconUrl", "");
            GlideApp.with(MyApplication.getContext())
                    .load(userIconUrl)
                    .placeholder(R.drawable.ic_head)
                    .error(R.drawable.ic_head)
                    .into(ivImage);
        }
    }

    @Override
    protected int getResId() {
        return R.layout.activity_change_avatar;
    }

    @Override
    protected void initView() {
        ivImage = findViewById(R.id.ivImage);
        ivBack = findViewById(R.id.ivBack);
        tvChooseAlbum = findViewById(R.id.tvChooseAlbum);
        tvChooseCamera = findViewById(R.id.tvChooseCamera);

        tvChooseAlbum.setOnClickListener(this);
        tvChooseCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChooseAlbum:
                ChangeAvatarActivityPermissionsDispatcher.requestStorageWithPermissionCheck(ChangeAvatarActivity.this);
                break;
            case R.id.tvChooseCamera:

                break;
            case R.id.ivBack:
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
                    .into(ivImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ChangeAvatarActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestStorage() {
        ChoosePicture.choosePicture(ChangeAvatarActivity.this,AppConstant.REQUEST_CODE_CHOOSE);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void deniedStorage() {
        UiTools.showToast("权限被拒绝,无法获取图片信息");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void neverAskStorage() {
    }
}
