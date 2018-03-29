package com.one.zyk.soup.ui.flow.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.FloorBean;
import com.one.zyk.soup.http.request.ServiceRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloorActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_line)
    TextView mTvLine;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_time)
    TextView mTvTime;


    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("communityId");
    }

    @Override
    protected void initView() {
        ServiceRequest.getFloors(this, id);
    }

    @Override
    protected <T> void onRetrofitCallBack(T t) {
        FloorBean floorBean = (FloorBean) t;
        mTvContent.setText(floorBean.getCommunity().getContent());
    }
}
