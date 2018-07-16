package com.one.zyk.soup.ui.me;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.request.HttpRequest;
import com.one.zyk.soup.utils.MyGlideEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_nickName)
    EditText et_nickName;
    private File mUserFace;
    private String mUserName, mUserId;
    private final int REQUEST_CODE_CHOOSE = 0x001;
    private final int REQUEST_CODE_CROP = 0x002;
    private ProgressDialog mProgressDialog;
    private boolean isPicChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("请稍后...");
        mUserName = mUserSp.getString(Constant.sp_usrName);
        mUserId = mUserSp.getString(Constant.sp_usrId);
        Glide.with(this)
                .load(Urls.PIC_URL + mUserSp.getString(Constant.sp_usrFace))
                .into(iv_head);
        et_nickName.setText(mUserName);
    }

    private void selectPic() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.PNG, MimeType.JPEG))
                .countable(true)
                .maxSelectable(1)
                .theme(R.style.Matisse_Zhihu)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(1f)
                .imageEngine(new MyGlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void cropPic(Uri uri) {
        mUserFace = new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".jpg");
        try {
            if (mUserFace.exists()) {
                mUserFace.delete();
                mUserFace.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mUserFace));//
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
        intent.putExtra("outputX", 140);
        intent.putExtra("outputY", 140);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    @OnTextChanged({R.id.et_nickName})
    public void OnTextChanged(CharSequence text) {
        if (!TextUtils.equals(text, mUserName) || isPicChanged) {
            tv_save.setVisibility(View.VISIBLE);
        } else {
            tv_save.setVisibility(View.GONE);
        }
        mUserName = text.toString();
    }

    @OnClick({R.id.rl_head, R.id.rl_nickName, R.id.tv_save})
    public void onUserClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head:
                selectPic();
                break;
            case R.id.rl_nickName:
                break;
            case R.id.tv_save:
                saveUserInfo();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE:
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    if (uris != null && uris.size() > 0) {
                        cropPic(uris.get(0));
                    } else {
                        Toast.makeText(this, "选取图片失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    Glide.with(this)
                            .load(mUserFace)
                            .into(iv_head);
                    isPicChanged = true;
                    tv_save.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void saveUserInfo() {
        mProgressDialog.show();
        HashMap<String, Object> map = new HashMap<>();
        map.put("usrId", mUserId);
        map.put("usrName", mUserName);
        if (mUserFace != null) {
            map.put("usrFace", mUserFace);
        }
        HttpRequest.post(map, Urls.POST_MOD_USR_INFO, new HttpRequest.MyCallBack() {
            @Override
            public void onSuccess(String str) {
                try {
                    JSONObject object = new JSONObject(str);
                    if (object.getInt("code") == 0) {
                        mUserSp.put(Constant.sp_usrFace, object.getString("usrFace"));
                        mUserSp.put(Constant.sp_usrName, object.getString("usrName"));
                        Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UserInfoActivity.this, "修改失败了哦  请再试一下吧！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mProgressDialog.dismiss();
            }

            @Override
            public void onFail(IOException e) {
                Toast.makeText(UserInfoActivity.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

}
