package store.chinaotec.com.medicalcare.fragment.regist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import store.chinaotec.com.medicalcare.activity.DoctRegNextActivity;
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
public class DoctorFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.doctor_regist_next)
    Button doctorRegistNext;
    @Bind(R.id.doct_regis_phone)
    EditText doctRegisPhone;
    @Bind(R.id.doct_regis_vercode)
    EditText doctRegisVercode;
    @Bind(R.id.doct_regis_name)
    EditText doctRegisName;
    @Bind(R.id.doct_regis_pasw)
    EditText doctRegisPasw;
    @Bind(R.id.doct_count_time)
    TimeView doctCountTime;

    private String localMacIP, mKey, verCode, regisName, regisPass;
    private String registPhone;

    public DoctorFragment() {
        // Required empty public constructor
    }

    public static DoctorFragment instance() {
        Bundle bundle = new Bundle();
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(bundle);
        return doctorFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        doctorRegistNext.setOnClickListener(this);
        doctCountTime.setOnClickListener(this);
        //获取当前系统mac地址
        localMacIP = BaseUtill.getMacAddres(MyApp.getContext());
    }

    @Override
    public void onClick(View v) {
        //获取注册账户手机号
        registPhone = doctRegisPhone.getText().toString();
        switch (v.getId()) {
            case R.id.doct_count_time:
                if (RegexUtils.isMobileExact(registPhone)) {
                    //获取短信验证码
                    doctGetVerCode(registPhone);
                } else {
                    BaseUtill.toastUtil("请输入正确格式的注册手机号");
                }
                break;
            case R.id.doctor_regist_next:
                if (!RegexUtils.isMobileExact(registPhone)) {
                    ToastUtil.showToast(getActivity(), "请输入正确格式的注册手机号");
                    return;
                }
                //获取收到的验证码
                verCode = doctRegisVercode.getText().toString();
                if (MyCommonUtil.isEmpty(verCode)) {
                    ToastUtil.showToast(getActivity(), "验证码不能为空");
                    return;
                }
                //获取设置的用户名
                regisName = doctRegisName.getText().toString();
                if (MyCommonUtil.isEmpty(regisName)) {
                    ToastUtil.showToast(getActivity(), "用户名不能为空");
                    return;
                }
                //获取设置的密码
                regisPass = doctRegisPasw.getText().toString();
                if (MyCommonUtil.isEmpty(regisPass)) {
                    ToastUtil.showToast(getActivity(), "密码不能为空");
                    return;
                }
                if (!MyCommonUtil.isEmpty(regisPass) && regisPass.length() < 6) {
                    ToastUtil.showToast(getActivity(), "请输入六位以上密码");
                    return;
                }
                MyLog.d("doctor_regist_next..收到验证码.." + verCode + "..设置用户名.." + regisName + "..设置密码.." + regisPass);
                //判断输入的验证码  用户名 密码是否为空
                if (!(TextUtils.isEmpty(verCode)) && !(TextUtils.isEmpty(regisName)) && !(TextUtils.isEmpty(regisPass))) {
                    //校验短信验证码,用户名等信息
                    doctCheckVerCode(registPhone, mKey, verCode);
                }
                break;
        }
    }

    /**
     * @param doctPhone 注册手机号
     * @param mKey      用户注册返回的key
     * @param verCode   注册收到的验证码
     *                  验证设置的用户名  验证码等信息
     */
    private void doctCheckVerCode(final String doctPhone, final String mKey, final String verCode) {
        NetWorkUtill.checkRegistCode(doctPhone, MyUrl.CHECK_REGIS, mKey, verCode, regisName, new NetWorkUtill.CheckRegisListener() {
            @Override
            public void getMsgCode(int code, String mes) {
                if (code == 500) {
                    ToastUtil.showToast(getContext(), mes);
                } else if (code == 0) {
                    //验证码等信息填写正确,进入下一步继续注册填写信息
                    RegistInfo registInfo = null;
                    registInfo = new RegistInfo(doctPhone, verCode, mKey, regisName, regisPass, localMacIP);
                    Intent intent = new Intent(MyApp.getContext(), DoctRegNextActivity.class);
                    intent.putExtra("RegistInfo", registInfo);
                    startActivity(intent);
                } else if (code == 300) {
                    ToastUtil.showToast(getContext(), mes);
                }
            }
        }, "医生用户注册");
    }

    /**
     * @param registPhone 用户注册手机号
     *                    根据注册手机号获取验证码 并启动倒计时1分钟
     */
    private void doctGetVerCode(String registPhone) {
        int isNet = NetUtil.getAPNType(getActivity());
        if (isNet != -1) {
            NetWorkUtill.allGetvercode(registPhone, localMacIP, MyUrl.REGIS_VERF_CODE, "医生用户注册", new NetWorkUtill.GetKeyListener() {
                @Override
                public void getBindKey(String key, int code) {
                    if (code == 0) {
                        doctCountTime.setTimes(60);
                        if (!doctCountTime.isRun()) {
                            doctCountTime.beginRun();
                        }
                        mKey = key;
                    } else if (code == 500) { //输入手机号码已经注册过
                        ToastUtil.showToast(getContext(), "该手机号已经注册,请你直接登录");
                    }
                }
            });
        }else {
            BaseUtill.toastUtil("请检查您的网络");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
/*
    class MyDoctorText implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            doctPhone = s.toString();
            doctCountTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取短信验证码并判断校验
                    doctGetVerCode();
                }
            });
        }
    }*/
}
