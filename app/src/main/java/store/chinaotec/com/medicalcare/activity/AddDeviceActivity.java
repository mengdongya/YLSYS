package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
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
 * 添加健康检测设备页面
 */
public class AddDeviceActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.connect_device)
    Button connectDevice;
    @Bind(R.id.add_manuly)
    TextView addManuly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        connectDevice.setOnClickListener(this);
        addManuly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_device:
                Toast.makeText(getApplicationContext(), "连接设备成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.add_manuly:
                startActivity(new Intent(getApplicationContext(), ManuHealMesActivity.class));
                break;
        }
    }
}
