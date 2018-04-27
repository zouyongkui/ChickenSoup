package com.one.zyk.soup.ui.flow.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.utils.FileUtil;
import com.one.zyk.soup.utils.KeyBoardUtils;
import com.one.zyk.soup.utils.LogUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostFlowActivity extends BaseActivity {
    private File mFile;//所要上传的图片文件
    private static final int REQUEST_CODE_CHOOSE = 491;

    @BindView(R.id.iv_post)
    ImageView iv_post;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.iv_addPic)
    ImageView iv_addPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_flow);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_post, R.id.tv_back, R.id.iv_addPic})
    void postComment(View v) {
        switch (v.getId()) {
            case R.id.iv_post:
                MultipartBody.Part body = null;
                if (mFile != null) {
                    // 创建 RequestBody，用于封装构建RequestBody
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
                    // MultipartBody.Part  和后端约定好Key，这里的partName是用image
                    body = MultipartBody.Part.createFormData("pic", mFile.getName(), requestFile);
                }
                if (et_title.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "请先输入标题哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_content.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "请先输入内容哦！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ServiceRequest.updateCommunity(this, mUserSp.getString(Constant.useId)
                        , et_title.getText().toString(), et_content.getText().toString(), body);
                KeyBoardUtils.closeKeybord(et_content, this);
                KeyBoardUtils.closeKeybord(et_title, this);
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_addPic:
                Toast.makeText(this, "暂时还不可以发图片哦！", Toast.LENGTH_SHORT).show();
//                selectPic();
                break;
        }
    }

    @OnTextChanged(R.id.et_title)
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
        LogUtils.d("json huo去成功" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 0) {
                Toast.makeText(this, "发帖成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else if (code == -1) {
                et_title.setText("");
                Toast.makeText(this, "小盆友，不要挑事哦，不要出现作者名称哦...", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "未知错误...", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPic() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .theme(R.style.Matisse_Zhihu)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(1f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                mFile = new File(FileUtil.getRealFilePath(this, uris.get(0)));
                Glide.with(this)
                        .load(mFile)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                iv_addPic.setImageDrawable(resource);
                            }
                        });
            } else {
                LogUtils.e("获取图片失败！");
            }
            Log.e("Matisse", "mFile: " + mFile.getAbsolutePath());
        }

    }
}
