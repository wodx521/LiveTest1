package com.wanou.framelibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanou.framelibrary.R;
import com.wanou.framelibrary.weight.SimpleMultiStateView;

/**
 * Author by wodx521
 * Date on 2018/11/10.
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    SimpleMultiStateView mSimpleMultiStateView;

    public void startActivity(Fragment fragment, Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(fragment.getActivity(), cls);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        fragment.startActivity(intent);
    }

    public void startActivityForResult(Fragment fragment, Bundle bundle, int requestCode, Class<?> cls) {
        Intent intent = new Intent(fragment.getActivity(), cls);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getResId() > 0) {
            return inflater.inflate(getResId(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initStateView();
        initData();
    }

    protected abstract int getResId();

    protected abstract void initView(View view);

    protected abstract void initData();

    @Override
    public void onHiddenChanged(boolean hidden) {
        isHiddenListener(hidden);
    }

    protected abstract void isHiddenListener(boolean hidden);

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

    private void initStateView() {
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
                .setLoadingResource(R.layout.view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setonReLoadlistener(this::onRetry);
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
        initDataOnUserVisible();
    }

    /**
     * 初始化数据的空实现，fragment切换加载数据的时候重写
     */
    protected void initDataOnUserVisible() {

    }
}
