package store.chinaotec.com.medicalcare.shopmarket.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/***
 * 自定义WebView子类，继承WebView
 *
 * @author Administrator
 *         com.fushionbaby.android.fushionbaby.common.view.MyScrollGridView
 */
public class MyScrollGridView extends GridView {

    public MyScrollGridView(Context context) {
        super(context);
    }

    public MyScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}