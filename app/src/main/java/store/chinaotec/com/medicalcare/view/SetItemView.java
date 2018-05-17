package store.chinaotec.com.medicalcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by hxk on 2017/9/21 0021 14:06
 * 我的页面,标题自定义控件"我的收藏" "我的订单" "我的设备" "收货地址" "设置"
 */

public class SetItemView extends LinearLayout {

    private Drawable setItemLogo;
    private String setItemTitle;
    private ImageView mineLogo;
    private TextView mineTitle;
    private View inflate;

    public SetItemView(Context context) {
        super(context);
    }

    public SetItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAtters(context, attrs);
        initAtterValue();
    }

    /**
     * 自定义属性赋值
     */
    private void initAtterValue() {
        mineLogo.setImageDrawable(setItemLogo);
        mineTitle.setText(setItemTitle);
        //设置宽高
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(inflate, layoutParams);
    }

    /**
     * @param context 初始化view
     */
    private void initView(Context context) {
        inflate = View.inflate(context, R.layout.fragment_set_item, null);
        mineLogo = (ImageView) inflate.findViewById(R.id.mine_logo);
        mineTitle = (TextView) inflate.findViewById(R.id.mine_title);
    }

    /**
     * @param context
     * @param attrs   初始化属性
     */
    private void initAtters(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SetItemView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            initAtter(index, typedArray);
        }
        typedArray.recycle();
    }

    private void initAtter(int index, TypedArray typedArray) {
        if (index == R.styleable.SetItemView_setItemLogo) {
            setItemLogo = typedArray.getDrawable(index);
        } else if (index == R.styleable.SetItemView_setItemTitle) {
            setItemTitle = typedArray.getString(index);
        }
    }
}
