package store.chinaotec.com.medicalcare.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HospitalDetailResponse;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.AmapActivity;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;
import store.chinaotec.com.medicalcare.view.MySetRatingBar;

/**
 * Created by wjc on 2017/9/15 0015.
 * 医院详情
 */
public class HospitalDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.go_hosipiatl_one)
    TextView goHosipiatlOne;
    @Bind(R.id.hosp_det_intell_treatment)
    Button hospDetIntellTreatment;
    @Bind(R.id.linear_hosp_detail_title)
    RelativeLayout linearHospDetailTitle;
    @Bind(R.id.hospital_logo)
    ImageView hospitalLogo;
    @Bind(R.id.hospital_name)
    TextView hospitalName;
    @Bind(R.id.hospital_detail_stars)
    MySetRatingBar hospitalDetailStars;
    @Bind(R.id.bus_route)
    Button busRoute;
    @Bind(R.id.regist_doctor)
    Button registDoctor;
    @Bind(R.id.guide_doctor)
    Button guideDoctor;
    @Bind(R.id.hospital_introduct)
    TextView hospitalIntroduct;
    @Bind(R.id.hospitalRealRecycleview)
    RecyclerView hospitalRealRecycleview;
    @Bind(R.id.ll_hospital_level)
    LinearLayout ll_hospital_level;
    @Bind(R.id.tv_hospital_collect)
    TextView tvHospitalCollect;
    @Bind(R.id.rv_expert_column)
    RecyclerView rvExpertColumn;
    @Bind(R.id.tv_hospital_doctor_more)
    TextView doctorMore;
    @Bind(R.id.tv_hospital_appoint)
    TextView tvHospitalAppoint;

    private String hospitalCode;
    private String hospitalLabel;
    private HospitalDetailResponse.DataBean.HospitalDetail hospitalDetail;
    private List<String> landscapeImagesList;
    private List<HospitalDetailResponse.DataBean.DoctorList> doctorLists;
    private String sid;
    private String isAttention;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hospitalCode = getIntent().getStringExtra("hospitalCode");
        hospitalLabel = getIntent().getStringExtra("hospitalLabel");
        setContentView(R.layout.activity_hospital_destail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        initListener();
        getHospitalDetail();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        hospDetIntellTreatment.setOnClickListener(this);
        busRoute.setOnClickListener(this);
        doctorMore.setOnClickListener(this);
        tvHospitalCollect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.hosp_det_intell_treatment:
                startActivity(new Intent(this, SeeDoctorActivity.class));
                break;
            case R.id.bus_route:
                checkIsOpenLoaction();
                break;
            case R.id.tv_hospital_doctor_more:
                Intent intent = new Intent(this, HospitalDoctorListActivity.class);
                intent.putExtra("hospitalCode", hospitalCode);
                startActivity(intent);
                break;
            case R.id.tv_hospital_collect:
                if ("".equals(sid)) {
                    startActivityForResult(new Intent(this, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                    return;
                }

                String collect = tvHospitalCollect.getText().toString();
                if ("收藏".equals(collect)) {
                    isAttention = "y";
                } else {
                    isAttention = "n";
                }
                getCollectionRequest();
                break;
        }
    }

    private void getCollectionRequest() {
        OkHttpUtils.post().url(MyUrl.COLLECTION_TYPE).addParams("sid", sid).addParams("isAttention", isAttention).
                addParams("hospitalCode", hospitalCode).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {

                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)) {
                    ToastUtil.MyToast(HospitalDetailActivity.this, "y".equals(isAttention) ? "收藏成功" : "取消收藏");
                    tvHospitalCollect.setText("y".equals(isAttention) ? "已收藏" : "收藏");
                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void getHospitalDetail() {
        OkHttpUtils.post().url(MyUrl.HOSPITAL_DETAIL).addParams("sid", sid).addParams("hospitalCode", hospitalCode).
                build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                HospitalDetailResponse detailResponse = JSON.parseObject(response, HospitalDetailResponse.class);
                if ("0".equals(detailResponse.getResponseCode())) {
                    hospitalDetail = detailResponse.getData().getHospitalDetail();
                    doctorLists = detailResponse.getData().getDoctor();
                    setData();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {
        hospitalName.setText(hospitalDetail.getName());
        Glide.with(this).load(hospitalDetail.getHeadImage()).fitCenter().transform(new GlideRoundTransform(this, 5)).into(hospitalLogo);
        hospitalIntroduct.setText(hospitalDetail.getSummary());
        hospitalDetailStars.setStar(Float.parseFloat(hospitalDetail.getStarLevel()));
        tvHospitalCollect.setText(("n").equals(hospitalDetail.getIsCollect()) ? "收藏" : "已收藏");
//        tvHospitalAppoint.setText(hospitalDetail.get);
        String[] split = hospitalLabel.split(",");
        for (int i = 0; i < split.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_hospital_level, null);
            ((TextView) view.findViewById(R.id.tv_hospital_level)).setText(split[i]);
            ll_hospital_level.addView(view);
        }

        if (doctorLists.size() >= 3) {
            doctorMore.setVisibility(View.VISIBLE);
        } else {
            doctorMore.setVisibility(View.GONE);
        }
        rvExpertColumn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvExpertColumn.setAdapter(new HospitalDoctorAdapter());

        if (!"".equals(hospitalDetail.getLandscapeImages()) && null != hospitalDetail.getLandscapeImages()) {
            String[] landscapeImages = hospitalDetail.getLandscapeImages().split(",");

            landscapeImagesList = Arrays.asList(landscapeImages);
            hospitalRealRecycleview.setLayoutManager(new GridLayoutManager(this, 2));
            hospitalRealRecycleview.setAdapter(new HospitalSceneryAdapter());
        }

    }


    private void checkIsOpenLoaction() {
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
        Intent intent = new Intent(this, AmapActivity.class);
        if (hospitalDetail != null) {
            intent.putExtra(SourceConstant.STORE_LOCATION_LONGITUDE, hospitalDetail.getLongitude());
            intent.putExtra(SourceConstant.STORE_LOCATION_LATITUDE, hospitalDetail.getLatitude());
            startActivity(intent);
        }

    }

    class HospitalSceneryAdapter extends RecyclerView.Adapter<HospitalSceneryAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(HospitalDetailActivity.this).inflate(R.layout.item_hospital_scenery, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(HospitalDetailActivity.this).load(landscapeImagesList.get(position)).fitCenter().into(holder.ivHospitalScenery);
        }

        @Override
        public int getItemCount() {
            return landscapeImagesList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_hospital_scenery)
            ImageView ivHospitalScenery;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class HospitalDoctorAdapter extends RecyclerView.Adapter<HospitalDoctorAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(HospitalDetailActivity.this).
                    inflate(R.layout.item_hospital_doctor, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HospitalDetailResponse.DataBean.DoctorList doctor = doctorLists.get(position);
            Glide.with(HospitalDetailActivity.this).load(doctor.getHeadImage()).transform(new GlideRoundTransform(HospitalDetailActivity.this, 5)).into(holder.ivDoctorHeadImg);

            holder.tvDoctorName.setText(doctor.getName());
            holder.tvDoctorLable.setText(doctor.getDoctorLabel());
            holder.tvDoctorSummary.setText(doctor.getSummary());
        }

        @Override
        public int getItemCount() {
            return doctorLists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.iv_doctor_head_img)
            ImageView ivDoctorHeadImg;
            @Bind(R.id.tv_doctor_name)
            TextView tvDoctorName;
            @Bind(R.id.tv_doctor_lable)
            TextView tvDoctorLable;
            @Bind(R.id.tv_doctor_summary)
            TextView tvDoctorSummary;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
            checkCollection();
        }
    }

    private void checkCollection() {

        OkHttpUtils.post().url(MyUrl.COLLECTION_QUERY).addParams("sid", sid).addParams("hospitalCode", hospitalCode).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)) {
                    tvHospitalCollect.setText("已收藏");
                } else {
                    tvHospitalCollect.setText("收藏");
                }
            }
        });
    }
}
