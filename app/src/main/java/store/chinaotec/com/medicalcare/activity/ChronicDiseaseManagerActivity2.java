package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.dialog.LoadingView;
import store.chinaotec.com.medicalcare.fragment.slowdise.ChronicDiseaseManagerFragment2;
import store.chinaotec.com.medicalcare.javabean.ChronicDiseaseBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2018/2/1 0001.
 * 慢性病管理
 */
public class ChronicDiseaseManagerActivity2 extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.slow_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.slow_viewpager)
    ViewPager viewPager;
    @Bind(R.id.add_slow_dise)
    ImageView addSlowDisease;
    @Bind(R.id.tv_activity_slow_disease_dian)
    TextView tvActivitySlowDiseaseDian;
    @Bind(R.id.delete_dise)
    FrameLayout deleteDisease;
    @Bind(R.id.ll_disease_null)
    LinearLayout ll_disease_null;
    @Bind(R.id.ll_disease)
    LinearLayout ll_disease;
    @Bind(R.id.slow_disease_titile)
    TextView slowDiseaseTitile;
    @Bind(R.id.slow_top_title)
    RelativeLayout slowTopTitle;
    @Bind(R.id.ll_load_again)
    LinearLayout llLoadAgain;
    @Bind(R.id.ll_login_lose)
    LinearLayout llLoginLose;
    @Bind(R.id.ll_disease_default)
    LinearLayout llDiseaseDefault;
    private String sid;
    private List<Fragment> fragments;
    private List<String> titles;
    private DiseaseViewPagerAdapter myViewPagerAdapter;

    private List<ChronicDiseaseBean.DataBean.ChronicPatientDto> list;
    private LoadingView loadingDialog;
    private String refMemberId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slow_disease);
        ButterKnife.bind(this);
        refMemberId = getIntent().getStringExtra("refMemberId");
        initView();
        initListener();
        getDiseaseData();
    }

    private void initView() {
        addSlowDisease.setVisibility(View.GONE);
        tvActivitySlowDiseaseDian.setVisibility(View.GONE);
        deleteDisease.setVisibility(View.GONE);
        loadingDialog = new LoadingView(this, R.style.ProgressDialog);
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        myViewPagerAdapter = new DiseaseViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initListener() {
        llLoadAgain.setOnClickListener(this);
        llLoginLose.setOnClickListener(this);
    }

    private void getDiseaseData() {
        loadingDialog.show();
        String memberId;
        if (!StringUtils.isEmpty(refMemberId)){
            memberId = refMemberId;
        }else {
            memberId = "";
        }
        OkHttpUtils.post().url(MyUrl.dise_patient_info).addParams("sid", sid).addParams("memberId",memberId)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ChronicDiseaseBean diseaseBean = JSONObject.parseObject(response, ChronicDiseaseBean.class);

                if ("0".equals(diseaseBean.getResponseCode())) {
                    list = diseaseBean.getData().getChronicPatientDtos();
                    if (null != list && list.size() > 0) {
                        ll_disease.setVisibility(View.VISIBLE);
                        slowTopTitle.setVisibility(View.VISIBLE);
                        ll_disease_null.setVisibility(View.GONE);
                        llDiseaseDefault.setVisibility(View.GONE);
                        fragments.clear();
                        titles.clear();
                        for (int i = 0; i < list.size(); i++) {
                            fragments.add(ChronicDiseaseManagerFragment2.newInstance(list.get(i)));
                            titles.add(list.get(i).getChronicDto().getName());
                        }
                        myViewPagerAdapter.notifyDataSetChanged();

                    } else {
                        ll_disease.setVisibility(View.GONE);
                        ll_disease_null.setVisibility(View.VISIBLE);
                        slowTopTitle.setVisibility(View.VISIBLE);
                        llDiseaseDefault.setVisibility(View.GONE);
                    }
                } else {
                    ll_disease.setVisibility(View.GONE);
                    ll_disease_null.setVisibility(View.GONE);
                    slowTopTitle.setVisibility(View.GONE);
                    llDiseaseDefault.setVisibility(View.VISIBLE);
                    llLoginLose.setVisibility(View.VISIBLE);
                    llLoadAgain.setVisibility(View.GONE);
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ll_disease.setVisibility(View.GONE);
                ll_disease_null.setVisibility(View.GONE);
                slowTopTitle.setVisibility(View.GONE);
                llDiseaseDefault.setVisibility(View.VISIBLE);
                llLoadAgain.setVisibility(View.VISIBLE);
                llLoginLose.setVisibility(View.GONE);
                loadingDialog.dismiss();
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_load_again:
                getDiseaseData();
                break;
            case R.id.ll_login_lose:
                startActivityForResult(new Intent(this,LoginActivity.class),ResourseSum.LOGIN_RESPONSE);
                break;
        }
    }

    class DiseaseViewPagerAdapter extends FragmentPagerAdapter {

        public DiseaseViewPagerAdapter(FragmentManager fm) {
            super(fm);
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
            ChronicDiseaseManagerFragment2 fragment = (ChronicDiseaseManagerFragment2) super.instantiateItem(container, position);
            fragment.setDiseaseData(list.get(position));
            return fragment;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
            getDiseaseData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
