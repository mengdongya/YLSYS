package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.BankInputInfo;
import store.chinaotec.com.medicalcare.javabean.RegistInfo;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;

/**
 * 医生注册下一步填写详细信息页面
 */
public class DoctRegNextActivity extends BaseActivity implements View.OnClickListener {

    private final int BUSINES_WORK_CODE = 20;
    private final int SPECIA_EXPER_CODE = 30;
    private final int ACUNT_CODE = 40;
    @Bind(R.id.doctor_regist_back)
    ImageView doctorRegistBack;
    @Bind(R.id.work_experience)
    TextView workExperience;
    @Bind(R.id.relative_work)
    RelativeLayout relativeWork;
    @Bind(R.id.specia_expert)
    TextView speciaExpert;
    @Bind(R.id.relative_specia)
    RelativeLayout relativeSpecia;
    @Bind(R.id.relative_account)
    RelativeLayout relativeAccount;
    @Bind(R.id.doctor_name)
    EditText doctorName;
    @Bind(R.id.id_card_number)
    EditText idCardNumber;
    @Bind(R.id.profession_title)
    EditText professionTitle;
    @Bind(R.id.service_organt)
    EditText serviceOrgant;
    @Bind(R.id.works)
    TextView works;
    @Bind(R.id.specia)
    TextView specia;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.doctor_regist)
    Button doctorRegist;
    @Bind(R.id.receive_accunt)
    TextView receiveAccunt;
    //收款账户的拥有人
    private String holdName;
    //银行卡开户支行名字
    private String branchName;
    //银行卡开户支行
    private String bankName;
    //支付方式 1-支付宝，2-微信，3-银联
    private int payType;
    private int DOCTOR_USER = 222;
    private String phone, smsCode, bindKey, nickName, nickPasword, macAddres, docName, idCard, profesTitle,
            serviceGrop, workExper, speciaExper, banckCode, banckCard, aliPayAcunt, microAcunt, acunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_regist_next);
        ButterKnife.bind(this);
        initListener(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("docName", docName);
        outState.putString("idCard", idCard);
        outState.putString("profesTitle", profesTitle);
        outState.putString("serviceGrop", serviceGrop);
        outState.putString("workExper", workExper);
        outState.putString("speciaExper", speciaExper);
    }

    private void initListener(Bundle savedInstanceState) {
        relativeWork.setOnClickListener(this);
        relativeSpecia.setOnClickListener(this);
        relativeAccount.setOnClickListener(this);
        doctorRegistBack.setOnClickListener(this);
        doctorRegist.setOnClickListener(this);

        Intent intent = getIntent();
        RegistInfo registInfo = (RegistInfo) intent.getSerializableExtra("RegistInfo");
        //注册手机号
        phone = registInfo.getPhone();
        //手机验证码
        smsCode = registInfo.getSmsCode();
        //后台获取的key
        bindKey = registInfo.getBindKey();
        //用户名
        nickName = registInfo.getNickName();
        //设置的密码
        nickPasword = registInfo.getNickPasword();
        //获取mac地址
        macAddres = registInfo.getMacAddres();

        //当前活动被回收后,再次回到当前页面并显示原来的数据
        if (savedInstanceState != null) {
            String docName = savedInstanceState.getString("docName");
            doctorName.setText(docName);
            String idCard = savedInstanceState.getString("idCard");
            idCardNumber.setText(idCard);
            String profesTitle = savedInstanceState.getString("profesTitle");
            professionTitle.setText(profesTitle);
            String serviceGrop = savedInstanceState.getString("serviceGrop");
            serviceOrgant.setText(serviceGrop);
            String workExper = savedInstanceState.getString("workExper");
            workExperience.setText(workExper);
            String speciaExper = savedInstanceState.getString("speciaExper");
            speciaExpert.setText(speciaExper);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doctor_regist_back:
                finish();
                break;
            case R.id.relative_work:
                startActivityForResult(new Intent(this, BusineExperActivity.class), BUSINES_WORK_CODE);
                break;
            case R.id.relative_specia:
                startActivityForResult(new Intent(this, SpeciaExpertActivity.class), SPECIA_EXPER_CODE);
                break;
            case R.id.relative_account:
                startActivityForResult(new Intent(this, AcuntReiveActivity.class), ACUNT_CODE);
                break;
            //医生用户提交验证信息
            case R.id.doctor_regist:
                //医生名字
                docName = doctorName.getText().toString();
                //身份证号
                idCard = idCardNumber.getText().toString();
                //专业职称
                profesTitle = professionTitle.getText().toString();
                //服务机构
                serviceGrop = serviceOrgant.getText().toString();
                //从业经历
                workExper = workExperience.getText().toString();
                //专科特长
                speciaExper = speciaExpert.getText().toString();
                //收款账户
                acunt = receiveAccunt.getText().toString();
                if ((!(TextUtils.isEmpty(docName))) && (!(TextUtils.isEmpty(idCard))) && (!(TextUtils.isEmpty(profesTitle))) &&
                        (!(TextUtils.isEmpty(serviceGrop))) && (!(TextUtils.isEmpty(workExper))) && (!(TextUtils.isEmpty(speciaExper)))
                        && (!(TextUtils.isEmpty(acunt)))) {
                    //提交注册信息并验证
                    NetWorkUtill.doctorRegistEndSure(phone, smsCode, bindKey, nickName, nickPasword, macAddres, docName, idCard, profesTitle, serviceGrop,
                            workExper, speciaExper, banckCode, banckCard, bankName, branchName, aliPayAcunt, microAcunt, holdName, payType);
                    //跳转到首页
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("doctorUser", DOCTOR_USER);
                    startActivity(intent);
                } else {
                    BaseUtill.toastUtil("请补充输入必填项注册信息");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //获取从业经历信息
            case BUSINES_WORK_CODE:
                if (resultCode == RESULT_OK) {
                    String works = data.getStringExtra("works");
                    workExperience.setText(works);
                }
                break;
            //获取专科特长信息
            case SPECIA_EXPER_CODE:
                if (resultCode == RESULT_OK) {
                    String specia = data.getStringExtra("specia");
                    speciaExpert.setText(specia);
                }
                break;
            //获取收款账户信息
            case ACUNT_CODE:
                if (resultCode == RESULT_OK) {
                    //获取支付宝帐号信息
                    aliPayAcunt = data.getStringExtra("aliPayAcunt");
                    if (aliPayAcunt != null) {
                        receiveAccunt.setText(aliPayAcunt);
                        holdName = data.getStringExtra("aliPayName");
                        payType = 1;
                        return;
                    }
                    //获取微信账户信息
                    microAcunt = data.getStringExtra("microAcunt");
                    if (microAcunt != null) {
                        receiveAccunt.setText(microAcunt);
                        holdName = data.getStringExtra("microAcuntName");
                        payType = 2;
                        return;
                    }
                    //获取注册银行卡信息
                    BankInputInfo bankInputInfo = (BankInputInfo) data.getSerializableExtra("bankInputInfo");
                    if (bankInputInfo != null) {
                        //银行卡号
                        banckCard = bankInputInfo.getBanckCard();
                        receiveAccunt.setText(banckCard);
                        //银行编码
                        banckCode = bankInputInfo.getBanckCode();
                        //银行名字
                        bankName = bankInputInfo.getBankName();
                        //支行名字
                        branchName = bankInputInfo.getBranchName();
                        //银行卡开户人
                        holdName = bankInputInfo.getCardOwener();
                        payType = 3;
                        return;
                    }
                }
                break;
        }
    }
}
