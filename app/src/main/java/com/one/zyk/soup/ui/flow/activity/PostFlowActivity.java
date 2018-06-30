package com.one.zyk.soup.ui.flow.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.request.HttpRequest;
import com.one.zyk.soup.utils.FileUtil;
import com.one.zyk.soup.utils.KeyBoardUtils;
import com.one.zyk.soup.utils.LogUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 风语模块 发布
 */
public class PostFlowActivity extends BaseActivity implements Callback {

    private File mFile;//所要上传的图片文件
    private static final int REQUEST_CODE_CHOOSE = 491;

    @BindView(R.id.et_content)
    EditText et_content;
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


    @OnClick({R.id.tv_post, R.id.tv_back, R.id.iv_addPic})
    void postComment(View v) {
        switch (v.getId()) {
            case R.id.tv_post:
                if (et_content.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "请先输入内容哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mFile != null) {
                    Luban.with(this)
                            .load(mFile)
                            .ignoreBy(512)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    Toast.makeText(PostFlowActivity.this, "正在上传图片哦，请稍后~", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(File file) {
                                    doPostFlowRequest(et_content.getText().toString(), file);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(PostFlowActivity.this, "选取图片异常！", Toast.LENGTH_SHORT).show();
                                }
                            }).launch();
                } else {
                    doPostFlowRequest(et_content.getText().toString(), null);
                }
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_addPic:
                choicePic();
                break;
        }
    }

    private void doPostFlowRequest(String content, File file) {
        KeyBoardUtils.closeKeybord(et_content, this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("usrId", mUserSp.getString(Constant.sp_useId));
        map.put("content", content);
        if (file != null) {
            map.put("image", file);
        }
        HttpRequest.post(map, Urls.UPDATE_BOLO_C, this);
    }

    @Subscribe
    public void getRefreshStatus(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 0) {
                Toast.makeText(this, "发帖成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else if (code == -1) {
                Toast.makeText(this, "小盆友，不要挑事哦，不要出现作者名称哦...", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "未知错误...", Toast.LENGTH_SHORT).show();
        }
    }

    private void choicePic() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .theme(R.style.Matisse_Zhihu)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(1f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    List<Uri> uris = Matisse.obtainResult(data);
                    if (uris != null && uris.size() > 0) {
                        mFile = new File(FileUtil.getRealFilePath(this, uris.get(0)));
                        Bitmap bitmap = BitmapFactory.decodeFile(mFile.getAbsolutePath());
                        iv_addPic.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull final IOException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PostFlowActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull final Response response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = response.body().string();
                    LogUtils.d("jsonStr" + jsonStr);
                    JSONObject object = new JSONObject(jsonStr);
                    if (object.getInt("code") == 0) {
                        Toast.makeText(PostFlowActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.d(e.getMessage());
                    Toast.makeText(PostFlowActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
