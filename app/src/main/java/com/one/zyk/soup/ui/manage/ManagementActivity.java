package com.one.zyk.soup.ui.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.one.zyk.soup.R;
import com.one.zyk.soup.base.BaseActivity;
import com.one.zyk.soup.bean.SoupManageBean;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.http.request.ServiceRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagementActivity extends BaseActivity {

    private List<SoupManageBean.SoupListBean> list;
    private ManagementAdapter adapter;
    @BindView(R.id.lv_soups)
    public ListView lv_soups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        adapter = new ManagementAdapter();
        list = new ArrayList<>();
        lv_soups.setAdapter(adapter);
        lv_soups.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        lv_soups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(ManagementActivity.this, CommentManagementActivity.class);
                in.putExtra("soupId", list.get(i).getId());
                startActivity(in);
            }
        });
        ServiceRequest.getSoupList(ManagementActivity.this);
    }

    @Override
    protected <T> void onRetrofitCallBack(T t) {
        list = ((SoupManageBean) t).getSoupList();
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void getSoupList(SoupManageBean bean) {
        list = bean.getSoupList();
        adapter.notifyDataSetChanged();
    }

    public class ManagementAdapter extends BaseAdapter {

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
                view = LayoutInflater.from(ManagementActivity.this).inflate(R.layout.adapter_comments_lv, null);
                viewHolder = new ViewHolder(view);
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
