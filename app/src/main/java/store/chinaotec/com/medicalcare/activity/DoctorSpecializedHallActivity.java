package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.fragment.medicalcare.DoctorSpecializedHallFragment;
import store.chinaotec.com.medicalcare.javabean.SpecialHallBean;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;

/**
 * Created by wjc on 2017/11/13 0013.
 * 名医堂
 */
public class DoctorSpecializedHallActivity extends AppCompatActivity implements View.OnClickListener,DoctorSpecializedHallFragment.BackHandlerInterface {
    @Bind(R.id.tab_special_hall)
    TabLayout tabLayout;
    @Bind(R.id.tab_special_view_pager)
    ViewPager viewPager;
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_doctor_setup)
    TextView tvDoctorSetup;
    private List<Fragment> fragment;
    private List<String> title;
    private MyViewPagerAdapter myViewPagerAdapter;
    private SpecialHallBean specialHallBean;
    private int mPosition = 0;
    private Handler handler;
    private DoctorSpecializedHallFragment selectedFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_special_hall);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(this);
        tvDoctorSetup.setOnClickListener(this);
        handler = new Handler();
        fragment = new ArrayList<>();
        title = new ArrayList<>();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(10);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {

        OkHttpUtils.post().url(MyUrl.FIND_TOPIC_CLASS).addParams("baseClassId", "4").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                specialHallBean = JSON.parseObject(response, SpecialHallBean.class);
                if ("0".equals(specialHallBean.getResponseCode())) {
                    for (int i = 0; i < specialHallBean.getData().size(); i++) {
                        fragment.add(DoctorSpecializedHallFragment.newInstance(specialHallBean.getData().get(i).getId()));
                        title.add(specialHallBean.getData().get(i).getName());
                    }

                    myViewPagerAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.MyToast(DoctorSpecializedHallActivity.this, specialHallBean.getMsg());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_doctor_setup:
                Intent intent = new Intent(this,DoctorPatientForumAddTopicActivity.class);
                intent.putExtra("classId",specialHallBean.getData().get(mPosition).getId());
                startActivityForResult(intent, SourceConstant.GO_TO_SHARE);
                break;
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if(selectedFragment != null && !selectedFragment.onBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    public void setSelectedFragment(DoctorSpecializedHallFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }
}
