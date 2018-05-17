package store.chinaotec.com.medicalcare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxk on 2017/9/29 0029 15:53
 * 慢性病管理页面碎片适配器
 */

public class SlodiseAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentData = new ArrayList();
    private List<String> diseData = new ArrayList();

    public SlodiseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentData.get(position);
    }

    public void setData(List<Fragment> fragmentList, List<String> diseList) {
        if (diseData.size() != 0 && fragmentData.size() != 0) {
            fragmentData.clear();
            diseData.clear();
            fragmentData.addAll(fragmentList);
            diseData.addAll(diseList);
        } else {
            fragmentData.addAll(fragmentList);
            diseData.addAll(diseList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragmentData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return diseData.get(position);
    }
}
