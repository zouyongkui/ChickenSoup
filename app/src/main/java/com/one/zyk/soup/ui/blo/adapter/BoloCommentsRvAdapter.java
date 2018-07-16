package com.one.zyk.soup.ui.blo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.BoloEntity;
import com.one.zyk.soup.utils.TimeUtils;
import com.one.zyk.soup.weight.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ykco on 2018/1/15.
 */

public class BoloCommentsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<BoloEntity.DataBean.CommentListBean> mCommentListBeanList;
    private Context mContext;

    public void setDataBeanList(List<BoloEntity.DataBean.CommentListBean> dataBeanList) {
        this.mCommentListBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    public BoloCommentsRvAdapter(List<BoloEntity.DataBean.CommentListBean> dataBeanList, Context context) {
        mCommentListBeanList = dataBeanList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_comments_lv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_comment.setText((position + 1) + "# " + mCommentListBeanList.get(position).getContent());
        ((ViewHolder) holder).tv_postTime.setText(TimeUtils.getFriendlyTimeSpanByNow(mCommentListBeanList.get(position).getCreatetime()));
    }

    @Override
    public int getItemCount() {
        return mCommentListBeanList == null ? 0 : mCommentListBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_comment)
        ExpandableTextView tv_comment;
        @BindView(R.id.tv_postTime)
        TextView tv_postTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
