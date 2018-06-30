package com.one.zyk.soup.ui.flow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.FlowCircleBean;
import com.one.zyk.soup.callback.RvOnItemClickListener;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.utils.DateUtil;
import com.one.zyk.soup.weight.MyListView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/8 18:18
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class FlowRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FlowCircleBean.Data> mList;
    private Context mContext;
    private RvOnItemClickListener mOnItemClickListener;


    public FlowRvAdapter(List<FlowCircleBean.Data> list, Context context, RvOnItemClickListener listener) {
        mList = list;
        mContext = context;
        mOnItemClickListener = listener;
    }

    public void setList(List<FlowCircleBean.Data> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_community_lv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        FlowCircleBean.Data data = mList.get(position);
        ((ViewHolder) holder).tv_name.setText(data.getUsrName());
        ((ViewHolder) holder).tv_content.setText(data.getContent());
        ((ViewHolder) holder).tv_time.setText(DateUtil.formatDateASYYYYMMDDHHMM(data.getCreateTime()));
        Glide.with(mContext)
                .load(Urls.PIC_URL + data.getPicUrl())
                .into(((ViewHolder) holder).iv_show);
        ((ViewHolder) holder).iv_postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onRvItemClick(holder.getAdapterPosition(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        ExpandableTextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.iv_postComment)
        ImageView iv_postComment;
        @BindView(R.id.iv_show)
        ImageView iv_show;
        @BindView(R.id.lv_comments)
        MyListView lv_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
