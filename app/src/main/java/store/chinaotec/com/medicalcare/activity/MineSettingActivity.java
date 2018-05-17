package store.chinaotec.com.medicalcare.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.simple.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.SuggestionFeedbackActivity;
import store.chinaotec.com.medicalcare.utill.AppUtils;
import store.chinaotec.com.medicalcare.utill.EventTag;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * 应用设置功能展示页面
 */
public class MineSettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.mine_setting_back)
    ImageView mineSettingBack;
    @Bind(R.id.linear_message_notice)
    LinearLayout linearMessageNotice;
    @Bind(R.id.linear_remove_cash)
    LinearLayout linearRemoveCash;
    @Bind(R.id.linear_secret)
    LinearLayout linearSecret;
    @Bind(R.id.linear_help_feedback)
    LinearLayout linearHelpFeedback;
    @Bind(R.id.linear_statement)
    LinearLayout linearStatement;
    @Bind(R.id.now_version)
    TextView nowVersion;
    @Bind(R.id.sign_out)
    Button signOut;

    private AlertDialog alertDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);
        ButterKnife.bind(this);
        initBseData();
        initListener();
    }

    private void initBseData() {
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        String versionName = MyApp.getContext().getAppVersionName(this);
        nowVersion.setText(versionName + "_" + AppUtils.getAppVersionCode());
    }

    private void initListener() {
        mineSettingBack.setOnClickListener(this);
        linearMessageNotice.setOnClickListener(this);
        linearRemoveCash.setOnClickListener(this);
        linearSecret.setOnClickListener(this);
        linearHelpFeedback.setOnClickListener(this);
        linearStatement.setOnClickListener(this);
        signOut.setOnClickListener(this);

        Intent intent = getIntent();
        boolean login = intent.getBooleanExtra("login", false);
        if (login) {
            signOut.setVisibility(View.VISIBLE);
        } else {
            signOut.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_setting_back:
                finish();
                break;
            case R.id.linear_message_notice:
                startActivity(new Intent(getApplicationContext(), MessageNoticeActivity.class));
                break;
            case R.id.linear_remove_cash:
                removeCash();
                break;
            case R.id.linear_secret:
                break;
            case R.id.linear_help_feedback:
                startActivity(new Intent(this, SuggestionFeedbackActivity.class));
                break;
            case R.id.linear_statement:
                Intent intent = new Intent(this, HealthInfoDetailActivity.class);
                intent.putExtra("title", "关于我们");
                intent.putExtra("url", "http://219.144.68.15:9000/img/userfiles/appInfo/statement.html");
                startActivity(intent);
                break;
            //退出程序登录
            case R.id.sign_out:
                sharedPreferences.edit().putBoolean("login", false).apply();
                sharedPreferences.edit().putBoolean("signOut", true).apply();
                sharedPreferences.edit().putString("saveSid", "").apply();
                EventBus.getDefault().post(0, EventTag.LOGIN);
                finish();
                break;
            case R.id.remove_cash_canle:
                Toast.makeText(getApplicationContext(), "清楚缓存操作取消了", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                break;
            case R.id.remove_cash_ok:
                Toast.makeText(getApplicationContext(), "清楚缓存完毕", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                break;
        }
    }

    private void removeCash() {
        alertDialog = new AlertDialog.Builder(this, R.style.MyDialog).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.item_remove_cash);
        Button removeCashCanle = (Button) alertDialog.getWindow().findViewById(R.id.remove_cash_canle);
        Button removeCashOk = (Button) alertDialog.getWindow().findViewById(R.id.remove_cash_ok);
        //清除缓存,取消清除缓存操作逻辑处理
        removeCashCanle.setOnClickListener(this);
        removeCashOk.setOnClickListener(this);
    }
}
