package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.fragment.regist.DoctorFragment;
import store.chinaotec.com.medicalcare.fragment.regist.UserFragment;
import store.chinaotec.com.medicalcare.utill.BaseUtill;

public class UserRegistActivity extends BaseActivity{
    @Bind(R.id.top_tablayout)
    TabLayout topTablayout;
    @Bind(R.id.down_viewpager)
    ViewPager downViewpager;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regist);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleList = new ArrayList();
        titleList.add("普通用户");
        titleList.add("医护人员");
        for (int i = 0; i < titleList.size(); i++) {
            topTablayout.addTab(topTablayout.newTab().setText(titleList.get(i)));
        }
        fragmentList = new ArrayList();
        fragmentList.add(UserFragment.instance());
        fragmentList.add(DoctorFragment.instance());

        downViewpager.setOffscreenPageLimit(1);
        downViewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        topTablayout.setupWithViewPager(downViewpager);
        BaseUtill.setTabDownLine(topTablayout, 40, 40);
    }
    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
