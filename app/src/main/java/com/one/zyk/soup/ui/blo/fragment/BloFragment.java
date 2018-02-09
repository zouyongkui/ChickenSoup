package com.one.zyk.soup.ui.blo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.bean.SoupBean;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.blo.activity.PostCommentActivity;
import com.one.zyk.soup.ui.blo.adapter.CommentsRvAdapter;
import com.one.zyk.soup.utils.DateUtil;
import com.one.zyk.soup.utils.SPUtils;
import com.one.zyk.soup.utils.SizeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/8 14:39
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class BloFragment extends BaseFragment {
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
    @BindView(R.id.tv_temperature)
    TextView tv_temperature;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    private RadioGroup mRadioGroup;
    boolean isBottomShow = true;
    private String soupId = "";

    private List<CommentBean.DataBean> dataBeanList;
    private Date date = new Date();
    private String mDate_day = DateUtil.formatDateASD(date);
    private String mYearAndMonth = DateUtil.formatDateASYYYYMMM(date);
    private FloatingActionButton mFloBtn;

    private boolean mIsVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blo, null);
        ButterKnife.bind(this, view);
        mRadioGroup = (RadioGroup) getActivity().findViewById(R.id.main_rg);
        mFloBtn = (FloatingActionButton) getActivity().findViewById(R.id.fb_post);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    protected void initView() {
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int height = iv_detail.getHeight();
                if (verticalOffset >= -25) {
                    swipe_refresh.setEnabled(true);
                    if (!isBottomShow) {    //出现
                        isBottomShow = true;
                        mRadioGroup.animate().translationY(0);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mFloBtn.animate().translationY(0);
                            }
                        }, 100);
                    }
                } else {
                    swipe_refresh.setEnabled(false);
                    if (isBottomShow && verticalOffset <= -height + 25) {  //隐藏
                        isBottomShow = false;
                        mRadioGroup.animate().translationY(mRadioGroup.getHeight());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mFloBtn.animate().translationY(mRadioGroup.getHeight() + mFloBtn.getHeight() + SizeUtils.dp2px(5));
                            }
                        }, 100);
                    }
                }
            }
        });

        swipe_refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServiceRequest.getVisitCount(BloFragment.this);
                ServiceRequest.getSoup(BloFragment.this, "", "");
            }
        });

        dataBeanList = new ArrayList<>();
        mRvAdapter = new CommentsRvAdapter(dataBeanList, getActivity());
        mlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mlv.setAdapter(mRvAdapter);
        tv_day.setText(mDate_day);
        tv_yearAndMonth.setText(mYearAndMonth);
        tv_temperature.setText("银河/核心区  " + getTemperature());
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");

    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        swipe_refresh.setRefreshing(false);
        if (bean != null) {
            soupId = bean.getSoupid();
            dataBeanList.clear();
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

    public void postComment() {
        if (isVisible()) {
            if (!TextUtils.isEmpty(soupId)) {
                Intent intent = new Intent(getActivity(), PostCommentActivity.class);
                intent.putExtra("soupId", soupId);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "网络状态异常，请检查网络后重试", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
