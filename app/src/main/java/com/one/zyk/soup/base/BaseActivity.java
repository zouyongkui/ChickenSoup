package com.one.zyk.soup.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;


import com.one.zyk.soup.app.Constant;
import com.one.zyk.soup.http.RetrofitCallBack;
import com.one.zyk.soup.app.SoupApp;
import com.one.zyk.soup.http.Subscribe;
import com.one.zyk.soup.utils.LogUtils;
import com.one.zyk.soup.utils.SPUtils;
import com.one.zyk.soup.utils.ScreenUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import retrofit2.Retrofit;


public abstract class BaseActivity extends AppCompatActivity implements RetrofitCallBack {
    protected Toolbar toolbar;
    protected SPUtils mUserSp;
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserSp = SPUtils.getInstance(Constant.db_user);
        mScreenHeight = ScreenUtils.getScreenHeight();
        mScreenWidth = ScreenUtils.getScreenWidth();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setTitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    protected void initView() {
    }


    public String getABTitle() {
        return "";
    }

    /***
     * 当前app
     */
    public SoupApp getSoupApp() {
        return (SoupApp) getApplication();
    }


    @Override
    public void loading() {
    }

    @Override
    public Retrofit retrofit() {
        return ((SoupApp) getApplication()).retrofit();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void handleObject(Object object) {
        if (isFinishing()) {
            return;
        }
        onRetrofitCallBack(object);
        for (Method method : this.getClass().getDeclaredMethods()) {
            //保证方法添加了注解
            if (!method.isAnnotationPresent(Subscribe.class)) {
                continue;
            }
            //保证方法为非泛型
            if (method.isBridge()) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            //保证方法只有一个参数
            if (parameterTypes.length != 1) {
                continue;
            }
            //保证参数是共有的x
            if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                continue;
            }
            //保证该参数的方法只使用一个
            Class c = object.getClass();
            if (c == parameterTypes[0]) {
                try {
                    method.invoke(this, object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected <T> void onRetrofitCallBack(T t) {
    }

}
