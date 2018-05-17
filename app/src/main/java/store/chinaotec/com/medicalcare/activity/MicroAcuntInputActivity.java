package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

public class MicroAcuntInputActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.micro_back)
    ImageView microBack;
    @Bind(R.id.sure_micro)
    Button sureMicro;
    @Bind(R.id.microleter_acunt)
    EditText microleterAcunt;
    @Bind(R.id.microleter_acunt_name)
    EditText microleterAcuntName;
    private String microAcunt;
    private String microAcuntName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_acunt_input);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        microBack.setOnClickListener(this);
        sureMicro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.micro_back:
                finish();
                break;
            case R.id.sure_micro:
                //获取微信帐号
                microAcunt = microleterAcunt.getText().toString();
                //获取微信用户名
                microAcuntName = microleterAcuntName.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("microAcunt", microAcunt);
                intent.putExtra("microAcuntName", microAcuntName);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
