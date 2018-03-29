package com.one.zyk.soup.ui.manage;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.CommentBean;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentManagementActivity extends BaseActivity {
    private List<CommentBean.DataBean> list;
    private CommentAdapter adapter;
    @BindView(R.id.lv_comments)
    public ListView lv_comments;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_management);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        adapter = new CommentAdapter();
        list = new ArrayList<>();
        ServiceRequest.getCommentList(this, getIntent().getStringExtra("soupId"));
        list = new ArrayList<>();
        lv_comments.setAdapter(adapter);
        lv_comments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteComment(list.get(i).getId());
            }
        });
    }

    @Override
    protected <T> void onRetrofitCallBack(T t) {
        Toast.makeText(this, (String) t, Toast.LENGTH_SHORT).show();
    }

    private void deleteComment(final String id) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(CommentManagementActivity.this);
        normalDialog.setTitle("删除确认");
        normalDialog.setMessage("确定删除该项吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ServiceRequest.delCommentList(CommentManagementActivity.this, id);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Subscribe
    public void getCommentList(CommentBean bean) {
        list = bean.getData();
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void delComment(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public class CommentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
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
                view = LayoutInflater.from(CommentManagementActivity.this).inflate(R.layout.adapter_manage_lv, null);
                viewHolder = new CommentAdapter.ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_comment.setText(list.get(i).getContent());
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_comment)
            TextView tv_comment;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
