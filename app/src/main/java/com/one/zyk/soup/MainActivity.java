package com.one.zyk.soup;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.one.zyk.soup.ui.blo.fragment.BloFragment;
import com.one.zyk.soup.ui.flow.fragment.FlowCircleFragment;
import com.one.zyk.soup.ui.me.SelfFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_rg)
    RadioGroup rg_main;
    @BindView(R.id.fb_post)
    FloatingActionButton fb_post;
    private Fragment mNowFragment;//当前展示的fragment
    BloFragment bloFragment;
    FlowCircleFragment mFlowCircle;
    SelfFragment selfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        bloFragment = new BloFragment();
        mFlowCircle = new FlowCircleFragment();
        selfFragment = new SelfFragment();
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_blo:
                        changeFragment(bloFragment);
                        if (fb_post.getVisibility() != View.VISIBLE) {
                            fb_post.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.rb_flow:
                        changeFragment(mFlowCircle);
                        if (fb_post.getVisibility() != View.VISIBLE) {
                            fb_post.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.rb_self:
                        if (fb_post.getVisibility() == View.VISIBLE) {
                            fb_post.setVisibility(View.GONE);
                        }
                        changeFragment(selfFragment);
                        break;
                }
            }
        });
        rg_main.check(R.id.rb_blo);
    }


    private void changeFragment(Fragment fragment) {
        if (fragment == mNowFragment) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mNowFragment != null && mNowFragment.isAdded()) {
            fragmentTransaction.hide(mNowFragment);
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fl_mainBody, fragment, fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();
        mNowFragment = fragment;
    }

    @OnClick(R.id.fb_post)
    void transmit() {
        if (bloFragment != null && bloFragment.isVisible()) {
            bloFragment.postComment();
        } else if (mFlowCircle != null && mFlowCircle.isVisible()) {
            mFlowCircle.postFloor();
        }
    }
}
