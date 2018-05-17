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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.PatientBean;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.ToastUtil;

/**
 * 添加病程记录页面
 */
public class AddRecourseCordActivity extends BaseActivity implements View.OnClickListener {

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
    private String patientId;
    //眼底检查期数
    private String chackPeriod;
    private Context mContext = AddRecourseCordActivity.this;
    private PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean.DiseaseRecordDtosBean diseaseRecordDtos;
    private Integer oprType = 0;//0新增1编辑
    private Integer diseaseRecordId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recourse_cord);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this);
        initBaseData();
        initListener();
    }

    private void initBaseData() {
        Intent intent = getIntent();
        patientId = intent.getStringExtra("patientId");
//        diseaseRecordDtos = (PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean.DiseaseRecordDtosBean) intent.getSerializableExtra("diseaseRecordDtos");
        MyLog.d("添加病程记录时..病人id.." + patientId);

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
        if (diseaseRecordDtos != null) {
            oprType = 1;
        }
        NetWorkUtill.addRecourseRecord(oprType, patientId, triglyceridesContent, lowDensityContent, highDensityContent, chackPeriod, conditionContent, diseaseRecordId, new NetWorkUtill.RecourseRecordListener() {
            @Override
            public void getContent(String s) {
                JSONObject parseObject = JSON.parseObject(s);
                Integer responseCode = parseObject.getInteger("responseCode");
                if (responseCode == 0) {
                    //添加病程记录的标志
                    SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "diseaseRecords", true);
                    ToastUtil.showToast(AddRecourseCordActivity.this, "添加成功!");
                    Intent intent = new Intent(ResourseSum.REFRESH_HEALTH_FRAGMENT);
                    sendBroadcast(intent);
                    finish();
                } else {
                    ToastUtil.showToast(mContext, "添加失败 " + parseObject.getString("msg"));
                }
            }
        });

    }
}
