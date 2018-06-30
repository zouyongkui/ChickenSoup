package com.one.zyk.soup.ui.flow.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.one.zyk.soup.R;
import com.one.zyk.soup.app.FloatBtnStatus;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.bean.CommunityBean;
import com.one.zyk.soup.bean.FlowCircleBean;
import com.one.zyk.soup.bean.SoupManageBean;
import com.one.zyk.soup.callback.RvOnItemClickListener;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.flow.activity.FloorActivity;
import com.one.zyk.soup.ui.flow.activity.PostFlowActivity;
import com.one.zyk.soup.ui.flow.adapter.FlowRvAdapter;
import com.one.zyk.soup.utils.LogUtils;
import com.one.zyk.soup.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowCircleFragment extends BaseFragment implements RvOnItemClickListener {
    @BindView(R.id.rv_floor)
    RecyclerView rv_floor;
    @BindView(R.id.vf_flipper)
    ViewFlipper mViewFlipper;
    @BindView(R.id.swp_refresh)
    SwipeRefreshLayout swp_refresh;

    private FlowRvAdapter mAdapter;
    private List<FlowCircleBean.Data> mList;
    private boolean isBottomShow = true;
    private RadioGroup mRadioGroup;
    private boolean isSlidingToLast = false;

    private FloatBtnStatus mStatus = FloatBtnStatus.TOP;
    private FloatingActionButton mFloBtn;

    private boolean isFlipperReady = false;

    private static final int sPageSize = 10;
    private int mCurrentIndex = 0;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mViewFlipper != null) {
            if (isVisible()) {
                if (isFlipperReady && !mViewFlipper.isFlipping()) {
                    mViewFlipper.startFlipping();
                }
            } else {
                if (mViewFlipper.isFlipping()) {
                    mViewFlipper.stopFlipping();
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flow, null);
        ButterKnife.bind(this, view);
        mFloBtn = (FloatingActionButton) getActivity().findViewById(R.id.fb_post);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCommunity();
        swp_refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swp_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServiceRequest.getFlowCircleList(FlowCircleFragment.this, mCurrentIndex, sPageSize);
            }
        });
    }

    private void initCommunity() {
        swp_refresh.setRefreshing(true);
        mList = new ArrayList<>();
        mAdapter = new FlowRvAdapter(mList, getActivity(), this);
        rv_floor.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_floor.setAdapter(mAdapter);
        initRecycleFlow();
        ServiceRequest.getFlowCircleList(FlowCircleFragment.this, mCurrentIndex, sPageSize);
        ServiceRequest.getSoupList(this);
    }

    private void initRecycleFlow() {
        final LinearLayoutManager manager = (LinearLayoutManager) rv_floor.getLayoutManager();
        mRadioGroup = (RadioGroup) getActivity().findViewById(R.id.main_rg);
        rv_floor.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        mFloBtn.animate().translationY(mFloBtn.getHeight() + SizeUtils.dp2px(5) + mRadioGroup.getHeight());//完全隐藏
                        mStatus = FloatBtnStatus.HIDE;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >= 1) {
                    isSlidingToLast = true;
                    if (isBottomShow) {
                        isBottomShow = false;
                        mRadioGroup.animate().translationY(mRadioGroup.getHeight());
                        mFloBtn.animate().translationY(mRadioGroup.getHeight());//降半旗
                        mStatus = FloatBtnStatus.MIDDLE;
                    }
                } else if (dy <= -1) {
                    isSlidingToLast = false;
                    if (!isBottomShow) {
                        isBottomShow = true;
                        mRadioGroup.animate().translationY(0);
                        mFloBtn.animate().translationY(-mRadioGroup.getHeight());//升半旗
                        mStatus = FloatBtnStatus.MIDDLE;
                    } else {
                        if (mStatus == FloatBtnStatus.MIDDLE) {
                            mFloBtn.animate().translationY(0);
                            mStatus = FloatBtnStatus.TOP;
                        }
                    }
                }
            }
        });
    }

    @Subscribe
    public void getFlowCircleList(FlowCircleBean bean) {
        LogUtils.d("flowCircle  huoqu chenggong ");
        swp_refresh.setRefreshing(false);
        if (bean != null) {
            mList = bean.getData();
            mAdapter.setList(mList);
        }
    }

    @Subscribe
    public void getSoupList(SoupManageBean bean) {
        isFlipperReady = true;
        List<SoupManageBean.SoupListBean> list = bean.getSoupList();
        mViewFlipper.removeAllViews();
        for (SoupManageBean.SoupListBean listBean : list) {
            TextView textView = new TextView(getActivity());
            textView.setPadding(SizeUtils.dp2px(10), SizeUtils.dp2px(10), SizeUtils.dp2px(10), SizeUtils.dp2px(10));
            textView.setText(listBean.getContent());
            textView.setGravity(Gravity.CENTER);
            textView.setMaxLines(3);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "还不能点击哦！", Toast.LENGTH_SHORT).show();
                }
            });
//            textView.setTextSize(SizeUtils.sp2px(6));
            textView.setTextColor(Color.BLACK);
            mViewFlipper.addView(textView);
        }
        mViewFlipper.startFlipping();
    }

    public void postFloor() {
        if (isVisible()) {
            Intent intent = new Intent(getActivity(), PostFlowActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRvItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.iv_postComment:
                Toast.makeText(getActivity(), "我要开始评论了！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
