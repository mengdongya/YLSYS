package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.BankInputInfo;

/**
 * 银行卡信息输入页面
 */
public class CardInputActivity extends BaseActivity implements View.OnClickListener {

    private final int BLANK_CHOSE_CODE = 80;
    @Bind(R.id.blank_back)
    ImageView blankBack;
    @Bind(R.id.sure_blank)
    Button sureBlank;
    @Bind(R.id.relative_chose_blank)
    RelativeLayout relativeChoseBlank;
    @Bind(R.id.blank)
    TextView blank;
    @Bind(R.id.card)
    TextView card;
    @Bind(R.id.bank_card_number)
    EditText bankCardNumber;
    @Bind(R.id.branch)
    TextView branch;
    @Bind(R.id.bank_branch_name)
    EditText bankBranchName;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.bank_input_name)
    TextView bankInputName;
    @Bind(R.id.card_hold_name)
    EditText cardHoldName;

    private String bankName;
    private String bankCode;
    private String branchName;
    private String bankCard;
    private String holdName;
    private BankInputInfo bankInputInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blan_card_input);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        blankBack.setOnClickListener(this);
        sureBlank.setOnClickListener(this);
        relativeChoseBlank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blank_back:
                finish();
                break;
            case R.id.relative_chose_blank:
                startActivityForResult(new Intent(this, BankChoseActivity.class), BLANK_CHOSE_CODE);
                break;
            case R.id.sure_blank:
                //支行名字
                branchName = bankBranchName.getText().toString();
                //银行卡号
                bankCard = bankCardNumber.getText().toString();
                //银行卡开户人
                holdName = cardHoldName.getText().toString();
                bankInputInfo = new BankInputInfo(bankCode, bankName, branchName, bankCard, holdName);
                Intent intent = new Intent();
                intent.putExtra("bankInputInfo", bankInputInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BLANK_CHOSE_CODE:
                if (resultCode == RESULT_OK) {
                    //银行名字
                    bankName = data.getStringExtra("blankName");
                    //银行编码
                    bankCode = data.getStringExtra("bankCode");
                    bankInputName.setText(bankName);
                }
                break;
        }
    }
}
