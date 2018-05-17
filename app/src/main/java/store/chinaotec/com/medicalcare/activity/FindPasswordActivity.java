package store.chinaotec.com.medicalcare.activity;

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
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.view.TimeView;

/**
 * 忘记密码页面
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.sure_password)
    Button surePassword;
    @Bind(R.id.findpas_get_vercode)
    TimeView findpasGetVercode;
    @Bind(R.id.findpas_phone)
    EditText findpasPhone;
    @Bind(R.id.findpas_vercode)
    EditText findpasVercode;
    @Bind(R.id.new_password)
    EditText newPassword;

    private String localMacIP;
    private String regisPhone;
    //设置新密码获取短信验证码的同时获取到一个key值
    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        surePassword.setOnClickListener(this);
        findpasGetVercode.setOnClickListener(this);
        //获取mac地址
        localMacIP = BaseUtill.getMacAddres(MyApp.getContext());
        findpasPhone.addTextChangedListener(new MyUserText());
    }

    private void findGetVerCode(String userPhone) {
        NetWorkUtill.allGetvercode(userPhone, localMacIP, MyUrl.FORGOT_VERF_CODE, "忘记密码时", new NetWorkUtill.GetKeyListener() {
            @Override
            public void getBindKey(String key, int code) {
                if (code == 0) {
                    findpasGetVercode.setTimes(60);
                    if (!findpasGetVercode.isRun()) {
                        findpasGetVercode.beginRun();
                    }
                    mKey = key;
                } else if (code == 500) { //输入手机号码已经注册过
                    BaseUtill.toastUtil("注册电话号码输入错误,请重新输入!");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //获取手机验证码
            case R.id.findpas_get_vercode:
                BaseUtill.toastUtil("请输入注册电话号码");
                break;
            //忘记密码,重新设置密码后验证密码
            case R.id.sure_password:
                //获取验证码
                String smsCode = findpasVercode.getText().toString();
                //获取新密码
                String pass = newPassword.getText().toString();
                NetWorkUtill.findPasWordCheck(regisPhone, mKey, pass, smsCode, "设置新密码", new NetWorkUtill.SetPassListener() {
                    @Override
                    public void setPass(int code) {
                        finish();
                    }
                });
                break;
        }
    }

    class MyUserText implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            //获取当前输入的注册手机号
            regisPhone = s.toString();
            findpasGetVercode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取手机验证码
                    findGetVerCode(regisPhone);
                }
            });
        }
    }
}
