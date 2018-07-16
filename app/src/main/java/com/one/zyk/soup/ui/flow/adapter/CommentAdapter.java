package com.one.zyk.soup.ui.flow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.bean.FlowCircleBean;
import com.one.zyk.soup.utils.ScreenUtils;
import com.one.zyk.soup.utils.SizeUtils;
import com.one.zyk.soup.weight.ColorfulTextView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<FlowCircleBean.DataBean.CommentBean> mList;

    public CommentAdapter(Context context, List<FlowCircleBean.DataBean.CommentBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public void setList(List<FlowCircleBean.DataBean.CommentBean> list) {
        mList = list;
        notifyDataSetChanged();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FlowCircleBean.DataBean.CommentBean commentBean = mList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_comment, null);
            holder = new ViewHolder();
            holder.tv_comment = convertView.findViewById(R.id.tv_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String usrName = commentBean.getUsrName();
        // TODO: 2018/7/9 是什么原因导致了不重新设置就会重复出现5次？
        holder.tv_comment.reset();
        holder.tv_comment.addPiece(new ColorfulTextView.Piece.Builder(usrName + ":")
                .textSize(SizeUtils.sp2px(14.0f))
                .textColor(Color.BLUE)
                .build()
        );

        holder.tv_comment.addPiece(new ColorfulTextView.Piece.Builder(commentBean.getContent())
                .textSize(SizeUtils.sp2px(14.0f))
                .textColor(Color.BLACK)
                .build());
        holder.tv_comment.display();
        return convertView;
    }

    public class ViewHolder {
        ColorfulTextView tv_comment;
    }
}
