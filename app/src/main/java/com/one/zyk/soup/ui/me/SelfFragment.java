package com.one.zyk.soup.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.request.HttpRequest;
import com.one.zyk.soup.ui.manage.ReleaseSoupActivity;
import com.one.zyk.soup.utils.FileUtil;
import com.one.zyk.soup.utils.LogUtils;
import com.one.zyk.soup.utils.SPUtils;
import com.one.zyk.soup.utils.ScreenUtils;
import com.one.zyk.soup.utils.SizeUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/8 15:59
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class SelfFragment extends BaseFragment {

    @BindView(R.id.btn_release)
    Button btn_release;
    @BindView(R.id.iv_head)
    ImageView iv_head;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ("+8617600209962".equals(SPUtils.getInstance(Constant.db_user).getString("phone"))) {
            btn_release.setVisibility(View.VISIBLE);
        } else {
            btn_release.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_release, R.id.iv_head})
    public void goRelease(View view) {
        switch (view.getId()) {
            case R.id.btn_release:
                startActivity(new Intent(getActivity(), ReleaseSoupActivity.class));
                break;
            case R.id.iv_head:
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
