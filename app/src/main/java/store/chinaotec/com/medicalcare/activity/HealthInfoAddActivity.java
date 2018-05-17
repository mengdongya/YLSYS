package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * 慢性病管理模块,血压,血糖等身体健康信息输入页面
 */
public class HealthInfoAddActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.syst_blood_prese)
    EditText systBloodPrese;
    @Bind(R.id.diast_blood_prese)
    EditText diastBloodPrese;
    @Bind(R.id.heart_rate)
    EditText heartRate;
    @Bind(R.id.blood_sugar)
    EditText bloodSugar;
    @Bind(R.id.comit_health_info)
    Button comitHealthInfo;
    @Bind(R.id.blood_fat)
    EditText bloodFat;
    @Bind(R.id.tv_title)
    TextView tv_title;
    private String patientid;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info_add);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        comitHealthInfo.setOnClickListener(this);
        //获取病人id
        Intent intent = getIntent();
        patientid = intent.getStringExtra("patientid");
        title = intent.getStringExtra("title");
        tv_title.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //提交血压等数据
            case R.id.comit_health_info:
                //收缩压数据值
                String bloodPresSys = systBloodPrese.getText().toString();
                //舒张压数据值
                String bloodPresDia = diastBloodPrese.getText().toString();
                //心率数据值
                String rateHeart = heartRate.getText().toString();
                //血糖数据值
                String sugarBlood = bloodSugar.getText().toString();
                //血脂数据值
                String fatBlood = bloodFat.getText().toString();
                //添加各种健康信息数据值
//                if ("".equals(bloodPresSys) || Integer.parseInt(format(bloodPresSys)) < 40 || Integer.parseInt(format(bloodPresSys)) > 250){
//                    BaseUtill.toastUtil("请输入正确的收缩压");
//                    return;
//                }
//                if ("".equals(bloodPresDia) || Integer.parseInt(format(bloodPresDia)) < 40 || Integer.parseInt(format(bloodPresDia)) > 250){
//                    BaseUtill.toastUtil("请输入正确的舒张压");
//                    return;
//                }
//                if ("".equals(rateHeart) || Integer.parseInt(format(rateHeart)) < 30 || Integer.parseInt(format(rateHeart)) > 180){
//                    BaseUtill.toastUtil("请输入正确的心率");
//                    return;
//                }
    //                if ("".equals(sugarBlood) || Integer.parseInt(sugarBlood) < 0 || Integer.parseInt(sugarBlood) > 12){
    //                    BaseUtill.toastUtil("请输入正确的血糖");
    //                    return;
    //                }
//                if ("".equals(fatBlood) || Integer.parseInt(format(fatBlood)) < 0 ){
//                    BaseUtill.toastUtil("请输入正确的体重");
//                    return;
//                }

                if (!"".equals(bloodPresSys) || Integer.parseInt(format(bloodPresSys)) > 40 || Integer.parseInt(format(bloodPresSys)) < 250 ||
                        !"".equals(bloodPresDia) || Integer.parseInt(format(bloodPresDia)) > 40 || Integer.parseInt(format(bloodPresDia)) < 250 ||
                        !"".equals(rateHeart) || Integer.parseInt(format(rateHeart)) > 30 || Integer.parseInt(format(rateHeart)) < 180 ||
                        !"".equals(fatBlood) || Integer.parseInt(format(fatBlood)) > 0 ){

                    NetWorkUtill.addHealthInfo(patientid, bloodPresSys, rateHeart, sugarBlood, fatBlood, bloodPresDia);
                    BaseUtill.toastUtil("血压等健康信息数据添加成功");
                    Intent intent = new Intent(ResourseSum.REFRESH_HEALTH_FRAGMENT);
                    sendBroadcast(intent);
                    finish();
                }else{
                    BaseUtill.toastUtil("请输入正确的数据");
                    return;
                }
                break;
        }
    }

    private String format(String str){
//        DecimalFormat df = new DecimalFormat("###0");
        if (!"".equals(str)){
            int idx = str.lastIndexOf(".");//查找小数点的位置
            if (idx != -1){
                String strNum = str.substring(0,idx);//截取从字符串开始到小数点位置的字符串，就是整数部分
                return strNum;
            }else {
                return str;
            }
        }else {
            return "-1";
        }
    }
}
