package store.chinaotec.com.medicalcare.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HospitalBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;
import store.chinaotec.com.medicalcare.view.MySetRatingBar;

/**
 * Created by wjc on 2017/9/15 0015.
 */
public class GoToHospitalActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.hospital_root)
    RelativeLayout hospital_root;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.go_hospital_search)
    ImageView goHospitalSearch;
    @Bind(R.id.on_line_intellg_treatment)
    TextView onLineIntelligentTreatment;
    @Bind(R.id.ll_go_hospital_choose)
    LinearLayout llChoose;
    @Bind(R.id.ll_hospital_level)
    LinearLayout llHospitalLevel;
    @Bind(R.id.ll_hospital_type)
    LinearLayout llHospitalType;
    @Bind(R.id.ll_hospital_sort)
    LinearLayout llHospitalSort;
    @Bind(R.id.go_hospitals_recycleview)
    LRecyclerView recyclerView;
    @Bind(R.id.iv_near_hospital)
    ImageView iv_near_hospital;
    @Bind(R.id.iv_ad_img)
    ImageView ivAdImg;
    @Bind(R.id.tv_ad_close)
    TextView tvAdClose;
    @Bind(R.id.rl_ad)
    RelativeLayout rl_ad;
    @Bind(R.id.iv_level)
    ImageView ivLevel;
    @Bind(R.id.iv_type)
    ImageView ivType;
    @Bind(R.id.iv_sort)
    ImageView ivSort;
    @Bind(R.id.view_night)
    View view_night;

    private List<HospitalBean.DataBean.HospitalListBean> hospitalList;
    private HospitalListAdapter adapter;
    private PopupWindow screenPopupWindow;
    private PopupWindow searchPopupWindow;
    private HashMap<String, String> mParams;
    private String cityArr;
    private HospitalBean.DataBean.MedicalAD medicalAD;
    private String[] split;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int pageIndex = 1;
    private boolean adFirst = true;
    private List<HospitalBean.DataBean.HospitalLevel> hospitalChooseType;
    private HospitalChooseAdapter chooseTypeAdapter;
    private HospitalBean hospitalBean;
    private String hospitalLevelId;
    private String hospitalTypeId;
    private String sort;
    private String chooseType = "1";
    private String searchBody;
    private boolean isSearch = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_hospital);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        goHospitalSearch.setOnClickListener(this);
        onLineIntelligentTreatment.setOnClickListener(this);
        iv_near_hospital.setOnClickListener(this);
        tvAdClose.setOnClickListener(this);
        llHospitalLevel.setOnClickListener(this);
        llHospitalType.setOnClickListener(this);
        llHospitalSort.setOnClickListener(this);
    }

    private void initView() {

        cityArr = SpUtill.getString(this, ResourseSum.Medica_SP, "saveLocationAddress");
        if (!MyCommonUtil.isEmpty(cityArr)) {
            split = cityArr.split(",");
        } else {
            split = new String[]{"0.0", "0.0"};
        }
        mParams = new HashMap<>();
        hospitalChooseType = new ArrayList<>();
        hospitalList = new ArrayList<>();
        ivLevel.setBackgroundResource(R.mipmap.arrow_up);
        ivType.setBackgroundResource(R.mipmap.arrow_up);
        ivSort.setBackgroundResource(R.mipmap.arrow_up);
        adapter = new HospitalListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
//                if (isSearch) {
//                    getSearchResponse();
//                } else {
                    getHospitalList();
//                }

            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
//                if (isSearch) {
//                    getSearchResponse();
//                } else {
                    getHospitalList();
//                }
            }
        });
        initScreenPopupWindow();
        initSearchPopupWindow();

        getHospitalList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.go_hospital_search:
                searchPopupWindow.showAsDropDown(hospital_root);
                view_night.setVisibility(View.VISIBLE);
                break;
            case R.id.on_line_intellg_treatment:
                startActivity(new Intent(this, SeeDoctorActivity.class));
                break;
            case R.id.iv_near_hospital:
                checkIsOpenLocation();
                break;
            case R.id.tv_ad_close:
                rl_ad.setVisibility(View.GONE);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_near_hospital.getLayoutParams();
                layoutParams.bottomMargin = 40;
                iv_near_hospital.setLayoutParams(layoutParams);
                break;
            case R.id.ll_hospital_level:
                chooseType = "1";
                hospitalChooseType.clear();
                hospitalChooseType.addAll(hospitalBean.getData().getMedicalLevelList());
                chooseTypeAdapter.notifyDataSetChanged();
                ivLevel.setBackgroundResource(R.mipmap.arrow_down);
                ivType.setBackgroundResource(R.mipmap.arrow_up);
                ivSort.setBackgroundResource(R.mipmap.arrow_up);
                screenPopupWindow.showAsDropDown(llChoose);
//                backgroundAlpha(0.7f);
                view_night.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_hospital_type:
                chooseType = "2";
                hospitalChooseType.clear();
                hospitalChooseType.addAll(hospitalBean.getData().getMedicalTypeList());
                chooseTypeAdapter.notifyDataSetChanged();
                ivLevel.setBackgroundResource(R.mipmap.arrow_up);
                ivType.setBackgroundResource(R.mipmap.arrow_down);
                ivSort.setBackgroundResource(R.mipmap.arrow_up);
                screenPopupWindow.showAsDropDown(llChoose);
//                backgroundAlpha(0.7f);
                view_night.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_hospital_sort:
                chooseType = "3";
                hospitalChooseType.clear();
                hospitalChooseType.addAll(hospitalBean.getData().getSortList());
                chooseTypeAdapter.notifyDataSetChanged();
                ivLevel.setBackgroundResource(R.mipmap.arrow_up);
                ivType.setBackgroundResource(R.mipmap.arrow_up);
                ivSort.setBackgroundResource(R.mipmap.arrow_down);
                screenPopupWindow.showAsDropDown(llChoose);
//                backgroundAlpha(0.7f);
                view_night.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getHospitalList() {
        String url = MyUrl.HOSPITAL_LIST_URL;
        mParams.clear();

        mParams.put("latitude", split[0]);
        mParams.put("longitude", split[1]);
        mParams.put("pageIndex", pageIndex + "");
        if (!StringUtils.isEmpty(hospitalLevelId)) {
            mParams.put("hospitalLevelId", hospitalLevelId);
        }
        if (!StringUtils.isEmpty(hospitalTypeId)) {
            mParams.put("hospitalTypeId", hospitalTypeId);
        }
        if (!StringUtils.isEmpty(sort)) {
            mParams.put("sort", sort);
        }
        if (null != searchBody){
            mParams.put("keyWord",searchBody);
        }


        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                hospitalBean = JSON.parseObject(response, HospitalBean.class);
                if ("0".equals(hospitalBean.getResponseCode())) {
//                    isSearch = false;
                    if (pageIndex == 1) {
                        hospitalList.clear();
                    }
                    hospitalList.addAll(hospitalBean.getData().getHospitalList());
                    lRecyclerViewAdapter.notifyDataSetChanged();

                    hospitalChooseType.clear();
                    hospitalChooseType.addAll(hospitalBean.getData().getMedicalLevelList());
                    chooseTypeAdapter.notifyDataSetChanged();

                    medicalAD = hospitalBean.getData().getMedicalADV().get(0);
                    if (!"".equals(medicalAD.getImg()) && adFirst) {
                        adFirst = false;
                        Glide.with(GoToHospitalActivity.this).load(medicalAD.getImg()).transform(new GlideRoundTransform(GoToHospitalActivity.this, 1)).into(ivAdImg);
                        ivAdImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!StringUtils.isEmpty(medicalAD.getUrl())){
                                    Intent intent = new Intent(GoToHospitalActivity.this, HealthInfoDetailActivity.class);
                                    intent.putExtra("url", medicalAD.getUrl());
                                    intent.putExtra("title", medicalAD.getName());
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void initSearchPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwin_search, null);
        searchPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置可以获得焦点
        searchPopupWindow.setTouchable(true);
        // 设置弹窗内可点击
        searchPopupWindow.setFocusable(true);
        // 设置点击Dialog外部任意区域关闭Dialog
        searchPopupWindow.setOutsideTouchable(true);
        searchPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        searchPopupWindow.update();
        searchPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                searchPopupWindow.dismiss();
                view_night.setVisibility(View.GONE);
            }
        });

        final EditText search_body = (EditText) view.findViewById(R.id.et_search_body);
        view.findViewById(R.id.tv_click_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBody = search_body.getText().toString().trim();
//                if ("".equals(body)) {
//                    ToastUtil.MyToast(GoToHospitalActivity.this, "请输入内容");
//                    return;
//                }

//                getSearchResponse();
                searchPopupWindow.dismiss();
                pageIndex = 1;
                getHospitalList();

            }
        });


    }

    private void getSearchResponse() {
        String url = MyUrl.HOSPITAL_SEARCH_LIST;
        mParams.clear();
        mParams.put("name", searchBody);
        mParams.put("pageIndex", pageIndex + "");
        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                HospitalBean hospitalBean = JSON.parseObject(response, HospitalBean.class);
                if ("0".equals(hospitalBean.getResponseCode())) {
                    isSearch = true;
                    if (pageIndex == 1){
                        hospitalList.clear();
                    }
                    hospitalList.addAll(hospitalBean.getData().getHospitalList());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    searchPopupWindow.dismiss();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }


    public void initScreenPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwin_screen, null);
        screenPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置可以获得焦点
        screenPopupWindow.setTouchable(true);
        // 设置弹窗内可点击
        screenPopupWindow.setFocusable(true);
        // 设置点击Dialog外部任意区域关闭Dialog
        screenPopupWindow.setOutsideTouchable(true);
        screenPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        screenPopupWindow.update();
        screenPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                screenPopupWindow.dismiss();
//                backgroundAlpha(1f);
                view_night.setVisibility(View.GONE);
            }
        });

        RecyclerView recyclerViewType = (RecyclerView) view.findViewById(R.id.rv_hospital_choose);
        recyclerViewType.setLayoutManager(new LinearLayoutManager(this));
        chooseTypeAdapter = new HospitalChooseAdapter();
        recyclerViewType.setAdapter(chooseTypeAdapter);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    class HospitalChooseAdapter extends RecyclerView.Adapter<HospitalChooseAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(GoToHospitalActivity.this).inflate(R.layout.item_choose_type, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final HospitalBean.DataBean.HospitalLevel hospital = hospitalChooseType.get(position);
            if ("1".equals(chooseType)) {
                holder.chooseName.setText(hospital.getTypeName());
            } else if ("2".equals(chooseType)) {
                holder.chooseName.setText(hospital.getTypeName());
            } else {
                holder.chooseName.setText(hospital.getName());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("1".equals(chooseType)) {
                        if ("三甲".equals(hospital.getTypeName())) {
                            hospitalLevelId = "1";
                        } else if ("二甲".equals(hospital.getTypeName())) {
                            hospitalLevelId = "2";
                        } else {
                            hospitalLevelId = "3";
                        }
                    } else if ("2".equals(chooseType)) {
                        hospitalTypeId = String.valueOf(hospital.getId());
                    } else {
                        sort = hospital.getKey();
                    }
                    screenPopupWindow.dismiss();
                    ivLevel.setBackgroundResource(R.mipmap.arrow_up);
                    ivType.setBackgroundResource(R.mipmap.arrow_up);
                    ivSort.setBackgroundResource(R.mipmap.arrow_up);
                    pageIndex = 1;
                    getHospitalList();
                }
            });
        }

        @Override
        public int getItemCount() {
            return hospitalChooseType.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_choose_name)
            TextView chooseName;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class HospitalLevelAdapter extends BaseAdapter {
        private int selectorPosition;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(GoToHospitalActivity.this, R.layout.item_screen_contion_content, null);
            RelativeLayout mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            TextView textView = (TextView) convertView.findViewById(R.id.contion_content);
            textView.setText(hospitalChooseType.get(position).getTypeName());
            //如果当前的position等于传过来点击的position,就去改变他的状态
            if (selectorPosition == position) {
                mRelativeLayout.setBackgroundResource(R.drawable.bg_choose_green);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                //其他的恢复原来的状态
                mRelativeLayout.setBackgroundResource(R.drawable.bg_choose_white);
                textView.setTextColor(Color.parseColor("#3F51B5"));
            }
            return convertView;
        }


        public void changeState(int pos) {
            selectorPosition = pos;
            notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            return hospitalChooseType.size();
        }

        @Override
        public Object getItem(int position) {
            return hospitalChooseType.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

    class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(GoToHospitalActivity.this).inflate(R.layout.item_go_hospitals, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final HospitalBean.DataBean.HospitalListBean hospitalListBean = hospitalList.get(position);
            Glide.with(GoToHospitalActivity.this).load(hospitalListBean.getHeadImage()).fitCenter().transform(new GlideRoundTransform(GoToHospitalActivity.this, 5)).placeholder(R.mipmap.ic_launcher).into(holder.hospitalLogo);
            holder.hospitalName.setText(hospitalListBean.getName());
            holder.appStars.setStar(Float.parseFloat(hospitalListBean.getStarLevel()));
            if (!"null".equals(hospitalListBean.getAppointment()) && hospitalListBean.getAppointment() != 0){
                holder.linear_reservation.setVisibility(View.VISIBLE);
                holder.reservationNumber.setText(hospitalListBean.getAppointment());
            }else{
                holder.linear_reservation.setVisibility(View.GONE);
            }
            String[] split = hospitalListBean.getHospitalLabel().split(",");
            holder.ll_hospital_level.removeAllViews();
            for (int i = 0; i < split.length; i++) {
                View view = LayoutInflater.from(GoToHospitalActivity.this).inflate(R.layout.item_hospital_level, null);
                ((TextView) view.findViewById(R.id.tv_hospital_level)).setText(split[i]);
                holder.ll_hospital_level.addView(view);
            }

            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GoToHospitalActivity.this, HospitalDetailActivity.class);
                    intent.putExtra("hospitalCode", hospitalListBean.getCode());
                    intent.putExtra("hospitalLabel", hospitalListBean.getHospitalLabel());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return hospitalList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.hospital_logo)
            ImageView hospitalLogo;
            @Bind(R.id.hospital_name)
            TextView hospitalName;
            @Bind(R.id.app_stars)
            MySetRatingBar appStars;
            @Bind(R.id.distace_data)
            TextView distanceData;
            @Bind(R.id.reservation_number)
            TextView reservationNumber;
            @Bind(R.id.ll_item_hospital)
            LinearLayout llItem;
            @Bind(R.id.ll_hospital_level)
            LinearLayout ll_hospital_level;
            @Bind(R.id.linear_reservation)
            LinearLayout linear_reservation;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adFirst = true;
    }

    private void checkIsOpenLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    ToastUtil.showMessageOKCancel("您需要获取定位的权限\n设置方法:权限管理->定位->允许", this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse("package:" + "store.chinaotec.com.medicalcare"));
                            startActivity(intent);
                        }
                    });
                } else {
                    openLocationService();
                }
            } else {
                openLocationService();
            }
        } else {
            openLocationService();
        }
    }

    private void openLocationService() {
        startActivity(new Intent(this, NearbyHospitalActivity.class));
    }

}
