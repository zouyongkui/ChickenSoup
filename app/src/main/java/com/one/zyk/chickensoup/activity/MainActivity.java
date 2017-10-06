package com.one.zyk.chickensoup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.one.zyk.chickensoup.activity.adapter.CommentsAdapter;
import com.one.zyk.chickensoup.bean.SoupBean;
import com.one.zyk.chickensoup.bean.VisitCountBean;
import com.one.zyk.chickensoup.utils.DeviceUtils;
import com.one.zyk.chickensoup.utils.KeyBoardUtils;
import com.one.zyk.chickensoup.weight.MyListView;
import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.http.request.ServiceRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private TextView tv_soup;
    private SwipeRefreshLayout srl;
    private MyListView mlv;
    @BindView(R.id.et_send)
    EditText et_send;
    @BindView(R.id.tv_visit)
    TextView tv_visit;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    private List<CommentBean.DataBean> dataBeanList;
    private final String IMEI = DeviceUtils.getIMEI();
    private final String BRAND = DeviceUtils.getDeviceBrand() + DeviceUtils.getSystemModel();
    private String soupId = "";
    private CommentsAdapter mCommentsAdapter;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("--BRAND ", BRAND);
        Log.e("--IMEI ", IMEI);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        tv_soup = (TextView) findViewById(R.id.tv_soup);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initSoupInfo();
            }
        });
        mlv = (MyListView) findViewById(R.id.mlv_comments);
        dataBeanList = new ArrayList<>();
        mCommentsAdapter = new CommentsAdapter(dataBeanList, this);
        mlv.setAdapter(mCommentsAdapter);
        initSoupInfo();
    }

    private void initSoupInfo() {
        srl.setRefreshing(true);
        ServiceRequest.getSoup(this, IMEI, BRAND);
        ServiceRequest.getVisitCount(this);
    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        try {
            tv_soup.setText(bean.getSoup());
            soupId = bean.getSoupid();
            Log.e("---", soupId);
            ServiceRequest.getCommentList(this, soupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!tv_soup.getText().toString().equals("")) {
            srl.setRefreshing(false);
        }
        et_send.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    if (("Sudo.").equals(textView.getText().toString())) {
                        Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    ServiceRequest.postComment(MainActivity.this, IMEI, soupId, textView.getText().toString());
                    srl.setRefreshing(true);
                    KeyBoardUtils.closeKeybord(et_send, MainActivity.this);
                    et_send.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @Subscribe
    public void updateComment(CommentBean bean) {
        if (isFirst) {
            isFirst = false;
        } else {
            Toast.makeText(MainActivity.this, "已经刷新评论！", Toast.LENGTH_SHORT).show();
        }
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
}
