package com.lairui.livetest1.module.four_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.CommentBean;
import com.lairui.livetest1.entity.bean.UseridBean;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class CommentListAdapter extends BaseRecycleViewAdapter {
    private List<CommentBean> commentBeanList;

    public CommentListAdapter(Context context) {
        super(context);
    }

    public void setCommentBeanList(List<CommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_comment;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new CommentListViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        CommentListViewHolder commentListViewHolder = (CommentListViewHolder) viewHolder;
        CommentBean commentBean = commentBeanList.get(position);
        List<CommentBean> children = commentBean.getChildren();
        UseridBean userid = commentBean.getUserid();
        if (userid != null) {
            commentListViewHolder.tvCommentName.setText(userid.getNickname());
            GlideApp.with(MyApplication.getContext())
                    .load(AppConstant.BASE_URL+userid.getPortrait())
                    .placeholder(R.drawable.chatroom_02)
                    .error(R.drawable.chatroom_02)
                    .into(commentListViewHolder.ivUserIcon);
        }
        // 判断是否有子评论, 如有显示子评论内容, 没有显示一般评论内容
        if (children != null && children.size() > 0) {
            commentListViewHolder.tvChildComment.setVisibility(View.VISIBLE);
            commentListViewHolder.tvCommentContent.setVisibility(View.GONE);
            CommentBean commentBean1 = children.get(0);
            if (commentBean1 != null) {
                // 回复信息
                UseridBean userid1 = commentBean1.getUserid();
                if (userid1 != null) {
                    String nickname1 = userid1.getNickname();
                    commentListViewHolder.tvChildComment.setText("回复" + nickname1 + ":" + commentBean1.getContent());
                } else {
                    commentListViewHolder.tvChildComment.setText("");
                }
            }
        } else {
            commentListViewHolder.tvCommentContent.setVisibility(View.VISIBLE);
            commentListViewHolder.tvChildComment.setVisibility(View.GONE);
            String content = commentBean.getContent();
            if (UiTools.noEmpty(content)) {
                commentListViewHolder.tvCommentContent.setText(content);
            } else {
                commentListViewHolder.tvCommentContent.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (commentBeanList != null && commentBeanList.size() > 0) {
            return commentBeanList.size();
        }
        return 0;
    }

    static class CommentListViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserIcon;
        private TextView tvCommentName;
        private TextView tvCommentContent;
        private TextView tvCommentTime;
        private TextView tvChildComment;

        public CommentListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserIcon = itemView.findViewById(R.id.ivUserIcon);
            tvCommentName = itemView.findViewById(R.id.tvCommentName);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
            tvCommentTime = itemView.findViewById(R.id.tvCommentTime);
            tvChildComment = itemView.findViewById(R.id.tvChildComment);

        }
    }
}
