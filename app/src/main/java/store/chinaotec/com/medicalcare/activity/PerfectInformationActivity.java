package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.DateChoseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2018/3/9 0009.
 * 补充患者信息
 */

public class PerfectInformationActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.chose_sex_men)
    RadioButton choseSexMen;
    @Bind(R.id.chose_sex_women)
    RadioButton choseSexWomen;
    @Bind(R.id.tv_inquiry_choose_date)
    TextView tvInquiryChooseDate;
    @Bind(R.id.rl_inquiry_choose_birthday)
    RelativeLayout rlInquiryChooseBirthday;
    @Bind(R.id.tv_begin_inquiry)
    TextView tvBeginInquiry;
    @Bind(R.id.radio_chose_sex)
    RadioGroup radioChoseSex;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //初始化sp对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        ivTitleBack.setOnClickListener(this);
        tvBeginInquiry.setOnClickListener(this);
        rlInquiryChooseBirthday.setOnClickListener(this);
        radioChoseSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chose_sex_men:
                        sharedPreferences.edit().putInt("sex", 0).apply();
                        break;
                    case R.id.chose_sex_women:
                        sharedPreferences.edit().putInt("sex", 1).apply();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_inquiry_choose_birthday:
                DateChoseUtill.choseDate(this, new DateChoseUtill.GetChoseDateListener() {
                    @Override
                    public void getChoseDate(String date) {
                        //根据选中日期的年份,和当前的年份算出年龄
                        tvInquiryChooseDate.setText(date);
                        sharedPreferences.edit().putString("daishuage", date).apply();
                    }
                }, "yyyy-MM-dd");
                break;
            case R.id.tv_begin_inquiry:
                checkInfo();
                break;
        }
    }

    private void checkInfo() {
        String birthday = tvInquiryChooseDate.getText().toString();
        if (StringUtils.isEmpty(birthday)){
            BaseUtill.toastUtil("请选择出生日期");
            return;
        }
        int sex = sharedPreferences.getInt("sex",22);
        if (sex == 22){
            BaseUtill.toastUtil("请选择性别");
            return;
        }

        startActivity(new Intent(this,IntelligentInquiryActivity.class));
        finish();

    }

}
