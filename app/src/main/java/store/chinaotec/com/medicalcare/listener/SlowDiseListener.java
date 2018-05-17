package store.chinaotec.com.medicalcare.listener;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;

import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.fragment.slowdise.SlowDiseFragment;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by hxk on 2017/10/12 0012 17:47
 * 慢性病模块首页ViewPager页面变化监听
 */

public class SlowDiseListener implements ViewPager.OnPageChangeListener {
    private Context context;
    private List<Fragment> fragmentList;

    public SlowDiseListener(Context context, List<Fragment> fragmentList) {
        this.context = context;
        this.fragmentList = fragmentList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MyLog.d("onPageSelected....当前页面编码..." + position);
        Fragment fragment = fragmentList.get(position);
        if (fragment instanceof SlowDiseFragment) {
            ((SlowDiseFragment) fragment).reshSlodiseData(context,position);
            SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition", position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
