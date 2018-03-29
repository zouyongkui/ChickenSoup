package com.one.zyk.soup.base;

import android.support.v4.app.Fragment;

import com.one.zyk.soup.app.SoupApp;
import com.one.zyk.soup.http.RetrofitCallBack;
import com.one.zyk.soup.http.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import retrofit2.Retrofit;

/**
 * Created by zyk on 2017/9/30.
 */

public class BaseFragment extends Fragment implements RetrofitCallBack {


    @Override
    public void loading() {

    }

    @Override
    public Retrofit retrofit() {
        return ((SoupApp) getActivity().getApplication()).retrofit();
    }

    @Override
    public void handleObject(Object o) {
        if (isRemoving()) {
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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
