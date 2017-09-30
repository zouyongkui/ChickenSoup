package com.one.zyk.chickensoup.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.bean.CommentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyk on 2017/9/23.
 */

public class CommentsAdapter extends BaseAdapter {
    private List<CommentBean.DataBean> dataBeanList;
    private Context mContext;
    private int mSize = 0;

    public void setDataBeanList(List<CommentBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    public CommentsAdapter(List<CommentBean.DataBean> dataBeanList, Context context) {
        this.dataBeanList = dataBeanList;
        this.mContext = context;
    }

    @Override
    public int getCount() {

        mSize = dataBeanList == null ? 0 : dataBeanList.size();

        return mSize;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_comments_lv, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_comment.setText(dataBeanList.get(i).getContent());
        return view;
    }


    class ViewHolder {
        @BindView(R.id.tv_comment)
        TextView tv_comment;

        public ViewHolder(View view) {
            ButterKnife.bind(ViewHolder.this, view);
        }
    }
}
