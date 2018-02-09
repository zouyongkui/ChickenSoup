package com.one.zyk.soup.ui.blo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.ui.blo.activity.PostCommentActivity;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.bean.SoupBean;
import com.one.zyk.soup.bean.VisitCountBean;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.blo.adapter.CommentsRvAdapter;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SoupFragment extends BaseFragment {

    @BindView(R.id.mlv_comments)
    RecyclerView mlv;
    @BindView(R.id.tv_visit)
    TextView tv_visit;
    @BindView(R.id.fb_post_comment)
    FloatingActionButton fb_post_comment;
    @BindView(R.id.view_toolbar)
    Toolbar viewToolbar;
    @BindView(R.id.clp_toolbar)
    CollapsingToolbarLayout clpToolbar;
    private List<CommentBean.DataBean> dataBeanList;
    private String soupId = "";
    private CommentsRvAdapter mRvAdapter;

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

//        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initSoupInfo();
//            }
//        });
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");
        dataBeanList = new ArrayList<>();
        mRvAdapter = new CommentsRvAdapter(dataBeanList, getActivity());
        mlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mlv.setAdapter(mRvAdapter);
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
            clpToolbar.setTitle(bean.getSoup());
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
            Collections.reverse(dataBeanList); // 倒序排列
            String picUrl = Urls.BASEPICURL + bean.getPic();
            Log.e("pic", picUrl);
//            Glide.with(getActivity()).load(picUrl).into(iv_pic);
            mRvAdapter.setDataBeanList(dataBeanList);
        }

    }

    @Subscribe
    public void updateComment(CommentBean bean) {
//        srl.setRefreshing(false);
        dataBeanList = bean.getData();
        Collections.reverse(dataBeanList); // 倒序排列
        mRvAdapter.setDataBeanList(dataBeanList);
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
