package com.lairui.livetest1.module.three_module.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.module.three_module.presenter.LiveApprovePresenter;
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
public class LiveApproveActivity extends BaseMvpActivity<LiveApprovePresenter> implements View.OnClickListener {
    private TextView tvToolbarTitle, tvConfirm;
    private EditText etRealName, etPhone;
    private ConstraintLayout clCardFront, clCardBack, clHandCard;
    private ImageView ivLeft, ivCardFront, ivCardBack, ivHandCard;
    private List<Uri> mSelected;

    @Override
    protected LiveApprovePresenter getPresenter() {
        return new LiveApprovePresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_approve;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        etRealName = findViewById(R.id.etRealName);
        etPhone = findViewById(R.id.etPhone);
        clCardFront = findViewById(R.id.clCardFront);
        ivCardFront = findViewById(R.id.ivCardFront);
        clCardBack = findViewById(R.id.clCardBack);
        ivCardBack = findViewById(R.id.ivCardBack);
        clHandCard = findViewById(R.id.clHandCard);
        ivHandCard = findViewById(R.id.ivHandCard);
        tvConfirm = findViewById(R.id.tvConfirm);

        ivLeft.setImageResource(R.drawable.arrow_left_main_color);

        initClick();
    }

    private void initClick() {
        ivLeft.setOnClickListener(this);
        clCardBack.setOnClickListener(this);
        clHandCard.setOnClickListener(this);
        clCardFront.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.clCardFront:
                clCardFront.setSelected(true);
                clCardBack.setSelected(false);
                clHandCard.setSelected(false);
                LiveApproveActivityPermissionsDispatcher.requestStorageWithPermissionCheck(LiveApproveActivity.this);
                break;
            case R.id.clCardBack:
                clCardBack.setSelected(true);
                clCardFront.setSelected(false);
                clHandCard.setSelected(false);
                LiveApproveActivityPermissionsDispatcher.requestStorageWithPermissionCheck(LiveApproveActivity.this);
                break;
            case R.id.clHandCard:
                clHandCard.setSelected(true);
                clCardFront.setSelected(false);
                clCardBack.setSelected(false);
                LiveApproveActivityPermissionsDispatcher.requestStorageWithPermissionCheck(LiveApproveActivity.this);
                break;
            case R.id.tvConfirm:

                break;
            default:
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestStorage() {
        // 同意了请求后操作
        if (clCardFront.isSelected()) {
            ChoosePicture.choosePicture(LiveApproveActivity.this, AppConstant.REQUEST_CODE_CHOOSE_FRONT);
        }
        if (clCardBack.isSelected()) {
            ChoosePicture.choosePicture(LiveApproveActivity.this, AppConstant.REQUEST_CODE_CHOOSE_BACK);
        }
        if (clHandCard.isSelected()) {
            ChoosePicture.choosePicture(LiveApproveActivity.this, AppConstant.REQUEST_CODE_CHOOSE_HAND);
        }
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denyStorage() {
        // 拒绝请求后操作
        UiTools.showToast("权限被拒绝,无法获取图片信息");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void neverStorage() {
        // 不在提醒后操作

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri;
            switch (requestCode) {
                case AppConstant.REQUEST_CODE_CHOOSE_FRONT:
                    mSelected = Matisse.obtainResult(data);
                    uri = mSelected.get(0);
                    GlideApp.with(MyApplication.getContext())
                            .load(uri)
                            .into(ivCardFront);
                    break;
                case AppConstant.REQUEST_CODE_CHOOSE_BACK:
                    mSelected = Matisse.obtainResult(data);
                    uri = mSelected.get(0);
                    GlideApp.with(MyApplication.getContext())
                            .load(uri)
                            .into(ivCardBack);
                    break;
                case AppConstant.REQUEST_CODE_CHOOSE_HAND:
                    mSelected = Matisse.obtainResult(data);
                    uri = mSelected.get(0);
                    GlideApp.with(MyApplication.getContext())
                            .load(uri)
                            .into(ivHandCard);
                    break;
                default:
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LiveApproveActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
