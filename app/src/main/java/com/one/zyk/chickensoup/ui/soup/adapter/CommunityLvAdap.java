package com.one.zyk.chickensoup.ui.soup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.one.zyk.chickensoup.R;
import com.one.zyk.chickensoup.bean.CommunityPreviewBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/10/6.
 */

public class CommunityLvAdap extends BaseAdapter {

    List<CommunityPreviewBean> beanList;
    Context context;


    public CommunityLvAdap(List<CommunityPreviewBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (beanList==null){
            return 0;
        }
        return beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_community_lv, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.community_lv_title.setText(beanList.get(i).getTitle());
        viewHolder.community_lv_preview.setText(beanList.get(i).getPreview());
        viewHolder.community_lv_browseTime.setText(beanList.get(i).getBrowseTimes());
        viewHolder.community_lv_commentTime.setText(beanList.get(i).getCommentTimes());
        return view;
    }




    class ViewHolder {
        @BindView(R.id.community_lv_title)
        TextView community_lv_title;
        @BindView(R.id.community_lv_preview)
        TextView community_lv_preview;
        @BindView(R.id.community_lv_browseTime)
        TextView community_lv_browseTime;
        @BindView(R.id.community_lv_commentTime)
        TextView community_lv_commentTime;

        public ViewHolder(View view) {
            ButterKnife.bind(CommunityLvAdap.ViewHolder.this, view);
        }
    }
}
