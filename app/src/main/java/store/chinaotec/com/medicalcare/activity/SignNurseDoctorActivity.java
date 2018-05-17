package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.fragment.sign.SignDoctorFragment;
import store.chinaotec.com.medicalcare.fragment.sign.SignNurseFragment;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 医患签约
 */
public class SignNurseDoctorActivity extends BaseActivity {
    @Bind(R.id.sign_viewpager)
    ViewPager signViewpager;
    @Bind(R.id.sign_kind_tablayout)
    TabLayout signKindTablayout;
    private List<String> titleList;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_nurse_doctor);
        ButterKnife.bind(this);
        iniListener();
    }

    private void iniListener() {
        String cityArr = SpUtill.getString(this, ResourseSum.Medica_SP, "saveLocationAddress");
        titleList = new ArrayList();
        titleList.add("签约医生");
        titleList.add("签约护士");
        for (int i = 0; i < titleList.size(); i++) {
            signKindTablayout.addTab(signKindTablayout.newTab().setText(titleList.get(i)));
        }
        fragmentList = new ArrayList();
        fragmentList.add(SignDoctorFragment.instance(cityArr));
        fragmentList.add(SignNurseFragment.instance(cityArr));
        signViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        signKindTablayout.setupWithViewPager(signViewpager);
        BaseUtill.setTabDownLine(signKindTablayout, 40, 40);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
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
