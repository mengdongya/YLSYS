package store.chinaotec.com.medicalcare.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.alibaba.fastjson.JSON;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.InquiryAddCase;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 去看病二级页面
 */
public class SeeDoctorActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.ask_people)
    TextView askPeople;
    @Bind(R.id.ask_linear)
    LinearLayout askLinear;
    @Bind(R.id.see_doctor_next)
    Button seeDoctorNext;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    private PopupWindow mPopWindow;
    private ImageView choseMeLogo;
    private ImageView choseBehalfLogo;
    private TextView meText;
    private TextView behalfText;
    private SharedPreferences sharedPreferences;
    private AlertDialog alertDialog;
    private String sid;
    private static final int REQ_CODE_PERMISSION = 0x1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_doctor);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        askLinear.setOnClickListener(this);
        seeDoctorNext.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        //初始化sp对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        //默认选择自己代述
        sharedPreferences.edit().putBoolean("daishu", false).apply();
        sharedPreferences.edit().putBoolean("is_scan", false).apply();
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.see_doctor_next:
//                startActivity(new Intent(this, VoiceConnectActivity.class));
                String askType = askPeople.getText().toString();
                if(!StringUtils.isEmpty(sid)){
                    if ("自己".equals(askType)){
                        //判断性别 年龄是否存在
                        int sex = sharedPreferences.getInt("saveSex", 222);
//                        String age = sharedPreferences.getString("saveAge", "");
//                        if (TextUtils.isEmpty(age) || sex == 222){
//                            startActivity(new Intent(this,PerfectInformationActivity.class));
//                        }else {
                        sharedPreferences.edit().putInt("sex", sex).apply();
                        sharedPreferences.edit().putString("daishuage","").apply();

                        startActivity(new Intent(this,IntelligentInquiryActivity.class));
//                        }

                    }else {
                        startActivity(new Intent(this,PerfectInformationActivity.class));
                    }
                }else {
                    startActivity(new Intent(this,PerfectInformationActivity.class));
                }

                break;
            case R.id.ask_linear:
                showPopWindow();
                break;
            //自己
            case R.id.linear_mine:
                meText.setTextColor(getResources().getColor(R.color.colorTooBar));
                choseMeLogo.setVisibility(View.VISIBLE);
                askPeople.setText("自己");
                alertDialog.dismiss();
                sharedPreferences.edit().putBoolean("daishu", false).apply();
                break;
            //代述
            case R.id.linear_behalf:
                behalfText.setTextColor(getResources().getColor(R.color.colorTooBar));
                choseBehalfLogo.setVisibility(View.VISIBLE);
                askPeople.setText("代述");
                alertDialog.dismiss();
                sharedPreferences.edit().putBoolean("daishu", true).apply();
                break;
            case R.id.iv_scan:
                // Open Scan Activity
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
        }
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(this, "You must agree the camera permission request " +
                            "before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String stringExtra = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        if (!StringUtils.isEmpty(stringExtra)){
                            InquiryAddCase.DataBase base = JSON.parseObject(stringExtra, InquiryAddCase.DataBase.class);
                            if (!"".equals(base.getCasecode()) && !"".equals(base.getSessionid())){
                                //保存病历号
                                sharedPreferences.edit().putString("caseCode", base.getCasecode()).apply();
                                sharedPreferences.edit().putString("sessionId", base.getSessionid()).apply();
                                sharedPreferences.edit().putBoolean("is_scan", true).apply();
                                startActivity(new Intent(this,IntelligentInquiryActivity.class));
                            }
                        }

                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
//                            tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }
    }

    private void showPopWindow() {
        View inflate = LayoutInflater.from(SeeDoctorActivity.this).inflate(R.layout.chose_ask_people, null);
        inflate.setAlpha(0.72f);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setContentView(inflate);
        Window window = alertDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            WindowManager windowManager = getWindowManager();
            Display defaultDisplay = windowManager.getDefaultDisplay();
            //设置提示弹窗的位置
            lp.x = -170;
            lp.y = defaultDisplay.getHeight() / 3 - 17;
            MyLog.d("弹窗Y坐标..." + defaultDisplay.getHeight() / 2);
            lp.width = askLinear.getWidth() + 50;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            choseMeLogo = (ImageView) inflate.findViewById(R.id.chose_me_logo);
            choseBehalfLogo = (ImageView) inflate.findViewById(R.id.chose_behalf_logo);
            meText = (TextView) inflate.findViewById(R.id.me_text);
            behalfText = (TextView) inflate.findViewById(R.id.behalf_text);
            View linearMine = inflate.findViewById(R.id.linear_mine);
            View linearBehalf = inflate.findViewById(R.id.linear_behalf);
            linearMine.setOnClickListener(this);
            linearBehalf.setOnClickListener(this);
        }
    }
}
