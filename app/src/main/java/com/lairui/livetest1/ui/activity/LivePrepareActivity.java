package com.lairui.livetest1.ui.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.LivePreparePresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.weight.RatioImageView;

public class LivePrepareActivity extends BaseMvpActivity<LivePreparePresenter> implements View.OnClickListener {
    private CheckBox cbRoomLock;
    private TextView tvChooseSort;
    private EditText etLiveTitle;
    private LinearLayout llAddImage;
    private RatioImageView ivAddImage;
    private ImageView ivClose;
    private CheckBox cbQQ;
    private CheckBox cbWechat;
    private CheckBox cbMoments;
    private CheckBox cbWeibo;
    private CheckBox cbQQZone;
    private TextView tvStartLive;

    @Override
    protected int getResId() {
        return R.layout.activity_live_prepare;
    }

    @Override
    protected void initView() {
        cbRoomLock = findViewById(R.id.cbRoomLock);
        tvChooseSort = findViewById(R.id.tvChooseSort);
        etLiveTitle = findViewById(R.id.etLiveTitle);
        llAddImage = findViewById(R.id.llAddImage);
        ivClose = findViewById(R.id.ivClose);
        ivAddImage = findViewById(R.id.ivAddImage);
        cbQQ = findViewById(R.id.cbQQ);
        cbWechat = findViewById(R.id.cbWechat);
        cbMoments = findViewById(R.id.cbMoments);
        cbWeibo = findViewById(R.id.cbWeibo);
        cbQQZone = findViewById(R.id.cbQQZone);
        tvStartLive = findViewById(R.id.tvStartLive);

    }

    @Override
    protected LivePreparePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbRoomLock:

                break;
            case R.id.tvChooseSort:

                break;
            case R.id.ivClose:
                finish();
                break;
            case R.id.llAddImage:

                break;
            default:
        }
    }
}
