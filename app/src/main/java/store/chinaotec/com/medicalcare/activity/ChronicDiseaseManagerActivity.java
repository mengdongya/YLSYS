package store.chinaotec.com.medicalcare.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import store.chinaotec.com.medicalcare.fragment.slowdise.ChronicDiseaseManagerFragment;
import store.chinaotec.com.medicalcare.javabean.ChronicDiseaseBean;
import store.chinaotec.com.medicalcare.javabean.LinkMessageNum;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2018/2/1 0001.
 * 慢性病管理
 */
public class ChronicDiseaseManagerActivity extends BaseActivity implements View.OnClickListener {
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
    private BroadcastReceiver refreshFragment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ResourseSum.REFRESH_HEALTH_FRAGMENT)) {
                getDiseaseData();
            }
        }
    };
    private List<ChronicDiseaseBean.DataBean.ChronicPatientDto> list;
    private LoadingView loadingDialog;
    private LinkMessageNum.DataBean messageNumData;
    private String refMemberId;
    private TextView tvPopupDialogDian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slow_disease);
        ButterKnife.bind(this);
        initView();
        initListener();
        popupView();
        getDiseaseData();
        checkReadInfo();
    }

    private void initView() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ResourseSum.REFRESH_HEALTH_FRAGMENT);
        registerReceiver(refreshFragment, intentFilter);
        loadingDialog = new LoadingView(this, R.style.ProgressDialog);
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        myViewPagerAdapter = new DiseaseViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initListener() {
        addSlowDisease.setOnClickListener(this);
        deleteDisease.setOnClickListener(this);
        llLoadAgain.setOnClickListener(this);
        llLoginLose.setOnClickListener(this);
    }

    private void getDiseaseData() {
        loadingDialog.show();
        String memberId;
//        if (!StringUtils.isEmpty(refMemberId)){
//            memberId = refMemberId;
//        }else {
            memberId = "";
//        }
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
                            fragments.add(ChronicDiseaseManagerFragment.newInstance(list.get(i)));
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

    private void checkReadInfo() {
        OkHttpUtils.post().url(MyUrl.GET_NOREADE_MESSAGE_NUM).addParams("sid",sid).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                LinkMessageNum messageNum = JSON.parseObject(response, LinkMessageNum.class);
                if ("0".equals(messageNum.getResponseCode())){
                    messageNumData = messageNum.getData();
                    int num = messageNumData.getNoticeNum() + messageNumData.getRemindNum();
                    if (num > 0 && null != tvPopupDialogDian){
                        tvActivitySlowDiseaseDian.setVisibility(View.VISIBLE);
                        tvPopupDialogDian.setVisibility(View.VISIBLE);
                    }else {
                        tvActivitySlowDiseaseDian.setVisibility(View.GONE);
                        tvPopupDialogDian.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_slow_dise:
                searchPopupWindow.showAsDropDown(addSlowDisease);
                break;
            case R.id.delete_dise:
                startActivityForResult(new Intent(this, DeleteChronicDiseaseActivity.class), ResourseSum.ADD_HEALTH_PERSON);
                break;
            case R.id.ll_load_again:
                getDiseaseData();
                break;
            case R.id.ll_login_lose:
                startActivityForResult(new Intent(this,LoginActivity.class),ResourseSum.LOGIN_RESPONSE);
                break;
        }
    }

    private PopupWindow searchPopupWindow;

    /**
     * 点击右上角
     */
    private void popupView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_dialog, null);
        TextView tvPopupDialogView = (TextView) view.findViewById(R.id.tv_popup_dialog_view);
        tvPopupDialogDian = (TextView) view.findViewById(R.id.tv_popup_dialog_dian);
        TextView tvPopupDialogLinkman = (TextView) view.findViewById(R.id.tv_popup_dialog_linkman);

        //添加
        tvPopupDialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ChronicDiseaseManagerActivity.this, AddSlowDiseActivity.class), ResourseSum.ADD_PATIENT);
                searchPopupWindow.dismiss();
            }
        });

        //联系人
        tvPopupDialogLinkman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChronicDiseaseManagerActivity.this, LinkManManageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("messageNumData",messageNumData);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
                //跳转联系人管理
                searchPopupWindow.dismiss();
            }
        });

        searchPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置可以获得焦点
        searchPopupWindow.setTouchable(true);
        // 设置弹窗内可点击
        searchPopupWindow.setFocusable(true);
        // 设置点击Dialog外部任意区域关闭Dialog
        searchPopupWindow.setOutsideTouchable(true);
        searchPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                searchPopupWindow.dismiss();
            }
        });

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
            ChronicDiseaseManagerFragment fragment = (ChronicDiseaseManagerFragment) super.instantiateItem(container, position);
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
        checkReadInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshFragment);
    }
}
