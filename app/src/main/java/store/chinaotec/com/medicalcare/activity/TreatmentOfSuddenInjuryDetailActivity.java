package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2018/3/26 0026.
 * 突发伤病详情
 */

public class TreatmentOfSuddenInjuryDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.wv_sudden_detail)
    WebView webView;
    @Bind(R.id.online_doctor)
    Button onlineDoctor;
    @Bind(R.id.intellg_treatment)
    Button intellgTreatment;
    @Bind(R.id.one_call_help)
    Button oneCallHelp;
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_bar)
    TextView tvTitleBar;
    private String title;
    private String url;
    private String sid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudden_injury_detail);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        initView();
        initData();
    }

    private void initView() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        tvTitleBar.setText(title);
        ivTitleBack.setOnClickListener(this);
        onlineDoctor.setOnClickListener(this);
        intellgTreatment.setOnClickListener(this);
        oneCallHelp.setOnClickListener(this);
    }

    private void initData() {
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.online_doctor:
                if (!"".equals(sid)) {
                    startActivity(new Intent(this, HelpDoctorShowActivity.class));
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class),ResourseSum.LOGIN_RESPONSE);
                }
                break;
            case R.id.intellg_treatment:
                startActivity(new Intent(this, SeeDoctorActivity.class));
                break;
            case R.id.one_call_help:
                startActivity(new Intent(this, OneKeyCallActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
    }
}
