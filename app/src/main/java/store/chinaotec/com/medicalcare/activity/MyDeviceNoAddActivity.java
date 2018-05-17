package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.BaseUtill;

/**
 * 我的设备没有添加设备显示页面
 */
public class MyDeviceNoAddActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.add_device)
    Button addDevice;
    @Bind(R.id.no_add_click_buy)
    TextView noAddClickBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        addDevice.setOnClickListener(this);
        noAddClickBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_device:
                Intent intent = new Intent();
                intent.putExtra("DeviceNoAddActivity", "DeviceNoAddActivity");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.no_add_click_buy:
                BaseUtill.toastUtil("购买设备成功");
                break;
        }
    }
}
