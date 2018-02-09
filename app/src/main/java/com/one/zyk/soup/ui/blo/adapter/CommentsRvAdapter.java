package com.one.zyk.soup.ui.blo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.weight.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ykco on 2018/1/15.
 */

public class CommentsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<CommentBean.DataBean> dataBeanList;
    private Context mContext;
    private int mSize = 0;

    public void setDataBeanList(List<CommentBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    public CommentsRvAdapter(List<CommentBean.DataBean> dataBeanList, Context context) {
        this.dataBeanList = dataBeanList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_comments_lv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_comment.setText(dataBeanList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        mSize = dataBeanList == null ? 0 : dataBeanList.size();
        return mSize;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_comment)
        ExpandableTextView tv_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
