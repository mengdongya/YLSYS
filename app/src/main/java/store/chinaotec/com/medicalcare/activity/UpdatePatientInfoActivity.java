package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import store.chinaotec.com.medicalcare.javabean.NormalUserInfo;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by seven on 2018/1/25 0025.
 * 修改病人信息
 */
public class UpdatePatientInfoActivity extends AppCompatActivity implements View.OnClickListener {
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
    @Bind(R.id.tv_info_man)
    TextView infoMan;
    @Bind(R.id.tv_info_woman)
    TextView tvInfoWoman;
    @Bind(R.id.ll_info_sex)
    LinearLayout llInfoSex;
    @Bind(R.id.info_data)
    LinearLayout infoData;
    private String title;
    private Map<String, String> mParam;
    private String sid;
    private NormalUserInfo.DataBean userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        userInfo = (NormalUserInfo.DataBean) bundle.getSerializable("patientInfo");
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

        if ("性别".equals(title)){
            llInfoSex.setVisibility(View.VISIBLE);
            infoData.setVisibility(View.GONE);
            tvAddFinish.setVisibility(View.GONE);
        }else {
            llInfoSex.setVisibility(View.GONE);
            infoData.setVisibility(View.VISIBLE);
            tvAddFinish.setVisibility(View.VISIBLE);
        }

        switch (title) {
            case "用户名":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getNickName());
                }
                break;
            case "姓名":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getName());
                }
                break;
            case "年龄":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getAge());
                }
                break;
            case "民族":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getNation());
                }
                break;
            case "职业":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getOccupation());
                }
                break;
            case "常住地":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getDomicile());
                }
                break;
            case "身高":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getHeight());
                }
                break;
            case "体重":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getWeight());
                }
                break;
            case "联系方式":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getContactType());
                }
                break;
            case "身份证":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getIdentityCard());
                }
                break;
            case "社保卡":
                etInfoInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                if (userInfo != null) {
                    etInfoInput.setHint(userInfo.getSocialInsuranceCard());
                }
                break;
        }
    }

    private void initListener() {
        ivTitleBack.setOnClickListener(this);
        tvAddFinish.setOnClickListener(this);
        infoMan.setOnClickListener(this);
        tvInfoWoman.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        mParam.clear();
        mParam.put("sid", sid);
        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_add_finish:
                updateInfo();
                break;
            case R.id.tv_info_man:
                mParam.put("sex", "0");
                requestUpdate();
                break;
            case R.id.tv_info_woman:
                mParam.put("sex", "1");
                requestUpdate();
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
        mParam.put("sid", sid);
        switch (title) {
            case "用户名":
                mParam.put("nickName", tempValue);
                break;
            case "姓名":
                mParam.put("name", tempValue);
                break;
            case "年龄":
                mParam.put("age", tempValue);
                break;
            case "民族":
                mParam.put("nation", tempValue);
                break;
            case "职业":
                mParam.put("occupation", tempValue);
                break;
            case "常住地":
                mParam.put("domicile", tempValue);
                break;
            case "身高":
                mParam.put("height", tempValue);
                break;
            case "体重":
                mParam.put("weight", tempValue);
                break;
            case "身份证":
                if (tempValue.length() != 15 && tempValue.length() != 18) {
                    ToastUtil.MyToast(this, "请输入正确的身份证号码");
                    return;
                }
                mParam.put("identityCard", tempValue);
                break;
            case "社保卡":
                mParam.put("socialInsuranceCard", tempValue);
                break;
        }

        requestUpdate();
    }

    private void requestUpdate() {
        OkHttpUtils.post().url(MyUrl.UPDATE_USER_INFO).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    ToastUtil.MyToast(UpdatePatientInfoActivity.this, "修改成功");
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
