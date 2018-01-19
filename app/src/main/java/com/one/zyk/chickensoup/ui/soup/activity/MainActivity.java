package com.one.zyk.chickensoup.ui.soup.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.base.BaseActivity;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.bean.SoupBean;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.http.Urls;
import com.one.zyk.chickensoup.http.request.ServiceRequest;
import com.one.zyk.chickensoup.ui.soup.adapter.CommentsRvAdapter;
import com.one.zyk.chickensoup.utils.DateUtil;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    //    @BindView(R.id.clp_toolbar)
//    CollapsingToolbarLayout clp_toolbar;
    private CommentsRvAdapter mRvAdapter;
    //    @BindView(R.id.view_toolbar)
//    Toolbar viewToolbar;
    @BindView(R.id.mlv_comments)
    RecyclerView mlv;
    @BindView(R.id.iv_detail)
    ImageView iv_detail;
    @BindView(R.id.tv_copyRight)
    TextView tv_copyRight;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.tv_yearAndMonth)
    TextView tv_yearAndMonth;
    @BindView(R.id.soupParent)
    CoordinatorLayout coordinatorLayout;

    private String soupId = "";

    private List<CommentBean.DataBean> dataBeanList;
    private Date date = new Date();
    private String mDate_day = DateUtil.formatDateASD(date);
    private String mYearAndMonth = DateUtil.formatDateASYYYYMMM(date);
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locationManager.getProviders(true);
        if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Snackbar.make(coordinatorLayout, "请先打开网络定位哦！", Snackbar.LENGTH_LONG)
                    .setAction("", null)
                    .show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            String string = "纬度为：" + location.getLatitude() + ",经度为：" + location.getLongitude();
            Log.e("location", string);
        }else {
            Log.e("location", "null");

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");
    }

    @Override
    protected void initView() {
//        clp_toolbar.setMaxLines(6);
        dataBeanList = new ArrayList<>();
        mRvAdapter = new CommentsRvAdapter(dataBeanList, this);
        mlv.setLayoutManager(new LinearLayoutManager(this));
        mlv.setAdapter(mRvAdapter);
        tv_day.setText(mDate_day);
        tv_yearAndMonth.setText(mYearAndMonth);
    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        if (bean != null) {
            soupId = bean.getSoupid();
            dataBeanList.clear();
//            clp_toolbar.setTitle(bean.getSoup());
            SpannableStringBuilder span = new SpannableStringBuilder("缩进" + bean.getSoup());
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_copyRight.setText(span);
            for (SoupBean.DataBean d : bean.getData()) {
                CommentBean.DataBean data = new CommentBean.DataBean();
                data.setContent(d.getContent());
                data.setCreatetime(d.getCreatetime());
                data.setId(d.getId());
                data.setSoupid(d.getSoupid());
                data.setUserid(d.getUserid());
                dataBeanList.add(data);
            }
            Collections.reverse(dataBeanList); // 倒序排列
            String picUrl = Urls.BASEPICURL + bean.getPic();
            Log.e("pic", picUrl);
            Glide.with(this)
                    .load(picUrl)
                    .placeholder(R.mipmap.yijing)
                    .into(iv_detail);
            mRvAdapter.setDataBeanList(dataBeanList);
        }

    }

    @OnClick(R.id.fb_post_comment)
    void postComment() {
        Intent intent = new Intent(MainActivity.this, PostCommentActivity.class);
        intent.putExtra("soupId", soupId);
        startActivity(intent);
    }
}
