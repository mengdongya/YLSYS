package store.chinaotec.com.medicalcare.listener;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;

import java.util.List;

import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.fragment.HomeFragment;
import store.chinaotec.com.medicalcare.fragment.MineFragment;
import store.chinaotec.com.medicalcare.utill.ReshUtill;

/**
 * Created by hxk on 2017/9/26 0026 14:21
 * 首页viewpager左右滑动监听器
 */

public class HomeChangListener implements ViewPager.OnPageChangeListener {
    public List<RadioButton> radioButtons;
    public List<Fragment> fragments;
    public Context context;

    public HomeChangListener(List<RadioButton> radioButtons, List<Fragment> fragments, Context context) {
        this.fragments = fragments;
        this.context = context;
        this.radioButtons = radioButtons;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面滑动过程中
        RadioButton radioButton = radioButtons.get(position);
        radioButton.setChecked(true);
        //当前页面显示时,其他页面的标题不显示选中状态
        if (position == 0) {
            radioButtons.get(position + 1).setChecked(false);
        } else if (position == 3) {
            radioButtons.get(position - 1).setChecked(false);
        } else {
            radioButtons.get(position + 1).setChecked(false);
            radioButtons.get(position - 1).setChecked(false);
        }
        //判断当前显示的碎片是MineFragment的话进行刷新
//        ReshUtill.reshMineFragment(position, fragments, context);
        MyLog.d("onPageSelected..当前页面编码.." + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
