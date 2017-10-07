package com.one.zyk.chickensoup.ui.soup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.one.zyk.chickensoup.R;

public class WebActivity extends AppCompatActivity {
    WebView wb_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wb_view = (WebView) findViewById(R.id.wv_view);
        wb_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        wb_view.loadUrl("http://47.94.130.167:8081/ztcaipiaomanagement/chickensoup/create");
    }
}