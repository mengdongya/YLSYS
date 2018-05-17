package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.RegexUtils;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.TimeView;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bind_phone)
    EditText bindPhone;
    @Bind(R.id.bind_vercode)
    EditText bindVercode;
    @Bind(R.id.bind_time)
    TimeView bindTime;
    @Bind(R.id.bind_sure)
    Button bindSure;

    private String usersid;
    private String mac;
    private String key;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        bindTime.setOnClickListener(this);
        bindSure.setOnClickListener(this);
        //获取用户的sid值
        usersid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        //获取mac地址
        mac = BaseUtill.getMacAddres(BindPhoneActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_time:
                getCheckCode();
                break;
            case R.id.bind_sure:
                String smscode = bindVercode.getText().toString();
                NetWorkUtill.sureChangePhone(MyUrl.CHANGE_PHONE, usersid, phone, smscode, key);
                //回传更改后的电话
                String usePhone = bindPhone.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("changePhone", usePhone);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void getCheckCode() {
        phone = bindPhone.getText().toString().trim();
        if (RegexUtils.isMobileExact(phone)){
            BaseUtill.toastUtil("请输入正确格式的手机号");
            return;
        }

        changePhoneCode(phone, usersid, mac);
    }

    private void changePhoneCode(String phone, String usersid, String mac) {
        NetWorkUtill.changePhone(MyUrl.CHANGE_PHONE_CODE, usersid, phone, mac, new NetWorkUtill.ChangePhoneListenr() {
            @Override
            public void changePhone(String data) {
                key = data;
                bindTime.setTimes(60);
                if (!bindTime.isRun()) {
                    bindTime.beginRun();
                }
            }
        });
    }

}
