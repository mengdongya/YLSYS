package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.simple.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.DoctUserInfo;
import store.chinaotec.com.medicalcare.javabean.LoginMess;
import store.chinaotec.com.medicalcare.javabean.NormalUserInfo;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.EventTag;
import store.chinaotec.com.medicalcare.utill.ModueUtill;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 用户登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.regist)
    TextView regist;
    @Bind(R.id.login_id)
    EditText loginId;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.find_password)
    Button findPassword;
    @Bind(R.id.login)
    Button login;


    //用户登陆成功后获取到的id
    private String signId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_login);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        regist.setOnClickListener(this);
        findPassword.setOnClickListener(this);
        login.setOnClickListener(this);
        //获取帐号和密码
        String loginName = SpUtill.getString(this, ResourseSum.Medica_SP, "loginName");
        String loginPasw = SpUtill.getString(this, ResourseSum.Medica_SP, "loginPasw");
        //默认显示帐号和密码
        if ((!(TextUtils.isEmpty(loginName))) && (!(TextUtils.isEmpty(loginPasw)))) {
            loginId.setText(loginName);
            loginPassword.setText(loginPasw);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist:
                startActivity(new Intent(this, UserRegistActivity.class));
                break;
            case R.id.find_password:
                startActivity(new Intent(MyApp.getContext(), FindPasswordActivity.class));
                break;
            case R.id.login:
                //获取登录账户
                final String loginName = loginId.getText().toString();
                //获取登录密码
                String loginPasw = loginPassword.getText().toString();
                //获取mac地址
                String macIp = BaseUtill.getMacAddres(MyApp.getContext());
                String registId = getSharedPreferences("jpush", Context.MODE_PRIVATE).getString("regid", "");
                //登陆时,判断登录用户名手机号和密码是否为空,并且手机号必须为11位
                if ((!(TextUtils.isEmpty(loginName))) && (!(TextUtils.isEmpty(loginPasw))) && loginName.length() == 11) {
                    //第二次登录同一个账户时,不用再次输入账户密码
                    SpUtill.putString(this, ResourseSum.Medica_SP, "loginName", loginName);
                    SpUtill.putString(this, ResourseSum.Medica_SP, "loginPasw", loginPasw);
                    MyLog.d("用户名字.." + loginName + "..密码.." + loginPasw + "..mac地址.." + macIp);

                    //开始登陆
                    NetWorkUtill.userLogin(loginName, loginPasw, macIp, registId, new NetWorkUtill.LoginListener() {
                        @Override
                        public void login(LoginMess.DataBean dataBean, int code) {
                            if (code == 0) {
                                //用户登陆成功后保存对应的信息
                                ModueUtill.saveLoginUserInfo(MyApp.getContext(), dataBean);
                                //调用获取用户信息的接口
                                int memberType = dataBean.getMemberType();
                                if (memberType == 1) { //当前登录用户为普通用户
                                    saveNormalUserAge(dataBean);
                                } else if (memberType == 2) {//当前登录用户为医生用户
                                    saveDoctorUserAge(dataBean);
                                }
                                EventBus.getDefault().post(0, EventTag.LOGIN);
                                System.out.print("sid ==== " + dataBean.getSid());
                                //退出登录页面之前先隐藏软键盘
                                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().
                                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                setResult(RESULT_OK);
                                //登陆成功后退出登录页面
                                finish();
                            } else if (code == 500) {
                                BaseUtill.toastUtil("你输入的账户或者密码有错误");
                            }
                        }
                    });
                } else {
                    BaseUtill.toastUtil("请检查你的帐号和密码");
                }
                //登录状态
                SpUtill.putBoolen(this, ResourseSum.Medica_SP, "login", true);
                //登录操作的标志
                SpUtill.putBoolen(this, ResourseSum.Medica_SP, "clickLogin", true);
                break;
        }
    }

    /**
     * @param dataBean 包含医生用户信息的java对象
     *                 保存医生用户的年龄
     */
    private void saveDoctorUserAge(LoginMess.DataBean dataBean) {
        NetWorkUtill.getDoctInfo(MyUrl.DOCTOR_INFO, dataBean.getSid(), new NetWorkUtill.DoctInfoListener() {
            @Override
            public void getDoctorInfo(DoctUserInfo.DataBean dataBean) {
                //用户登陆成功后保存用户的年龄
//                                            sharedPreferences.edit().putString("sid", dataBean.).apply();
            }
        });
    }

    /**
     * @param dataBean 包含普通用户信息的java对象
     *                 保存普通用户的
     */
    private void saveNormalUserAge(LoginMess.DataBean dataBean) {
        NetWorkUtill.getUserInfo(MyUrl.USER_INFO, dataBean.getSid(), new NetWorkUtill.UserInfoListener() {
            @Override
            public void getUserInfo(NormalUserInfo.DataBean data) {
                //用户登陆成功后保存用户的年龄  性别一次保存
//                sharedPreferences.edit().putString("age", data.getAge()).apply();
//                sharedPreferences.edit().putInt("sex", data.getSex()).apply();
                //用户登陆成功后保存用户的年龄  性别二次保存
                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "saveAge", data.getAge());
                SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "saveSex", data.getSex());
                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "userPhone", data.getTelephone());
            }
        });
    }
}
