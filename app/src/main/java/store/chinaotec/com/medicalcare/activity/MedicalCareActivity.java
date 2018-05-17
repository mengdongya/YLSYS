package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyMedicKindAdapter;
import store.chinaotec.com.medicalcare.fragment.medicalcare.FileDownloadFragment;
import store.chinaotec.com.medicalcare.fragment.medicalcare.ForumNationFragment;
import store.chinaotec.com.medicalcare.fragment.medicalcare.KnoleChargeFragment;
import store.chinaotec.com.medicalcare.fragment.medicalcare.LatestTreatFragment;
import store.chinaotec.com.medicalcare.fragment.medicalcare.UtitiesFragment;
import store.chinaotec.com.medicalcare.fragment.medicalcare.lineHospitalFragment;
import store.chinaotec.com.medicalcare.view.MyViewPager;

/**
 * 医养都会展示页面
 */
public class MedicalCareActivity extends BaseActivity {
    @Bind(R.id.meidical_care_title)
    TextView meidicalCareTitle;
    @Bind(R.id.meidical_care_logo)
    View meidicalCareLogo;
    @Bind(R.id.medical_care_viewpager)
    MyViewPager medicalCareViewpager;
    @Bind(R.id.medical_kind_recycleview)
    RecyclerView medicalKindRecycleview;

    private String[] medicalKindName = {"论坛导航", "资料下载", "医养知识充电", "最新诊疗成果", "实用工具", "网上医院"};
    private int[] medicalKindLogo = {R.mipmap.form_nation, R.mipmap.medic_file_download, R.mipmap.medical_knowle_charge, R.mipmap.med_latest_treat, R.mipmap.uity_tool, R.mipmap.medical_online_hospital};
    private List<Fragment> medicalFragments;
    private TextView tv_include_title_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_care);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("医养都会");
        ButterKnife.bind(this);
        defaShowPatieForum();
        initAllData();
        initListener();
    }

    private void initAllData() {
        medicalFragments = new ArrayList<>();
        medicalFragments.add(new ForumNationFragment());
        medicalFragments.add(FileDownloadFragment.instance());
        medicalFragments.add(KnoleChargeFragment.instance());
        medicalFragments.add(LatestTreatFragment.instance());
        medicalFragments.add(new UtitiesFragment());
        medicalFragments.add(lineHospitalFragment.instance());
    }

    private void initListener() {
        //医养都会子页面标题
        medicalKindRecycleview.setLayoutManager(new GridLayoutManager(this, 4));
        MyMedicKindAdapter myMedicKindAdapter = new MyMedicKindAdapter(getApplicationContext(), medicalKindName, medicalKindLogo);
        medicalKindRecycleview.setAdapter(myMedicKindAdapter);
        myMedicKindAdapter.setOnClickLisenerMedicalKind(new MyMedicKindAdapter.MedicalKindLisener() {
            @Override
            public void itemClick(int position) {
                switch (position) {
                    case 0:
                        meidicalCareTitle.setText(R.string.medical_patient_forum);
                        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContOne));
                        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContOne));
                        medicalCareViewpager.setCurrentItem(0);
                        break;
                    case 1:
                        meidicalCareTitle.setText(R.string.medical_file_download);
                        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContTwo));
                        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContTwo));
                        medicalCareViewpager.setCurrentItem(1);
                        break;
                    case 2:
                        meidicalCareTitle.setText(R.string.medical_knowle_charge);
                        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContThree));
                        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContThree));
                        medicalCareViewpager.setCurrentItem(2);
                        break;
                    case 3:
                        meidicalCareTitle.setText(R.string.medical_latest_treatment);
                        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContFour));
                        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContFour));
                        medicalCareViewpager.setCurrentItem(3);
                        break;
                    case 4:
                        meidicalCareTitle.setText(R.string.medical_play_video);
                        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContSix));
                        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContSix));
                        medicalCareViewpager.setCurrentItem(4);
                        break;
                    case 5:
                        meidicalCareTitle.setText(R.string.medical_online_hospital);
                        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContSeven));
                        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContSeven));
                        medicalCareViewpager.setCurrentItem(5);
                        break;
                }
            }
        });

        MyMedicalFragmentAdapter myMedicalFragmentAdapter = new MyMedicalFragmentAdapter(getSupportFragmentManager());
        medicalCareViewpager.setAdapter(myMedicalFragmentAdapter);
        medicalCareViewpager.setOffscreenPageLimit(1);
    }

    private void defaShowPatieForum() {
        //打开当前页面后默认显示"医患论坛"板块
        meidicalCareTitle.setText(R.string.medical_patient_forum);
        meidicalCareLogo.setBackgroundColor(getResources().getColor(R.color.meidicalCareContOne));
        meidicalCareTitle.setTextColor(getResources().getColor(R.color.meidicalCareContOne));
    }

    class MyMedicalFragmentAdapter extends FragmentStatePagerAdapter {
        public MyMedicalFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return medicalFragments.get(position);
        }

        @Override
        public int getCount() {
            return medicalFragments.size();
        }
    }
}
