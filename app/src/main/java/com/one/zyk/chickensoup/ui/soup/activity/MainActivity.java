package com.one.zyk.chickensoup.ui.soup.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.base.BaseActivity;
import com.one.zyk.chickensoup.bean.CommentBean;
import com.one.zyk.chickensoup.bean.SoupBean;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.http.Urls;
import com.one.zyk.chickensoup.http.request.ServiceRequest;
import com.one.zyk.chickensoup.ui.soup.adapter.CommentsRvAdapter;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    //    @BindView(R.id.clp_toolbar)
//    CollapsingToolbarLayout clp_toolbar;
    private CommentsRvAdapter mRvAdapter;
    //    @BindView(R.id.view_toolbar)
//    Toolbar viewToolbar;
    @BindView(R.id.mlv_comments)
    RecyclerView mlv;
    @BindView(R.id.iv_detail)
    ImageView iv_detail;
    @BindView(R.id.tv_copyRight)
    TextView tv_copyRight;

    private String soupId = "";

    private List<CommentBean.DataBean> dataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
//        clp_toolbar.setMaxLines(6);
        dataBeanList = new ArrayList<>();
        mRvAdapter = new CommentsRvAdapter(dataBeanList, this);
        mlv.setLayoutManager(new LinearLayoutManager(this));
        mlv.setAdapter(mRvAdapter);
        ServiceRequest.getVisitCount(this);
        ServiceRequest.getSoup(this, "", "");
    }

    @Subscribe
    public void getSoup(SoupBean bean) {
        if (bean != null) {
            soupId = bean.getSoupid();
            dataBeanList.clear();
//            clp_toolbar.setTitle(bean.getSoup());
            SpannableStringBuilder span = new SpannableStringBuilder("缩进" + bean.getSoup());
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_copyRight.setText(span);
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
            Glide.with(this)
                    .load(picUrl)
                    .placeholder(R.mipmap.yijing)
                    .into(iv_detail);
            for (int i = 0; i < 25; i++) {
                CommentBean.DataBean dataBean = new CommentBean.DataBean();
                dataBean.setContent("第一个人" + i);
                dataBeanList.add(dataBean);
            }
            mRvAdapter.setDataBeanList(dataBeanList);
        }

    }
}
