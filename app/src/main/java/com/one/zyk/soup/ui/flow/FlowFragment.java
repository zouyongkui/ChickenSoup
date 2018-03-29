package com.one.zyk.soup.ui.flow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.one.zyk.soup.R;
import com.one.zyk.soup.app.FloatBtnStatus;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.bean.CommunityBean;
import com.one.zyk.soup.callback.RvOnItemClickListener;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.blo.activity.PostCommentActivity;
import com.one.zyk.soup.ui.flow.FlowRvAdapter;
import com.one.zyk.soup.ui.flow.activity.FloorActivity;
import com.one.zyk.soup.utils.SizeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/8 14:39
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class FlowFragment extends BaseFragment implements RvOnItemClickListener {

    @BindView(R.id.rv_floor)
    RecyclerView rv_floor;
    private FlowRvAdapter mAdapter;
    private List<CommunityBean.CommunityListBean> mList;
    private boolean isBottomShow = true;
    RadioGroup mRadioGroup;
    private boolean isSlidingToLast = false;

    FloatBtnStatus mStatus = FloatBtnStatus.TOP;
    private FloatingActionButton mFloBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCommunity();
    }

    private void initCommunity() {
        mList = new ArrayList<>();
        mAdapter = new FlowRvAdapter(mList, getActivity(), this);
        rv_floor.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_floor.setAdapter(mAdapter);
        ServiceRequest.getCommunityList(this);
        initRecycleFlow();
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
    public void getCommunityList(CommunityBean bean) {
        if (bean != null) {
            mList = bean.getCommunityList();
            Collections.reverse(mList); // 倒序排列
            mAdapter.setList(mList);
        }
    }

    public void postFloor() {
        if (isVisible()) {
            Toast.makeText(getActivity(), "我要开始发帖子啦！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRvItemClick(int position, View view) {
        Intent intent = new Intent(getActivity(), FloorActivity.class);
        intent.putExtra("communityId", mList.get(position).getId());
        startActivity(intent);
    }
}
