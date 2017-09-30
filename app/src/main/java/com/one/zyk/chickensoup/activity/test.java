package com.one.zyk.chickensoup.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.one.zyk.chickensoup.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {
    TextView tv_soup;
    ProgressBar pgb;
    TextView iv_hole;
    SwipeRefreshLayout srl;
    //需要监听几次点击事件数组的长度就为几
    //如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
    long[] mHints = new long[4];//初始全部为0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        tv_soup = (TextView) findViewById(R.id.tv_soup);
        pgb = (ProgressBar) findViewById(R.id.pgb);
        postStringRequest();
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postStringRequest();
            }
        });

        iv_hole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将mHints数组内的所有元素左移一个位置
                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                //获得当前系统已经启动的时间
                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                if (SystemClock.uptimeMillis() - mHints[0] <= 500) {
                    Toast.makeText(test.this, "小子还挺能...666", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(test.this, WebActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void postStringRequest() {
        String url = "http://47.94.130.167:8081/ztcaipiaomanagement/login";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("str__POST", s);
                try {
                    JSONObject object = new JSONObject(s);
                    tv_soup.setText(object.getString("soup"));
                    if (!tv_soup.getText().toString().equals("")) {
                        pgb.setVisibility(View.INVISIBLE);
                        srl.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("username", "admin");
                map.put("password", "admin");
                return map;
            }
        };
        queue.add(request);
    }
}
