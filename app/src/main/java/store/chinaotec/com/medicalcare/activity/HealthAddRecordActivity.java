package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.PatientBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.utill.DateChoseUtill;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.ToastUtil;

/**
 * 添加病程记录页面
 */
public class HealthAddRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.triglycerides)
    EditText triglycerides;
    @Bind(R.id.high_density_lipoprotein)
    EditText highDensityLipoprotein;
    @Bind(R.id.one_period)
    RadioButton onePeriod;
    @Bind(R.id.two_period)
    RadioButton twoPeriod;
    @Bind(R.id.three_period)
    RadioButton threePeriod;
    @Bind(R.id.recourse_other)
    RadioButton recourseOther;
    @Bind(R.id.number_periods)
    RadioGroup numberPeriods;
    @Bind(R.id.commit_recourse_record)
    Button commitRecourseRecord;
    @Bind(R.id.low_density_lipoprotein)
    EditText lowDensityLipoprotein;
    @Bind(R.id.condition_description)
    EditText conditionDescription;
    @Bind(R.id.tv_inoculation)
    TextView tvInoculation;
    @Bind(R.id.tv_contraception)
    TextView tvContraception;

    private String patientId;
    //眼底检查期数
    private String chackPeriod;
    private Context mContext = HealthAddRecordActivity.this;
    private Integer oprType = 0;//0新增1编辑
    private HashMap<String, String> mParams;
//    private Integer diseaseRecordId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_add_record);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this);
        initBaseData();
        initListener();
    }

    private void initBaseData() {
        Intent intent = getIntent();
        patientId = intent.getStringExtra("patientId");
        MyLog.d("添加病程记录时..病人id.." + patientId);
        mParams = new HashMap<>();
//        if (diseaseRecordDtos != null) {
//            triglycerides.setText(diseaseRecordDtos.getBloodFatTg());
//            lowDensityLipoprotein.setText(diseaseRecordDtos.getBloodFatLdl());
//            highDensityLipoprotein.setText(diseaseRecordDtos.getBloodFatHdl());
//            conditionDescription.setText(diseaseRecordDtos.getDiseaseDesc());
//            chackPeriod = diseaseRecordDtos.getFundoscopy();
//            if (!MyCommonUtil.isEmpty(chackPeriod)) {
//                switch (chackPeriod) {
//                    case "Ⅰ期":
//                        onePeriod.setChecked(true);
//                        break;
//                    case "Ⅱ期":
//                        twoPeriod.setChecked(true);
//                        break;
//                    case "Ⅲ期":
//                        threePeriod.setChecked(true);
//                        break;
//                    case "正常":
//                        recourseOther.setChecked(true);
//                        break;
//                }
//            }
//            diseaseRecordId = diseaseRecordDtos.getDiseaseRecordId();
//        }

    }

    private void initListener() {
        tvInoculation.setOnClickListener(this);
        tvContraception.setOnClickListener(this);
        commitRecourseRecord.setOnClickListener(this);

        numberPeriods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.one_period:
                        chackPeriod = "Ⅰ期";
                        break;
                    case R.id.two_period:
                        chackPeriod = "Ⅱ期";
                        break;
                    case R.id.three_period:
                        chackPeriod = "Ⅲ期";
                        break;
                    case R.id.recourse_other:
                        chackPeriod = "正常";
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_inoculation:
                DateChoseUtill.choseDate(this, new DateChoseUtill.GetChoseDateListener() {
                    @Override
                    public void getChoseDate(String date) {
                        tvInoculation.setText(date);
                    }
                }, "yyyy-MM-dd");
                break;
            case R.id.tv_contraception:
                DateChoseUtill.choseDate(this, new DateChoseUtill.GetChoseDateListener() {
                    @Override
                    public void getChoseDate(String date) {
                        tvContraception.setText(date);
                    }
                }, "yyyy-MM-dd");
                break;
            case R.id.commit_recourse_record:
                addRecourseRecord();
                break;
        }
    }

    /**
     * 获取录入的甘油三酯  低密度脂蛋白  高密度脂蛋白  病情描述数据并添加修改
     */
    private void addRecourseRecord() {
        //甘油三酯 低密度脂蛋白 高密度脂蛋白  病情描述 数据获取
        final String triglyceridesContent = triglycerides.getText().toString();
        String lowDensityContent = lowDensityLipoprotein.getText().toString();
        String highDensityContent = highDensityLipoprotein.getText().toString();
        String conditionContent = conditionDescription.getText().toString();
        String inoculation = tvInoculation.getText().toString();
        String contraception = tvContraception.getText().toString();
        if (MyCommonUtil.isEmpty(triglyceridesContent)) {
            ToastUtil.showToast(this, "请输入甘油三酯");
            return;
        }
        if (MyCommonUtil.isEmpty(lowDensityContent)) {
            ToastUtil.showToast(this, "请输入低密度脂蛋白");
            return;
        }
        if (MyCommonUtil.isEmpty(highDensityContent)) {
            ToastUtil.showToast(this, "请输入高密度脂蛋白");
            return;
        }
        if (MyCommonUtil.isEmpty(chackPeriod)) {
            ToastUtil.showToast(this, "请选择眼底检查");
            return;
        }
        if (MyCommonUtil.isEmpty(inoculation)) {
            ToastUtil.showToast(this, "请选择接种时间");
            return;
        }
        if (MyCommonUtil.isEmpty(contraception)) {
            ToastUtil.showToast(this, "请选择避孕时间");
            return;
        }
//        if (diseaseRecordDtos != null) {
//            oprType = 1;
//        }

        mParams.clear();

        mParams.put("oprType","0");
        mParams.put("patientId",patientId);
        mParams.put("bloodFatTg",triglyceridesContent);
        mParams.put("bloodFatLdl",lowDensityContent);
        mParams.put("bloodFatHdl",highDensityContent);
        mParams.put("fundoscopy",chackPeriod);
        mParams.put("diseaseDesc",conditionContent);
        mParams.put("inoculate",inoculation);
        mParams.put("contraception",contraception);
        OkHttpUtils.post().url(MyUrl.add_dise_record).params(mParams).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    ToastUtil.showToast(HealthAddRecordActivity.this, "添加成功!");
                    Intent intent = new Intent(ResourseSum.REFRESH_HEALTH_FRAGMENT);
                    sendBroadcast(intent);
                    finish();
                }else {
                    ToastUtil.showToast(HealthAddRecordActivity.this, resultBean.msg);
                }
            }
        });

    }
}
