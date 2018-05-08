package com.one.zyk.soup.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.one.zyk.soup.R;
import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.base.BaseFragment;
import com.one.zyk.soup.ui.manage.ReleaseSoupActivity;
import com.one.zyk.soup.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author ：Yongkui.Zou
 * Date : 2018/2/8 15:59
 * Company ：北京虹宇科技有限公司
 * Function
 */
public class SelfFragment extends BaseFragment {

    @BindView(R.id.btn_release)
    Button btn_release;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ("+8617600209962".equals(SPUtils.getInstance(Constant.db_user).getString("phone"))) {
            btn_release.setVisibility(View.VISIBLE);
        } else {
            btn_release.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_release)
    public void goRelease() {
        startActivity(new Intent(getActivity(), ReleaseSoupActivity.class));
    }
}
