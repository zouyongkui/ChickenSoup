package com.one.zyk.soup.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 本类是 一个自定义的ListView类MyListView 类描述：本类功能是重写ListView中的onMeasure方法，
 * 解决ScrollVeiw中嵌套ListView不能滑动问题
 *
 * @author WZL
 * @version Ver 1.0 2014-5-13 新建
 * @since ElevCheck Ver1.0
 */
public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 返回给父控件需要的空间 根据提供的大小值和模式创建一个测量值(格式)
         */
        int expandSpec = MeasureSpec.makeMeasureSpec(1000,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
