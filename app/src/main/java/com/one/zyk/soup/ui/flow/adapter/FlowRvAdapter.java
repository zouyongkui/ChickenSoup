package com.one.zyk.soup.ui.flow.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.FlowCircleBean;
import com.one.zyk.soup.callback.RvOnItemClickListener;
import com.one.zyk.soup.http.Urls;
import com.one.zyk.soup.ui.flow.fragment.FlowCircleFragment;
import com.one.zyk.soup.utils.DateUtil;
import com.one.zyk.soup.utils.SizeUtils;
import com.one.zyk.soup.weight.MyListView;


import java.util.Arrays;
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
    private List<FlowCircleBean.DataBean> mList;
    private Context mContext;
    private RvOnItemClickListener mOnItemClickListener;
    private final SparseBooleanArray mCollapsedStatus;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_BOTTOM = 1;
    private boolean mHideBottom = false;
    private ViewHolder mViewHolder;
    private RequestOptions faceOption, picOption;


    public FlowRvAdapter(List<FlowCircleBean.DataBean> list, Context context, RvOnItemClickListener listener) {
        mList = list;
        mContext = context;
        mOnItemClickListener = listener;
        mCollapsedStatus = new SparseBooleanArray();
        faceOption = new RequestOptions()
                .placeholder(R.mipmap.logo)
                .override(SizeUtils.dp2px(45), SizeUtils.dp2px(45));
        picOption = new RequestOptions()
                .fitCenter().dontAnimate();
    }

    public void setList(List<FlowCircleBean.DataBean> list) {
        mList = list;
        notifyDataSetChanged();
        if (mViewHolder != null) {
            //每次更新列表时 同时更新其评论信息
            mViewHolder.lv_Adapter.notifyDataSetInvalidated();
//            mViewHolder.lv_Adapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOTTOM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_circle_bottom, parent, false);
            return new ViewHolderForBottom(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_community_lv, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            mViewHolder = (ViewHolder) holder;
            FlowCircleBean.DataBean data = mList.get(position);
            ((ViewHolder) holder).tv_name.setText(data.getUsrName());
            ((ViewHolder) holder).tv_content.setText(data.getContent(), mCollapsedStatus, position);
            ((ViewHolder) holder).tv_time.setText(DateUtil.formatDateASYYYYMMDDHHMMSS(data.getCreateTime()));
            Glide.with(mContext)
                    .load(Urls.PIC_URL + data.getUsrFace())
                    .apply(faceOption)
                    .into(((ViewHolder) holder).iv_head);
            Glide.with(mContext)
                    .load(Urls.PIC_URL + data.getPicUrl())
                    .apply(picOption)
                    .into(((ViewHolder) holder).iv_show);

            ((ViewHolder) holder).iv_postComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onRvItemClick(holder.getAdapterPosition(), v);
                }
            });
            ((ViewHolder) holder).iv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onRvItemClick(holder.getAdapterPosition(), v);
                }
            });
            if (data.getComment() != null && data.getComment().size() > 0) {
                ((ViewHolder) holder).lv_comment.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).lv_Adapter.setList(data.getComment());
                ((ViewHolder) holder).lv_comment.setAdapter(((ViewHolder) holder).lv_Adapter);
            } else {
                ((ViewHolder) holder).lv_comment.setVisibility(View.GONE);
            }
        } else {
            if (mHideBottom) {
                ((ViewHolderForBottom) holder).pgb.setVisibility(View.GONE);
                ((ViewHolderForBottom) holder).tv_tip.setText("---- 这里是下线啦 ^_^ ----");
            } else {
                ((ViewHolderForBottom) holder).pgb.setVisibility(View.VISIBLE);
                ((ViewHolderForBottom) holder).tv_tip.setText("  正在加载 ...");
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null && position == mList.size()) {
            return TYPE_BOTTOM;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            if (mList.size() >= FlowCircleFragment.sPageSize) {
                return mList.size() + 1;
            } else {
                return mList.size();
            }
        }
        return 0;
    }

    public void hideBottom(boolean b) {
        if (b != mHideBottom) {
            mHideBottom = b;
            notifyDataSetChanged();
        }
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
        @BindView(R.id.iv_head)
        ImageView iv_head;
        CommentAdapter lv_Adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            lv_Adapter = new CommentAdapter(mContext, null);
        }
    }

    public class ViewHolderForBottom extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_bottom)
        LinearLayout ll_bottom;
        @BindView(R.id.pgb)
        ProgressBar pgb;
        @BindView(R.id.tv_tip)
        TextView tv_tip;

        ViewHolderForBottom(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
