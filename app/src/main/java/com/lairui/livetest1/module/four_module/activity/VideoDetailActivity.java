package com.lairui.livetest1.module.four_module.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.CommentBean;
import com.lairui.livetest1.entity.bean.PraiseBean;
import com.lairui.livetest1.entity.bean.VideoBean;
import com.lairui.livetest1.entity.jsonparam.CommentParams;
import com.lairui.livetest1.entity.jsonparam.SendCommentParams;
import com.lairui.livetest1.module.four_module.adapter.CommentListAdapter;
import com.lairui.livetest1.module.four_module.presenter.VideoDetailPresenter;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lzy.okgo.model.HttpParams;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class VideoDetailActivity extends BaseMvpActivity<VideoDetailPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private ImageView ivRight1;
    private StandardGSYVideoPlayer gsyVideoPlayer;
    private CircleImageView ivAuthorIcon;
    private TextView tvAuthorName;
    private TextView tvVideoDes;
    private EditText etCommentContent;
    private TextView tvSendComment;
    private TextView tvVideoWatch;
    private CheckBox cbVideoPraise;
    private RecyclerView rvCommentList;
    private HttpParams httpParams = new HttpParams();
    private CommentParams commentParams = new CommentParams();
    private SendCommentParams sendCommentParams = new SendCommentParams();
    private CommentListAdapter commentListAdapter;
    private int id;

    @Override
    protected VideoDetailPresenter getPresenter() {
        return new VideoDetailPresenter();
    }

    @Override
    protected void initData() {
        commentListAdapter = new CommentListAdapter(this);
        rvCommentList.setAdapter(commentListAdapter);
        if (mBundle != null) {
            VideoBean videoBean = mBundle.getParcelable("videoBean");
            if (videoBean != null) {
                String nickname = videoBean.getNickname();
                id = videoBean.getId();
                PraiseBean praise = videoBean.getPraise();
                String num = praise.getNum();
                if (UiTools.noEmpty(nickname)) {
                    tvAuthorName.setText(nickname);
                } else {
                    tvAuthorName.setText("");
                }
                if (UiTools.noEmpty(num)) {
                    tvVideoWatch.setText(num);
                } else {
                    tvVideoWatch.setText("0");
                }
                getComment(id);
            }
        }
    }

    private void getComment(int id) {
        commentParams.operate = "commentGroup-getCommlist";
        commentParams.videoId = id + "";
        commentParams.token = (String) SpUtils.get("token", "");
        mPresenter.getComment(GsonUtils.toJson(commentParams));
    }

    @Override
    protected int getResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        ivRight1 = findViewById(R.id.ivRight1);
        gsyVideoPlayer = findViewById(R.id.gsyVideoPlayer);
        ivAuthorIcon = findViewById(R.id.ivAuthorIcon);
        tvAuthorName = findViewById(R.id.tvAuthorName);
        tvVideoDes = findViewById(R.id.tvVideoDes);
        tvVideoWatch = findViewById(R.id.tvVideoWatch);
        cbVideoPraise = findViewById(R.id.cbVideoPraise);
        etCommentContent = findViewById(R.id.etCommentContent);
        tvSendComment = findViewById(R.id.tvSendComment);
        rvCommentList = findViewById(R.id.rvCommentList);
        ivLeft.setImageResource(R.drawable.arrow_left_main_color);
        tvToolbarTitle.setText(R.string.videoDetail);
        viewVisible(ivLeft);
        ivLeft.setOnClickListener(this);
        tvSendComment.setOnClickListener(this);
        cbVideoPraise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String token = (String) SpUtils.get("token", "");
        String userId = (String) SpUtils.get("userId", "");
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.cbVideoPraise:
                sendCommentParams.operate = "videoGroup-videoPraise";
                sendCommentParams.id = "" + id;
                sendCommentParams.token = token;
                mPresenter.sendPraise(GsonUtils.toJson(sendCommentParams));
                break;
            case R.id.tvSendComment:
                String commentContent = UiTools.getText(etCommentContent);
                if (UiTools.noEmpty(commentContent)) {
                    sendCommentParams.operate = "commentGroup-comment";
                    sendCommentParams.userid = userId;
                    sendCommentParams.atuserid = "";
                    sendCommentParams.videoId = id + "";
                    sendCommentParams.content = commentContent;
                    sendCommentParams.id = "";
                    sendCommentParams.token = token;
                    mPresenter.sendComment(GsonUtils.toJson(sendCommentParams));
                } else {
                    UiTools.showToast(R.string.inputComment);
                }
                break;
            default:
        }
    }

    public void setCommentList(List<CommentBean> commentBeanList) {
        commentListAdapter.setCommentBeanList(commentBeanList);
    }

    public void sendCommentSuccess() {
        etCommentContent.setText("");
        getComment(id);
    }

    public void sendPraiseSuccess() {
        cbVideoPraise.setChecked(cbVideoPraise.isChecked());

    }
}
