package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.HashMap;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.DoctUserInfo;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2018/1/24 0024.
 * 修改用户信息
 */
public class UpDateUserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.tv_add_finish)
    TextView tvAddFinish;
    @Bind(R.id.et_info_input)
    EditText etInfoInput;
    @Bind(R.id.iv_info_clear)
    ImageView ivInfoClear;
    private DoctUserInfo.DataBean userInfo;
    private String title;
    private Map<String, String> mParam;
    private String sid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        userInfo = (DoctUserInfo.DataBean) bundle.getSerializable("userInfo");
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        mParam = new HashMap<>();
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        tvTitleName.setText(title);
        switch (title){
            case "用户名":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getNickName());
                }
                break;
            case "姓名":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getDoctorName());
                }
                break;
            case "身份证":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getIdentityCard());
                }
                break;
            case "专业职称":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getProfessionalTitle());
                }
                break;
            case "服务机构":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getFacilitatingAgency());
                }
                break;
            case "从业经历":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getWorkingExperience());
                }
                break;
            case "专科特长":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getJuniorStrong());
                }
                break;
            case "收款账户":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getBankInfo());
                }
                break;
        }
    }

    private void initListener() {
        ivTitleBack.setOnClickListener(this);
        tvAddFinish.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_add_finish:
                updateInfo();
                break;
        }
    }

    private void updateInfo() {
        String tempValue = etInfoInput.getText().toString();
        if (StringUtils.isEmpty(tempValue)) {
            ToastUtil.MyToast(this, "请输入" + title);
            return;
        }
        mParam.clear();
        mParam.put("sid",sid);
        switch (title) {
            case "用户名":
                mParam.put("nickName",tempValue);
                mParam.put("doctorName", "");
                mParam.put("facilitatingAgency", "");
                mParam.put("professionalTitle", "");
                mParam.put("identityCard", "");
                mParam.put("workingExperience","");
                mParam.put("juniorStrong","");
                break;
            case "姓名":
                mParam.put("doctorName", tempValue);
                mParam.put("facilitatingAgency", "");
                mParam.put("professionalTitle", "");
                mParam.put("identityCard", "");
                mParam.put("workingExperience","");
                mParam.put("juniorStrong","");
                break;
            case "身份证":
                if (tempValue.length() != 15 && tempValue.length() != 18) {
                    ToastUtil.MyToast(this, "请输入正确的身份证号码");
                    return;
                }
                mParam.put("doctorName", "");
                mParam.put("facilitatingAgency", "");
                mParam.put("professionalTitle", "");
                mParam.put("identityCard", tempValue);
                mParam.put("workingExperience","");
                mParam.put("juniorStrong","");
                break;
            case "专业职称":
                mParam.put("doctorName", "");
                mParam.put("facilitatingAgency", "");
                mParam.put("professionalTitle",tempValue);
                mParam.put("identityCard", "");
                mParam.put("workingExperience","");
                mParam.put("juniorStrong","");
                break;
            case "服务机构":
                mParam.put("doctorName", "");
                mParam.put("facilitatingAgency",tempValue);
                mParam.put("professionalTitle","");
                mParam.put("identityCard", "");
                mParam.put("workingExperience","");
                mParam.put("juniorStrong","");
                break;
            case "从业经历":
                mParam.put("doctorName", "");
                mParam.put("facilitatingAgency","");
                mParam.put("professionalTitle","");
                mParam.put("identityCard", "");
                mParam.put("workingExperience",tempValue);
                mParam.put("juniorStrong","");
                break;
            case "专科特长":
                mParam.put("doctorName", "");
                mParam.put("facilitatingAgency","");
                mParam.put("professionalTitle","");
                mParam.put("identityCard", "");
                mParam.put("workingExperience","");
                mParam.put("juniorStrong",tempValue);
                break;
        }

        requestUpdate();
    }

    private void requestUpdate() {
        OkHttpUtils.post().url(MyUrl.UPDATE_DOCTOR_INFO).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    ToastUtil.MyToast(UpDateUserInfoActivity.this, "修改成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }
}
