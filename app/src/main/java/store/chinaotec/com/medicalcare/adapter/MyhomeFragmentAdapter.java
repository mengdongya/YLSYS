package store.chinaotec.com.medicalcare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hxk on 2017/10/27 0027 15:23
 * 首页fragment适配器
 */

public class MyhomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MyhomeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
