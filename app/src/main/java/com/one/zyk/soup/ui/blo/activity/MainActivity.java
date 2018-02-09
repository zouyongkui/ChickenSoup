package com.one.zyk.soup.ui.blo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.bean.SoupBean;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.blo.adapter.CommentsRvAdapter;
import com.one.zyk.soup.utils.DateUtil;
import com.one.zyk.soup.utils.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private CommentsRvAdapter mRvAdapter;
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
    @BindView(R.id.tv_temperature)
    TextView tv_temperature;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout llDetailBottom;

    boolean isBottomShow = true;
    private String soupId = "";

    private List<CommentBean.DataBean> dataBeanList;
    private Date date = new Date();
    private String mDate_day = DateUtil.formatDateASD(date);
    private String mYearAndMonth = DateUtil.formatDateASYYYYMMM(date);
    private boolean isFirstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");


    }

    @Override
    protected void initView() {
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (isFirstLaunch) {
                    isFirstLaunch = false;
                    return;
                }
                int height = iv_detail.getHeight();
                int botHeight = llDetailBottom.getHeight();
                if (verticalOffset >= 0) {
                    swipe_refresh.setEnabled(true);
                    if (isBottomShow) {  //隐藏
                        isBottomShow = false;
                        llDetailBottom.animate().translationY(llDetailBottom.getHeight());
                    }
                } else {
                    swipe_refresh.setEnabled(false);
                    if (!isBottomShow && verticalOffset <= -height + 25) {    //出现
                        isBottomShow = true;
                        llDetailBottom.animate().translationY(0);
                    }

                }
            }
        });

        swipe_refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServiceRequest.getVisitCount(MainActivity.this);
                ServiceRequest.getSoup(MainActivity.this, "", "");
            }
        });


        dataBeanList = new ArrayList<>();
        mRvAdapter = new CommentsRvAdapter(dataBeanList, this);
        mlv.setLayoutManager(new LinearLayoutManager(this));
        mlv.setAdapter(mRvAdapter);
        tv_day.setText(mDate_day);
        tv_yearAndMonth.setText(mYearAndMonth);
        tv_temperature.setText("银河/核心区  " + getTemperature());

    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        swipe_refresh.setRefreshing(false);
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

    private String getTemperature() {
        String dateStr = DateUtil.formatDateASYYYYMMDD(new Date());
        SPUtils spUtils = SPUtils.getInstance(Constant.db_device);
        String temperature = spUtils.getString(dateStr);
        if (TextUtils.isEmpty(temperature)) {
            spUtils.clear();
            int max = 520;
            int min = -273;
            Random random = new Random();
            int randInt = random.nextInt(max) % (max - min + 1) + min;
            temperature = randInt + "°C";
            spUtils.put(dateStr, temperature);
            Log.e("newTemperature", temperature);
        } else {
            Log.e("oldTemperature", temperature);

        }
        return temperature;
    }
}
