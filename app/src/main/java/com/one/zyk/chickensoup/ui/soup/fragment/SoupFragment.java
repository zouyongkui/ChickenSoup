package com.one.zyk.chickensoup.ui.soup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.base.BaseFragment;
import com.one.zyk.chickensoup.ui.soup.adapter.CommentsAdapter;
import com.one.zyk.chickensoup.ui.soup.activity.PostCommentActivity;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.bean.SoupBean;
import com.one.zyk.chickensoup.bean.VisitCountBean;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.http.request.ServiceRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SoupFragment extends BaseFragment {

    @BindView(R.id.tv_soup)
    TextView tv_soup;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.mlv_comments)
    ListView mlv;
    @BindView(R.id.tv_visit)
    TextView tv_visit;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.fb_post_comment)
    FloatingActionButton fb_post_comment;
    private List<CommentBean.DataBean> dataBeanList;
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
        srl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initSoupInfo();
            }
        });
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");
        dataBeanList = new ArrayList<>();
        mCommentsAdapter = new CommentsAdapter(dataBeanList, getActivity());
        mlv.setAdapter(mCommentsAdapter);
    }

    private void initSoupInfo() {
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");
    }

    @Override
    public void onResume() {
        super.onResume();
        initSoupInfo();
    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        if (bean != null) {
            soupId = bean.getSoupid();
            tv_soup.setText("       " + bean.getSoup());
            srl.setRefreshing(false);
            dataBeanList.clear();
            for (SoupBean.DataBean d : bean.getData()) {
                CommentBean.DataBean data = new CommentBean.DataBean();
                data.setContent(d.getContent());
                data.setCreatetime(d.getCreatetime());
                data.setId(d.getId());
                data.setSoupid(d.getSoupid());
                data.setUserid(d.getUserid());
                dataBeanList.add(data);
            }
            if (dataBeanList == null || dataBeanList.size() == 0) {
                tv_comment.setText("还没有评论哦，快来抢占沙发吧！");
            } else {
                tv_comment.setText("--网友评论--");
            }
            Collections.reverse(dataBeanList); // 倒序排列
            mCommentsAdapter.setDataBeanList(dataBeanList);
        }

    }

    @Subscribe
    public void updateComment(CommentBean bean) {
        srl.setRefreshing(false);
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
