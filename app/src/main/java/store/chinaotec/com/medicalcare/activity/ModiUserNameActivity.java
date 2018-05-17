package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 修改昵称页面
 */
public class ModiUserNameActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.change_username)
    EditText changeUsername;
    @Bind(R.id.sure_come_back)
    Button sureComeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        sureComeBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_come_back:
                Toast.makeText(this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
