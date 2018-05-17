package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.CheckDoctorSign;
import store.chinaotec.com.medicalcare.javabean.MyCollectList;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;
import store.chinaotec.com.medicalcare.view.MySetRatingBar;

/**
 * 在线医师详情页面
 */
public class DoctorDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.doctor_detail_intel_treat)
    TextView doctorDetailIntelTreat;
    @Bind(R.id.sign_proto)
    TextView signProto;
    @Bind(R.id.doctor_photo)
    ImageView doctorPhoto;
    @Bind(R.id.doctor_name)
    TextView doctorName;
    @Bind(R.id.want_sign)
    Button wantSign;
    @Bind(R.id.linear_sign)
    LinearLayout linearSign;
    @Bind(R.id.agree_sign)
    RadioButton agreeSign;
    @Bind(R.id.tv_collection)
    TextView tvCollect;
    @Bind(R.id.dotor_stars)
    MySetRatingBar signDoctorStars;
    @Bind(R.id.patient_advice)
    TextView patient_advice;
    @Bind(R.id.ll_doctor_level)
    LinearLayout ll_doctor_level;
    @Bind(R.id.hospital_name)
    TextView hospital_name;
    @Bind(R.id.tv_doctor_phone_fee)
    TextView phoneFee;
    @Bind(R.id.tv_doctor_pic_ask)
    TextView tvPicAsk;
    @Bind(R.id.tv_doctor_video_fee)
    TextView tvVideoFee;
    @Bind(R.id.tv_doctor_summary)
    TextView doctorSummary;
    @Bind(R.id.tv_sign_fee)
    TextView signFee;
    private String sid;
    private String isAttention;
    private String doctorCode;
    private MyCollectList.CollectionBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_doctor_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
        getData();
        checkSign();
    }

    private void initView() {
        Intent intent = getIntent();
        doctorCode = intent.getStringExtra("doctor_code");
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
    }

    private void initListener() {
        doctorDetailIntelTreat.setOnClickListener(this);
        signProto.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        wantSign.setOnClickListener(this);
    }

    private void getData() {
        OkHttpUtils.post().url(MyUrl.SIGN_DOCTOR_DETAIL).addParams("sid",sid).addParams("doctorCode",doctorCode).
                build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean result = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(result.responseCode)){
                    bean = JSONObject.parseObject(result.data, MyCollectList.CollectionBean.class);
                    setResponseData();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setResponseData() {

        Glide.with(this).load(bean.getHeadImage()).fitCenter().transform(
                new GlideRoundTransform(this, 5)).placeholder(R.mipmap.online_doctor_logo).into(doctorPhoto);
        doctorName.setText(bean.getName());
        signDoctorStars.setStar(Float.parseFloat(bean.getStarLevel()));
        patient_advice.setText(bean.getAppointment()+"人咨询");
        String[] split = bean.getDoctorLabel().split(",");
        ll_doctor_level.removeAllViews();
        for (int i = 0; i < split.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_hospital_level, null);
            ((TextView) view.findViewById(R.id.tv_hospital_level)).setText(split[i]);
            ll_doctor_level.addView(view);
        }
        hospital_name.setText(bean.getHospitalName());
        tvCollect.setText("y".equals(bean.getIsCollect())? "已收藏":"收藏");

        phoneFee.setText("￥" + bean.getTelephoneFee() + "元/分钟");
        tvPicAsk.setText("￥" + bean.getImageTextSeekFee() + "元/一次");
        tvVideoFee.setText("￥" + bean.getVideoFee() + "元/分钟");
        doctorSummary.setText(bean.getSummary());
        signFee.setText("￥" + bean.getContractFee() + "元/周");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doctor_detail_intel_treat:
                startActivity(new Intent(getApplicationContext(), SeeDoctorActivity.class));
                break;
            //跳转到签约协议展示页面
            case R.id.sign_proto:
                Intent intent = new Intent(this, SignProtoShowActivity.class);
                intent.putExtra("url",bean.getSignAgreen());
                startActivity(intent);
                break;
            case R.id.tv_collection:
                if ("".equals(sid)) {
                    startActivityForResult(new Intent(this, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                    return;
                }

                String collect = tvCollect.getText().toString();
                if ("收藏".equals(collect)) {
                    isAttention = "y";
                } else {
                    isAttention = "n";
                }
                getCollectionRequest();
                break;
            case R.id.want_sign:
                //根据是否登录判断是否能点击"我要签约"按钮
                if ("".equals(sid)) {
                    startActivityForResult(new Intent(this, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                    return;
                }
                if (!"已签约".equals(wantSign.getText().toString())){
                    boolean checked = agreeSign.isChecked();
                    if (checked) {
                        //医生签约标志
                        addAppoint();
                    } else {
                        BaseUtill.toastUtil("请选中同意按钮");
                    }
                }
                break;
        }
    }


    private void checkSign() {
        OkHttpUtils.post().url(MyUrl.SIGN_DOCTOR_FIND_APPOINT).addParams("sid",sid).addParams("doctorCode",doctorCode)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                CheckDoctorSign sign = JSONObject.parseObject(response, CheckDoctorSign.class);
                if ("0".equals(sign.getResponseCode())){
                    if (!"".equals(sign.getData().getEndTime())){
                        linearSign.setVisibility(View.INVISIBLE);
                        wantSign.setText("已签约");
                    }
                }else {
                    linearSign.setVisibility(View.VISIBLE);
                    wantSign.setText("我要签约");
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void addAppoint() {
        OkHttpUtils.post().url(MyUrl.SIGN_DOCTOR_ADD_APPOINT).addParams("sid",sid).addParams("doctorCode",doctorCode).
                build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    linearSign.setVisibility(View.INVISIBLE);
                    wantSign.setText("已签约");
                }else {
                    linearSign.setVisibility(View.VISIBLE);
                    wantSign.setText("我要签约");
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }
        });
    }

    private void getCollectionRequest() {
        OkHttpUtils.post().url(MyUrl.COLLECTION_DOCTOR_TYPE).addParams("sid", sid).addParams("isAttention", isAttention)
                .addParams("doctorCode",doctorCode).build().execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {

                        ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                        if ("0".equals(resultBean.responseCode)) {
                            ToastUtil.MyToast(DoctorDetailActivity.this, "y".equals(isAttention) ? "收藏成功" : "取消收藏");
                            tvCollect.setText("y".equals(isAttention) ? "已收藏" : "收藏");
                        }

                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
            checkCollection();
            checkSign();
        }
    }

    private void checkCollection() {

        OkHttpUtils.post().url(MyUrl.COLLECTION_DOCTOR_QUERY).addParams("sid",sid).addParams("doctorCode",doctorCode)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    tvCollect.setText("已收藏");
                }else {
                    tvCollect.setText("收藏");
                }
            }
        });
    }
}
