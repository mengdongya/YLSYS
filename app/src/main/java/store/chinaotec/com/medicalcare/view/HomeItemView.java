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
 * Created by hxk on 2017/9/20 0020 17:12
 * 首页"突发伤病处置"  "在线药店" "交叉结果解读"  "优生优育" "慢性病管理"  "医养都会"  "去医院"  "医联体医疗"布局自定义控件
 */

public class HomeItemView extends LinearLayout {
    private ImageView homeLogo;
    private TextView homeTitle;
    private TextView homeDescrip;
    private Drawable itemLogo;
    private String itemTitle;
    private String itemDesion;
    private View inflate;

    public HomeItemView(Context context) {
        super(context);
    }

    public HomeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttr(context, attrs);
        initAttrValue();
    }

    private void initAttrValue() {
        homeLogo.setImageDrawable(itemLogo);
        homeTitle.setText(itemTitle);
        homeDescrip.setText(itemDesion);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(inflate, layoutParams);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomePageView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            initBasicAttr(index, typedArray);
        }
        typedArray.recycle();
    }

    private void initBasicAttr(int index, TypedArray typedArray) {
        if (index == R.styleable.HomePageView_itemLogo) {
            itemLogo = typedArray.getDrawable(index);
        } else if (index == R.styleable.HomePageView_itemTitle) {
            itemTitle = typedArray.getString(index);
        } else if (index == R.styleable.HomePageView_itemDesion) {
            itemDesion = typedArray.getString(index);
        }
    }

    private void initView() {
        inflate = View.inflate(getContext(), R.layout.fragment_home_item, null);
        homeLogo = (ImageView) inflate.findViewById(R.id.home_fragment_logo);
        homeTitle = (TextView) inflate.findViewById(R.id.home_fragment_title);
        homeDescrip = (TextView) inflate.findViewById(R.id.home_fragment_descrion);
    }

    public HomeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
