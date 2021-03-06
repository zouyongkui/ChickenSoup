package com.one.zyk.soup.ui.blo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.CommentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyk on 2017/9/23.
 */

public class CommentListAdapter extends BaseAdapter {

    private List<CommentBean.DataBean> dataBeanList;
    private Context mContext;
    private int mSize = 0;

    public CommentListAdapter(List<CommentBean.DataBean> dataBeanList, Context context) {
        this.dataBeanList = dataBeanList;
        this.mContext = context;
    }

    public void setmCommentList(List<CommentBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
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
            Log.e("qqq", view.toString());
            ButterKnife.bind(this, view);
        }
    }
}
