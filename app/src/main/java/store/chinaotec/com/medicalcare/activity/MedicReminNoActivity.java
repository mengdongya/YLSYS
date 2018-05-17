package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 没有体检提醒的展示页面
 */
public class MedicReminNoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.add_manuly_medical)
    TextView addManulyMedical;
    @Bind(R.id.recomendHospRecycleview)
    RecyclerView recomendHospRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_medic_remind);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        addManulyMedical.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_manuly_medical:
                startActivity(new Intent(getApplicationContext(), AddRemindsActivity.class));
                break;
        }
    }
}
