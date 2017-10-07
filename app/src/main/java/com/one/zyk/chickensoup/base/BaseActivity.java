package com.one.zyk.chickensoup.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.one.zyk.chickensoup.app.Constant;
import com.one.zyk.chickensoup.http.Handle;
import com.one.zyk.chickensoup.app.SoupApp;
import com.one.zyk.chickensoup.http.Subscribe;
import com.one.zyk.chickensoup.utils.SPUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import retrofit2.Retrofit;

/**
 * Created by duan .
 */

public abstract class BaseActivity extends AppCompatActivity implements Handle {
    protected Toolbar toolbar;
    protected String TAG;
    protected SPUtils mUserSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印日志
        TAG = this.getClass().getName();
        mUserSp = SPUtils.getInstance(Constant.db_user);
        //设置状态栏 颜色和透明度，，兼容4.4手机
//        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorStatusBar));
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

    protected abstract void initView();


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
    public void handleObject(Object o) {
        if (isFinishing()) {
            return;
        }
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
            Class c = o.getClass();
            if (c == parameterTypes[0]) {
                try {
                    method.invoke(this, o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}