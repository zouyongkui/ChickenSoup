package com.one.zyk.chickensoup.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.http.request.ServiceRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsListActivity extends BaseActivity {
    private List<CommentBean.DataBean> dataBeanList;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.pgb)
    ProgressBar pgb;

    private CommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        dataBeanList = new ArrayList<>();
        commentListAdapter = new CommentListAdapter(dataBeanList, this);
        lv.setAdapter(commentListAdapter);
        ServiceRequest.getCommentList(this, getIntent().getStringExtra("soupId"));

    }

    @Subscribe
    public void getCommentsResult(CommentBean commentBean) {
        pgb.setVisibility(View.GONE);
        dataBeanList = commentBean.getData();
        Collections.reverse(dataBeanList); // 倒序排列

        commentListAdapter.setmCommentList(dataBeanList);
    }
}
