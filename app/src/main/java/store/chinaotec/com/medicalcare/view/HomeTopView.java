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
 * Created by hxk on 2017/9/27 0027 10:27
 * 首页"去医院"  "医联体医疗"布局自定义控件
 */

public class HomeTopView extends LinearLayout {
    private View inflate;
    private ImageView topLogo;
    private TextView topTitle;
    private TextView topDescrip;
    private Drawable itemLogo;
    private String itemTitle;
    private String itemDesion;

    public HomeTopView(Context context) {
        super(context);
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttr(context, attrs);
        initAttrValue();
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttrValue() {
        topLogo.setImageDrawable(itemLogo);
        topTitle.setText(itemTitle);
        topDescrip.setText(itemDesion);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(inflate, layoutParams);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeTopView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            initBasicAttr(index, typedArray);
        }
        typedArray.recycle();
    }

    private void initBasicAttr(int index, TypedArray typedArray) {
        if (index == R.styleable.HomeTopView_topLogo) {
            itemLogo = typedArray.getDrawable(index);
        } else if (index == R.styleable.HomeTopView_topTitle) {
            itemTitle = typedArray.getString(index);
        } else if (index == R.styleable.HomeTopView_topDesion) {
            itemDesion = typedArray.getString(index);
        }
    }

    private void initView() {
        inflate = View.inflate(getContext(), R.layout.fragment_top_item, null);
        topLogo = (ImageView) inflate.findViewById(R.id.home_fragment_logo);
        topTitle = (TextView) inflate.findViewById(R.id.home_fragment_title);
        topDescrip = (TextView) inflate.findViewById(R.id.home_fragment_descrion);
    }
}
