package com.one.zyk.chickensoup.ui.soup.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.base.BaseActivity;
import com.one.zyk.chickensoup.http.request.ServiceRequest;
import com.one.zyk.chickensoup.utils.FileUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReleaseSoupActivity extends BaseActivity {
    private String mContent;//所要发表的内容
    private File mFile;//所要上传的图片文件
    private static final int REQUEST_CODE_CHOOSE = 491;
    private ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_soup);
    }

    @Override
    protected void initView() {
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });
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
                if (mFile == null) {
                    ServiceRequest.updateSoup(this, mContent, null);
                } else {
                    ServiceRequest.updateSoup(this, mContent, mFile);
                }
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
                .maxSelectable(9)
                .theme(R.style.Matisse_Zhihu)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                mFile = new File(FileUtil.getRealFilePath(this, mSelected.get(0)));
                Glide.with(this)
                        .load(mFile)
                        .into(iv_add);
            }
            Log.e("Matisse", "mSelected: " + mSelected.get(0).getPath());
            Log.e("Matisse", "mFile: " + mFile.getAbsolutePath());
        }
    }
}
