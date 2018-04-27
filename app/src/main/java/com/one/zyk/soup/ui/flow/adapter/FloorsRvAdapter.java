package com.one.zyk.soup.ui.flow.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.FloorBean;
import com.one.zyk.soup.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/4/9 14:19
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class FloorsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private Context mContext;
    private FloorBean mFloorBean;
    private List<FloorBean.FloorsBean> mList;

    public void setFloorBean(FloorBean floorBean) {
        mFloorBean = floorBean;
        mList = floorBean == null ? null : floorBean.getFloors();
        notifyDataSetChanged();
    }

    public FloorsRvAdapter(Context context, FloorBean floorBean) {
        mContext = context;
        mFloorBean = floorBean;
        mList = floorBean == null ? null : floorBean.getFloors();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_floor_content, parent, false);
            return new HeadHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_floor_floor, parent, false);
        return new FloorHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            HeadHolder headHolder = (HeadHolder) holder;
            headHolder.tv_content.setText(mFloorBean.getCommunity().getContent());
            headHolder.tv_title.setText(mFloorBean.getCommunity().getTitle());
            headHolder.tv_time.setText(mFloorBean.getCommunity().getCreatetime());
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            FloorHolder floorHolder = (FloorHolder) holder;
            floorHolder.tv_comment.setText((position) + "# " + mList.get(position - 1).getFlcontent());
            floorHolder.tv_postTime.setText(TimeUtils.getFriendlyTimeSpanByNow(mList.get(position - 1).getCreatetime()));
        }
    }

    @Override
    public int getItemCount() {
        if (mFloorBean == null) {
            return 0;
        }
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_NORMAL;
        }
    }

    public class HeadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public HeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FloorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_comment)
        TextView tv_comment;
        @BindView(R.id.tv_postTime)
        TextView tv_postTime;

        public FloorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
