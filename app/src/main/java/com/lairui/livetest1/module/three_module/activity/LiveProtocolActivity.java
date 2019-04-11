package com.lairui.livetest1.module.three_module.activity;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.presenter.LiveProtocolPresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;

public class LiveProtocolActivity extends BaseMvpActivity<LiveProtocolPresenter> implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private FrameLayout flWebContainer;
    private TextView tvAgree;
    private AgentWeb mAgentWeb;

    @Override
    protected LiveProtocolPresenter getPresenter() {
        return new LiveProtocolPresenter();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        String url = mBundle.getString("url");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebContainer, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .interceptUnkownUrl() //拦截找不到相关页面
                .createAgentWeb()
                .ready()
                .go(url);
        WebSettings settings = mAgentWeb.getWebCreator().getWebView().getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        settings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_live_protocol;
    }

    @Override
    protected void initView() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        flWebContainer = findViewById(R.id.fl_web_container);
        tvAgree = findViewById(R.id.tvAgree);

        tvTitle.setText("主播协议");
        ivBack.setOnClickListener(this);
        tvAgree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                if (!mAgentWeb.back()) {
                    finish();
                }
                break;
            case R.id.tvAgree:
                SpUtils.put("isAgreeProtocol", true);
                startActivity(LiveProtocolActivity.this, null, LivePrepareActivity.class);
                finish();
                break;
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}
