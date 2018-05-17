package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 联系方式选择页面 电话  邮箱  微信三选一
 */
public class ContactChoseActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.connect_chose_back)
    ImageView connectChoseBack;
    @Bind(R.id.tele_phone)
    EditText telePhone;
    @Bind(R.id.micro_letter)
    EditText microLetter;
    @Bind(R.id.mail_box)
    EditText mailBox;
    @Bind(R.id.sure_ok)
    Button sureOk;
    private String connectmes;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conect_mes_chose);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        connectChoseBack.setOnClickListener(this);
        sureOk.setOnClickListener(this);

        telePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                connectmes = s.toString();
                if (!(connectmes.isEmpty())) {
                    type = 1;
                    microLetter.setFocusable(false);
                    mailBox.setFocusable(false);
                }
            }
        });
        microLetter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                connectmes = s.toString();
                if (!(connectmes.isEmpty())) {
                    type = 2;
                    telePhone.setFocusable(false);
                    mailBox.setFocusable(false);
                }
            }
        });
        mailBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                connectmes = s.toString();
                if (!(connectmes.isEmpty())) {
                    type = 3;
                    microLetter.setFocusable(false);
                    telePhone.setFocusable(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_chose_back:
                finish();
                break;
            case R.id.sure_ok:
                Intent intent = new Intent();
                intent.putExtra("connect", connectmes);
                intent.putExtra("type", type);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
