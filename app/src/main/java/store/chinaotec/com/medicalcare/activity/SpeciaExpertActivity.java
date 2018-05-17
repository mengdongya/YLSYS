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

/**
 * 专业特长填写页面
 */
public class SpeciaExpertActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.specia_back)
    ImageView speciaBack;
    @Bind(R.id.specia_expert)
    EditText speciaExpert;
    @Bind(R.id.sure_specia)
    Button sureSpecia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specia_expert);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        speciaBack.setOnClickListener(this);
        sureSpecia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.specia_back:
                finish();
                break;
            case R.id.sure_specia:
                String specia = speciaExpert.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("specia", specia);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
