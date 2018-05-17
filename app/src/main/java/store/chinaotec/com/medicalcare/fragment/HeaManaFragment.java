package store.chinaotec.com.medicalcare.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.AddHealInfoActivity;
import store.chinaotec.com.medicalcare.activity.HealthDeletePersonActivity;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.fragment.health.HealthControlFragmentV2;
import store.chinaotec.com.medicalcare.javabean.HealthControlBean;
import store.chinaotec.com.medicalcare.utill.EventTag;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaManaFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.btn_health_add)
    Button mainHealthAdd;
    @Bind(R.id.tablayout_health_control)
    TabLayout tabLayout;
    @Bind(R.id.delete_person)
    FrameLayout deletePerson;
    @Bind(R.id.viewpager_health_control)
    ViewPager viewPager;
    @Bind(R.id.ll_health)
    LinearLayout llHealth;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.ll_health_none)
    LinearLayout llHealthNone;
    @Bind(R.id.ll_no_data)
    LinearLayout llNoData;
    @Bind(R.id.rl_health_title)
    RelativeLayout rlHealthTitle;
    @Bind(R.id.ll_load_again)
    LinearLayout llLoadAgain;
    @Bind(R.id.ll_login_lose)
    LinearLayout llLoginLose;
    @Bind(R.id.ll_health_default)
    LinearLayout llHealthDefault;
    private List<Fragment> fragments;
    private List<String> titles;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String sid;
    private List<HealthControlBean.DataBean.PatientDtosBean> data;
    private HealthControlBean.DataBean.MedicalADV medicalADV;

    private BroadcastReceiver refreshFragment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ResourseSum.REFRESH_HEALTH_FRAGMENT)) {
                initData();
            }
        }
    };

    public static HeaManaFragment instance() {
        Bundle bundle = new Bundle();
        HeaManaFragment heaManaFragment = new HeaManaFragment();
        heaManaFragment.setArguments(bundle);
        return heaManaFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hea_manage, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initListener();
        initView();
        return view;
    }


    private void initView() {
        initVisibility();

        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
//        viewPager.setOffscreenPageLimit(20);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initVisibility() {
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        if (!"".equals(sid)) {
            rlHealthTitle.setVisibility(View.VISIBLE);
            llHealth.setVisibility(View.VISIBLE);
            llHealthNone.setVisibility(View.GONE);
            llHealthDefault.setVisibility(View.GONE);
            initData();
        } else {
            llHealthNone.setVisibility(View.VISIBLE);
            llHealth.setVisibility(View.GONE);
            llNoData.setVisibility(View.GONE);
            llHealthDefault.setVisibility(View.GONE);
            rlHealthTitle.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ResourseSum.REFRESH_HEALTH_FRAGMENT);
        getActivity().registerReceiver(refreshFragment, intentFilter);

        mainHealthAdd.setOnClickListener(this);
        deletePerson.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        llLoadAgain.setOnClickListener(this);
        llLoginLose.setOnClickListener(this);
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.HEALTH_CONTROL).addParams("sid", sid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                rlHealthTitle.setVisibility(View.GONE);
                llNoData.setVisibility(View.GONE);
                llHealth.setVisibility(View.GONE);
                llHealthNone.setVisibility(View.GONE);
                llHealthDefault.setVisibility(View.VISIBLE);
                llLoginLose.setVisibility(View.GONE);
                llLoadAgain.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                HealthControlBean healthControlBean = JSON.parseObject(response, HealthControlBean.class);
                if ("0".equals(healthControlBean.getResponseCode())) {
                    data = healthControlBean.getData().getPatientDtos();
                    if (data.size() > 0) {

                        llHealthNone.setVisibility(View.GONE);
                        llHealth.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                        llHealthDefault.setVisibility(View.GONE);
                        rlHealthTitle.setVisibility(View.VISIBLE);
                        medicalADV = healthControlBean.getData().getMedicalADV().get(0);
                        fragments.clear();
                        titles.clear();
                        for (int i = 0; i < data.size(); i++) {
                            fragments.add(HealthControlFragmentV2.newInstance(data.get(i), medicalADV));
                            titles.add(data.get(i).getName());
                        }
                        myViewPagerAdapter.notifyDataSetChanged();
                    } else {
                        rlHealthTitle.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.VISIBLE);
                        llHealth.setVisibility(View.GONE);
                        llHealthDefault.setVisibility(View.GONE);
                        llHealthNone.setVisibility(View.GONE);
                    }

                } else {
                    rlHealthTitle.setVisibility(View.GONE);
                    llNoData.setVisibility(View.GONE);
                    llHealth.setVisibility(View.GONE);
                    llHealthNone.setVisibility(View.GONE);
                    llHealthDefault.setVisibility(View.VISIBLE);
                    llLoginLose.setVisibility(View.VISIBLE);
                    llLoadAgain.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), healthControlBean.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_health_add:
                startActivityForResult(new Intent(getActivity(), AddHealInfoActivity.class), ResourseSum.ADD_PATIENT);
                break;
            case R.id.delete_person:
                startActivityForResult(new Intent(getActivity(), HealthDeletePersonActivity.class), ResourseSum.ADD_HEALTH_PERSON);
                break;
            case R.id.ll_login_lose:
            case R.id.btn_login:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                break;
            case R.id.ll_load_again:
                initData();
                break;
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            HealthControlFragmentV2 fragment = (HealthControlFragmentV2) super.instantiateItem(container, position);
            fragment.setHealthData(data.get(position), medicalADV);
            return fragment;
        }
    }

    @Subscriber(tag = EventTag.LOGIN)
    private void loginEvent(int i) {
        initVisibility();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initVisibility();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        getActivity().unregisterReceiver(refreshFragment);
    }
}
