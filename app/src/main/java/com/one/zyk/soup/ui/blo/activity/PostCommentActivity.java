package com.one.zyk.soup.ui.blo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.manage.ReleaseSoupActivity;
import com.one.zyk.soup.utils.KeyBoardUtils;
import com.one.zyk.soup.ui.manage.ManagementActivity;
import com.one.zyk.soup.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected <T> void onRetrofitCallBack(T t) {
        switch (t.getClass().getSimpleName()) {
            case "CommentBean":
                CommentBean bean = (CommentBean) t;
                if (bean.getCode() == 1) {
                    Toast.makeText(this, "评论成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (bean.getCode() == -1) {
                    et_comment.setText("");
                    Toast.makeText(this, "小盆友，不要挑事哦，不要出现作者名称哦...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
                ServiceRequest.postComment(this, mUserSp.getString(Constant.sp_usrId), getIntent().getStringExtra("boloId"), et_comment.getText().toString());
                KeyBoardUtils.closeKeybord(et_comment, this);
                iv_post.setEnabled(false);
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @OnTextChanged(R.id.et_comment)
    void onTextChanged(CharSequence text) {
        if (!text.toString().equals("")) {
            iv_post.setImageResource(R.mipmap.reply);
            iv_post.setEnabled(true);
        } else {
            iv_post.setImageResource(R.mipmap.reply_gray);
            iv_post.setEnabled(false);
        }
    }

    @Subscribe
    public void getRefreshStatus(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 0) {
                Toast.makeText(this, "评论成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "未知错误...", Toast.LENGTH_SHORT).show();
        }
    }
}
