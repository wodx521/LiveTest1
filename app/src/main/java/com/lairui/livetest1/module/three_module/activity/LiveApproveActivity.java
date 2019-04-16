package com.lairui.livetest1.module.three_module.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UploadResultBean;
import com.lairui.livetest1.entity.jsonparam.ApproveParamsBean;
import com.lairui.livetest1.module.three_module.presenter.LiveApprovePresenter;
import com.lairui.livetest1.utils.ChoosePicture;
import com.lairui.livetest1.utils.ObjectBox;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.UiTools;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
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
    private HttpParams httpParams = new HttpParams();
    private String frontPath;
    private String backPath;
    private String handPath;
    private List<String> strings;
    private ApproveParamsBean approveParamsBean = new ApproveParamsBean();

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

        tvToolbarTitle.setText(R.string.approveLiveAuthor);
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
                String realName = UiTools.getText(etRealName);
                String phone = UiTools.getText(etPhone);
                String token = ObjectBox.getToken();
                if (UiTools.noEmpty(realName, phone, frontPath, backPath, handPath)) {
                    approveParamsBean.name = realName;
                    approveParamsBean.phone = phone;
                    approveParamsBean.token = token;
                    approveParamsBean.cardPositive = frontPath;
                    approveParamsBean.cardOpposite = backPath;
                    approveParamsBean.handCard = handPath;
                    mPresenter.applyApprove(GsonUtils.toJson(approveParamsBean));
                } else {
                    if (!UiTools.noEmpty(realName)) {
                        UiTools.showToast(R.string.inputRealName);
                    }
                    if (!UiTools.noEmpty(phone)) {
                        UiTools.showToast(R.string.inputPhoneNumber);
                    }
                    if (!UiTools.noEmpty(frontPath)) {
                        UiTools.showToast(R.string.uploadFront);
                    }
                    if (!UiTools.noEmpty(backPath)) {
                        UiTools.showToast(R.string.uploadBack);
                    }
                    if (!UiTools.noEmpty(handPath)) {
                        UiTools.showToast(R.string.uploadHand);
                    }
                }
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
            httpParams.clear();
            strings = Matisse.obtainPathResult(data);
            File file = new File(strings.get(0));
            String token = ObjectBox.getToken();
            httpParams.put("operate", "uploadGroup-aloneUpload");
            httpParams.put("token", token);
            httpParams.put("file", new HttpParams.FileWrapper(file, file.getName(), MediaType.parse("image/*")));
            httpParams.put("type", "2");
            mPresenter.uploadImage(httpParams, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LiveApproveActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void setUploadSuccess(UploadResultBean uploadResultBean, int requestCode) {
        String savename = uploadResultBean.getGetSaveName();
        switch (requestCode) {
            case AppConstant.REQUEST_CODE_CHOOSE_FRONT:
                frontPath = savename;
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + savename)
                        .into(ivCardFront);
                break;
            case AppConstant.REQUEST_CODE_CHOOSE_BACK:
                backPath = savename;
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + savename)
                        .into(ivCardBack);
                break;
            case AppConstant.REQUEST_CODE_CHOOSE_HAND:
                handPath = savename;
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + savename)
                        .into(ivHandCard);
                break;
            default:
        }
    }
}
