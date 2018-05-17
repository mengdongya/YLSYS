package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 一键呼叫联系人展示页面
 */
public class ContactShowActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.contact_show_back)
    ImageView contactShowBack;
    @Bind(R.id.edit_contact_switch)
    Button editContactSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        contactShowBack.setOnClickListener(this);
        editContactSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_show_back:
                finish();
                break;
            case R.id.edit_contact_switch:
                startActivity(new Intent(getApplicationContext(), ContaAddActivity.class));
                break;
        }
    }
}
