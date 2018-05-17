package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.ToastUtil;
import store.chinaotec.com.medicalcare.view.InstallVideoApp;
import store.chinaotec.com.medicalcare.view.LoadingAlertDialog;
import store.chinaotec.com.medicalcare.view.MedUnionView;

/**
 * 医联体医疗页面
 */
public class MedicUnionTreatActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.linear_far_treat)
    MedUnionView linearFarTreat;
    @Bind(R.id.linear_video_meet)
    MedUnionView linearVideoMeet;
    @Bind(R.id.linear_share_mess)
    MedUnionView linearShareMess;
    @Bind(R.id.linear_sign)
    MedUnionView linearSign;
    private LoadingAlertDialog mDialog;
    private TextView tv_include_title_view;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line_doctor);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("医联体医疗");
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        linearFarTreat.setOnClickListener(this);
        linearShareMess.setOnClickListener(this);
        linearSign.setOnClickListener(this);
        linearVideoMeet.setOnClickListener(this);
        mDialog = new LoadingAlertDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //远程诊疗
            case R.id.linear_far_treat:
                //视频会议
            case R.id.linear_video_meet:
                if (!"".equals(sid)){
                    try {
                        InstallVideoApp.InstallLaunchApp(mDialog, MedicUnionTreatActivity.this);
                    } catch (Exception e) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        ToastUtil.showToast(MedicUnionTreatActivity.this, "启动失败，请稍后重试!");
                    }
                }else {
                    startActivityForResult(new Intent(this,LoginActivity.class),ResourseSum.LOGIN_RESPONSE);
                }

                break;
            //信息共享
            case R.id.linear_share_mess:
                startActivity(new Intent(this, MedicalInfoShareActivity.class));
                break;
            //医患签约
            case R.id.linear_sign:
                startActivity(new Intent(this, SignNurseDoctorActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
    }
}
