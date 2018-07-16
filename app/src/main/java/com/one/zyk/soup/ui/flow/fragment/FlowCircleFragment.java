package com.one.zyk.soup.ui.flow.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.app.FloatBtnStatus;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.bean.FlowCircleBean;
import com.one.zyk.soup.bean.SoupListEntity;
import com.one.zyk.soup.callback.RvOnItemClickListener;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.flow.activity.PostFlowActivity;
import com.one.zyk.soup.ui.flow.adapter.FlowRvAdapter;
import com.one.zyk.soup.utils.KeyBoardUtils;
import com.one.zyk.soup.utils.SizeUtils;
import com.one.zyk.soup.weight.CustomPopWindow;

import org.json.JSONException;
import org.json.JSONObject;

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
    private List<FlowCircleBean.DataBean> mList;
    private boolean isBottomShow = true;
    private RadioGroup mRadioGroup;
    private boolean isSlidingToLast = false;
    private FloatBtnStatus mStatus = FloatBtnStatus.TOP;
    private FloatingActionButton mFloBtn;
    private boolean isFlipperReady = false;
    private static final int sPageSize = 10;
    private int mCurrentIndex = 0;
    private CustomPopWindow mPopWindow;
    private TextView mTv_send;
    private EditText mEt_comment;
    private boolean mIsEmpty = true;
    private View mView;
    private String mUsrId;
    private Drawable mDrawableNormal;
    private Drawable mDrawableSend;
    private String mCircleId;

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
        mView = inflater.inflate(R.layout.fragment_flow, null);
        ButterKnife.bind(this, mView);
        mFloBtn = (FloatingActionButton) getActivity().findViewById(R.id.fb_post);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUsrId = mUserSp.getString(Constant.sp_usrId);
        mDrawableNormal = getActivity().getDrawable(R.drawable.btn_gray);
        mDrawableSend = getActivity().getDrawable(R.drawable.btn_blue);
        initPopWindow();
        initCommunity();
        swp_refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swp_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentIndex = 0;
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
        mRadioGroup = getActivity().findViewById(R.id.main_rg);
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
                        mCurrentIndex++;
                        ServiceRequest.getFlowCircleList(FlowCircleFragment.this, mCurrentIndex, sPageSize);
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
        swp_refresh.setRefreshing(false);
        if (mCurrentIndex == 0) {
            mList.clear();
        } else {
            if (bean.getData().size() == 0) {
                mCurrentIndex--;
                mAdapter.hideBottom(true);
                Toast.makeText(getActivity(), "没有更多内容了！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        mList.addAll(bean.getData());
        mAdapter.setList(mList);
    }

    @Subscribe
    public void getBoloList(SoupListEntity bean) {
        isFlipperReady = true;
        List<SoupListEntity.DataBean> dataBeanList = bean.getData();
        mViewFlipper.removeAllViews();
        for (SoupListEntity.DataBean listBean : dataBeanList) {
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
            textView.setTextColor(Color.BLACK);
            mViewFlipper.addView(textView);
        }
        if (dataBeanList.size() > 1) {
            mViewFlipper.startFlipping();
        }
    }

    @Subscribe
    public void postCircleComment(String str) {
        try {
            JSONObject object = new JSONObject(str);
            if (object.getInt("code") == 0) {
                Toast.makeText(getActivity(), "评论成功  ^_^", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "评论失败咯，请再试一下吧！-_-||", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage() + " raw : " + str, Toast.LENGTH_SHORT).show();
        }
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
                mCircleId = mList.get(position).getId();
                showCommentWindow();
                break;
            case R.id.iv_show:
                Toast.makeText(getActivity(), "还不能点击图片哦！ (^o^) ~~~", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initPopWindow() {
        mPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(R.layout.pop_comment)//显示的布局，还可以通过设置一个View
//                .size(ScreenUtils.getScreenWidth(), SizeUtils.dp2px(50)) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                .create();//创建PopupWindow
        mTv_send = (TextView) mPopWindow.getContentView().findViewById(R.id.tv_send);
        mEt_comment = (EditText) mPopWindow.getContentView().findViewById(R.id.et_comment);
        mTv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsEmpty) {
                    ServiceRequest.postCircleComment(FlowCircleFragment.this, mUsrId, mEt_comment.getText().toString(), mCircleId, " ");
                }
                mEt_comment.setText("");
                KeyBoardUtils.toggleKeybord(mEt_comment, getActivity());
                mPopWindow.dissmiss();
            }
        });
        mEt_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mTv_send.setBackground(mDrawableNormal);
                    mIsEmpty = true;
                } else {
                    mTv_send.setBackground(mDrawableSend);
                    mIsEmpty = false;
                }
            }
        });
    }

    private void showCommentWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mPopWindow.showAtLocation(mView, Gravity.TOP, 0, dm.heightPixels);
        KeyBoardUtils.toggleKeybord(mEt_comment, getActivity());
    }
}
