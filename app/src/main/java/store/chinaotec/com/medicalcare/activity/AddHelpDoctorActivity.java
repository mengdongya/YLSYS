package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.SpUtill;

public class AddHelpDoctorActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.add)
    TextView add;
    @Bind(R.id.doctor_name)
    EditText doctorName;
    @Bind(R.id.doctor_phone)
    EditText doctorPhone;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help_doctor);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        add.setOnClickListener(this);
        //用户登陆后返回的sid
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                String addDoctorName = doctorName.getText().toString();
                String addDoctorPhone = doctorPhone.getText().toString();
                if (addDoctorPhone.length() == 11 && !TextUtils.isEmpty(addDoctorName)) {//判断添加求救医生电话是否为11位
                    NetWorkUtill.addHelpDoctor(sid, addDoctorName, addDoctorPhone);
                    //添加完求救医生的标志
                    SpUtill.putBoolen(AddHelpDoctorActivity.this, ResourseSum.Medica_SP, "addDoctor", true);
                    finish();
                } else {
                    BaseUtill.toastUtil("请检查你输入的求救医生信息");
                }
                break;
        }
    }
}
