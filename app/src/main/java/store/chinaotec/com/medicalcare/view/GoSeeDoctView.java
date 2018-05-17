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
 * Created by hxk on 2017/9/22 0022 16:43
 * 首页 "去看病"布局自定义控件
 */

public class GoSeeDoctView extends LinearLayout {

    private View inflate;
    private TextView seeDoctTitle;
    private ImageView seeDoctLogo;
    private TextView seeDoctDesp;
    private Drawable doctLogo;
    private String doctorDescrip;
    private String doctorTitle;

    public GoSeeDoctView(Context context) {
        super(context);
    }

    public GoSeeDoctView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAtters(context, attrs);
        initAtterValue();
    }

    /**
     * 初始化自定义属性值
     */
    private void initAtterValue() {
        seeDoctLogo.setImageDrawable(doctLogo);
        seeDoctTitle.setText(doctorTitle);
        seeDoctDesp.setText(doctorDescrip);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(inflate, lp);
    }

    /**
     * @param context
     * @param attrs   初始化自定义属性
     */
    private void initAtters(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeeDoctView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            initAtter(typedArray, index);
        }
        typedArray.recycle();
    }

    /**
     * @param typedArray
     * @param index      初始化自定义属性值
     */
    private void initAtter(TypedArray typedArray, int index) {
        if (index == R.styleable.SeeDoctView_SeeDoctLogo) {
            doctLogo = typedArray.getDrawable(index);
        } else if (index == R.styleable.SeeDoctView_SeeDoctTitle) {
            doctorTitle = typedArray.getString(index);
        } else if (index == R.styleable.SeeDoctView_SeeDoctDescrip) {
            doctorDescrip = typedArray.getString(index);
        }
    }

    /**
     * 初始化view控件
     */
    private void initView(Context context) {
        inflate = View.inflate(context, R.layout.home_go_doctor, null);
        seeDoctLogo = (ImageView) inflate.findViewById(R.id.see_doctor_logo);
        seeDoctTitle = (TextView) inflate.findViewById(R.id.see_doctor_title);
        seeDoctDesp = (TextView) inflate.findViewById(R.id.see_doctor_desp);
    }
}
