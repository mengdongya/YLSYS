package store.chinaotec.com.medicalcare.fragment.regist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.UserRegNextActivity;
import store.chinaotec.com.medicalcare.javabean.RegistInfo;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.NetUtil;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.RegexUtils;
import store.chinaotec.com.medicalcare.utill.ToastUtil;
import store.chinaotec.com.medicalcare.view.TimeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.user_regist_next)
    Button userRegistNext;
    @Bind(R.id.user_regis_phone)
    EditText userRegisPhone;
    @Bind(R.id.user_regis_vercode)
    EditText userRegisVercode;
    @Bind(R.id.user_regis_name)
    EditText userRegisName;
    @Bind(R.id.user_regis_pasw)
    EditText userRegisPasw;
    @Bind(R.id.user_count_time)
    TimeView userCountTime;

    private String localMacIP, regisName, regisPass, verCode, mKey;
    private String registPhone;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment instance() {
        Bundle bundle = new Bundle();
        UserFragment userFragment = new UserFragment();
        userFragment.setArguments(bundle);
        return userFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal_user, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        userRegistNext.setOnClickListener(this);
        userCountTime.setOnClickListener(this);
        //获取当前系统mac地址
        localMacIP = BaseUtill.getMacAddres(MyApp.getContext());
    }

    @Override
    public void onClick(View v) {
        //获取用户注册手机号
        registPhone = userRegisPhone.getText().toString();
        switch (v.getId()) {
            case R.id.user_count_time:
                if (RegexUtils.isMobileExact(registPhone)) {
                    //获取手机验证码并判断
                    userGetVerCode(registPhone);
                } else {
                    BaseUtill.toastUtil("请输入正确格式的注册手机号");
                }
                break;
            //注册通过验证码和收到的key进行确认
            case R.id.user_regist_next:
                if (!RegexUtils.isMobileExact(registPhone)) {
                    ToastUtil.showToast(getActivity(), "请输入正确格式的注册手机号");
                    return;
                }
                //获取收到的验证码
                verCode = userRegisVercode.getText().toString();
                if (MyCommonUtil.isEmpty(verCode)) {
                    ToastUtil.showToast(getActivity(), "验证码不能为空");
                    return;
                }
                //获取设置的用户名
                regisName = userRegisName.getText().toString();
                if (MyCommonUtil.isEmpty(regisName)) {
                    ToastUtil.showToast(getActivity(), "用户名不能为空");
                    return;
                }
                //获取设置的密码
                regisPass = userRegisPasw.getText().toString();
                if (MyCommonUtil.isEmpty(regisPass)) {
                    ToastUtil.showToast(getActivity(), "密码不能为空");
                    return;
                }
                if (!MyCommonUtil.isEmpty(regisPass) && regisPass.length() < 6) {
                    ToastUtil.showToast(getActivity(), "请输入六位以上密码");
                    return;
                }
                //校验短信验证码,设置用户名等信息
                userCheckVerCode(registPhone, mKey, verCode, regisName);
                MyLog.d("onClick..短信验证码...smsCode.." + verCode + "..用户名..nickName.." + regisName + ".smsCodeBindingKey..." + mKey);
                break;
        }
    }

    private void userCheckVerCode(final String phone, final String mKey, final String verCode, final String regisName) {
        NetWorkUtill.checkRegistCode(phone, MyUrl.CHECK_REGIS, mKey, verCode, regisName, new NetWorkUtill.CheckRegisListener() {
            @Override
            public void getMsgCode(int code, String mes) {
                if (code == 500) {
                    BaseUtill.toastUtil(mes);
                } else if (code == 0) {
                    //验证码等信息填写正确,进入下一步继续注册填写信息
                    RegistInfo registInfo = null;
                    registInfo = new RegistInfo(phone, verCode, mKey, regisName, regisPass, localMacIP);
                    Intent intent = new Intent(MyApp.getContext(), UserRegNextActivity.class);
                    intent.putExtra("RegistInfo", registInfo);
                    startActivity(intent);
                } else if (code == 300) {
                    ToastUtil.showToast(getContext(), mes);
                }
            }
        }, "普通用户注册");
    }

    private void userGetVerCode(String userPhone) {

        int isNet = NetUtil.getAPNType(getActivity());
        if (isNet != -1) {
            NetWorkUtill.allGetvercode(userPhone, localMacIP, MyUrl.REGIS_VERF_CODE, "普通用户注册", new NetWorkUtill.GetKeyListener() {
                @Override
                public void getBindKey(String key, int code) {
                    if (code == 0) {
                        startCountDown();
                        //保存获取到的key值
                        mKey = key;
                    } else if (code == 500) {//输入手机号码已经注册过
                        BaseUtill.toastUtil("该手机号已经注册,请你直接登录");
                    }
                }
            });
        }else {
            BaseUtill.toastUtil("请检查您的网络");
        }
    }

    /**
     * 开始一分钟倒计时
     */
    private void startCountDown() {
        userCountTime.setTimes(60);
        if (!userCountTime.isRun()) {
            userCountTime.beginRun();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
