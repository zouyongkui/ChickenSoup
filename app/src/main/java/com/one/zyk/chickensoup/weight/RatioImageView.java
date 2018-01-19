package com.one.zyk.chickensoup.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.one.zyk.chickensoup.R;


/**
 * Created by ztsx_hd
 * 项目：WDF
 * 日期：2017/6/15.
 * 时间：17:38
 * 作者: BruceChang
 * 功能：宽高比设置的imageview
 * 链接：
 */

public class RatioImageView extends AppCompatImageView {

    //宽高比===宽/高，默认为1，正方形，，如果==2，代表宽/高==2，，代表长方形，，
    private float radtio = 1;

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadioImageView);
        radtio = ta.getFloat(R.styleable.RadioImageView_radio, radtio);
        ta.recycle();
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width / radtio);
        setMeasuredDimension(width, height);
    }
}
