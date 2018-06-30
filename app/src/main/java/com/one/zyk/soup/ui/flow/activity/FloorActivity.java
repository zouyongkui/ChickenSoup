package com.one.zyk.soup.ui.flow.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.FloorBean;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import com.one.zyk.soup.ui.flow.adapter.FloorsRvAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class FloorActivity extends BaseActivity implements View.OnClickListener {

    TextView tv_back;
    TextView tv_content, tv_time, tv_title, et_comment;
    //    ImageView iv_postLike;
    ImageView iv_postComment;
    TextView tv_likeCount, tv_comCount;
    RecyclerView rv_content;
    private FloorsRvAdapter mFloorsRvAdapter;
    private FloorBean mFloorBean;
    private String mCommunityId;


    private boolean isKeyboardShowed = false;
    private String comment = "";
    private View bottomView;


    @BindView(R.id.swp_refresh)
    SwipeRefreshLayout swp_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        mCommunityId = getIntent().getStringExtra("communityId");
        ButterKnife.bind(this);

    }

    @Override
    protected void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        et_comment = (EditText) findViewById(R.id.et_comment);
//        iv_postLike = (ImageView) findViewById(R.id.iv_postLike);
        iv_postComment = (ImageView) findViewById(R.id.iv_postComment);
        tv_likeCount = (TextView) findViewById(R.id.tv_like_count);
        tv_comCount = (TextView) findViewById(R.id.tv_com_count);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        bottomView = findViewById(R.id.rl_bottom);

        tv_back.setOnClickListener(this);
        et_comment.setOnClickListener(this);
        iv_postComment.setOnClickListener(this);
//        iv_postLike.setOnClickListener(this);
        mFloorsRvAdapter = new FloorsRvAdapter(this, mFloorBean);

        rv_content.setLayoutManager(mLayoutManager);
        rv_content.setAdapter(mFloorsRvAdapter);
        ServiceRequest.getFloors(this, mCommunityId);


        swp_refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swp_refresh.setRefreshing(true);
        swp_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServiceRequest.getFloors(FloorActivity.this, mCommunityId);
            }
        });
    }

    @Subscribe
    public void handleFloorBean(FloorBean floorBean) {
        swp_refresh.setRefreshing(false);
        mFloorBean = floorBean;
        mFloorsRvAdapter.setFloorBean(mFloorBean);
//        int floorsCount = mFloorBean.getFloors() == null ? 0 : mFloorBean.getFloors().size();
//        if (floorsCount > 0) {
//            iv_postComment.setVisibility(View.VISIBLE);
//            tv_comCount.setVisibility(View.VISIBLE);
//            tv_comCount.setText(String.valueOf(floorsCount));
//        } else {
//            tv_comCount.setVisibility(View.GONE);
//            iv_postComment.setVisibility(View.GONE);
//        }
    }

    @Subscribe
    public void handlePostStatus(String json) {
        swp_refresh.setRefreshing(false);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 0) {
                Toast.makeText(this, "评论成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "评论失败！", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void postComment() {
        swp_refresh.setRefreshing(true);
        String str_comment = et_comment.getText().toString();
        ServiceRequest.updateFloor(this, mCommunityId, mUserSp.getString(Constant.sp_useId), str_comment);
        hideInputMethod(this);
        et_comment.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_postComment:
                postComment();
                break;
        }
    }

    private Boolean hideInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.hideSoftInputFromWindow(bottomView.getWindowToken(), 0);
    }

    public boolean isShouldHideInput(MotionEvent event) {
        if (bottomView != null) {
            int[] leftTop = {0, 0};
            bottomView.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + bottomView.getHeight(), right = left + bottomView.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShouldHideInput(ev)) {
                if (hideInputMethod(this)) {
                    isKeyboardShowed = false;
                    //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @OnTextChanged(R.id.et_comment)
    void onTextChanged(CharSequence text) {
        if (!text.toString().equals("")) {
            iv_postComment.setImageResource(R.mipmap.reply);
            iv_postComment.setEnabled(true);
        } else {
            iv_postComment.setImageResource(R.mipmap.reply_gray);
            iv_postComment.setEnabled(false);
        }
    }
}
