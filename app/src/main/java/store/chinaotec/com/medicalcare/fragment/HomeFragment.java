package store.chinaotec.com.medicalcare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
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
import store.chinaotec.com.medicalcare.activity.CheckResultInterpretActivity;
import store.chinaotec.com.medicalcare.activity.ChooseCityActivity;
import store.chinaotec.com.medicalcare.activity.ChronicDiseaseManagerActivity;
import store.chinaotec.com.medicalcare.activity.GoToHospitalActivity;
import store.chinaotec.com.medicalcare.activity.HealthInfoDetailActivity;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.activity.MedicUnionTreatActivity;
import store.chinaotec.com.medicalcare.activity.MedicalCareActivity;
import store.chinaotec.com.medicalcare.activity.OneKeyCallActivity;
import store.chinaotec.com.medicalcare.activity.PretentPostCareActivity;
import store.chinaotec.com.medicalcare.activity.SeeDoctorActivity;
import store.chinaotec.com.medicalcare.activity.SudDiseActivity;
import store.chinaotec.com.medicalcare.activity.TreatmentOfSuddenInjuryActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.view.XListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.adapter.ShopListAdapter;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.TimeUtil;
import store.chinaotec.com.medicalcare.view.GoSeeDoctView;
import store.chinaotec.com.medicalcare.view.HomeItemView;
import store.chinaotec.com.medicalcare.view.HomeTopView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.position_city)
    TextView positionCity;
    @Bind(R.id.linear_position)
    LinearLayout linearPosition;
    @Bind(R.id.onekey_call)
    Button onekeyCall;
    @Bind(R.id.rv_health_information)
    LRecyclerView recyclerView;
    @Bind(R.id.lv_health_information_list)
    XListView listView;

    private HomeItemView homeMedicalCare;
    private HomeItemView homeSuddenTreat;
    private HomeItemView homeCheckResult;
    private HomeItemView homeSlowDisease;
    private HomeItemView homeOnlinePharm;
    private HomeItemView homeYoushengYouyu;
    private GoSeeDoctView homeSeeDoctor;
    private HomeTopView homeGoHospital;
    private HomeTopView homeOnlinePhysic;
    private int CHOOSE_CITY = 1;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private FragmentActivity mContext;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> mDataList;
    private int pageIndex = 1;
    private String sid;
    private HealthInfoListAdapter adapter;

    public static HomeFragment instance(String city) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    private void initView() {
        mContext = getActivity();
        Bundle bundle = getArguments();
        String city1 = (String) bundle.get("city");
        positionCity.setText(city1);

        mDataList = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        lRecyclerViewAdapter = new LRecyclerViewAdapter(new HealthInfoAdapter());
//        recyclerView.setAdapter(lRecyclerViewAdapter);
//        recyclerView.setLoadMoreEnabled(false);
//        lRecyclerViewAdapter.addHeaderView(getHeaderView());
//        recyclerView.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pageIndex = 1;
//                getHealthData();
//            }
//        });
        adapter = new HealthInfoListAdapter();
        listView.setPullLoadEnable(false);
        listView.addHeaderView(getHeaderView());
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getHealthData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HealthInfoDetailActivity.class);
                intent.putExtra("title", mDataList.get(position).getName());
                intent.putExtra("url", mDataList.get(position).getUrl());
                startActivity(intent);
            }
        });
        initListener();

        getHealthData();
    }

    private void initListener() {
        homeSlowDisease.setOnClickListener(this);
        homeCheckResult.setOnClickListener(this);
        homeGoHospital.setOnClickListener(this);
        homeMedicalCare.setOnClickListener(this);
        homeYoushengYouyu.setOnClickListener(this);
        homeOnlinePharm.setOnClickListener(this);
        homeSeeDoctor.setOnClickListener(this);
        homeSuddenTreat.setOnClickListener(this);
        homeOnlinePhysic.setOnClickListener(this);
        linearPosition.setOnClickListener(this);
        onekeyCall.setOnClickListener(this);
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_home, null);
        homeSeeDoctor = (GoSeeDoctView) view.findViewById(R.id.home_see_doctor);
        homeGoHospital = (HomeTopView) view.findViewById(R.id.home_go_hospital);
        homeOnlinePhysic = (HomeTopView) view.findViewById(R.id.home_online_physic);
        homeMedicalCare = (HomeItemView) view.findViewById(R.id.home_medical_care);
        homeSuddenTreat = (HomeItemView) view.findViewById(R.id.home_sudden_treat);
        homeCheckResult = (HomeItemView) view.findViewById(R.id.home_check_result);
        homeSlowDisease = (HomeItemView) view.findViewById(R.id.home_slow_disease);
        homeOnlinePharm = (HomeItemView) view.findViewById(R.id.home_online_pharm);
        homeYoushengYouyu = (HomeItemView) view.findViewById(R.id.home_yousheng_youyu);
        return view;
    }

    private void getHealthData() {

        OkHttpUtils.post().url(MyUrl.medical_book_list).addParams("type", "13").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalBookBean medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
                List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> medicalTypeList = medicalBookBean.
                        getData().getMedicalTypeList().get(0).getDataList().get(0).getMedicalBookList();
                if (pageIndex == 1) {
                    mDataList.clear();
                }
                mDataList.addAll(medicalTypeList);
//                lRecyclerViewAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                try {
//                    recyclerView.refreshComplete(pageIndex);
                    listView.stopRefresh();
                    listView.setRefreshTime(TimeUtil.getDateForHHmm());
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页"导航城市选择"模块
            case R.id.linear_position:
                startActivityForResult(new Intent(getContext(), ChooseCityActivity.class), CHOOSE_CITY);
                break;
            //首页"去看病"模块
            case R.id.home_see_doctor:
                startActivity(new Intent(getContext(), SeeDoctorActivity.class));
                break;
            //首页"去医院"模块
            case R.id.home_go_hospital:
                startActivity(new Intent(getContext(), GoToHospitalActivity.class));
                break;
            //首页"医联体医疗"模块
            case R.id.home_online_physic:
                startActivity(new Intent(getContext(), MedicUnionTreatActivity.class));
                break;
            //首页"检查结果解读"模块
            case R.id.home_check_result:
//                startActivity(new Intent(getContext(), TestResultActivity.class));
                startActivity(new Intent(getContext(), CheckResultInterpretActivity.class));
                break;
            //首页"医养都会"模块
            case R.id.home_medical_care:
                startActivity(new Intent(getContext(), MedicalCareActivity.class));
                break;
            //首页"一键呼叫"模块
            case R.id.onekey_call:
                startActivity(new Intent(getContext(), OneKeyCallActivity.class));
                break;
            //首页"优生优育"模块
            case R.id.home_yousheng_youyu:
                startActivity(new Intent(getContext(), PretentPostCareActivity.class));
                break;
            //首页"慢性病管理"模块
            case R.id.home_slow_disease:
                //获取用户的登录状态
                sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
                if (!StringUtils.isEmpty(sid)) {
//                    startActivity(new Intent(MyApp.getContext(), SlowDiseaseActivity.class));
                    startActivity(new Intent(MyApp.getContext(), ChronicDiseaseManagerActivity.class));
                } else {
                    startActivityForResult(new Intent(MyApp.getContext(), LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                }
                break;
            //首页"突发伤病处置"模块
            case R.id.home_sudden_treat:
//                startActivity(new Intent(getContext(), SudDiseActivity.class));
                startActivity(new Intent(getContext(),TreatmentOfSuddenInjuryActivity.class));
                break;
            //首页"在线药店"模块
            case R.id.home_online_pharm:
                startActivity(new Intent(getContext(), MainTabActivity.class));
                break;
        }
    }

    public class HealthInfoListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(mContext, R.layout.item_health_info, null);
                holder.healthInfoLogo = (ImageView) convertView.findViewById(R.id.health_info_logo);
                holder.healthBigTitle = (TextView) convertView.findViewById(R.id.health_big_title);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Glide.with(mContext).load(mDataList.get(position).getImage()).override(150, 150).into(holder.healthInfoLogo);
            holder.healthBigTitle.setText(mDataList.get(position).getName());

            return convertView;
        }

        class Holder {
            ImageView healthInfoLogo;
            TextView healthBigTitle;
        }
    }

    public class HealthInfoAdapter extends RecyclerView.Adapter<HealthInfoAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_health_info, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Glide.with(mContext).load(mDataList.get(position).getImage()).override(150, 150).into(holder.healthInfoLogo);
            holder.healthBigTitle.setText(mDataList.get(position).getName());
//        holder.healthSmallTitle.setText(mData.get(position).getLexiconName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), HealthInfoDetailActivity.class);
                    intent.putExtra("title", mDataList.get(position).getName());
                    intent.putExtra("url", mDataList.get(position).getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.health_info_logo)
            ImageView healthInfoLogo;
            @Bind(R.id.health_big_title)
            TextView healthBigTitle;
            @Bind(R.id.health_small_title)
            TextView healthSmallTitle;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getHeaderView();
        if (lRecyclerViewAdapter != null){
            lRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_CITY && resultCode == getActivity().RESULT_OK) {
            String city = SpUtill.getString(getActivity(), ResourseSum.Medica_SP, "saveHotCity");
            positionCity.setText(city);
        } else if (requestCode == ResourseSum.LOGIN_RESPONSE && resultCode == getActivity().RESULT_OK) {
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
