package com.lairui.livetest1.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.SearchListBean;
import com.lairui.livetest1.entity.jsonparam.AttentionParams;
import com.lairui.livetest1.presenter.SearchPresenter;
import com.lairui.livetest1.ui.adapter.SearchListAdapter;
import com.lairui.livetest1.utils.CustomTextChange;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements View.OnClickListener {
    private ImageView ivBack;
    private EditText etSearchLive;
    private ImageView ivClear;
    private ImageView ivSearch;
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvSearchList;
    private HttpParams httpParams = new HttpParams();
    private List<SearchListBean.DataBean> tempData = new ArrayList<>();
    private SearchListAdapter searchListAdapter;
    private int page = 0;
    private long time = 0;
    private AttentionParams attentionParams = new AttentionParams();

    @Override
    protected SearchPresenter getPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initData() {
        searchListAdapter = new SearchListAdapter(this);
        rvSearchList.setAdapter(searchListAdapter);
        etSearchLive.addTextChangedListener(new CustomTextChange() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && UiTools.noEmpty(s.toString())) {
                    viewVisible(ivClear);
                    srlRefresh.setEnableRefresh(true);
                } else {
                    viewInvisible(ivClear);
                    srlRefresh.setEnableRefresh(false);
                }
            }
        });
    }

    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ivBack = findViewById(R.id.ivBack);
        etSearchLive = findViewById(R.id.etSearchLive);
        ivClear = findViewById(R.id.ivClear);
        ivSearch = findViewById(R.id.ivSearch);
        srlRefresh = findViewById(R.id.srlRefresh);
        rvSearchList = findViewById(R.id.rvSearchList);
        rvSearchList.addItemDecoration(new DividerItemDecoration(SearchActivity.this, DividerItemDecoration.VERTICAL));
        ivBack.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String token = (String) SpUtils.get("token", "");
        long currentTime = System.currentTimeMillis();
        if ((currentTime - time) > AppConstant.CLICK_TIME_OUT) {
            time = currentTime;
            switch (v.getId()) {
                case R.id.ivBack:
                    finish();
                    break;
                case R.id.ivClear:
                    etSearchLive.setText("");
                    break;
                case R.id.ivSearch:
                    tempData.clear();
                    httpParams.clear();
                    page = 0;
                    httpParams.put("operate", "userGroup-user");
                    httpParams.put("page", page);
                    httpParams.put("token", token);
                    if (UiTools.noEmpty(UiTools.getText(etSearchLive))) {
                        httpParams.put("key", UiTools.getText(etSearchLive));
                    } else {
                        httpParams.put("key", "");
                    }
                    mPresenter.getSearchList(httpParams);
                    break;
                default:
            }
        }
    }

    public void setSearchSuccess(SearchListBean searchListBean) {
        int currentPage = searchListBean.getCurrentPage();
        int lastPage = searchListBean.getLastPage();
        if (currentPage < lastPage) {
            srlRefresh.setEnableLoadMore(true);
        } else {
            srlRefresh.setEnableLoadMore(false);
        }
        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                String token = (String) SpUtils.get("token", "");
                page = currentPage + 1;
                httpParams.put("operate", "userGroup-user");
                httpParams.put("key", UiTools.getText(etSearchLive));
                httpParams.put("page", page);
                httpParams.put("token", token);
                mPresenter.getSearchList(httpParams);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                tempData.clear();
                ivSearch.performClick();
            }
        });

        List<SearchListBean.DataBean> data = searchListBean.getData();
        tempData.addAll(data);
        searchListAdapter.setSearchListBeanList(tempData);

        searchListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                // TODO: 2019/3/29 搜索条目点击

            }
        });

        searchListAdapter.setFollowClickListener(new SearchListAdapter.FollowClickListener() {
            @Override
            public void onFollowClick(View v, int position) {
                String token = (String) SpUtils.get("token", "");
                SearchListBean.DataBean dataBean = tempData.get(position);
                attentionParams.anchorUid = "";
                attentionParams.token = token;
                mPresenter.setAttention(GsonUtils.toJson(attentionParams), v);
            }
        });
    }

    public void setSearchError(SimpleResponse simpleResponse) {
        if (simpleResponse != null) {
            if (simpleResponse.code == -1) {
                startActivity(SearchActivity.this, null, LoginActivity.class);
                SpUtils.put("token", "");
                ActivityManage.getInstance().finishAll();
            }
        }
    }

    public void setAttentionSuccess() {

    }

    public void setAttentionError(SimpleResponse simpleResponse) {

    }
}
