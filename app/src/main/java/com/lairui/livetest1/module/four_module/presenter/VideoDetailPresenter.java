package com.lairui.livetest1.module.four_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.CommentBean;
import com.lairui.livetest1.module.four_module.activity.VideoDetailActivity;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;

import java.lang.reflect.Type;
import java.util.List;

public class VideoDetailPresenter extends BasePresenterImpl<VideoDetailActivity> {
    public void getComment(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "commentList", json, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<CommentBean>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                List<CommentBean> commentBeanList = (List<CommentBean>) generalResult.data;
                mPresenterView.setCommentList(commentBeanList);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {

            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {

            }

            @Override
            public void onRequestFinish() {

            }
        });
    }

    public void sendComment(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "sendComment", json, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<Void>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                mPresenterView.sendCommentSuccess();
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {

            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {

            }

            @Override
            public void onRequestFinish() {

            }
        });
    }

    public void sendPraise(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "sendComment", json, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<Void>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                mPresenterView.sendPraiseSuccess();
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {

            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {

            }

            @Override
            public void onRequestFinish() {

            }
        });
    }
}
