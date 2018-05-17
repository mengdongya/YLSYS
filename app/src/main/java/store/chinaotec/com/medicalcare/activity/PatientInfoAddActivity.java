package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.Calendar;
import java.util.HashMap;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.ChronicDiseaseBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.DateChoseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.ToastUtil;

/**
 * 慢性病模块修改病人个人信息页面
 */
public class PatientInfoAddActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.slow_patient_name)
    EditText slowPatientName;
    @Bind(R.id.slow_patient_age)
    TextView slowPatientAge;
    @Bind(R.id.linear_patient_age)
    LinearLayout linearPatientAge;
    @Bind(R.id.patient_sex_group)
    RadioGroup patientSexGroup;
    @Bind(R.id.slow_patient_illtime)
    TextView slowPatientIlltime;
    @Bind(R.id.linear_patient_illtime)
    LinearLayout linearPatientIlltime;
    @Bind(R.id.slow_take_medicine)
    EditText slowTakeMedicine;
    @Bind(R.id.comit_patient_info)
    Button comitPatientInfo;
    @Bind(R.id.patient_men)
    RadioButton patientMen;
    @Bind(R.id.patient_women)
    RadioButton patientWomen;
    private int sex = 0;
    private ChronicDiseaseBean.DataBean.ChronicPatientDto chronicPatientDto;
    private String patientId;
    private HashMap<String, String> mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_add);
        ButterKnife.bind(this);
        initBaseData();
        initListener();
    }

    private void initBaseData() {
        Bundle bundle = getIntent().getExtras();
        chronicPatientDto = (ChronicDiseaseBean.DataBean.ChronicPatientDto) bundle.getSerializable("chronicPatientDto");
        patientId = chronicPatientDto.getPatientDtos().get(0).getPatientId();
        ChronicDiseaseBean.DataBean.ChronicPatientDto.PatientDto patientDto = chronicPatientDto.getPatientDtos().get(0);
        mParams = new HashMap<>();
        //修改病人信息时默认显示信息
        if (!(TextUtils.isEmpty(patientDto.getName())) || !(TextUtils.isEmpty(patientDto.getStarTime())) ||
                !(TextUtils.isEmpty(patientDto.getMedicine1()))) {
            slowPatientName.setText(patientDto.getName());
            slowPatientIlltime.setText(patientDto.getStarTime());
            slowTakeMedicine.setText(patientDto.getMedicine1());

        }
        if ("null".equals(patientDto.getAge())){
            slowPatientAge.setText("");
        }else {
            slowPatientAge.setText(patientDto.getAge());
        }
        if (!"null".equals(patientDto.getSex())) {
            if ("1".equals(String.valueOf(patientDto.getSex()))) {  //性别为 男
                patientMen.setChecked(true);
                sex = 1;
            } else if ("2".equals(String.valueOf(patientDto.getSex()))) { //性别为女
                patientWomen.setChecked(true);
                sex = 2;
            }
        }
    }

    private void initListener() {
        linearPatientAge.setOnClickListener(this);
        linearPatientIlltime.setOnClickListener(this);
        comitPatientInfo.setOnClickListener(this);

        patientSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.patient_men:
                        sex = 1;
                        break;
                    case R.id.patient_women:
                        sex = 2;
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //年龄
            case R.id.linear_patient_age:
                DateChoseUtill.choseDate(this, new DateChoseUtill.GetChoseDateListener() {
                    @Override
                    public void getChoseDate(String date) {
                        //根据选中日期的年份,和当前的年份算出年龄
                        int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(date.substring(0, 4));
                        slowPatientAge.setText(age + "");
                    }
                }, "yyyy-MM-dd");
                break;
            //起病时间
            case R.id.linear_patient_illtime:
                DateChoseUtill.choseDate(this, new DateChoseUtill.GetChoseDateListener() {
                    @Override
                    public void getChoseDate(String date) {
                        slowPatientIlltime.setText(date);
                        MyLog.d("getChoseDate..当前选中时间.." + date);
                    }
                }, "yyyy-MM-dd HH:mm:ss");
                break;
            //提交
            case R.id.comit_patient_info:
                commit();
                break;
        }
    }

    private void commit() {
        String name = slowPatientName.getText().toString().trim();
        String age = slowPatientAge.getText().toString().trim();
        String startTime = slowPatientIlltime.getText().toString().trim();
        String medical = slowTakeMedicine.getText().toString().trim();
        if (StringUtils.isEmpty(name)){
            BaseUtill.toastUtil("请输入姓名");
            return;
        }
        if (StringUtils.isEmpty(age)){
            BaseUtill.toastUtil("请选择年龄");
            return;
        }
        if(sex == 0){
            BaseUtill.toastUtil("请选择性别");
            return;
        }
        if (StringUtils.isEmpty(startTime)){
            BaseUtill.toastUtil("请选择起病时间");
            return;
        }

        mParams.clear();
        mParams.put("patientId",patientId);
        mParams.put("name",name);
        mParams.put("sex",sex+"");
        mParams.put("age",age);
        mParams.put("medicine1",medical);
        mParams.put("starTime",startTime);

        OkHttpUtils.post().url(MyUrl.edit_patient_info).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean bean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(bean.responseCode)){
                    Intent intent = new Intent(ResourseSum.REFRESH_HEALTH_FRAGMENT);
                    sendBroadcast(intent);
//                    ToastUtil.showToast(PatientInfoAddActivity.this, "请求成功!");
                    finish();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }


}
