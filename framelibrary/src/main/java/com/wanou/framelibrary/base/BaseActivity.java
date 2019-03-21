package com.wanou.framelibrary.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.wanou.framelibrary.R;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.weight.SimpleMultiStateView;

/**
 * Author by wodx521
 * Date on 2018/11/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected Bundle mBundle;
    protected SimpleMultiStateView mSimpleMultiStateView;

    public void startActivity(Context context, Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        context.startActivity(intent);
    }

    public void compatStartActivity(Context context, Bundle intentBundle, Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (intentBundle != null) {
            intent.putExtra("bundle", intentBundle);
        }
        ActivityCompat.startActivity(context, intent, bundle);
    }

    public void startActivityForResult(Context context, Bundle bundle, int requestCode, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).startActivityForResult(intent, requestCode);
        }
    }

    public void compatStartActivityForResult(Context context, Bundle intentBundle, Bundle bundle, int requestCode, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (intentBundle != null) {
            intent.putExtra("bundle", intentBundle);
        }
        if (context instanceof BaseActivity) {
            ActivityCompat.startActivityForResult((BaseActivity) context, intent, requestCode, bundle);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        highApiEffects();
        ActivityManage.getInstance().addActivity(this);
        setContentView(getResId());
        mBundle = getIntent().getBundleExtra("bundle");
        initView();
        initStateView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    //设置布局id
    protected abstract int getResId();

    //初始化view
    protected abstract void initView();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void highApiEffects() {
        getWindow().getDecorView().setFitsSystemWindows(true);
        //透明状态栏 @顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.getInstance().removeActivity(this);
    }

    private void initStateView() {
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
                .setLoadingResource(R.layout.view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setonReLoadlistener(this::onRetry);
    }

    protected void viewGone(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
        }
    }

    protected void viewVisible(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void viewInvisible(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    @Override
    public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    @Override
    public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    @Override
    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }

    @Override
    public void onRetry() {
        initDatas();
    }

    /**
     * 初始化数据
     */
    public void initDatas() {

    }

}
