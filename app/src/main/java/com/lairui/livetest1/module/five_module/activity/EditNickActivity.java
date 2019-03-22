package com.lairui.livetest1.module.five_module.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.jsonparam.ChangeNickParams;
import com.lairui.livetest1.module.five_module.presenter.EditNickPresenter;
import com.lairui.livetest1.utils.CustomTextChange;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

public class EditNickActivity extends BaseMvpActivity<EditNickPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private TextView tvOperate;
    private EditText etNickName;
    private ImageView ivClear;
    private TextView tvInputCount;
    private ChangeNickParams changeNickParams = new ChangeNickParams();

    @Override
    protected EditNickPresenter getPresenter() {
        return new EditNickPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_edit_nick;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        tvOperate = findViewById(R.id.tvOperate);
        etNickName = findViewById(R.id.etNickName);
        ivClear = findViewById(R.id.ivClear);
        tvInputCount = findViewById(R.id.tvInputCount);

        tvToolbarTitle.setText(R.string.editNick);
        viewVisible(ivLeft, tvOperate);
        ivLeft.setImageResource(R.drawable.arrow_left_main_color);
        tvOperate.setText(R.string.save);
        tvOperate.setTextColor(UiTools.getColor(R.color.colorGray3));

        ivLeft.setOnClickListener(this);
        tvOperate.setOnClickListener(this);
        ivClear.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mBundle != null) {
            String nickname = mBundle.getString("nickname", "");
            if (UiTools.noEmpty(nickname)) {
                etNickName.setText(nickname);
                tvInputCount.setText(nickname.length() + "/16");
                viewVisible(ivClear);
            } else {
                etNickName.setText("");
                tvInputCount.setText("0/16");
                viewGone(ivClear);
            }
        }

        etNickName.addTextChangedListener(new CustomTextChange() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (UiTools.noEmpty(s.toString())) {
                    tvInputCount.setText(s.length() + "/16");
                    viewVisible(ivClear);
                } else {
                    tvInputCount.setText("0/16");
                    viewGone(ivClear);
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivClear:
                etNickName.setText("");
                break;
            case R.id.tvOperate:
                String nickName = UiTools.getText(etNickName);
                String token = (String) SpUtils.get("token", "");
                changeNickParams.operate = "userGroup-nickname";
                changeNickParams.nickname = nickName;
                changeNickParams.token = token;
                mPresenter.saveEdit(GsonUtils.toJson(changeNickParams));
                break;
            default:
        }
    }

    public void setSaveSuccess() {
        Intent intent = new Intent();
        intent.putExtra("nickName", UiTools.getText(etNickName));
        setResult(RESULT_OK, intent);
        finish();
    }
}
