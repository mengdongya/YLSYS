package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 我的设备已添加设备显示页面
 */
public class DeviceNoAddActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.device_name)
    TextView deviceName;
    @Bind(R.id.statues)
    TextView statues;
    @Bind(R.id.create_date)
    TextView createDate;
    @Bind(R.id.change_device)
    Button changeDevice;
    @Bind(R.id.add_click_buy)
    TextView addClickBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device_added);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        changeDevice.setOnClickListener(this);
        addClickBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_device:
                Toast.makeText(getApplicationContext(), "更换设备成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_click_buy:
                Toast.makeText(getApplicationContext(), "购买设备成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
