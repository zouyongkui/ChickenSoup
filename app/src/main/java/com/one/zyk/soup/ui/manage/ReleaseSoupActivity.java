package com.one.zyk.soup.ui.manage;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.api.ServiceApi;
import com.one.zyk.soup.http.request.HttpRequest;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.utils.FileUtil;
import com.one.zyk.soup.utils.LogUtils;
import com.one.zyk.soup.utils.ScreenUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReleaseSoupActivity extends BaseActivity implements Callback {
    private String mContent;//所要发表的内容
    private File mFile;//所要上传的图片文件
    private static final int REQUEST_CODE_CHOOSE = 491;
    @BindView(R.id.iv_add)
    ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_soup);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
    }


    @OnTextChanged(R.id.et_content)
    void queryString(CharSequence charSequence) {
        mContent = charSequence.toString();
    }

    @OnClick({R.id.btn_send, R.id.iv_add})
    void releaseContent(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                if (TextUtils.isEmpty(mContent)) {
                    Toast.makeText(this, "请输入要发布的内容！", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("content", mContent);
                map.put("pic", mFile);
                HttpRequest.post(map, Urls.RELEASE_SOUP, this);
                LogUtils.d("开始发布！");
                break;
            case R.id.iv_add:
                selectPic();
                break;
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
                                iv_add.setImageDrawable(resource);
                            }
                        });
            } else {
                LogUtils.e("获取图片失败！");
            }
            Log.e("Matisse", "mFile: " + mFile.getAbsolutePath());
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        LogUtils.d(e.getMessage());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull final Response response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }
}
