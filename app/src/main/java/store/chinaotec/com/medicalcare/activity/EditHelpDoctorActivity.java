package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HelpDoctorBeean;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 编辑修改求救医生信息页面
 */
public class EditHelpDoctorActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.edit)
    TextView edit;
    @Bind(R.id.edit_doctor_name)
    EditText editDoctorName;
    @Bind(R.id.edit_doctor_phone)
    EditText editDoctorPhone;
    private HelpDoctorBeean.DataBean.MemberCalloutListBean memberCalBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_help_doctor);
        ButterKnife.bind(this);
        initBasicData();

    }

    private void initBasicData() {
        edit.setOnClickListener(this);
        //获取修改的求救医生信息
        Intent intent = getIntent();
        memberCalBean = ((HelpDoctorBeean.DataBean.MemberCalloutListBean) intent.getSerializableExtra("memberCalBean"));
        MyLog.d("initBasicData..当前修改的求救医生信息..名字.." + memberCalBean.getCalloutName() + "..电话.." + memberCalBean.getTelephone());
        //展示当前修改的求救医生信息
        editDoctorName.setText(memberCalBean.getCalloutName());
        editDoctorPhone.setText(memberCalBean.getTelephone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
                //用户登录后返回的sid  修改后的求救医生信息 名字和电话号码  信息的id
                String saveSid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
                String editName = editDoctorName.getText().toString();
                String editPhone = editDoctorPhone.getText().toString();
                String memberCalloutId = memberCalBean.getMemberCalloutId();
                //修改求救医生信息
                NetWorkUtill.editHelpDoctor(saveSid, memberCalloutId, editName, editPhone);
                //修改求救医生信息的标志 addDoctor 表示修改成功
                SpUtill.putBoolen(this,ResourseSum.Medica_SP,"editDoctor",true);
                BaseUtill.toastUtil("急救医生信息修改成功");
                finish();
                break;
        }
    }
}
