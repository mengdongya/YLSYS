package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 从业经历填写页面
 */
public class BusineExperActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.sure_work)
    TextView sureWork;
    @Bind(R.id.busines_work)
    EditText businesWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busine_experenc);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        sureWork.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_work:
                String works = businesWork.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("works", works);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
