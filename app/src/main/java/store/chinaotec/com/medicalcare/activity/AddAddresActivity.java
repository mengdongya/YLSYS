package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 收货地址添加页面
 */
public class AddAddresActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ship_address_save)
    Button shipAddressSave;
    @Bind(R.id.receive_phone)
    EditText receivePhone;
    @Bind(R.id.receive_area)
    EditText receiveArea;
    @Bind(R.id.receive_street)
    EditText receiveStreet;
    @Bind(R.id.detail_address)
    EditText detailAddress;
    @Bind(R.id.ship_edit)
    RadioButton shipEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ship_address);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        shipAddressSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ship_address_save:
                Toast.makeText(this, "收货地址添加成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
