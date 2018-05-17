package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 收款账户选择页面
 */
public class AcuntReiveActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.relative_ali_pay)
    RelativeLayout relativeAliPay;
    @Bind(R.id.relative_micro_letter)
    RelativeLayout relativeMicroLetter;
    @Bind(R.id.relative_blank_card)
    RelativeLayout relativeBlankCard;

    private final int ALIPAY_CODE = 11;
    private final int MICRO_CODE = 22;
    private final int BANK_CODE = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_reive);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        relativeAliPay.setOnClickListener(this);
        relativeMicroLetter.setOnClickListener(this);
        relativeBlankCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_ali_pay:
                startActivityForResult(new Intent(this, AliPayAcuntActivity.class), ALIPAY_CODE);
                break;
            case R.id.relative_micro_letter:
                startActivityForResult(new Intent(this, MicroAcuntInputActivity.class), MICRO_CODE);
                break;
            case R.id.relative_blank_card:
                startActivityForResult(new Intent(this, CardInputActivity.class), BANK_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ALIPAY_CODE:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
            case MICRO_CODE:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
            case BANK_CODE:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
        }
    }
}
