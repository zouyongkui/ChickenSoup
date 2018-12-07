package com.one.zyk.soup.ui.flow.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.one.zyk.soup.R;
import com.one.zyk.soup.ui.flow.adapter.FlowRvAdapter;
import com.one.zyk.soup.utils.LogUtils;


public class PhotoViewActivity extends AppCompatActivity {

    private PhotoView mIv_photo;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        String picUrl = getIntent().getStringExtra("picUrl");
        mIv_photo = findViewById(R.id.iv_photo);
        mImageView = findViewById(R.id.iv_IV);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mIv_photo.enable();
        mIv_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LogUtils.d(picUrl, mIv_photo.getScaleType());

        Glide.with(this)
                .load(picUrl)
                .into(mImageView);

    }

}
