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
 * Created by hxk on 2017/9/21 0021 15:45
 * 医联体页面 功能条目自定义view "远程诊疗" "医联体视频通信" "医联体信息共享" "医护签约"
 */

public class MedUnionView extends LinearLayout {

    private ImageView medicItemLogo;
    private TextView medicItemTitle;
    private TextView medicItemDescrip;
    private Drawable unionLogo;
    private String unionTitle;
    private String unionDescrip;
    private View inflate;

    public MedUnionView(Context context) {
        super(context);
    }

    public MedUnionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAtters(context, attrs);
        initAtterValue();
    }

    /**
     * 自定义属性赋值
     */
    private void initAtterValue() {
        medicItemLogo.setImageDrawable(unionLogo);
        medicItemTitle.setText(unionTitle);
        medicItemDescrip.setText(unionDescrip);
        //设置当前控件的宽 高
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(inflate, params);
    }

    /**
     * @param context
     * @param attrs
     * 初始化自定义属性
     */
    private void initAtters(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnionItemView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            initAtter(index, typedArray);
        }
        typedArray.recycle();
    }

    private void initAtter(int index, TypedArray typedArray) {
        if (index == R.styleable.UnionItemView_unionItemLogo) {
            unionLogo = typedArray.getDrawable(index);
        } else if (index == R.styleable.UnionItemView_unionItemTitle) {
            unionTitle = typedArray.getString(index);
        } else if (index == R.styleable.UnionItemView_unionItemDescrip) {
            unionDescrip = typedArray.getString(index);
        }
    }

    /**
     * @param context
     * 初始化view
     */
    private void initView(Context context) {
        inflate = View.inflate(context, R.layout.medic_union_item, null);
        medicItemLogo = (ImageView) inflate.findViewById(R.id.medic_item_logo);
        medicItemTitle = (TextView) inflate.findViewById(R.id.medic_item_title);
        medicItemDescrip = (TextView) inflate.findViewById(R.id.medic_item_despion);
    }
}
