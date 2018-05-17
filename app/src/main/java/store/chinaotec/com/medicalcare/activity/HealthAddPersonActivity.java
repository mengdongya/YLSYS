package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HealthControlBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.DateChoseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2017/12/27 0027.
 * 添加健康管理人员
 */
public class HealthAddPersonActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.slow_patient_name)
    EditText slowPatientName;
    @Bind(R.id.slow_patient_age)
    TextView slowPatientAge;
    @Bind(R.id.linear_patient_age)
    LinearLayout linearPatientAge;
    @Bind(R.id.patient_men)
    RadioButton patientMen;
    @Bind(R.id.patient_women)
    RadioButton patientWomen;
    @Bind(R.id.patient_sex_group)
    RadioGroup patientSexGroup;
    @Bind(R.id.slow_patient_height)
    EditText slowPatientHeight;
    @Bind(R.id.slow_patient_weight)
    EditText slowPatientWeight;
    @Bind(R.id.commit_patient_info)
    Button commitPatientInfo;
    private int sex = 0;
    private HashMap<String, String> mParams;
    private String sid;
    private HealthControlBean.DataBean.PatientDtosBean patientDtosBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ResourseSum.TURN_TO_UPDATE == 1){
            Bundle bundle = getIntent().getExtras();
            patientDtosBean = (HealthControlBean.DataBean.PatientDtosBean)bundle.getSerializable("patientDtosBean");
        }

        setContentView(R.layout.activity_health_add_person);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        mParams = new HashMap<>();
        if (ResourseSum.TURN_TO_UPDATE == 1){
            slowPatientName.setText(patientDtosBean.getName());
            slowPatientAge.setText(patientDtosBean.getAge()+"");

            if (patientDtosBean.getSex() == 1) {  //性别为 男
                patientMen.setChecked(true);
                sex = 1;
            } else if (patientDtosBean.getSex() == 2) { //性别为女
                patientWomen.setChecked(true);
                sex = 2;
            }

            slowPatientHeight.setText(patientDtosBean.getHeight());
            slowPatientWeight.setText(patientDtosBean.getWeight());

        }
    }

    private void initListener() {
        ivTitleBack.setOnClickListener(this);
        linearPatientAge.setOnClickListener(this);
        commitPatientInfo.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
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
            case R.id.commit_patient_info:
                commitInfo();
                break;
        }
    }

    private void commitInfo() {
        //获取病人名字
        String patientName = slowPatientName.getText().toString();
        //获取病人年龄
        String patientAge = slowPatientAge.getText().toString();

        String height = slowPatientHeight.getText().toString();
        String weight = slowPatientWeight.getText().toString();

        if ("".equals(patientName)){
            ToastUtil.MyToast(this,"请输入姓名");
            return;
        }
        if ("".equals(patientAge)){
            ToastUtil.MyToast(this,"请选择年龄");
            return;
        }
        if (sex == 0){
            ToastUtil.MyToast(this,"请选择性别");
            return;
        }
        if ("".equals(height)){
            ToastUtil.MyToast(this,"请输入身高");
            return;
        }
        if ("".equals(weight)){
            ToastUtil.MyToast(this,"请输入体重");
            return;
        }

        mParams.clear();
        mParams.put("sid",sid);
        mParams.put("isOwn","0");
        mParams.put("name",patientName);
        mParams.put("sex",sex+"");
        mParams.put("age",patientAge);
        mParams.put("height",height);
        mParams.put("weight",weight);

        String url;
        if (ResourseSum.TURN_TO_UPDATE == 1){
            mParams.put("patientId",patientDtosBean.getPatientId());
            url = MyUrl.HEALTH_UPDATE_PERSON;
        }else {
            url = MyUrl.HEALTH_ADD_PERSON;
        }

        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean result = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(result.responseCode)){
                    if (ResourseSum.TURN_TO_UPDATE == 1){
                        Intent intent = new Intent(ResourseSum.REFRESH_HEALTH_FRAGMENT);
                        sendBroadcast(intent);
                    }
                    setResult(RESULT_OK);
                    finish();
                }else {
                    ToastUtil.MyToast(HealthAddPersonActivity.this,result.msg);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ResourseSum.TURN_TO_UPDATE = 0;
    }
}
