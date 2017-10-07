package com.one.zyk.chickensoup.ui.soup.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.one.zyk.chickensoup.R;

@SuppressLint("ValidFragment")
public class CommunityFragment extends Fragment {
    private String mTitle;

    public static CommunityFragment getInstance(String title) {
        CommunityFragment sf = new CommunityFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText("正在施工...");


        TwinklingRefreshLayout community_refresh= (TwinklingRefreshLayout) v.findViewById(R.id.community_refresh);
        ListView community_comments= (ListView) v.findViewById(R.id.community_comments);

        return v;
    }
}