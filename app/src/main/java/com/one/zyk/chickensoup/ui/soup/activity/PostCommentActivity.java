package com.one.zyk.chickensoup.ui.soup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.base.BaseActivity;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.app.Constant;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.http.request.ServiceRequest;
import com.one.zyk.chickensoup.utils.KeyBoardUtils;
import com.one.zyk.chickensoup.ui.management.ManagementActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PostCommentActivity extends BaseActivity {
    @BindView(R.id.iv_post)
    ImageView iv_post;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.tv_back)
    TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.iv_post, R.id.tv_back})
    void postComment(View v) {
        switch (v.getId()) {

            case R.id.iv_post:
                if (et_comment.getText().toString().equals("")) {
                    Toast.makeText(this, "评论内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (("Sudo.").equals(et_comment.getText().toString())) {
                    Intent intent = new Intent(PostCommentActivity.this, ManagementActivity.class);
                    startActivity(intent);
                    return;
                }
                if (("Release.").equals(et_comment.getText().toString())) {
                    Intent intent = new Intent(PostCommentActivity.this, ReleaseSoupActivity.class);
                    startActivity(intent);
                    return;
                }
                ServiceRequest.postComment(this, mUserSp.getString(Constant.useId), getIntent().getStringExtra("soupId"), et_comment.getText().toString());
                KeyBoardUtils.closeKeybord(et_comment, this);
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @OnTextChanged(R.id.et_comment)
    void onTextChanged(CharSequence text) {
        if (!text.toString().equals("")) {
            iv_post.setImageResource(R.mipmap.hot_reply);
            iv_post.setEnabled(true);
        } else {
            iv_post.setImageResource(R.mipmap.hot_reply_gray);
            iv_post.setEnabled(false);
        }
    }

    @Subscribe
    public void updateComment(CommentBean bean) {
        if (bean == null) {
            Toast.makeText(this, "评论失败...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bean.getCode() == 1) {
            Toast.makeText(this, "评论成功！", Toast.LENGTH_SHORT).show();
            finish();
        } else if (bean.getCode() == -1) {
            et_comment.setText("");
            Toast.makeText(this, "小盆友，不要挑事哦，不要出现作者名称哦...", Toast.LENGTH_SHORT).show();
        }
    }
}
