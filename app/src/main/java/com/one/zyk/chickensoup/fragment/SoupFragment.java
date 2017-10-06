package com.one.zyk.chickensoup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.activity.CommentsAdapter;
import com.one.zyk.chickensoup.activity.PostCommentActivity;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.bean.SoupBean;
import com.one.zyk.chickensoup.bean.VisitCountBean;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.http.request.ServiceRequest;
import com.one.zyk.chickensoup.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SoupFragment extends BaseFragment {

    @BindView(R.id.tv_soup)
    TextView tv_soup;
    @BindView(R.id.rfl_refresh)
    TwinklingRefreshLayout rfl_refresh;
    @BindView(R.id.mlv_comments)
    ListView mlv;
    @BindView(R.id.tv_visit)
    TextView tv_visit;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.fb_post_comment)
    FloatingActionButton fb_post_comment;
    private List<CommentBean.DataBean> dataBeanList;
    private final String IMEI = DeviceUtils.getIMEI();
    private final String BRAND = DeviceUtils.getDeviceBrand() + DeviceUtils.getSystemModel();
    private String soupId = "";
    private CommentsAdapter mCommentsAdapter;

    public static SoupFragment getInstance(String title) {
        SoupFragment sf = new SoupFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_soup, null);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    protected void initView() {
        rfl_refresh.setEnableLoadmore(false);
        rfl_refresh.setHeaderView(new SinaRefreshView(getActivity()));
        rfl_refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                initSoupInfo();
            }
        });
        dataBeanList = new ArrayList<>();
        mCommentsAdapter = new CommentsAdapter(dataBeanList, getActivity());
        mlv.setAdapter(mCommentsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initSoupInfo();
    }

    private void initSoupInfo() {
        ServiceRequest.getSoup(this, IMEI, BRAND);
        ServiceRequest.getVisitCount(this);
    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        try {
            tv_soup.setText("       " + bean.getSoup());
            soupId = bean.getSoupid();
            Log.e("---", soupId);
            ServiceRequest.getCommentList(this, soupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!tv_soup.getText().toString().equals("")) {
            rfl_refresh.finishRefreshing();
        }
    }

    @Subscribe
    public void updateComment(CommentBean bean) {
        rfl_refresh.finishRefreshing();
        dataBeanList = bean.getData();
        if (dataBeanList == null || dataBeanList.size() == 0) {
            tv_comment.setText("还没有评论哦，快来抢占沙发吧！");
        } else {
            tv_comment.setText("--网友评论--");
        }
        Collections.reverse(dataBeanList); // 倒序排列
        mCommentsAdapter.setDataBeanList(dataBeanList);
    }

    @Subscribe
    public void setTv_visit(VisitCountBean bean) {
        tv_visit.setText("--- 当前访问总人次：" + bean.getVisitCount() + " ---");
    }

    @OnClick(R.id.fb_post_comment)
    void postComment() {
        Intent intent = new Intent(getActivity(), PostCommentActivity.class);
        intent.putExtra("soupId", soupId);
        startActivity(intent);
    }
}
