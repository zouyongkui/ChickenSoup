package com.one.zyk.soup.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.request.HttpRequest;
import com.one.zyk.soup.utils.SizeUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserInfoActivity extends BaseActivity implements Callback {

    private File mHeadFile;
    private final int REQUEST_CODE_CHOOSE = 0x001;
    private final int REQUEST_CODE_CROP = 0x002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    List<Uri> uris = Matisse.obtainResult(data);
                    if (uris != null && uris.size() > 0) {
                        cropPic(uris.get(0));
                    } else {
                        Toast.makeText(this, "选取图片失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case REQUEST_CODE_CROP:
                    Glide.with(this)
                            .load(mHeadFile)
                            .into(new SimpleTarget<GlideDrawable>(SizeUtils.dp2px(50), SizeUtils.dp2px(50)) {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                                    iv_head.setImageDrawable(resource);
                                }
                            });
                    HashMap<String, Object> map = new HashMap<>();
                    HttpRequest.post(map, Urls.RELEASE_SOUP, this);

                    break;
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {

    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {

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
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void cropPic(Uri uri) {
        mHeadFile = new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".jpg");
        try {
            if (mHeadFile.exists()) {
                mHeadFile.delete();
                mHeadFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mHeadFile));//
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

}
