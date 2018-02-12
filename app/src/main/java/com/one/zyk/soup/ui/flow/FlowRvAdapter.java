package com.one.zyk.soup.ui.flow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.CommunityBean;
import com.one.zyk.soup.utils.DateUtil;
import com.one.zyk.soup.weight.ExpandableTextView;

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
    private List<CommunityBean.CommunityListBean> mList;
    private Context mContext;


    public FlowRvAdapter(List<CommunityBean.CommunityListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void setList(List<CommunityBean.CommunityListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_community_lv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).etv_preview.setText(mList.get(position).getContent());
        ((ViewHolder) holder).tv_title.setText(mList.get(position).getTitle());
        ((ViewHolder) holder).tv_time.setText(DateUtil.formatDateASYYYYMMDDHHMM(DateUtil.getDate(mList.get(position).getReplytime())));
        ((ViewHolder) holder).tv_comment.setText(mList.get(position).getFloorsCount());
        ((ViewHolder) holder).tv_visit.setText(mList.get(position).getVisitcount());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.etv_preview)
        TextView etv_preview;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_comment)
        TextView tv_comment;
        @BindView(R.id.tv_visit)
        TextView tv_visit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
