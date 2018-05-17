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

public class AliPayAcuntActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.sure_alipay)
    Button sureAlipay;
    @Bind(R.id.alipay_acunt)
    EditText alipayAcunt;
    @Bind(R.id.alipay_holder)
    EditText alipayHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_acunt_input);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        sureAlipay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_alipay:
                //获取支付宝帐号
                String aliPayAcunt = alipayAcunt.getText().toString();
                //获取支付宝用户名
                String aliPayName = alipayHolder.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("aliPayAcunt", aliPayAcunt);
                intent.putExtra("aliPayName", aliPayName);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
