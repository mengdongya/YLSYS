package store.chinaotec.com.medicalcare.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.LoginMess;
import store.chinaotec.com.medicalcare.javabean.RegistInfo;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ModueUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;

public class UserRegNextActivity extends BaseActivity implements View.OnClickListener {
    private final int DATE_DIALOG = 1;
    @Bind(R.id.relative_connect_inform)
    RelativeLayout relativeConnectInform;
    @Bind(R.id.connect_inform)
    TextView connectInform;
    @Bind(R.id.born_date)
    TextView bornDate;
    @Bind(R.id.relatiuve_born_date)
    RelativeLayout relatiuveBornDate;
    @Bind(R.id.user_age)
    TextView userAge;
    @Bind(R.id.relative_sex)
    RelativeLayout relativeSex;
    @Bind(R.id.sex_content)
    TextView sexContent;
    @Bind(R.id.user_regist)
    Button userRegist;
    @Bind(R.id.inout_name)
    EditText inoutName;
    @Bind(R.id.input_nation)
    EditText inputNation;
    @Bind(R.id.input_career)
    EditText inputCareer;
    @Bind(R.id.input_tolive)
    EditText inputTolive;
    @Bind(R.id.input_height)
    EditText inputHeight;
    @Bind(R.id.input_weight)
    EditText inputWeight;
    @Bind(R.id.connect_title)
    TextView connectTitle;
    @Bind(R.id.input_ID_card)
    EditText inputIDCard;
    @Bind(R.id.input_socia_card)
    EditText inputSociaCard;
    //普通用户
    int NORMAL_USER = 111;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    //sex_chose为0表示选中性别为男,sex_chose为1表示选中性别为男
    private int sex_chose;
    //联系方式的种类 1-电话、2-微信、3-邮件
    private int connectType;
    private RegistInfo registInfo;
    //注册用户联系信息
    private String contactInfo;
    private TextView tv_include_title_view;
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            display();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regist_next);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("完善资料");
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        relativeConnectInform.setOnClickListener(this);
        relatiuveBornDate.setOnClickListener(this);
        relativeSex.setOnClickListener(this);
        userRegist.setOnClickListener(this);
        //初始化获取当前的年 月 日 小时 分钟
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        //获取第一步注册填写的信息
        Intent intent = getIntent();
        registInfo = (RegistInfo) intent.getSerializableExtra("RegistInfo");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_connect_inform:
                startActivityForResult(new Intent(this, ContactChoseActivity.class), 10);
                break;
            //选择出生日期
            case R.id.relatiuve_born_date:
                showDialog(DATE_DIALOG);
                break;
            case R.id.relative_sex:
                choseSex();
                break;
            //提交注册填写的资料信息验证
            case R.id.user_regist:
                registData();
                break;
        }
    }

    private void registData() {
        //mac地址
        final String macAddres = registInfo.getMacAddres();
        //注册手机号
        final String phone = registInfo.getPhone();
        //注册时收到的验证码
        String smsCode = registInfo.getSmsCode();
        //注册收到验证码时,同时收到的key
        String bindKey = registInfo.getBindKey();
        //注册时设置的用户名
        final String nickName = registInfo.getNickName();
        //注册时设置的密码
        final String nickPasword = registInfo.getNickPasword();
        //注册人的姓名;
        String name = inoutName.getText().toString();
        //注册人性别
        String mSex = sex_chose + "";
        //注册人年龄
        String age = userAge.getText().toString();
        //注册人出生年月日
        String birthDay = bornDate.getText().toString();
        //注册人民族
        String nation = inputNation.getText().toString();
        //注册人职业
        String occupation = inputCareer.getText().toString();
        //注册人常住地
        String domicile = inputTolive.getText().toString();
        //注册人身高
        String height = inputHeight.getText().toString();
        //注册人体重
        String weight = inputWeight.getText().toString();
        //注册人身份证号
        String IdCard = inputIDCard.getText().toString();
        //注册人社保卡号
        String socialCard = inputSociaCard.getText().toString();
        if ((!(TextUtils.isEmpty(name))) && (!(TextUtils.isEmpty(nation))) && (!(TextUtils.isEmpty(occupation))) &&
                (!(TextUtils.isEmpty(domicile))) && (!(TextUtils.isEmpty(height))) && (!(TextUtils.isEmpty(weight)))) {
            //提交注册信息并校验
            NetWorkUtill.userRegistEndSure(phone, smsCode, bindKey, nickName, nickPasword, name, sex_chose, age, birthDay, nation, occupation, domicile, height, weight, connectType, socialCard, IdCard, contactInfo, new NetWorkUtill.UserSureListener() {
                @Override
                public void userSureEnd() {
                    //注册成功后登录当前用户,并跳转到首页
                    NetWorkUtill.userLogin(phone, nickPasword, macAddres, "", new NetWorkUtill.LoginListener() {
                        @Override
                        public void login(LoginMess.DataBean dataBean, int code) {
                            //保存登录后的用户信息等
                            ModueUtill.saveLoginUserInfo(MyApp.getContext(), dataBean);
                            //跳转到首页显示登录状态
                            Intent intent = new Intent(UserRegNextActivity.this, HomeActivity.class);
                            intent.putExtra("normalUser", NORMAL_USER);
                            startActivity(intent);
                        }
                    });
                }
            });
        } else {
            BaseUtill.toastUtil("请补充输入必填项注册信息");
        }
    }

    private void choseSex() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置弹窗大小
        attributes.width = 400;
        attributes.height = 400;
        window.setAttributes(attributes);
        //设置弹窗位置
        attributes.x = 100;
        attributes.y = -300;
        alertDialog.onWindowAttributesChanged(attributes);
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_sex, null);
        RadioGroup sexRadiogrop = (RadioGroup) inflate.findViewById(R.id.sex_radiogrop);
        //选择性别男 女
        sexRadiogrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.sex_men_chose:
                        alertDialog.dismiss();
                        sexContent.setText("男");
                        sex_chose = 0;
                        break;
                    case R.id.sex_women_chose:
                        alertDialog.dismiss();
                        sexContent.setText("女");
                        sex_chose = 1;
                        break;
                }
            }
        });
        alertDialog.setContentView(inflate);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                String contact = data.getStringExtra("connect");
                connectType = data.getIntExtra("type", 6);
                contactInfo = contact;
                connectInform.setText(contact);
            }
        }
    }

    /**
     * 显示选中的出生年月日,计算当前年龄
     */
    public void display() {
        bornDate.setText(new StringBuffer().append(mYear).append("-").append(mMonth+1).append("-").append(mDay));
        userAge.setText((calendar.get(Calendar.YEAR) - mYear) + "");
    }
}
